package sparta.sparta_scheduler_jpa.dto;

import lombok.Getter;

@Getter
public class SignupResponseDto {
    private final Long id;

    private final String username;

    private final Integer age;

    private final String email;

    public SignupResponseDto(Long id, String username, Integer age, String email) {
        this.id = id;
        this.username = username;
        this.age = age;
        this.email = email;
    }
}