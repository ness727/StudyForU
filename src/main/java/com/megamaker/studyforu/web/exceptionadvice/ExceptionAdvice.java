package com.megamaker.studyforu.web.exceptionadvice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
@RequiredArgsConstructor
@RestControllerAdvice(basePackages = {

})
public class ExceptionAdvice {
    private final MessageSource ms;



    // 그 외의 모든 예외 처리
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult etcException(Exception e) {
        return createErrorResult("etc", e);
    }

    private ErrorResult createErrorResult(String exName, Exception e) {
        String code = ms.getMessage("code." + exName, null, null);
        String message = ms.getMessage("message." + exName, null, null);
        log.error(message, e);
        return new ErrorResult(code, message);
    }
}
