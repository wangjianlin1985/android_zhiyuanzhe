package com.chengxusheji.dao;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.chengxusheji.domain.Team;
import com.chengxusheji.domain.Exercise;

@Service @Transactional
public class ExerciseDAO {

	@Resource SessionFactory factory;
    /*每页显示记录数目*/
    private final int PAGE_SIZE = 10;

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加图书信息*/
    public void AddExercise(Exercise exercise) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(exercise);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Exercise> QueryExerciseInfo(String exerciseName,String exerciseDate,Team teamObj,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Exercise exercise where 1=1";
    	if(!exerciseName.equals("")) hql = hql + " and exercise.exerciseName like '%" + exerciseName + "%'";
    	if(!exerciseDate.equals("")) hql = hql + " and exercise.exerciseDate like '%" + exerciseDate + "%'";
    	if(null != teamObj && !teamObj.getTeamUserName().equals("")) hql += " and exercise.teamObj.teamUserName='" + teamObj.getTeamUserName() + "'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List exerciseList = q.list();
    	return (ArrayList<Exercise>) exerciseList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Exercise> QueryExerciseInfo(String exerciseName,String exerciseDate,Team teamObj) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Exercise exercise where 1=1";
    	if(!exerciseName.equals("")) hql = hql + " and exercise.exerciseName like '%" + exerciseName + "%'";
    	if(!exerciseDate.equals("")) hql = hql + " and exercise.exerciseDate like '%" + exerciseDate + "%'";
    	if(null != teamObj && !teamObj.getTeamUserName().equals("")) hql += " and exercise.teamObj.teamUserName='" + teamObj.getTeamUserName() + "'";
    	Query q = s.createQuery(hql);
    	List exerciseList = q.list();
    	return (ArrayList<Exercise>) exerciseList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Exercise> QueryAllExerciseInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Exercise";
        Query q = s.createQuery(hql);
        List exerciseList = q.list();
        return (ArrayList<Exercise>) exerciseList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String exerciseName,String exerciseDate,Team teamObj) {
        Session s = factory.getCurrentSession();
        String hql = "From Exercise exercise where 1=1";
        if(!exerciseName.equals("")) hql = hql + " and exercise.exerciseName like '%" + exerciseName + "%'";
        if(!exerciseDate.equals("")) hql = hql + " and exercise.exerciseDate like '%" + exerciseDate + "%'";
        if(null != teamObj && !teamObj.getTeamUserName().equals("")) hql += " and exercise.teamObj.teamUserName='" + teamObj.getTeamUserName() + "'";
        Query q = s.createQuery(hql);
        List exerciseList = q.list();
        recordNumber = exerciseList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Exercise GetExerciseByExerciseId(int exerciseId) {
        Session s = factory.getCurrentSession();
        Exercise exercise = (Exercise)s.get(Exercise.class, exerciseId);
        return exercise;
    }

    /*更新Exercise信息*/
    public void UpdateExercise(Exercise exercise) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(exercise);
    }

    /*删除Exercise信息*/
    public void DeleteExercise (int exerciseId) throws Exception {
        Session s = factory.getCurrentSession();
        Object exercise = s.load(Exercise.class, exerciseId);
        s.delete(exercise);
    }

}
