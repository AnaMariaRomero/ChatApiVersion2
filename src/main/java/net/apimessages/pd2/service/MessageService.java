package net.apimessages.pd2.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import net.apimessages.pd2.model.Message;
import net.apimessages.pd2.repository.MessageRepository;

@Service
public class MessageService{
	
	private MessageRepository messageRepository;
	
	public MessageService(MessageRepository repository) {
		this.messageRepository = repository;
	}
	

	public List<Message> findAll() {
		List<Message> messages = messageRepository.findAll();
		System.out.print("Getting: " + messages);
		return messages;
	}

	public Message getById(Long id){
		return messageRepository.getById(id);
	}

	public Optional<Message> findById(Long id) {
		return messageRepository.findById(id);
	}
}
