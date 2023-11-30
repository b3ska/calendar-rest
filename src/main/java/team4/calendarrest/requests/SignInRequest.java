package team4.calendarrest.requests;

import lombok.Getter;

@Getter
public class SignInRequest {
    String usernameOrEmail;
    String password;
}
