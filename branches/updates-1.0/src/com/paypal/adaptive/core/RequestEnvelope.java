/**
 * 
 */
package com.paypal.adaptive.core;

import java.io.UnsupportedEncodingException;


/**
 * <p>Java class for RequestEnvelop.
 * 
 */
public class RequestEnvelope{

	public static final String DEFAULT_DETAIL_LEVEL_CODE = "ReturnAll";
	protected String errorLanguage;
	protected String detailLevel = DEFAULT_DETAIL_LEVEL_CODE;
	/**
	 * @param errorLanguage the errorLanguage to set
	 */
	public void setErrorLanguage(String errorLanguage) {
		this.errorLanguage = errorLanguage;
	}
	/**
	 * @return the errorLanguage
	 */
	public String getErrorLanguage() {
		return errorLanguage;
	}
	/**
	 * @param detailLevel the detailLevel to set
	 */
	public void setDetailLevel(String detailLevel) {
		this.detailLevel = detailLevel;
	}
	/**
	 * @return the detailLevel
	 */
	public String getDetailLevel() {
		return detailLevel;
	}
	
	public String serialize() throws UnsupportedEncodingException{
		return ParameterUtils.createUrlParameter("requestEnvelope.errorLanguage", this.getErrorLanguage());
	}
}
