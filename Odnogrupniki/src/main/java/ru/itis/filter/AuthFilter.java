package ru.itis.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebFilter("/*")
public class AuthFilter implements Filter {

    private static final List<String> PROTECTED_URIS = List.of("/main", "/logout");
    private static final List<String> NOTAUTH_URIS = List.of("/signIn", "/signUp");

    private static final String PROTECTED_REDIRECT = "/signIn";
    private static final String NOTAUTH_REDIRECT = "/main";

    public static final String AUTHORIZATION = "authorization";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String uri = request.getRequestURI();

        if(isUserAuth(request)) {
            if(isNotAuthUri(uri)) {
                response.sendRedirect(NOTAUTH_REDIRECT);
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } else {
            if(isProtectedUri(uri)) {
                response.sendRedirect(PROTECTED_REDIRECT);
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
    }

    private boolean isProtectedUri(String uri) {
        return PROTECTED_URIS.contains(uri);
    }

    private boolean isNotAuthUri(String uri) {
        return NOTAUTH_URIS.contains(uri);
    }

    private boolean isUserAuth(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session == null) return false;

        Boolean flag = (Boolean) session.getAttribute(AUTHORIZATION);
        return flag != null && flag;
    }
}
