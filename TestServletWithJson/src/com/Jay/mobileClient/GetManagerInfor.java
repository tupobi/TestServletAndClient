package com.Jay.mobileClient;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Jay.biz.ManagerBiz;
import com.Jay.entity.Manager;

public class GetManagerInfor extends HttpServlet {
	private Map managerMap;
	private String result;
	private ManagerBiz managerBiz;
	
	/**
	 * Constructor of the object.
	 */
	public GetManagerInfor() {
		super();
		managerMap = new HashMap();
		managerBiz = new ManagerBiz();
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
	
	public String productJsonData(){
		List<Manager> managers = managerBiz.getManagerInforFromDB();
////		for(int i=0; i<managers.size(); i++){
////			System.out.println(i);
////			managerMap.put("uname", managers.get(i).getUname());
////			managerMap.put("upwd", managers.get(i).getUpwd());
////		}
//		managerMap.put("managers", managers);
//		
//		JsonConfig config = new JsonConfig();
//		JSONObject jsonObject = JSONObject.fromObject(managerMap, config);
//		return jsonObject.toString();
//		//左边参数是要转换的数据，右边是数据的配置信息，包括屏蔽某些信息。
		
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		
		//双引号引起变量需要加\"表示双引号，不方便
//		for(Manager manager : managers){
//			sb.append("{").append("\"uname\":").append("\""+manager.getUname()+"\"").append(",");
//			sb.append("\"upwd\":").append("\""+manager.getUpwd()+"\"");
//			sb.append("}").append(",");
//		}
//		sb.deleteCharAt(sb.length()-1);
//		//去掉最后一个逗号。
//		sb.append("]");
//		return new String(sb);
		
		//单引号引起变量，不用转义字符，方便一些。
		for(Manager manager : managers){
			sb.append("{").append("'uname':").append("'"+manager.getUname()+"'").append(",");
			sb.append("'upwd':").append("'"+manager.getUpwd()+"'");
			sb.append("}").append(",");
		}
		sb.deleteCharAt(sb.length()-1);
		//去掉最后一个逗号。
		sb.append("]");
		return new String(sb);
		
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
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		out.write(productJsonData());
		out.flush();
		out.close();
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
