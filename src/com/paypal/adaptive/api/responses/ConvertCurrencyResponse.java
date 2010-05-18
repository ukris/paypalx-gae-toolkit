
package com.paypal.adaptive.api.responses;

import java.util.ArrayList;
import java.util.HashMap;

import com.paypal.adaptive.core.AckCode;
import com.paypal.adaptive.core.CurrencyConversionList;
import com.paypal.adaptive.core.PayError;
import com.paypal.adaptive.core.ResponseEnvelope;
import com.paypal.adaptive.exceptions.PayPalErrorException;

/**
 * ConvertCurrencyResponse is returned as a result of the ConvertCurrency API operation.
 */
public class ConvertCurrencyResponse {

	HashMap<String, String> convertCurrencyResponseParams;
	protected ResponseEnvelope responseEnvelope;
	protected ArrayList<PayError> errorList;
	protected ArrayList<CurrencyConversionList> estimatedAmountTable;
	
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

    public ArrayList<PayError> getErrorList() {
		return errorList;
	}

	public void setErrorList(ArrayList<PayError> errorList) {
		this.errorList = errorList;
	}


    public ConvertCurrencyResponse(String responseString) throws PayPalErrorException{
    	
    	convertCurrencyResponseParams = new HashMap<String, String>();
    	// parse the string and load into the object
    	String[] nmValPairs = responseString.split("&");
    	for(String nmVal: nmValPairs){
    		String[] field = nmVal.split("=");
    		convertCurrencyResponseParams.put(field[0], (field.length > 1)?field[1].trim():"");
    	}

    	responseEnvelope = new ResponseEnvelope(convertCurrencyResponseParams);
    	    	
    	errorList = new ArrayList<PayError>();
    	// we will parse 10 errors for now
    	for(int i = 0; i < 10; i++){
    		if(convertCurrencyResponseParams.containsKey("error(" + i +").errorId")){
    			PayError error = new PayError(convertCurrencyResponseParams, i);
    			errorList.add(error);
    		} else {
    			break;
    		}
    	}
    	/*
    	 *  &estimatedAmountTable.currencyConversionList(0).baseAmount.code=GBP
    	 * &estimatedAmountTable.currencyConversionList(0).baseAmount.amount=1.0
		 * &estimatedAmountTable.currencyConversionList(0).currencyList.currency(0).code=JPY
		 * &estimatedAmountTable.currencyConversionList(0).currencyList.currency(0).amount=231
		 * &estimatedAmountTable.currencyConversionList(1).baseAmount.code=EUR
		 * &estimatedAmountTable.currencyConversionList(1).baseAmount.amount=100.0
		 * &estimatedAmountTable.currencyConversionList(1).currencyList.currency(0).code=JPY
		 * &estimatedAmountTable.currencyConversionList(1).currencyList.currency(0).amount=15764
	   	 */
    	// parse estimatedAmountTable objects
    	for(int i=0; i<10;i++){
    		if(convertCurrencyResponseParams.containsKey(
    				"estimatedAmountTable.currencyConversionList(" + i + ").baseAmount.code")){
    			CurrencyConversionList convList = new CurrencyConversionList(convertCurrencyResponseParams, i);
    			this.addToEstimatedAmountTable(convList);
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

	public ArrayList<CurrencyConversionList> getEstimatedAmountTable() {
		return estimatedAmountTable;
	}

	public void setEstimatedAmountTable(
			ArrayList<CurrencyConversionList> estimatedAmountTable) {
		this.estimatedAmountTable = estimatedAmountTable;
	}
	
	public void addToEstimatedAmountTable(CurrencyConversionList convList){
		if(this.estimatedAmountTable == null){
			this.estimatedAmountTable = new ArrayList<CurrencyConversionList>();
		}
		this.estimatedAmountTable.add(convList);
	}


}
