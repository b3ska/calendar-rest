package team4.calendarrest.Repositories;

import com.sun.jdi.event.EventSet;
import org.springframework.data.jpa.repository.JpaRepository;
import team4.calendarrest.enteties.Event;
import team4.calendarrest.enteties.User;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findById(int id);
    List<Event> findByUser(User user);
}
