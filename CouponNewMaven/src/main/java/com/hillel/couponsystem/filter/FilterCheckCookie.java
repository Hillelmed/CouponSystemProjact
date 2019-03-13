package com.hillel.couponsystem.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.hillel.couponsystem.util.Utilextractor;

/**
 * Servlet Filter implementation class filterCheckSession
 */
@Component
public class FilterCheckCookie implements Filter {

	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		try {
			HttpServletRequest request  = (HttpServletRequest) req;
			HttpServletResponse response = (HttpServletResponse) res;
            String URL = request.getRequestURI().toString();
			if (URL.equals("/coupons/readall")||URL.equals("/login")) {
				chain.doFilter(request, response);
				return;
			}
			Cookie idvaild = Utilextractor.getCookieServer(request.getCookies());
			long idofLogin = 0;
			if (idvaild != null) {
				idofLogin = Long.parseLong(idvaild.getValue());
			}
			boolean loggedIn = idofLogin != 0;
			if (loggedIn) {
				String ipclient = Utilextractor.getClientIp(request, idofLogin);
				System.out.println(new Date() + " login filter " + idvaild.getName() + " " + ipclient);
				chain.doFilter(request, response);
				return;
			}
			response.setStatus(401);
		} catch (NullPointerException | NumberFormatException e) {
			throw new ServletException(e.getMessage());
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

}
