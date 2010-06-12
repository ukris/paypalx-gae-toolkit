
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

import com.paypal.adaptive.api.responses.PreapprovalDetailsResponse;
import com.paypal.adaptive.core.APICredential;
import com.paypal.adaptive.core.EndPointUrl;
import com.paypal.adaptive.core.ParameterUtils;
import com.paypal.adaptive.core.RequestEnvelope;
import com.paypal.adaptive.core.ServiceEnvironment;
import com.paypal.adaptive.exceptions.InvalidResponseDataException;
import com.paypal.adaptive.exceptions.MissingParameterException;
import com.paypal.adaptive.exceptions.RequestFailureException;

/**
 * PreapprovalDetails API provides information about a preapproval set up with the Preapproval API operation. 
 * 
 */
public class PreapprovalDetailsRequest {

	private static final Logger log = Logger.getLogger(PreapprovalDetailsRequest.class.getName());

    protected RequestEnvelope requestEnvelope;
    protected String preapprovalKey;
    protected boolean getBillingAddress = false;
    protected ServiceEnvironment env;
    
    /**
     * Gets the value of the requestEnvelope property.
     * 
     * @return
     *     possible object is
     *     {@link RequestEnvelope }
     *     
     */
    public RequestEnvelope getRequestEnvelope() {
        return requestEnvelope;
    }

    /**
     * Sets the value of the requestEnvelope property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestEnvelope }
     *     
     */
    public void setRequestEnvelope(RequestEnvelope value) {
        this.requestEnvelope = value;
    }

    /**
     * Gets the value of the preapprovalKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreapprovalKey() {
        return preapprovalKey;
    }

    /**
     * Sets the value of the preapprovalKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreapprovalKey(String value) {
        this.preapprovalKey = value;
    }

    /**
     * Gets the value of the getBillingAddress property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isGetBillingAddress() {
        return getBillingAddress;
    }

    /**
     * Sets the value of the getBillingAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setGetBillingAddress(boolean value) {
        this.getBillingAddress = value;
    }

    public PreapprovalDetailsRequest(String language, ServiceEnvironment env){
    	
    	requestEnvelope = new RequestEnvelope();
    	requestEnvelope.setErrorLanguage(language);
    	this.env = env;    	
    }
    
    public PreapprovalDetailsResponse execute(APICredential credentialObj) throws MissingParameterException, InvalidResponseDataException, RequestFailureException, IOException {
    	String responseString = "";
    	// do input validation
    	if(preapprovalKey == null | preapprovalKey.length() <=0)
    		throw new MissingParameterException("preapprovalKey");
       	// prepare request parameters
    	StringBuilder postParameters = new StringBuilder();
    	
    	// add request envelope
    	postParameters.append(requestEnvelope.serialize());
    	postParameters.append(ParameterUtils.PARAM_SEP);
    	// set preapprovalKey
    	postParameters.append("preapprovalKey=");
    	postParameters.append(this.preapprovalKey);
    	
    	// add getBillingAddress
    	if(this.getBillingAddress) {
    		postParameters.append(ParameterUtils.PARAM_SEP);
    		postParameters.append("getBillingAddress=");
    		postParameters.append(this.getBillingAddress);
    	}
    	
    	    	
    	if(log.isLoggable(Level.INFO))
    		log.info("Sending PreapprovalDetails Request with: " + postParameters.toString());    	
    	
    	try {
            URL url = new URL(EndPointUrl.get(this.env) + "PreapprovalDetails");
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
            		log.info("Received PreapprovalDetails Response: " + responseString);
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
        PreapprovalDetailsResponse response = new PreapprovalDetailsResponse(responseString);
    	
    	// handle errors
    	return response;
    }

}
