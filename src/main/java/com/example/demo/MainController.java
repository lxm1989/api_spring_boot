package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/demo")
public class MainController {

	private final PlayerInfoService playerInfoService;

	public MainController(PlayerInfoService playerInfoService) {
		this.playerInfoService = playerInfoService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PlayerInfo createPlayerInfo(@RequestBody @Validated PlayerInfo playerInfo) {
		return playerInfoService.createPlayerInfo(playerInfo);
	}

	@GetMapping("/{id}")
	public ResponseEntity<PlayerInfo> findPlayerInfoById(@PathVariable Integer id) {
		return playerInfoService.findPlayerInfoById(id).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<PlayerInfo> deletePlayerInfo(@PathVariable Integer id) {
		return playerInfoService.findPlayerInfoById(id).map(playerInfo -> {
			playerInfoService.deletePlayerInfoById(id);
			return ResponseEntity.ok(playerInfo);
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/name")
	public List<Integer> findPlayerInfoByName(@RequestParam("name") List<String> names) {
		List<Integer> lt = new ArrayList<>();
		for (String name : names) {
			for (PlayerInfo playerInfo : playerInfoService.findPlayerInfoByName(name)) {
				lt.add(playerInfo.getScore());
			}
		}

		return lt;
	}

	@GetMapping("/before")
	public List<Integer> findPlayerInfoBeforeTime(@RequestParam("before") String before) {
		List<Integer> lt = new ArrayList<>();

		for (PlayerInfo playerInfo : playerInfoService.findPlayerInfoBeforeTime(before)) {
			lt.add(playerInfo.getScore());
		}

		return lt;
	}

	@GetMapping("/after")
	public List<Integer> findPlayerInfoAfterTime(@RequestParam("after") String after) {
		List<Integer> lt = new ArrayList<>();

		for (PlayerInfo playerInfo : playerInfoService.findPlayerInfoAfterTime(after)) {
			lt.add(playerInfo.getScore());
		}

		return lt;
	}

}