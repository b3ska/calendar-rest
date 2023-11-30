package team4.calendarrest.enteties;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@Setter
@Getter
@Entity
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String name;
    private Date date;
    private Time start_at;
    private Time end_at;

    public Event(User user, String name, Date date, Time start_at, Time end_at) {
        this.user = user;
        this.name = name;
        this.date = date;
        this.start_at = start_at;
        this.end_at = end_at;
    }

    public Event() {

    }

    public Event(long id, Object o, String name, java.util.Date date, Time startAt, Time endAt) {
    }
}
