/**
 * 
 */
package org.selfip.nibelungen.tagsortpictures.job;


/**
 * @author Mickaël
 *
 */
public class MessageRowTSP {
	
	/**
	 * 
	 */
	private String errorMessage;
	
	/**
	 * 
	 */
	private MessageRowTSPEnum errorType;

	public MessageRowTSP(String errorMessage, MessageRowTSPEnum errorType) {
		this.errorMessage = errorMessage;
		this.errorType = errorType;
	}
	
	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @return the errorType
	 */
	public MessageRowTSPEnum getErrorType() {
		return errorType;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @param errorType the errorType to set
	 */
	public void setErrorType(MessageRowTSPEnum errorType) {
		this.errorType = errorType;
	}
	
	
}
