package com.example.demo;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IntegratedApplicationTests {

	private int findPlayerInfoId = 3;
	private int deletePlayerInfoId = 23;
	
	@Autowired
	private WebApplicationContext wac;
	private ObjectMapper objectMapper;
	private MockMvc mockMvc;
	private PlayerInfo playerInfo;	
	private PlayerInfo playerInfo_delete;	

	@BeforeAll
	public void setup() {
		this.playerInfo = new PlayerInfo("First", 2, "2020-01-01 12:12:12");
		this.playerInfo_delete = new PlayerInfo("First", 2, "2020-01-01 12:12:12");
		this.objectMapper = new ObjectMapper();
		this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

	}

	@Test 
	@Order(1)  
	void test_createPlayerInfo() throws Exception {

		this.mockMvc.perform(post("/demo")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsBytes(playerInfo)))
				    .andExpect(status().isCreated())
				    .andExpect(jsonPath("$.playerName", is(playerInfo.getPlayerName())))
				    .andExpect(jsonPath("$.score", is(playerInfo.getScore())))
				    .andExpect(jsonPath("$.time", is(playerInfo.getTime())));
	}

	@Test
	@Order(2)  
	public void test_findPlayerInfoById() throws Exception {
		
		this.mockMvc.perform(get("/demo/{id}", findPlayerInfoId))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.playerName", is(playerInfo.getPlayerName())))
					.andExpect(jsonPath("$.score", is(playerInfo.getScore())))
					.andExpect(jsonPath("$.time", is(playerInfo.getTime())));
	}
	
	@Test
	@Order(3)  
    void test_deletePlayerInfo() throws Exception {

        this.mockMvc.perform(delete("/demo/{id}",deletePlayerInfoId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.playerName", is(playerInfo_delete.getPlayerName())))
                .andExpect(jsonPath("$.score", is(playerInfo_delete.getScore())))
                .andExpect(jsonPath("$.time", is(playerInfo_delete.getTime())));
    }
	 
}