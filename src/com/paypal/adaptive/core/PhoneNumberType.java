package com.paypal.adaptive.core;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * <p>Java class for PhoneNumberType.
 * 
 */
public class PhoneNumberType {

	protected double countryCode = -1;
	protected double extension = -1;
	protected double phonenumber = -1;
	
	public double getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(double countryCode) {
		this.countryCode = countryCode;
	}
	public double getExtension() {
		return extension;
	}
	public void setExtension(double extension) {
		this.extension = extension;
	}
	public double getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(double phonenumber) {
		this.phonenumber = phonenumber;
	}
	
	PhoneNumberType(double countryCode, double extension, double phonenumber){
		this.countryCode = countryCode;
		this.extension = extension;
		this.phonenumber = phonenumber;
	}
	
	PhoneNumberType(HashMap<String, String> params, int index){
		if(params.containsKey("error(" + index +").receiver.phone.countryCode")){
			this.countryCode = Double.parseDouble(params.get("error(" + index +").receiver.phone.countryCode"));
		}
		if(params.containsKey("error(" + index +").receiver.phone.extension")){
			this.extension = Double.parseDouble(params.get("error(" + index +").receiver.phone.extension"));
		}
		if(params.containsKey("error(" + index +").receiver.phone.phonenumber")){
			this.countryCode = Double.parseDouble(params.get("error(" + index +").receiver.phone.phonenumber"));
		}
	}
	
	public String serialize(int index) throws UnsupportedEncodingException{
		StringBuilder outString = new StringBuilder();
		if(this.countryCode != -1) {
			outString.append(ParameterUtils.createUrlParameter("receiverList.receiver(" + 
					index + ").phone.countryCode", Double.toString(this.countryCode)));
			outString.append(ParameterUtils.PARAM_SEP);
		}
		if(this.extension != -1) {
			outString.append(ParameterUtils.createUrlParameter("receiverList.receiver(" + 
					index + ").phone.extension", Double.toString(this.extension)));
			outString.append(ParameterUtils.PARAM_SEP);
		}
		if(this.phonenumber != -1) {
			outString.append(ParameterUtils.createUrlParameter("receiverList.receiver(" + 
					index + ").phone.phonenumber", Double.toString(this.phonenumber)));
			outString.append(ParameterUtils.PARAM_SEP);
		}
		return outString.toString();
	}
	
}
