package com.club.badminton.controller.web.advice;

import com.club.badminton.exception.attachment.FileTooBigException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * controller에 접근하기전 터지는 에러를 잡아줌
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 제한된 용량보다 큰 파일이 들어올경우
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxUploadSizeExceeded(MaxUploadSizeExceededException ex,
                                              HttpServletRequest request,
                                              RedirectAttributes redirectAttributes) {

        // Add your custom flash message
        redirectAttributes.addFlashAttribute("popUpMessage", FileTooBigException.ERROR_MESSAGE);

        // Get the previous page URL from Referer header
        String referer = request.getHeader("Referer");

        // Fallback in case referer is null
        return "redirect:" + (referer != null ? referer : "/");
    }
}
