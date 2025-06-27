package com.ssoserver.servlet;
import com.ssoserver.model.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/forbid")
public class ForbidServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forbidId = request.getParameter("forbidId");
        String isForbid = request.getParameter("isForbid");
        UserDao dao=new UserDao();
        dao.forbid(Integer.parseInt(forbidId),Integer.parseInt(isForbid));
        response.sendRedirect("logUser");
    }
}
