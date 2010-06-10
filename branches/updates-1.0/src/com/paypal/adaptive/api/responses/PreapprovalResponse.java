
package com.paypal.adaptive.api.responses;

import java.util.ArrayList;
import java.util.HashMap;

import com.paypal.adaptive.core.AckCode;
import com.paypal.adaptive.core.PayError;
import com.paypal.adaptive.core.ResponseEnvelope;
import com.paypal.adaptive.exceptions.AuthorizationRequiredException;
import com.paypal.adaptive.exceptions.PayPalErrorException;


/**
 * PreapprovalResponse is returned as a result of the Preapproval API operation.
 */
public class PreapprovalResponse {

	HashMap<String, String> payResponseParams;
	protected ResponseEnvelope responseEnvelope;
	protected String preapprovalKey;
	protected ArrayList<PayError> errorList;
    
    public PreapprovalResponse(String responseString)
    throws PayPalErrorException, AuthorizationRequiredException{
    	
    	payResponseParams = new HashMap<String, String>();
    	// parse the string and load into the object
    	String[] nmValPairs = responseString.split("&");
    	for(String nmVal: nmValPairs){
    		String[] field = nmVal.split("=");
    		payResponseParams.put(field[0], (field.length > 1)?field[1].trim():"");
    	}

    	if(payResponseParams.containsKey("preapprovalKey")){
    		this.preapprovalKey = payResponseParams.get("preapprovalKey");
    	}

    	responseEnvelope = new ResponseEnvelope(payResponseParams);
    	
    	errorList = new ArrayList<PayError>();
    	// we will parse 10 errors for now
    	for(int i = 0; i < 10; i++){
    		if(payResponseParams.containsKey("error(" + i +").errorId")){
    			PayError pErr = new PayError( payResponseParams, i);
    			errorList.add(pErr);
    		} else {
    			break;
    		}
    	}
    	
    	// if there is an API level error handle those first - look for responseEnvelope/ack
    	if(responseEnvelope.getAck() == AckCode.Failure
    		|| responseEnvelope.getAck() == AckCode.FailureWithWarning){
    		// throw error
    		throw new PayPalErrorException(responseEnvelope, errorList);
    	}
    	if(responseEnvelope.getAck() == AckCode.Success
    			|| responseEnvelope.getAck() == AckCode.SuccessWithWarning){
    		// throw exception to redirect user for authorization
			AuthorizationRequiredException ex = new AuthorizationRequiredException();
			ex.setPreapprovalKey(preapprovalKey);
			throw ex;
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
	 * @param errorList the errorList to set
	 */
	public void seErrorList(ArrayList<PayError> errorList) {
		this.errorList = errorList;
	}
	
	/**
	 * @param errorList the payErrorList to set
	 */
	public void addToErrorList(ArrayList<PayError> errorList) {
		if(this.errorList == null)
			this.errorList = new ArrayList<PayError>();
		
		this.errorList = errorList;
	}
	
	/**
	 * @return the errorList
	 */
	public ArrayList<PayError> getErrorList() {
		return errorList;
	}
	public String getPreapprovalKey() {
		return preapprovalKey;
	}
	public void setPreapprovalKey(String preapprovalKey) {
		this.preapprovalKey = preapprovalKey;
	}
    



}
