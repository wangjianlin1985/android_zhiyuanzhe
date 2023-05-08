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
import com.chengxusheji.dao.ExerciseDAO;
import com.chengxusheji.domain.Exercise;
import com.chengxusheji.dao.TeamDAO;
import com.chengxusheji.domain.Team;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class ExerciseAction extends BaseAction {

    /*�������Ҫ��ѯ������: �����*/
    private String exerciseName;
    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }
    public String getExerciseName() {
        return this.exerciseName;
    }

    /*�������Ҫ��ѯ������: �����*/
    private String exerciseDate;
    public void setExerciseDate(String exerciseDate) {
        this.exerciseDate = exerciseDate;
    }
    public String getExerciseDate() {
        return this.exerciseDate;
    }

    /*�������Ҫ��ѯ������: ��Ŷ�*/
    private Team teamObj;
    public void setTeamObj(Team teamObj) {
        this.teamObj = teamObj;
    }
    public Team getTeamObj() {
        return this.teamObj;
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

    private int exerciseId;
    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }
    public int getExerciseId() {
        return exerciseId;
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
    @Resource ExerciseDAO exerciseDAO;

    /*��������Exercise����*/
    private Exercise exercise;
    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }
    public Exercise getExercise() {
        return this.exercise;
    }

    /*��ת�����Exercise��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�Team��Ϣ*/
        List<Team> teamList = teamDAO.QueryAllTeamInfo();
        ctx.put("teamList", teamList);
        return "add_view";
    }

    /*���Exercise��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddExercise() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Team teamObj = teamDAO.GetTeamByTeamUserName(exercise.getTeamObj().getTeamUserName());
            exercise.setTeamObj(teamObj);
            exerciseDAO.AddExercise(exercise);
            ctx.put("message",  java.net.URLEncoder.encode("Exercise��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Exercise���ʧ��!"));
            return "error";
        }
    }

    /*��ѯExercise��Ϣ*/
    public String QueryExercise() {
        if(currentPage == 0) currentPage = 1;
        if(exerciseName == null) exerciseName = "";
        if(exerciseDate == null) exerciseDate = "";
        List<Exercise> exerciseList = exerciseDAO.QueryExerciseInfo(exerciseName, exerciseDate, teamObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        exerciseDAO.CalculateTotalPageAndRecordNumber(exerciseName, exerciseDate, teamObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = exerciseDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = exerciseDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("exerciseList",  exerciseList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("exerciseName", exerciseName);
        ctx.put("exerciseDate", exerciseDate);
        ctx.put("teamObj", teamObj);
        List<Team> teamList = teamDAO.QueryAllTeamInfo();
        ctx.put("teamList", teamList);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryExerciseOutputToExcel() { 
        if(exerciseName == null) exerciseName = "";
        if(exerciseDate == null) exerciseDate = "";
        List<Exercise> exerciseList = exerciseDAO.QueryExerciseInfo(exerciseName,exerciseDate,teamObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Exercise��Ϣ��¼"; 
        String[] headers = { "�����","�����","����ʱ��","��ص�","��������","��Ŷ�"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<exerciseList.size();i++) {
        	Exercise exercise = exerciseList.get(i); 
        	dataset.add(new String[]{exercise.getExerciseName(),new SimpleDateFormat("yyyy-MM-dd").format(exercise.getExerciseDate()),exercise.getServiceTime(),exercise.getAddress(),exercise.getPersonNum() + "",exercise.getTeamObj().getTeamName()
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
			response.setHeader("Content-disposition","attachment; filename="+"Exercise.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯExercise��Ϣ*/
    public String FrontQueryExercise() {
        if(currentPage == 0) currentPage = 1;
        if(exerciseName == null) exerciseName = "";
        if(exerciseDate == null) exerciseDate = "";
        List<Exercise> exerciseList = exerciseDAO.QueryExerciseInfo(exerciseName, exerciseDate, teamObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        exerciseDAO.CalculateTotalPageAndRecordNumber(exerciseName, exerciseDate, teamObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = exerciseDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = exerciseDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("exerciseList",  exerciseList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("exerciseName", exerciseName);
        ctx.put("exerciseDate", exerciseDate);
        ctx.put("teamObj", teamObj);
        List<Team> teamList = teamDAO.QueryAllTeamInfo();
        ctx.put("teamList", teamList);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�Exercise��Ϣ*/
    public String ModifyExerciseQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������exerciseId��ȡExercise����*/
        Exercise exercise = exerciseDAO.GetExerciseByExerciseId(exerciseId);

        List<Team> teamList = teamDAO.QueryAllTeamInfo();
        ctx.put("teamList", teamList);
        ctx.put("exercise",  exercise);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Exercise��Ϣ*/
    public String FrontShowExerciseQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������exerciseId��ȡExercise����*/
        Exercise exercise = exerciseDAO.GetExerciseByExerciseId(exerciseId);

        List<Team> teamList = teamDAO.QueryAllTeamInfo();
        ctx.put("teamList", teamList);
        ctx.put("exercise",  exercise);
        return "front_show_view";
    }

    /*�����޸�Exercise��Ϣ*/
    public String ModifyExercise() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Team teamObj = teamDAO.GetTeamByTeamUserName(exercise.getTeamObj().getTeamUserName());
            exercise.setTeamObj(teamObj);
            exerciseDAO.UpdateExercise(exercise);
            ctx.put("message",  java.net.URLEncoder.encode("Exercise��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Exercise��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Exercise��Ϣ*/
    public String DeleteExercise() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            exerciseDAO.DeleteExercise(exerciseId);
            ctx.put("message",  java.net.URLEncoder.encode("Exerciseɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Exerciseɾ��ʧ��!"));
            return "error";
        }
    }

}
