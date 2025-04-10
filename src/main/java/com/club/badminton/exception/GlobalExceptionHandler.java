package com.club.badminton.exception;

import com.club.badminton.exception.attachment.FileTooBigException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

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
