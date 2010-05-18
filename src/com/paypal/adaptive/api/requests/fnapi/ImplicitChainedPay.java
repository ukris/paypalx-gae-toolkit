/**
 * 
 */
package com.paypal.adaptive.api.requests.fnapi;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.paypal.adaptive.api.requests.PayRequest;
import com.paypal.adaptive.api.responses.PayResponse;
import com.paypal.adaptive.core.APICredential;
import com.paypal.adaptive.core.ActionType;
import com.paypal.adaptive.core.ClientDetails;
import com.paypal.adaptive.core.CurrencyCodes;
import com.paypal.adaptive.core.PaymentDetails;
import com.paypal.adaptive.core.PaymentType;
import com.paypal.adaptive.core.Receiver;
import com.paypal.adaptive.core.ServiceEnvironment;
import com.paypal.adaptive.exceptions.AuthorizationRequiredException;
import com.paypal.adaptive.exceptions.InvalidAPICredentialsException;
import com.paypal.adaptive.exceptions.InvalidPrimaryReceiverAmountException;
import com.paypal.adaptive.exceptions.InvalidResponseDataException;
import com.paypal.adaptive.exceptions.MissingAPICredentialsException;
import com.paypal.adaptive.exceptions.MissingParameterException;
import com.paypal.adaptive.exceptions.NotEnoughReceivers;
import com.paypal.adaptive.exceptions.PayPalErrorException;
import com.paypal.adaptive.exceptions.PaymentExecException;
import com.paypal.adaptive.exceptions.PaymentInCompleteException;
import com.paypal.adaptive.exceptions.PaymentTypeNotAllowedException;
import com.paypal.adaptive.exceptions.ReceiversCountMismatchException;
import com.paypal.adaptive.exceptions.RequestAlreadyMadeException;
import com.paypal.adaptive.exceptions.RequestFailureException;

/**
 * <p>Java class to send implicit chained payment.
 *
 */
public class ImplicitChainedPay {

	/*
	 * Required applicationName
	 */
	protected String applicationName;
	/*
	 * Required APICredential
	 */
	protected APICredential credentialObj;
	/*
	 * Required Primary Receiver info
	 */
	protected Receiver primaryReceiver;
	/*
	 * Required Secondary Receivers info
	 */
	protected List<Receiver> secondaryReceivers;
	/*
	 * Required Environment
	 */
	protected ServiceEnvironment env;
    /*
     * Required memo
     */
    protected String memo;
    /*
     * Required CurrencyCode
     */
    protected CurrencyCodes currencyCode;
    /*
     * Required language for localization
     */
    protected String language;
    /*
     * Required Client IP Address where the request is sent from
     */
    protected String clientIp;
    /*
     * Optional ipnURL
     */
    protected String ipnURL;
  
    // internal field
    protected boolean requestProcessed = false;
    // internal field
    protected int numberOfSecondaryReceivers;
    /*
     * Default constructor
     */
    public ImplicitChainedPay(int numberOfSecondaryReceivers) throws NotEnoughReceivers{
    	if(numberOfSecondaryReceivers < 1){
    		// throw exception
    		throw new NotEnoughReceivers(1, numberOfSecondaryReceivers);
    	}
    	this.numberOfSecondaryReceivers = numberOfSecondaryReceivers;
		this.secondaryReceivers = new ArrayList<Receiver>(numberOfSecondaryReceivers);
	}
    
	public PayResponse makeRequest()
	throws IOException, MalformedURLException, MissingAPICredentialsException,
	   		InvalidAPICredentialsException, MissingParameterException, 
	   		UnsupportedEncodingException, RequestFailureException,
	   		InvalidResponseDataException, PayPalErrorException,
	   		RequestAlreadyMadeException, PaymentExecException,
	   		AuthorizationRequiredException, PaymentInCompleteException,
	   		ReceiversCountMismatchException, PaymentTypeNotAllowedException,
	   		InvalidPrimaryReceiverAmountException{
		
		validate();
		
		PaymentDetails paymentDetails = new PaymentDetails(ActionType.PAY);
		PayRequest payRequest = new PayRequest(language, env);
		
		paymentDetails.addToReceiverList(primaryReceiver);
		paymentDetails.addAllToReceiverList(secondaryReceivers);
		
		if(ipnURL != null){
			paymentDetails.setIpnNotificationUrl(ipnURL);
		}
		paymentDetails.setCurrencyCode(currencyCode);
		// Implicit payments do not require cancel and returnUrl as there is 
		// no authorization required - this is a bug in Pay API
		paymentDetails.setCancelUrl("http://www.paypal.com");
		paymentDetails.setReturnUrl("http://www.paypal.com");
		
		paymentDetails.setSenderEmail(credentialObj.getAccountEmail());

		// set clientDetails
		ClientDetails clientDetails = new ClientDetails();
		clientDetails.setIpAddress(clientIp);
		clientDetails.setApplicationId(applicationName);
		payRequest.setClientDetails(clientDetails);
		
		// set payment details
		payRequest.setPaymentDetails(paymentDetails);
		PayResponse payResp = payRequest.execute(credentialObj);
		return payResp;
	}
	
