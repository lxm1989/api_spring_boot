package com.example.demo;

import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called playerInfoRepository
// CRUD refers Create, Read, Update, Delete

public interface PlayerInfoRepository extends CrudRepository<PlayerInfo, Integer> {

}

