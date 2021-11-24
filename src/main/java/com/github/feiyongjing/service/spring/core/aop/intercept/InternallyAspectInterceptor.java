package com.github.feiyongjing.service.spring.core.aop.intercept;

import com.github.feiyongjing.service.spring.annotation.aop.*;
import com.github.feiyongjing.service.spring.core.aop.lang.MethodInvocationProceedingJoinPoint;
import com.github.feiyongjing.service.spring.core.aop.util.PatternMatchUtils;
import com.github.feiyongjing.service.spring.common.ReflectionUtil;
import com.github.feiyongjing.service.spring.core.aop.lang.JoinPoint;
import com.github.feiyongjing.service.spring.core.aop.lang.JoinPointImpl;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class InternallyAspectInterceptor extends Interceptor {

    /**
     * 一个被@Aspect标注的切面类实例
     */
    private final Object adviceBean;
    /**
     * key @Aspect标注类中方法上标注了@Pointcut注解的方法切点表达式
     * value @Aspect标注类中标注了@Pointcut的方法集合
     */
    private final HashMap<String, List<Method>> pointcutExpressionUrlAndMethods = new HashMap<>();
    /**
     * key @Aspect标注类中方法上标注了@Around注解的方法切点表达式
     * value @Aspect标注类中标注了@Around的方法集合
     */
    private final HashMap<String, List<Method>> aroundExpressionUrlAndMethods = new HashMap<>();
    /**
     * key   @Aspect标注类中标注了@Before注解的方法切点表达式
     * value @Aspect标注类中标注了@Before的方法集合
     */
    private final HashMap<String, List<Method>> beforeExpressionUrlAndMethods = new HashMap<>();

    /**
     * key   @Aspect标注类中标注了@After注解的方法切点表达式
     * value @Aspect标注类中标注了@After的方法集合
     */
    private final HashMap<String, List<Method>> afterExpressionUrlAndMethods = new HashMap<>();

    /**
     * key   @Aspect标注类中标注了@AfterReturning注解的方法切点表达式
     * value @Aspect标注类中标注了@AfterReturning的方法集合
     */
    private final HashMap<String, List<Method>> afterReturningExpressionUrlAndMethods = new HashMap<>();
    /**
     * key   @Aspect标注类中标注了@AfterThrowing注解的方法切点表达式
     * value @Aspect标注类中标注了@AfterThrowing的方法集合
     */
    private final HashMap<String, List<Method>> afterThrowingExpressionUrlAndMethods = new HashMap<>();

    public InternallyAspectInterceptor(Object adviceBean) {
        this.adviceBean = adviceBean;
        init();
    }

    /**
     * 拦截器的初始化
     * 获取切面的切入点放入expressionUrls、beforeMethods、afterMethods字段中存储
     */
    private void init() {
        for (Method method : adviceBean.getClass().getMethods()) {
            filterByMethodAnnotationPointcut(method);
            filterByMethodAnnotationAround(method);
            filterByMethodAnnotationBefore(method);
            filterByMethodAnnotationAfter(method);
            filterByMethodAnnotationAfterReturning(method);
            filterByMethodAnnotationAfterThrowing(method);
        }
    }

    private void filterByMethodAnnotationPointcut(Method method) {
        Pointcut pointcut = method.getAnnotation(Pointcut.class);
        if (!Objects.isNull(pointcut)) {
            mapValuesAdd(pointcutExpressionUrlAndMethods, pointcut.value(), method);
        }
    }

    private void filterByMethodAnnotationAround(Method method) {
        Around around = method.getAnnotation(Around.class);
        if (!Objects.isNull(around)) {
            mapValuesAdd(aroundExpressionUrlAndMethods, around.value(), method);
        }
    }

    private void filterByMethodAnnotationBefore(Method method) {
        Before before = method.getAnnotation(Before.class);
        if (!Objects.isNull(before)) {
            mapValuesAdd(beforeExpressionUrlAndMethods, before.value(), method);
        }
    }

    private void filterByMethodAnnotationAfter(Method method) {
        After after = method.getAnnotation(After.class);
        if (!Objects.isNull(after)) {
            mapValuesAdd(afterExpressionUrlAndMethods, after.value(), method);
        }
    }

    private void filterByMethodAnnotationAfterReturning(Method method) {
        AfterReturning afterReturning = method.getAnnotation(AfterReturning.class);
        if (!Objects.isNull(afterReturning)) {
            mapValuesAdd(afterReturningExpressionUrlAndMethods, afterReturning.value(), method);
        }
    }

    private void filterByMethodAnnotationAfterThrowing(Method method) {
        AfterThrowing afterReturning = method.getAnnotation(AfterThrowing.class);
        if (!Objects.isNull(afterReturning)) {
            mapValuesAdd(afterThrowingExpressionUrlAndMethods, afterReturning.value(), method);
        }
    }

    private void mapValuesAdd(Map<String, List<Method>> map, String key, Method method) {
        List<Method> methods = map.containsKey(key) ? map.get(key) : new ArrayList<>();
        methods.add(method);
        map.put(key, methods);
    }

    @Override
    public boolean supports(Object bean) {
        return pointMatch(aroundExpressionUrlAndMethods, bean.getClass()) ||
                pointMatch(beforeExpressionUrlAndMethods, bean.getClass()) ||
                pointMatch(afterExpressionUrlAndMethods, bean.getClass()) ||
                pointMatch(afterReturningExpressionUrlAndMethods, bean.getClass()) ||
                pointMatch(afterThrowingExpressionUrlAndMethods, bean.getClass());
    }

    /**
     * 判断是否beanClass是否被部分切面方法拦截
     * @param expressionMethod 部分切面的切点表达式和方法集
     * @param beanClass beanClass
     * @return beanClass被部分切面方法拦截返回true，否则返回false
     */
    private boolean pointMatch(HashMap<String, List<Method>> expressionMethod, Class<?> beanClass) {
        return expressionMethod.keySet().stream()
                .anyMatch(url -> PatternMatchUtils.isMatch(url, beanClass, pointcutExpressionUrlAndMethods));
    }

    @Override
    public Object intercept(MethodInvocation methodInvocation) {
        Object result;
        Object targetBean = methodInvocation.getTargetObject();
        JoinPoint joinPoint=new JoinPointImpl(adviceBean, targetBean,
                methodInvocation.getArgs());
        List<Method> matchTheAroundMethods = getMatchMethods(aroundExpressionUrlAndMethods, targetBean.getClass());
        List<Method> matchTheBeforeMethods = getMatchMethods(beforeExpressionUrlAndMethods, targetBean.getClass());
        List<Method> matchTheAfterMethods = getMatchMethods(afterExpressionUrlAndMethods, targetBean.getClass());
        List<Method> matchTheAfterReturningMethods = getMatchMethods(afterReturningExpressionUrlAndMethods, targetBean.getClass());
        List<Method> matchTheAfterThrowingMethods = getMatchMethods(afterThrowingExpressionUrlAndMethods, targetBean.getClass());
        try {
            if (!matchTheAroundMethods.isEmpty()) {
                // 执行Around注解的方法
                joinPoint = new MethodInvocationProceedingJoinPoint(adviceBean,
                        methodInvocation, matchTheBeforeMethods,matchTheAfterMethods);
                result = executeMatchMethods(matchTheAroundMethods, joinPoint);
            } else {
                joinPoint = new JoinPointImpl(adviceBean, targetBean,
                        methodInvocation.getArgs());
                executeMatchMethods(matchTheBeforeMethods, joinPoint);
                result = methodInvocation.proceed();
            }
            executeMatchMethods(matchTheAfterMethods, joinPoint);
            executeMatchMethods(matchTheAfterReturningMethods, joinPoint);
            return result;
        }catch (Throwable e){
            executeMatchMethods(matchTheAfterThrowingMethods, joinPoint,e);
            throw e;
        }
    }


    /**
     * 获取Bean匹配切点表达式的切面方法集合
     * @param expressionUrlAndMethod 切点表达式到切面方法的集合
     * @param beanClass beanClass
     * @return Bean匹配切点表达式的切面方法集合
     */
    private List<Method> getMatchMethods(HashMap<String, List<Method>> expressionUrlAndMethod, Class<?> beanClass) {
        List<Method> result = new ArrayList<>();
        expressionUrlAndMethod.keySet().stream()
                .filter(url -> PatternMatchUtils.isMatch(url, beanClass, pointcutExpressionUrlAndMethods))
                .map(expressionUrlAndMethod::get)
                .forEach(result::addAll);
        return result;
    }

    /**
     * 执行一组切面方法
     * @param methods 切面方法集合
     * @param ags 切面方法集合
     * @return 获取执行的第一个方法结果，若没有第一个方法结果则返回null
     */
    public Object executeMatchMethods(List<Method> methods, Object... ags) {
        Iterator<Object> iterator = methods.stream()
                .map(method -> ReflectionUtil.executeTargetMethod(adviceBean, method, ags))
                .collect(Collectors.toList())
                .iterator();
        return iterator.hasNext() ? iterator.next() : null;
    }
}
