package filters;

import models.User;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"}) 
public class AuthorizationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String path = req.getServletPath();

        if (path.startsWith("/assets") || path.equals("/login") || path.equals("/logout") || path.equals("/403.jsp") || path.equals("/404.jsp")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("currentUser");

            if (user != null) {
                int roleId = user.getRoleId(); 

                if (roleId == 2) {
                    if (path.startsWith("/role")) {
                        resp.sendError(403);
                        return;
                    }
                    if (path.startsWith("/user-") && !path.equals("/user-table") && !path.equals("/user-details")) {
                        resp.sendError(403);
                        return;
                    }
                }
                
                if (roleId == 3) {
                    if (path.startsWith("/groupwork") || path.startsWith("/user") || path.startsWith("/role")) {
                        resp.sendError(403);
                        return;
                    }
                }
            }
        }
        
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}