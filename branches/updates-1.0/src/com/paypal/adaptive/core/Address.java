
package com.paypal.adaptive.core;

import java.util.HashMap;

/**
 * <p>Java class for Address.
 */
public class Address {

    protected String addresseeName;
    protected BaseAddress baseAddress;

    
    public Address(HashMap<String, String> params, int index){
		
		if(params.containsKey("addressList.address("+ index +").addresseeName")){
			this.addresseeName = params.get("addressList.address("+ index +").addresseeName");
		}
		this.baseAddress = new BaseAddress(params, index); 
	}
    
    /**
     * Gets the value of the addresseeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddresseeName() {
        return addresseeName;
    }

    /**
     * Sets the value of the addresseeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddresseeName(String value) {
        this.addresseeName = value;
    }

    /**
     * Gets the value of the baseAddress property.
     * 
     * @return
     *     possible object is
     *     {@link BaseAddress }
     *     
     */
    public BaseAddress getBaseAddress() {
        return baseAddress;
    }

    /**
     * Sets the value of the baseAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link BaseAddress }
     *     
     */
    public void setBaseAddress(BaseAddress value) {
        this.baseAddress = value;
    }

}
