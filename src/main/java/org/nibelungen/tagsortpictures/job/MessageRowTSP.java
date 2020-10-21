package org.nibelungen.tagsortpictures.job;

public class MessageRowTSP {
	private String errorMessage;
	private MessageRowTSPEnum errorType;

	public MessageRowTSP(String errorMessage, MessageRowTSPEnum errorType) {
		this.errorMessage = errorMessage;
		this.errorType = errorType;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public MessageRowTSPEnum getErrorType() {
		return errorType;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void setErrorType(MessageRowTSPEnum errorType) {
		this.errorType = errorType;
	}
	
	
}
