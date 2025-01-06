package sparta.sparta_scheduler_jpa.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import jakarta.validation.constraints.Pattern;

@Getter
public class SignupRequestDto {

    @NotBlank
    private final String username;

    @NotBlank
    private final String password;

    private final Integer age;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private final String email;

    public SignupRequestDto(String username, String password, Integer age, String email) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.email = email;
    }
}