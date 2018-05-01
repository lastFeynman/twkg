package twkg.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twkg.dao.IUserDao;
import twkg.dao.factory.DaoFactory;
import twkg.entity.User;
import twkg.util.ConfigUtil;
import twkg.util.EncryptUtil;


@WebServlet("/admin/EditUserServlet")
public class EditUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditUserServlet() {
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
		String editUserIdStr = request.getParameter("editUserId");
		int editUserId = 0;
		if(editUserIdStr!=null) {
			editUserId = Integer.parseInt(editUserIdStr);
		}
		String useru=new String(request.getParameter("usern").getBytes("ISO8859-1"),"UTF-8") ;
		String pas=request.getParameter("pas");
		String name=new String(request.getParameter("name").getBytes("ISO8859-1"),"UTF-8") ;
		String adm=request.getParameter("isAdmin");
		String sex=request.getParameter("gender");
		String mail=request.getParameter("mail");
		String gender=null;
		if(sex.equals("man"))
			gender="男";
		else if(sex.equals("woman"))
			gender="女";
		
		boolean isAdmin=false;
		if(adm.equals("is"))
			isAdmin=true;
		
		DaoFactory daoFactory=DaoFactory.getDaoFactory();
		IUserDao UserDao=daoFactory.getUserDao();
		User us = null;
		if(editUserId==0)
			us=new User();
		else
			us = UserDao.findUserByUserId(editUserId);
		
		us.setUserName(useru);
		if(!pas.equals("")&&editUserId==0)
			us.setUserKey("假装我是密码");
		else if(!pas.equals("")) {
			us.setUserKey(EncryptUtil.encrypt(pas, editUserId));
		}
		
		if(!name.equals(""))
			us.setUserRealName(name);
		
		if(!mail.equals(""))
			us.setUserEmail(mail);
		
		us.setAdmin(isAdmin);
		us.setUserGender(gender);
		
		if(editUserId==0)
			us.setRegisterTime(new Timestamp(new Date().getTime()));
		
		
		
		User existUserName = UserDao.findUserByUserName(useru);
		User existUserEmail = UserDao.findUserByUserEmail(mail);
		if((editUserId==0 && (existUserName!=null || existUserEmail!=null)) || 
				(editUserId!=0 && ((existUserName!=null && existUserName.getUserId()!=editUserId) || (existUserEmail!=null && existUserEmail.getUserId()!=editUserId)))) {
			if(editUserId==0) {
				request.setAttribute("statusMsg", "添加失败：用户名或邮件地址重复");
			}else {
				request.setAttribute("statusMsg", "修改失败：用户名或邮件地址重复");
			}
			request.getServletContext().getRequestDispatcher("/admin/status.jsp").forward(request, response);
			return;
		}
		if(editUserId==0) {
			if(UserDao.insert(us)) {
				us = UserDao.findUserByUserName(useru);
				//加密密码
				us.setUserKey(EncryptUtil.encrypt(pas, us.getUserId()));
				if(!UserDao.update(us)) {
					request.setAttribute("statusMsg", "用户添加失败");
					request.getServletContext().getRequestDispatcher("/admin/status.jsp").forward(request, response);
					return;
				}
					
				
				String servPath = request.getServletContext().getRealPath("/");
				us = UserDao.findUserByUserName(useru);
				File avatarFile = new File(servPath+ConfigUtil.USER_AVATAR_PATH.substring(6)+us.getUserId()+".jpg");
				File defaultAvatar = new File(servPath+ConfigUtil.USER_AVATAR_PATH.substring(6)+"0.jpg");
				FileInputStream fis = null;
				FileOutputStream fos = null;
				
				try {
					if(!avatarFile.exists()) {
						avatarFile.createNewFile();
					}
					fis = new FileInputStream(defaultAvatar);
					fos = new FileOutputStream(avatarFile);
					
					byte[] buffer = new byte[2048];
					int len;
					while((len=fis.read(buffer))>0) {
						fos.write(buffer, 0, len);
					}
					fos.flush();
				}catch (Exception e) {
					// TODO: handle exception
				}finally {
					if(fis!=null)
						fis.close();
					if(fos!=null)
						fos.close();
				}
				request.setAttribute("statusMsg", "用户添加成功");
			}else {
				request.setAttribute("statusMsg", "用户添加失败");
			}
		}else {
			if(UserDao.update(us)) {
				request.setAttribute("statusMsg", "用户修改成功");
			}else {
				request.setAttribute("statusMsg", "用户修改失败");
			}
		}
        request.getRequestDispatcher("/admin/status.jsp").forward(request, response);
	}

}
