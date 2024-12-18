package sparta.sparta_scheduler_jpa.dto;

import lombok.Getter;

@Getter
public class SignupRequestDto {

    private final String username;

    private final String password;

    private final Integer age;

    private final String email;

    public SignupRequestDto(String username, String password, Integer age, String email) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.email = email;
    }
}