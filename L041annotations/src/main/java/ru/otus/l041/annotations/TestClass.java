package ru.otus.l041.annotations;

import ru.otus.l041.annotations.annotations.After;
import ru.otus.l041.annotations.annotations.Before;
import ru.otus.l041.annotations.annotations.Test;

public class TestClass {

    @Before
    public void before() {
        System.out.println("Выполнен метод Before");
    }

    @Test
    public void firstTest() {
        System.out.println("Выполнен тест № 1");
    }

    @Test
    public void secondTest() {
        System.out.println("Выполнен тест № 2");
    }

    @Test
    public void thirdTest() {
        System.out.println("Выполнен тест № 3");
    }

    @After
    public void after() {
        System.out.println("Выполнен метод After");
    }
}
