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

public class AdminSearchFilter implements Filter {

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
		
		String searchType = req.getParameter("searchType");
		String searchContent = req.getParameter("searchContent");
		String currentPageStr = req.getParameter("currentPage");
		String maxPageStr = req.getParameter("maxPage");
		int currentPage = 1;
		int maxPage = 0;
		
		if(searchType==null || (!searchType.equals("user")&&!searchType.equals("song")&&!searchType.equals("cover"))) {
			if(searchType==null)
				req.setAttribute("errMsg", "缺少请求参数");
			else
				req.setAttribute("errMsg", "请求参数错误:searchType");
			req.getServletContext().getRequestDispatcher("/error.jsp").forward(req, res);
			return;
		}
		
		
//		if(rawSearchContent!=null) 
//			searchContent = new String(rawSearchContent.getBytes("ISO8859-1"),"UTF-8");
		if(searchContent==null)
			searchContent = "";
		
		if(currentPageStr!=null) {
			try {
				currentPage = Integer.parseInt(currentPageStr);
			}catch (Exception e) {
				req.setAttribute("errMsg", "请求参数错误:currentPage");
				req.getServletContext().getRequestDispatcher("/error.jsp").forward(req, res);
				return;
			}
		}
		
		if(maxPageStr!=null) {
			try {
				maxPage = Integer.parseInt(maxPageStr);
			}catch (Exception e) {
				req.setAttribute("errMsg", "请求参数错误:maxPage");
				req.getServletContext().getRequestDispatcher("/error.jsp").forward(req, res);
				return;
			}
		}
		//调整后的请求参数
		req.setAttribute("searchContent", searchContent);
		req.setAttribute("currentPage", currentPage);
		req.setAttribute("maxPage", maxPage);
		req.getServletContext().getRequestDispatcher("/admin/AdminSearchServlet").forward(req, res);
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
