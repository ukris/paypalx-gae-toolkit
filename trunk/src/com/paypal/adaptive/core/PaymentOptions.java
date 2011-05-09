package com.paypal.adaptive.core;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

public class PaymentOptions {
	
	protected InitiatingEntity initiatingEntity;
	protected DisplayOptions displayOptions;
	protected String shippingAddressId;
	protected SenderOptions senderOptions;
	protected ReceiverOptions receiverOptions;

	/**
	 * @return the initiatingEntity
	 */
	public InitiatingEntity getInitiatingEntity() {
		return initiatingEntity;
	}
	/**
	 * @param initiatingEntity the initiatingEntity to set
	 */
	public void setInitiatingEntity(InitiatingEntity initiatingEntity) {
		this.initiatingEntity = initiatingEntity;
	}
	/**
	 * @return the displayOptions
	 */
	public DisplayOptions getDisplayOptions() {
		return displayOptions;
	}
	/**
	 * @param displayOptions the displayOptions to set
	 */
	public void setDisplayOptions(DisplayOptions displayOptions) {
		this.displayOptions = displayOptions;
	}
	/**
	 * @return the shippingAddressId
	 */
	public String getShippingAddressId() {
		return shippingAddressId;
	}
	/**
	 * @param shippingAddressId the shippingAddressId to set
	 */
	public void setShippingAddressId(String shippingAddressId) {
		this.shippingAddressId = shippingAddressId;
	}
	/**
	 * @return the senderOptions
	 */
	public SenderOptions getSenderOptions() {
		return senderOptions;
	}
	/**
	 * @param senderOptions the senderOptions to set
	 */
	public void setSenderOptions(SenderOptions senderOptions) {
		this.senderOptions = senderOptions;
	}
	/**
	 * @return the receiverOptions
	 */
	public ReceiverOptions getReceiverOptions() {
		return receiverOptions;
	}
	/**
	 * @param receiverOptions the receiverOptions to set
	 */
	public void setReceiverOptions(ReceiverOptions receiverOptions) {
		this.receiverOptions = receiverOptions;
	}

	public PaymentOptions(){
		// default constructor
	}
	
	public PaymentOptions(HashMap<String, String> params){

		this.initiatingEntity = new InitiatingEntity(params);
		
		this.displayOptions = new DisplayOptions(params);
		
		if(params.containsKey("shippingAddressId")) {
			this.shippingAddressId = params.get("shippingAddressId");
		}
		
		this.senderOptions = new SenderOptions(params);
		
		this.receiverOptions = new ReceiverOptions(params);
		
	}
	
	public String serialize() throws UnsupportedEncodingException{
		// prepare request parameters
		StringBuilder postParameters = new StringBuilder();
		// add initiatingEntity if set
		if(this.initiatingEntity != null) {
			postParameters.append(this.initiatingEntity.serialize());
			postParameters.append(ParameterUtils.PARAM_SEP);        	
		}
		// add displayOptions if set
		if(this.displayOptions != null) {
			postParameters.append(this.displayOptions.serialize());
			postParameters.append(ParameterUtils.PARAM_SEP);        	
		}
		// add shippingAddressId if set
		if(this.shippingAddressId != null) {
			postParameters.append(ParameterUtils.createUrlParameter("shippingAddressId", this.shippingAddressId));
			postParameters.append(ParameterUtils.PARAM_SEP);        	
		}
		// add senderOptions if set
		if(this.senderOptions != null) {
			postParameters.append(this.senderOptions.serialize());
			postParameters.append(ParameterUtils.PARAM_SEP);        	
		}
		// add receiverOptions if set
		if(this.receiverOptions != null) {
			postParameters.append(this.receiverOptions.serialize());
			postParameters.append(ParameterUtils.PARAM_SEP);        	
		}

		return postParameters.toString();
	}


public String toString(){
		
		StringBuilder outStr = new StringBuilder();
		
		outStr.append("<table>");
		outStr.append("<tr><th>");
		outStr.append(this.getClass().getSimpleName());
		outStr.append("</th><td></td></tr>");
		BeanInfo info;
		try {
			info = Introspector.getBeanInfo( this.getClass(), Object.class );
			for ( PropertyDescriptor pd : info.getPropertyDescriptors() ) {
				try {
					String name = pd.getName();
					Object value = this.getClass().getDeclaredField(name).get(this);
					if(value != null) {
						outStr.append("<tr><td>");
						outStr.append(pd.getName());
						outStr.append("</td><td>");
						outStr.append(value.toString());
					}
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				outStr.append("</td></tr>");
			}
	    } catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		outStr.append("</table>");
		return outStr.toString(); 
		
	}
}
