package sparta.sparta_scheduler_jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "user")
public class User extends BaseEntity {
    // 식별자
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 로그인 ID
    @Column(nullable = false, unique = true, length = 50)
    private String userName;
    // 비밀번호
    @Column(nullable = false)
    private String password;


    // 나이
    private Integer age;

    public User() {}

    public User(String userName, String password, Integer age) {
        this.userName = userName;
        this.password = password;
        this.age = age;
    }


}