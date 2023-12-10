package com.example.Event.Scheduler.service;

import com.example.Event.Scheduler.entity.Event;
import com.example.Event.Scheduler.entity.User;
import com.example.Event.Scheduler.repository.EventRepository;
import com.example.Event.Scheduler.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    public Event createBusySlot(Long userId, LocalDateTime startTime, LocalDateTime endTime) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Event event = new Event();
        event.setOrganiser(user);
        event.setStartTime(startTime);
        event.setEndTime(endTime);
        return eventRepository.save(event);
    }

    public List<Event> getUserEvents(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // Fetch events where the user is the organizer
        List<Event> organizedEvents = eventRepository.findByOrganiser(user);

        // Fetch events where the user is a participant
        List<Event> participantEvents = eventRepository.findByParticipantsContains(user);

        // Combine both lists and remove duplicates
        return Stream.concat(organizedEvents.stream(), participantEvents.stream())
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Event> getConflictEvents(Long userId, LocalDateTime date) {
        List<Event> userEvents = getUserEvents(userId);
        return userEvents.stream()
                .filter(event -> event.getStartTime().toLocalDate().equals(date.toLocalDate()))
                .collect(Collectors.toList());
    }

    public LocalDateTime findFavourableSlot(List<Long> participantIds, int durationMinutes) {
        // Get the current time as the starting point for finding a slot
        LocalDateTime now = LocalDateTime.now();

        // Get the schedules for all participants
        List<List<Event>> schedules = participantIds.stream()
                .map(userRepository::findById)
                .filter(java.util.Optional::isPresent)
                .map(java.util.Optional::get)
                .map(user -> eventRepository.findByOrganiser(user))
                .collect(Collectors.toList());

        // Find a slot that is free for all participants
        // This is a simplified version and assumes events are sorted by start time
        LocalDateTime potentialStart = now;
        boolean slotFound;
        do {
            slotFound = true;
            LocalDateTime potentialEnd = potentialStart.plusMinutes(durationMinutes);

            // Check each participant's schedule for conflicts
            for (List<Event> schedule : schedules) {
                for (Event event : schedule) {
                    if (event.getStartTime().isBefore(potentialEnd) && event.getEndTime().isAfter(potentialStart)) {
                        // Conflict found, move potential start after this event
                        potentialStart = event.getEndTime();
                        slotFound = false;
                        break;
                    }
                }
                if (!slotFound) break; // No need to check further if a conflict is found
            }
        } while (!slotFound);

        return potentialStart;

    }

    public Event createEventWithUsers(Long organiserId, List<Long> participantIds, LocalDateTime startTime, LocalDateTime endTime) {
        User organiser = userRepository.findById(organiserId).orElseThrow(() -> new RuntimeException("Organiser not found"));
        List<User> participants = participantIds.stream()
                .map(id -> userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")))
                .collect(Collectors.toList());

        Event event = new Event();
        event.setOrganiser(organiser);
        event.setParticipants(participants);
        event.setStartTime(startTime);
        event.setEndTime(endTime);
        return eventRepository.save(event);
    }

}
