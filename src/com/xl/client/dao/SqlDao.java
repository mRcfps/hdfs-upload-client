package com.xl.client.dao;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.xl.client.bean.Caseinfo;
import com.xl.client.bean.Evidence;

public class SqlDao {

	/**
	 * wyw 数据入库
	 * 
	 * @param t
	 *            传入的类
	 */
	public <T> void replaceBeanToMysql(T t) {
		DBHelper db1 = null;
		ResultSet ret = null;
		String sql = null;
		String collectionName = t.getClass().getSimpleName();
		// System.out.println(collectionName);
		try {
			Field[] fields = t.getClass().getDeclaredFields();
			String sqlkey = "REPLACE INTO " + collectionName + " (";// SQL语句
			String sqlValue = " VALUES (";
			for (Field field : fields) {
				// System.out.println(field);
				String s = field.getName();
				// System.out.println(s);
				if (s.equals("id")) {
					continue;
				}
				String methodName = "get" + s.substring(0, 1).toUpperCase() + s.substring(1);
				// System.out.println("测试方法名******"+methodName);
				Method m = t.getClass().getDeclaredMethod(methodName);
				if (m.invoke(t) == null||m.invoke(t).equals("")) {
					continue;
				}
				sqlkey = sqlkey + s + ",";
				
				String res=m.invoke(t).toString();
				if(res.contains("'")){
					res.replace("'", "");
				}
				
				sqlValue = sqlValue + "'" + res + "',";
			}
			sql = sqlkey.substring(0, sqlkey.length() - 1) + ")" + sqlValue.substring(0, sqlValue.length() - 1) + ")";
			db1 = new DBHelper(sql);
			if (collectionName.equals("ChatRoom") || collectionName.equals("GroupMember")|| collectionName.equals("FriendZone")|| collectionName.equals("Messages")) {
				db1.pst.executeQuery("SET NAMES utf8mb4;");
			}
//			System.out.print("\n");
//			System.out.print(sql);
			db1.pst.executeUpdate(sql);
			
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ret != null) {
					ret.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			db1.close();
		}

	}
	
	
	
