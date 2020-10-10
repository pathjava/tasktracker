package ru.progwards.tasktracker.controller.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter("/*")
public class CharsetFilter implements Filter {

    public void init(FilterConfig config) {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain next)
            throws IOException, ServletException {
        if (request.getCharacterEncoding() == null) {
            request.setCharacterEncoding("UTF-8");
        }

        response.setCharacterEncoding("UTF-8");

        next.doFilter(request, response);
    }

    public void destroy() {
    }
}