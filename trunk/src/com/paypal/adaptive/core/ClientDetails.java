/**
 * 
 */
package com.paypal.adaptive.core;

import java.io.UnsupportedEncodingException;

/**
 * <p>Java class for ClientDetails.
 */
public class ClientDetails {

	protected String applicationId;
	protected String ipAddress;
	/**
	 * @param applicationId the applicationId to set
	 */
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	/**
	 * @return the applicationId
	 */
	public String getApplicationId() {
		return applicationId;
	}
	/**
	 * @param ipAddress the ipAddress to set
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	/**
	 * @return the ipAddress
	 */
	public String getIpAddress() {
		return ipAddress;
	}
	
	public String serialize() throws UnsupportedEncodingException{
		StringBuilder outString = new StringBuilder();
		outString.append(ParameterUtils.createUrlParameter("clientDetails.applicationId", this.applicationId));
		outString.append(ParameterUtils.PARAM_SEP);
		outString.append(ParameterUtils.createUrlParameter("clientDetails.ipAddress",this.ipAddress));
		outString.append(ParameterUtils.PARAM_SEP);
		outString.append(ParameterUtils.createUrlParameter("clientDetails.deviceId","mydevice"));
		
		return outString.toString();
	}
}
