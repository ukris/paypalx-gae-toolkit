/**
 * 
 */
package com.paypal.adaptive.api.requests;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.paypal.adaptive.api.responses.SetPaymentOptionsResponse;
import com.paypal.adaptive.core.APICredential;
import com.paypal.adaptive.core.EndPointUrl;
import com.paypal.adaptive.core.ParameterUtils;
import com.paypal.adaptive.core.PaymentOptions;
import com.paypal.adaptive.core.RequestEnvelope;
import com.paypal.adaptive.core.ServiceEnvironment;
import com.paypal.adaptive.exceptions.InvalidResponseDataException;
import com.paypal.adaptive.exceptions.MissingParameterException;
import com.paypal.adaptive.exceptions.RequestFailureException;



/**
 * PaymentDetails API provides information about a payment set up with the Pay API operation.
 * 
 */
public class SetPaymentOptionsRequest{

	private static final Logger log = Logger.getLogger(SetPaymentOptionsRequest.class.getName());

	protected RequestEnvelope requestEnvelope;
	private ServiceEnvironment env;
	protected String payKey;
	protected PaymentOptions paymentOptions;
 
    public SetPaymentOptionsRequest(String language, ServiceEnvironment env){
    	
    	requestEnvelope = new RequestEnvelope();
    	requestEnvelope.setErrorLanguage(language);
    	this.env = env;
    	
    }

	public String getPayKey() {
		return payKey;
	}

	public void setPayKey(String payKey) {
		this.payKey = payKey;
	}


	public SetPaymentOptionsResponse execute(APICredential credentialObj) throws MissingParameterException, InvalidResponseDataException, RequestFailureException, IOException {
    	String responseString = "";
    	// do input validation
    	if ( this.payKey == null || this.payKey=="") {
	    	throw new MissingParameterException("payKey");
	    }
    	
    	// prepare request parameters
    	StringBuilder postParameters = new StringBuilder();
    	
    	// add request envelope
    	postParameters.append(requestEnvelope.serialize());
    	postParameters.append(ParameterUtils.PARAM_SEP);
    	// add payKey
    	postParameters.append(ParameterUtils.createUrlParameter("payKey", this.payKey));
    	postParameters.append(ParameterUtils.PARAM_SEP);
    	// add paymentoptions
    	if(this.paymentOptions != null){
    		postParameters.append(this.paymentOptions.serialize());
    	}
    	    	
    	if(log.isLoggable(Level.INFO))
    		log.info("Sending SetPaymentOptionsRequest with: " + postParameters.toString());    	
    	
    	try {
    		/*
        	// Create a trust manager that does not validate certificate chains 
    		TrustManager[] trustAllCerts = new TrustManager[] { 
    				new X509TrustManager() { 
    					public java.security.cert.X509Certificate[] getAcceptedIssuers() { return null; } 
    					public void checkClientTrusted( java.security.cert.X509Certificate[] certs, String authType) { } 
    					public void checkServerTrusted( java.security.cert.X509Certificate[] certs, String authType) { } 
    				} };
    		// Install the all-trusting trust manager 

    		SSLContext sc = SSLContext.getInstance("TLS"); 
    		sc.init(null, trustAllCerts, new java.security.SecureRandom()); 
    		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory()); 
    		HttpsURLConnection.setDefaultHostnameVerifier( new HostnameVerifier(){
    			public boolean verify(String string,SSLSession ssls) {
    				return true;
    			}
    		});
    		*/
            URL url = new URL(EndPointUrl.get(this.env) + "SetPaymentOptions");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            // method is always POST
            connection.setRequestMethod("POST");
            // set HTTP headers
            connection.setRequestProperty("X-PAYPAL-SECURITY-USERID", credentialObj.getAPIUsername());
            connection.setRequestProperty("X-PAYPAL-SECURITY-PASSWORD", credentialObj.getAPIPassword());
            connection.setRequestProperty("X-PAYPAL-SECURITY-SIGNATURE", credentialObj.getSignature());
            connection.setRequestProperty("X-PAYPAL-REQUEST-DATA-FORMAT", "NV");
            connection.setRequestProperty("X-PAYPAL-RESPONSE-DATA-FORMAT", "NV");
            connection.setRequestProperty("X-PAYPAL-APPLICATION-ID", credentialObj.getAppId());
            connection.setRequestProperty("X-PAYPAL-REQUEST-SOURCE", "GAE-JAVA_Toolkit");
            
            System.out.println(connection.toString());
            System.out.println(postParameters.toString());
            
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(postParameters.toString());
            writer.close();
    
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            	String inputLine;
            	BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                    while ((inputLine = reader.readLine()) != null) {
                    	responseString += inputLine;
                    }
        	    reader.close();
        	    if(responseString.length() <= 0){
        	    	throw new InvalidResponseDataException(responseString);
        	    }
        	    if(log.isLoggable(Level.INFO))
            		log.info("Received SetPaymentOptions Response: " + responseString);
            } else {
                // Server returned HTTP error code.
            	throw new RequestFailureException(connection.getResponseCode());
            }
        } catch (MalformedURLException e) {
            // ...
        	throw e;
        } catch (IOException e) {
            // ...
        	throw e;
        }/* catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RequestFailureException(503);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RequestFailureException(503);
		} */
    	// send request
    	    	
    	// parse response
        SetPaymentOptionsResponse response = new SetPaymentOptionsResponse(responseString);
    	
    	// handle errors
    	return response;
    }

	/**
	 * @return the paymentOptions
	 */
	public PaymentOptions getPaymentOptions() {
		return paymentOptions;
	}

	/**
	 * @param paymentOptions the paymentOptions to set
	 */
	public void setPaymentOptions(PaymentOptions paymentOptions) {
		this.paymentOptions = paymentOptions;
	}
	
public String toString(){
		
		StringBuilder outStr = new StringBuilder();
		
		outStr.append("<table border=1>");
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
