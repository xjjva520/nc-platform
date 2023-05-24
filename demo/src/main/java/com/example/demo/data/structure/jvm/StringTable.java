package com.example.demo.data.structure.jvm;

import java.text.ParseException;

/**
 * @description:
 * @author: jjxu
 * @time: 2022/12/29
 * @package: com.example.demo.data.structure.jvm
 */
public class StringTable {
    public static void main(String[] args) throws ParseException {
        String a = new String("1");
        a.intern();
        String b = "1";
        System.out.println(a==b);//jdk 1.6 false jdk7/8 false
        //原理？
        /* *
         * 在jdk 1.6的时候，String常量池是存放在永久代里面，  在new String的时候看字节码就知道，相当于创建了2个对象，1、将1放入的常量池中，2、创建一个String对象。
         * a 是在堆空间里面内存地址，b是在常量池里面的地址
         * 因此不管是7还是6都是false
         */

        String c = new String("1") + new String("1");
        /**
         *  1、创建StringBuilder对象
         *  2、new String对象
         *  3、将1放入常量池
         *  4 new String对象
         *  5、StringBuilder对象中toString() 实际也是new String对象 该地方并不会将11放入到常量池
         */
        c.intern();
        String bb = "11";
        System.out.println(c==bb);//jdk 1.6 false jdk7/8 true
        //原理？ 因为JDK1.6 是因为String常量池是存放在永久代里面，因此还是false
        /**
         *   在第一步创建的c的时候，因为是通过StringBuild创建的，并不会将11放入到常量池，也就是不会在常量池开辟空间地址，而c.intern（）方法在1.7,1.8中，String常量池放入到了堆中
         *   为了避免重复开辟内存，因此存放的c的地址值，因此在1.7之后是true,在1.6中，不管咋样都是开辟了两个内存空间
         */

    }
}
