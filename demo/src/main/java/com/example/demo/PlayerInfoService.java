package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class PlayerInfoService {

    private final PlayerInfoRepository playerInfoRepository;

    @Autowired
    public PlayerInfoService(PlayerInfoRepository playerInfoRepository) {
        this.playerInfoRepository = playerInfoRepository;
    }
/*
    public PlayerInfo createUser(PlayerInfo playerInfo) {
        Optional<PlayerInfo> userOptional = playerInfoRepository.findByEmail(user.getEmail());
        if(userOptional.isPresent()) {
            throw new UserRegistrationException("User with email "+ user.getEmail()+" already exists");
        }

        return playerInfoRepository.save(playerInfo);
    }
*/
    public PlayerInfo updateUser(PlayerInfo PlayerInfo) {
        return playerInfoRepository.save(PlayerInfo);
    }

    public Iterable<PlayerInfo> findAllUsers() {
        return playerInfoRepository.findAll();
    }

    public Optional<PlayerInfo> findPlayerInfoById(Integer id) {
        return playerInfoRepository.findById(id);
    }

    public void deleteUserById(Integer id) {
    	playerInfoRepository.deleteById(id);
    }
}

