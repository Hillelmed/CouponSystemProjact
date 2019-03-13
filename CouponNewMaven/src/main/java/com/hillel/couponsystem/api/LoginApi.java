package com.hillel.couponsystem.api;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillel.couponsystem.beans.Userdetails;
import com.hillel.couponsystem.dao.CompaniesDAOJPA;
import com.hillel.couponsystem.dao.CustomersDAOJPA;
import com.hillel.couponsystem.entitiy.CompanyEntity;
import com.hillel.couponsystem.entitiy.CustomerEntity;
import com.hillel.couponsystem.enums.ClientType;
import com.hillel.couponsystem.enums.ErrorType;
import com.hillel.couponsystem.exception.CouponSystemExeption;
import com.hillel.couponsystem.util.Utilextractor;

@RestController
@RequestMapping("/login")
public class LoginApi {
	
	@Autowired
	private CompaniesDAOJPA companydao;
	@Autowired
	private CustomersDAOJPA customerdao;

	@PostMapping
	public void Logintosystem(@RequestBody Userdetails userdetails, HttpServletResponse response)
			throws CouponSystemExeption {
		
		try {
			if (userdetails.getEmail().equals("admin@admin.com") && userdetails.getPassword().equals("admin")) {
				Cookie cookie = new Cookie("Adminlog", "9999");
				response.addCookie(cookie);
				userdetails.setId(9999);
				userdetails.setComeback(0);
				userdetails.setClienttype(ClientType.Administrator);
				String objectUser = new ObjectMapper().writeValueAsString(userdetails);
				response.setHeader("Content-Type", "application/json");
				response.setStatus(200);
				response.getWriter().println(objectUser);
			} else if (userdetails.getClienttype() == ClientType.Company) {
				userdetails = companydao.login(userdetails);
				if (userdetails.getId() != -1) {
					String kooki = String.valueOf(userdetails.getId());
					Cookie cookie = new Cookie("Companylog", kooki);
					response.addCookie(cookie);
					userdetails.setComeback(0);
					String objectUser = new ObjectMapper().writeValueAsString(userdetails);
					response.setHeader("Content-Type", "application/json");
					response.setStatus(200);
					response.getWriter().println(objectUser);
				} else {
					throw new CouponSystemExeption(ErrorType.GENERAL_ERROR_BADLOGIN);
				}
			} else if (userdetails.getClienttype() == ClientType.Customer) {
				userdetails = customerdao.login(userdetails);
				if (userdetails.getId() != -1) {
					String kooki = String.valueOf(userdetails.getId());
					Cookie cookie = new Cookie("Customerlog", kooki);
					response.addCookie(cookie);
					userdetails.setComeback(0);
					String objectUser = new ObjectMapper().writeValueAsString(userdetails);
					response.setHeader("Content-Type", "application/json");
					response.setStatus(200);
					response.getWriter().println(objectUser);
				} else {
					throw new CouponSystemExeption(ErrorType.GENERAL_ERROR_BADLOGIN);
				}
			}
		} catch (CouponSystemExeption | IOException e) {
			throw new CouponSystemExeption(ErrorType.GENERAL_ERROR_BADLOGIN);
		}
	}

	@GetMapping
	public void checkiflog(HttpServletResponse response, HttpServletRequest request) throws CouponSystemExeption {
		try {
			Userdetails ud = new Userdetails();
			Cookie getfrom = Utilextractor.getCookieServer(request.getCookies());
			if (getfrom.getName().equals("Customerlog")) {
				long Id = Long.parseLong(getfrom.getValue());
				CustomerEntity c2 = customerdao.readCustomer(Id);
				ud.setId(Id);
				ud.setClienttype(ClientType.Customer);
				ud.setEmail(c2.getEmail());
				ud.setComeback(1);
				response.setHeader("Content-Type", "application/json");
				response.setStatus(200);
				response.getWriter().println(ud);
			} else if (getfrom.getName().equals("Companylog")) {
				long Id = Long.parseLong(getfrom.getValue());
				CompanyEntity c2 = companydao.readCompany(Id);
				ud.setId(Id);
				ud.setClienttype(ClientType.Company);
				ud.setEmail(c2.getEmail());
				ud.setComeback(1);
				response.setHeader("Content-Type", "application/json");
				response.setStatus(200);
				response.getWriter().println(ud);
			} else if (getfrom.getName().equals("Adminlog")) {
				long Id = Long.parseLong(getfrom.getValue());
				ud.setId(Id);
				ud.setClienttype(ClientType.Administrator);
				ud.setComeback(1);
				response.setHeader("Content-Type", "application/json");
				response.setStatus(200);
				response.getWriter().println(ud);
			}
		} catch (IOException | NullPointerException | NumberFormatException | CouponSystemExeption e) {
			throw new CouponSystemExeption(ErrorType.GENERAL_ERROR_BADLOGIN);
		}
	}

	@DeleteMapping
	public void Logoutnow(HttpServletResponse response, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null)
			for (Cookie cookie : cookies) {
				cookie.setMaxAge(0);
				cookie.setValue("");
				response.addCookie(cookie);
			}
	}
}
