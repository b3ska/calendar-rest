package team4.calendarrest.requests;

import lombok.Getter;

@Getter
public class SignUpRequest {
    private String name;
    private String username;
    private String email;
    private String password;
}
