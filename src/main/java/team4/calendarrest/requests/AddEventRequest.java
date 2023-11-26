package team4.calendarrest.requests;

import lombok.Data;

import java.sql.Date;
import java.sql.Time;

@Data
public class AddEventRequest {
    private String usernameOrEmail;
    private String password;
    private String name;
    private Date date;
    private Time start_at;
    private Time end_at;

    // Constructors, getters, and setters

    public AddEventRequest() {
    }

    public AddEventRequest(String usernameOrEmail, String password, String name, Date date, Time start_at, Time end_at) {
        this.usernameOrEmail = usernameOrEmail;
        this.password = password;
        this.name = name;
        this.date = date;
        this.start_at = start_at;
        this.end_at = end_at;
    }
}