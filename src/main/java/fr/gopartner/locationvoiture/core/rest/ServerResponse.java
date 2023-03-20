package fr.gopartner.locationvoiture.core.rest;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ServerResponse {

    private LocalDateTime timeStamp;
    private String message;

    public ServerResponse(LocalDateTime timeStamp, String message) {
        this.timeStamp = timeStamp;
        this.message = message;
    }

}
