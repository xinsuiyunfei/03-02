package com.xtgj.j2ee.chapter03.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xtgj.j2ee.chapter03.car.beans.DBOperator;

public class SearchModel extends HttpServlet {
	public void doGet(HttpServletRequest request,HttpServletResponse response)
			throws ServletException,IOException{
		//获取年份和品牌
		int year = Integer.parseInt(request.getParameter("year"));
		String make = request.getParameter("make");
		//根据年份和品牌查询车型
		DBOperator db = new DBOperator();
		List list = null;
		try{
			list = db.findModels(year, make);
		}
		catch(Exception e){
			throw new ServletException("数据库操作错误!");
		}
		//将车型打包为XML格式的数据
		StringBuffer xml = new StringBuffer("<models>");
		for(Iterator it = list.iterator();it.hasNext();){
			String model = (String)it.next();
			xml.append("<model>").append(model).append("</model>");
		}
		xml.append("</models>");
		//设置响应格式的字符集
		response.setContentType("text/xml;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		//发送响应数据
		out.print(xml.toString());
		out.close();
	}
    public void doPost(HttpServletRequest request
        ,HttpServletResponse response)
              throws ServletException,IOException{
		doGet(request,response);
	}
}



