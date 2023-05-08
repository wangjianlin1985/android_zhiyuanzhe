package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.News;
import com.mobileserver.util.DB;

public class NewsDAO {

	public List<News> QueryNews(String title,Timestamp newsDate) {
		List<News> newsList = new ArrayList<News>();
		DB db = new DB();
		String sql = "select * from News where 1=1";
		if (!title.equals(""))
			sql += " and title like '%" + title + "%'";
		if(newsDate!=null)
			sql += " and newsDate='" + newsDate + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				News news = new News();
				news.setNewsId(rs.getInt("newsId"));
				news.setTitle(rs.getString("title"));
				news.setNewsDate(rs.getTimestamp("newsDate"));
				news.setComeFrom(rs.getString("comeFrom"));
				news.setNewsContent(rs.getString("newsContent"));
				newsList.add(news);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return newsList;
	}
	/* �������Ŷ��󣬽��л���ŵ����ҵ�� */
	public String AddNews(News news) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в����»���� */
			String sqlString = "insert into News(title,newsDate,comeFrom,newsContent) values (";
			sqlString += "'" + news.getTitle() + "',";
			sqlString += "'" + news.getNewsDate() + "',";
			sqlString += "'" + news.getComeFrom() + "',";
			sqlString += "'" + news.getNewsContent() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "�������ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ������� */
	public String DeleteNews(int newsId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from News where newsId=" + newsId;
			db.executeUpdate(sqlString);
			result = "�����ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�����ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ��������id��ȡ������� */
	public News GetNews(int newsId) {
		News news = null;
		DB db = new DB();
		String sql = "select * from News where newsId=" + newsId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				news = new News();
				news.setNewsId(rs.getInt("newsId"));
				news.setTitle(rs.getString("title"));
				news.setNewsDate(rs.getTimestamp("newsDate"));
				news.setComeFrom(rs.getString("comeFrom"));
				news.setNewsContent(rs.getString("newsContent"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return news;
	}
	/* ���»���� */
	public String UpdateNews(News news) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update News set ";
			sql += "title='" + news.getTitle() + "',";
			sql += "newsDate='" + news.getNewsDate() + "',";
			sql += "comeFrom='" + news.getComeFrom() + "',";
			sql += "newsContent='" + news.getNewsContent() + "'";
			sql += " where newsId=" + news.getNewsId();
			db.executeUpdate(sql);
			result = "����Ÿ��³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "����Ÿ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
