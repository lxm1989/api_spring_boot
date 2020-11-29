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

	
	public PlayerInfo createPlayerInfo(PlayerInfo playerInfo) {
		if (playerInfo.getId()!=null) {
			Optional<PlayerInfo> playerInfoOptional = playerInfoRepository.findById(playerInfo.getId());
			if (!(playerInfoOptional.isPresent())) {
				return playerInfoRepository.save(playerInfo);
			} else
				throw new PlayerScoreExistException(
						"PlayerID " + playerInfo.getId() + " already exists");
		}
			
		return playerInfoRepository.save(playerInfo);
	}

	public Optional<PlayerInfo> findPlayerInfoById(Integer id) {
		return playerInfoRepository.findById(id);
	}

	public void deletePlayerInfoById(Integer id) {
		playerInfoRepository.deleteById(id);
	}
	
	public List<PlayerInfo> findPlayerInfoByName(String name) {
		return playerInfoRepository.findByPlayerName(name);
	}
	
	public List<PlayerInfo> findPlayerInfoBeforeTime(String before) {
		return playerInfoRepository.findBeforeTime(before);
	}
	
	public List<PlayerInfo> findPlayerInfoAfterTime(String after) {
		return playerInfoRepository.findAfterTime(after);
	}

}
