/**
 * 
 */
package com.paypal.adaptive.api.requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.paypal.adaptive.api.responses.PaymentDetailsResponse;
import com.paypal.adaptive.core.APICredential;
import com.paypal.adaptive.core.EndPointUrl;
import com.paypal.adaptive.core.ParameterUtils;
import com.paypal.adaptive.core.RequestEnvelope;
import com.paypal.adaptive.core.ServiceEnvironment;
import com.paypal.adaptive.exceptions.InvalidResponseDataException;
import com.paypal.adaptive.exceptions.MissingParameterException;
import com.paypal.adaptive.exceptions.RequestFailureException;



/**
 * PaymentDetails API provides information about a payment set up with the Pay API operation.
 * 
 */
public class PaymentDetailsRequest{

	private static final Logger log = Logger.getLogger(PaymentDetailsRequest.class.getName());

	protected RequestEnvelope requestEnvelope;
	private ServiceEnvironment env;
	protected String payKey;
	protected String transactionId;
	protected String trackingId;
 
    public PaymentDetailsRequest(String language, ServiceEnvironment env){
    	
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

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}
	
	public PaymentDetailsResponse execute(APICredential credentialObj) throws MissingParameterException, InvalidResponseDataException, RequestFailureException, IOException {
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
    	// add transactionId if set
    	if(this.transactionId != null) {
    		postParameters.append(ParameterUtils.createUrlParameter("transactionId", this.transactionId));
    		postParameters.append(ParameterUtils.PARAM_SEP);        	
    	}
    	// add trackingId if set
    	if(this.trackingId != null) {
    		postParameters.append(ParameterUtils.createUrlParameter("transactionId", this.trackingId));
    		postParameters.append(ParameterUtils.PARAM_SEP);        	
    	}
    	
    	    	
    	if(log.isLoggable(Level.INFO))
    		log.info("Sending PaymentDetails Request with: " + postParameters.toString());    	
    	
    	try {
            URL url = new URL(EndPointUrl.get(this.env) + "PaymentDetails");
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
            		log.info("Received PaymentDetails Response: " + responseString);
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
        }
    	// send request
    	    	
    	// parse response
        PaymentDetailsResponse response = new PaymentDetailsResponse(responseString);
    	
    	// handle errors
    	return response;
    }

}
