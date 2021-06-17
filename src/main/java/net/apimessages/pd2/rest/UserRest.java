package net.apimessages.pd2.rest;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
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
import net.apimessages.pd2.exceptions.BadRequest;
import net.apimessages.pd2.exceptions.MessageNotFoundException;
import net.apimessages.pd2.exceptions.ServerError;
import net.apimessages.pd2.model.Message;
import net.apimessages.pd2.model.User;
import net.apimessages.pd2.service.UserService;

@RestController
@RequestMapping("/api/users/")
public class UserRest {
	@Autowired
	private UserService userService;
	
	@GetMapping
	public ResponseEntity<Optional<List<User>>> getAll(){
		Optional<List<User>> users =  Optional.of(userService.findAll());
		return ResponseEntity.ok().body(users);
	}
	
	@PostMapping ("/create/")
	public ResponseEntity<User> create (@RequestBody @Validated User user) throws Exception{
		User temporal = userService.create(user);
		try {
			return ResponseEntity.created(new URI("/api/user/"+temporal.getAlias())).body(temporal);
			
		}catch (Exception n) {
			if (n instanceof NullPointerException ) {
				throw new ServerError("El alias o email que quiere usar ya se encuentra en uso. ");
			}else {
			    throw new BadRequest("Mal pedido por parte del usuario.");}
		}
	}
	
	@PutMapping(path = "/{alias}/chat/", consumes = MediaType.APPLICATION_JSON_VALUE)
		 public ResponseEntity<User> sendMessage(@PathVariable ("alias") String alias, @RequestBody CopyMessage copyMessage) throws Exception{
		 User userTemporal = userService.findByAlias(alias);  
		 Message messageToSend = new Message(userTemporal.getUUID().toString(),copyMessage.getRecipient(),copyMessage.getContent());
		 if ((userTemporal == null) || (userTemporal.getStatus() == "Inactivo")){
		 	throw new BadRequest("Verifique el alias y/o su estado antes de enviar un mensaje por favor.");
		 }else{
		  		
		 	userTemporal.getMessages().add(messageToSend);
		 	userService.save(userTemporal);
		  
		 	return ResponseEntity.created(new URI("/api/users/alias/chat/send/"+userTemporal.getAlias())).body(userTemporal);
		 }
	}
	
	@GetMapping(value = "{alias}")
	public User buscarUsuarioPorAlias (@PathVariable ("alias") String alias) throws Exception{
		try{
			System.out.print(userService.findByAlias(alias));
		}catch(Exception e){
			throw new MessageNotFoundException("No se encontro el usuario con el alias: " + alias);
		}
		
		return userService.findByAlias(alias);
	}
	
	public static class CopyMessage{
		String recipient;
		String content;
		public String getRecipient() {
			return recipient;
		}
		public void setRecipient(String recipient) {
			this.recipient = recipient;
		}
		public CopyMessage(String recipient, String content) {
			this.recipient = recipient;
			this.content = content;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
	}
}
