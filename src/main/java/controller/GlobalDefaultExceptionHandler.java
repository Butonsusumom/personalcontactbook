package controller;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
public class GlobalDefaultExceptionHandler {
    public static final String DEFAULT_ERROR_VIEW = "error";

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }

        String detailedException = "";
        Throwable cause = e.getCause();
        while (cause != null) {
            detailedException = detailedException + " Case: " + cause + " ";
            cause = cause.getCause();
        }

        ModelAndView model = new ModelAndView();
        model.addObject("warningMessage", detailedException);
        model.addObject("errorMessage", req.getRequestURL());
        model.setViewName("Login");
        return model;
    }
}
