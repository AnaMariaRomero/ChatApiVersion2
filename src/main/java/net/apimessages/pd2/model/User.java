package net.apimessages.pd2.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table (name = "users")
@JsonIgnoreProperties(ignoreUnknown = true, value = {"hibernateLazyInitializer", "fieldHandler"})
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank(message="Alias it cant be empty.")
	private String alias;
	@NotBlank(message="Name it cant be empty.")
	private String name;
	@NotBlank(message="Email it cant be empty.")
	@Pattern(regexp="^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$",message="Email invalid.")
	private String email;
	private String status;
	private UUID uuid;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "userMessageIdentifier", referencedColumnName = "id")
	List<Message> messages = new ArrayList<>();
	
	public User() {
		this.status = "Activo";
		this.uuid = UUID.randomUUID();
	}
	
	public User(Long id, UUID uuid,
			@NotBlank(message = "Name it cant be empty.") String name,
			@NotBlank(message = "Email it cant be empty.") 
			@Pattern(regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$", 
			message = "Email invalid.") String email,
			@NotBlank(message = "Alias it cant be empty.") String alias, String status, List<Message> messages) {
		this.id = id;
		this.uuid = uuid;
		this.alias = alias;
		this.name = name;
		this.email = email;
		this.status = status;
		this.messages = messages;
	}
	
	public User(@NotBlank(message = "Name it cant be empty.") String name,
			@NotBlank(message = "Email it cant be empty.") 
			@Pattern(regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$", 
			message = "Email invalid.") String email, 
			@NotBlank(message = "Alias it cant be empty.") String alias) {
		this.alias = alias;
		this.name = name;
		this.email = email;
	}
	
	public Long getId() {
		return id;
	}

	public UUID getUUID() {
		return uuid;
	}
	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String Email) {
		this.email = Email;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public String getName() {
		return name;
	}

	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
}
