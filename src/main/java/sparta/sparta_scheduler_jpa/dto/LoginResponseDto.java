package sparta.sparta_scheduler_jpa.dto;

import lombok.Getter;

@Getter
public class LoginResponseDto {
    private final Long id;
    private final String username;
    // 이외 응답에 필요한 데이터들을 필드로 구성하면 된다.
    // 필요한 생성자
    public LoginResponseDto(Long id, String username) {
        this.id = id;
        this.username = username;
    }
}
