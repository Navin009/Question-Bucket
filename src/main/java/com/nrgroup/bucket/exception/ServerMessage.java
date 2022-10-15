package com.nrgroup.bucket.exception;

import com.nrgroup.bucket.enums.Status;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ServerMessage {
    String message;
    Status status;

    public ServerMessage(String message) {
        this.message = message;
    }

    public ServerMessage(Status status) {
        this.status = status;
    }

    public ServerMessage(String message, Status status) {
        this.message = message;
        this.status = status;
    }

    public ServerMessage() {
    }
}
