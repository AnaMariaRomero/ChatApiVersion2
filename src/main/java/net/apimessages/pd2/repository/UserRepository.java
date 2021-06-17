package net.apimessages.pd2.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.apimessages.pd2.model.User;

public interface UserRepository extends JpaRepository<User,Long>{

	@Query(value = "SELECT * FROM users user WHERE user.alias = :alias", 
			  nativeQuery = true) 
	User findByAlias(@Param ("alias") String alias);

	@Query(value = "SELECT * FROM users user WHERE user.email = :email", 
			  nativeQuery = true)
	User findByEmail(String email);

}