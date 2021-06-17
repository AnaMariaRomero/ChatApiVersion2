package net.apimessages.pd2.exceptions;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "error")
public class MessageErrorResponse {
	//General error message about nature of error
    private String message;
 
    //Specific errors in API request processing
    private List<String> details;
	
	public MessageErrorResponse(String message, List<String> details) {
		super();
        this.setMessage(message);
        this.setDetails(details);        
	}

	//Getter and setters
	public List<String> getDetails() {
		return details;
	}

	public void setDetails(List<String> details) {
		this.details = details;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
