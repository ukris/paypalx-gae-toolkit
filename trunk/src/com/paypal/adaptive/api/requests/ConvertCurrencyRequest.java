
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

import com.paypal.adaptive.api.responses.ConvertCurrencyResponse;
import com.paypal.adaptive.core.APICredential;
import com.paypal.adaptive.core.CurrencyCodes;
import com.paypal.adaptive.core.CurrencyType;
import com.paypal.adaptive.core.EndPointUrl;
import com.paypal.adaptive.core.ParameterUtils;
import com.paypal.adaptive.core.RequestEnvelope;
import com.paypal.adaptive.core.ServiceEnvironment;
import com.paypal.adaptive.exceptions.InvalidAPICredentialsException;
import com.paypal.adaptive.exceptions.InvalidResponseDataException;
import com.paypal.adaptive.exceptions.MissingAPICredentialsException;
import com.paypal.adaptive.exceptions.MissingParameterException;
import com.paypal.adaptive.exceptions.PayPalErrorException;
import com.paypal.adaptive.exceptions.RequestFailureException;


/**
 * The ConvertCurrency API provides Foreign Exchange currency conversion rates for a list of amounts.
 * 
 */
public class ConvertCurrencyRequest {

	private static final Logger log = Logger.getLogger(ConvertCurrencyRequest.class.getName());

    protected RequestEnvelope requestEnvelope;
    protected ServiceEnvironment env;
    protected List<CurrencyType> baseAmountList;
    protected List<CurrencyCodes> convertToCurrencyList;
    
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


    public ConvertCurrencyRequest(String language, ServiceEnvironment env){
    	
    	requestEnvelope = new RequestEnvelope();
    	requestEnvelope.setErrorLanguage(language);
    	this.env = env;    	
    }
    
    public ConvertCurrencyResponse execute(APICredential credentialObj) 
    throws MissingParameterException, MissingAPICredentialsException, InvalidAPICredentialsException, 
    RequestFailureException, InvalidResponseDataException, IOException, PayPalErrorException {
    	String responseString = "";
    	// do input validation
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
    	// atleast one baseamountlist and one convertToCurrencylist
    	if(this.baseAmountList == null || this.baseAmountList.size() <=0 )
    		throw new MissingParameterException("baseAmountList");

    	if(this.convertToCurrencyList == null || this.convertToCurrencyList.size() <=0 )
    		throw new MissingParameterException("baseAmountList");

    	// prepare request parameters
    	StringBuilder postParameters = new StringBuilder();
    	
    	// add request envelope
    	postParameters.append(requestEnvelope.serialize());
    	postParameters.append(ParameterUtils.PARAM_SEP);
    	
    	// add baseAmountList
    	int i = 0;
    	if(baseAmountList != null) {
	    	for(CurrencyType currType: baseAmountList){
	    		postParameters.append(currType.serialize(i));
	    		i++;
	    	}
    	}
    	// add receiverList
    	i = 0;
    	if(convertToCurrencyList != null) {
	    	for(CurrencyCodes code: convertToCurrencyList){
	    		postParameters.append(ParameterUtils.createUrlParameter("convertToCurrencyList.currencyCode("+i+")", code.toString()));
	    		postParameters.append(ParameterUtils.PARAM_SEP);
	    		i++;
	    	}
    	}
    	if(log.isLoggable(Level.INFO))
    		log.info("Sending ConvertCurrency Request with: " + postParameters.toString());

    	try {
            URL url = new URL(EndPointUrl.get(this.env) + "ConvertCurrency");
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
            		log.info("Received Convert Currency Response: " + responseString);
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
        ConvertCurrencyResponse response = new ConvertCurrencyResponse(responseString);
    	
    	// handle errors
    	return response;
    }

	public List<CurrencyType> getBaseAmountList() {
		return baseAmountList;
	}

	public void setBaseAmountList(List<CurrencyType> baseAmountList) {
		this.baseAmountList = baseAmountList;
	}
	
	public void addToBaseAmountList(CurrencyType currType){
		if(this.baseAmountList == null){
			this.baseAmountList = new ArrayList<CurrencyType>();
		}
		this.baseAmountList.add(currType);
	}

	public List<CurrencyCodes> getConvertToCurrencyList() {
		return convertToCurrencyList;
	}

	public void setConvertToCurrencyList(List<CurrencyCodes> convertToCurrencyList) {
		this.convertToCurrencyList = convertToCurrencyList;
	}
	public void addToConvertToCurrencyList(CurrencyCodes code){
		if(this.convertToCurrencyList == null)
			this.convertToCurrencyList = new ArrayList<CurrencyCodes>();
		
		this.convertToCurrencyList.add(code);
	}

}
