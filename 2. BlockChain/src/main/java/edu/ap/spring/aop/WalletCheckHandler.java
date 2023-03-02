package edu.ap.spring.aop;

import java.security.PublicKey;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import org.aspectj.lang.*;
import org.aspectj.lang.annotation.*;
import org.bouncycastle.cms.Recipient;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import edu.ap.spring.service.Wallet;
import edu.ap.spring.transaction.Transaction;

@Aspect
@Component
public class WalletCheckHandler {
    @Around("@annotation(edu.ap.spring.aop.WalletCheck) && execution(public edu.ap.spring.transaction.Transaction *(..))")
    public void aroundInterceptable(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Around " + joinPoint.getSignature());
        
        float balance = ((Wallet) joinPoint.getThis()).getBalance();
        PublicKey publicKey = ((Wallet) joinPoint.getThis()).getPublicKey();
        PublicKey recipient = (PublicKey) joinPoint.getArgs()[0];
        float value = Float.parseFloat(joinPoint.getArgs()[1].toString());
        
        if(balance < value) {
			System.out.println("# Not Enough funds to send transaction. Transaction Discarded.");
			throw new Exception();
		}

        if(publicKey == recipient) {
			System.out.println("# You cannot transfer funds to yourself. Transaction Discarded.");
			throw new Exception();
		}
        
        Transaction result = (Transaction) joinPoint.proceed();
        System.out.println("RESULT : " + result);
    }


        @Around("@annotation(edu.ap.spring.aop.Interceptable) && execution(public String getTransactionForm(..)) ")
    public String checkLogin(ProceedingJoinPoint joinPoint) throws Throwable {

        String template = "login";
        //boolean loggedIn = false;

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes())
                .getRequest();

        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if ("Authorisation".equals(cookie.getName())) {
                    if(cookie.getValue().equalsIgnoreCase("admin")) {
                        template = "transaction";
                    }
                }
            }
        }

        joinPoint.proceed();
        return template;
    }
}
