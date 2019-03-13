package com.hillel.couponsystem.couponsystem;

import java.sql.SQLException;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.hillel.couponsystem.connectionpool.ConnectionPool;
import com.hillel.couponsystem.enums.ErrorType;
import com.hillel.couponsystem.exception.CouponSystemExeption;
import com.hillel.couponsystem.threadjob.DailyCouponExpirationTask;

@Component
public enum CouponSystemSingelton {

	INSTANCE;

	private DailyCouponExpirationTask dailyJob = new DailyCouponExpirationTask();
	private Thread thread = new Thread(dailyJob);

	@PostConstruct
	public void startup() {
		ConnectionPool.getInstance();
		thread.start();
	}

	public void shutdown() throws CouponSystemExeption {

		try {
			dailyJob.stopTask();
			thread.interrupt();
			Thread.currentThread().join();
			ConnectionPool.getInstance().closeAllConnections();
		} catch (SQLException | CouponSystemExeption | InterruptedException e) {
			System.out.println("Close Dont Work : " + e.getMessage());
			throw new CouponSystemExeption(ErrorType.GENERAL_ERROR);
		}

	}
}
