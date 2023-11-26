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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/add")
    public ResponseEntity<String> addEvent(@RequestBody AddEventRequest request) {
        // Authentication
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                request.getUsernameOrEmail(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(authToken);

        // Check if the user exists
        Optional<User> optionalUser = userRepository.findByUsernameOrEmail(request.getUsernameOrEmail(), request.getUsernameOrEmail());
        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        User user = optionalUser.get();

        // Create and save the event
        Event event = new Event(user, request.getName(), request.getDate(), request.getStart_at(), request.getEnd_at());
        eventRepository.save(event);

        // Set the authentication in the SecurityContextHolder

        // Return the ID of the newly added event in the response
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Event>> getAllEventsForCurrentUser(@RequestBody LoginRequest logReq) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                logReq.getUsernameOrEmail(), logReq.getPassword());
        Authentication authentication = authenticationManager.authenticate(authToken);

        // Find the user based on username or email
        Optional<User> optionalUser = userRepository.findByUsernameOrEmail(logReq.getUsernameOrEmail(), logReq.getUsernameOrEmail());
        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User user = optionalUser.get();

        // Retrieve all events associated with the user
        List<Event> userEvents = eventRepository.findByUser(user);
        System.out.println(userEvents);
        return new ResponseEntity<>(userEvents, HttpStatus.OK);
    }

    @DeleteMapping("/del/{eventId}")
    public ResponseEntity<?> deleteEvent(@PathVariable long eventId, @RequestParam String usernameOrEmail, @RequestParam String password) {

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                usernameOrEmail, password);
        Authentication authentication = authenticationManager.authenticate(authToken);
        // Check if the event with the given ID exists
        Optional<Event> optionalEvent = eventRepository.findById(eventId);

        if (optionalEvent.isPresent()) {
            // Event found, proceed with deletion
            Event eventToDelete = optionalEvent.get();
            eventRepository.delete(eventToDelete);

            return new ResponseEntity("Success", HttpStatus.OK); // Successful deletion
        } else {
            // Event not found, return a 404 Not Found response
            return ResponseEntity.notFound().build();
        }
    }
}