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

@Service @Transactional
public class TeamDAO {

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
    public void AddTeam(Team team) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(team);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Team> QueryTeamInfo(String teamUserName,String email,String teamName,String shoolName,String contactGroup,String mainUnit,String area,String telephone,String chargeMan,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Team team where 1=1";
    	if(!teamUserName.equals("")) hql = hql + " and team.teamUserName like '%" + teamUserName + "%'";
    	if(!email.equals("")) hql = hql + " and team.email like '%" + email + "%'";
    	if(!teamName.equals("")) hql = hql + " and team.teamName like '%" + teamName + "%'";
    	if(!shoolName.equals("")) hql = hql + " and team.shoolName like '%" + shoolName + "%'";
    	if(!contactGroup.equals("")) hql = hql + " and team.contactGroup like '%" + contactGroup + "%'";
    	if(!mainUnit.equals("")) hql = hql + " and team.mainUnit like '%" + mainUnit + "%'";
    	if(!area.equals("")) hql = hql + " and team.area like '%" + area + "%'";
    	if(!telephone.equals("")) hql = hql + " and team.telephone like '%" + telephone + "%'";
    	if(!chargeMan.equals("")) hql = hql + " and team.chargeMan like '%" + chargeMan + "%'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List teamList = q.list();
    	return (ArrayList<Team>) teamList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Team> QueryTeamInfo(String teamUserName,String email,String teamName,String shoolName,String contactGroup,String mainUnit,String area,String telephone,String chargeMan) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Team team where 1=1";
    	if(!teamUserName.equals("")) hql = hql + " and team.teamUserName like '%" + teamUserName + "%'";
    	if(!email.equals("")) hql = hql + " and team.email like '%" + email + "%'";
    	if(!teamName.equals("")) hql = hql + " and team.teamName like '%" + teamName + "%'";
    	if(!shoolName.equals("")) hql = hql + " and team.shoolName like '%" + shoolName + "%'";
    	if(!contactGroup.equals("")) hql = hql + " and team.contactGroup like '%" + contactGroup + "%'";
    	if(!mainUnit.equals("")) hql = hql + " and team.mainUnit like '%" + mainUnit + "%'";
    	if(!area.equals("")) hql = hql + " and team.area like '%" + area + "%'";
    	if(!telephone.equals("")) hql = hql + " and team.telephone like '%" + telephone + "%'";
    	if(!chargeMan.equals("")) hql = hql + " and team.chargeMan like '%" + chargeMan + "%'";
    	Query q = s.createQuery(hql);
    	List teamList = q.list();
    	return (ArrayList<Team>) teamList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Team> QueryAllTeamInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Team";
        Query q = s.createQuery(hql);
        List teamList = q.list();
        return (ArrayList<Team>) teamList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(String teamUserName,String email,String teamName,String shoolName,String contactGroup,String mainUnit,String area,String telephone,String chargeMan) {
        Session s = factory.getCurrentSession();
        String hql = "From Team team where 1=1";
        if(!teamUserName.equals("")) hql = hql + " and team.teamUserName like '%" + teamUserName + "%'";
        if(!email.equals("")) hql = hql + " and team.email like '%" + email + "%'";
        if(!teamName.equals("")) hql = hql + " and team.teamName like '%" + teamName + "%'";
        if(!shoolName.equals("")) hql = hql + " and team.shoolName like '%" + shoolName + "%'";
        if(!contactGroup.equals("")) hql = hql + " and team.contactGroup like '%" + contactGroup + "%'";
        if(!mainUnit.equals("")) hql = hql + " and team.mainUnit like '%" + mainUnit + "%'";
        if(!area.equals("")) hql = hql + " and team.area like '%" + area + "%'";
        if(!telephone.equals("")) hql = hql + " and team.telephone like '%" + telephone + "%'";
        if(!chargeMan.equals("")) hql = hql + " and team.chargeMan like '%" + chargeMan + "%'";
        Query q = s.createQuery(hql);
        List teamList = q.list();
        recordNumber = teamList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Team GetTeamByTeamUserName(String teamUserName) {
        Session s = factory.getCurrentSession();
        Team team = (Team)s.get(Team.class, teamUserName);
        return team;
    }

    /*更新Team信息*/
    public void UpdateTeam(Team team) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(team);
    }

    /*删除Team信息*/
    public void DeleteTeam (String teamUserName) throws Exception {
        Session s = factory.getCurrentSession();
        Object team = s.load(Team.class, teamUserName);
        s.delete(team);
    }

}
