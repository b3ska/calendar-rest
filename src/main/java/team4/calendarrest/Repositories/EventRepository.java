package team4.calendarrest.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import team4.calendarrest.enteties.Event;

import java.util.Map;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findById(int id);
}
