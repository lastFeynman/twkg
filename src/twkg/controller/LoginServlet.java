package twkg.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import twkg.dao.ICookieDao;
import twkg.dao.IUserDao;
import twkg.dao.factory.DaoFactory;
import twkg.entity.User;
import twkg.util.ConfigUtil;
import twkg.util.EncryptUtil;

/**
 * Servlet implementation class login
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
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
		
		response.setContentType("text/html;charset=UTF-8");
		
		//获取传过来的登录信息
		String username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
		String password = request.getParameter("password");
		request.setAttribute("username", username);

		String autologin=request.getParameter("autologin");
		
		User user = new User();
		user.setUserName(username);
		
		DaoFactory daoFactory = DaoFactory.getDaoFactory();
		IUserDao userDao = daoFactory.getUserDao();
		user=userDao.findUserByUserName(user.getUserName());
		if(user==null) {
			response.sendRedirect("./login.jsp?error=1");
			return;
		}
		String key = user.getUserKey();
		
		if (EncryptUtil.verifyPassword(password, user.getUserId(), key)) {//验证用户名和密码是否对应
			
			//添加Cookie
			Cookie twkgCookie=new Cookie(ConfigUtil.COOKIE_NAME, null);
			twkgCookie.setPath("/twkg");
			twkgCookie.setValue(EncryptUtil.encryptCookie(user.getUserId()));
			twkgCookie.setMaxAge(ConfigUtil.COOKIE_EXPIRED*24*60*60);
			response.addCookie(twkgCookie);
			
			//往数据库中添加Cookie
			ICookieDao cookieDao=daoFactory.getCookieDao();
			twkg.entity.Cookie cookie=new twkg.entity.Cookie();
			Date date=new Date();
			cookie.setUserId(user.getUserId());
			cookie.setCookieValue(twkgCookie.getValue());
			cookie.setCookieTime(new java.sql.Date(date.getTime()));
			if(autologin!=null){
				cookie.setAutoLogin(true);
			}else{
				cookie.setAutoLogin(false);
			}
			cookieDao.insert(cookie);
			
			//设置session
			HttpSession session=request.getSession();
			session.setAttribute("currentUser", user);
			
			//跳转
			if(user.isAdmin())
				response.sendRedirect("/twkg/admin/adminIndex.jsp");
			else
				response.sendRedirect("/twkg/index.jsp");
		} else {
			//验证错误返回
			response.sendRedirect("./login.jsp?error=1");
		}
	}

}
