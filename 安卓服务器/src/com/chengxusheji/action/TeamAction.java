package com.chengxusheji.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork2.ActionContext;
import com.chengxusheji.dao.TeamDAO;
import com.chengxusheji.domain.Team;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class TeamAction extends BaseAction {

    /*�������Ҫ��ѯ������: �û���*/
    private String teamUserName;
    public void setTeamUserName(String teamUserName) {
        this.teamUserName = teamUserName;
    }
    public String getTeamUserName() {
        return this.teamUserName;
    }

    /*�������Ҫ��ѯ������: ��������*/
    private String email;
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return this.email;
    }

    /*�������Ҫ��ѯ������: ־Ը��������*/
    private String teamName;
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
    public String getTeamName() {
        return this.teamName;
    }

    /*�������Ҫ��ѯ������: ����ԺУ*/
    private String shoolName;
    public void setShoolName(String shoolName) {
        this.shoolName = shoolName;
    }
    public String getShoolName() {
        return this.shoolName;
    }

    /*�������Ҫ��ѯ������: ��������*/
    private String contactGroup;
    public void setContactGroup(String contactGroup) {
        this.contactGroup = contactGroup;
    }
    public String getContactGroup() {
        return this.contactGroup;
    }

    /*�������Ҫ��ѯ������: ���ܵ�λ*/
    private String mainUnit;
    public void setMainUnit(String mainUnit) {
        this.mainUnit = mainUnit;
    }
    public String getMainUnit() {
        return this.mainUnit;
    }

    /*�������Ҫ��ѯ������: ����*/
    private String area;
    public void setArea(String area) {
        this.area = area;
    }
    public String getArea() {
        return this.area;
    }

    /*�������Ҫ��ѯ������: ��ϵ�˵绰*/
    private String telephone;
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getTelephone() {
        return this.telephone;
    }

    /*�������Ҫ��ѯ������: ����������*/
    private String chargeMan;
    public void setChargeMan(String chargeMan) {
        this.chargeMan = chargeMan;
    }
    public String getChargeMan() {
        return this.chargeMan;
    }

    /*��ǰ�ڼ�ҳ*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*һ������ҳ*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*ҵ������*/
    @Resource TeamDAO teamDAO;

    /*��������Team����*/
    private Team team;
    public void setTeam(Team team) {
        this.team = team;
    }
    public Team getTeam() {
        return this.team;
    }

    /*��ת�����Team��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���Team��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddTeam() {
        ActionContext ctx = ActionContext.getContext();
        /*��֤�û����Ƿ��Ѿ�����*/
        String teamUserName = team.getTeamUserName();
        Team db_team = teamDAO.GetTeamByTeamUserName(teamUserName);
        if(null != db_team) {
            ctx.put("error",  java.net.URLEncoder.encode("���û����Ѿ�����!"));
            return "error";
        }
        try {
            teamDAO.AddTeam(team);
            ctx.put("message",  java.net.URLEncoder.encode("Team��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Team���ʧ��!"));
            return "error";
        }
    }

    /*��ѯTeam��Ϣ*/
    public String QueryTeam() {
        if(currentPage == 0) currentPage = 1;
        if(teamUserName == null) teamUserName = "";
        if(email == null) email = "";
        if(teamName == null) teamName = "";
        if(shoolName == null) shoolName = "";
        if(contactGroup == null) contactGroup = "";
        if(mainUnit == null) mainUnit = "";
        if(area == null) area = "";
        if(telephone == null) telephone = "";
        if(chargeMan == null) chargeMan = "";
        List<Team> teamList = teamDAO.QueryTeamInfo(teamUserName, email, teamName, shoolName, contactGroup, mainUnit, area, telephone, chargeMan, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        teamDAO.CalculateTotalPageAndRecordNumber(teamUserName, email, teamName, shoolName, contactGroup, mainUnit, area, telephone, chargeMan);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = teamDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = teamDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("teamList",  teamList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("teamUserName", teamUserName);
        ctx.put("email", email);
        ctx.put("teamName", teamName);
        ctx.put("shoolName", shoolName);
        ctx.put("contactGroup", contactGroup);
        ctx.put("mainUnit", mainUnit);
        ctx.put("area", area);
        ctx.put("telephone", telephone);
        ctx.put("chargeMan", chargeMan);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryTeamOutputToExcel() { 
        if(teamUserName == null) teamUserName = "";
        if(email == null) email = "";
        if(teamName == null) teamName = "";
        if(shoolName == null) shoolName = "";
        if(contactGroup == null) contactGroup = "";
        if(mainUnit == null) mainUnit = "";
        if(area == null) area = "";
        if(telephone == null) telephone = "";
        if(chargeMan == null) chargeMan = "";
        List<Team> teamList = teamDAO.QueryTeamInfo(teamUserName,email,teamName,shoolName,contactGroup,mainUnit,area,telephone,chargeMan);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Team��Ϣ��¼"; 
        String[] headers = { "�û���","��������","־Ը��������","����ԺУ","��������","���ܵ�λ","����","�ʱ�","��������","־Ը������","��ϵ�˵绰","����������"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<teamList.size();i++) {
        	Team team = teamList.get(i); 
        	dataset.add(new String[]{team.getTeamUserName(),team.getEmail(),team.getTeamName(),team.getShoolName(),team.getContactGroup(),team.getMainUnit(),team.getArea(),team.getPostCode(),new SimpleDateFormat("yyyy-MM-dd").format(team.getBirthDate()),team.getPersonNum() + "",team.getTelephone(),team.getChargeMan()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Team.xls");//filename�����ص�xls���������������Ӣ�� 
			response.setContentType("application/msexcel;charset=UTF-8");//�������� 
			response.setHeader("Pragma","No-cache");//����ͷ 
			response.setHeader("Cache-Control","no-cache");//����ͷ 
			response.setDateHeader("Expires", 0);//��������ͷ  
			String rootPath = ServletActionContext.getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
		return null;
    }
    /*ǰ̨��ѯTeam��Ϣ*/
    public String FrontQueryTeam() {
        if(currentPage == 0) currentPage = 1;
        if(teamUserName == null) teamUserName = "";
        if(email == null) email = "";
        if(teamName == null) teamName = "";
        if(shoolName == null) shoolName = "";
        if(contactGroup == null) contactGroup = "";
        if(mainUnit == null) mainUnit = "";
        if(area == null) area = "";
        if(telephone == null) telephone = "";
        if(chargeMan == null) chargeMan = "";
        List<Team> teamList = teamDAO.QueryTeamInfo(teamUserName, email, teamName, shoolName, contactGroup, mainUnit, area, telephone, chargeMan, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        teamDAO.CalculateTotalPageAndRecordNumber(teamUserName, email, teamName, shoolName, contactGroup, mainUnit, area, telephone, chargeMan);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = teamDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = teamDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("teamList",  teamList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("teamUserName", teamUserName);
        ctx.put("email", email);
        ctx.put("teamName", teamName);
        ctx.put("shoolName", shoolName);
        ctx.put("contactGroup", contactGroup);
        ctx.put("mainUnit", mainUnit);
        ctx.put("area", area);
        ctx.put("telephone", telephone);
        ctx.put("chargeMan", chargeMan);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�Team��Ϣ*/
    public String ModifyTeamQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������teamUserName��ȡTeam����*/
        Team team = teamDAO.GetTeamByTeamUserName(teamUserName);

        ctx.put("team",  team);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Team��Ϣ*/
    public String FrontShowTeamQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������teamUserName��ȡTeam����*/
        Team team = teamDAO.GetTeamByTeamUserName(teamUserName);

        ctx.put("team",  team);
        return "front_show_view";
    }

    /*�����޸�Team��Ϣ*/
    public String ModifyTeam() {
        ActionContext ctx = ActionContext.getContext();
        try {
            teamDAO.UpdateTeam(team);
            ctx.put("message",  java.net.URLEncoder.encode("Team��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Team��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Team��Ϣ*/
    public String DeleteTeam() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            teamDAO.DeleteTeam(teamUserName);
            ctx.put("message",  java.net.URLEncoder.encode("Teamɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Teamɾ��ʧ��!"));
            return "error";
        }
    }

}
