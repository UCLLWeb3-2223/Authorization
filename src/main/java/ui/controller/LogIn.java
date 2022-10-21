package ui.controller;

import domain.model.Role;
import domain.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogIn extends RequestHandler {

    @Override
    public String handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // simplified login:
        // no identity check, form sends only role
        Role role = Role.valueOf(request.getParameter("role").toUpperCase());
        // user name is defined by user role
        User user = new User(role.getStringValue(), role);

        request.getSession().setAttribute("user", user);
        response.sendRedirect("Controller?action=Home");
        return "Controller?action=Home";
    }
}
