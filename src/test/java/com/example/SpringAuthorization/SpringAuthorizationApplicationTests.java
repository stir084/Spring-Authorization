package com.example.SpringAuthorization;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



class SpringAuthorizationApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Test
	void test() throws Exception {
		String hello = "hello";

		mvc.perform(post("/login"))
				.andExpect(status().isOk());
				//.andExpect(content().string(hello));
	}
}
