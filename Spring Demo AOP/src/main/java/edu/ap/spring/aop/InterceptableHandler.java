package edu.ap.spring.aop;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import org.aspectj.lang.*;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Aspect
@Component
public class InterceptableHandler {

     /*@Before("@annotation(edu.ap.spring.aop.Interceptable)")
     public void beforeInterceptable(JoinPoint joinPoint) {
        System.out.println("Before " + joinPoint.getSignature());
    }

    @After("@annotation(edu.ap.spring.aop.Interceptable)")
    public void afterInterceptable(JoinPoint joinPoint) {
        System.out.println("After " + joinPoint.getSignature());
    }*/

    /*@Before("execution(* edu.ap.spring.jpa.PersonRepository.findAll(..))")
    public void beforeInterceptable2(JoinPoint joinPoint) {
        System.out.println("Before " + joinPoint.getSignature());
    }*/

    /*@Before("execution(* edu.ap.spring.jpa.PersonRepository.findByName(..))")
    public void beforeInterceptable3(JoinPoint joinPoint) {
        System.out.println("Before " + joinPoint.getSignature());
        Object[] args = joinPoint.getArgs();
        for(Object arg : args) {
            System.out.println(arg);
        }
    }*/

    @Around("@annotation(edu.ap.spring.aop.Interceptable) && execution(public * get*(..))")
    public ResponseEntity<String> aroundInterceptable(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Around " + joinPoint.getSignature());
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes())
                .getRequest();
        System.out.println(request.getMethod());
        System.out.println(request.getRequestURI());
        System.out.println(request.getRemoteAddr());
        
        String result = joinPoint.proceed().toString();
        System.out.println("RESULT : " + result);
        
        return new ResponseEntity<String>("Hello Intercepted Person", HttpStatus.OK);
    }
}
