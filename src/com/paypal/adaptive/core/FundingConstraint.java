
package com.paypal.adaptive.core;

import java.io.UnsupportedEncodingException;



/**
 * <p>Java class for FundingConstraint complex type.
 * 
 */

public class FundingConstraint {

	protected FundingType fundingType;

	/**
	 * Gets the value of the fundingType property.
	 * 
	 * @return
	 *     possible object is
	 *     {@link String }
	 *     
	 */
	public FundingType getFundingType() {
	    return fundingType;
	}

	/**
	 * Sets the value of the fundingType property.
	 * 
	 * @param value
	 *     allowed object is
	 *     {@link String }
	 *     
	 */
	public void setFundingType(FundingType value) {
	    this.fundingType = value;
	}
	
	public String serialize(int index) throws UnsupportedEncodingException{
		// prepare request parameters
    	return ParameterUtils.createUrlParameter("fundingConstraint.allowedFundingType.fundingTypeInfo(" + index + ").fundingType", this.fundingType.toString());
	}

}
