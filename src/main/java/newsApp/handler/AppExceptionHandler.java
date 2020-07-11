package newsApp.handler;

import lombok.extern.log4j.Log4j2;
import newsApp.exceptions.NewsNotFound;
import newsApp.exceptions.userException.UserException;
import newsApp.exceptions.userException.UsernameAlreadyExistsEx;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {NewsNotFound.class})
    public ModelAndView handle_error(HttpServletRequest rq,Exception ex){
        log.error(String.format("UserException caught!->%s",ex));
        ModelAndView mav = new ModelAndView();
        mav.addObject("url",rq.getRequestURL());
        mav.addObject("ex",ex);
        mav.addObject("info",ex.getClass().getSimpleName());
        mav.setViewName("error");
        return mav;
    }

    @ExceptionHandler(value = {UsernameAlreadyExistsEx.class})
    public RedirectView handle_user_already_exists(){
        return new RedirectView("/login?exists");
    }

}
