import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UserMockitoTest {
	@Autowired
    private MockMvc mockMvc;
	@Mock
	UserService userService;
	
	@Test
	void notNulls() {
		assertThat(userService).isNotNull();		
	}
	
	static String asJsonToString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
