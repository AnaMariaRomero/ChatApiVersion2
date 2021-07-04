package net.apimessages.pd2.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import net.apimessages.pd2.model.User;
import net.apimessages.pd2.repository.UserRepository;

@Service
public class UserService{
	
	private UserRepository userRepository;
	
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
		if (userRepository.findByAlias(user.getAlias()) == null && (userRepository.findByEmail(user.getEmail()) == null)) {
			return userRepository.save(user);
		}else {
			return null;
		}	
	}

	public User findByAlias(String alias){
		return userRepository.findByAlias(alias);
	}
	
	public User save(User entity) {
		return userRepository.save(entity);
	}
	
	public void setStatus(User user, String status) {
		String ACTIVE_STATUS = "Activo";
		String INACTIVE_STATUS = "Inactivo";
		if (status.contains(ACTIVE_STATUS)) {
			user.setStatus(INACTIVE_STATUS);
		}else if(status.contains(INACTIVE_STATUS)){
			user.setStatus(ACTIVE_STATUS);
		}
	}
}
