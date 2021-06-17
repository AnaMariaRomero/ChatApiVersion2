package net.apimessages.pd2.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table (name = "messages")
@JsonIgnoreProperties(ignoreUnknown = true, value = {"hibernateLazyInitializer", "fieldHandler"})
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank(message="Sender it cant be empty.")
    @Pattern(regexp="^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$",message="sender UUID invalid.")
	private String sender;
	@NotBlank(message="Recipient it cant be empty.")
    @Pattern(regexp="^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$",message="recipient UUID invalid.")
	private String recipient;
	private String content;
	
	public Message() {}
	
	public Message(Long id, String sender, String recipient, String content) {
		this.id = id;
		this.sender = sender;
		this.recipient = recipient;
		this.content = content;
	}
	
	public Message(String sender, String recipient, String content) {
		this.sender = sender;
		this.recipient = recipient;
		this.content = content;
	}
	
	public Long getId() {return id;}
	
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public void getId(long l) {
		this.id = l;	
	}
}
