package sparta.sparta_scheduler_jpa.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class ErrorPageController {

    @RequestMapping("/error-page/404")
    public String errorPage404(HttpServletRequest request, HttpServletResponse response) {
        log.info("errorPage 404");
        printErrorInfo(request);
        return "error-page/404";
    }

    @RequestMapping(value = "/error-page/500", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> errorPage500Api(HttpServletRequest
                                                                       request, HttpServletResponse response) {
        log.info("API errorPage 500");
        Map<String, Object> result = new HashMap<>();

        Exception ex = (Exception) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        result.put("status", request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
        result.put("message", ex.getMessage());

        Integer statusCode = (Integer)
                request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        return new ResponseEntity(result, HttpStatus.valueOf(statusCode));
    }

    private void printErrorInfo(HttpServletRequest request) {
        log.info("ERROR_EXCEPTION: ex=", request.getAttribute(RequestDispatcher.ERROR_EXCEPTION));
        log.info("ERROR_EXCEPTION_TYPE: {}",
                request.getAttribute(RequestDispatcher.ERROR_EXCEPTION_TYPE));
        log.info("ERROR_MESSAGE: {}",
                request.getAttribute(RequestDispatcher.ERROR_MESSAGE));
        // 예외의 경우 NestedServletException으로 스프링이 한 번 감싸서 반환합니다.
        log.info("ERROR_REQUEST_URI: {}",
                request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI));
        log.info("ERROR_SERVLET_NAME: {}",
                request.getAttribute(RequestDispatcher.ERROR_SERVLET_NAME));
        log.info("ERROR_STATUS_CODE: {}",
                request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
        log.info("dispatchType={}", request.getDispatcherType());
    }
}