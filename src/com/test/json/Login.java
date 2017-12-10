package com.test.json;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.test.model.DB;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 输出流
		PrintWriter out = response.getWriter();
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		// 获取pid
		int status = -1;
		int pid = -1;
		try {
			pid = DB.getId(name);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (pid == -1) {
			status = -1;
		} else {
			// 获取数据库的密码
			String passwordDB = "";
			try {
				passwordDB = DB.getPassword(pid);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (password.equals(passwordDB)) {
				status = pid;
				Cookie pidCookie = new Cookie("pid", String.valueOf(pid));
				pidCookie.setMaxAge(7200);
				response.addCookie(pidCookie);
			}
		}
		out.print(status);
		out.flush();

	}

}
