package net.apimessages.pd2;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Collections;
import org.springframework.test.web.servlet.ResultActions.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doReturn;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder.*;
import net.apimessages.pd2.model.Message;
import net.apimessages.pd2.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import ch.qos.logback.core.status.Status;
import net.apimessages.pd2.service.MessageService;
import net.apimessages.pd2.service.UserService;
import org.springframework.http.HttpHeaders;
import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.*;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


@SpringBootTest
@AutoConfigureMockMvc
public class UserMockitoTest {

	@Autowired
    private MockMvc mockMvc;
	
	@Mock
	UserService userService;
	
	@Test
	void notNulls() {
		assertThat(userService).isNotNull();		
	}
	
	@Test
	void connectionIsOK() throws Exception {
		List<User> users = new ArrayList<User>();
		when(userService.findAll()).thenReturn(users);
	
		mockMvc.perform(get("/api/users/"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(1)));
	}
	
	@Test
	void createdIsOK() throws Exception {
		Long id = 1l;
		List<Message> messages = new ArrayList<>();
		UUID uuid = UUID.randomUUID();
		User userReturn = new User("juan","gmail@gmail.com","alias");
		User userReturned = new User(id,uuid,"juan","gmail@gmail.com","alias","Activo",messages);
		when(userService.create(any())).thenReturn(userReturned);
	
		mockMvc.perform(post("/api/users/create/").contentType(MediaType.APPLICATION_JSON).content(asJsonToString(userReturned)))
		.andExpect(status().isCreated())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(header().string(HttpHeaders.LOCATION, "/api/user/alias"))
		.andExpect(jsonPath("$.id", is(2)))
		.andExpect(jsonPath("$.status", is("Activo")))
		.andExpect(jsonPath("$.name", is("juan")));
		
	}
	
	@Test
	void createdBadRequest() throws Exception {	    
		when(userService.create(null)).thenReturn(null);
		mockMvc.perform(post("/api/users/create/"))
		.andExpect(status().isBadRequest());
	}
	
	@Test
	void changeStatusIssNotFoundUser() throws Exception {
		Long id = 1l;
		List<Message> messages = new ArrayList<>();
		UUID uuid = UUID.randomUUID();
		User userReturn = new User("juan","gmail@gmail.com","alias");
		User userReturned = new User(id,uuid,"juan","gmail@gmail.com","alias","activo",messages);
		when(userService.create(any())).thenReturn(userReturned);
		
		mockMvc.perform(put("/api/users/alas/status/").contentType(MediaType.APPLICATION_JSON).content(asJsonToString(Collections.singletonMap("Status ", "Activo"))))
		.andExpect(status().isNotFound());
	}
	/*
	@Test
	void changeStatusIsAccepted() throws Exception {
		Long id = 1l;
		List<Message> messages = new ArrayList<>();
		UUID uuid = UUID.randomUUID();
		User userReturn = new User("juan","gmail@gmail.com","alias");
		User userReturned = new User(id,uuid,"juan","gmail@gmail.com","alias","Activo",messages);
		when(userService.save(userReturn)).thenReturn(userReturned);
		
		mockMvc.perform(put("/api/users/{alias}/status/","alias").contentType(MediaType.APPLICATION_JSON).content(asJsonToString(Collections.singletonMap("Status ", "Inactivo"))))
		.andExpect(status().isAccepted());
	}*/
	
	@Test
	void getAliasUserNotFound() throws Exception{
		when(userService.findByAlias("pepe")).thenReturn(new User() {});
		mockMvc.perform(get("/api/users/{alias}", "pepe"))
		.andExpect(status().isNotFound())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	
	}
	
	static String asJsonToString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	
}
