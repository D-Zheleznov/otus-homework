package ru.otus.l041.annotations;

import ru.otus.l041.annotations.annotations.After;
import ru.otus.l041.annotations.annotations.Before;
import ru.otus.l041.annotations.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> classString = Class.forName("ru.otus.l041.annotations.TestClass");
        int beforeAnnotationsCounter = 0;
        int afterAnnotationsCounter = 0;
        Method beforeMethod = null;
        Method afterMethod = null;
        Set<Method> testMethods = new HashSet<>();

        for (Method method : classString.getMethods()) {
            Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
            for (Annotation annotation : declaredAnnotations) {
                if (annotation.annotationType().equals(Before.class)) {
                    beforeAnnotationsCounter++;
                    beforeMethod = method;
                } else if (annotation.annotationType().equals(After.class)) {
                    afterAnnotationsCounter++;
                    afterMethod = method;
                } else if (annotation.annotationType().equals(Test.class))
                    testMethods.add(method);
            }
        }
        if (beforeAnnotationsCounter > 1) throw new IllegalArgumentException("В тесте может быть только один метод помеченный аннотацией Before");
        if (afterAnnotationsCounter > 1) throw new IllegalArgumentException("В тесте может быть только один метод помеченный аннотацией After");
        if (testMethods.isEmpty()) throw new IllegalArgumentException("В тесте не найдено методов помеченных аннотацией Test");

        Class<TestClass> clazz = TestClass.class;
        TestClass object;
        for (Method method : testMethods) {
            object = clazz.getConstructor().newInstance();
            if (beforeMethod != null) beforeMethod.invoke(object);
            method.invoke(object);
            if (afterMethod != null) afterMethod.invoke(object);
        }
    }
}
