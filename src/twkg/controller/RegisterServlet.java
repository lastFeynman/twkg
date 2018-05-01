package twkg.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
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

/**
 * Servlet implementation class register
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterServlet() {
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
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// doGet(request, response);

		response.setContentType("text/html;charset=UTF-8");

		String username = new String(request.getParameter("username").getBytes("ISO8859-1"), "UTF-8");
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		User user = new User();

		Timestamp date = new Timestamp(System.currentTimeMillis());

		user.setUserName(username);
		//插入后才能得到userId
		user.setUserKey("假装我是密码");
		user.setUserEmail(email);
		user.setRegisterTime(date);
		
		DaoFactory daoFactory = DaoFactory.getDaoFactory();
		IUserDao userDao = daoFactory.getUserDao();
		if (userDao.findUserByUserName(user.getUserName()) != null) {
			response.sendRedirect("/twkg/register.jsp?error=2");
		}else if(userDao.findUserByUserEmail(user.getUserEmail()) != null) {
			response.sendRedirect("/twkg/register.jsp?error=1");
		} else if (userDao.insert(user)) {
			user = userDao.findUserByUserName(username);
			//加密密码
			user.setUserKey(EncryptUtil.encrypt(password, user.getUserId()));
			if(!userDao.update(user)) {
				request.setAttribute("errMsg", "注册失败");
				request.getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
				return;
			}
			
			//生成默认头像
			String servPath = request.getServletContext().getRealPath("/");
			user = userDao.findUserByUserName(username);
			File avatarFile = new File(servPath+ConfigUtil.USER_AVATAR_PATH.substring(6)+user.getUserId()+".jpg");
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
			response.sendRedirect("/twkg/login.jsp?error=3");
		}
	}
}
