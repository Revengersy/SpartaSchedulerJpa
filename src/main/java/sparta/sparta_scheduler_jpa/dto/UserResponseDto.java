package sparta.sparta_scheduler_jpa.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    // 유저 식별자
    private final Long id;
    // 유저 이름
    private final String name;

    public UserResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
