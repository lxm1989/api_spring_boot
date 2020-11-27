package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @GetMapping
    public Iterable<PlayerInfo> getAllUsers() {
        return playerInfoService.findAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerInfo> getUserById(@PathVariable Integer id) {
        return playerInfoService.findPlayerInfoById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlayerInfo createUser(@RequestBody @Validated PlayerInfo user) {
        return playerInfoService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PlayerInfo> deleteUser(@PathVariable Integer id) {
        return playerInfoService.findPlayerInfoById(id)
                .map(user -> {
                    playerInfoService.deleteUserById(id);
                    return ResponseEntity.ok(user);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}