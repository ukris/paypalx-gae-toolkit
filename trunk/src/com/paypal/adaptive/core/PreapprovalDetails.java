/**
 * 
 */
package com.paypal.adaptive.core;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;


/**
 * <p>Java class for PreapprovalDetals.
 * 
 */
public class PreapprovalDetails  {

	    protected String cancelUrl;
	    protected String ipnNotificationUrl;
	    protected String returnUrl;
	    protected CurrencyCodes currencyCode = CurrencyCodes.USD;
	    protected int dateOfMonth;
	    protected DayOfWeek dayOfWeek = DayOfWeek.NO_DAY_SPECIFIED;
	    protected String endingDate;
	    protected double maxAmountPerPayment;
	    protected int maxNumberOfPayments;
	    protected int maxNumberOfPaymentsPerPeriod;
	    protected double maxTotalAmountOfAllPayments;
	    protected PaymentPeriodType paymentPeriod = PaymentPeriodType.NO_PERIOD_SPECIFIED;
	    protected String memo;
	    protected String senderEmail;
		protected String startingDate;
	    protected PinType pinType = PinType.NOT_REQUIRED;
	    protected String status;
	    protected boolean approved;
	    protected long curPayments;
	    protected double curPaymentsAmount;
	    protected long curPeriodAttempts;
	    protected String curPeriodEndingDate;
	    // addressList
	    
	    
	    
		public String getCancelUrl() {
			return cancelUrl;
		}
		public void setCancelUrl(String cancelUrl) {
			this.cancelUrl = cancelUrl;
		}
		 public CurrencyCodes getCurrencyCode() {
			 return currencyCode;
		 }
		 public void setCurrencyCode(CurrencyCodes currencyCode) {
			 this.currencyCode = currencyCode;
		 }
		public int getDateOfMonth() {
			return dateOfMonth;
		}
		public void setDateOfMonth(int dateOfMonth) {
			this.dateOfMonth = dateOfMonth;
		}
		public DayOfWeek getDayOfWeek() {
			return dayOfWeek;
		}
		public void setDayOfWeek(DayOfWeek dayOfWeek) {
			this.dayOfWeek = dayOfWeek;
		}
		public String getEndingDate() {
			return endingDate;
		}
		public void setEndingDate(String endingDate) {
			this.endingDate = endingDate;
		}
		public double getMaxAmountPerPayment() {
			return maxAmountPerPayment;
		}
		public void setMaxAmountPerPayment(double maxAmountPerPayment) {
			this.maxAmountPerPayment = maxAmountPerPayment;
		}
		public int getMaxNumberOfPayments() {
			return maxNumberOfPayments;
		}
		public void setMaxNumberOfPayments(int maxNumberOfPayments) {
			this.maxNumberOfPayments = maxNumberOfPayments;
		}
		public int getMaxNumberOfPaymentsPerPeriod() {
			return maxNumberOfPaymentsPerPeriod;
		}
		public void setMaxNumberOfPaymentsPerPeriod(int maxNumberOfPaymentsPerPeriod) {
			this.maxNumberOfPaymentsPerPeriod = maxNumberOfPaymentsPerPeriod;
		}
		public double getMaxTotalAmountOfAllPayments() {
			return maxTotalAmountOfAllPayments;
		}
		public void setMaxTotalAmountOfAllPayments(double maxTotalAmountOfAllPayments) {
			this.maxTotalAmountOfAllPayments = maxTotalAmountOfAllPayments;
		}
		public PaymentPeriodType getPaymentPeriod() {
			return paymentPeriod;
		}
		public void setPaymentPeriod(PaymentPeriodType paymentPeriod) {
			this.paymentPeriod = paymentPeriod;
		}
		public String getReturnUrl() {
			return returnUrl;
		}
		public void setReturnUrl(String returnUrl) {
			this.returnUrl = returnUrl;
		}
		public String getMemo() {
			return memo;
		}
		public void setMemo(String memo) {
			this.memo = memo;
		}
		public String getIpnNotificationUrl() {
			return ipnNotificationUrl;
		}
		public void setIpnNotificationUrl(String ipnNotificationUrl) {
			this.ipnNotificationUrl = ipnNotificationUrl;
		}
		public String getSenderEmail() {
			return senderEmail;
		}
		public void setSenderEmail(String senderEmail) {
			this.senderEmail = senderEmail;
		}
		public String getStartingDate() {
			return startingDate;
		}
		public void setStartingDate(String startingDate) {
			this.startingDate = startingDate;
		}
		public PinType getPinType() {
			return pinType;
		}
		public void setPinType(PinType pinType) {
			this.pinType = pinType;
		}
		

