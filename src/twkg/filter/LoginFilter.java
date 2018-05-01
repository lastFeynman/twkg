package twkg.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import twkg.dao.ICookieDao;
import twkg.dao.IUserDao;
import twkg.dao.factory.DaoFactory;
import twkg.entity.User;
import twkg.util.ConfigUtil;

/**
 * LoginFilter
 */
public class LoginFilter implements Filter {
	private FilterConfig config = null;
	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		config = null;
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		String uri = req.getRequestURI();
		String contextPath = req.getContextPath();
		uri = uri.substring(contextPath.length()+1);
		//跳过css、js、png、jpg、mp3、lrc的过滤
		if(uri.endsWith("js")||uri.endsWith("css")||uri.endsWith("png")||uri.endsWith("jpg")||uri.endsWith("mp3")||uri.endsWith("lrc")) {
			chain.doFilter(request, response);
			return;
		}
		//获取不需要登录可以访问的uri
		String[] noLogin = config.getInitParameter("noLogin").split(";");
		boolean isNoLogin = false;
		for(int i=0;i<noLogin.length;i++) {
			if(noLogin[i].equals(uri)) {
				isNoLogin=true;
				break;
			}
		}
		if(!isNoLogin) {
			HttpSession session = req.getSession();
			if(session.getAttribute("currentUser") == null) {
				//尝试自动登录
				Cookie[] cookies = req.getCookies();
				int cookieIndex;
				for(cookieIndex=0;cookies!=null&&cookieIndex<cookies.length;cookieIndex++) {
					if(cookies[cookieIndex].getName().equals(ConfigUtil.COOKIE_NAME)) {
						break;
					}
				}
				if(cookies==null || cookieIndex == cookies.length) {
					req.getServletContext().getRequestDispatcher("/login.jsp").forward(req, res);
					return;
				}
				
				DaoFactory daoFactory = DaoFactory.getDaoFactory();
				ICookieDao cookieDao = daoFactory.getCookieDao();
				twkg.entity.Cookie cookie = null;
				
				if((cookie = cookieDao.findCookieByCookieValue(cookies[cookieIndex].getValue())) == null || !cookie.isAutoLogin()) {
					req.getServletContext().getRequestDispatcher("/login.jsp").forward(req, res);
					return;
				}
				
				IUserDao userDao = daoFactory.getUserDao();
				User currentUser = null;
				if((currentUser = userDao.findUserByUserId(cookie.getUserId())) == null) {
					req.getServletContext().getRequestDispatcher("/login.jsp").forward(req, res);
					return;
				}
				session.setAttribute("currentUser", currentUser);
				if(uri.equals("login.jsp")) {
					req.getServletContext().getRequestDispatcher("/index.jsp").forward(req, res);
					return;
				}
			}
		}
		
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		this.config = fConfig;
	}

}
