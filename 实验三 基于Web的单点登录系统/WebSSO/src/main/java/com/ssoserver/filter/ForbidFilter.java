package com.ssoserver.filter;
import com.ssoserver.model.DB;
import com.ssoserver.tool.JwtBuilder;

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

@WebFilter(urlPatterns = {"/logUser"})
public class ForbidFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("进行过滤！！");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        // 获取 cookies
        Cookie cookieToDelete = null;
        Cookie[] cookies = httpRequest.getCookies();
        String token = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("SSO_Cookie".equals(cookie.getName())) {
                    System.out.println("成功找到cookie！！！");
                    cookieToDelete = cookie;
                    token = JwtBuilder.parseJwt(cookie.getValue());
                    break;
                }
            }
        }
        if (token != null) {
            //被禁用
            if(DB.queryUser(Integer.parseInt(token),"").get(0).getIsForbid()==1)
            {
                // 删除 cookie
                cookieToDelete.setValue(null); // 清空值
                cookieToDelete.setMaxAge(0); // 设置生存时间为 0
                cookieToDelete.setPath("/"); // 设置路径, 确保与原来的 Cookie 一致
                httpResponse.addCookie(cookieToDelete); // 重新添加到响应中，删除 cookie

                System.out.println("！！！！被禁用");
                httpRequest.setAttribute("name", DB.queryUser(Integer.parseInt(token),"").get(0).getName());
                httpRequest.setAttribute("order","您的账号已被禁用，如有疑问请联系管理员");
                httpRequest.getRequestDispatcher("/exit.jsp").forward(request, response);
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
