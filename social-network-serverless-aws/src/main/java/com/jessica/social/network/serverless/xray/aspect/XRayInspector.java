package com.jessica.social.network.serverless.xray.aspect;

import com.amazonaws.xray.spring.aop.AbstractXRayInterceptor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class XRayInspector extends AbstractXRayInterceptor {

    // 监控所有的带有@XRayEnabled，@Service或者@Repository的注解的类
    @Override
    @Pointcut("@within(com.amazonaws.xray.spring.aop.XRayEnabled) || @within(org.springframework.stereotype.Service) || @within(org.springframework.stereotype.Repository)")
    public void xrayEnabledClasses() {
    }
}
