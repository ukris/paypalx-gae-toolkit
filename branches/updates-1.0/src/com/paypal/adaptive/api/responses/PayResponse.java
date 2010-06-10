
package com.paypal.adaptive.api.responses;

import java.util.ArrayList;
import java.util.HashMap;

import com.paypal.adaptive.core.AckCode;
import com.paypal.adaptive.core.PayError;
import com.paypal.adaptive.core.PaymentExecStatus;
import com.paypal.adaptive.core.ResponseEnvelope;
import com.paypal.adaptive.exceptions.AuthorizationRequiredException;
import com.paypal.adaptive.exceptions.PayPalErrorException;
import com.paypal.adaptive.exceptions.PaymentExecException;
import com.paypal.adaptive.exceptions.PaymentInCompleteException;


/**
 * PayResponse is returned as a result of the Pay API operation.
 */
public class PayResponse {

	HashMap<String, String> payResponseParams;
	protected ResponseEnvelope responseEnvelope;
	protected String payKey;
	protected PaymentExecStatus paymentExecStatus;
	protected ArrayList<PayError> payErrorList;
    
    public PayResponse(String responseString) 
    throws PayPalErrorException, PaymentExecException, 
    	   AuthorizationRequiredException, PaymentInCompleteException{
    	
    	payResponseParams = new HashMap<String, String>();
    	// parse the string and load into the object
    	String[] nmValPairs = responseString.split("&");
    	for(String nmVal: nmValPairs){
    		String[] field = nmVal.split("=");
    		payResponseParams.put(field[0].trim(), (field.length > 1)?field[1].trim():"");
    	}

    	if(payResponseParams.containsKey("payKey")){
    		this.payKey = payResponseParams.get("payKey");
    	}
    	
    	if(payResponseParams.containsKey("paymentExecStatus")){
    		this.paymentExecStatus = PaymentExecStatus.valueOf(payResponseParams.get("paymentExecStatus"));
    	}
    	responseEnvelope = new ResponseEnvelope(payResponseParams);
    	
    	payErrorList = new ArrayList<PayError>();
    	// we will parse 10 errors at max for now
    	for(int i = 0; i < 10; i++){
    		if(payResponseParams.containsKey("error(" + i +").errorId")){
    			PayError pErr = new PayError( payResponseParams, i);
    			payErrorList.add(pErr);
    		} else {
    			break;
    		}
    	}
    	
    	// if there is an API level error handle those first - look for responseEnvelope/ack
    	if(responseEnvelope.getAck() == AckCode.Failure
    		|| responseEnvelope.getAck() == AckCode.FailureWithWarning){
    		// throw error
    		throw new PayPalErrorException(responseEnvelope, payErrorList);
    	}
    	
    	// if it's a payment execution error throw an exception
    	if(paymentExecStatus != null){
    		if(paymentExecStatus == PaymentExecStatus.ERROR ) {
    			
    			PaymentExecException peex = new PaymentExecException(paymentExecStatus);
    			peex.setPayErrorList(payErrorList);
    			peex.setResponseEnvelope(responseEnvelope);
    			throw peex;
    			
    		} else if( paymentExecStatus == PaymentExecStatus.INCOMPLETE
    				|| paymentExecStatus == PaymentExecStatus.REVERSALERROR ){
    			PaymentInCompleteException ex = new PaymentInCompleteException(paymentExecStatus);
    			ex.setPayErrorList(payErrorList);
    			ex.setPayKey(payKey);
    			ex.setResponseEnvelope(responseEnvelope);
    			throw ex;
    		
    		} else if(paymentExecStatus == PaymentExecStatus.CREATED){
    			// throw exception to redirect user for authorization
    			AuthorizationRequiredException ex = new AuthorizationRequiredException();
    			ex.setPayKey(payKey);
    			throw ex;
    		} else if(paymentExecStatus == PaymentExecStatus.COMPLETED
    				|| paymentExecStatus == PaymentExecStatus.PROCESSING
    				|| paymentExecStatus == PaymentExecStatus.PENDING){
    			// no further action required so treat these as success
    		} else {
    			// unknown paymentExecStatus - throw exception
    			PaymentExecException peex = new PaymentExecException(paymentExecStatus);
    			peex.setPayErrorList(payErrorList);
    			peex.setResponseEnvelope(responseEnvelope);
    			throw peex;
    		}
    	}
    	
    }
	/**
	 * @param responseEnvelope the responseEnvelope to set
	 */
	public void setResponseEnvelope(ResponseEnvelope responseEnvelope) {
		this.responseEnvelope = responseEnvelope;
	}
	/**
	 * @return the responseEnvelope
	 */
	public ResponseEnvelope getResponseEnvelope() {
		return responseEnvelope;
	}
	/**
	 * @param payKey the payKey to set
	 */
	public void setPayKey(String payKey) {
		this.payKey = payKey;
	}
	/**
	 * @return the payKey
	 */
	public String getPayKey() {
		return payKey;
	}
	/**
	 * @param paymentExecStatus the paymentExecStatus to set
	 */
	public void setPaymentExecStatus(PaymentExecStatus paymentExecStatus) {
		this.paymentExecStatus = paymentExecStatus;
	}
	/**
	 * @return the paymentExecStatus
	 */
	public PaymentExecStatus getPaymentExecStatus() {
		return paymentExecStatus;
	}
	/**
	 * @param payErrorList the payErrorList to set
	 */
	public void setPayErrorList(ArrayList<PayError> payErrorList) {
		this.payErrorList = payErrorList;
	}
	
	/**
	 * @param errorList the payErrorList to set
	 */
	public void addToPayErrorList(PayError payError) {
		if(this.payErrorList == null)
			this.payErrorList = new ArrayList<PayError>();
		
		this.payErrorList.add(payError);
	}
	
	/**
	 * @return the payErrorList
	 */
	public ArrayList<PayError> getPayErrorList() {
		return payErrorList;
	}
    

	public boolean isPaymentCREATED(){
		return this.paymentExecStatus  == PaymentExecStatus.CREATED;
	}
	
	public boolean isPaymentCOMPLETED(){
		return this.paymentExecStatus  == PaymentExecStatus.COMPLETED;
	}
	
	public boolean isPaymentINCOMPLETE(){
		return this.paymentExecStatus  == PaymentExecStatus.INCOMPLETE;
	}
	
	public boolean isPaymentERROR(){
		return this.paymentExecStatus  == PaymentExecStatus.ERROR;
	}
	
	public boolean isPaymentREVERSALERROR(){
		return this.paymentExecStatus  == PaymentExecStatus.REVERSALERROR;
	}
	
	public boolean isPaymentPROCESSING(){
		return this.paymentExecStatus  == PaymentExecStatus.PROCESSING;
	}
	
	public boolean isPaymentPENDING(){
		return this.paymentExecStatus  == PaymentExecStatus.PENDING;
	}

}
