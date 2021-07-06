package net.apimessages.pd2.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;

import net.apimessages.pd2.management.RandomLanguage;
import net.apimessages.pd2.model.Message;
import net.apimessages.pd2.model.User;
import net.apimessages.pd2.repository.UserRepository;

@Service
public class UserService{
	
	private UserRepository userRepository;
	
	private String  INACTIVE_STATUS = "Inactivo";
	private String ACTIVE_STATUS = "Activo";
	
	public UserService(UserRepository repository) {
		this.userRepository = repository;
	}

	public List<User> findAll() {
		List<User> users = userRepository.findAll();
		return users;
	}
	
	public User getById(Long id){
		return userRepository.getById(id);
	}

	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}
	
	public User create(User user) {
		return (userRepository.findByAlias(user.getAlias()).isEmpty() && (userRepository.findByEmail(user.getEmail()).isEmpty())) ? userRepository.save(user) : null;
	}

	public Optional<User> findByAlias(String alias){
		return userRepository.findByAlias(alias);
	}
	
	public User save(User entity) {
		return userRepository.save(entity);
	}
	
	public Optional<User> sendMessage(Message messageToSend, Optional<User> userTemporal) {
		if((userTemporal.isPresent()) && (userTemporal.get().getStatus().equals(ACTIVE_STATUS))) {
			userTemporal.get().getMessages().add(messageToSend);
			this.save(userTemporal.get());
			return userTemporal;
		}
		return null;
	}
	
	public void setStatus(User user, String status) {
		if (status.contains(ACTIVE_STATUS)) {
			user.setStatus(ACTIVE_STATUS);
		}else if(status.contains(INACTIVE_STATUS)){
			user.setStatus(INACTIVE_STATUS);
		}
		this.save(user);
	}

	public Optional<Object> getView(Optional<User> user) {
		int MIN_MENSAJES = 2;
		if((user.isPresent()) && user.get().getMessages().size()<MIN_MENSAJES) {
			return Optional.of(user);
		}else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", user.get().getName());
			map.put("alias", user.get().getAlias());
			map.put("messages", user.get().getMessages());
			map.put("languages", RandomLanguage.getMapOfPromUsers(user.get().getMessages().size()).toString());
			return Optional.of(map);
		}
	}
}
