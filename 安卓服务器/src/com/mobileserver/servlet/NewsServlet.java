package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.NewsDAO;
import com.mobileserver.domain.News;

import org.json.JSONStringer;

public class NewsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*��������ҵ������*/
	private NewsDAO newsDAO = new NewsDAO();

	/*Ĭ�Ϲ��캯��*/
	public NewsServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*��ȡaction����������action��ִֵ�в�ͬ��ҵ����*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*��ȡ��ѯ����ŵĲ�����Ϣ*/
			String title = request.getParameter("title");
			title = title == null ? "" : new String(request.getParameter(
					"title").getBytes("iso-8859-1"), "UTF-8");
			Timestamp newsDate = null;
			if (request.getParameter("newsDate") != null)
				newsDate = Timestamp.valueOf(request.getParameter("newsDate"));

			/*����ҵ���߼���ִ�л���Ų�ѯ*/
			List<News> newsList = newsDAO.QueryNews(title,newsDate);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Newss>").append("\r\n");
			for (int i = 0; i < newsList.size(); i++) {
				sb.append("	<News>").append("\r\n")
				.append("		<newsId>")
				.append(newsList.get(i).getNewsId())
				.append("</newsId>").append("\r\n")
				.append("		<title>")
				.append(newsList.get(i).getTitle())
				.append("</title>").append("\r\n")
				.append("		<newsDate>")
				.append(newsList.get(i).getNewsDate())
				.append("</newsDate>").append("\r\n")
				.append("		<comeFrom>")
				.append(newsList.get(i).getComeFrom())
				.append("</comeFrom>").append("\r\n")
				.append("		<newsContent>")
				.append(newsList.get(i).getNewsContent())
				.append("</newsContent>").append("\r\n")
				.append("	</News>").append("\r\n");
			}
			sb.append("</Newss>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(News news: newsList) {
				  stringer.object();
			  stringer.key("newsId").value(news.getNewsId());
			  stringer.key("title").value(news.getTitle());
			  stringer.key("newsDate").value(news.getNewsDate());
			  stringer.key("comeFrom").value(news.getComeFrom());
			  stringer.key("newsContent").value(news.getNewsContent());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��ӻ���ţ���ȡ����Ų������������浽�½��Ļ���Ŷ��� */ 
			News news = new News();
			int newsId = Integer.parseInt(request.getParameter("newsId"));
			news.setNewsId(newsId);
			String title = new String(request.getParameter("title").getBytes("iso-8859-1"), "UTF-8");
			news.setTitle(title);
			Timestamp newsDate = Timestamp.valueOf(request.getParameter("newsDate"));
			news.setNewsDate(newsDate);
			String comeFrom = new String(request.getParameter("comeFrom").getBytes("iso-8859-1"), "UTF-8");
			news.setComeFrom(comeFrom);
			String newsContent = new String(request.getParameter("newsContent").getBytes("iso-8859-1"), "UTF-8");
			news.setNewsContent(newsContent);

			/* ����ҵ���ִ����Ӳ��� */
			String result = newsDAO.AddNews(news);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ������ţ���ȡ����ŵ�����id*/
			int newsId = Integer.parseInt(request.getParameter("newsId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = newsDAO.DeleteNews(newsId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���»����֮ǰ�ȸ���newsId��ѯĳ�������*/
			int newsId = Integer.parseInt(request.getParameter("newsId"));
			News news = newsDAO.GetNews(newsId);

			// �ͻ��˲�ѯ�Ļ���Ŷ��󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("newsId").value(news.getNewsId());
			  stringer.key("title").value(news.getTitle());
			  stringer.key("newsDate").value(news.getNewsDate());
			  stringer.key("comeFrom").value(news.getComeFrom());
			  stringer.key("newsContent").value(news.getNewsContent());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���»���ţ���ȡ����Ų������������浽�½��Ļ���Ŷ��� */ 
			News news = new News();
			int newsId = Integer.parseInt(request.getParameter("newsId"));
			news.setNewsId(newsId);
			String title = new String(request.getParameter("title").getBytes("iso-8859-1"), "UTF-8");
			news.setTitle(title);
			Timestamp newsDate = Timestamp.valueOf(request.getParameter("newsDate"));
			news.setNewsDate(newsDate);
			String comeFrom = new String(request.getParameter("comeFrom").getBytes("iso-8859-1"), "UTF-8");
			news.setComeFrom(comeFrom);
			String newsContent = new String(request.getParameter("newsContent").getBytes("iso-8859-1"), "UTF-8");
			news.setNewsContent(newsContent);

			/* ����ҵ���ִ�и��²��� */
			String result = newsDAO.UpdateNews(news);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
