package com.example.demo.data.structure.array;

/**
 * @description: 数组环形队列
 * @author: jjxu
 * @time: 2022/9/4
 * @package: com.example.demo.data.structure.array
 */
public class CycleArrayQueue<T> {

    //数组存储
    private Object[] o ;

    //指向最后一个元素的后面那个位置
    private int tail;

    //指向队列第一个元素
    private int prev ;

    public CycleArrayQueue(int size) {
        this.o = new Object[size];
    }

    public void add(T t){
       /* if((tail +1 % o.length) == prev){
            System.out.println("queue is limit =" + o.length);
            return;
        }*/

        if((tail - prev) == o.length){
            System.out.println("queue is limit =" + o.length);
            return;
        }


        //往后移动一位(环形写法)
        //o[tail] = t;
        //tail = tail+1 % o.length;

        //tail数字一直往上面增
        o[tail % o.length] = t;
        tail++ ;
    }

    public T get(){
        if(tail == prev){
            System.out.println("queue is empty");
        }

        //T t= (T) this.o[prev];
        //prev往后移动一位,取一个值，则prev往后移动
        //取模是为了不能超过 相当于在 0~o.length之间循环递增
        //prev = prev+1 % o.length;

        //prev数字一直往上面增
        T t= (T) this.o[prev % o.length];
        prev++;
        return t;
    }

    public void show(){
        if(tail == prev){
            System.out.println("queue is empty");
        }
        for(int i= 0;i<(tail - prev);i++){
            System.out.println(o[i]);
        }
    }
}
