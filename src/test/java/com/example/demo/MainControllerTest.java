package com.example.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@WebMvcTest
public class MainControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PlayerInfoService playerInfoService;

	@Autowired
    private ObjectMapper objectMapper;
	
	private PlayerInfo playerInfo;
	
	@BeforeEach
    void setUp() {
        this.playerInfo = new PlayerInfo(1,"test",100,"2020-01-01 12:12:12");
 
		this.objectMapper = new ObjectMapper();
		this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }
	
	@Test
    void test_createPlayerInfo() throws Exception {
		
        given(playerInfoService.createPlayerInfo(any(PlayerInfo.class))).willAnswer((invocation) -> invocation.getArgument(0));

        this.mockMvc.perform(post("/demo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(playerInfo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.playerName", is(playerInfo.getPlayerName())))
                .andExpect(jsonPath("$.score", is(playerInfo.getScore())))
                .andExpect(jsonPath("$.time", is(playerInfo.getTime())));
    }
	
	@Test
	public void test_findPlayerInfoById() throws Exception {

		given(playerInfoService.findPlayerInfoById(1)).willReturn(Optional.of(playerInfo));

		this.mockMvc.perform(get("/demo/{id}",playerInfo.getId()))
		        .andExpect(status().isOk())
		        .andExpect(jsonPath("$.playerName", is(playerInfo.getPlayerName())))
                .andExpect(jsonPath("$.score", is(playerInfo.getScore())))
                .andExpect(jsonPath("$.time", is(playerInfo.getTime())));
	}
	
	@Test
    void test_deletePlayerInfo() throws Exception {
        
		given(playerInfoService.findPlayerInfoById(1)).willReturn(Optional.of(playerInfo));
        doNothing().when(playerInfoService).deletePlayerInfoById(playerInfo.getId());

        this.mockMvc.perform(delete("/demo/{id}", playerInfo.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.playerName", is(playerInfo.getPlayerName())))
                .andExpect(jsonPath("$.score", is(playerInfo.getScore())))
                .andExpect(jsonPath("$.time", is(playerInfo.getTime())));

    }
	
}
