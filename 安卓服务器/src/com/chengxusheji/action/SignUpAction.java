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

    /*�������Ҫ��ѯ������: �Ŷӻ*/
    private Exercise exerciseObj;
    public void setExerciseObj(Exercise exerciseObj) {
        this.exerciseObj = exerciseObj;
    }
    public Exercise getExerciseObj() {
        return this.exerciseObj;
    }

    /*�������Ҫ��ѯ������: �����û�*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*�������Ҫ��ѯ������: ���״̬*/
    private SignUpSate signUpState;
    public void setSignUpState(SignUpSate signUpState) {
        this.signUpState = signUpState;
    }
    public SignUpSate getSignUpState() {
        return this.signUpState;
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

    private int signUpId;
    public void setSignUpId(int signUpId) {
        this.signUpId = signUpId;
    }
    public int getSignUpId() {
        return signUpId;
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
    @Resource ExerciseDAO exerciseDAO;
    @Resource UserInfoDAO userInfoDAO;
    @Resource SignUpSateDAO signUpSateDAO;
    @Resource SignUpDAO signUpDAO;

    /*��������SignUp����*/
    private SignUp signUp;
    public void setSignUp(SignUp signUp) {
        this.signUp = signUp;
    }
    public SignUp getSignUp() {
        return this.signUp;
    }

    /*��ת�����SignUp��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�Exercise��Ϣ*/
        List<Exercise> exerciseList = exerciseDAO.QueryAllExerciseInfo();
        ctx.put("exerciseList", exerciseList);
        /*��ѯ���е�UserInfo��Ϣ*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        /*��ѯ���е�SignUpSate��Ϣ*/
        List<SignUpSate> signUpSateList = signUpSateDAO.QueryAllSignUpSateInfo();
        ctx.put("signUpSateList", signUpSateList);
        return "add_view";
    }

    /*���SignUp��Ϣ*/
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
            ctx.put("message",  java.net.URLEncoder.encode("SignUp��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SignUp���ʧ��!"));
            return "error";
        }
    }

    /*��ѯSignUp��Ϣ*/
    public String QuerySignUp() {
        if(currentPage == 0) currentPage = 1;
        List<SignUp> signUpList = signUpDAO.QuerySignUpInfo(exerciseObj, userObj, signUpState, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        signUpDAO.CalculateTotalPageAndRecordNumber(exerciseObj, userObj, signUpState);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = signUpDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��̨������excel*/
    public String QuerySignUpOutputToExcel() { 
        List<SignUp> signUpList = signUpDAO.QuerySignUpInfo(exerciseObj,userObj,signUpState);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "SignUp��Ϣ��¼"; 
        String[] headers = { "�Ŷӻ","�����û�","����ʱ��","���״̬"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"SignUp.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯSignUp��Ϣ*/
    public String FrontQuerySignUp() {
        if(currentPage == 0) currentPage = 1;
        List<SignUp> signUpList = signUpDAO.QuerySignUpInfo(exerciseObj, userObj, signUpState, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        signUpDAO.CalculateTotalPageAndRecordNumber(exerciseObj, userObj, signUpState);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = signUpDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�SignUp��Ϣ*/
    public String ModifySignUpQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������signUpId��ȡSignUp����*/
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

    /*��ѯҪ�޸ĵ�SignUp��Ϣ*/
    public String FrontShowSignUpQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������signUpId��ȡSignUp����*/
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

    /*�����޸�SignUp��Ϣ*/
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
            ctx.put("message",  java.net.URLEncoder.encode("SignUp��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SignUp��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��SignUp��Ϣ*/
    public String DeleteSignUp() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            signUpDAO.DeleteSignUp(signUpId);
            ctx.put("message",  java.net.URLEncoder.encode("SignUpɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("SignUpɾ��ʧ��!"));
            return "error";
        }
    }

}
