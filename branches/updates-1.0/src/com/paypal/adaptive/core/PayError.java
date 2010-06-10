/**
 * 
 */
package com.paypal.adaptive.core;

import java.util.HashMap;

/**
 * <p>Java class for PayError.
 */
public class PayError  {

	protected ErrorData error;
	protected Receiver receiver;
	
	/**
	 * @param error the error to set
	 */
	public void setError(ErrorData error) {
		this.error = error;
	}
	/**
	 * @return the error
	 */
	public ErrorData getError() {
		return error;
	}
	/**
	 * @param receiver the receiver to set
	 */
	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}
	/**
	 * @return the receiver
	 */
	public Receiver getReceiver() {
		return receiver;
	}
	
	public PayError(HashMap<String, String> params, int index){
		error = new ErrorData(params, index);
		if(params.containsKey("error(" + index +").receiver.amount")
				|| params.containsKey("error(" + index +").receiver.email")
				|| params.containsKey("error(" + index +").receiver.paymentType")
				|| params.containsKey("error(" + index +").receiver.phone")
				|| params.containsKey("error(" + index +").receiver.primary")
				|| params.containsKey("error(" + index +").receiver.invoiceId")){
			this.receiver = new Receiver(params, index);
		}
	}
	
}
