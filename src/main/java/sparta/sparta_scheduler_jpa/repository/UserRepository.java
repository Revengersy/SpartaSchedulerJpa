package sparta.sparta_scheduler_jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.sparta_scheduler_jpa.entity.User;


import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String userName);
    User findByUserNameAndPassword(String userName, String password);

}