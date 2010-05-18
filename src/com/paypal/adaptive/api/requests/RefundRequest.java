
package com.paypal.adaptive.api.requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.paypal.adaptive.api.responses.RefundResponse;
import com.paypal.adaptive.core.APICredential;
import com.paypal.adaptive.core.CurrencyCodes;
import com.paypal.adaptive.core.EndPointUrl;
import com.paypal.adaptive.core.ParameterUtils;
import com.paypal.adaptive.core.Receiver;
import com.paypal.adaptive.core.RequestEnvelope;
import com.paypal.adaptive.core.ServiceEnvironment;
import com.paypal.adaptive.exceptions.InvalidAPICredentialsException;
import com.paypal.adaptive.exceptions.InvalidResponseDataException;
import com.paypal.adaptive.exceptions.MissingAPICredentialsException;
import com.paypal.adaptive.exceptions.MissingParameterException;
import com.paypal.adaptive.exceptions.PayPalErrorException;
import com.paypal.adaptive.exceptions.RequestFailureException;

/**
 * Refund API refunds all or part of a payment.
 * 
 */
public class RefundRequest {

	private static final Logger log = Logger.getLogger(RefundRequest.class.getName());

	protected ServiceEnvironment env;
    protected RequestEnvelope requestEnvelope;
    protected CurrencyCodes currencyCode;
    protected String payKey;
    protected String transactionId;
    protected String trackingId;
    protected List<Receiver> receiverList;

    public RefundRequest(String language, ServiceEnvironment env){
    	
    	requestEnvelope = new RequestEnvelope();
    	requestEnvelope.setErrorLanguage(language);
    	this.env = env;
    }
    
    public RefundResponse execute(APICredential credentialObj) 
    throws MissingParameterException, MissingAPICredentialsException, InvalidAPICredentialsException, 
    PayPalErrorException, RequestFailureException, IOException, InvalidResponseDataException{
    	String responseString = "";
    	// do input validation
    	/* - VALIDATE REQUIRED PARAMS- */
    	/*
    	 * check for the following things
    	 *  1. API Credentials
    	 *  2. Atleast one of payKey, transactionId, trackingId
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
    	if(this.trackingId == null && this.transactionId == null && this.payKey == null){
    		throw new MissingParameterException("payKey");
    	}
    	// prepare request parameters
    	StringBuilder postParameters = new StringBuilder();
    	
    	// add request envelope
    	postParameters.append(requestEnvelope.serialize());
    	postParameters.append(ParameterUtils.PARAM_SEP);
    	
    	// add receiverList
    	int i = 0;
    	if(receiverList != null) {
	    	for(Receiver recvr: receiverList){
	    		postParameters.append(recvr.serialize(i));
	    		i++;
	    	}
    	}
    	// add currencyCode
    	if(this.currencyCode != null) {
	    	postParameters.append(ParameterUtils.createUrlParameter("currencyCode", this.getCurrencyCode().toString()));
	    	postParameters.append(ParameterUtils.PARAM_SEP);
    	}
    	// set trackingId
    	if(this.trackingId != null) {
    		postParameters.append(ParameterUtils.createUrlParameter("trackingId", this.getTrackingId()));
    		postParameters.append(ParameterUtils.PARAM_SEP);
    	}
    	// set transactionId
    	if(this.transactionId != null) {
    		postParameters.append(ParameterUtils.createUrlParameter("transactionId", this.getTransactionId()));
    		postParameters.append(ParameterUtils.PARAM_SEP);
    	}
    	
    	// set preapprovalKey
    	if(this.payKey != null) {
    		postParameters.append(ParameterUtils.createUrlParameter("payKey", this.getPayKey()));
    		postParameters.append(ParameterUtils.PARAM_SEP);
    	}
    	
    	if(log.isLoggable(Level.INFO))
    		log.info("Sending RefundRequest with: " + postParameters.toString());
    	
    	
    	// create HTTP Request
    	try {
            URL url = new URL(EndPointUrl.get(this.env) + "Refund");
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
            		log.info("Received RefundResponse: " + responseString);
            } else {
            	// Server returned HTTP error code.
            	throw new RequestFailureException(connection.getResponseCode());            }
        } catch (MalformedURLException e) {
            // ...
        	throw e;
        } catch (IOException e) {
            // ...
        	throw e;
        }
    	// send request
    	    	
    	// parse response
        RefundResponse response = new RefundResponse(responseString);
    	
    	// handle errors
    	return response;
    }

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
     * Gets the value of the currencyCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public CurrencyCodes getCurrencyCode() {
        return currencyCode;
    }

    /**
     * Sets the value of the currencyCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrencyCode(CurrencyCodes value) {
        this.currencyCode = value;
    }

    /**
     * Gets the value of the payKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayKey() {
        return payKey;
    }

    /**
     * Sets the value of the payKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayKey(String value) {
        this.payKey = value;
    }

    /**
     * Gets the value of the transactionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * Sets the value of the transactionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransactionId(String value) {
        this.transactionId = value;
    }

    /**
     * Gets the value of the trackingId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrackingId() {
        return trackingId;
    }

    /**
     * Sets the value of the trackingId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrackingId(String value) {
        this.trackingId = value;
    }

    /**
	 * @param receiverList the receiverList to set
	 */
	public void setReceiverList(List<Receiver> receiverList) {
		this.receiverList = receiverList;
	}
	/**
	 * @param receiverList the receiverList to set
	 */
	public void addToReceiverList(Receiver receiver) {
		if(this.receiverList == null)
			this.receiverList = new ArrayList<Receiver>();
		
		this.receiverList.add(receiver);
	}
	/**
	 * @return the receiverList
	 */
	public List<Receiver> getReceiverList() {
		return receiverList;
	}

}
