package twkg.controller;
import java.io.IOException;
import java.sql.Date;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import twkg.dao.IUserDao;
import twkg.dao.factory.DaoFactory;
import twkg.entity.User;

@WebServlet("/Change_Personal_InfoServlet")
public class Change_Personal_InfoServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Change_Personal_InfoServlet() {
		super();
		// TODO Auto-generated constructor stub
	}	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("currentUser");
		
		if(user==null) {
			request.setAttribute("errMsg", "登录已过期，请重新登录");
			request.getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
			return;
		}

		response.setContentType("text/html;charset=UTF-8");

		String username = new String(request.getParameter("uname").getBytes("ISO8859-1"), "UTF-8");
		String useremail = request.getParameter("uemail");
		String userbirth = request.getParameter("ubirth");
		String usergender = request.getParameter("ugender");
		String userrealname = request.getParameter("urealname");
		String userbio = request.getParameter("ubio");
		
		if(!username.equals(""))
			user.setUserName(username);
		
		if(!useremail.equals(""))
			user.setUserEmail(useremail);
		
		if(!userbirth.equals(""))
			user.setUserBirth(strToDate(userbirth));
		
		if(!usergender.equals(""))
			user.setUserGender(usergender);
		
		if(!userrealname.equals(""))
			user.setUserRealName(userrealname);
		
		if(!userbio.equals(""))
			user.setUserBio(userbio);

		DaoFactory daoFactory = DaoFactory.getDaoFactory();
		IUserDao userDao = daoFactory.getUserDao();
		if(userDao.update(user)) {
			response.sendRedirect("/twkg/personal.jsp");
		}
		else {
			request.setAttribute("errMsg", "修改信息失败");
			request.getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}
	
	
	
	 public static Date strToDate(String strDate) {
		     SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		     ParsePosition pos = new ParsePosition(0);
		     Date strtodate = (Date) formatter.parse(strDate, pos);
		     return strtodate;
		  }
}
