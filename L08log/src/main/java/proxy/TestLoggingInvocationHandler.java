package proxy;

import demo.TestLogging;
import demo.TestLoggingImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class TestLoggingInvocationHandler implements InvocationHandler {

    private TestLogging testLogging = new TestLoggingImpl();
    private String[] annotatedMethods;

    public TestLoggingInvocationHandler() {
        annotatedMethods = Arrays.stream(TestLoggingImpl.class.getMethods())
                                 .filter(method -> Arrays.stream(method.getDeclaredAnnotations()).anyMatch(annotation -> annotation.annotationType().getSimpleName().equals("Log")))
                                 .map(Method::getName).toArray(String[]::new);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Arrays.stream(annotatedMethods).noneMatch(method.getName()::equals))
            return method.invoke(testLogging, args);

        StringBuilder params = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            if (i > 0)
                params.append("; ");

            params.append(args[i]);
        }
        System.out.println("executed method: " + method.getName() + ", params: " + params);
        return method.invoke(testLogging, args);
    }
}