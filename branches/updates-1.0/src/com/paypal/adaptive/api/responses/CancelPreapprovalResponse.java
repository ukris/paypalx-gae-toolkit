
package com.paypal.adaptive.api.responses;

import java.util.ArrayList;
import java.util.HashMap;

import com.paypal.adaptive.core.PayError;
import com.paypal.adaptive.core.ResponseEnvelope;


/**
 * CancelPreapprovalResponse is returned as a result of the CancelPreapproval API.
 */
public class CancelPreapprovalResponse {

	HashMap<String, String> preapprovalDetailsResponseParams;
	protected ResponseEnvelope responseEnvelope;
	protected ArrayList<PayError> payErrorList;

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

    public CancelPreapprovalResponse(String responseString){
    	
    	preapprovalDetailsResponseParams = new HashMap<String, String>();
    	// parse the string and load into the object
    	String[] nmValPairs = responseString.split("&");
    	for(String nmVal: nmValPairs){
    		String[] field = nmVal.split("=");
    		preapprovalDetailsResponseParams.put(field[0], (field.length > 1)?field[1].trim():"");
    	}

    	responseEnvelope = new ResponseEnvelope(preapprovalDetailsResponseParams);
    	    	
    	// we will parse 10 errors for now
    	for(int i = 0; i < 10; i++){
    		if(preapprovalDetailsResponseParams.containsKey("error(" + i +").errorId")){
    			PayError pErr = new PayError( preapprovalDetailsResponseParams, i);
    			payErrorList.add(pErr);
    		} else {
    			break;
    		}
    	}
    }


}
