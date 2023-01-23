package web.app.restfulquiz.AOP;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class PointCuts {

    @Pointcut("within(web.app.restfulquiz.controller.*)")
    public void inControllerLayer(){}

    @Pointcut("bean(*Service)")
    public void inService(){}

    @Pointcut("execution(* web.app.restfulquiz.dao.*.*(..))")
    public void inDAOLayer(){}

}
