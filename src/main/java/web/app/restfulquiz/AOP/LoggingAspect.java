package web.app.restfulquiz.AOP;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("web.app.restfulquiz.AOP.PointCuts.inControllerLayer()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        logger.info("Executed method: {} with execution time: {} ms", joinPoint.getSignature(), executionTime);
        return proceed;
    }

    @AfterThrowing(value = "web.app.restfulquiz.AOP.PointCuts.inControllerLayer()", throwing = "ex")
    public void logThrownException(JoinPoint joinPoint, Throwable ex){
        logger.error("From LoggingAspect.logThrownException in controller: " + ex.getMessage() + ": " + joinPoint.getSignature());
    }

}
