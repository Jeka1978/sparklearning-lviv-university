package spring.check;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * Created by Evegeny on 11/03/2017.
 */
@Component
@Aspect
@EnableAspectJAutoProxy
public class MyAspect {

    @Pointcut("@annotation(ProstoTak)")
    public void prostoTalMethods(){}


    @Before("prostoTalMethods()")
    public void handleProstoTakMethods(){
        System.out.println("PROSTO TAK!!!");
    }


    @After("prostoTalMethods()")
    public void handleProstoTakMethods2(){
        System.out.println("PROSTO VSE!!!");
    }
}
