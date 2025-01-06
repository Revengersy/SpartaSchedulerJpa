package sparta.sparta_scheduler_jpa.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import sparta.sparta_scheduler_jpa.Service.UserService;
import sparta.sparta_scheduler_jpa.dto.SignupRequestDto;
import sparta.sparta_scheduler_jpa.dto.SignupResponseDto;
import sparta.sparta_scheduler_jpa.dto.UserResponseDto;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signUp(@Valid @RequestBody SignupRequestDto requestDto) {

        SignupResponseDto signUpResponseDto =
                userService.signUp(
                        requestDto.getUsername(),
                        requestDto.getPassword(),
                        requestDto.getAge(),
                        requestDto.getEmail()
                );

        return new ResponseEntity<>(signUpResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable Long id) {


        UserResponseDto memberResponseDto = userService.findById(id);

        return new ResponseEntity<>(memberResponseDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAllUsers() {
        List<UserResponseDto> users = userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody SignupRequestDto requestDto) {
        UserResponseDto updatedUser = userService.updateUser(
                id,
                requestDto.getUsername(),
                requestDto.getPassword(),
                requestDto.getAge(),
                requestDto.getEmail());
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}