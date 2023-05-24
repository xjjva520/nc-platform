package com.example.demo.data.structure.jvm;

import com.example.demo.data.structure.entity.People;
import com.example.demo.data.structure.entity.WebPage;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description:
 * @author: jjxu
 * @time: 2023/1/30
 * @package: com.example.demo.data.structure.jvm
 */
public class HeapOutMemery {

    public static void heapError() throws InterruptedException {
        People p1 = new People("检检1");
        People p2 = new People("检检2");
        People p3 = new People("检检3");
        for (int i = 0; i < 100000; i++) {
            WebPage pg = new WebPage(i,"http://www."+Integer.toString(i)+".com");
            if(i%3==0){
                p1.visit(pg);
            }
            if(i%5==0){
                p2.visit(pg);
            }
            if(i%7==0){
                p3.visit(pg);
            }
        }
        System.gc();
    }
}
