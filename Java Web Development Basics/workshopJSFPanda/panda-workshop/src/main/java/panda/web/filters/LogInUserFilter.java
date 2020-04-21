package panda.web.filters;

import javax.faces.context.FacesContext;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter({"/faces/view/register.xhtml",
        "/faces/view/login.xhtml",
        "/faces/view/index.xhtml",
        "/"
})
public class LogInUserFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        HttpSession session = httpServletRequest.getSession();

        if (session.getAttribute("username") != null){
            httpServletResponse.sendRedirect("/faces/view/home.xhtml");
        }else {
            filterChain.doFilter(httpServletRequest,httpServletResponse);
        }
    }
}
