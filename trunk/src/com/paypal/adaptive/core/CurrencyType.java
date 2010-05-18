
package com.paypal.adaptive.core;

import java.io.UnsupportedEncodingException;

/**
 * <p>Java class for CurrencyType.
 */
public class CurrencyType {

    protected CurrencyCodes code;
    protected double amount;

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public CurrencyCodes getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(CurrencyCodes value) {
        this.code = value;
    }

    /**
     * Gets the value of the amount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAmount(double value) {
        this.amount = value;
    }
    
    public String serialize(int index) throws UnsupportedEncodingException{
		StringBuilder outString = new StringBuilder();
		if(this.amount > 0) {
			outString.append(ParameterUtils.createUrlParameter("baseAmountList.currency(" + 
					index + ").amount", Double.toString(this.amount)));
			outString.append(ParameterUtils.PARAM_SEP);
		}
		if(this.code != null) {
			outString.append(ParameterUtils.createUrlParameter("baseAmountList.currency(" + 
					index + ").code", this.code.toString()));
			outString.append(ParameterUtils.PARAM_SEP);
		}
		return outString.toString();
	}

    public CurrencyType(CurrencyCodes code, double amount){
    	this.amount = amount;
    	this.code = code;
    }
}
