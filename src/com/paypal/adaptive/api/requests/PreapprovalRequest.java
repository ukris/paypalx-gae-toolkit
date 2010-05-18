
package com.paypal.adaptive.api.requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.paypal.adaptive.api.responses.PreapprovalResponse;
import com.paypal.adaptive.core.APICredential;
import com.paypal.adaptive.core.ClientDetails;
import com.paypal.adaptive.core.EndPointUrl;
import com.paypal.adaptive.core.ParameterUtils;
import com.paypal.adaptive.core.PreapprovalDetails;
import com.paypal.adaptive.core.RequestEnvelope;
import com.paypal.adaptive.core.ServiceEnvironment;
import com.paypal.adaptive.exceptions.AuthorizationRequiredException;
import com.paypal.adaptive.exceptions.InvalidAPICredentialsException;
import com.paypal.adaptive.exceptions.InvalidResponseDataException;
import com.paypal.adaptive.exceptions.MissingAPICredentialsException;
import com.paypal.adaptive.exceptions.MissingParameterException;
import com.paypal.adaptive.exceptions.PayPalErrorException;
import com.paypal.adaptive.exceptions.PaymentExecException;
import com.paypal.adaptive.exceptions.RequestFailureException;


/**
 * Preaproval API provides way to obtain preapprovals, which are an approval to make future payments on the
sender’s behalf.
 * 
 */
public class PreapprovalRequest {

	private static final Logger log = Logger.getLogger(PreapprovalRequest.class.getName());


    protected ClientDetails clientDetails;
    protected PreapprovalDetails preapprovalDetails;
    protected RequestEnvelope requestEnvelope;
    protected ServiceEnvironment env;
    
    
    public PreapprovalRequest(String language, ServiceEnvironment env){
    	
    	requestEnvelope = new RequestEnvelope();
    	requestEnvelope.setErrorLanguage(language);
    	this.env = env;
    	preapprovalDetails = new PreapprovalDetails();
    	
    }
    
    public PreapprovalResponse execute(APICredential credentialObj)
    throws IOException, MalformedURLException, MissingAPICredentialsException,
	   InvalidAPICredentialsException, MissingParameterException, 
	   UnsupportedEncodingException, RequestFailureException,
	   InvalidResponseDataException, PayPalErrorException, 
	   PaymentExecException, AuthorizationRequiredException{
    	String responseString = "";
    	// do input validation
    	
    	/*
    	 * check for the following things
    	 *  1. API Credentials
    	 *  2. Atleast one receiver has been set
    	 *  3. CurrencyCode is set
    	 */
    	if(credentialObj == null){
    		throw new MissingAPICredentialsException();
    	} else if(credentialObj != null) {
			InvalidAPICredentialsException ex = new InvalidAPICredentialsException();
			if (credentialObj.getAppId() == null
					|| credentialObj.getAppId().length() <= 0) {
				ex.addToMissingCredentials("AppId");
			}
			if (credentialObj.getAPIPassword() == null
					|| credentialObj.getAPIPassword().length() <= 0) {
				ex.addToMissingCredentials("APIPassword");
			}

			if (credentialObj.getAPIUsername() == null
					|| credentialObj.getAPIUsername().length() <= 0) {
				ex.addToMissingCredentials("APIUsername");
			}
			if (credentialObj.getSignature() == null
					|| credentialObj.getSignature().length() <= 0) {
				ex.addToMissingCredentials("Signature");
			}
			if(ex.getMissingCredentials() != null){
				throw ex;
			} else {
				ex = null;
			}
		}
    	
    	if(this.preapprovalDetails.getCancelUrl() == null){
    		throw new MissingParameterException("cancelUrl");
    	}
    	if(this.preapprovalDetails.getReturnUrl() == null){
    		throw new MissingParameterException("returnUrl");
    	}
    	if(this.preapprovalDetails.getStartingDate() == null){
    		throw new MissingParameterException("startingDate");
    	}
    	if(this.preapprovalDetails.getEndingDate() == null){
    		throw new MissingParameterException("endingDate");
    	}
    	if(this.preapprovalDetails.getMaxTotalAmountOfAllPayments() <= 0){
    		throw new MissingParameterException("maxTotalAmountOfAllPayments");
    	}
    	if(this.preapprovalDetails.getCurrencyCode() == null){
    		throw new MissingParameterException("CurrencyCode");
    	}
    	
    	// prepare request parameters
    	StringBuilder postParameters = new StringBuilder();
    	
    	// add request envelope
    	postParameters.append(requestEnvelope.serialize());
    	postParameters.append(ParameterUtils.PARAM_SEP);
    	// set clientDetails
    	postParameters.append(this.clientDetails.serialize());
    	postParameters.append(ParameterUtils.PARAM_SEP);
    	// add payment details
    	postParameters.append(this.preapprovalDetails.serialize());
    	    	
    	if(log.isLoggable(Level.INFO))
    		log.info("Sending PreapprovalRequest with: " + postParameters.toString());
    	
    	// create HTTP Request
    	try {
            URL url = new URL(EndPointUrl.get(this.env) + "Preapproval");
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
            connection.setRequestProperty("X-PAYPAL-TOOLKIT", "GAE-Toolkit");

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
            		log.info("Received PreapprovalResponse: " + responseString);
            	
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
        
    	// parse response
        PreapprovalResponse response = new PreapprovalResponse(responseString);
    	
    	// handle errors
    	return response;
    }
    
	/**
	 * @param clientDetails the clientDetails to set
	 */
	public void setClientDetails(ClientDetails clientDetails) {
		this.clientDetails = clientDetails;
	}
	/**
	 * @return the clientDetails
	 */
	public ClientDetails getClientDetails() {
		return clientDetails;
	}

	public PreapprovalDetails getPreapprovalDetails() {
		return preapprovalDetails;
	}

	public void setPreapprovalDetails(PreapprovalDetails preapprovalDetails) {
		this.preapprovalDetails = preapprovalDetails;
	}

	
	
	
}
