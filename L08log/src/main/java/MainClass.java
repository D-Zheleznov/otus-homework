import demo.TestLogging;
import proxy.TestLoggingInvocationHandler;

import java.lang.reflect.Proxy;

public class MainClass {

    public static void main(String[] args) {
        TestLogging testLogging = (TestLogging) Proxy.newProxyInstance(TestLogging.class.getClassLoader(), new Class[]{TestLogging.class}, new TestLoggingInvocationHandler());
        testLogging.test1("Hello Otus!");
        testLogging.test2("...");
        testLogging.test3("2019-06", 8);
    }
}
