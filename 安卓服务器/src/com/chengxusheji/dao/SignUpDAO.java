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
import com.chengxusheji.domain.Exercise;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.domain.SignUpSate;
import com.chengxusheji.domain.SignUp;

@Service @Transactional
public class SignUpDAO {

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
    public void AddSignUp(SignUp signUp) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(signUp);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<SignUp> QuerySignUpInfo(Exercise exerciseObj,UserInfo userObj,SignUpSate signUpState,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From SignUp signUp where 1=1";
    	if(null != exerciseObj && exerciseObj.getExerciseId()!=0) hql += " and signUp.exerciseObj.exerciseId=" + exerciseObj.getExerciseId();
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and signUp.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(null != signUpState && signUpState.getStateId()!=0) hql += " and signUp.signUpState.stateId=" + signUpState.getStateId();
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List signUpList = q.list();
    	return (ArrayList<SignUp>) signUpList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<SignUp> QuerySignUpInfo(Exercise exerciseObj,UserInfo userObj,SignUpSate signUpState) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From SignUp signUp where 1=1";
    	if(null != exerciseObj && exerciseObj.getExerciseId()!=0) hql += " and signUp.exerciseObj.exerciseId=" + exerciseObj.getExerciseId();
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and signUp.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(null != signUpState && signUpState.getStateId()!=0) hql += " and signUp.signUpState.stateId=" + signUpState.getStateId();
    	Query q = s.createQuery(hql);
    	List signUpList = q.list();
    	return (ArrayList<SignUp>) signUpList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<SignUp> QueryAllSignUpInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From SignUp";
        Query q = s.createQuery(hql);
        List signUpList = q.list();
        return (ArrayList<SignUp>) signUpList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(Exercise exerciseObj,UserInfo userObj,SignUpSate signUpState) {
        Session s = factory.getCurrentSession();
        String hql = "From SignUp signUp where 1=1";
        if(null != exerciseObj && exerciseObj.getExerciseId()!=0) hql += " and signUp.exerciseObj.exerciseId=" + exerciseObj.getExerciseId();
        if(null != userObj && !userObj.getUser_name().equals("")) hql += " and signUp.userObj.user_name='" + userObj.getUser_name() + "'";
        if(null != signUpState && signUpState.getStateId()!=0) hql += " and signUp.signUpState.stateId=" + signUpState.getStateId();
        Query q = s.createQuery(hql);
        List signUpList = q.list();
        recordNumber = signUpList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public SignUp GetSignUpBySignUpId(int signUpId) {
        Session s = factory.getCurrentSession();
        SignUp signUp = (SignUp)s.get(SignUp.class, signUpId);
        return signUp;
    }

    /*更新SignUp信息*/
    public void UpdateSignUp(SignUp signUp) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(signUp);
    }

    /*删除SignUp信息*/
    public void DeleteSignUp (int signUpId) throws Exception {
        Session s = factory.getCurrentSession();
        Object signUp = s.load(SignUp.class, signUpId);
        s.delete(signUp);
    }

}
