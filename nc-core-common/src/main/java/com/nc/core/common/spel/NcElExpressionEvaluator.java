package com.nc.core.common.spel;

import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.context.expression.CachedExpressionEvaluator;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: 定义表达式解析
 * @author: jjxu
 * @time: 2022/5/24
 * @package: com.nc.core.common.spel
 */
public class NcElExpressionEvaluator extends CachedExpressionEvaluator {


    private final Map<ExpressionKey, Expression> expressionCache = new ConcurrentHashMap(64);
    private final Map<AnnotatedElementKey, Method> methodCache = new ConcurrentHashMap(64);

    public EvaluationContext createContext(Method method, Object[] args, Object target, Class<?> targetClass, @Nullable BeanFactory beanFactory) {
        Method targetMethod = this.getTargetMethod(targetClass, method);
        NcExpressionRootObject rootObject = new NcExpressionRootObject(method, args, target, targetClass, targetMethod);
        MethodBasedEvaluationContext evaluationContext = new MethodBasedEvaluationContext(rootObject, targetMethod, args, this.getParameterNameDiscoverer());
        if (beanFactory != null) {
            evaluationContext.setBeanResolver(new BeanFactoryResolver(beanFactory));
        }
        return evaluationContext;
    }

    private Method getTargetMethod(Class<?> targetClass, Method method) {
        AnnotatedElementKey methodKey = new AnnotatedElementKey(method, targetClass);
        return this.methodCache.computeIfAbsent(methodKey, (key) -> AopUtils.getMostSpecificMethod(method, targetClass));
    }

    public String evalAsText(String expression, AnnotatedElementKey methodKey, EvaluationContext evalContext) {
        return this.eval(expression, methodKey, evalContext, String.class);
    }

    @Nullable
    public <T> T eval(String expression, AnnotatedElementKey methodKey, EvaluationContext evalContext, @Nullable Class<T> valueType) {
        return this.getExpression(this.expressionCache, methodKey, expression).getValue(evalContext, valueType);
    }
}
