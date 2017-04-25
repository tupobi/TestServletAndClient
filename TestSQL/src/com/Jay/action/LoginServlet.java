package com.Jay.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Jay.biz.ManagerBiz;
import com.Jay.entity.Manager;

public class LoginServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public LoginServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("1111111");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
//		response.setContentType("text/html");
		
		String uname = request.getParameter("uname");
		String upwd = request.getParameter("upwd");
		
		ManagerBiz managerBiz = new ManagerBiz();
		Manager manager = managerBiz.login(uname, upwd);
		System.out.println("22222222222222222");
		if(manager!=null){
			//使用RequestDispatcher，进行servlet页面跳转
			System.out.println("33");
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
			//请求转发不用绝对路径，但jsp文件前面加/
			requestDispatcher.forward(request, response);
		}else{
			System.out.println("555555555");
			response.sendRedirect("/TestSQL/login.jsp");
			//重新定向，不传递request数据。还在原页。
			//重新定向需要用绝对路径，且项目路径前需要加/
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
