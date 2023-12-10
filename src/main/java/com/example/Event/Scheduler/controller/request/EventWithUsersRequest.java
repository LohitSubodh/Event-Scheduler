package com.example.Event.Scheduler.controller.request;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EventWithUsersRequest {
    private Long organiserId;
    private List<Long> participantIds;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
