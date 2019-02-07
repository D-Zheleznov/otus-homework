package ru.otus.l041.annotations;

import ru.otus.l041.annotations.annotations.After;
import ru.otus.l041.annotations.annotations.Before;
import ru.otus.l041.annotations.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class TestFramework {

    public static void runTests(String className) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Set<Method> beforeMethods = new HashSet<>();
        Set<Method> testMethods = new HashSet<>();
        Set<Method> afterMethods = new HashSet<>();

        testClassReflection(Class.forName(className), beforeMethods, testMethods, afterMethods);

        TestClass testObject;
        for (Method method : testMethods) {
            testObject = TestClass.class.getConstructor().newInstance();
            try {
                if (!beforeMethods.isEmpty())
                    beforeMethods.stream().findFirst().get().invoke(testObject);

                runTest(testObject, method);
            }
            catch (Exception e) {
                System.out.println("Тест: " + method.getName() + " завершился неудачей! Причина: " + e.getCause());
            }
            finally {
                if (!afterMethods.isEmpty())
                    try {
                        afterMethods.stream().findFirst().get().invoke(testObject);
                    } catch (Exception e) {
                        throw new RuntimeException("Ошибка в методе @After: " + e.getCause());
                    }
            }
        }
    }

    private static void testClassReflection(Class<?> classForTest, Set<Method> beforeMethods, Set<Method> testMethods, Set<Method> afterMethods) {
        for (Method method : classForTest.getMethods()) {
            Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
            for (Annotation annotation : declaredAnnotations) {
                if (annotation.annotationType().equals(Before.class)) {
                    beforeMethods.add(method);
                } else if (annotation.annotationType().equals(After.class)) {
                    afterMethods.add(method);
                } else if (annotation.annotationType().equals(Test.class))
                    testMethods.add(method);
            }
        }
        if (beforeMethods.size() > 1) throw new IllegalArgumentException("В тесте может быть только один метод помеченный аннотацией Before");
        if (testMethods.isEmpty()) throw new IllegalArgumentException("В тесте не найдено методов помеченных аннотацией Test");
        if (afterMethods.size() > 1) throw new IllegalArgumentException("В тесте может быть только один метод помеченный аннотацией After");
    }

    private static void runTest(TestClass testObject, Method method) throws InvocationTargetException, IllegalAccessException {
        method.invoke(testObject);
    }
}
