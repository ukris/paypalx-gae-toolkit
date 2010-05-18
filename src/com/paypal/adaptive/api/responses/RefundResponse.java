
package com.paypal.adaptive.api.responses;

import java.util.ArrayList;
import java.util.HashMap;

import com.paypal.adaptive.core.AckCode;
import com.paypal.adaptive.core.CurrencyCodes;
import com.paypal.adaptive.core.PayError;
import com.paypal.adaptive.core.RefundInfo;
import com.paypal.adaptive.core.ResponseEnvelope;
import com.paypal.adaptive.exceptions.PayPalErrorException;

/**
 * RefundResponse is returned as a result of the Refund API operation..
 */
public class RefundResponse {

	HashMap<String, String> refundResponseParams;
    protected ResponseEnvelope responseEnvelope;
    protected CurrencyCodes currencyCode;
    protected ArrayList<RefundInfo> refundInfoList;
    protected ArrayList<PayError> errorList;
    
   
    public RefundResponse(String responseString) throws PayPalErrorException{
    	
    	refundResponseParams = new HashMap<String, String>();
    	// parse the string and load into the object
    	String[] nmValPairs = responseString.split("&");
    	for(String nmVal: nmValPairs){
    		String[] field = nmVal.split("=");
    		refundResponseParams.put(field[0], (field.length > 1)?field[1].trim():"");
    	}

    	if(refundResponseParams.containsKey("currencyCode")){
    		this.currencyCode = CurrencyCodes.valueOf(refundResponseParams.get("currencyCode"));
    	}
    	responseEnvelope = new ResponseEnvelope(refundResponseParams);
    	
    	// parse refundInfo objects
    	for(int i=0; i<10;i++){
    		if(refundResponseParams.containsKey("refundInfoList.refundInfo(" + i +").refundStatus")){
    			RefundInfo refInfo = new RefundInfo(refundResponseParams, i);
    			this.addToRefundInfoList(refInfo);
    		} else {
    			break;
    		}
    	}
    	errorList = new ArrayList<PayError>();
    	// we will parse 10 errors for now
    	for(int i = 0; i < 10; i++){
    		if(refundResponseParams.containsKey("error(" + i +").errorId")){
    			PayError error = new PayError(refundResponseParams, i);
    			errorList.add(error);
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
    	
    }

    public ArrayList<PayError> getErrorList() {
		return errorList;
	}

	public void setErrorList(ArrayList<PayError> errorList) {
		this.errorList = errorList;
	}

	/**
     * Gets the value of the responseEnvelope property.
     * 
     * @return
     *     possible object is
     *     {@link ResponseEnvelope }
     *     
     */
    public ResponseEnvelope getResponseEnvelope() {
        return responseEnvelope;
    }

    /**
     * Sets the value of the responseEnvelope property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseEnvelope }
     *     
     */
    public void setResponseEnvelope(ResponseEnvelope value) {
        this.responseEnvelope = value;
    }

    /**
     * Gets the value of the currencyCode property.
     * 
     * @return
     *     possible object is
     *     {@link CurrencyCodes }
     *     
     */
    public CurrencyCodes getCurrencyCode() {
        return currencyCode;
    }

    /**
     * Sets the value of the currencyCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CurrencyCodes }
     *     
     */
    public void setCurrencyCode(CurrencyCodes value) {
        this.currencyCode = value;
    }

    /**
     * Gets the value of the refundInfoList property.
     * 
     * @return
     *     possible object is
     *     {@link RefundInfoList }
     *     
     */
    public ArrayList<RefundInfo> getRefundInfoList() {
        return refundInfoList;
    }

    /**
     * Sets the value of the refundInfoList property.
     * 
     * @param value
     *     allowed object is
     *     {@link RefundInfoList }
     *     
     */
    public void setRefundInfoList(ArrayList<RefundInfo> value) {
        this.refundInfoList = value;
    }
    
    public void addToRefundInfoList(RefundInfo refundInfo) {
        if(this.refundInfoList == null)
        	this.refundInfoList = new ArrayList<RefundInfo>();
        this.refundInfoList.add(refundInfo);
    }
    

 

}
