package sparta.sparta_scheduler_jpa.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sparta.sparta_scheduler_jpa.dto.LoginResponseDto;
import sparta.sparta_scheduler_jpa.dto.SignupResponseDto;
import sparta.sparta_scheduler_jpa.dto.UserResponseDto;
import sparta.sparta_scheduler_jpa.entity.User;
import sparta.sparta_scheduler_jpa.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public SignupResponseDto signUp(String username, String password, Integer age, String email) {

        User user = new User(username, password, age, email);

        User savedUser = userRepository.save(user);

        return new SignupResponseDto(savedUser.getId(), savedUser.getUserName(), savedUser.getAge(), savedUser.getEmail());
    }

    public LoginResponseDto login(String userName, String password) {
        // 입력받은 userName, password와 일치하는 Database 조회
        log.info("login trial");
        User user = userRepository.findByUserNameAndPassword(userName, password);
        log.info("user found {}", user);

        if (user == null) {
            // 로그인 실패 처리
            return null; // 또는 예외를 던집니다.
        }

        // 로그인 성공 시 LoginResponseDto 반환
        return new LoginResponseDto(user.getId(), user.getUserName());
    }

    public UserResponseDto findById(Long id) {

        Optional<User> optionalMember = userRepository.findById(id);

        // NPE 방지
        if (optionalMember.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        User findMember = optionalMember.get();

        return new UserResponseDto(findMember.getId(), findMember.getUserName(), findMember.getEmail());
    }

    public List<UserResponseDto> findAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserResponseDto(user.getId(), user.getUserName(), user.getEmail()))
                .collect(Collectors.toList());
    }

    @Transactional
    public UserResponseDto updateUser(Long id, String username, String password, Integer age, String email) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        user.setUserName(username);
        user.setPassword(password);
        user.setAge(age);
        user.setEmail(email);

        userRepository.save(user);
        return new UserResponseDto(user.getId(), user.getUserName(), user.getEmail());
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        userRepository.delete(user);
    }

}