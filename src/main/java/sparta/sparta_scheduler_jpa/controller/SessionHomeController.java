package sparta.sparta_scheduler_jpa.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import sparta.sparta_scheduler_jpa.Service.UserService;
import sparta.sparta_scheduler_jpa.dto.UserResponseDto;


@Slf4j
@Controller
@RequiredArgsConstructor
public class SessionHomeController {

    private final UserService userService;

    @GetMapping("/")
    public String loginUi() {
        return "redirect:/session-home";
    }

    @GetMapping("/session-home")
    public String home(
            HttpServletRequest request,
            Model model
    ) {
        log.info("session-home");
        // default인 true로 설정되면 로그인하지 않은 사람들도 값은 비어있지만 세션이 만들어진다.
        // session을 생성할 의도가 없다.
        HttpSession session = request.getSession(false);

        // session이 없으면 로그인 페이지로 이동
        if(session == null) {
            log.info("session is null. Convert to login page");
            return "session-login-view";
        }

        // session에 저장된 유저정보 조회
        // 반환타입이 Object여서 Type Casting이 필요하다.
        UserResponseDto loginUser = (UserResponseDto) session.getAttribute(Const.LOGIN_USER);

        // Session에 유저 정보가 없으면 login 페이지 이동
        if (loginUser == null) {
            log.info("login user is null");
            return "session-login-view";
        }

        log.info("login user: {}", loginUser);
        log.info("check the session");

        // Session이 정상적으로 조회되면 로그인된것으로 간주
        model.addAttribute("loginUser", loginUser);
        // home 화면으로 이동
        return "home";
    }

}