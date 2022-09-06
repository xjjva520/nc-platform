package com.example.demo.data.structure.array;

/**
 * @description: 通过数组模拟--栈数据结构
 * @author: jjxu
 * @time: 2022/9/6
 * @package: com.example.demo.data.structure.array
 */
public class StackArray {

    //栈顶位置
    private int top;

    //存放数据
    private int[] data;

    public StackArray(int size) {
        this.top = -1;
        this.data = new int[size];
    }

    public void add(int val){
        if(top == data.length - 1){
            throw new RuntimeException("stack is full");
        }
        top++;
        data[top] = val;
    }

    public int get(){
        if(top == -1){
            throw new RuntimeException("stack is empty");
        }
        int datum = data[top];
        data[top] = 0;
        top -- ;

        return  datum;
    }

    public int size(){
        return top;
    }

    public boolean isEmpty(){
        return top == -1;
    }

    public int peek(){
        if(top == -1){
            throw new RuntimeException("stack is empty");
        }
        return data[top];
    }

    public void showArray(){
        for(int i=0;i<top+1;i++){
            System.out.println(data[i]);
        }
    }
}