	    public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public boolean isApproved() {
			return approved;
		}
		public void setApproved(boolean approved) {
			this.approved = approved;
		}
		public long getCurPayments() {
			return curPayments;
		}
		public void setCurPayments(long curPayments) {
			this.curPayments = curPayments;
		}
		public double getCurPaymentsAmount() {
			return curPaymentsAmount;
		}
		public void setCurPaymentsAmount(double curPaymentsAmount) {
			this.curPaymentsAmount = curPaymentsAmount;
		}
		public long getCurPeriodAttempts() {
			return curPeriodAttempts;
		}
		public void setCurPeriodAttempts(long curPeriodAttempts) {
			this.curPeriodAttempts = curPeriodAttempts;
		}
		public String getCurPeriodEndingDate() {
			return curPeriodEndingDate;
		}
		public void setCurPeriodEndingDate(String curPeriodEndingDate) {
			this.curPeriodEndingDate = curPeriodEndingDate;
		}
	    
		public PreapprovalDetails(){
			
		}
		
		public String serialize() throws UnsupportedEncodingException{
			// prepare request parameters
	    	StringBuilder postParameters = new StringBuilder();
	    	
		    // add dateOfMonth
		    if(this.dateOfMonth >= 0){
		    	postParameters.append(ParameterUtils.createUrlParameter("dateOfMonth", Integer.toString(this.dateOfMonth)));
		    	postParameters.append(ParameterUtils.PARAM_SEP);
		    }
		    // add dayOfWeek
		    if(this.dayOfWeek != null){
		    	postParameters.append(ParameterUtils.createUrlParameter("dayOfWeek", this.dayOfWeek.toString()));
		    	postParameters.append(ParameterUtils.PARAM_SEP);
		    }
		    // add endingDate
		    if(this.endingDate != null){
		    	postParameters.append(ParameterUtils.createUrlParameter("endingDate", this.endingDate));
		    	postParameters.append(ParameterUtils.PARAM_SEP);
		    }
		    // add startingDate
		    if(this.startingDate != null){
		    	postParameters.append(ParameterUtils.createUrlParameter("startingDate", this.startingDate));
		    	postParameters.append(ParameterUtils.PARAM_SEP);
		    }
	    	// add senderEmail if set
	    	if(this.senderEmail != null) {
	    		postParameters.append(ParameterUtils.createUrlParameter("senderEmail", this.senderEmail));
	    		postParameters.append(ParameterUtils.PARAM_SEP);        	
	    	}
	    	// add maxAmountPerPayment if set
	    	if(this.maxAmountPerPayment > 0) {
	    		postParameters.append(ParameterUtils.createUrlParameter("maxAmountPerPayment", Double.toString(this.maxAmountPerPayment)));
	    		postParameters.append(ParameterUtils.PARAM_SEP);        	
	    	}
	    	// add maxTotalAmountOfAllPayments if set
	    	if(this.maxTotalAmountOfAllPayments > 0) {
	    		postParameters.append(ParameterUtils.createUrlParameter("maxTotalAmountOfAllPayments", Double.toString(this.maxTotalAmountOfAllPayments)));
	    		postParameters.append(ParameterUtils.PARAM_SEP);        	
	    	}
	    	
		    // add maxNumberOfPayments if set
	    	if(this.maxNumberOfPayments > 0) {
	    		postParameters.append(ParameterUtils.createUrlParameter("maxNumberOfPayments", Integer.toString(this.maxNumberOfPayments)));
	    		postParameters.append(ParameterUtils.PARAM_SEP);        	
	    	}
	    	// add maxNumberOfPaymentsPerPeriod if set
	    	if(this.maxNumberOfPaymentsPerPeriod > 0) {
	    		postParameters.append(ParameterUtils.createUrlParameter("maxNumberOfPaymentsPerPeriod", Integer.toString(this.maxNumberOfPaymentsPerPeriod)));
	    		postParameters.append(ParameterUtils.PARAM_SEP);        	
	    	}
		    
	    	// add paymentPeriod
	    	postParameters.append(ParameterUtils.createUrlParameter("paymentPeriod", this.getPaymentPeriod().toString()));
	    	postParameters.append(ParameterUtils.PARAM_SEP);
	    	// add pinType
	    	postParameters.append(ParameterUtils.createUrlParameter("pinType", this.getPinType().toString()));
	    	postParameters.append(ParameterUtils.PARAM_SEP);
	    	
	    	// add currencyCode
	    	postParameters.append(ParameterUtils.createUrlParameter("currencyCode", this.getCurrencyCode().toString()));
	    	postParameters.append(ParameterUtils.PARAM_SEP);
	    	// add memo
	    	if(memo != null) {
	    		postParameters.append(ParameterUtils.createUrlParameter("memo", this.getMemo()));
	    		postParameters.append(ParameterUtils.PARAM_SEP);
	    	}
	    	// add cancel/return urls
	    	postParameters.append(ParameterUtils.createUrlParameter("cancelUrl", this.getCancelUrl()));
	    	postParameters.append(ParameterUtils.PARAM_SEP);
	    	postParameters.append(ParameterUtils.createUrlParameter("returnUrl", this.getReturnUrl()));
	    	postParameters.append(ParameterUtils.PARAM_SEP);
	    	// add ipn url
	    	if(this.ipnNotificationUrl != null) {
	    		postParameters.append(ParameterUtils.createUrlParameter("ipnNotificationUrl", this.getIpnNotificationUrl()));
	    		postParameters.append(ParameterUtils.PARAM_SEP);
	    	}
	    	
	    	
	    	
	    	return postParameters.toString();
		}
		
