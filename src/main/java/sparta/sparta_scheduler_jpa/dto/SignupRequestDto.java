package sparta.sparta_scheduler_jpa.dto;

import lombok.Getter;

@Getter
public class SignupRequestDto {

    private final String username;

    private final String password;

    private final Integer age;

    public SignupRequestDto(String username, String password, Integer age) {
        this.username = username;
        this.password = password;
        this.age = age;
    }
}