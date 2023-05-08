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
import com.chengxusheji.dao.SignUpSateDAO;
import com.chengxusheji.domain.SignUpSate;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class SignUpSateAction extends BaseAction {

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

    private int stateId;
    public void setStateId(int stateId) {
        this.stateId = stateId;
    }
    public int getStateId() {
        return stateId;
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
    @Resource SignUpSateDAO signUpSateDAO;

    /*��������SignUpSate����*/
    private SignUpSate signUpSate;
    public void setSignUpSate(SignUpSate signUpSate) {
        this.signUpSate = signUpSate;
    }
    public SignUpSate getSignUpSate() {
        return this.signUpSate;
    }

    /*��ת�����SignUpSate��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���SignUpSate��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddSignUpSate() {
        ActionContext ctx = ActionContext.getContext();
        try {
            signUpSateDAO.AddSignUpSate(signUpSate);
            ctx.put("message",  java.net.URLEncoder.encode("SignUpSate��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SignUpSate���ʧ��!"));
            return "error";
        }
    }

    /*��ѯSignUpSate��Ϣ*/
    public String QuerySignUpSate() {
        if(currentPage == 0) currentPage = 1;
        List<SignUpSate> signUpSateList = signUpSateDAO.QuerySignUpSateInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        signUpSateDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = signUpSateDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = signUpSateDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("signUpSateList",  signUpSateList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QuerySignUpSateOutputToExcel() { 
        List<SignUpSate> signUpSateList = signUpSateDAO.QuerySignUpSateInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "SignUpSate��Ϣ��¼"; 
        String[] headers = { "״̬id","״̬����"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<signUpSateList.size();i++) {
        	SignUpSate signUpSate = signUpSateList.get(i); 
        	dataset.add(new String[]{signUpSate.getStateId() + "",signUpSate.getStateName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"SignUpSate.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯSignUpSate��Ϣ*/
    public String FrontQuerySignUpSate() {
        if(currentPage == 0) currentPage = 1;
        List<SignUpSate> signUpSateList = signUpSateDAO.QuerySignUpSateInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        signUpSateDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = signUpSateDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = signUpSateDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("signUpSateList",  signUpSateList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�SignUpSate��Ϣ*/
    public String ModifySignUpSateQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������stateId��ȡSignUpSate����*/
        SignUpSate signUpSate = signUpSateDAO.GetSignUpSateByStateId(stateId);

        ctx.put("signUpSate",  signUpSate);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�SignUpSate��Ϣ*/
    public String FrontShowSignUpSateQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������stateId��ȡSignUpSate����*/
        SignUpSate signUpSate = signUpSateDAO.GetSignUpSateByStateId(stateId);

        ctx.put("signUpSate",  signUpSate);
        return "front_show_view";
    }

    /*�����޸�SignUpSate��Ϣ*/
    public String ModifySignUpSate() {
        ActionContext ctx = ActionContext.getContext();
        try {
            signUpSateDAO.UpdateSignUpSate(signUpSate);
            ctx.put("message",  java.net.URLEncoder.encode("SignUpSate��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SignUpSate��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��SignUpSate��Ϣ*/
    public String DeleteSignUpSate() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            signUpSateDAO.DeleteSignUpSate(stateId);
            ctx.put("message",  java.net.URLEncoder.encode("SignUpSateɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SignUpSateɾ��ʧ��!"));
            return "error";
        }
    }

}
