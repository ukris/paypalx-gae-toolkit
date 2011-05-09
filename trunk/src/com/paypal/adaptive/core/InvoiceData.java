/**
 * 
 */
package com.paypal.adaptive.core;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author palavilli
 *
 */
public class InvoiceData {

	protected List<InvoiceItem> item;
	protected double totalTax = -1;
	protected double totalShipping = -1;
	/**
	 * 
	 */
	public InvoiceData() {
		item = new ArrayList<InvoiceItem>();
	}
	
	public InvoiceData(HashMap<String, String> params) {
		for(int i=0; i<10;i++){
			if(params.containsKey("receiverOptions.invoiceData.item("+i+").name")
					|| params.containsKey("receiverOptions.invoiceData.item("+i+").identifier")
					|| params.containsKey("receiverOptions.invoiceData.item("+i+").price")
					|| params.containsKey("receiverOptions.invoiceData.item("+i+").itemPrice")
					|| params.containsKey("receiverOptions.invoiceData.item("+i+").itemCount")){
				InvoiceItem invoiceItem = new InvoiceItem(params, "receiverOptions.invoiceData.item",i);
				this.addToItem(invoiceItem);
			} else {
				break;
			}
		}
		if(params.containsKey("")){
			this.totalShipping = Double.parseDouble(params.get(""));
		}
		if(params.containsKey("")){
			this.totalTax = Double.parseDouble(params.get(""));
		}
	}

	public String serialize() throws UnsupportedEncodingException{
		StringBuilder outString = new StringBuilder();
		boolean isFirst = true;
		if(item != null) {
			int i = 0;
	    	for(InvoiceItem itm: item){
	    		outString.append(itm.serialize("receiverOptions.invoiceData.item", i));
	    		i++;
	    		isFirst = false;
	    	}
    	}
		
		if(this.totalTax != -1) {
			if(!isFirst) outString.append(ParameterUtils.PARAM_SEP);
			outString.append(ParameterUtils.createUrlParameter("receiverOptions.invoiceData.totalTax", Double.toString(this.totalTax)));
			isFirst = false;
		}
		if(this.totalShipping != -1) {
			if(!isFirst) outString.append(ParameterUtils.PARAM_SEP);
			outString.append(ParameterUtils.createUrlParameter("receiverOptions.invoiceData.totalShipping", Double.toString(this.totalShipping)));
			isFirst = false;
		}
		
		return outString.toString();
	}

	/**
	 * @return the item
	 */
	public List<InvoiceItem> getItem() {
		return item;
	}

	/**
	 * @param item the item to set
	 */
	public void setItem(List<InvoiceItem> item) {
		this.item = item;
	}
	
	/**
	 * @param InvoiceItem the invoiceItem to set
	 */
	public void addToItem(InvoiceItem invoiceItem) {
		if(this.item == null)
			this.item = new ArrayList<InvoiceItem>();
		
		this.item.add(invoiceItem);
	}
	

	/**
	 * @return the totalTax
	 */
	public double getTotalTax() {
		return totalTax;
	}

	/**
	 * @param totalTax the totalTax to set
	 */
	public void setTotalTax(double totalTax) {
		this.totalTax = totalTax;
	}

	/**
	 * @return the totalShipping
	 */
	public double getTotalShipping() {
		return totalShipping;
	}

	/**
	 * @param totalShipping the totalShipping to set
	 */
	public void setTotalShipping(double totalShipping) {
		this.totalShipping = totalShipping;
	}
	
public String toString(){
		
		StringBuilder outStr = new StringBuilder();
		
		outStr.append("<table>");
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
