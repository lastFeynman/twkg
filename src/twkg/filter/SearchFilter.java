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

public class SearchFilter implements Filter {

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
		//搜索原唱还是翻唱 没有则是搜索原唱
		String searchType = req.getParameter("searchType");
		if(!(searchType==null || !searchType.equals("song") || !searchType.equals("cover"))) {
			req.setAttribute("errMsg", "请求参数错误");
			req.getServletContext().getRequestDispatcher("/error.jsp").forward(req, res);
			return;
		}
		//搜索内容 没有则是搜索""
		String rawSearchContent = req.getParameter("searchContent");
		String searchContent = null;
		if(rawSearchContent!=null)
			searchContent = new String(rawSearchContent.getBytes("ISO8859-1"),"UTF-8");
		if(searchContent==null)
			searchContent = "";
		req.setAttribute("searchContent", searchContent);
		//当前请求页 没有则为第一页
		String currentPageStr = req.getParameter("currentPage");
		int currentPage = 1;
		if(currentPageStr!=null) {
			try {
				currentPage = Integer.parseInt(currentPageStr);
			} catch (Exception e) {
				req.setAttribute("errMsg", "请求参数错误:currentPage");
				req.getServletContext().getRequestDispatcher("/error.jsp").forward(req, res);
				return;
			}
		}
		//总页数 没有则为0
		String maxPageStr = request.getParameter("maxPage");
		int maxPage = 0;
		if(maxPageStr!=null) {
			try {
				maxPage = Integer.parseInt(maxPageStr);
			}catch (Exception e) {
				req.setAttribute("errMsg", "请求参数错误:maxPage");
				req.getServletContext().getRequestDispatcher("/error.jsp").forward(req, res);
				return;
			}
		}
		req.setAttribute("maxPage", maxPage);
		req.setAttribute("currentPage", currentPage);
		req.getServletContext().getRequestDispatcher("/SearchServlet").forward(req, res);
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
