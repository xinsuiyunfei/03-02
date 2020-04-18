package com.xtgj.j2ee.chapter03.car.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.xtgj.j2ee.chapter02.db.DBUtil;

public class DBOperator {
	// 查找所有品牌列表
	public List findMakes() throws Exception {
		List list = new ArrayList();
		Connection cn = getConnection();
		Statement st = cn.createStatement();
		ResultSet rs = st.executeQuery("select distinct make from car");
		while (rs.next()) {
			list.add(rs.getString(1));
		}
		rs.close();
		st.close();
		cn.close();
		return list;
	}

	// 根据年份和品牌查找相应的车型列表
	public List findModels(int year, String make) throws Exception {
		List list = new ArrayList();
		Connection cn = getConnection();
		String sql = "select model from car where year=? and make=?";
		PreparedStatement pst = cn.prepareStatement(sql);
		pst.setInt(1, year);
		pst.setString(2, make);
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
			list.add(rs.getString(1));
		}
		rs.close();
		pst.close();
		cn.close();
		return list;
	}

	// 查找所有年份列表
	public List findYears() throws Exception {
		List list = new ArrayList();
		Connection cn = getConnection();
		Statement st = cn.createStatement();
		ResultSet rs = st.executeQuery("select distinct year from car");
		while (rs.next()) {
			list.add(new Integer(rs.getInt(1)));
		}
		rs.close();
		st.close();
		cn.close();
		return list;
	}

	public List findModels(String modelPrefix) throws Exception {
		List list = new ArrayList();
		Connection cn = getConnection();
		String sql = "select model from car where model like '" + modelPrefix
				+ "%' group by model";
		Statement st = cn.createStatement();
		ResultSet rs = st.executeQuery(sql);
		while (rs.next()) {
			list.add(rs.getString(1));
		}
		rs.close();
		st.close();
		cn.close();
		return list;
	}

	// 获取数据库连接
	private Connection getConnection() throws Exception {
		return DBUtil.getConn();
	}
}


