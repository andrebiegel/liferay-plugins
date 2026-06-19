package io.github.andrebiegel.boundary;

public class ResponseDTO {
	
	private String message;
	
	public ResponseDTO(String message) {
		setMessage(message);
	}
	
	private void setMessage(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
}
