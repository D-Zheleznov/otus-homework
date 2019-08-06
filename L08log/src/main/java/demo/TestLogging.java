package demo;

import annotation.Log;

public interface TestLogging {

    @Log
    void test1(String param);

    void test2(String param);

    @Log
    void test3(String param1, Integer param2);
}
