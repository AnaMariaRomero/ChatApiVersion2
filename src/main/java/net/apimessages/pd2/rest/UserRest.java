package net.apimessages.pd2.rest;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import net.apimessages.pd2.exceptions.*;
import net.apimessages.pd2.model.Message;
import net.apimessages.pd2.model.User;
import net.apimessages.pd2.service.UserService;

@RestController
@RequestMapping("/api/users/")
public class UserRest {
	@Autowired
	private UserService userService;
	
	public UserRest(UserService service) {
		this.userService = service;
	}
	
	@GetMapping
	public ResponseEntity<List<User>> getAll(){
		List<User> users =  userService.findAll();
		return ResponseEntity.ok().body(users);
	}
	
	@PostMapping ("/create/")
	public ResponseEntity<User> create (@RequestBody @Validated User user) throws Exception{
		User temporal = userService.create(user);
		try {
			return ResponseEntity.created(new URI("/api/user/"+temporal.getAlias())).body(temporal);
			
		}catch (Exception n) {
			if (n instanceof NullPointerException ) {
				throw new BadRequest("El alias o email que quiere usar ya se encuentra en uso. ");
			}else {
			    throw new ServerError("Falló el sistema. Lo sentimos.");}
		}
	}
	
	@PutMapping(path = "/{alias}/chat/", consumes = MediaType.APPLICATION_JSON_VALUE)
		 public ResponseEntity<User> sendMessage(@PathVariable ("alias") String alias, @RequestBody CopyMessage copyMessage) throws Exception{
		 User userTemporal = userService.findByAlias(alias);  
		 String  USER_STATUS_INACTIVE = "Inactivo";
		 Message messageToSend = new Message(userTemporal.getUUID().toString(),copyMessage.getRecipient(),copyMessage.getContent());
		 if ((userTemporal == null) || (userTemporal.getStatus().equalsIgnoreCase(USER_STATUS_INACTIVE))){
		 	throw new BadRequest("Verifique el alias y/o su estado antes de enviar un mensaje por favor.");
		 }else{
		  		
		 	userTemporal.getMessages().add(messageToSend);
		 	userService.save(userTemporal);
		  
		 	return ResponseEntity.created(new URI("/chat/"+userTemporal.getAlias())).body(userTemporal);
		 }
	}
	
	@GetMapping(value = "{alias}")
	public User searchForAlias (@PathVariable ("alias") String alias) throws Exception{
		if(userService.findByAlias(alias) == null) {
			throw new MessageNotFoundException("No se encontro usuario.");
		}else {
			return userService.findByAlias(alias);
		}
		
	}
	
	@PutMapping(path = "/{alias}/status/", consumes = MediaType.APPLICATION_JSON_VALUE)
	 public ResponseEntity<Object> changeStatus(@PathVariable ("alias") String alias, @RequestBody String copy) throws Exception{
		try{
			User userTemporal = userService.findByAlias(alias);
			userService.setStatus(userTemporal,copy);
			userService.save(userTemporal);
		}catch(Exception e){
			if (e instanceof NullPointerException ) {
				throw new MessageNotFoundException("No se encontro el usuario con el alias: " + alias);
			}else {
			    throw new BadRequest("El estado es inválido.");}
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(
		           Collections.singletonMap("Status ", "Cambiado"));
	}
	
	public static class CopyMessage{
		String recipient;
		String content;
		public String getRecipient() {
			return recipient;
		}
		public CopyMessage(String recipient, String content) {
			this.recipient = recipient;
			this.content = content;
		}
		public String getContent() {
			return content;
		}
	}
}
