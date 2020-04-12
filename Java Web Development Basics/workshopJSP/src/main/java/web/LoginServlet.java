package web;

import models.service.UserServiceModel;
import services.UsersService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/users/login")
public class LoginServlet extends HttpServlet {
    private final UsersService userService;

    @Inject
    public LoginServlet(UsersService usersService) {
        this.userService = usersService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/user-login.jsp")
                .forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("username");
        String password = req.getParameter("password");
        UserServiceModel user = userService.login(userName,password);

        if (user == null){
            resp.sendRedirect("/users/login");
        }else {
            resp.sendRedirect("/home");
        }
    }
}
