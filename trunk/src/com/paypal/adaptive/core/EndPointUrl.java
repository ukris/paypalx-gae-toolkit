package com.paypal.adaptive.core;

/**
 * <p>Java class for EndPointUrl.
 */
public class EndPointUrl {

	public static String get(ServiceEnvironment env){
		if(env == ServiceEnvironment.PRODUCTION){
			return "https://svcs.paypal.com/AdaptivePayments/";			
		} else if(env == ServiceEnvironment.BETA_SANDBOX){
			return "https://svcs.beta-sandbox.paypal.com/AdaptivePayments/";			
		} else {
			return "https://svcs.sandbox.paypal.com/AdaptivePayments/";			
		} 
	}
	
	public static String getAuthorizationUrl(ServiceEnvironment env){
		if(env == ServiceEnvironment.PRODUCTION){
			return "https://www.paypal.com/cgi-bin/webscr";			
		} else if(env == ServiceEnvironment.BETA_SANDBOX){
			return "https://www.sandbox.paypal.com/cgi-bin/webscr";			
		} else {
			return "https://www.sandbox.paypal.com/cgi-bin/webscr";			
		} 
	}
}
