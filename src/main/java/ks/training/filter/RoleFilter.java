package ks.training.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*") // Áp dụng cho tất cả các trang
public class RoleFilter implements Filter {
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession(false);

		String role = (session != null) ? (String) session.getAttribute("role") : null;
		String requestURI = req.getRequestURI();

		boolean isAllowed = role != null && (role.equals("Admin") || role.equals("Employee"));
		boolean isPublicPage = requestURI.contains("login.jsp") || requestURI.contains("index.jsp");

		if (!isAllowed && !isPublicPage) {
			resp.sendRedirect("accessDenied.jsp");
			return;
		}

		chain.doFilter(request, response);
	}
}
