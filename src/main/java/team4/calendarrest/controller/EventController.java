package team4.calendarrest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import team4.calendarrest.Repositories.EventRepository;
import team4.calendarrest.Repositories.UserRepository;
import team4.calendarrest.enteties.Event;
import team4.calendarrest.enteties.User;
import team4.calendarrest.requests.AddEventRequest;
import team4.calendarrest.requests.LoginRequest;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/event")
public class EventController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @PostMapping("/add")
    public ResponseEntity<String> addEvent(@RequestBody AddEventRequest request) {
        // Authentication
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                request.getUsernameOrEmail(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(authToken);

        Optional<User> optionalUser = userRepository.findByUsernameOrEmail(request.getUsernameOrEmail(), request.getUsernameOrEmail());
        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        User user = optionalUser.get();

        Event event = new Event(user, request.getName(), request.getDate(), request.getStart_at(), request.getEnd_at());
        eventRepository.save(event);

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Event>> getAllEventsForCurrentUser(@RequestParam String usernameOrEmail, @RequestParam String password) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                usernameOrEmail, password);
        Authentication authentication = authenticationManager.authenticate(authToken);

        Optional<User> optionalUser = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User user = optionalUser.get();

        List<Event> userEvents = eventRepository.findByUser(user);
        System.out.println(userEvents);
        return new ResponseEntity<>(userEvents, HttpStatus.OK);
    }

    @DeleteMapping("/del/{eventId}")
    public ResponseEntity<?> deleteEvent(@PathVariable long eventId, @RequestParam String usernameOrEmail, @RequestParam String password) {

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                usernameOrEmail, password);
        Authentication authentication = authenticationManager.authenticate(authToken);

        Optional<Event> optionalEvent = eventRepository.findById(eventId);

        if (optionalEvent.isPresent()) {
            Event eventToDelete = optionalEvent.get();
            eventRepository.delete(eventToDelete);

            return new ResponseEntity("Success", HttpStatus.OK); // Successful deletion
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}