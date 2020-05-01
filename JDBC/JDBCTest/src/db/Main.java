/**   
 * Copyright © 2020 zongyue All rights reserved.
 * 
 * 功能描述：
 * @Package: db 
 * @author: zongyue   
 * @date: 2020年4月16日 下午12:46:14 
 */
package db;

import java.sql.Connection;
import java.sql.SQLException;

/**   
* Copyright: Copyright (c) 2020
*
* Date         Author          Version           ClassName 
*---------------------------------------------------------*
* 2020年4月16日     zongyue           v1.0.0           Main.java    
*
* @Description: 
*/
public class Main {
	public static void main(String[] args) {
		try {
			Connection connection = DBmanager.getConn();
			System.out.println("调用方法");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("wufalianjieshujuku");
		}
	}
}
