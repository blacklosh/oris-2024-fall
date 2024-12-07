package ru.itis.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebFilter("/*")
public class AuthFilter implements Filter {

    private List<String> PROTECTED_URIS;
    private List<String> NOTAUTH_URIS;

    private String PROTECTED_REDIRECT;
    private String NOTAUTH_REDIRECT;

    private String AUTHORIZATION;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext context = filterConfig.getServletContext();
        PROTECTED_URIS = (List<String>) context.getAttribute("PROTECTED_URIS");
        NOTAUTH_URIS = (List<String>) context.getAttribute("NOTAUTH_URIS");

        PROTECTED_REDIRECT = (String) context.getAttribute("PROTECTED_REDIRECT");
        NOTAUTH_REDIRECT = (String) context.getAttribute("NOTAUTH_REDIRECT");
        AUTHORIZATION = (String) context.getAttribute("AUTHORIZATION");
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

        Map<String, String> map = new HashMap<>();
        map.put(null, "");

        Boolean flag = (Boolean) session.getAttribute(AUTHORIZATION);
        return flag != null && flag;
    }
}
