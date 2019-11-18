package ru.otus.l19.thread;

public class ThreadClass extends Thread {

    private static final Object object = new Object();
    private int counter = 0;
    private boolean reverse;

    @Override
    public void run() {
        startCounting();
    }

    private void startCounting() {
        while (true) {
            synchronized (object) {
                try {
                    if (counter > 9)
                        reverse = true;
                    else if (counter == 1 && reverse)
                        reverse = false;
                    System.out.print(Thread.currentThread().getName() + (reverse ? --counter : ++counter));
                    Thread.sleep(300);
                    object.notify();
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}