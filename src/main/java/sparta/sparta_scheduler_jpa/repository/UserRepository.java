package sparta.sparta_scheduler_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sparta.sparta_scheduler_jpa.dto.UserResponseDto;
import sparta.sparta_scheduler_jpa.entity.User;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String userName);
    User findByUserNameAndPassword(String userName, String password);

}