package com.example.filter;

import com.example.command.CreateHistoryCommand;
import com.example.tool.JwtBuilder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/index"})
public class loginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Filter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("成功进行过滤！！");

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;


//        String hadId = request.getParameter("hadId");
//        // 判断是否存在
//        if (hadId != null) {
//            System.out.println("过滤器：已有cookie: " + hadId);
//            httpRequest.setAttribute("hadId", hadId);
//            chain.doFilter(request, response); // chain 变量应该在这里有效
//            return;
//        }


        // 获取 cookies
        Cookie[] cookies = httpRequest.getCookies();
        String token = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("SSO_Cookie".equals(cookie.getName())) {
                    System.out.println("成功找到cookie！！！");
                    token = JwtBuilder.parseJwt(cookie.getValue());
                    break;
                }
            }
        }

        if (token != null) {
            httpRequest.setAttribute("user",token);
            int id=Integer.parseInt(token);
            CreateHistoryCommand.create(id,"http://localhost:8081/Web1_war_exploded/index");//记录用户自动登录记录
        }
        else {

            String requestURL = httpRequest.getRequestURL().toString();
            requestURL="http://localhost:8081/Web1_war_exploded/index";
            httpResponse.sendRedirect("http://localhost:8080/WebSSO_war_exploded/login?redirect_url=" + requestURL);
            System.out.println("！！！未找到cookie");
            return;
        }

         // 继续链中的下一个过滤器或目标 Servlet
        chain.doFilter(request, response); // chain 变量应该在这里有效
    }

    @Override
    public void destroy() {
        System.out.println("Filter destroyed");
    }
}