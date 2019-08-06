package proxy;

import annotation.Log;
import demo.TestLogging;
import demo.TestLoggingImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class TestLoggingInvocationHandler implements InvocationHandler {

    private TestLogging testLogging = new TestLoggingImpl();

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (!method.isAnnotationPresent(Log.class))
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