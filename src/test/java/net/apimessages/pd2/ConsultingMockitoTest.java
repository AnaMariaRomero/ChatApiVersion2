package net.apimessages.pd2.management;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Collections;
import java.util.HashMap;

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
import java.util.Map;
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
class ConsultingMockitoTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Mock
	UserService userService;
	
	@Mock
	MessageService service;

	@Test
	void notNulls() {
		assertThat(userService).isNotNull();	
	}
	
	@Test
	void notNulls2() {
		assertThat(service).isNotNull();
	}
	
	@Test
	void getMessagesSent() throws Exception{
		Long id = 1l;
		List<Message> messages = new ArrayList<>();
		UUID uuid = UUID.randomUUID();
		User userReturn = new User("juan","gmail@gmail.com","alias");
		User userReturned = new User(id,uuid,"juan","gmail@gmail.com","alias","Activo",messages);
		when(userService.create(any())).thenReturn(userReturned);
		
		mockMvc.perform(get("/api/admin/users/alias/consulting/messages/sent"))
		.andExpect(status().isOk());
	}
	
	static String asJsonToString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
