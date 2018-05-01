package twkg.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import twkg.dao.ICoverSongDao;
import twkg.dao.factory.DaoFactory;
import twkg.entity.CoverSong;
import twkg.util.ConfigUtil;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final long MAX_FILE_SIZE = 20 * 1024 * 1024;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int songId;
		int userId;
		try {
			songId = Integer.parseInt(request.getParameter("songId"));
			userId = Integer.parseInt(request.getParameter("userId"));
		} catch (Exception e) {
			request.setAttribute("errMsg", "缺少参数");
			request.getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
			return;
		}
		
		CoverSong coverSong = new CoverSong();
		coverSong.setUserId(userId);
		coverSong.setSongId(songId);
		coverSong.setSingTime(new Timestamp(new Date().getTime()));
		coverSong.setCoverPlayCount(0);
		
		DaoFactory daoFactory = DaoFactory.getDaoFactory();
		ICoverSongDao coverSongDao = daoFactory.getCoverSongDao();
		if(!coverSongDao.insert(coverSong)) {
			request.setAttribute("errMsg", "出错");
			request.getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
			return;
		}
		
		if((coverSong = coverSongDao.findLastCoverSong(userId,songId)) == null) {
			request.setAttribute("errMsg", "出错");
			request.getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
			return;
		}
		File saveFile = new File(request.getSession().getServletContext().getRealPath("")+ConfigUtil.COVER_SONG_PATH.substring(5)+coverSong.getCoverSongId()+".mp3");
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(MAX_FILE_SIZE);
		
		try {
			List<FileItem> fileItems = upload.parseRequest(request);
			Iterator<FileItem> iterator = fileItems.iterator();
			while(iterator.hasNext()) {
				FileItem fileItem = iterator.next();
				if(!fileItem.isFormField()) {
					fileItem.write(saveFile);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errMsg", "出错");
			request.getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
			return;
		}
	}

}
