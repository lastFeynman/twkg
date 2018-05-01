package twkg.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import twkg.dao.ISongDao;
import twkg.dao.factory.DaoFactory;
import twkg.entity.Song;

public class RecordFilter implements Filter {

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		
		int recordSong = 0;
		if(req.getParameter("recordSong")!=null) {
			recordSong = Integer.parseInt(req.getParameter("recordSong"));
		}else {
			req.setAttribute("errMsg", "缺少请求参数");
			req.getServletContext().getRequestDispatcher("/error.jsp").forward(req, res);
			return;
		}
		DaoFactory daoFactory = DaoFactory.getDaoFactory();
		ISongDao songDao = daoFactory.getSongDao();
		
		Song song = songDao.findSongBySongId(recordSong);
		if(song == null) {
			req.setAttribute("errMsg", "请求参数出错");
			req.getServletContext().getRequestDispatcher("/error.jsp").forward(req, res);
			return;
		}
		song.setCoverCount(song.getCoverCount()+1);
		if(!songDao.update(song)) {
			System.out.println("更新失败");
		}
		
		HttpSession session = req.getSession();
		session.setAttribute("recordSong", song);
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
