
package com.paypal.adaptive.core;

import java.util.HashMap;

/**
 * <p>Java class for BaseAddress.
 */

public class BaseAddress  {

    protected String line1;
    protected String line2;
    protected String city;
    protected String state;
    protected String postalCode;
    protected String countryCode;
    protected String type;

    public BaseAddress(HashMap<String, String> params, int index){
		
    	/*
    	 * &addressList.address(0).addresseeName=&
	 * addressList.address(0).baseAddress.line1=1+Main+St&addressList.address(0).baseAddress.line2=&
	 * addressList.address(0).baseAddress.city=San+Jose&addressList.address(0).baseAddress.state=CA&
	 * addressList.address(0).baseAddress.postalCode=95131&addressList.address(0).baseAddress.countryCode=US&
	 * addressList.address(0).baseAddress.type=BILLING
    	 */
    	
		if(params.containsKey("addressList.address("+ index +").baseAddress.line1")){
			this.line1 = params.get("addressList.address("+ index +").baseAddress.line1");
		}
		if(params.containsKey("addressList.address("+ index +").baseAddress.line2")){
			this.line2 = params.get("addressList.address("+ index +").baseAddress.line2");
		}
		if(params.containsKey("addressList.address("+ index +").baseAddress.city")){
			this.city = params.get("addressList.address("+ index +").baseAddress.city");
		}
		if(params.containsKey("addressList.address("+ index +").baseAddress.state")){
			this.state = params.get("addressList.address("+ index +").baseAddress.state");
		}
		if(params.containsKey("addressList.address("+ index +").baseAddress.postalCode")){
			this.postalCode = params.get("addressList.address("+ index +").baseAddress.postalCode");
		}
		if(params.containsKey("addressList.address("+ index +").baseAddress.countryCode")){
			this.countryCode = params.get("addressList.address("+ index +").baseAddress.countryCode");
		}
		if(params.containsKey("addressList.address("+ index +").baseAddress.type")){
			this.type = params.get("addressList.address("+ index +").baseAddress.type");
		}
	}

    
    /**
     * Gets the value of the line1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLine1() {
        return line1;
    }

    /**
     * Sets the value of the line1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLine1(String value) {
        this.line1 = value;
    }

    /**
     * Gets the value of the line2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLine2() {
        return line2;
    }

    /**
     * Sets the value of the line2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLine2(String value) {
        this.line2 = value;
    }

    /**
     * Gets the value of the city property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the value of the city property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCity(String value) {
        this.city = value;
    }

    /**
     * Gets the value of the state property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setState(String value) {
        this.state = value;
    }

    /**
     * Gets the value of the postalCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the value of the postalCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostalCode(String value) {
        this.postalCode = value;
    }

    /**
     * Gets the value of the countryCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * Sets the value of the countryCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountryCode(String value) {
        this.countryCode = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

}
