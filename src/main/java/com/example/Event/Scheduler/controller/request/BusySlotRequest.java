package com.example.Event.Scheduler.controller.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BusySlotRequest {
    private Long userId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
