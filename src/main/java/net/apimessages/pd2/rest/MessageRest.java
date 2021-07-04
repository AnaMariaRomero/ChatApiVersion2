package net.apimessages.pd2.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