	/**
	 * wyw 数据入库
	 * 
	 * @param t
	 *            传入的类
	 */
	public <T> Integer setBeanToMysql(T t) {
		Integer genKey = null;
		DBHelper db1 = null;
		ResultSet ret = null;
		String sql = null;
		String collectionName = t.getClass().getSimpleName();
		try {
			Field[] fields = t.getClass().getDeclaredFields();
			String sqlkey = "INSERT INTO " + collectionName + " (";// SQL语句
			String sqlValue = " VALUES (";
			for (Field field : fields) {
				String s = field.getName();
				if (s.equals("id")) {
					continue;
				}
				String methodName = "get" + s.substring(0, 1).toUpperCase() + s.substring(1);
				Method m = t.getClass().getDeclaredMethod(methodName);
				if (m.invoke(t) == null||m.invoke(t).equals("")) {
					continue;
				}
				sqlkey = sqlkey + s + ",";
				
				String res=m.invoke(t).toString();
				if(res.contains("'")){
					res.replace("'", "");
				}
				
				sqlValue = sqlValue + "'" + res + "',";
			}
			sql = sqlkey.substring(0, sqlkey.length() - 1) + ")" + sqlValue.substring(0, sqlValue.length() - 1) + ")";
			db1 = new DBHelper(sql);
//			System.out.print(sql);
			db1.pst.executeUpdate(sql, PreparedStatement.RETURN_GENERATED_KEYS );
			ret = db1.pst.getGeneratedKeys();
			if (ret.next()) {
				genKey = ret.getInt(1);
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ret != null) {
					ret.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			db1.close();
		}

		return genKey;
	}
	/**
	 * 
	 * @param t
	 * @param containInvalidChar 是否包含特殊字符
	 */
	public <T> void setBeanToMysql(T t,boolean containInvalidChar) {
		DBHelper db1 = null;
		ResultSet ret = null;
		String sql = null;
		String collectionName = t.getClass().getSimpleName();
		try {
			Field[] fields = t.getClass().getDeclaredFields();
			String sqlkey = "INSERT INTO " + collectionName + " (";// SQL语句
			String sqlValue = " VALUES (";
			for (Field field : fields) {
				String s = field.getName();
				if (s.equals("id")) {
					continue;
				}
				String methodName = "get" + s.substring(0, 1).toUpperCase() + s.substring(1);
				Method m = t.getClass().getDeclaredMethod(methodName);
				if (m.invoke(t) == null||m.invoke(t).equals("")) {
					continue;
				}
				sqlkey = sqlkey + s + ",";
				String res=m.invoke(t).toString();
				if(res.contains("'")){
					res.replace("'", "");
				}
				sqlValue = sqlValue + "'" + res + "',";
			}
			sql = sqlkey.substring(0, sqlkey.length() - 1) + ")" + sqlValue.substring(0, sqlValue.length() - 1) + ")";
//			System.out.println(sql);
			db1 = new DBHelper(sql);
			if (containInvalidChar) {
				db1.pst.executeQuery("SET NAMES utf8mb4;");
			}
			db1.pst.executeUpdate(sql);

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ret != null) {
					ret.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			db1.close();
		}

	}

	/**
	 * wyw 数据修改
	 * 
	 * @param t
	 *            传入的类
	 */
	public <T> void updateToMysql(T t) {
		DBHelper db1 = null;
		ResultSet ret = null;
		String sql = null;
		String collectionName = t.getClass().getSimpleName();
		int id = -1;
		try {
			Field[] fields = t.getClass().getDeclaredFields();
			sql = "update " + collectionName + " set";// SQL语句
			String sqlValue = " ";
			for (Field field : fields) {
				String s = field.getName();

				String methodName = "get" + s.substring(0, 1).toUpperCase() + s.substring(1);
				Method m = t.getClass().getDeclaredMethod(methodName);
				if (s.equals("id")) {
					id = (Integer) m.invoke(t);
					continue;
				}
				if (m.invoke(t) == null||m.invoke(t).equals("")) {
					continue;
				}
				if(field.getGenericType().toString().equals("int")&&(Integer) m.invoke(t) ==-1){
					continue;
				}
				String res=m.invoke(t).toString();
				
				res=res.replace("'", "");
				
				sqlValue = sqlValue + s + "='" + res + "',";
			}
			sql = sql + sqlValue.substring(0, sqlValue.length() - 1) + " where id=" + id;
			db1 = new DBHelper(sql);// 创建DBHelper对象
			if (collectionName.equals("ChatRoom") || collectionName.equals("GroupMember")|| collectionName.equals("User")||collectionName.equals("FriendZone")|| collectionName.equals("Messages")) {
				db1.pst.executeQuery("SET NAMES utf8mb4;");
			}

//			System.out.println(sql);
			db1.pst.executeUpdate(sql);// 执行语句，得到结果集

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ret != null) {
					ret.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			db1.close();
		}

	}
	
	/**
	 * 数据查询去重
	 * 
	 * @param t
	 *            查询条件类
	 * @return 查询结果list
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getDistinctListfromMysql(T t,String sql1) {
		DBHelper db1 = null;
		ResultSet ret = null;
		String sql = null;
		String collectionName = t.getClass().getSimpleName();
		List<T> tList = new ArrayList<T>();
		sql = "SELECT * FROM " + collectionName;
		String value = "";
		boolean dateflag = false;
		try {
			Field[] fields = t.getClass().getDeclaredFields();

			for (Field field : fields) {
				String s = field.getName();
				if (s.equals("date")) {
					dateflag = true;
				}
				String methodName = "get" + s.substring(0, 1).toUpperCase() + s.substring(1);
				Method m = t.getClass().getDeclaredMethod(methodName);
				if (m.invoke(t) == null||m.invoke(t).equals("")) {
					continue;
				}
				if (field.getType().getSimpleName().equals("int") && (Integer) m.invoke(t) == -1) {

					continue;
				}
				value = value + s + "='" + m.invoke(t).toString() + "' and ";
				// System.out.println(value);
			}

			if (!value.equals("")) {
				sql = sql + " WHERE " + value.substring(0, value.length() - 5);
			}
			if (dateflag) {
				if(collectionName.contains("OrderTitle")){
					sql = sql + " order by date asc";
				}else{
				    sql = sql + " order by date desc";
				}
			}
			
			if(collectionName.contains("GroupMember")){
				sql=sql+" order by count desc";
			}
			if(collectionName.contains("Messages")||collectionName.contains("FriendZone")){
				sql=sql+" order by crawedTime desc";
			}
			db1 = new DBHelper(sql1);// 创建DBHelper对象
			// System.out.println(sql);

			if (collectionName.equals("ChatRoom") || collectionName.equals("GroupMember")||collectionName.equals("FriendZone")|| collectionName.equals("Messages")) {
				db1.pst.executeQuery("SET NAMES utf8mb4;");
			}
			ret = db1.pst.executeQuery(sql1);// 执行语句，得到结果集
			if (ret != null) {
				while (ret.next()) {
					T t_temp = (T) t.getClass().newInstance();// com.xl.wechat.AutoReply

					for (Field field : fields) {// field=int

						String s = field.getName();// s=id
						String methodName = "set" + s.substring(0, 1).toUpperCase() + s.substring(1);// setId
						Method m = t_temp.getClass().getDeclaredMethod(methodName, field.getType());// void
																									// com.xl.wechat.bean.AutoReply.setId(int)
						String typename = field.getType().getSimpleName();// int
						if ((typename != null) && typename.equals("int")) {
							m.invoke(t_temp, ret.getInt(s));
						}

						if ((typename != null) && typename.equals("String")) {
							String retvalue = ret.getObject(s) == null ? "" : ret.getObject(s).toString();
							m.invoke(t_temp, retvalue);
						}
					}
					tList.add(t_temp);
				} // 显示数据
			}

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ret != null) {
					ret.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			db1.close();
		}
		return tList;
	}

	/**
	 * 数据查询
	 * 
	 * @param t
	 *            查询条件类
	 * @return 查询结果list
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getListfromMysql(T t) {
		DBHelper db1 = null;
		ResultSet ret = null;
		String sql = null;
		String collectionName = t.getClass().getSimpleName();
		List<T> tList = new ArrayList<T>();
		sql = "SELECT * FROM " + collectionName;
		String value = "";
		boolean dateflag = false;
		try {
			Field[] fields = t.getClass().getDeclaredFields();

			for (Field field : fields) {
				String s = field.getName();
				if (s.equals("date")) {
					dateflag = true;
				}
				String methodName = "get" + s.substring(0, 1).toUpperCase() + s.substring(1);
				Method m = t.getClass().getDeclaredMethod(methodName);
				if (m.invoke(t) == null||m.invoke(t).equals("")) {
					continue;
				}
				if (field.getType().getSimpleName().equals("int") && (Integer) m.invoke(t) == -1) {

					continue;
				}
				value = value + s + "='" + m.invoke(t).toString() + "' and ";
			}

			if (!value.equals("")) {
				sql = sql + " WHERE " + value.substring(0, value.length() - 5);
			}
			if (dateflag) {
				if(collectionName.contains("OrderTitle")||collectionName.contains("CountTitle")){
					sql = sql + " order by date asc";
				}else{
				    sql = sql + " order by date desc";
				}
			}
			if(collectionName.contains("Clue")) {
				sql = sql + " order by id desc";
			}
			
			db1 = new DBHelper(sql);// 创建DBHelper对象
			// System.out.println(sql);

//			if (collectionName.equals("ChatRoom") || collectionName.equals("GroupMember")||collectionName.equals("FriendZone")|| collectionName.equals("Messages")) {
//				db1.pst.executeQuery("SET NAMES utf8mb4;");
//			}
			ret = db1.pst.executeQuery(sql);// 执行语句，得到结果集
//			System.out.println(sql);
			if (ret != null) {
				while (ret.next()) {
					T t_temp = (T) t.getClass().newInstance();// com.xl.wechat.AutoReply

					for (Field field : fields) {// field=int

						String s = field.getName();// s=id
						String methodName = "set" + s.substring(0, 1).toUpperCase() + s.substring(1);// setId
						Method m = t_temp.getClass().getDeclaredMethod(methodName, field.getType());// void
																									// com.xl.wechat.bean.AutoReply.setId(int)
						String typename = field.getType().getSimpleName();// int
						if ((typename != null) && typename.equals("int")) {
							m.invoke(t_temp, ret.getInt(s));
						}

						if ((typename != null) && typename.equals("String")) {
							String retvalue = ret.getObject(s) == null ? "" : ret.getObject(s).toString();
							m.invoke(t_temp, retvalue);
						}
					}
					tList.add(t_temp);
				} // 显示数据
			}

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ret != null) {
					ret.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			db1.close();
		}
		return tList;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getListfromMysql(T t, String appendSql) {
		DBHelper db1 = null;
		ResultSet ret = null;
		String sql = null;
		String collectionName = t.getClass().getSimpleName();
		List<T> tList = new ArrayList<T>();
		sql = "SELECT * FROM " + collectionName;
		String value = "";
		try {
			Field[] fields = t.getClass().getDeclaredFields();

			for (Field field : fields) {
				String s = field.getName();
				String methodName = "get" + s.substring(0, 1).toUpperCase() + s.substring(1);
				Method m = t.getClass().getDeclaredMethod(methodName);
				if (m.invoke(t) == null||m.invoke(t).equals("")) {
					continue;
				}
				if (field.getType().getSimpleName().equals("int") && (Integer) m.invoke(t) == -1) {

					continue;
				}
				value = value + s + "='" + m.invoke(t).toString() + "' and ";
			}
			if (!value.equals("")) {
				sql = sql + " WHERE " + value.substring(0, value.length() - 5);
				sql = sql + " AND "+appendSql;
			}else{
				sql = sql + " where "+appendSql;
			}
			if(collectionName.contains("Clue")) {
				sql = sql + " order by id desc";
			}
			db1 = new DBHelper(sql);// 创建DBHelper对象
			// System.out.println(sql);

			ret = db1.pst.executeQuery(sql);// 执行语句，得到结果集
//			System.out.println(sql);
			if (ret != null) {
				while (ret.next()) {
					T t_temp = (T) t.getClass().newInstance();// com.xl.wechat.AutoReply

					for (Field field : fields) {// field=int

						String s = field.getName();// s=id
						String methodName = "set" + s.substring(0, 1).toUpperCase() + s.substring(1);// setId
						Method m = t_temp.getClass().getDeclaredMethod(methodName, field.getType());// void
																									// com.xl.wechat.bean.AutoReply.setId(int)
						String typename = field.getType().getSimpleName();// int
						if ((typename != null) && typename.equals("int")) {
							m.invoke(t_temp, ret.getInt(s));
						}

						if ((typename != null) && typename.equals("String")) {
							String retvalue = ret.getObject(s) == null ? "" : ret.getObject(s).toString();
							m.invoke(t_temp, retvalue);
						}
					}
					tList.add(t_temp);
				} // 显示数据
			}

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ret != null) {
					ret.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			db1.close();
		}
		return tList;
	}

	/**
	 * 数据查询
	 * 
	 * @param t
	 *            查询条件类
	 * @return 查询结果list
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getListfromMysqlLikecase(T t,String starttime,String endtime) {
		DBHelper db1 = null;
		ResultSet ret = null;
		String sql = null;
		String collectionName = t.getClass().getSimpleName();
		List<T> tList = new ArrayList<T>();
		sql = "SELECT * FROM " + collectionName;
		String value = "";
		boolean dateflag = false;
		try {
			Field[] fields = t.getClass().getDeclaredFields();

			for (Field field : fields) {
				String s = field.getName();
				if (s.equals("date")) {
					dateflag = true;
				}
				String methodName = "get" + s.substring(0, 1).toUpperCase() + s.substring(1);
				Method m = t.getClass().getDeclaredMethod(methodName);
				if (m.invoke(t) == null||m.invoke(t).equals("")) {
					continue;
				}
				if (field.getType().getSimpleName().equals("int") && (Integer) m.invoke(t) == -1) {
					continue;
				}
				value = value + s + " like '%" + m.invoke(t).toString() + "%' and ";
			}

			if (!value.equals("")) {
				sql = sql + " WHERE " + value.substring(0, value.length() - 5);
				sql=sql+" AND createdTime between "+"'"+starttime+"'"+" AND "+"'"+endtime+"'";
			}else{
				sql=sql+" where createdTime between "+"'"+starttime+"'"+" AND "+"'"+endtime+"'";
			}
			
			if (dateflag) {
				sql = sql + " order by date desc";
			}
			db1 = new DBHelper(sql);// 创建DBHelper对象
			//System.out.println(sql);

//			if (collectionName.equals("ChatRoom") || collectionName.equals("GroupMember")||collectionName.equals("FriendZone")|| collectionName.equals("Messages")) {
//				db1.pst.executeQuery("SET NAMES utf8mb4;");
//			}

			ret = db1.pst.executeQuery(sql);// 执行语句，得到结果集
//			System.out.println(sql);
			if (ret != null) {
				while (ret.next()) {
					T t_temp = (T) t.getClass().newInstance();// com.xl.wechat.AutoReply

					for (Field field : fields) {// field=int

						String s = field.getName();// s=id
						String methodName = "set" + s.substring(0, 1).toUpperCase() + s.substring(1);// setId
						Method m = t_temp.getClass().getDeclaredMethod(methodName, field.getType());// void
																									// com.xl.wechat.bean.AutoReply.setId(int)
						String typename = field.getType().getSimpleName();// int
						if ((typename != null) && typename.equals("int")) {
							m.invoke(t_temp, ret.getInt(s));
						}

						if ((typename != null) && typename.equals("String")) {
							String retvalue = ret.getObject(s) == null ? "" : ret.getObject(s).toString();
							m.invoke(t_temp, retvalue);
						}

					}
					tList.add(t_temp);
				} // 显示数据
			}

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ret != null) {
					ret.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			db1.close();
		}
		return tList;
	}
	
	/**
	 * 数据查询
	 * 
	 * @param t
	 *            查询条件类
	 * @return 查询结果list
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getListfromMysqlLikecase(T t,String starttime,String endtime, int limitstart, int linitsize) {
		DBHelper db1 = null;
		ResultSet ret = null;
		String sql = null;
		String collectionName = t.getClass().getSimpleName();
		List<T> tList = new ArrayList<T>();
		sql = "SELECT * FROM " + collectionName;
		String value = "";
		boolean dateflag = false;
		try {
			Field[] fields = t.getClass().getDeclaredFields();

			for (Field field : fields) {
				String s = field.getName();
				if (s.equals("date")) {
					dateflag = true;
				}
				String methodName = "get" + s.substring(0, 1).toUpperCase() + s.substring(1);
				Method m = t.getClass().getDeclaredMethod(methodName);
				if (m.invoke(t) == null||m.invoke(t).equals("")) {
					continue;
				}
				if (field.getType().getSimpleName().equals("int") && (Integer) m.invoke(t) == -1) {
					continue;
				}
				value = value + s + " like '%" + m.invoke(t).toString() + "%' and ";
			}

			if (!value.equals("")) {
				sql = sql + " WHERE " + value.substring(0, value.length() - 5);
				sql=sql+" AND createdTime between "+"'"+starttime+"'"+" AND "+"'"+endtime+"'";
			}else{
				sql=sql+" where createdTime between "+"'"+starttime+"'"+" AND "+"'"+endtime+"'";
			}
			
			if (dateflag) {
				sql = sql + " order by date desc";
			}
			sql = sql + " limit " + limitstart + " , " + linitsize;
			db1 = new DBHelper(sql);// 创建DBHelper对象
//			System.out.println(sql);

//			if (collectionName.equals("ChatRoom") || collectionName.equals("GroupMember")||collectionName.equals("FriendZone")|| collectionName.equals("Messages")) {
//				db1.pst.executeQuery("SET NAMES utf8mb4;");
//			}

			ret = db1.pst.executeQuery(sql);// 执行语句，得到结果集
			if (ret != null) {
				while (ret.next()) {
					T t_temp = (T) t.getClass().newInstance();// com.xl.wechat.AutoReply

					for (Field field : fields) {// field=int

						String s = field.getName();// s=id
						String methodName = "set" + s.substring(0, 1).toUpperCase() + s.substring(1);// setId
						Method m = t_temp.getClass().getDeclaredMethod(methodName, field.getType());// void
																									// com.xl.wechat.bean.AutoReply.setId(int)
						String typename = field.getType().getSimpleName();// int
						if ((typename != null) && typename.equals("int")) {
							m.invoke(t_temp, ret.getInt(s));
						}

						if ((typename != null) && typename.equals("String")) {
							String retvalue = ret.getObject(s) == null ? "" : ret.getObject(s).toString();
							m.invoke(t_temp, retvalue);
						}

					}
					tList.add(t_temp);
				} // 显示数据
			}

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ret != null) {
					ret.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			db1.close();
		}
		return tList;
	}
	
	/**
	 * 数据查询
	 * 
	 * @param t
	 *            查询条件类
	 * @return 查询结果list
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getListfromMysqlLike(T t) {
		DBHelper db1 = null;
		ResultSet ret = null;
		String sql = null;
		String collectionName = t.getClass().getSimpleName();
		List<T> tList = new ArrayList<T>();
		sql = "SELECT * FROM " + collectionName;
		String value = "";
		boolean dateflag = false;
		try {
			Field[] fields = t.getClass().getDeclaredFields();

			for (Field field : fields) {
				String s = field.getName();
				if (s.equals("date")) {
					dateflag = true;
				}
				String methodName = "get" + s.substring(0, 1).toUpperCase() + s.substring(1);
				Method m = t.getClass().getDeclaredMethod(methodName);
				if (m.invoke(t) == null||m.invoke(t).equals("")) {
					continue;
				}
				if (field.getType().getSimpleName().equals("int") && (Integer) m.invoke(t) == -1) {
					continue;
				}
				value = value + s + " like '%" + m.invoke(t).toString() + "%' and ";
			}

			if (!value.equals("")) {
				sql = sql + " WHERE " + value.substring(0, value.length() - 5);
			}
			if (dateflag) {
				sql = sql + " order by date desc";
			}
			db1 = new DBHelper(sql);// 创建DBHelper对象
//			System.out.println(sql);

			ret = db1.pst.executeQuery(sql);// 执行语句，得到结果集
			if (ret != null) {
				while (ret.next()) {
					T t_temp = (T) t.getClass().newInstance();//

					for (Field field : fields) {// field=int

						String s = field.getName();// s=id
						String methodName = "set" + s.substring(0, 1).toUpperCase() + s.substring(1);// setId
						Method m = t_temp.getClass().getDeclaredMethod(methodName, field.getType());// void
																									// com.xl.wechat.bean.AutoReply.setId(int)
						String typename = field.getType().getSimpleName();// int
						if ((typename != null) && typename.equals("int")) {
							m.invoke(t_temp, ret.getInt(s));
						}

						if ((typename != null) && typename.equals("String")) {
							String retvalue = ret.getObject(s) == null ? "" : ret.getObject(s).toString();
							m.invoke(t_temp, retvalue);
						}

					}
					tList.add(t_temp);
				} // 显示数据
			}

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ret != null) {
					ret.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			db1.close();
		}
		return tList;
	}
	
	/**
	 * 数据查询
	 * 
	 * @param t
	 *            查询条件类
	 * @return 查询结果list
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getListfromMysqlNotLike(T t) {
		DBHelper db1 = null;
		ResultSet ret = null;
		String sql = null;
		String collectionName = t.getClass().getSimpleName();
		List<T> tList = new ArrayList<T>();
		sql = "SELECT * FROM " + collectionName;
		String value = "";
		boolean dateflag = false;
		try {
			Field[] fields = t.getClass().getDeclaredFields();

			for (Field field : fields) {
				String s = field.getName();
				if (s.equals("date")) {
					dateflag = true;
				}
				String methodName = "get" + s.substring(0, 1).toUpperCase() + s.substring(1);
				Method m = t.getClass().getDeclaredMethod(methodName);
				if (m.invoke(t) == null||m.invoke(t).equals("")) {
					continue;
				}
				if (field.getType().getSimpleName().equals("int") && (Integer) m.invoke(t) == -1) {
					continue;
				}
				value = value + s + " not like '%" + m.invoke(t).toString() + "%' and ";
			}

			if (!value.equals("")) {
				sql = sql + " WHERE " + value.substring(0, value.length() - 5);
			}
			if (dateflag) {
				sql = sql + " order by date desc";
			}
			db1 = new DBHelper(sql);// 创建DBHelper对象
//			System.out.println(sql);

			ret = db1.pst.executeQuery(sql);// 执行语句，得到结果集
			if (ret != null) {
				while (ret.next()) {
					T t_temp = (T) t.getClass().newInstance();

					for (Field field : fields) {// field=int

						String s = field.getName();// s=id
						String methodName = "set" + s.substring(0, 1).toUpperCase() + s.substring(1);// setId
						Method m = t_temp.getClass().getDeclaredMethod(methodName, field.getType());// void
																									// com.xl.wechat.bean.AutoReply.setId(int)
						String typename = field.getType().getSimpleName();// int
						if ((typename != null) && typename.equals("int")) {
							m.invoke(t_temp, ret.getInt(s));
						}

						if ((typename != null) && typename.equals("String")) {
							String retvalue = ret.getObject(s) == null ? "" : ret.getObject(s).toString();
							m.invoke(t_temp, retvalue);
						}
					}
					tList.add(t_temp);
				} // 显示数据
			}

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ret != null) {
					ret.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			db1.close();
		}
		return tList;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getListfromMysqlNotLike(T t, String appendSql) {
		DBHelper db1 = null;
		ResultSet ret = null;
		String sql = null;
		String collectionName = t.getClass().getSimpleName();
		List<T> tList = new ArrayList<T>();
		sql = "SELECT * FROM " + collectionName;
		String value = "";
		boolean dateflag = false;
		try {
			Field[] fields = t.getClass().getDeclaredFields();

			for (Field field : fields) {
				String s = field.getName();
				if (s.equals("date")) {
					dateflag = true;
				}
				String methodName = "get" + s.substring(0, 1).toUpperCase() + s.substring(1);
				Method m = t.getClass().getDeclaredMethod(methodName);
				if (m.invoke(t) == null||m.invoke(t).equals("")) {
					continue;
				}
				if (field.getType().getSimpleName().equals("int") && (Integer) m.invoke(t) == -1) {
					continue;
				}
				value = value + s + " not like '%" + m.invoke(t).toString() + "%' and ";
			}
			
			if (!value.equals("")) {
				sql = sql + " WHERE " + value.substring(0, value.length() - 5);
				sql = sql + " AND "+appendSql;
			}else{
				sql = sql + " where "+appendSql;
			}

			if (dateflag) {
				sql = sql + " order by date desc";
			}
			db1 = new DBHelper(sql);// 创建DBHelper对象
//			System.out.println(sql);

			ret = db1.pst.executeQuery(sql);// 执行语句，得到结果集
			if (ret != null) {
				while (ret.next()) {
					T t_temp = (T) t.getClass().newInstance();

					for (Field field : fields) {// field=int

						String s = field.getName();// s=id
						String methodName = "set" + s.substring(0, 1).toUpperCase() + s.substring(1);// setId
						Method m = t_temp.getClass().getDeclaredMethod(methodName, field.getType());// void
																									// com.xl.wechat.bean.AutoReply.setId(int)
						String typename = field.getType().getSimpleName();// int
						if ((typename != null) && typename.equals("int")) {
							m.invoke(t_temp, ret.getInt(s));
						}

						if ((typename != null) && typename.equals("String")) {
							String retvalue = ret.getObject(s) == null ? "" : ret.getObject(s).toString();
							m.invoke(t_temp, retvalue);
						}
					}
					tList.add(t_temp);
				} // 显示数据
			}

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ret != null) {
					ret.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			db1.close();
		}
		return tList;
	}
	
	/**
	 * 数据查询
	 * 
	 * @param t
	 *            查询条件类
	 * @return 查询结果list
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getListfromMysqlLike(T t, int limitstart, int linitsize) {
		DBHelper db1 = null;
		ResultSet ret = null;
		String sql = null;
		String collectionName = t.getClass().getSimpleName();
		List<T> tList = new ArrayList<T>();
		sql = "SELECT * FROM " + collectionName;
		String value = "";
		boolean dateflag = false;
		try {
			Field[] fields = t.getClass().getDeclaredFields();

			for (Field field : fields) {
				String s = field.getName();
				if (s.equals("date")) {
					dateflag = true;
				}
				String methodName = "get" + s.substring(0, 1).toUpperCase() + s.substring(1);
				Method m = t.getClass().getDeclaredMethod(methodName);
				if (m.invoke(t) == null||m.invoke(t).equals("")) {
					continue;
				}
				if (field.getType().getSimpleName().equals("int") && (Integer) m.invoke(t) == -1) {
					continue;
				}
				value = value + s + " like '%" + m.invoke(t).toString() + "%' and ";
			}

			if (!value.equals("")) {
				sql = sql + " WHERE " + value.substring(0, value.length() - 5);
			}
			if (dateflag) {
				sql = sql + " order by date desc";
			}
			sql = sql + " limit " + limitstart + " , " + linitsize;
			db1 = new DBHelper(sql);// 创建DBHelper对象

			if (collectionName.equals("ChatRoom") || collectionName.equals("GroupMember")||collectionName.equals("FriendZone")|| collectionName.equals("Messages")) {
				db1.pst.executeQuery("SET NAMES utf8mb4;");
			}

			ret = db1.pst.executeQuery(sql);// 执行语句，得到结果集
			if (ret != null) {
				while (ret.next()) {
					T t_temp = (T) t.getClass().newInstance();

					for (Field field : fields) {// field=int

						String s = field.getName();// s=id
						String methodName = "set" + s.substring(0, 1).toUpperCase() + s.substring(1);// setId
						Method m = t_temp.getClass().getDeclaredMethod(methodName, field.getType());// void
																									// com.xl.wechat.bean.AutoReply.setId(int)
						String typename = field.getType().getSimpleName();// int
						if ((typename != null) && typename.equals("int")) {
							m.invoke(t_temp, ret.getInt(s));
						}

						if ((typename != null) && typename.equals("String")) {
							String retvalue = ret.getObject(s) == null ? "" : ret.getObject(s).toString();
							m.invoke(t_temp, retvalue);
						}

					}
					tList.add(t_temp);
				} // 显示数据
			}

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ret != null) {
					ret.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			db1.close();
		}
		return tList;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getOrderListfromMysqlLike(T t, String orderKey) {
		DBHelper db1 = null;
		ResultSet ret = null;
		String sql = null;
		String collectionName = t.getClass().getSimpleName();
		List<T> tList = new ArrayList<T>();
		sql = "SELECT * FROM " + collectionName;
		String value = "";
		try {
			Field[] fields = t.getClass().getDeclaredFields();

			for (Field field : fields) {
				String s = field.getName();
				String methodName = "get" + s.substring(0, 1).toUpperCase() + s.substring(1);
				Method m = t.getClass().getDeclaredMethod(methodName);
				if (m.invoke(t) == null||m.invoke(t).equals("")) {
					continue;
				}
				if (field.getType().getSimpleName().equals("int") && (Integer) m.invoke(t) == -1) {
					continue;
				}
				value = value + s + " like '%" + m.invoke(t).toString() + "%' and ";
			}

			if (!value.equals("")) {
				sql = sql + " WHERE " + value.substring(0, value.length() - 5);
			}
			sql = sql + " order by "+orderKey+" desc";
			db1 = new DBHelper(sql);// 创建DBHelper对象

			if (collectionName.equals("ChatRoom") || collectionName.equals("GroupMember")||collectionName.equals("FriendZone")|| collectionName.equals("Messages")) {
				db1.pst.executeQuery("SET NAMES utf8mb4;");
			}

			ret = db1.pst.executeQuery(sql);// 执行语句，得到结果集
			if (ret != null) {
				while (ret.next()) {
					T t_temp = (T) t.getClass().newInstance();

					for (Field field : fields) {// field=int

						String s = field.getName();// s=id
						String methodName = "set" + s.substring(0, 1).toUpperCase() + s.substring(1);// setId
						Method m = t_temp.getClass().getDeclaredMethod(methodName, field.getType());// void
																									// com.xl.wechat.bean.AutoReply.setId(int)
						String typename = field.getType().getSimpleName();// int
						if ((typename != null) && typename.equals("int")) {
							m.invoke(t_temp, ret.getInt(s));
						}

						if ((typename != null) && typename.equals("String")) {
							String retvalue = ret.getObject(s) == null ? "" : ret.getObject(s).toString();
							m.invoke(t_temp, retvalue);
						}

					}
					tList.add(t_temp);
				} // 显示数据
			}

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ret != null) {
					ret.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			db1.close();
		}
		return tList;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getOrderListfromMysqlLike(T t, int limitstart, int linitsize, String orderKey) {
		DBHelper db1 = null;
		ResultSet ret = null;
		String sql = null;
		String collectionName = t.getClass().getSimpleName();
		List<T> tList = new ArrayList<T>();
		sql = "SELECT * FROM " + collectionName;
		String value = "";
		try {
			Field[] fields = t.getClass().getDeclaredFields();

			for (Field field : fields) {
				String s = field.getName();
				String methodName = "get" + s.substring(0, 1).toUpperCase() + s.substring(1);
				Method m = t.getClass().getDeclaredMethod(methodName);
				if (m.invoke(t) == null||m.invoke(t).equals("")) {
					continue;
				}
				if (field.getType().getSimpleName().equals("int") && (Integer) m.invoke(t) == -1) {
					continue;
				}
				value = value + s + " like '%" + m.invoke(t).toString() + "%' and ";
			}

			if (!value.equals("")) {
				sql = sql + " WHERE " + value.substring(0, value.length() - 5);
			}
			sql = sql + " order by "+orderKey+" desc limit " + limitstart + " , " + linitsize;
			db1 = new DBHelper(sql);// 创建DBHelper对象

			if (collectionName.equals("ChatRoom") || collectionName.equals("GroupMember")||collectionName.equals("FriendZone")|| collectionName.equals("Messages")) {
				db1.pst.executeQuery("SET NAMES utf8mb4;");
			}

			ret = db1.pst.executeQuery(sql);// 执行语句，得到结果集
			if (ret != null) {
				while (ret.next()) {
					T t_temp = (T) t.getClass().newInstance();

					for (Field field : fields) {// field=int

						String s = field.getName();// s=id
						String methodName = "set" + s.substring(0, 1).toUpperCase() + s.substring(1);// setId
						Method m = t_temp.getClass().getDeclaredMethod(methodName, field.getType());// void
																									// com.xl.wechat.bean.AutoReply.setId(int)
						String typename = field.getType().getSimpleName();// int
						if ((typename != null) && typename.equals("int")) {
							m.invoke(t_temp, ret.getInt(s));
						}

						if ((typename != null) && typename.equals("String")) {
							String retvalue = ret.getObject(s) == null ? "" : ret.getObject(s).toString();
							m.invoke(t_temp, retvalue);
						}

					}
					tList.add(t_temp);
				} // 显示数据
			}

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ret != null) {
					ret.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			db1.close();
		}
		return tList;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getOrderListfromMysqlLike(T t, String appendSql, String orderKey) {
		DBHelper db1 = null;
		ResultSet ret = null;
		String sql = null;
		String collectionName = t.getClass().getSimpleName();
		List<T> tList = new ArrayList<T>();
		sql = "SELECT * FROM " + collectionName;
		String value = "";
		try {
			Field[] fields = t.getClass().getDeclaredFields();

			for (Field field : fields) {
				String s = field.getName();
				String methodName = "get" + s.substring(0, 1).toUpperCase() + s.substring(1);
				Method m = t.getClass().getDeclaredMethod(methodName);
				if (m.invoke(t) == null||m.invoke(t).equals("")) {
					continue;
				}
				if (field.getType().getSimpleName().equals("int") && (Integer) m.invoke(t) == -1) {
					continue;
				}
				value = value + s + " like '%" + m.invoke(t).toString() + "%' and ";
			}

			if (!value.equals("")) {
				sql = sql + " WHERE " + value.substring(0, value.length() - 5);
				sql = sql + " AND "+appendSql;
			}else{
				sql = sql + " where "+appendSql;
			}
			
			sql = sql + " order by "+orderKey+" desc";
			db1 = new DBHelper(sql);// 创建DBHelper对象

			ret = db1.pst.executeQuery(sql);// 执行语句，得到结果集
			if (ret != null) {
				while (ret.next()) {
					T t_temp = (T) t.getClass().newInstance();

					for (Field field : fields) {// field=int

						String s = field.getName();// s=id
						String methodName = "set" + s.substring(0, 1).toUpperCase() + s.substring(1);// setId
						Method m = t_temp.getClass().getDeclaredMethod(methodName, field.getType());// void
																									// com.xl.wechat.bean.AutoReply.setId(int)
						String typename = field.getType().getSimpleName();// int
						if ((typename != null) && typename.equals("int")) {
							m.invoke(t_temp, ret.getInt(s));
						}

						if ((typename != null) && typename.equals("String")) {
							String retvalue = ret.getObject(s) == null ? "" : ret.getObject(s).toString();
							m.invoke(t_temp, retvalue);
						}

					}
					tList.add(t_temp);
				} // 显示数据
			}

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ret != null) {
					ret.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			db1.close();
		}
		return tList;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getOrderListfromMysqlLike(T t, String appendSql, int limitstart, int linitsize, String orderKey) {
		DBHelper db1 = null;
		ResultSet ret = null;
		String sql = null;
		String collectionName = t.getClass().getSimpleName();
		List<T> tList = new ArrayList<T>();
		sql = "SELECT * FROM " + collectionName;
		String value = "";
		try {
			Field[] fields = t.getClass().getDeclaredFields();

			for (Field field : fields) {
				String s = field.getName();
				String methodName = "get" + s.substring(0, 1).toUpperCase() + s.substring(1);
				Method m = t.getClass().getDeclaredMethod(methodName);
				if (m.invoke(t) == null||m.invoke(t).equals("")) {
					continue;
				}
				if (field.getType().getSimpleName().equals("int") && (Integer) m.invoke(t) == -1) {
					continue;
				}
				value = value + s + " like '%" + m.invoke(t).toString() + "%' and ";
			}

			if (!value.equals("")) {
				sql = sql + " WHERE " + value.substring(0, value.length() - 5);
				sql = sql + " AND "+appendSql;
			}else{
				sql = sql + " where "+appendSql;
			}
			
			sql = sql + " order by "+orderKey+" desc limit " + limitstart + " , " + linitsize;
//			System.out.println(sql);
			db1 = new DBHelper(sql);// 创建DBHelper对象

//			if (collectionName.equals("ChatRoom") || collectionName.equals("GroupMember")||collectionName.equals("FriendZone")|| collectionName.equals("Messages")) {
//				db1.pst.executeQuery("SET NAMES utf8mb4;");
//			}

			ret = db1.pst.executeQuery(sql);// 执行语句，得到结果集
			if (ret != null) {
				while (ret.next()) {
					T t_temp = (T) t.getClass().newInstance();

					for (Field field : fields) {// field=int

						String s = field.getName();// s=id
						String methodName = "set" + s.substring(0, 1).toUpperCase() + s.substring(1);// setId
						Method m = t_temp.getClass().getDeclaredMethod(methodName, field.getType());// void
																									// com.xl.wechat.bean.AutoReply.setId(int)
						String typename = field.getType().getSimpleName();// int
						if ((typename != null) && typename.equals("int")) {
							m.invoke(t_temp, ret.getInt(s));
						}

						if ((typename != null) && typename.equals("String")) {
							String retvalue = ret.getObject(s) == null ? "" : ret.getObject(s).toString();
							m.invoke(t_temp, retvalue);
						}

					}
					tList.add(t_temp);
				} // 显示数据
			}

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ret != null) {
					ret.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			db1.close();
		}
		return tList;
	}

	/**
	 * 数据查询,分页查询
	 * 
	 * @param t
	 *            查询条件类
	 * @return 查询结果list
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getListfromMysql(T t, int limitstart, int linitsize) {
		DBHelper db1 = null;
		ResultSet ret = null;
		String sql = null;
		String collectionName = t.getClass().getSimpleName();
		List<T> tList = new ArrayList<T>();
		sql = "SELECT * FROM " + collectionName;
		String value = "";
		boolean dateflag = false;
		try {
			Field[] fields = t.getClass().getDeclaredFields();

			for (Field field : fields) {
				String s = field.getName();
				if (s.equals("date")) {
					dateflag = true;
				}
				String methodName = "get" + s.substring(0, 1).toUpperCase() + s.substring(1);
				Method m = t.getClass().getDeclaredMethod(methodName);
				if (m.invoke(t) == null||m.invoke(t).equals("")) {
					continue;
				}
				if (field.getType().getSimpleName().equals("int") && (Integer) m.invoke(t) == -1) {
					continue;
				}
				value = value + s + "='" + m.invoke(t).toString() + "' and ";
			}

			if (!value.equals("")) {
				sql = sql + " WHERE " + value.substring(0, value.length() - 5);
			}
			if (dateflag) {
				sql = sql + " order by date desc";
			}
			sql = sql + " limit " + limitstart + " , " + linitsize;
			db1 = new DBHelper(sql);// 创建DBHelper对象
//			System.out.println(sql);
//			if (collectionName.equals("ChatRoom") || collectionName.equals("GroupMember")||collectionName.equals("FriendZone")|| collectionName.equals("Messages")) {
//				db1.pst.executeQuery("SET NAMES utf8mb4;");
//			}
			ret = db1.pst.executeQuery(sql);// 执行语句，得到结果集

			while (ret.next()) {
				T t_temp = (T) t.getClass().newInstance();

				for (Field field : fields) {

					String s = field.getName();
					String methodName = "set" + s.substring(0, 1).toUpperCase() + s.substring(1);
					Method m = t_temp.getClass().getDeclaredMethod(methodName, field.getType());
					String typename = field.getType().getSimpleName();
					if (typename.equals("int")) {
						m.invoke(t_temp, ret.getInt(s));
					}
					if (typename.equals("String")) {
						String retvalue = ret.getObject(s) == null ? "" : ret.getObject(s).toString();
						m.invoke(t_temp, retvalue);
					}

				}
				tList.add(t_temp);
			} // 显示数据

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ret != null) {
					ret.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			db1.close();
		}
		return tList;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getOrderListfromMysql(T t, int limitstart, int linitsize, String orderKey) {
		DBHelper db1 = null;
		ResultSet ret = null;
		String sql = null;
		String collectionName = t.getClass().getSimpleName();
		List<T> tList = new ArrayList<T>();
		sql = "SELECT * FROM " + collectionName;
		String value = "";
		try {
			Field[] fields = t.getClass().getDeclaredFields();

			for (Field field : fields) {
				String s = field.getName();
				String methodName = "get" + s.substring(0, 1).toUpperCase() + s.substring(1);
				Method m = t.getClass().getDeclaredMethod(methodName);
				if (m.invoke(t) == null||m.invoke(t).equals("")) {
					continue;
				}
				if (field.getType().getSimpleName().equals("int") && (Integer) m.invoke(t) == -1) {
					continue;
				}
				value = value + s + "='" + m.invoke(t).toString() + "' and ";
			}

			if (!value.equals("")) {
				sql = sql + " WHERE " + value.substring(0, value.length() - 5);
			}
			sql = sql + " order by "+orderKey+" desc limit " + limitstart + " , " + linitsize;
			db1 = new DBHelper(sql);// 创建DBHelper对象
//			System.out.println(sql);
			ret = db1.pst.executeQuery(sql);// 执行语句，得到结果集

			while (ret.next()) {
				T t_temp = (T) t.getClass().newInstance();

				for (Field field : fields) {

					String s = field.getName();
					String methodName = "set" + s.substring(0, 1).toUpperCase() + s.substring(1);
					Method m = t_temp.getClass().getDeclaredMethod(methodName, field.getType());
					String typename = field.getType().getSimpleName();
					if (typename.equals("int")) {
						m.invoke(t_temp, ret.getInt(s));
					}
					if (typename.equals("String")) {
						String retvalue = ret.getObject(s) == null ? "" : ret.getObject(s).toString();
						m.invoke(t_temp, retvalue);
					}

				}
				tList.add(t_temp);
			} // 显示数据

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ret != null) {
					ret.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			db1.close();
		}
		return tList;
	}
	
	//升序
	@SuppressWarnings("unchecked")
	public <T> List<T> getOrderListfromMysql3(T t, int limitstart, int linitsize, String orderKey) {
		DBHelper db1 = null;
		ResultSet ret = null;
		String sql = null;
		String collectionName = t.getClass().getSimpleName();
		List<T> tList = new ArrayList<T>();
		sql = "SELECT * FROM " + collectionName;
//		String value = "";
		try {
			Field[] fields = t.getClass().getDeclaredFields();

			sql = sql + " order by "+orderKey+" asc limit " + limitstart + " , " + linitsize;
			db1 = new DBHelper(sql);// 创建DBHelper对象
//			System.out.println(sql);
			ret = db1.pst.executeQuery(sql);// 执行语句，得到结果集

			while (ret.next()) {
				T t_temp = (T) t.getClass().newInstance();

				for (Field field : fields) {

					String s = field.getName();
					String methodName = "set" + s.substring(0, 1).toUpperCase() + s.substring(1);
					Method m = t_temp.getClass().getDeclaredMethod(methodName, field.getType());
					String typename = field.getType().getSimpleName();
					if (typename.equals("int")) {
						m.invoke(t_temp, ret.getInt(s));
					}
					if (typename.equals("String")) {
						String retvalue = ret.getObject(s) == null ? "" : ret.getObject(s).toString();
						m.invoke(t_temp, retvalue);
					}

				}
				tList.add(t_temp);
			} // 显示数据

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ret != null) {
					ret.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			db1.close();
		}
		return tList;
	}
	
	
	//降序
	@SuppressWarnings("unchecked")
	public <T> List<T> getOrderListfromMysql2(T t, int limitstart, int linitsize, String orderKey) {
		DBHelper db1 = null;
		ResultSet ret = null;
		String sql = null;
		String collectionName = t.getClass().getSimpleName();
		List<T> tList = new ArrayList<T>();
		sql = "SELECT * FROM " + collectionName;
//		String value = "";
		try {
			Field[] fields = t.getClass().getDeclaredFields();

			

			
			sql = sql + " order by "+orderKey+" desc limit " + limitstart + " , " + linitsize;
			db1 = new DBHelper(sql);// 创建DBHelper对象
//			System.out.println(sql);
			ret = db1.pst.executeQuery(sql);// 执行语句，得到结果集

			while (ret.next()) {
				T t_temp = (T) t.getClass().newInstance();

				for (Field field : fields) {

					String s = field.getName();
					String methodName = "set" + s.substring(0, 1).toUpperCase() + s.substring(1);
					Method m = t_temp.getClass().getDeclaredMethod(methodName, field.getType());
					String typename = field.getType().getSimpleName();
					if (typename.equals("int")) {
						m.invoke(t_temp, ret.getInt(s));
					}
					if (typename.equals("String")) {
						String retvalue = ret.getObject(s) == null ? "" : ret.getObject(s).toString();
						m.invoke(t_temp, retvalue);
					}

				}
				tList.add(t_temp);
			} // 显示数据

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ret != null) {
					ret.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			db1.close();
		}
		return tList;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getOrderListfromMysql(T t, String orderKey) {
		DBHelper db1 = null;
		ResultSet ret = null;
		String sql = null;
		String collectionName = t.getClass().getSimpleName();
		List<T> tList = new ArrayList<T>();
		sql = "SELECT * FROM " + collectionName;
		String value = "";
		try {
			Field[] fields = t.getClass().getDeclaredFields();

			for (Field field : fields) {
				String s = field.getName();
				String methodName = "get" + s.substring(0, 1).toUpperCase() + s.substring(1);
				Method m = t.getClass().getDeclaredMethod(methodName);
				if (m.invoke(t) == null||m.invoke(t).equals("")) {
					continue;
				}
				if (field.getType().getSimpleName().equals("int") && (Integer) m.invoke(t) == -1) {
					continue;
				}
				value = value + s + "='" + m.invoke(t).toString() + "' and ";
			}

			if (!value.equals("")) {
				sql = sql + " WHERE " + value.substring(0, value.length() - 5);
			}
			sql = sql + " order by "+orderKey+" desc";
			db1 = new DBHelper(sql);// 创建DBHelper对象
//			System.out.println(sql);
			ret = db1.pst.executeQuery(sql);// 执行语句，得到结果集

			while (ret.next()) {
				T t_temp = (T) t.getClass().newInstance();

				for (Field field : fields) {

					String s = field.getName();
					String methodName = "set" + s.substring(0, 1).toUpperCase() + s.substring(1);
					Method m = t_temp.getClass().getDeclaredMethod(methodName, field.getType());
					String typename = field.getType().getSimpleName();
					if (typename.equals("int")) {
						m.invoke(t_temp, ret.getInt(s));
					}
					if (typename.equals("String")) {
						String retvalue = ret.getObject(s) == null ? "" : ret.getObject(s).toString();
						m.invoke(t_temp, retvalue);
					}

				}
				tList.add(t_temp);
			} // 显示数据

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ret != null) {
					ret.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			db1.close();
		}
		return tList;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getOrderListfromMysql(T t, String appendSql, String orderKey) {
		DBHelper db1 = null;
		ResultSet ret = null;
		String sql = null;
		String collectionName = t.getClass().getSimpleName();
		List<T> tList = new ArrayList<T>();
		sql = "SELECT * FROM " + collectionName;
		String value = "";
		try {
			Field[] fields = t.getClass().getDeclaredFields();

			for (Field field : fields) {
				String s = field.getName();
				String methodName = "get" + s.substring(0, 1).toUpperCase() + s.substring(1);
				Method m = t.getClass().getDeclaredMethod(methodName);
				if (m.invoke(t) == null||m.invoke(t).equals("")) {
					continue;
				}
				if (field.getType().getSimpleName().equals("int") && (Integer) m.invoke(t) == -1) {
					continue;
				}
				value = value + s + "='" + m.invoke(t).toString() + "' and ";
			}

			if (!value.equals("")) {
				sql = sql + " WHERE " + value.substring(0, value.length() - 5);
				sql = sql + " AND "+appendSql;
			}else{
				sql = sql + " where "+appendSql;
			}
			
			sql = sql + " order by "+orderKey+" desc";
			db1 = new DBHelper(sql);// 创建DBHelper对象

			ret = db1.pst.executeQuery(sql);// 执行语句，得到结果集
			if (ret != null) {
				while (ret.next()) {
					T t_temp = (T) t.getClass().newInstance();

					for (Field field : fields) {// field=int

						String s = field.getName();// s=id
						String methodName = "set" + s.substring(0, 1).toUpperCase() + s.substring(1);// setId
						Method m = t_temp.getClass().getDeclaredMethod(methodName, field.getType());// void
																									// com.xl.wechat.bean.AutoReply.setId(int)
						String typename = field.getType().getSimpleName();// int
						if ((typename != null) && typename.equals("int")) {
							m.invoke(t_temp, ret.getInt(s));
						}

						if ((typename != null) && typename.equals("String")) {
							String retvalue = ret.getObject(s) == null ? "" : ret.getObject(s).toString();
							m.invoke(t_temp, retvalue);
						}

					}
					tList.add(t_temp);
				} // 显示数据
			}

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ret != null) {
					ret.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			db1.close();
		}
		return tList;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getOrderListfromMysql(T t, String appendSql, int limitstart, int linitsize, String orderKey) {
		DBHelper db1 = null;
		ResultSet ret = null;
		String sql = null;
		String collectionName = t.getClass().getSimpleName();
		List<T> tList = new ArrayList<T>();
		sql = "SELECT * FROM " + collectionName;
		String value = "";
		try {
			Field[] fields = t.getClass().getDeclaredFields();

			for (Field field : fields) {
				String s = field.getName();
				String methodName = "get" + s.substring(0, 1).toUpperCase() + s.substring(1);
				Method m = t.getClass().getDeclaredMethod(methodName);
				if (m.invoke(t) == null||m.invoke(t).equals("")) {
					continue;
				}
				if (field.getType().getSimpleName().equals("int") && (Integer) m.invoke(t) == -1) {
					continue;
				}
				value = value + s + "='" + m.invoke(t).toString() + "' and ";
			}

			if (!value.equals("")) {
				sql = sql + " WHERE " + value.substring(0, value.length() - 5);
				sql = sql + " AND "+appendSql;
			}else{
				sql = sql + " where "+appendSql;
			}
			
			sql = sql + " order by "+orderKey+" desc limit " + limitstart + " , " + linitsize;
			db1 = new DBHelper(sql);// 创建DBHelper对象

			ret = db1.pst.executeQuery(sql);// 执行语句，得到结果集
			if (ret != null) {
				while (ret.next()) {
					T t_temp = (T) t.getClass().newInstance();

					for (Field field : fields) {// field=int

						String s = field.getName();// s=id
						String methodName = "set" + s.substring(0, 1).toUpperCase() + s.substring(1);// setId
						Method m = t_temp.getClass().getDeclaredMethod(methodName, field.getType());// void
																									// com.xl.wechat.bean.AutoReply.setId(int)
						String typename = field.getType().getSimpleName();// int
						if ((typename != null) && typename.equals("int")) {
							m.invoke(t_temp, ret.getInt(s));
						}

						if ((typename != null) && typename.equals("String")) {
							String retvalue = ret.getObject(s) == null ? "" : ret.getObject(s).toString();
							m.invoke(t_temp, retvalue);
						}

					}
					tList.add(t_temp);
				} // 显示数据
			}

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ret != null) {
					ret.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			db1.close();
		}
		return tList;
	}

	/**
	 * 数据查询,查询总个数
	 * 
	 * @param t
	 *            查询条件类
	 * @return 查询结果list
	 */
	public <T> int getcountfromMysql(T t) {
		DBHelper db1 = null;
		ResultSet ret = null;
		String sql = null;
		String collectionName = t.getClass().getSimpleName();
		sql = "SELECT count(*) FROM " + collectionName;
		String value = "";
		int size = 0;
		try {
			Field[] fields = t.getClass().getDeclaredFields();

			for (Field field : fields) {
				String s = field.getName();
				String methodName = "get" + s.substring(0, 1).toUpperCase() + s.substring(1);
				Method m = t.getClass().getDeclaredMethod(methodName);
				if (m.invoke(t) == null||m.invoke(t).equals("")) {
					continue;
				}
				if (field.getType().getSimpleName().equals("int") && (Integer) m.invoke(t) == -1) {
					continue;
				}
				value = value + s + "='" + m.invoke(t).toString() + "' and ";
			}
			if (!value.equals("")) {
				sql = sql + " WHERE " + value.substring(0, value.length() - 5);
			}
			db1 = new DBHelper(sql);// 创建DBHelper对象
//			System.out.println(sql);
//			if (collectionName.equals("ChatRoom") || collectionName.equals("GroupMember")||collectionName.equals("FriendZone")|| collectionName.equals("Messages")) {
//				db1.pst.executeQuery("SET NAMES utf8mb4;");
//			}
//			System.out.println(sql);
			ret = db1.pst.executeQuery(sql);// 执行语句，得到结果集
			if (ret.next()) {
				size = ret.getInt(1);
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ret != null) {
					ret.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			db1.close();
		}
		return size;
	}
	
	/**
	 * 数据查询,查询总个数
	 * 
	 * @param t
	 *            查询条件类
	 * @return 查询结果list
	 */
	public <T> int getcountfromMysql2(T t) {
		DBHelper db1 = null;
		ResultSet ret = null;
		String sql = null;
		String collectionName = t.getClass().getSimpleName();
		sql = "SELECT count(*) FROM " + collectionName;
		
		int size = 0;
		try {
			
			db1 = new DBHelper(sql);// 创建DBHelper对象

//			System.out.println(sql);
			ret = db1.pst.executeQuery(sql);// 执行语句，得到结果集
			if (ret.next()) {
				size = ret.getInt(1);
			}
		}  catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ret != null) {
					ret.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			db1.close();
		}
		return size;
	}
	
	public <T> int getcountfromMysql(T t, String appendSql) {
		DBHelper db1 = null;
		ResultSet ret = null;
		String sql = null;
		String collectionName = t.getClass().getSimpleName();
		sql = "SELECT count(*) FROM " + collectionName;
		String value = "";
		int size = 0;
		try {
			Field[] fields = t.getClass().getDeclaredFields();

			for (Field field : fields) {
				String s = field.getName();
				String methodName = "get" + s.substring(0, 1).toUpperCase() + s.substring(1);
				Method m = t.getClass().getDeclaredMethod(methodName);
				if (m.invoke(t) == null||m.invoke(t).equals("")) {
					continue;
				}
				if (field.getType().getSimpleName().equals("int") && (Integer) m.invoke(t) == -1) {
					continue;
				}
				value = value + s + "='" + m.invoke(t).toString() + "' and ";
			}
			if (!value.equals("")) {
				sql = sql + " WHERE " + value.substring(0, value.length() - 5);
				sql = sql + " AND " + appendSql;
			}else{
				sql = sql + " WHERE " + appendSql;
			}
			db1 = new DBHelper(sql);// 创建DBHelper对象
//			System.out.println(sql);

			ret = db1.pst.executeQuery(sql);// 执行语句，得到结果集
			if (ret.next()) {
				size = ret.getInt(1);
			}

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ret != null) {
					ret.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			db1.close();
		}
		return size;
	}

	public <T> int getcountfromMysqlLike(T t) {
		DBHelper db1 = null;
		ResultSet ret = null;
		String sql = null;
		String collectionName = t.getClass().getSimpleName();
		sql = "SELECT count(*) FROM " + collectionName;
		String value = "";
		int size = 0;
		try {
			Field[] fields = t.getClass().getDeclaredFields();

			for (Field field : fields) {
				String s = field.getName();
				String methodName = "get" + s.substring(0, 1).toUpperCase() + s.substring(1);
				Method m = t.getClass().getDeclaredMethod(methodName);
				if (m.invoke(t) == null||m.invoke(t).equals("")) {
					continue;
				}
				if (field.getType().getSimpleName().equals("int") && (Integer) m.invoke(t) == -1) {
					continue;
				}
				value = value + s + " like '%" + m.invoke(t).toString() + "%' and ";
			}
			if (!value.equals("")) {
				sql = sql + " WHERE " + value.substring(0, value.length() - 5);
			}
			db1 = new DBHelper(sql);// 创建DBHelper对象
//			System.out.println(sql);

//			if (collectionName.equals("ChatRoom") || collectionName.equals("GroupMember")||collectionName.equals("FriendZone")|| collectionName.equals("Messages")) {
//				db1.pst.executeQuery("SET NAMES utf8mb4;");
//			}

			ret = db1.pst.executeQuery(sql);// 执行语句，得到结果集
			if (ret.next()) {
				size = ret.getInt(1);
			}

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ret != null) {
					ret.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			db1.close();
		}
		return size;
	}
	
	public <T> int getcountfromMysqlLike(T t, String appendSql) {
		DBHelper db1 = null;
		ResultSet ret = null;
		String sql = null;
		String collectionName = t.getClass().getSimpleName();
		sql = "SELECT count(*) FROM " + collectionName;
		String value = "";
		int size = 0;
		try {
			Field[] fields = t.getClass().getDeclaredFields();

			for (Field field : fields) {
				String s = field.getName();
				String methodName = "get" + s.substring(0, 1).toUpperCase() + s.substring(1);
				Method m = t.getClass().getDeclaredMethod(methodName);
				if (m.invoke(t) == null||m.invoke(t).equals("")) {
					continue;
				}
				if (field.getType().getSimpleName().equals("int") && (Integer) m.invoke(t) == -1) {
					continue;
				}
				value = value + s + " like '%" + m.invoke(t).toString() + "%' and ";
			}
			if (!value.equals("")) {
				sql = sql + " WHERE " + value.substring(0, value.length() - 5);
				sql = sql + " AND " + appendSql;
			}else{
				sql = sql + " WHERE " + appendSql;
			}
			db1 = new DBHelper(sql);// 创建DBHelper对象
//			System.out.println(sql);

//			if (collectionName.equals("ChatRoom") || collectionName.equals("GroupMember")||collectionName.equals("FriendZone")|| collectionName.equals("Messages")) {
//				db1.pst.executeQuery("SET NAMES utf8mb4;");
//			}

			ret = db1.pst.executeQuery(sql);// 执行语句，得到结果集
			if (ret.next()) {
				size = ret.getInt(1);
			}

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ret != null) {
					ret.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			db1.close();
		}
		return size;
	}

	// 删除数据
	public <T> void deletefromMysql(T t) {
		DBHelper db1 = null;
		ResultSet ret = null;
		String sql = null;
		String collectionName = t.getClass().getSimpleName();
		try {
			Field[] fields = t.getClass().getDeclaredFields();
			sql = "DELETE FROM " + collectionName;
			String value = "";
			for (Field field : fields) {
				String s = field.getName();
				String methodName = "get" + s.substring(0, 1).toUpperCase() + s.substring(1);

				Method m = t.getClass().getDeclaredMethod(methodName);
				if (m.invoke(t) == null||m.invoke(t).equals("")) {
					continue;
				}
				if (field.getType().getSimpleName().equals("int") && (Integer) m.invoke(t) == -1) {
					continue;
				}
				value = value + s + "='" + m.invoke(t).toString() + "' and ";
			}
			if (!value.equals("")) {
				sql = sql + " WHERE " + value.substring(0, value.length() - 5);
			} else {
				sql = "truncate table " + collectionName; // 当传入的是一个空类的时候，清除表数据
			}
			db1 = new DBHelper(sql);// 创建DBHelper对象

//		    System.out.println(sql);
			db1.pst.executeUpdate(sql);// 执行语句，得到结果集

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ret != null) {
					ret.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			db1.close();
		}

	}

	/**
	 * wyw 数据入库
	 * 
	 * @param t
	 *            传入的类
	 */
	public <T> void setBeanToMysql(T t,int i) {
		DBHelper db1 = null;
		ResultSet ret = null;
		String sql = null;
		String collectionName = t.getClass().getSimpleName();
		try {
			Field[] fields = t.getClass().getDeclaredFields();
			String sqlkey = "INSERT INTO " + collectionName + " (";// SQL语句
			String sqlValue = " VALUES (";
			for (Field field : fields) {
				String s = field.getName();
				if (s.equals("id")) {
					continue;
				}
				String methodName = "get" + s.substring(0, 1).toUpperCase() + s.substring(1);
				Method m = t.getClass().getDeclaredMethod(methodName);
				if (m.invoke(t) == null||m.invoke(t).equals("")) {
					continue;
				}
				sqlkey = sqlkey + s + ",";
				if(s.equals("Nickname")){
					Blob b = (Blob) m.invoke(t);
					sqlValue = sqlValue + "'" + b + "',";
				}else{
				String res=m.invoke(t).toString();
				if(res.contains("'")){
					res.replace("'", "");
				}
				sqlValue = sqlValue + "'" + res + "',";
				}
				
			}
			sql = sqlkey.substring(0, sqlkey.length() - 1) + ")" + sqlValue.substring(0, sqlValue.length() - 1) + ")";
			db1 = new DBHelper(sql);
//			if (collectionName.equals("ChatRoom") || collectionName.equals("GroupMember")|| collectionName.equals("FriendZone")|| collectionName.equals("Messages")) {
//				db1.pst.executeQuery("SET NAMES utf8mb4;");
//			}
//			System.out.print("\n");
//			System.out.println(sql);
			db1.pst.executeUpdate(sql);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ret != null) {
					ret.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			db1.close();
		}

	}
	
	public List<Caseinfo> getcaseListfromMysql(String searchname,String starttime,String endtime,String searchman,String searchlabel,String searchparty){
		DBHelper db1 = null;
		ResultSet ret = null;
		String sql = null;		
		List<Caseinfo> list=new ArrayList<Caseinfo>();		
		try{
			sql="select *from caseinfo where caseName LIKE "+"'%"+""+searchname+""+"%'"+" AND userName LIKE "+"'%"+""+searchman+""+"%'"+" AND createdTime between "+starttime+" AND "+endtime+""+"AND label LIKE "+"'%"+""+searchlabel+""+"%'"+"AND mainParty LIKE "+"'%"+""+searchparty+""+"%'";
//			System.out.println(sql);
			db1 = new DBHelper(sql);// 创建DBHelper对象
			ret = db1.pst.executeQuery(sql);// 执行语句，得到结果集			
			while (ret.next()) {
				Caseinfo c=new Caseinfo();
				c.setId(ret.getInt("id"));
				c.setCaseName(ret.getString("caseName"));
				c.setCaseNum(ret.getString("caseNum"));
				c.setCreatedTime(ret.getString("createdTime"));
				c.setInquireTime(ret.getString("inquireTime"));
				c.setLabel(ret.getString("label"));
				c.setMainParty(ret.getString("mainParty"));
				c.setRelatedCompany(ret.getString("ralatedCompany"));
				c.setRelatedObject(ret.getString("relatedObject"));
				c.setRemark(ret.getString("remark"));
				c.setStatus(ret.getString("status"));
				c.setTrustee(ret.getString("trustee"));
				c.setUserName(ret.getString("userName"));
				list.add(c);								
			}
		}catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if (ret != null) {
					ret.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}	
			db1.close();
		}
		return list;
	}
	