	public void validate() 
	throws MissingParameterException,RequestAlreadyMadeException, ReceiversCountMismatchException,
		PaymentTypeNotAllowedException, InvalidPrimaryReceiverAmountException {
		
		if(requestProcessed){
			// throw error
			throw new RequestAlreadyMadeException();
		}
		if(language == null){
			// throw error
			throw new MissingParameterException("language");
		}
		if(primaryReceiver == null){
			// throw error
			throw new MissingParameterException("primaryReceiver");
		}
		if(secondaryReceivers == null){
			// throw error
			throw new MissingParameterException("secondaryReceivers");
		}
		if(numberOfSecondaryReceivers != secondaryReceivers.size()) {
			// throw error
			throw new ReceiversCountMismatchException(numberOfSecondaryReceivers, secondaryReceivers.size());
		}
		if(currencyCode == null){
			// throw error
			throw new MissingParameterException("CurrencyCode");
		}
		if(credentialObj.getAccountEmail() == null){
			// throw error
			throw new MissingParameterException("AccountEmail");
		}
		if(env == null){
			// throw error
			throw new MissingParameterException("ServiceEnvironment");
		}
		if(memo == null){
			// throw error
			throw new MissingParameterException("memo");
		}
		if(clientIp == null){
			// throw error
			throw new MissingParameterException("clientIp");
		}
		if(applicationName == null){
			// throw error
			throw new MissingParameterException("applicationName");
		}
		// make sure Paymenttype is not PERSONAL and CASHADVANCE - they are not allowed for Chained Pay
		if(primaryReceiver.getPaymentType() == PaymentType.CASHADVANCE
				|| primaryReceiver.getPaymentType() == PaymentType.PERSONAL){
			// throw error
			throw new PaymentTypeNotAllowedException(primaryReceiver.getPaymentType());
		}
		for(Receiver recv:secondaryReceivers){
			if(recv.getPaymentType() == PaymentType.CASHADVANCE
					|| recv.getPaymentType() == PaymentType.PERSONAL){
				// throw error
				throw new PaymentTypeNotAllowedException(recv.getPaymentType());
			}
		}
		// amount received by primary receiver should be >= sum of amounts received by secondary receivers
		double sumOfSecondaryReceiversAmount = 0;
		for(Receiver recv:secondaryReceivers){
			sumOfSecondaryReceiversAmount += recv.getAmount();
		}
		if(primaryReceiver.getAmount() < sumOfSecondaryReceiversAmount){
			throw new InvalidPrimaryReceiverAmountException(primaryReceiver.getAmount(), sumOfSecondaryReceiversAmount);
		}
		
	}

	/**
	 * @return the credentialObj
	 */
	public APICredential getCredentialObj() {
		return credentialObj;
	}

	/**
	 * @param credentialObj the credentialObj to set
	 */
	public void setCredentialObj(APICredential credentialObj) {
		this.credentialObj = credentialObj;
	}

	/**
	 * @return the env
	 */
	public ServiceEnvironment getEnv() {
		return env;
	}

	/**
	 * @param env the env to set
	 */
	public void setEnv(ServiceEnvironment env) {
		this.env = env;
	}

	/**
	 * @return the memo
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * @param memo the memo to set
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	 * @return the currencyCode
	 */
	public CurrencyCodes getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setCurrencyCode(CurrencyCodes currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

	
	/**
	 * @return the ipnURL
	 */
	public String getIpnURL() {
		return ipnURL;
	}

	/**
	 * @param ipnURL the ipnURL to set
	 */
	public void setIpnURL(String ipnURL) {
		this.ipnURL = ipnURL;
	}

	/**
	 * @return the clientIp
	 */
	public String getClientIp() {
		return clientIp;
	}

	/**
	 * @param clientIp the clientIp to set
	 */
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	/**
	 * @return the applicationName
	 */
	public String getApplicationName() {
		return applicationName;
	}

	/**
	 * @param applicationName the applicationName to set
	 */
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	/**
	 * @return the primaryReceiver
	 */
	public Receiver getPrimaryReceiver() {
		return primaryReceiver;
	}

	/**
	 * @param primaryReceiver the primaryReceiver to set
	 */
	public void setPrimaryReceiver(Receiver primaryReceiver) {
		this.primaryReceiver = primaryReceiver;
		// if the primary flag is not set - set it as this is implied
		if(!this.primaryReceiver.isPrimary())
			this.primaryReceiver.setPrimary(true);
	}

	/**
	 * @return the secondaryReceivers
	 */
	public List<Receiver> getSecondaryReceivers() {
		return secondaryReceivers;
	}

	/**
	 * @param secondaryReceivers the secondaryReceivers to set
	 */
	public void setSecondaryReceivers(List<Receiver> secondaryReceivers) {
		this.secondaryReceivers = secondaryReceivers;
	}
	/**
	 * @param secondaryReceiver the secondaryReceiver to add
	 */
	public void addToSecondaryReceivers(Receiver secondaryReceiver) {
		this.secondaryReceivers.add(secondaryReceiver);
	}
}
