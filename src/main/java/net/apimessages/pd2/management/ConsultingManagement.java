package net.apimessages.pd2.management;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import net.apimessages.pd2.model.Message;
import net.apimessages.pd2.model.User;
import net.apimessages.pd2.service.MessageService;
import net.apimessages.pd2.service.UserService;

@RestController
@RequestMapping("/api/admin/")
public class ConsultingManagement {
	@Autowired
	private UserService userService;
	@Autowired
	private MessageService messageService;
	
	@GetMapping(path = "users/{alias}/consulting/messages/sent")
	public Map<String, Integer> getAllMessagesSentFromUser(@PathVariable ("alias") String alias){
		Map<String, Integer> map = new HashMap<String, Integer>(); 
		User user = userService.findByAlias(alias);
		Iterator<Message> list =  (Iterator<Message>) messageService.findAll();
		while(list.hasNext()){
		    Message message=list.next();
		    if (message.getSender().equals(user.getUUID()) && map.containsValue(message.getSender())) {
		    	map.put(message.getSender(), 1);
		    }else {
		    	Integer integer = map.get(message.getSender());
		    	map.remove(message.getSender());
		    	map.put(message.getSender(), integer+1);
		    }
		}
		return map;
	}
	
	@GetMapping(path = "users/{alias}/consulting/messages/received")
	public Map<String, Integer> getAllMessagesReceivedFromUser(@PathVariable ("alias") String alias){
		Map<String, Integer> map = new HashMap<String, Integer>(); 
		User user = userService.findByAlias(alias);
		Iterator<Message> list =  (Iterator<Message>) messageService.findAll();
		while(list.hasNext()){
		    Message message=list.next();
		    if (message.getRecipient().equals(user.getUUID()) && map.containsValue(message.getRecipient())) {
		    	map.put(message.getSender(), 1);
		    }else {
		    	Integer integer = map.get(message.getRecipient());
		    	map.remove(message.getRecipient());
		    	map.put(message.getRecipient(), integer+1);
		    }
		}
		return map;
	}
	
}