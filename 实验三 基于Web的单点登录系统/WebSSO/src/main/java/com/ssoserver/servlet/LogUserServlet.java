package com.ssoserver.servlet;

import com.ssoserver.model.DB;
import com.ssoserver.factory.HistoryBOFactory;
import com.ssoserver.factory.OnlineBOFactory;
import com.ssoserver.model.HistoryBO;
import com.ssoserver.model.OnlineBO;
import com.ssoserver.model.User;
import com.ssoserver.model.UserDao;
import com.ssoserver.tool.JwtBuilder;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@WebServlet("/logUser")
public class LogUserServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("SSO_Cookie".equals(cookie.getName())) {
                    System.out.println("LogUserServlet: 成功找到cookie！！！");
                    token = JwtBuilder.parseJwt(cookie.getValue());
                    break;
                }
            }
        }
        if (token == null) {
            response.sendRedirect("login");
            return;
        }

        // 在线名单
        OnlineBOFactory onlineBOFactory = new OnlineBOFactory();
        OnlineBO onlineBO = onlineBOFactory.produce2();
        onlineBO.setOnline(Integer.parseInt(token), "SSO");
        request.setAttribute("onlineBO", onlineBO);

        // 历史记录
        if (token != null) {

            request.setAttribute("id", Integer.parseInt(token));
            request.setAttribute("name", DB.queryUser(Integer.parseInt(token),"").get(0).getName());
            request.setAttribute("isManager", DB.queryUser(Integer.parseInt(token),"").get(0).getIsManager());

            HistoryBOFactory historyBOFactory = new HistoryBOFactory();
            HistoryBO historyBO = historyBOFactory.produce2();
            historyBO.setHistory(Integer.parseInt(token));
//            DB2.setTableName("historyLogin");
//            ArrayList<User> dataList = DB2.queryUser(Integer.parseInt(token),"");
//            // 将数据放入请求作用域
//            request.setAttribute("dataList", dataList);
            request.setAttribute("historyBO", historyBO);
        }

        //禁用名单表
        if(DB.queryUser(Integer.parseInt(token),"").get(0).getIsManager()==1) {
            UserDao userDao = new UserDao();
            ArrayList<User> forbidList = userDao.getForbidUsers();
            request.setAttribute("forbidList", forbidList);
        }

        request.getRequestDispatcher("/LogUser.jsp").forward(request, response);

    }
}
