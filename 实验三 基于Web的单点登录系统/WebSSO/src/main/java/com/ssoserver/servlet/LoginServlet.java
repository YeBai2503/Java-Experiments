package com.ssoserver.servlet;
import com.ssoserver.model.DB;
import com.ssoserver.model.User;
import com.ssoserver.tool.HistoryCreate;
import com.ssoserver.tool.JwtBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    //记录重定向地址
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 允许跨域请求
        response.setHeader("Access-Control-Allow-Origin", "*"); // 允许任意域名访问
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        Cookie[] cookies = request.getCookies();
        String token = null;
        String redirectUrl = request.getParameter("redirect_url");
        request.setAttribute("previousUrl", redirectUrl);

        if (cookies != null) {//检查cookie是否存在
            for (Cookie cookie : cookies) {
                if ("SSO_Cookie".equals(cookie.getName())) {
                    token = JwtBuilder.parseJwt(cookie.getValue());//对cookie进行解析，获取token
                    if(token!=null)
                    {
                        System.out.println("自动跳转："+redirectUrl);
                        if(redirectUrl==null)HistoryCreate.create(Integer.parseInt(token),"SSO");
                        response.sendRedirect(redirectUrl != null ? redirectUrl : "logUser");
                        return;
                    }
                }
            }
        }
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
    // 处理登录请求
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*"); // 允许任意域名访问
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        String username = request.getParameter("username"); // 从表单获取的用户名或邮箱
        String password = request.getParameter("password"); // 从表单获取的密码
        String redirectUrl = null;

        // 验证用户名和密码是否正确
        if (isSucceedLogin(username, password)!= null) {
            // 登录成功时设置 Cookie
            int id=isSucceedLogin(username, password).getId();
            String token = JwtBuilder.buildJwt(id); // 加密token
            System.out.println("Token: "+id);
            // 设置 cookie
            Cookie cookie = new Cookie("SSO_Cookie", token);
//            cookie.setDomain(".example.com"); // 设置可用于子域
            cookie.setPath("/"); // 设置 cookie 可用于所有路径
            cookie.setHttpOnly(true); // 防止 JavaScript 访问
            cookie.setSecure(true); // 确保只通过 HTTPS 发送
            cookie.setMaxAge(60 * 60); // 设置 cookie 过期时间（1小时）
            response.addCookie(cookie);

            // 获取登录前页面的地址
            if(!request.getParameter("previousUrl").equals("null"))redirectUrl = (String) request.getParameter("previousUrl");
            // 记录登录历史
            if(redirectUrl==null)HistoryCreate.create(id,"SSO");
            // 转发到登录成功页面
            System.out.println("登录成功，跳转到: "+redirectUrl);
            response.sendRedirect(redirectUrl != null ? redirectUrl : "logUser");

        } else {
            // 登录失败，设置错误消息
            request.setAttribute("errorMessage", "用户名或密码错误");
            // 转发到登录页面
            if(!request.getParameter("previousUrl").equals("null"))redirectUrl = (String) request.getParameter("previousUrl");
            request.setAttribute("previousUrl", redirectUrl);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
    private User isSucceedLogin(String username, String password) {
        ArrayList<User> users;
        User user=null;
        if(isInteger(username))users= DB.queryUser(Integer.parseInt(username),"");
        else users=DB.queryUser(0,username);
        if(users.size()==1) {
            if (users.get(0).getPassword().equals(password)) {
                user = users.get(0);
                System.out.println("isSucceedLogin ID: "+user.getId());
                return user;
            }
        }
        return user;
    }
    //判断是否是数字
    public static boolean isInteger(String str) {
        if (str == null || str.isEmpty()) {
            return false; // 空字符串和null不是有效的int
        }
        try {
            Integer.parseInt(str); // 尝试将字符串转换为整数
            return true; // 转换成功，返回true
        } catch (NumberFormatException e) {
            return false; // 捕获到异常，返回false
        }
    }

}
