package com.example.Event.Scheduler.controller;

import com.example.Event.Scheduler.controller.request.BusySlotRequest;
import com.example.Event.Scheduler.controller.request.EventWithUsersRequest;
import com.example.Event.Scheduler.controller.request.FavourableSlotRequest;
import com.example.Event.Scheduler.entity.Event;
import com.example.Event.Scheduler.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class EventController {
    @Autowired
    private EventService eventService;

    @PostMapping("/busy")
    public ResponseEntity<Event> createBusySlot(@RequestBody BusySlotRequest request) {
        Event event = eventService.createBusySlot(request.getUserId(), request.getStartTime(), request.getEndTime());
        return ResponseEntity.ok(event);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Event>> getUserEvents(@PathVariable Long userId) {
        List<Event> events = eventService.getUserEvents(userId);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/conflicts/{userId}")
    public ResponseEntity<List<Event>> getConflictEvents(@PathVariable Long userId,
                                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime date) {
        List<Event> events = eventService.getConflictEvents(userId, date);
        return ResponseEntity.ok(events);
    }

    @PostMapping("/favourable-slot")
    public ResponseEntity<LocalDateTime> findFavourableSlot(@RequestBody FavourableSlotRequest request) {
        LocalDateTime favourableSlot = eventService.findFavourableSlot(request.getParticipantIds(), request.getDurationMinutes());
        return ResponseEntity.ok(favourableSlot);
    }

    @PostMapping("/create-event")
    public ResponseEntity<Event> createEventWithUsers(@RequestBody EventWithUsersRequest request) {
        Event event = eventService.createEventWithUsers(request.getOrganiserId(), request.getParticipantIds(), request.getStartTime(), request.getEndTime());
        return ResponseEntity.ok(event);
    }
}
