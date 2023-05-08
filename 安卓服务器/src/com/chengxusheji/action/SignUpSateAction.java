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

    private int stateId;
    public void setStateId(int stateId) {
        this.stateId = stateId;
    }
    public int getStateId() {
        return stateId;
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
    @Resource SignUpSateDAO signUpSateDAO;

    /*待操作的SignUpSate对象*/
    private SignUpSate signUpSate;
    public void setSignUpSate(SignUpSate signUpSate) {
        this.signUpSate = signUpSate;
    }
    public SignUpSate getSignUpSate() {
        return this.signUpSate;
    }

    /*跳转到添加SignUpSate视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加SignUpSate信息*/
    @SuppressWarnings("deprecation")
    public String AddSignUpSate() {
        ActionContext ctx = ActionContext.getContext();
        try {
            signUpSateDAO.AddSignUpSate(signUpSate);
            ctx.put("message",  java.net.URLEncoder.encode("SignUpSate添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SignUpSate添加失败!"));
            return "error";
        }
    }

    /*查询SignUpSate信息*/
    public String QuerySignUpSate() {
        if(currentPage == 0) currentPage = 1;
        List<SignUpSate> signUpSateList = signUpSateDAO.QuerySignUpSateInfo(currentPage);
        /*计算总的页数和总的记录数*/
        signUpSateDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = signUpSateDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = signUpSateDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("signUpSateList",  signUpSateList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QuerySignUpSateOutputToExcel() { 
        List<SignUpSate> signUpSateList = signUpSateDAO.QuerySignUpSateInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "SignUpSate信息记录"; 
        String[] headers = { "状态id","状态名称"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"SignUpSate.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询SignUpSate信息*/
    public String FrontQuerySignUpSate() {
        if(currentPage == 0) currentPage = 1;
        List<SignUpSate> signUpSateList = signUpSateDAO.QuerySignUpSateInfo(currentPage);
        /*计算总的页数和总的记录数*/
        signUpSateDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = signUpSateDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = signUpSateDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("signUpSateList",  signUpSateList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*查询要修改的SignUpSate信息*/
    public String ModifySignUpSateQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键stateId获取SignUpSate对象*/
        SignUpSate signUpSate = signUpSateDAO.GetSignUpSateByStateId(stateId);

        ctx.put("signUpSate",  signUpSate);
        return "modify_view";
    }

    /*查询要修改的SignUpSate信息*/
    public String FrontShowSignUpSateQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键stateId获取SignUpSate对象*/
        SignUpSate signUpSate = signUpSateDAO.GetSignUpSateByStateId(stateId);

        ctx.put("signUpSate",  signUpSate);
        return "front_show_view";
    }

    /*更新修改SignUpSate信息*/
    public String ModifySignUpSate() {
        ActionContext ctx = ActionContext.getContext();
        try {
            signUpSateDAO.UpdateSignUpSate(signUpSate);
            ctx.put("message",  java.net.URLEncoder.encode("SignUpSate信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SignUpSate信息更新失败!"));
            return "error";
       }
   }

    /*删除SignUpSate信息*/
    public String DeleteSignUpSate() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            signUpSateDAO.DeleteSignUpSate(stateId);
            ctx.put("message",  java.net.URLEncoder.encode("SignUpSate删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SignUpSate删除失败!"));
            return "error";
        }
    }

}
