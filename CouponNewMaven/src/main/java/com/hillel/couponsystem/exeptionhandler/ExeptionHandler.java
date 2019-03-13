package com.hillel.couponsystem.exeptionhandler;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.hillel.couponsystem.exception.CouponSystemExeption;

@ControllerAdvice
public class ExeptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler(CouponSystemExeption.class)
    public void handleConflict(HttpServletResponse response, CouponSystemExeption e) {
    	    if(e.getErrorPrintFromDao()!=null) {
    	    	//Send to my email was dao Problem and send client error
    	    	//Email.send(e.getErrorPrintFromDao());
    	    	response.setHeader("error", e.getMessage());
    	    }
    	    //else back to client the error
            System.err.println("Exception : " +  e.getMessage());
            response.setStatus(e.getErrortype().getStatuserror());
            response.setHeader("error", e.getErrortype().getMessage());
		}
}
