package sparta.sparta_scheduler_jpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sparta.sparta_scheduler_jpa.Service.UserService;
import sparta.sparta_scheduler_jpa.dto.SignupRequestDto;
import sparta.sparta_scheduler_jpa.dto.SignupResponseDto;
import sparta.sparta_scheduler_jpa.dto.UserResponseDto;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signUp(@RequestBody SignupRequestDto requestDto) {

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

}