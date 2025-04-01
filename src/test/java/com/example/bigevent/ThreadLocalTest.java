package com.example.bigevent;

import org.junit.jupiter.api.Test;

public class ThreadLocalTest {
    @Test
    public void testThreadLocalSetAndGet(){

        ThreadLocal tl=new ThreadLocal();
       //"开启两个线程"
       new Thread(()->{
           tl.set("王子安");
           System.out.println(Thread.currentThread().getName()+ "： "+tl.get());
           System.out.println(Thread.currentThread().getName()+ "： "+tl.get());
           System.out.println(Thread.currentThread().getName()+ "： "+tl.get());
       },"计算机").start();

        new Thread(()->{
            tl.set("王子安2");
            System.out.println(Thread.currentThread().getName()+ "： "+tl.get());
            System.out.println(Thread.currentThread().getName()+ "： "+tl.get());
            System.out.println(Thread.currentThread().getName()+ "： "+tl.get());
        },"计算机2").start();

    }
}