		public PreapprovalDetails(HashMap<String, String> preapprovalDetailsResponseParams){
			
			if(preapprovalDetailsResponseParams.containsKey("cancelUrl"))
				this.cancelUrl = preapprovalDetailsResponseParams.get("cancelUrl");
			if(preapprovalDetailsResponseParams.containsKey("returnUrl"))
				this.returnUrl = preapprovalDetailsResponseParams.get("returnUrl");
			if(preapprovalDetailsResponseParams.containsKey("ipnNotificationUrl"))
				this.ipnNotificationUrl = preapprovalDetailsResponseParams.get("ipnNotificationUrl");
			if(preapprovalDetailsResponseParams.containsKey("dateOfMonth"))
				this.dateOfMonth = Integer.parseInt(preapprovalDetailsResponseParams.get("dateOfMonth"));
			if(preapprovalDetailsResponseParams.containsKey("dayOfWeek"))
				this.dayOfWeek = DayOfWeek.valueOf(preapprovalDetailsResponseParams.get("dayOfWeek"));
			if(preapprovalDetailsResponseParams.containsKey("endingDate"))
				this.endingDate = preapprovalDetailsResponseParams.get("endingDate");
			if(preapprovalDetailsResponseParams.containsKey("memo"))
				this.memo = preapprovalDetailsResponseParams.get("memo");
			if(preapprovalDetailsResponseParams.containsKey("senderEmail"))
				this.senderEmail = preapprovalDetailsResponseParams.get("senderEmail");
			if(preapprovalDetailsResponseParams.containsKey("currencyCode"))
				this.currencyCode = CurrencyCodes.valueOf(preapprovalDetailsResponseParams.get("currencyCode"));
			if(preapprovalDetailsResponseParams.containsKey("startingDate"))
				this.startingDate = preapprovalDetailsResponseParams.get("startingDate");
			if(preapprovalDetailsResponseParams.containsKey("status"))
				this.status = preapprovalDetailsResponseParams.get("status");
			if(preapprovalDetailsResponseParams.containsKey("curPeriodEndingDate"))
				this.curPeriodEndingDate = preapprovalDetailsResponseParams.get("curPeriodEndingDate");
			
			if(preapprovalDetailsResponseParams.containsKey("maxAmountPerPayment"))
				this.maxAmountPerPayment = Double.parseDouble(preapprovalDetailsResponseParams.get("maxAmountPerPayment"));
			if(preapprovalDetailsResponseParams.containsKey("maxTotalAmountOfAllPayments"))
				this.maxTotalAmountOfAllPayments = Double.parseDouble(preapprovalDetailsResponseParams.get("maxTotalAmountOfAllPayments"));
			if(preapprovalDetailsResponseParams.containsKey("curPaymentsAmount"))
				this.curPaymentsAmount = Double.parseDouble(preapprovalDetailsResponseParams.get("curPaymentsAmount"));
			if(preapprovalDetailsResponseParams.containsKey("maxNumberOfPayments"))
				this.maxNumberOfPayments = Integer.parseInt(preapprovalDetailsResponseParams.get("maxNumberOfPayments"));
			if(preapprovalDetailsResponseParams.containsKey("maxNumberOfPaymentsPerPeriod"))
				this.maxNumberOfPaymentsPerPeriod = Integer.parseInt(preapprovalDetailsResponseParams.get("maxNumberOfPaymentsPerPeriod"));
			if(preapprovalDetailsResponseParams.containsKey("approved"))
				this.approved = Boolean.parseBoolean(preapprovalDetailsResponseParams.get("approved"));
			if(preapprovalDetailsResponseParams.containsKey("curPayments"))
				this.curPayments = Long.parseLong(preapprovalDetailsResponseParams.get("curPayments"));
			if(preapprovalDetailsResponseParams.containsKey("curPeriodAttempts"))
				this.curPeriodAttempts = Long.parseLong(preapprovalDetailsResponseParams.get("curPeriodAttempts"));

			if(preapprovalDetailsResponseParams.containsKey("pinType"))
				this.pinType = PinType.valueOf(preapprovalDetailsResponseParams.get("pinType"));

			if(preapprovalDetailsResponseParams.containsKey("paymentPeriod"))
				this.paymentPeriod = PaymentPeriodType.valueOf(preapprovalDetailsResponseParams.get("paymentPeriod"));
					
		}
}
