package twkg.controller;

import java.io.File;
import java.io.IOException;
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

import twkg.util.ConfigUtil;

/**
 * Servlet implementation class ChangeAvatar
 */
@WebServlet("/ChangeAvatar")
public class ChangeAvatar extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final long MAX_FILE_SIZE = 20 * 1024 * 1024;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangeAvatar() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(MAX_FILE_SIZE);
		String userId = null;	
		try {
			List<FileItem> fileItems = upload.parseRequest(request);
			Iterator<FileItem> iterator = fileItems.iterator();
			while(iterator.hasNext()) {
				FileItem fileItem = iterator.next();
				if(fileItem.isFormField() && fileItem.getFieldName().equals("userid") && fileItem.getSize()>0) {
					userId = fileItem.getString();
				}
			}
			iterator = fileItems.iterator();
			File saveFile = new File(request.getSession().getServletContext().getRealPath("/")+ConfigUtil.USER_AVATAR_PATH.substring(6)+userId+".jpg");
			while(iterator.hasNext()) {
				FileItem fileItem = iterator.next();
				if(!fileItem.isFormField() && fileItem.getFieldName().equals("uploadImg")) {
					fileItem.write(saveFile);
				}
			}
		}catch(Exception e){
			request.setAttribute("errMsg", "Í·ÏñÐÞ¸ÄÊ§°Ü");
			request.getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
			return;
		}
		
		response.sendRedirect("/twkg/personal.jsp");
	}

}
