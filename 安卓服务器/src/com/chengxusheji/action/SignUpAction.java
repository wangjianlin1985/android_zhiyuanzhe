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
import com.chengxusheji.dao.SignUpDAO;
import com.chengxusheji.domain.SignUp;
import com.chengxusheji.dao.ExerciseDAO;
import com.chengxusheji.domain.Exercise;
import com.chengxusheji.dao.UserInfoDAO;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.dao.SignUpSateDAO;
import com.chengxusheji.domain.SignUpSate;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class SignUpAction extends BaseAction {

    /*界面层需要查询的属性: 团队活动*/
    private Exercise exerciseObj;
    public void setExerciseObj(Exercise exerciseObj) {
        this.exerciseObj = exerciseObj;
    }
    public Exercise getExerciseObj() {
        return this.exerciseObj;
    }

    /*界面层需要查询的属性: 报名用户*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*界面层需要查询的属性: 审核状态*/
    private SignUpSate signUpState;
    public void setSignUpState(SignUpSate signUpState) {
        this.signUpState = signUpState;
    }
    public SignUpSate getSignUpState() {
        return this.signUpState;
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

    private int signUpId;
    public void setSignUpId(int signUpId) {
        this.signUpId = signUpId;
    }
    public int getSignUpId() {
        return signUpId;
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
    @Resource ExerciseDAO exerciseDAO;
    @Resource UserInfoDAO userInfoDAO;
    @Resource SignUpSateDAO signUpSateDAO;
    @Resource SignUpDAO signUpDAO;

    /*待操作的SignUp对象*/
    private SignUp signUp;
    public void setSignUp(SignUp signUp) {
        this.signUp = signUp;
    }
    public SignUp getSignUp() {
        return this.signUp;
    }

    /*跳转到添加SignUp视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的Exercise信息*/
        List<Exercise> exerciseList = exerciseDAO.QueryAllExerciseInfo();
        ctx.put("exerciseList", exerciseList);
        /*查询所有的UserInfo信息*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        /*查询所有的SignUpSate信息*/
        List<SignUpSate> signUpSateList = signUpSateDAO.QueryAllSignUpSateInfo();
        ctx.put("signUpSateList", signUpSateList);
        return "add_view";
    }

    /*添加SignUp信息*/
    @SuppressWarnings("deprecation")
    public String AddSignUp() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Exercise exerciseObj = exerciseDAO.GetExerciseByExerciseId(signUp.getExerciseObj().getExerciseId());
            signUp.setExerciseObj(exerciseObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(signUp.getUserObj().getUser_name());
            signUp.setUserObj(userObj);
            SignUpSate signUpState = signUpSateDAO.GetSignUpSateByStateId(signUp.getSignUpState().getStateId());
            signUp.setSignUpState(signUpState);
            signUpDAO.AddSignUp(signUp);
            ctx.put("message",  java.net.URLEncoder.encode("SignUp添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SignUp添加失败!"));
            return "error";
        }
    }

    /*查询SignUp信息*/
    public String QuerySignUp() {
        if(currentPage == 0) currentPage = 1;
        List<SignUp> signUpList = signUpDAO.QuerySignUpInfo(exerciseObj, userObj, signUpState, currentPage);
        /*计算总的页数和总的记录数*/
        signUpDAO.CalculateTotalPageAndRecordNumber(exerciseObj, userObj, signUpState);
        /*获取到总的页码数目*/
        totalPage = signUpDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = signUpDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("signUpList",  signUpList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("exerciseObj", exerciseObj);
        List<Exercise> exerciseList = exerciseDAO.QueryAllExerciseInfo();
        ctx.put("exerciseList", exerciseList);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("signUpState", signUpState);
        List<SignUpSate> signUpSateList = signUpSateDAO.QueryAllSignUpSateInfo();
        ctx.put("signUpSateList", signUpSateList);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QuerySignUpOutputToExcel() { 
        List<SignUp> signUpList = signUpDAO.QuerySignUpInfo(exerciseObj,userObj,signUpState);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "SignUp信息记录"; 
        String[] headers = { "团队活动","报名用户","报名时间","审核状态"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<signUpList.size();i++) {
        	SignUp signUp = signUpList.get(i); 
        	dataset.add(new String[]{signUp.getExerciseObj().getExerciseName(),
signUp.getUserObj().getName(),
signUp.getSignUpTime(),signUp.getSignUpState().getStateName()
});
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
			response.setHeader("Content-disposition","attachment; filename="+"SignUp.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询SignUp信息*/
    public String FrontQuerySignUp() {
        if(currentPage == 0) currentPage = 1;
        List<SignUp> signUpList = signUpDAO.QuerySignUpInfo(exerciseObj, userObj, signUpState, currentPage);
        /*计算总的页数和总的记录数*/
        signUpDAO.CalculateTotalPageAndRecordNumber(exerciseObj, userObj, signUpState);
        /*获取到总的页码数目*/
        totalPage = signUpDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = signUpDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("signUpList",  signUpList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("exerciseObj", exerciseObj);
        List<Exercise> exerciseList = exerciseDAO.QueryAllExerciseInfo();
        ctx.put("exerciseList", exerciseList);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("signUpState", signUpState);
        List<SignUpSate> signUpSateList = signUpSateDAO.QueryAllSignUpSateInfo();
        ctx.put("signUpSateList", signUpSateList);
        return "front_query_view";
    }

    /*查询要修改的SignUp信息*/
    public String ModifySignUpQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键signUpId获取SignUp对象*/
        SignUp signUp = signUpDAO.GetSignUpBySignUpId(signUpId);

        List<Exercise> exerciseList = exerciseDAO.QueryAllExerciseInfo();
        ctx.put("exerciseList", exerciseList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        List<SignUpSate> signUpSateList = signUpSateDAO.QueryAllSignUpSateInfo();
        ctx.put("signUpSateList", signUpSateList);
        ctx.put("signUp",  signUp);
        return "modify_view";
    }

    /*查询要修改的SignUp信息*/
    public String FrontShowSignUpQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键signUpId获取SignUp对象*/
        SignUp signUp = signUpDAO.GetSignUpBySignUpId(signUpId);

        List<Exercise> exerciseList = exerciseDAO.QueryAllExerciseInfo();
        ctx.put("exerciseList", exerciseList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        List<SignUpSate> signUpSateList = signUpSateDAO.QueryAllSignUpSateInfo();
        ctx.put("signUpSateList", signUpSateList);
        ctx.put("signUp",  signUp);
        return "front_show_view";
    }

    /*更新修改SignUp信息*/
    public String ModifySignUp() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Exercise exerciseObj = exerciseDAO.GetExerciseByExerciseId(signUp.getExerciseObj().getExerciseId());
            signUp.setExerciseObj(exerciseObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(signUp.getUserObj().getUser_name());
            signUp.setUserObj(userObj);
            SignUpSate signUpState = signUpSateDAO.GetSignUpSateByStateId(signUp.getSignUpState().getStateId());
            signUp.setSignUpState(signUpState);
            signUpDAO.UpdateSignUp(signUp);
            ctx.put("message",  java.net.URLEncoder.encode("SignUp信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SignUp信息更新失败!"));
            return "error";
       }
   }

    /*删除SignUp信息*/
    public String DeleteSignUp() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            signUpDAO.DeleteSignUp(signUpId);
            ctx.put("message",  java.net.URLEncoder.encode("SignUp删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SignUp删除失败!"));
            return "error";
        }
    }

}
