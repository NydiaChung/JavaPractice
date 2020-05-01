/**   
 * Copyright © 2020 zongyue All rights reserved.
 * 
 * 功能描述：
 * @Package: db 
 * @author: zongyue   
 * @date: 2020年4月16日 下午12:43:48 
 */
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.PropertyResourceBundle;

/**   
* Copyright: Copyright (c) 2020
*
* Date         Author          Version           ClassName 
*---------------------------------------------------------*
* 2020年4月16日     zongyue           v1.0.0           DBmanager.java    
*
* @Description: 
*/
public class DBmanager {
	private static String url; // 数据库连接字串
	private static String userName; // 数据库用户名称
	private static String driver; // 数据库驱动名称
	private static String pwd; // 数据库用户登陆密码
	private final static String fileName = "database"; // 属性文件名称
	public static final int PAGESIZE = 5;  
	// ThreadLocal 当前线程局部变量
	@SuppressWarnings("rawtypes")
	private static ThreadLocal connection = new ThreadLocal();

	
	public DBmanager(){}
	
	//静态代码块在类加载时执行并且只执行一次
	static {
		// PropertyResourceBundle使用属性文件中的静态字符串集合来管理语言环境资源。
		PropertyResourceBundle prb = (PropertyResourceBundle) PropertyResourceBundle.getBundle(fileName);
		// 枚举Enumeration
		Enumeration enu = prb.getKeys();
		while (enu.hasMoreElements()) {
			String propertyName = enu.nextElement().toString();
			// 读取配置文件中的静态字符串并且赋值给类成员变量
			if (propertyName.equals("database.driver"))
				driver = prb.getString("database.driver");
			if (propertyName.equals("database.url"))
				url = prb.getString("database.url");
			if (propertyName.equals("database.username"))
				userName = prb.getString("database.username");
			if (propertyName.equals("database.password"))
				pwd = prb.getString("database.password");
		}
		System.out.println("readConfig");
	}

	// getConn方法用于获取数据库连接
	/**
	 * synchronized 控制对类成员变量的访问：每个类实例对应一把锁， 每个 synchronized
	 * 方法都必须获得调用该方法的类实例的锁方能执行， 否则所属线程阻塞，方法一旦执行，就独占该锁，直到从该方法返回时才
	 * 将锁释放，此后被阻塞的线程方能获得该锁，重新进入可执行状态。这种机制 确保了同一时刻对于每一个类实例，其所有声明为 synchronized
	 * 的成员函数 中至多只有一个处于可执行状态（因为至多只有一个能够获得该类实例对应的锁）， 从而有效避免了类成员变量的访问冲突
	 */
	@SuppressWarnings("unchecked")
	public synchronized static Connection getConn() throws SQLException {
		//readConfig();
//		Connection con = (Connection) connection.get();
//		if (con != null && !con.isClosed()) {
//			return con;
//		}
		try {
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, userName, pwd);
			// setAutoCommit
			// 将此连接的自动提交模式设置为给定状态。如果连接处于自动提交模式下，则将执行其所有 SQL 语句，并将这些语句作为单独的事务提交。
			// 否则，其 SQL 语句将成组地进入通过调用 commit 方法或 rollback
			// 方法终止的事务中。默认情况下，新的连接处于自动提交模式下。
			connection.set(con);
			System.out.println("数据库已连接");
			return con;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("无法连接数据库");
		}
		return null;
	}

}