	/**
	 * 数据查询or
	 * 
	 * @param t
	 *            查询条件类
	 * @return 查询结果list
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getListfromMysqlLikTimeecaseOR(T t) {
		DBHelper db1 = null;
		ResultSet ret = null;
		String sql = null;
		String collectionName = t.getClass().getSimpleName();
		List<T> tList = new ArrayList<T>();
		sql = "SELECT * FROM " + collectionName;
		String value = "";
		boolean dateflag = false;
		try {
			Field[] fields = t.getClass().getDeclaredFields();

			for (Field field : fields) {
				String s = field.getName();
				System.out.println(s);
				if (s.equals("date")) {
					dateflag = true;
				}
				String methodName = "get" + s.substring(0, 1).toUpperCase() + s.substring(1);
				Method m = t.getClass().getDeclaredMethod(methodName);
				if (m.invoke(t) == null||m.invoke(t).equals("")) {
					continue;
				}
				if (field.getType().getSimpleName().equals("int") && (Integer) m.invoke(t) == -1) {
					continue;
				}
				//value = value + s + " like '%" + m.invoke(t).toString() + "%' and ";
				
				if(s=="label"){//标签的搜索     
					String label =  m.invoke(t).toString();
					String[] labels = label.split("\\s+");
					for(int i=0;i<labels.length;i++){
					    if(labels.length==1)
					    	value = value + s + " like '%" + labels[i] + "%' and ";
						else if(i==0)
							value = value +"("+ s + " like '%" + labels[i] + "%' or ";
						else if(i==labels.length-1){
							value = value + s + " like '%" + labels[i] + "%') and ";
						}
						else
							value = value + s + " like '%" + labels[i] + "%' or ";
					}
				}else if(s=="sheng"){//标签的搜索   
					String label =  m.invoke(t).toString();
					value = value + s + " like '%" + label + "%' or ";
				}
				else if(s=="shi"){//标签的搜索   
					String label =  m.invoke(t).toString();
					value = value + s + " like '%" + label + "%' "+ " and ";
					
				}
				else if(s=="userName"){//案件权限 科员 自己和被授权的
					String label =  m.invoke(t).toString();
					value = value + s + " like '%" + label + "%' "+ " or ";
					value = value + "trustee like '%" + label + "%' "+ " or ";
				}else if(s=="section"){//案件权限 科员 本科和被授权的
					String label =  m.invoke(t).toString();
					value = value + s + " like '%" + label.split("/")[0] + "%' "+ " or ";
					value = value + "trustee like '%" + label.split("/")[1] + "%' "+ " or ";
				}else{
					value = value + s + " like '%" + m.invoke(t).toString() + "%' and ";
				}
				
			}

			if (!value.equals("")) {
				sql = sql + " WHERE " + value.substring(0, value.length() - 5);
				
			}else{
				
			}
			
			if (dateflag) {
				sql = sql + " order by date desc";
			}
			
			db1 = new DBHelper(sql);// 创建DBHelper对象
			System.out.println("=========================================<<<<<"+sql);
//			System.out.println(sql);

//			if (collectionName.equals("ChatRoom") || collectionName.equals("GroupMember")||collectionName.equals("FriendZone")|| collectionName.equals("Messages")) {
//				db1.pst.executeQuery("SET NAMES utf8mb4;");
//			}

			ret = db1.pst.executeQuery(sql);// 执行语句，得到结果集
			if (ret != null) {
				while (ret.next()) {
					T t_temp = (T) t.getClass().newInstance();// com.xl.wechat.AutoReply

					for (Field field : fields) {// field=int

						String s = field.getName();// s=id
						String methodName = "set" + s.substring(0, 1).toUpperCase() + s.substring(1);// setId
						Method m = t_temp.getClass().getDeclaredMethod(methodName, field.getType());// void
																									// com.xl.wechat.bean.AutoReply.setId(int)
						String typename = field.getType().getSimpleName();// int
						if ((typename != null) && typename.equals("int")) {
							m.invoke(t_temp, ret.getInt(s));
						}

						if ((typename != null) && typename.equals("String")) {
							String retvalue = ret.getObject(s) == null ? "" : ret.getObject(s).toString();
							m.invoke(t_temp, retvalue);
						}

					}
					tList.add(t_temp);
				} // 显示数据
			}

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ret != null) {
					ret.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			db1.close();
		}
		return tList;
	}
	/**
	 * 时间范围内查询返回总条数
	 * 
	 * @param t
	 *            查询条件类
	 * @return 查询结果list
	 */
	public <T> int getListfromMysqlLikeTimecaseCountOR(T t,String starttime,String endtime) {
		DBHelper db1 = null;
		ResultSet ret = null;
		String sql = null;
		String collectionName = t.getClass().getSimpleName();
		sql = "SELECT count(*) FROM " + collectionName;
		String value = "";
		boolean dateflag = false;
		int size = 0;
		try {
			Field[] fields = t.getClass().getDeclaredFields();

			for (Field field : fields) {
				String s = field.getName();
				if (s.equals("date")) {
					dateflag = true;
				}
				String methodName = "get" + s.substring(0, 1).toUpperCase() + s.substring(1);
				Method m = t.getClass().getDeclaredMethod(methodName);
				if (m.invoke(t) == null||m.invoke(t).equals("")) {
					continue;
				}
				if (field.getType().getSimpleName().equals("int") && (Integer) m.invoke(t) == -1) {
					continue;
				}
				//value = value + s + " like '%" + m.invoke(t).toString() + "%' and ";
				
				if(s=="label"){//标签的搜索     
					String label =  m.invoke(t).toString();
					String[] labels = label.split("\\s+");
					for(int i=0;i<labels.length;i++){
					    if(labels.length==1)
					    	value = value + s + " like '%" + labels[i] + "%' and ";
						else if(i==0)
							value = value +"("+ s + " like '%" + labels[i] + "%' or ";
						else if(i==labels.length-1){
							value = value + s + " like '%" + labels[i] + "%') and ";
						}
						else
							value = value + s + " like '%" + labels[i] + "%' or ";
					}
				}else if(s=="sheng"){//标签的搜索   
					String label =  m.invoke(t).toString();
					value = value + s + " like '%" + label + "%' or ";
				}
				else if(s=="shi"){//标签的搜索   
					String label =  m.invoke(t).toString();
					value = value + s + " like '%" + label + "%' "+ " and ";
					
				}else if(s=="userName"){//案件权限 科员 自己和被授权的
					String label =  m.invoke(t).toString();
					value = value + s + " like '%" + label + "%' "+ " or ";
					value = value + "trustee like '%" + label + "%' "+ " or ";
				}else if(s=="section"){//案件权限 科员 本科和被授权的
					String label =  m.invoke(t).toString();
					value = value + s + " like '%" + label.split("/")[0] + "%' "+ " or ";
					value = value + "trustee like '%" + label.split("/")[1] + "%' "+ " or ";
				} else{
					value = value + s + " like '%" + m.invoke(t).toString() + "%' and ";
				}
				
			}

			if (!value.equals("")) {
				sql = sql + " WHERE " + value.substring(0, value.length() - 5);
				sql=sql+" AND createdTime between "+"'"+starttime+"'"+" AND "+"'"+endtime+"'";
			}else{
				sql=sql+" where createdTime between "+"'"+starttime+"'"+" AND "+"'"+endtime+"'";
			}
			
			if (dateflag) {
				sql = sql + " order by date desc";
			}
			db1 = new DBHelper(sql);// 创建DBHelper对象
//			System.out.println("=========================================<<<>>>"+sql);

//			if (collectionName.equals("ChatRoom") || collectionName.equals("GroupMember")||collectionName.equals("FriendZone")|| collectionName.equals("Messages")) {
//				db1.pst.executeQuery("SET NAMES utf8mb4;");
//			}

			ret = db1.pst.executeQuery(sql);// 执行语句，得到结果集
//			System.out.println(sql);
			if (ret.next()) {
				size = ret.getInt(1);
			}

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ret != null) {
					ret.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			db1.close();
		}
		return size;
	}
	public <T> int getcountfromMysqlLikeOR(T t) {
		DBHelper db1 = null;
		ResultSet ret = null;
		String sql = null;
		String collectionName = t.getClass().getSimpleName();
		sql = "SELECT count(*) FROM " + collectionName;
		String value = "";
		int size = 0;
		try {
			Field[] fields = t.getClass().getDeclaredFields();

			for (Field field : fields) {
				String s = field.getName();
				String methodName = "get" + s.substring(0, 1).toUpperCase() + s.substring(1);
				Method m = t.getClass().getDeclaredMethod(methodName);
				if (m.invoke(t) == null||m.invoke(t).equals("")) {
					continue;
				}
				if (field.getType().getSimpleName().equals("int") && (Integer) m.invoke(t) == -1) {
					continue;
				}
				
				if(s=="label"){//标签的搜索     
					String label =  m.invoke(t).toString();
					String[] labels = label.split("\\s+");
					for(int i=0;i<labels.length;i++){
					    if(labels.length==1)
					    	value = value + s + " like '%" + labels[i] + "%' and ";
						else if(i==0)
							value = value +"("+ s + " like '%" + labels[i] + "%' or ";
						else if(i==labels.length-1){
							value = value + s + " like '%" + labels[i] + "%') and ";
						}
						else
							value = value + s + " like '%" + labels[i] + "%' or ";
					}
				}else if(s=="userName"){//案件权限 科员 自己和被授权的
					String label =  m.invoke(t).toString();
					value = value + s + " like '%" + label + "%' "+ " or ";
					value = value + "trustee like '%" + label + "%' "+ " or ";
				}else if(s=="section"){//案件权限 科员 本科和被授权的
					String label =  m.invoke(t).toString();
					value = value + s + " like '%" + label.split("/")[0] + "%' "+ " or ";
					value = value + "trustee like '%" + label.split("/")[1] + "%' "+ " or ";
				}else{
					value = value + s + " like '%" + m.invoke(t).toString() + "%' and ";
				}
				
			}
			if (!value.equals("")) {
				sql = sql + " WHERE " + value.substring(0, value.length() - 5);
			}
			db1 = new DBHelper(sql);// 创建DBHelper对象
//			System.out.println("=========================================>>><<<"+sql);
//			System.out.println(sql);

//			if (collectionName.equals("ChatRoom") || collectionName.equals("GroupMember")||collectionName.equals("FriendZone")|| collectionName.equals("Messages")) {
//				db1.pst.executeQuery("SET NAMES utf8mb4;");
//			}

			ret = db1.pst.executeQuery(sql);// 执行语句，得到结果集
			if (ret.next()) {
				size = ret.getInt(1);
			}

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ret != null) {
					ret.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			db1.close();
		}
		return size;
	}
	@SuppressWarnings("unchecked")
	public <T> List<T> getOrderListfromMysqlLikeOR(T t, int limitstart, int linitsize, String orderKey) {
		DBHelper db1 = null;
		ResultSet ret = null;
		String sql = null;
		String collectionName = t.getClass().getSimpleName();
		List<T> tList = new ArrayList<T>();
		sql = "SELECT * FROM " + collectionName;
		String value = "";
		try {
			Field[] fields = t.getClass().getDeclaredFields();

			for (Field field : fields) {
				String s = field.getName();
				String methodName = "get" + s.substring(0, 1).toUpperCase() + s.substring(1);
				Method m = t.getClass().getDeclaredMethod(methodName);
				if (m.invoke(t) == null||m.invoke(t).equals("")) {
					continue;
				}
				if (field.getType().getSimpleName().equals("int") && (Integer) m.invoke(t) == -1) {
					continue;
				}
				
				if(s=="label"){//标签的搜索     
					String label =  m.invoke(t).toString();
					String[] labels = label.split("\\s+");
					for(int i=0;i<labels.length;i++){
					    if(labels.length==1)
					    	value = value + s + " like '%" + labels[i] + "%' and ";
						else if(i==0)
							value = value +"("+ s + " like '%" + labels[i] + "%' or ";
						else if(i==labels.length-1){
							value = value + s + " like '%" + labels[i] + "%') and ";
						}
						else
							value = value + s + " like '%" + labels[i] + "%' or ";
					}
				}else if(s=="userName"){//案件权限 科员 自己和被授权的
					String label =  m.invoke(t).toString();
					value = value + s + " like '%" + label + "%' "+ " or ";
					value = value + "trustee like '%" + label + "%' "+ " or ";
				}else if(s=="section"){//案件权限 科员 本科和被授权的
					String label =  m.invoke(t).toString();
					value = value + s + " like '%" + label.split("/")[0] + "%' "+ " or ";
					value = value + "trustee like '%" + label.split("/")[1] + "%' "+ " or ";
				}else{
					value = value + s + " like '%" + m.invoke(t).toString() + "%' and ";
				}

			}

			if (!value.equals("")) {
				sql = sql + " WHERE " + value.substring(0, value.length() - 5);
			}
			sql = sql + " order by "+orderKey+" desc limit " + limitstart + " , " + linitsize;
			//System.out.println("=========================================>>>>>"+sql);
			db1 = new DBHelper(sql);// 创建DBHelper对象

			if (collectionName.equals("ChatRoom") || collectionName.equals("GroupMember")||collectionName.equals("FriendZone")|| collectionName.equals("Messages")) {
				db1.pst.executeQuery("SET NAMES utf8mb4;");
			}

			ret = db1.pst.executeQuery(sql);// 执行语句，得到结果集
			if (ret != null) {
				while (ret.next()) {
					T t_temp = (T) t.getClass().newInstance();

					for (Field field : fields) {// field=int

						String s = field.getName();// s=id
						String methodName = "set" + s.substring(0, 1).toUpperCase() + s.substring(1);// setId
						Method m = t_temp.getClass().getDeclaredMethod(methodName, field.getType());// void
																									// com.xl.wechat.bean.AutoReply.setId(int)
						String typename = field.getType().getSimpleName();// int
						if ((typename != null) && typename.equals("int")) {
							m.invoke(t_temp, ret.getInt(s));
						}

						if ((typename != null) && typename.equals("String")) {
							String retvalue = ret.getObject(s) == null ? "" : ret.getObject(s).toString();
							m.invoke(t_temp, retvalue);
						}

					}
					tList.add(t_temp);
				} // 显示数据
			}

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ret != null) {
					ret.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			db1.close();
		}
		return tList;
	}
	
	
	public static void main(String[] args) throws InterruptedException {
//		long start = System.currentTimeMillis();
//		System.out.println(start);
//		Thread.sleep(1000);
//		long end = System.currentTimeMillis();
//		System.out.println(end);
//		System.out.println(end - start);
		Evidence evi = new Evidence();
		evi.setEvName("test201706261518");
		evi.setComment("测试客户端上传证据");
		evi.setEvAdmin("zhangsan");
		SqlDao sqlDao = new SqlDao();
		Integer in = sqlDao.setBeanToMysql(evi);
		System.out.println(in);
	}
}
