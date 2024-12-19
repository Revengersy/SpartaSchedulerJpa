package sparta.sparta_scheduler_jpa.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sparta.sparta_scheduler_jpa.config.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    public SignupResponseDto signUp(String username, String password, Integer age, String email) {
        String encryptedPassword = passwordEncoder.encode(password);

        User user = new User(username, encryptedPassword, age, email);
        User savedUser = userRepository.save(user);

        return new SignupResponseDto(savedUser.getId(), savedUser.getUserName(), savedUser.getAge(), savedUser.getEmail());
    }

    public LoginResponseDto login(String username, String password) {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        log.info("Service : login");

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect password.");
        }

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