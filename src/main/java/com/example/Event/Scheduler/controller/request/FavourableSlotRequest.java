package com.example.Event.Scheduler.controller.request;

import lombok.Data;

import java.util.List;

@Data
public class FavourableSlotRequest {
    private Long organiserId;
    private List<Long> participantIds;
    private int durationMinutes;
}
