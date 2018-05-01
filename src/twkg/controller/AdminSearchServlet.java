package twkg.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twkg.dao.ICoverSongDao;
import twkg.dao.ISongDao;
import twkg.dao.IUserDao;
import twkg.dao.factory.DaoFactory;
import twkg.entity.CoverSong;
import twkg.entity.Song;
import twkg.entity.User;

/**
 * Servlet implementation class AdminSearchServlet
 */
@WebServlet("/admin/AdminSearchServlet")
public class AdminSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int MAX_ROW = 20;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String searchType = request.getParameter("searchType");
		String searchContent = (String)request.getAttribute("searchContent");
		int currentPage = (int)request.getAttribute("currentPage");
		if(currentPage<=0)
			currentPage=1;
		int maxPage = (int)request.getAttribute("maxPage");
		
		DaoFactory daoFactory = DaoFactory.getDaoFactory();
		IUserDao userDao = null;
		ISongDao songDao = daoFactory.getSongDao();
		ICoverSongDao coverSongDao = daoFactory.getCoverSongDao();
		int resultCount = 0;
		if(searchType.equals("user")) {
			userDao = daoFactory.getUserDao();
			if(maxPage==0) {
				resultCount = userDao.findUserCountByUserNameAndUserRealName(searchContent);
				if(resultCount%MAX_ROW!=0 || resultCount==0)
					maxPage = resultCount/MAX_ROW+1;
				else
					maxPage = resultCount/MAX_ROW;
			}
			List<User> users = userDao.findUserByUserNameAndUserRealName(searchContent, (currentPage-1)*MAX_ROW, MAX_ROW);
			request.setAttribute("searchType", "user");
			request.setAttribute("users", users);
		}else if(searchType.equals("song")) {
			songDao = daoFactory.getSongDao();
			if(maxPage==0) {
				resultCount = songDao.findSongCountBySongNameAndSingerName(searchContent);
				if(resultCount%MAX_ROW!=0 || resultCount==0)
					maxPage = resultCount/MAX_ROW+1;
				else
					maxPage = resultCount/MAX_ROW;
			}
			List<Song> songs = songDao.findSongBySongNameAndSingerName(searchContent, (currentPage-1)*MAX_ROW, MAX_ROW);
			request.setAttribute("searchType", "song");
			request.setAttribute("songs", songs);
		}else {
			coverSongDao = daoFactory.getCoverSongDao();
			if(maxPage==0) {
				resultCount = coverSongDao.findCoverSongCountBySongNameAndSingerName(searchContent);
				if(resultCount%MAX_ROW!=0 || resultCount==0)
					maxPage = resultCount/MAX_ROW+1;
				else
					maxPage = resultCount/MAX_ROW;
			}
			List<CoverSong> coverSongs = coverSongDao.findCoverSongBySongNameAndSingerName(searchContent, (currentPage-1)*MAX_ROW, MAX_ROW);
			request.setAttribute("searchType", "cover");
			request.setAttribute("coverSongs", coverSongs);
		}
		
		request.setAttribute("maxPage", maxPage);
		request.getServletContext().getRequestDispatcher("/admin/adminSearch.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
