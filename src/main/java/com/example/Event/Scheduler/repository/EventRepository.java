package com.example.Event.Scheduler.repository;

import com.example.Event.Scheduler.entity.Event;
import com.example.Event.Scheduler.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByOrganiser(User organiser);
    List<Event> findByParticipantsContains(User participant);
}
