package com.example.demo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.mockito.BDDMockito.given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MainController.class)
@ActiveProfiles("test")

public class MainControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PlayerInfoService playerInfoService;

	@Test
	public void test_getPlayerById() throws Exception {
		PlayerInfo playerInfo = new PlayerInfo();

		playerInfo.setId(1);
		playerInfo.setplayerName("test");
		playerInfo.setScore(100);
		playerInfo.setTime("2020-01-01 12:12:12");

		given(playerInfoService.findPlayerInfoById(1)).willReturn(Optional.of(playerInfo));

		this.mockMvc.perform(get("/demo/{id}",playerInfo.getId()))
		        .andExpect(status().isOk())
				.andExpect(jsonPath("$.playerName").value("test"))
				.andExpect(jsonPath("$.score").value(100))
				.andExpect(jsonPath("$.time").value("2020-01-01 12:12:12"));
	}
}
