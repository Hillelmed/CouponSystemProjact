/**
 * Daily job for deleting all the coupons that expired
 * @author HillelMed
 */
package com.hillel.couponsystem.threadjob;

import java.time.LocalDate;

import com.hillel.couponsystem.beans.Coupon;
import com.hillel.couponsystem.dao.CouponsDAO;
import com.hillel.couponsystem.dao.ICouponsDAO;
import com.hillel.couponsystem.exception.CouponSystemExeption;


public class DailyCouponExpirationTask implements Runnable {
	private ICouponsDAO couponsDAO = new CouponsDAO();
	private boolean quit = false;
	private LocalDate today = LocalDate.now();

	public DailyCouponExpirationTask() {
	}

	public void run() {
		System.out.println("Daily job is runing!");
		while (!quit) {
			try {
				if (Thread.currentThread().isInterrupted()) {
					break;
				}
				deleteIrelevantCouponsInfo();
				if (Thread.currentThread().isInterrupted()) {
					break;
				}
				Thread.sleep(86_400_000);
				today = LocalDate.now();
			} catch (InterruptedException e) {
				quit = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void stopTask() throws InterruptedException {
		quit = true;
		Thread.currentThread().interrupt();
	}

	/**
	 * delete all the coupons that expired
	 * 
	 * @throws Exception
	 */
	private void deleteIrelevantCouponsInfo() throws Exception {
		for (Coupon coupon : couponsDAO.readAllCoupons()) {
			if (LocalDate.parse(coupon.getEndDates()).isBefore(today)) {
				try {
					couponsDAO.deleteCouponPurchase(coupon.getId());
					couponsDAO.deleteCoupon(coupon.getId());
					System.out.println(coupon.toString() + " Delete from Company : end date");
				} catch (CouponSystemExeption e) {
					System.out.println("Was Probelem with delet coupon");
					e.printStackTrace();
				}
			}

		}

	}
}
