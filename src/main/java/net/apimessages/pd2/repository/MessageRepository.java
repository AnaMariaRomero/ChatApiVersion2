package net.apimessages.pd2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.apimessages.pd2.model.Message;

public interface MessageRepository extends JpaRepository<Message,Long>{
	
}