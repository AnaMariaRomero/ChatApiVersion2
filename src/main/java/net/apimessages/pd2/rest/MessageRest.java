package net.apimessages.pd2.rest;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import net.apimessages.pd2.exceptions.MessageNotFoundException;
import net.apimessages.pd2.model.Message;
import net.apimessages.pd2.service.MessageService;

@RestController
public class MessageRest {
	
	@Autowired
	private MessageService messageService;
	
	@GetMapping("**/messages")
	public ResponseEntity<Optional<List<Message>>> getAll(){
		Optional<List<Message>> messages =  Optional.of(messageService.findAll());
		return ResponseEntity.ok().body(messages);
	}
	
	@PostMapping("**/sending")
	public ResponseEntity<Message> create (@RequestBody @Validated Message message){
		Message temporal = messageService.create(message);
		
		try {
			return ResponseEntity.created(new URI("/messages/"+temporal.getId())).body(temporal);
			
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	
	@GetMapping ("**/messages/{id}")
	public Message listarMensajesPorId (@PathVariable ("id") Long id) throws Exception{
		try{
			System.out.print(messageService.getById(id));
		}catch(Exception e){
			throw new MessageNotFoundException("No se encontro el mensaje con el id: " + id);
		}
		
		return messageService.getById(id);
	}
}
