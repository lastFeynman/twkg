package twkg.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import twkg.dao.ISongDao;
import twkg.dao.factory.DaoFactory;
import twkg.entity.Song;
import twkg.util.ConfigUtil;

@WebServlet("/admin/EditSongServlet")
public class EditSongServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final long MAX_UPLOAD_SIZE = 50*1024*1024;
       
	/**
     * @see HttpServlet#HttpServlet()
     */
    public EditSongServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Wrong Request Method");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String editSongIdStr = null;
		String Song = null;
		String Singer = null;
		String ct = null;
		Song so=new Song();
		//转换请求参数
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(MAX_UPLOAD_SIZE);
		List<FileItem> fileItems = null;
		Iterator<FileItem> iterator = null;
		Map<String, String> params = new HashMap<>();
		
		try {
			fileItems = upload.parseRequest(request);
			iterator = fileItems.iterator();
			while(iterator.hasNext()) {
				FileItem fileItem = iterator.next();
				if(fileItem.isFormField()) {
					params.put(fileItem.getFieldName(), new String(fileItem.getString().getBytes("ISO8859-1"),"UTF-8"));
				}
			}
		} catch (Exception e) {
			request.setAttribute("statusMsg", "转换请求参数出错");
		}
		
		editSongIdStr = params.get("editSongIdStr");
		Song = params.get("songName");
		Singer = params.get("Singer");
		ct = params.get("Createtime");
		
		int editSongId = 0;
		if(editSongIdStr!=null) {
			try {
				editSongId = Integer.parseInt(editSongIdStr);
			} catch (Exception e) {
				request.setAttribute("errMsg", "请求参数错误:editSongId");
				request.getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
				return;
			}
		}
		
		
		if(editSongId!=0)
			so.setSongId(editSongId);
		so.setSongName(Song);
		so.setSingerName(Singer);		
		if(!ct.equals("请选择创作时间")) {
			SimpleDateFormat sd=new SimpleDateFormat("MM/dd/yyyy");
			Date date=null;
			try {
				date = new Date(sd.parse(ct).getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			so.setCreateTime(date);
		}
		
		DaoFactory daoFactory=DaoFactory.getDaoFactory();
		ISongDao songDao=daoFactory.getSongDao();
		if(editSongId==0) {
			if(songDao.insert(so)) {
				request.setAttribute("statusMsg", "歌曲添加成功");
			}else {
				request.setAttribute("statusMsg", "歌曲添加失败");
			}
		}else {
			if(songDao.update(so)) {
				request.setAttribute("statusMsg", "歌曲修改成功");
			}else {
				request.setAttribute("statusMsg", "歌曲修改失败");
			}
		}
		
		so = songDao.findLastSongBySongName(Song);
		if(so!=null && (request.getAttribute("statusMsg").equals("歌曲添加成功") || request.getAttribute("statusMsg").equals("歌曲修改成功"))) {
			File songFile = new File(request.getServletContext().getRealPath("/")+ConfigUtil.SONG_PATH.substring(6)+so.getSongId()+".mp3");
			File thumbnailFile = new File(request.getServletContext().getRealPath("/")+ConfigUtil.THUMBNAIL_PATH.substring(6)+so.getSongId()+".jpg");
			File lyricFile = new File(request.getServletContext().getRealPath("/")+ConfigUtil.LYRIC_PATH.substring(6)+so.getSongId()+".lrc");
			try {
				iterator = fileItems.iterator();
				while(iterator.hasNext()) {
					FileItem fileItem = iterator.next();
					if(!fileItem.isFormField() && fileItem.getSize()>0) {
						if(fileItem.getFieldName().equals("songFile")) {
							fileItem.write(songFile);
						}else if(fileItem.getFieldName().equals("thumbnailFile")) {
							fileItem.write(thumbnailFile);
						}else if(fileItem.getFieldName().equals("lyricFile")) {
							fileItem.write(lyricFile);
						}
					}else if(fileItem.isFormField()){
						
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("uploadMsg", "文件上传失败");
			}
		}
		
		request.getRequestDispatcher("/admin/status.jsp").forward(request, response);
	}

}
