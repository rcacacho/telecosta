package telecosta.nuevo.web.login;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 *
 * @author rcacacho
 */
@WebFilter(filterName = "AuthFilter", urlPatterns = {"*.xhtml"})
public class AuthorizationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest reqt = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;
            HttpSession ses = reqt.getSession(false);
            
            String reqURI = reqt.getRequestURI();
            if (reqURI.indexOf("/login.xhtml") >= 0
                    || (ses != null && ses.getAttribute("usuario") != null)
                    || reqURI.indexOf("/telecosta/") >= 0
                    || reqURI.contains("javax.faces.resource")) {
                chain.doFilter(request, response);
            } else {
                resp.sendRedirect(reqt.getContextPath() + "/login.xhtml");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void destroy() {

    }
    
}
