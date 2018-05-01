package twkg.controller;

import java.io.IOException;
import java.util.ArrayList;
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
 * Servlet implementation class SearchServlet
 */
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int MAX_ROW = 13;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

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
		int resultCount=0;
		
		DaoFactory daoFactory = DaoFactory.getDaoFactory();
		ISongDao songDao = null;
		ICoverSongDao coverSongDao = null;
		IUserDao userDao = null;
		if(searchType == null || searchType.equals("song")) {
			songDao = daoFactory.getSongDao();
			if(maxPage==0) {
				resultCount = songDao.findSongCountBySongNameAndSingerName(searchContent);
				if(resultCount%MAX_ROW!=0 || resultCount==0)
					maxPage = resultCount/MAX_ROW+1;
				else
					maxPage = resultCount/MAX_ROW;
			}
			List<Song> songs = songDao.findSongBySongNameAndSingerName(searchContent,(currentPage-1)*MAX_ROW,MAX_ROW);
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
			songDao = daoFactory.getSongDao();
			userDao = daoFactory.getUserDao();
			List<CoverSong> coverSongs = coverSongDao.findCoverSongBySongNameAndSingerName(searchContent,(currentPage-1)*MAX_ROW,MAX_ROW);
			List<Song> songs = new ArrayList<>();
			Song song = null;
			List<User> users = new ArrayList<>();
			User user;
			for(int i=0;i<coverSongs.size();i++) {
				song = songDao.findSongBySongId(coverSongs.get(i).getSongId());
				songs.add(song);
				user = userDao.findUserByUserId(coverSongs.get(i).getUserId());
				users.add(user);
			}
			request.setAttribute("coverSongs", coverSongs);
			request.setAttribute("songs", songs);
			request.setAttribute("users", users);
		}
		
		request.setAttribute("maxPage", maxPage);
		request.getServletContext().getRequestDispatcher("/searchResult.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
