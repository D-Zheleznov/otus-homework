package demo;

import annotation.Log;

public class TestLoggingImpl implements TestLogging {

    @Log
    @Override
    public void test1(String param) {

    }

    @Override
    public void test2(String param) {

    }

    @Log
    @Override
    public void test3(String param1, Integer param2) {

    }
}
