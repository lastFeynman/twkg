package twkg.controller;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twkg.dao.ICoverSongDao;
import twkg.dao.ISongDao;
import twkg.dao.IUserDao;
import twkg.dao.factory.DaoFactory;
import twkg.util.ConfigUtil;

/**
 * Servlet implementation class DeleteServlet
 */
@WebServlet("/admin/DeleteServlet")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String deleteUserIdStr = request.getParameter("deleteUserId");
		int deleteUserId = 0;
		String deleteSongIdStr = request.getParameter("deleteSongId");
		int deleteSongId = 0;
		String deleteCoverSongIdStr = request.getParameter("deleteCoverSongId");
		int deleteCoverSongId = 0;
		DaoFactory daoFactory = DaoFactory.getDaoFactory();
		IUserDao userDao = null;
		ISongDao songDao = null;
		ICoverSongDao coverSongDao = null;
		if(deleteUserIdStr!=null) {
			try {
				deleteUserId = Integer.parseInt(deleteUserIdStr);
			} catch (Exception e) {
				request.setAttribute("errMsg", "ÇëÇó²ÎÊý´íÎó:deleteUserId");
				request.getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
				return;
			}
			userDao = daoFactory.getUserDao();
			if(userDao.delete(deleteUserId)) {
				String servPath = request.getServletContext().getRealPath("/");
				File avatar = new File(servPath+ConfigUtil.USER_AVATAR_PATH.substring(6)+deleteUserId+".jpg");
				if(avatar.exists())
					avatar.delete();
				request.setAttribute("statusMsg", "ÓÃ»§É¾³ý³É¹¦");
			}
			else
				request.setAttribute("statusMsg", "ÓÃ»§É¾³ýÊ§°Ü");
		}else if(deleteSongIdStr!=null) {
			try {
				deleteSongId = Integer.parseInt(deleteSongIdStr);
			} catch (Exception e) {
				request.setAttribute("errMsg", "ÇëÇó²ÎÊý´íÎó:deleteSongId");
				request.getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
				return;
			}
			songDao = daoFactory.getSongDao();
			if(songDao.delete(deleteSongId)) {
				String servPath = request.getServletContext().getRealPath("/");
				File songFile = new File(servPath+ConfigUtil.SONG_PATH.substring(6)+deleteSongId+".mp3");
				File thumbnailFile = new File(servPath+ConfigUtil.THUMBNAIL_PATH.substring(6)+deleteSongId+".jpg");
				if(songFile.exists())
					songFile.delete();
				if(thumbnailFile.exists())
					thumbnailFile.delete();
				request.setAttribute("statusMsg", "¸èÇúÉ¾³ý³É¹¦");
			}else
				request.setAttribute("statusMsg", "¸èÇúÉ¾³ýÊ§°Ü");
		}else if(deleteCoverSongIdStr!=null) {
			try {
				deleteCoverSongId = Integer.parseInt(deleteCoverSongIdStr);
			} catch (Exception e) {
				request.setAttribute("errMsg", "ÇëÇó²ÎÊý´íÎó:deleteCoverSongId");
				request.getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
				return;
			}
			coverSongDao = daoFactory.getCoverSongDao();
			if(coverSongDao.delete(deleteCoverSongId)) {
				String servPath = request.getServletContext().getRealPath("/");
				File coverFile = new File(servPath+ConfigUtil.COVER_SONG_PATH.substring(6)+deleteCoverSongId+".mp3");
				if(coverFile.exists())
					coverFile.delete();
				request.setAttribute("statusMsg", "·­³ªÉ¾³ý³É¹¦");
			}else
				request.setAttribute("statusMsg", "·­³ªÉ¾³ýÊ§°Ü");
		}
		request.getServletContext().getRequestDispatcher("/admin/status.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
