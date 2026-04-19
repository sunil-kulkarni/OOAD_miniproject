package com.example.blog.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
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
        redirectAttributes.addFlashAttribute("toastError", "Upload failed: file size must be under 10MB.");
        String referer = request.getHeader("Referer");
        if (referer != null && !referer.isBlank()) {
            String path = referer.replaceFirst("^[^:]+://[^/]+", "");
            return "redirect:" + path;
        }
        return "redirect:/";
    }
}
