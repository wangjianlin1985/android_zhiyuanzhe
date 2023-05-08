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
import com.chengxusheji.domain.SignUpSate;

@Service @Transactional
public class SignUpSateDAO {

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
    public void AddSignUpSate(SignUpSate signUpSate) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(signUpSate);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<SignUpSate> QuerySignUpSateInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From SignUpSate signUpSate where 1=1";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List signUpSateList = q.list();
    	return (ArrayList<SignUpSate>) signUpSateList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<SignUpSate> QuerySignUpSateInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From SignUpSate signUpSate where 1=1";
    	Query q = s.createQuery(hql);
    	List signUpSateList = q.list();
    	return (ArrayList<SignUpSate>) signUpSateList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<SignUpSate> QueryAllSignUpSateInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From SignUpSate";
        Query q = s.createQuery(hql);
        List signUpSateList = q.list();
        return (ArrayList<SignUpSate>) signUpSateList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From SignUpSate signUpSate where 1=1";
        Query q = s.createQuery(hql);
        List signUpSateList = q.list();
        recordNumber = signUpSateList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public SignUpSate GetSignUpSateByStateId(int stateId) {
        Session s = factory.getCurrentSession();
        SignUpSate signUpSate = (SignUpSate)s.get(SignUpSate.class, stateId);
        return signUpSate;
    }

    /*更新SignUpSate信息*/
    public void UpdateSignUpSate(SignUpSate signUpSate) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(signUpSate);
    }

    /*删除SignUpSate信息*/
    public void DeleteSignUpSate (int stateId) throws Exception {
        Session s = factory.getCurrentSession();
        Object signUpSate = s.load(SignUpSate.class, stateId);
        s.delete(signUpSate);
    }

}
