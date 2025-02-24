//package ks.training.filter;
//
//
//import jakarta.servlet.*;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import ks.training.entity.Role;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@WebFilter("/*")
//public class SessionFilter implements Filter {
//	private static final Map<String, List<String>> PERMISSION_MAP = new HashMap<>();
//
//	private static final List<String> PUBLIC_PAGES = Arrays.asList("/home", "/book", "/login", "/register");
//
//	@Override
//	public void init(FilterConfig filterConfig) throws ServletException {
//		// Khai báo quyền truy cập cho từng request
//		PERMISSION_MAP.put("/user", Arrays.asList("LIBRARY_MEMBER","MANAGER", "ADMIN"));
//		PERMISSION_MAP.put("/user/add", Arrays.asList("LIBRARY_MEMBER", "ADMIN"));
//		PERMISSION_MAP.put("/user/edit", Arrays.asList("ADMIN"));
//		PERMISSION_MAP.put("/user/delete", Arrays.asList("ADMIN"));
//
//		PERMISSION_MAP.put("/book", Arrays.asList("MANAGER", "USER", "LIBRARY_MEMBER", "ADMIN"));
//		PERMISSION_MAP.put("/book/add", Arrays.asList("MANAGER", "ADMIN"));
//		PERMISSION_MAP.put("/book/edit", Arrays.asList("MANAGER", "ADMIN"));
//		PERMISSION_MAP.put("/book/delete", Arrays.asList("MANAGER", "ADMIN"));
//
//		PERMISSION_MAP.put("/purchase", Arrays.asList("MANAGER", "ADMIN"));
//		PERMISSION_MAP.put("/member", Arrays.asList("MANAGER", "ADMIN"));
//
//		PERMISSION_MAP.put("/history-purchase", Arrays.asList("MANAGER", "ADMIN"));
//		PERMISSION_MAP.put("/history-rental", Arrays.asList("MANAGER", "ADMIN"));
//		PERMISSION_MAP.put("/history-rental/delete", Arrays.asList("MANAGER", "ADMIN"));
//		PERMISSION_MAP.put("/history-rental/status", Arrays.asList("MANAGER", "ADMIN"));
//
//		PERMISSION_MAP.put("/report-purchase", Arrays.asList("MANAGER", "ADMIN"));
//		PERMISSION_MAP.put("/report-rental", Arrays.asList("MANAGER", "ADMIN"));
//
//		PERMISSION_MAP.put("/rental-cart", Arrays.asList("LIBRARY_MEMBER"));
//		PERMISSION_MAP.put("/rental/confirm", Arrays.asList("LIBRARY_MEMBER"));
//
//	}
//
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//			throws IOException, ServletException {
//		HttpServletRequest httpRequest = (HttpServletRequest) request;
//		HttpServletResponse httpResponse = (HttpServletResponse) response;
//
//		HttpSession session = httpRequest.getSession(false);
//		String requestURI = httpRequest.getRequestURI();
//
//		String userEmail = (session != null) ? (String) session.getAttribute("userEmail") : null;
//		Role role = (session != null) ? (Role) session.getAttribute("role") : null;
//		System.out.println("role" + role);
//		String referer = httpRequest.getHeader("Referer");
//
//		boolean isPublicPage = PUBLIC_PAGES.stream().anyMatch(requestURI::endsWith);
//		if (isPublicPage) {
//			chain.doFilter(request, response);
//			return;
//		}
//
//		if (userEmail == null) {
//			httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
//			return;
//		}
//
//		for (Map.Entry<String, List<String>> entry : PERMISSION_MAP.entrySet()) {
//			if (requestURI.startsWith(httpRequest.getContextPath() + entry.getKey())) {
//				if (entry.getKey().equals("/rental-cart")
//						&& (role == null || (!role.name().equals("LIBRARY_MEMBER")))) {
//					session.setAttribute("errorMessage", "Bạn cần đăng ký thành viên để thuê sách!");
//					httpResponse.sendRedirect(httpRequest.getContextPath() + "/book");
//					return;
//				}
//				if (role == null || !entry.getValue().contains(role.name())) {
//					session.setAttribute("errorMessage", "Bạn không có quyền truy cập trang này!");
//					httpResponse.sendRedirect(referer != null ? referer : httpRequest.getContextPath() + "/home");
//					return;
//				}
//			}
//		}
//
//		chain.doFilter(request, response);
//	}
//
//	@Override
//	public void destroy() {
//
//	}
//}
