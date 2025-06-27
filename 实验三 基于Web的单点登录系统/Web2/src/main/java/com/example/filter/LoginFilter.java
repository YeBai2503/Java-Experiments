package com.example.filter;

import com.example.tool.HistoryCreate;
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
public class LoginFilter implements Filter {

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
            HistoryCreate.create(id,"http://localhost:8082/Web2_war_exploded/index");//记录用户自动登录记录
        }
        else {//跳转回登录页面
            // 1. 获取请求 URL
            String requestURL = httpRequest.getRequestURL().toString();

            // 2. 检查请求 URL 是否以 .jsp 结尾
            if (requestURL.endsWith(".jsp")) {
                // 3. 去除 .jsp 后缀
                requestURL = requestURL.substring(0, requestURL.lastIndexOf(".jsp"));
            }
            requestURL="http://localhost:8082/Web2_war_exploded/index";
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