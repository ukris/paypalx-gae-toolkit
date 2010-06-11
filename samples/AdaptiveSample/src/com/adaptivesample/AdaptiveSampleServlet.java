package com.adaptivesample;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.paypal.adaptive.api.responses.CancelPreapprovalResponse;
import com.paypal.adaptive.api.responses.ConvertCurrencyResponse;
import com.paypal.adaptive.api.responses.PaymentDetailsResponse;
import com.paypal.adaptive.api.responses.PreapprovalDetailsResponse;
import com.paypal.adaptive.api.responses.PreapprovalResponse;
import com.paypal.adaptive.api.responses.RefundResponse;
import com.paypal.adaptive.core.APICredential;
import com.paypal.adaptive.core.AckCode;
import com.paypal.adaptive.core.CurrencyConversionList;
import com.paypal.adaptive.core.CurrencyType;
import com.paypal.adaptive.core.PaymentInfo;
import com.paypal.adaptive.core.Receiver;
import com.paypal.adaptive.core.ServiceEnvironment;


@SuppressWarnings("serial")
public class AdaptiveSampleServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(AdaptiveSampleServlet.class.getName());


	private static APICredential credentialObj;
	PaymentDetailsResponse payDetailsResp;

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);

		// Get the value of APIUsername
		String APIUsername = getServletConfig().getInitParameter("PPAPIUsername"); 
		String APIPassword = getServletConfig().getInitParameter("PPAPIPassword"); 
		String APISignature = getServletConfig().getInitParameter("PPAPISignature"); 
		String AppID = getServletConfig().getInitParameter("PPAppID"); 
		String AccountEmail = getServletConfig().getInitParameter("PPAccountEmail");

		if(APIUsername == null || APIUsername.length() <= 0
				|| APIPassword == null || APIPassword.length() <=0 
				|| APISignature == null || APISignature.length() <= 0
				|| AppID == null || AppID.length() <=0 ) {
			// requires API Credentials not set - throw exception
			throw new ServletException("APICredential(s) missing");
		}

		credentialObj = new APICredential();
		credentialObj.setAPIUsername(APIUsername);
		credentialObj.setAPIPassword(APIPassword);
		credentialObj.setSignature(APISignature);
		credentialObj.setAppId(AppID);
		credentialObj.setAccountEmail(AccountEmail);
		log.info("Servlet initialized successfully");
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		String action = req.getParameter("action");
		String returnParam = req.getParameter("return"); 
		String cancel = req.getParameter("cancel");
		try {
			
			log.info("Received Action: " + action );

			if(cancel != null && cancel.equals("1")) {
				// user canceled the payment
			} 

			if(returnParam != null && returnParam.equals("1")){
				// user returned from PayPal AuthZ url
				resp.setContentType("text/html");
				
				if(action != null && action.equals("pay")){
					resp.getWriter().println("<html><head><title>Payment status</title></head><body>");
					resp.getWriter().println("<a href=\"/adaptivesample\">Home</a> <br/>");
					String payKey = req.getParameter("payKey");
					PaymentDetailsResponse payDetailsResp = 
					AdaptiveRequests.processPaymentDetails(resp, payKey, null, null, credentialObj);
					
					if (payDetailsResp.getPayErrorList().isEmpty())
					{
					PrintWriter out = resp.getWriter();
					out.print("<br/> Status: " + URLDecoder.decode(payDetailsResp.getPaymentDetails().getStatus(), "UTF-8"));
					out.print("<br/> PayKey: " + URLDecoder.decode(payDetailsResp.getPaymentDetails().getPayKey(), "UTF-8"));
					out.print("<br/> Cancel URL: " + URLDecoder.decode(payDetailsResp.getPaymentDetails().getCancelUrl(), "UTF-8"));
					out.print("<br/> Return URL: " + URLDecoder.decode(payDetailsResp.getPaymentDetails().getReturnUrl(), "UTF-8"));
					out.print("<br/> Sender Email: " + URLDecoder.decode(payDetailsResp.getPaymentDetails().getSenderEmail(), "UTF-8"));
					
					ArrayList<PaymentInfo> tmp = payDetailsResp.getPaymentDetails().getPaymentInfoList();
					
					for(int i=0;i<tmp.size();i++)
						{
						PaymentInfo test = tmp.get(i);
						Receiver testRev = test.getReceiver();
						out.print("<p><h3>Receiver " + (i+1) + "</h3></p>");
						out.print("<br/>Amount: " +testRev.getAmount());
						out.print("<br/>Email: " +URLDecoder.decode(testRev.getEmail(), "UTF-8"));
						out.print("<br/>Primary: " +testRev.isPrimary());
						}
					out.print("<br/><br/>PayDetails Success");
					}
					
					
					

				} else if(action != null && action.equals("preapproval")){
					resp.getWriter().println("<html><head><title>Preapproval status</title></head><body>");
					resp.getWriter().println("<a href=\"/adaptivesample\">Home</a> <br/>");
					String preapprovalKey = req.getParameter("preapprovalKey");
					PreapprovalDetailsResponse preapprovalDetailsResp = 						 
						AdaptiveRequests.processPreapprovalDetails(resp, preapprovalKey, true, credentialObj);
					
					PrintWriter out = resp.getWriter();
					out.println("<br/> PreApproval Key: " + URLDecoder.decode(preapprovalKey,"UTF-8"));
					out.println("<br/> Status: " + URLDecoder.decode(preapprovalDetailsResp.getPreapprovalDetails().getStatus(),"UTF-8"));
					out.println("<br/> Pin Type: " + URLDecoder.decode(preapprovalDetailsResp.getPreapprovalDetails().getPinType().toString(),"UTF-8"));
				}
				resp.getWriter().println("</body></html>");
			} else { 
				if (action != null && action.equals("pay")) {

					getServletConfig().getServletContext().getRequestDispatcher(
					"/pay.jsp").forward(req, resp);
				} else if (action != null && action.equals("preapproval")) {

					getServletConfig().getServletContext().getRequestDispatcher(
					"/preapproval.jsp").forward(req, resp);
				} else if (action != null && action.equals("refund")) {

					getServletConfig().getServletContext().getRequestDispatcher(
					"/refund.jsp").forward(req, resp);
				} else if (action != null && action.equals("payDetails")) {

					getServletConfig().getServletContext().getRequestDispatcher(
					"/payDetails.jsp").forward(req, resp);
				} else if (action != null && action.equals("preapprovalDetails")) {

					getServletConfig().getServletContext().getRequestDispatcher(
					"/preapprovalDetails.jsp").forward(req, resp);	
				} else if (action != null && action.equals("cancelPreapproval")) {

					getServletConfig().getServletContext().getRequestDispatcher(
					"/cancelPreapproval.jsp").forward(req, resp);	
				} else if (action != null && action.equals("currencyConversion")) {

					getServletConfig().getServletContext().getRequestDispatcher(
					"/convertCurrency.jsp").forward(req, resp);
				
				} else {
					getServletConfig().getServletContext().getRequestDispatcher(
					"/index.jsp").forward(req, resp);
				}
			}

		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {


		String action = req.getParameter("action");

		if (action != null && action.equals("pay")) {
						
			AdaptiveRequests.processPayRequest(req, resp, credentialObj);
			
		} else if (action != null && action.equals("payDetails")) {
			resp.setContentType("text/html");
			resp.getWriter().println("<html><head><title>Pay Details</title></head><body>");
			resp.getWriter().println("<a href=\"/adaptivesample\">Home</a> <br/>");

			String payKey = req.getParameter("payKey");
			if(payKey != ""){
				payDetailsResp = 
					AdaptiveRequests.processPaymentDetails(resp, payKey, null, null, credentialObj);	
				if (payDetailsResp.getPayErrorList().isEmpty())
					{
					PrintWriter out = resp.getWriter();
					out.print("<br/> Status: " + URLDecoder.decode(payDetailsResp.getPaymentDetails().getStatus(), "UTF-8"));
					out.print("<br/> PayKey: " + URLDecoder.decode(payDetailsResp.getPaymentDetails().getPayKey(), "UTF-8"));
					out.print("<br/> Cancel URL: " + URLDecoder.decode(payDetailsResp.getPaymentDetails().getCancelUrl(), "UTF-8"));
					out.print("<br/> Return URL: " + URLDecoder.decode(payDetailsResp.getPaymentDetails().getReturnUrl(), "UTF-8"));
					out.print("<br/> Sender Email: " + URLDecoder.decode(payDetailsResp.getPaymentDetails().getSenderEmail(), "UTF-8"));
					
					ArrayList<PaymentInfo> tmp = payDetailsResp.getPaymentDetails().getPaymentInfoList();
					
					for(int i=0;i<tmp.size();i++)
						{
						PaymentInfo test = tmp.get(i);
						Receiver testRev = test.getReceiver();
						out.print("<p><h3>Receiver " + (i+1) + "</h3></p>");
						out.print("<br/>Amount: " +testRev.getAmount());
						out.print("<br/>Email: " +URLDecoder.decode(testRev.getEmail(), "UTF-8"));
						out.print("<br/>Primary: " +testRev.isPrimary());
						}
					out.print("<br/>PayDetails Success");
					}
				else
					{
					resp.getWriter().println("<br/>PayDetails Failed");
					}
			}
			else
			{
				resp.getWriter().println("Please enter a pay key!!!");	
			}
			resp.getWriter().println("</body></html>");
		} else if (action != null && action.equals("preapproval")) {
			resp.setContentType("text/html");
			resp.getWriter().println("<html><head><title>Preapproval Response & Preapproval Details</title></head><body>");
			resp.getWriter().println("<a href=\"/adaptivesample\">Home</a> <br/>");

			PreapprovalResponse preapprovalResp = AdaptiveRequests.processPreapprovalRequest(req, resp, credentialObj);
			//resp.getWriter().println( preapprovalResp.toString());
			resp.getWriter().println( preapprovalResp.getPreapprovalKey());
			
			
			if(preapprovalResp != null) {
				if(preapprovalResp.getErrorList() != null && preapprovalResp.getErrorList().size() > 0) {
					// error occured

				} else {

					PreapprovalDetailsResponse payDetailsResp = 
						AdaptiveRequests.processPreapprovalDetails(resp, preapprovalResp.getPreapprovalKey(), true, credentialObj);

					resp.getWriter().println( payDetailsResp.toString());


					// generate authurization url
					if (preapprovalResp.getResponseEnvelope().getAck() == AckCode.Success) {

						resp.getWriter().println("Preapproval Success.");
					
						resp.getWriter().println("<a href=\""
								+ AdaptiveRequests.generatePreApprovalAuthorizeUrl(preapprovalResp.getPreapprovalKey(), ServiceEnvironment.SANDBOX)
								+ "\">Click here to authorize</a>");
					}


				}
			}  else {
				resp.getWriter().println("Preapproval Failed");
			}

			resp.getWriter().println("</body></html>");
		} else if (action != null && action.equals("preapprovalDetails")) {
			resp.setContentType("text/html");
			resp.getWriter().println("<html><head><title>Preapproval Details</title></head><body>");
			resp.getWriter().println("<a href=\"/adaptivesample\">Home</a> <br/>");

			String preapprovalKey = req.getParameter("preapprovalKey");
			if(preapprovalKey != null){
					PreapprovalDetailsResponse payDetailsResp = 
						AdaptiveRequests.processPreapprovalDetails(resp, preapprovalKey, true, credentialObj);

					resp.getWriter().println("<br/> Status: " + payDetailsResp.getPreapprovalDetails().getStatus());
					resp.getWriter().println("<br/> Start Date: " + URLDecoder.decode(payDetailsResp.getPreapprovalDetails().getStartingDate(), "UTF-8"));
					resp.getWriter().println("<br/> End Date: " + URLDecoder.decode(payDetailsResp.getPreapprovalDetails().getEndingDate(), "UTF-8"));
					resp.getWriter().println("<br/> Sender: " + URLDecoder.decode(payDetailsResp.getPreapprovalDetails().getSenderEmail() ,"UTF-8"));
			}  else {
				resp.getWriter().println("Preapproval Details Failed");
			}

			resp.getWriter().println("</body></html>");
		} else if (action != null && action.equals("cancelPreapproval")) {
			resp.setContentType("text/html");
			resp.getWriter().println("<html><head><title>Cancel Preapproval</title></head><body>");
			resp.getWriter().println("<a href=\"/adaptivesample\">Home</a> <br/>");

			String preapprovalKey = req.getParameter("preapprovalKey");
			if(preapprovalKey != null){
				CancelPreapprovalResponse cancelPreapprovalResp = 
						AdaptiveRequests.processCancelPreapproval(resp, preapprovalKey, credentialObj);
					
				resp.getWriter().println("Result: "+ cancelPreapprovalResp.getResponseEnvelope().getAck().toString());

			}  else {
				resp.getWriter().println("Cancel Preapproval Failed");
			}

			resp.getWriter().println("</body></html>");
		} else if (action != null && action.equals("refund")) {
			resp.setContentType("text/html");
			resp.getWriter().println("<html><head><title>Refund</title></head><body>");
			resp.getWriter().println("<a href=\"/adaptivesample\">Home</a> <br/>");

			RefundResponse refundResp = 
					AdaptiveRequests.processRefund(req, resp, credentialObj);

			resp.getWriter().println( refundResp.toString());


			resp.getWriter().println("</body></html>");
		} else if (action != null && action.equals("currencyConversion")) {
			resp.setContentType("text/html");
			resp.getWriter().println("<html><head><title>Convert Currency</title></head><body>");
			resp.getWriter().println("<a href=\"/adaptivesample\">Home</a> <br/>");

			ConvertCurrencyResponse currConvertResp = 
					AdaptiveRequests.processCurrencyConversion(req, resp, credentialObj);
			
			
			PrintWriter out = resp.getWriter();
			if (currConvertResp.getErrorList().isEmpty())
				{
				ArrayList<CurrencyConversionList> tmp = currConvertResp.getEstimatedAmountTable();
				
				for(int i=0;i<tmp.size();i++)
						{
							CurrencyConversionList test = tmp.get(i);
							CurrencyType testBase = test.getBaseAmount();
							ArrayList<CurrencyType> convCurr = test.getCurrencyList();
			
							out.print("<p><h3>Currency " + (i+1) + "</h3></p>");
							out.print("<br/>Base Amount: " +testBase.getAmount() + " " + testBase.getCode() );
							for(int j=0; j<convCurr.size(); j++)
								{
								CurrencyType convertedCurrency = convCurr.get(j);
								out.print("<br/> Converted Amount: " +convertedCurrency.getAmount()+ " " +convertedCurrency.getCode());
								}
						}
					out.print("<br/><br/>Conversion Success");
					}
			else
				{
				resp.getWriter().println("<br/> Error Occured");		
				}
			resp.getWriter().println("</body></html>");
		
		} else {
			try {
				getServletConfig().getServletContext().getRequestDispatcher(
						"/index.jsp").forward(req, resp);

			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
	}
}
