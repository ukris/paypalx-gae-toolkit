/**
 * 
 */
package com.paypal.adaptive.core;

import java.util.Map;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.paypal.adaptive.ipn.IPNListner;


/**
 * @author palavilli
 *
 */
@PersistenceCapable
public class PreapprovalIPNListner extends IPNListner {

	@Persistent
	protected boolean approved;
	protected int current_number_of_payments;
	protected double current_total_amount_of_all_payments;
	protected int current_period_attempts;
	protected CurrencyCodes currendyCode;
	protected String date_of_month;
    protected DayOfWeek day_of_week;
	protected String starting_date;
    protected String ending_date;
    protected double max_amount_per_payment;
    protected int max_number_of_payments;
    protected double max_total_amount_of_all_payments;
    protected PaymentPeriodType payment_period;
    protected PinType pin_type;
	
	
	public PreapprovalIPNListner(Map<String, String> ipnVariables){
		super(ipnVariables);
	}


	public boolean isApproved() {
		return approved;
	}


	public void setApproved(boolean approved) {
		this.approved = approved;
	}


	public int getCurrent_number_of_payments() {
		return current_number_of_payments;
	}


	public void setCurrent_number_of_payments(int currentNumberOfPayments) {
		current_number_of_payments = currentNumberOfPayments;
	}


	public double getCurrent_total_amount_of_all_payments() {
		return current_total_amount_of_all_payments;
	}


	public void setCurrent_total_amount_of_all_payments(
			double currentTotalAmountOfAllPayments) {
		current_total_amount_of_all_payments = currentTotalAmountOfAllPayments;
	}


	public int getCurrent_period_attempts() {
		return current_period_attempts;
	}


	public void setCurrent_period_attempts(int currentPeriodAttempts) {
		current_period_attempts = currentPeriodAttempts;
	}


	public CurrencyCodes getCurrendyCode() {
		return currendyCode;
	}


	public void setCurrendyCode(CurrencyCodes currendyCode) {
		this.currendyCode = currendyCode;
	}


	public String getDate_of_month() {
		return date_of_month;
	}


	public void setDate_of_month(String dateOfMonth) {
		date_of_month = dateOfMonth;
	}


	public DayOfWeek getDay_of_week() {
		return day_of_week;
	}


	public void setDay_of_week(DayOfWeek dayOfWeek) {
		day_of_week = dayOfWeek;
	}


	public String getStarting_date() {
		return starting_date;
	}


	public void setStarting_date(String startingDate) {
		starting_date = startingDate;
	}


	public String getEnding_date() {
		return ending_date;
	}


	public void setEnding_date(String endingDate) {
		ending_date = endingDate;
	}


	public double getMax_amount_per_payment() {
		return max_amount_per_payment;
	}


	public void setMax_amount_per_payment(double maxAmountPerPayment) {
		max_amount_per_payment = maxAmountPerPayment;
	}


	public int getMax_number_of_payments() {
		return max_number_of_payments;
	}


	public void setMax_number_of_payments(int maxNumberOfPayments) {
		max_number_of_payments = maxNumberOfPayments;
	}


	public double getMax_total_amount_of_all_payments() {
		return max_total_amount_of_all_payments;
	}


	public void setMax_total_amount_of_all_payments(
			double maxTotalAmountOfAllPayments) {
		max_total_amount_of_all_payments = maxTotalAmountOfAllPayments;
	}


	public PaymentPeriodType getPayment_period() {
		return payment_period;
	}


	public void setPayment_period(PaymentPeriodType paymentPeriod) {
		payment_period = paymentPeriod;
	}


	public PinType getPin_type() {
		return pin_type;
	}


	public void setPin_type(PinType pinType) {
		pin_type = pinType;
	}
}
