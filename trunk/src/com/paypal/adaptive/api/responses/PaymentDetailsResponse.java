/**
 * 
 */
package com.paypal.adaptive.api.responses;

import java.util.ArrayList;
import java.util.HashMap;

import com.paypal.adaptive.core.PayError;
import com.paypal.adaptive.core.PaymentDetails;
import com.paypal.adaptive.core.ResponseEnvelope;


/**
 * PaymentDetailsResponse is returned as a result of the PaymentDetails API operation..
 */
public class PaymentDetailsResponse {

	HashMap<String, String> payDetailsResponseParams;
    protected ResponseEnvelope responseEnvelope;
    
    protected PaymentDetails paymentDetails;
	protected ArrayList<PayError> payErrorList;


    public ArrayList<PayError> getPayErrorList() {
		return payErrorList;
	}


	public void setPayErrorList(ArrayList<PayError> payErrorList) {
		this.payErrorList = payErrorList;
	}


	public PaymentDetailsResponse(String responseString){
    	
    	payDetailsResponseParams = new HashMap<String, String>();
    	// parse the string and load into the object
    	String[] nmValPairs = responseString.split("&");
    	for(String nmVal: nmValPairs){
    		String[] field = nmVal.split("=");
    		payDetailsResponseParams.put(field[0], (field.length > 1)?field[1].trim():"");
    	}

    	responseEnvelope = new ResponseEnvelope(payDetailsResponseParams);
    	
    	paymentDetails = new PaymentDetails(payDetailsResponseParams);
    	
    	payErrorList = new ArrayList<PayError>();
    	// we will parse 10 errors for now
    	for(int i = 0; i < 10; i++){
    		if(payDetailsResponseParams.containsKey("error(" + i +").errorId")){
    			PayError pErr = new PayError( payDetailsResponseParams, i);
    			payErrorList.add(pErr);
    		} else {
    			break;
    		}
    	}
    }


	public ResponseEnvelope getResponseEnvelope() {
		return responseEnvelope;
	}


	public void setResponseEnvelope(ResponseEnvelope responseEnvelope) {
		this.responseEnvelope = responseEnvelope;
	}


	public PaymentDetails getPaymentDetails() {
		return paymentDetails;
	}


	public void setPaymentDetails(PaymentDetails paymentDetails) {
		this.paymentDetails = paymentDetails;
	}

	/*
	public String toString(){
		StringBuilder outStr = new StringBuilder();
		outStr.append("<table border=1>");
		outStr.append("<tr><th>PaymentDetailsResponse</th><td></td></tr>");
		outStr.append("<tr><td></td><td>");
		outStr.append(this.responseEnvelope.toString());
		outStr.append("</td></tr>");
		outStr.append("<tr><td></td><td>");
		outStr.append(this.paymentDetails.toString());
		outStr.append("</td></tr>");
		outStr.append("</table>");
		return outStr.toString();
		
	}*/
	
	
	
    
}
