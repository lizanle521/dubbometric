package com.lzl.interceptor;

import com.alibaba.metrics.FastCompass;
import com.alibaba.metrics.MetricManager;
import com.alibaba.metrics.MetricName;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author lizanle
 * @data 2019/3/18 9:10 PM
 */
public class FrontHandlerInterceptor implements HandlerInterceptor {
    ThreadLocal<Long> threadLocal = new ThreadLocal<Long>(){
        @Override
        protected Long initialValue() {
            return System.currentTimeMillis();
        }
    };
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            threadLocal.set(System.currentTimeMillis());
            return true;
        }
        return false;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Class<?> beanType = handlerMethod.getBeanType();
            Method method = handlerMethod.getMethod();
            FastCompass intercep = MetricManager.getFastCompass("dubbometric", MetricName.build(FrontHandlerInterceptor.class.getSimpleName()));
            FastCompass hand = MetricManager.getFastCompass("dubbometric", MetricName.build(beanType.getSimpleName() + "#" + method.getName()));
            long gap = System.currentTimeMillis()-threadLocal.get();
            intercep.record(gap,response.getStatus()+"");
            hand.record(gap,response.getStatus()+"");
        }
    }
}
