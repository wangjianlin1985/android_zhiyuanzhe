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

    /*界面层需要查询的属性: 用户名*/
    private String teamUserName;
    public void setTeamUserName(String teamUserName) {
        this.teamUserName = teamUserName;
    }
    public String getTeamUserName() {
        return this.teamUserName;
    }

    /*界面层需要查询的属性: 电子邮箱*/
    private String email;
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return this.email;
    }

    /*界面层需要查询的属性: 志愿团体名称*/
    private String teamName;
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
    public String getTeamName() {
        return this.teamName;
    }

    /*界面层需要查询的属性: 所属院校*/
    private String shoolName;
    public void setShoolName(String shoolName) {
        this.shoolName = shoolName;
    }
    public String getShoolName() {
        return this.shoolName;
    }

    /*界面层需要查询的属性: 联络团体*/
    private String contactGroup;
    public void setContactGroup(String contactGroup) {
        this.contactGroup = contactGroup;
    }
    public String getContactGroup() {
        return this.contactGroup;
    }

    /*界面层需要查询的属性: 主管单位*/
    private String mainUnit;
    public void setMainUnit(String mainUnit) {
        this.mainUnit = mainUnit;
    }
    public String getMainUnit() {
        return this.mainUnit;
    }

    /*界面层需要查询的属性: 区域*/
    private String area;
    public void setArea(String area) {
        this.area = area;
    }
    public String getArea() {
        return this.area;
    }

    /*界面层需要查询的属性: 联系人电话*/
    private String telephone;
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getTelephone() {
        return this.telephone;
    }

    /*界面层需要查询的属性: 负责人姓名*/
    private String chargeMan;
    public void setChargeMan(String chargeMan) {
        this.chargeMan = chargeMan;
    }
    public String getChargeMan() {
        return this.chargeMan;
    }

    /*当前第几页*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*一共多少页*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*当前查询的总记录数目*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*业务层对象*/
    @Resource TeamDAO teamDAO;

    /*待操作的Team对象*/
    private Team team;
    public void setTeam(Team team) {
        this.team = team;
    }
    public Team getTeam() {
        return this.team;
    }

    /*跳转到添加Team视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加Team信息*/
    @SuppressWarnings("deprecation")
    public String AddTeam() {
        ActionContext ctx = ActionContext.getContext();
        /*验证用户名是否已经存在*/
        String teamUserName = team.getTeamUserName();
        Team db_team = teamDAO.GetTeamByTeamUserName(teamUserName);
        if(null != db_team) {
            ctx.put("error",  java.net.URLEncoder.encode("该用户名已经存在!"));
            return "error";
        }
        try {
            teamDAO.AddTeam(team);
            ctx.put("message",  java.net.URLEncoder.encode("Team添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Team添加失败!"));
            return "error";
        }
    }

    /*查询Team信息*/
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
        /*计算总的页数和总的记录数*/
        teamDAO.CalculateTotalPageAndRecordNumber(teamUserName, email, teamName, shoolName, contactGroup, mainUnit, area, telephone, chargeMan);
        /*获取到总的页码数目*/
        totalPage = teamDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*后台导出到excel*/
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
        String title = "Team信息记录"; 
        String[] headers = { "用户名","电子邮箱","志愿团体名称","所属院校","联络团体","主管单位","区域","邮编","成立日期","志愿者人数","联系人电话","负责人姓名"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Team.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
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
    /*前台查询Team信息*/
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
        /*计算总的页数和总的记录数*/
        teamDAO.CalculateTotalPageAndRecordNumber(teamUserName, email, teamName, shoolName, contactGroup, mainUnit, area, telephone, chargeMan);
        /*获取到总的页码数目*/
        totalPage = teamDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*查询要修改的Team信息*/
    public String ModifyTeamQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键teamUserName获取Team对象*/
        Team team = teamDAO.GetTeamByTeamUserName(teamUserName);

        ctx.put("team",  team);
        return "modify_view";
    }

    /*查询要修改的Team信息*/
    public String FrontShowTeamQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键teamUserName获取Team对象*/
        Team team = teamDAO.GetTeamByTeamUserName(teamUserName);

        ctx.put("team",  team);
        return "front_show_view";
    }

    /*更新修改Team信息*/
    public String ModifyTeam() {
        ActionContext ctx = ActionContext.getContext();
        try {
            teamDAO.UpdateTeam(team);
            ctx.put("message",  java.net.URLEncoder.encode("Team信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Team信息更新失败!"));
            return "error";
       }
   }

    /*删除Team信息*/
    public String DeleteTeam() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            teamDAO.DeleteTeam(teamUserName);
            ctx.put("message",  java.net.URLEncoder.encode("Team删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Team删除失败!"));
            return "error";
        }
    }

}
