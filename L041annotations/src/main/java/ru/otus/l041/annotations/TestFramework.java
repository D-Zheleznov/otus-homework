package ru.otus.l041.annotations;

import ru.otus.l041.annotations.annotations.After;
import ru.otus.l041.annotations.annotations.Before;
import ru.otus.l041.annotations.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class TestFramework {

    private static Method beforeMethod = null;
    private static int beforeAnnotationsCounter = 0;
    private static Method afterMethod = null;
    private static int afterAnnotationsCounter = 0;
    private static final Set<Method> testMethods = new HashSet<>();

    public static void runTests(String className) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        testClassReflection(Class.forName(className));

        if (beforeAnnotationsCounter > 1) throw new IllegalArgumentException("В тесте может быть только один метод помеченный аннотацией Before");
        if (afterAnnotationsCounter > 1) throw new IllegalArgumentException("В тесте может быть только один метод помеченный аннотацией After");
        if (testMethods.isEmpty()) throw new IllegalArgumentException("В тесте не найдено методов помеченных аннотацией Test");

        TestClass testObject;
        for (Method method : testMethods) {
            testObject = TestClass.class.getConstructor().newInstance();
            try {
                if (beforeMethod != null) beforeMethod.invoke(testObject);
                runTest(testObject, method);
            }
            catch (Exception e) {
                System.out.println("Тест: " + method.getName() + " завершился неудачей! Причина: " + e.getCause());
            }
            finally {
                if (afterMethod != null)
                    try {
                        afterMethod.invoke(testObject);
                    } catch (Exception e) {
                        throw new RuntimeException("Ошибка в методе @After: " + e.getCause());
                    }
            }
        }
    }

    private static void testClassReflection(Class<?> classForTest) {
        for (Method method : classForTest.getMethods()) {
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
    }

    private static void runTest(TestClass testObject, Method method) throws InvocationTargetException, IllegalAccessException {
        method.invoke(testObject);
    }
}
