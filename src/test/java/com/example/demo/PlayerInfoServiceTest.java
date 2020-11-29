package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;


@ExtendWith(MockitoExtension.class)
public class PlayerInfoServiceTest {

    @Mock  
    private PlayerInfoRepository playerInfoRepository;

    @InjectMocks  
    private PlayerInfoService playerInfoService;
    
    private PlayerInfo playerInfo;

    @BeforeEach
    void setUp() {
    	this.playerInfo = new PlayerInfo(1,"test",100,"2020-01-01 12:12:12");
    }

    
    @Test
    void test_createPlayerInfo() {

        given(playerInfoRepository.save(playerInfo)).willAnswer(invocation -> invocation.getArgument(0));

        PlayerInfo savedUser = playerInfoService.createPlayerInfo(playerInfo);

        assertThat(savedUser).isNotNull();

        verify(playerInfoRepository).save(any(PlayerInfo.class));

    }
	
    @Test
    public void test_findPlayerInfoById() {

    	given(playerInfoRepository.findById(1)).willReturn(Optional.of(playerInfo));

        final Optional<PlayerInfo> expected  =playerInfoService.findPlayerInfoById(1);

        assertThat(expected).isNotNull();
    }
	
	 @Test
	    void test_deletePlayerInfo() {

		    playerInfoService.deletePlayerInfoById(1);	  
		    playerInfoService.deletePlayerInfoById(1);	

	        verify(playerInfoRepository, times(2)).deleteById(1);
	    }


}