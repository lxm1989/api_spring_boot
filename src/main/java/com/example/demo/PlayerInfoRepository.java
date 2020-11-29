package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

// This will be AUTO IMPLEMENTED by Spring into a Bean called playerInfoRepository
// CRUD refers Create, Read, Update, Delete

public interface PlayerInfoRepository extends CrudRepository<PlayerInfo, Integer>{
	
	List<PlayerInfo> findByPlayerName(String playerName);
	
	@Query("select u from PlayerInfo u where u.time <= :before order by u.id asc")
    List<PlayerInfo> findBeforeTime(@Param("before") String before);
	
	@Query("select u from PlayerInfo u where u.time >= :after order by u.id asc")
    List<PlayerInfo> findAfterTime(@Param("after") String after);
	
}

