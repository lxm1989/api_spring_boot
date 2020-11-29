package com.example.demo;

public class PlayerScoreExistException extends RuntimeException {
    public PlayerScoreExistException(String message) {
        super(message);
    }

    public PlayerScoreExistException(String message, Throwable cause) {
        super(message, cause);
    }
}

