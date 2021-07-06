package net.apimessages.pd2.management;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import net.apimessages.pd2.exceptions.MessageNotFoundException;
import net.apimessages.pd2.model.Message;
import net.apimessages.pd2.model.User;
import net.apimessages.pd2.service.UserService;

@RestController
@RequestMapping("/api/admin/")
public class ConsultingManagement {
	@Autowired
	private UserService userService;
	
	@GetMapping(path = "/users/{alias}/consulting/messages/sent/")
	public Map<String, Integer> getAllMessagesSentFromUser(@PathVariable ("alias") String alias){
		Map<String, Integer> map = new HashMap<String, Integer>(); 
		Optional<User> user = userService.findByAlias(alias);
		map.put("Enviados totales: ", user.get().getMessages().size());
		return map;
	}
	
	@GetMapping(path = "/users/{alias}/consulting/messages/received/")
	public Map<String, Integer> getAllMessagesReceivedFromUser(@PathVariable ("alias") String alias) throws Exception{
		Map<String, Integer> map = new HashMap<String, Integer>(); 
		try {
			Optional<User> user = userService.findByAlias(alias);
			List<Message> list = user.get().getMessages();
			Integer number = 0;
			for(int i = 0;i<list.size();i++) {
				if (list.get(i).getRecipient().equals(user.get().getUUID().toString())) {
			    	number+=1;
			    }}
			map.put("Recibidos totales: ", number);
		}catch(Exception e) {
			throw new MessageNotFoundException("No se encontró el usuario.");
		}
		return map;
	}
	
	@GetMapping(path = "/users/{alias}/consulting/messages/sent/{aliasOtherUser}/")
	public Map<String, Integer> getAllMessagesReceivedOfUser(@PathVariable ("alias") String alias, @PathVariable ("aliasOtherUser") String aliasOtherUser) throws Exception{
		Map<String, Integer> map = new HashMap<String, Integer>(); 
		try {
			Optional<User> userSender = userService.findByAlias(alias);
			Optional<User> userRecipient = userService.findByAlias(aliasOtherUser);
			List<Message> list = userSender.get().getMessages();
			Integer number = 0;
			for(int i = 0;i<list.size();i++) {
				if (list.get(i).getRecipient().equals(userRecipient.get().getUUID().toString())) {
					System.out.print(list.get(i).getRecipient());
			    	number+=1;
			    }}
			map.put("Recibidos totales: ", number);
		}catch(Exception e) {
			throw new MessageNotFoundException("No se encontró el usuario.");
		}
		return map;
	}
	
	@GetMapping(path = "/user/{alias}/lenguages/")
	public Map<String,Integer> getLenguages(@PathVariable ("alias") String alias){
		Optional<User> user = userService.findByAlias(alias);
		return RandomLanguage.getMapOfProm(user.get().getMessages().size());
	}
	
	@GetMapping(path = "/users/lenguages/")
	public Map<String,Integer> getLenguagesUsers(){
		List<User> users = userService.findAll();
		return RandomLanguage.getMapOfProm(users.size());
	}
	
}