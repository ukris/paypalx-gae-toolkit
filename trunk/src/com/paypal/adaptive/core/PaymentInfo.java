
package com.paypal.adaptive.core;

import java.math.BigDecimal;


/**
 * <p>Java class for PaymentInfo.
 * 
 */
public class PaymentInfo  {

	protected String transactionId;
    protected String transactionStatus;
    protected Receiver receiver;
    protected double refundedAmount;
    protected Boolean pendingRefund;
    protected String senderTransactionId;
    protected String senderTransactionStatus;

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
     * Gets the value of the transactionStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransactionStatus() {
        return transactionStatus;
    }

    /**
     * Sets the value of the transactionStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransactionStatus(String value) {
        this.transactionStatus = value;
    }

    /**
     * Gets the value of the receiver property.
     * 
     * @return
     *     possible object is
     *     {@link Receiver }
     *     
     */
    public Receiver getReceiver() {
        return receiver;
    }

    /**
     * Sets the value of the receiver property.
     * 
     * @param value
     *     allowed object is
     *     {@link Receiver }
     *     
     */
    public void setReceiver(Receiver value) {
        this.receiver = value;
    }

    /**
     * Gets the value of the refundedAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public double getRefundedAmount() {
        return refundedAmount;
    }

    /**
     * Sets the value of the refundedAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRefundedAmount(double value) {
        this.refundedAmount = value;
    }

    /**
     * Gets the value of the pendingRefund property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPendingRefund() {
        return pendingRefund;
    }

    /**
     * Sets the value of the pendingRefund property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPendingRefund(Boolean value) {
        this.pendingRefund = value;
    }

    /**
     * Gets the value of the senderTransactionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSenderTransactionId() {
        return senderTransactionId;
    }

    /**
     * Sets the value of the senderTransactionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSenderTransactionId(String value) {
        this.senderTransactionId = value;
    }

    /**
     * Gets the value of the senderTransactionStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSenderTransactionStatus() {
        return senderTransactionStatus;
    }

    /**
     * Sets the value of the senderTransactionStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSenderTransactionStatus(String value) {
        this.senderTransactionStatus = value;
    }

    
}
