package com.example.demo.data.structure.array;

/**
 * @description: 环形链表---约瑟夫问题
 * @author: jjxu
 * @time: 2022/9/6
 * @package: com.example.demo.data.structure.array
 */
public class CycleLinkList<T> {

    //环形链表第一个节点
    private Node<T> first;

    //该环由几个元素构成
    private int size;


   public void add(T item){
       //如果first节点为空，说明需要初始化
       if(first == null){
           //此处先与自己构成环行
           first = new Node(null,item);
           first.next = first;
           size++;
           return;
       }
       Node<T> curNode = first;
       while(true){
           if(curNode.next == first){
               curNode.next = new Node(first,item);
               size++;
               break;
           }
           curNode = curNode.next;
       }
   }

   /* *
    * @Param [k, num]
    * first节点数第k个为第一个节点,每次数num下 出对
    **/
    public void popCycleLink(int k,int num){
        if(k>size||k<0 || num>size || num<0){
            throw new RuntimeException("params is error");
        }
        Node<T> one =first;
        //找到第一个节点
        for(int i=0;i<k-1;i++){
           one = one.next;
        }
        //找到第一个节点的后一个节点
        Node<T> helper = one;
        Node<T> popNode;
        for(int i=0;i<size-1;i++){
            helper = helper.next;
        }
        while(true){
            //进行循环数数
            for(int i =0;i<num-1;i++){
                one = one.next;
                helper = helper.next;
            }
            //记住需要移除的节点
            popNode = one;
            System.out.println(popNode.item);
            one = one.next;
            helper.next = one;
            //next 指向null help GC
            popNode.next = null;
            //如果自己指向自己，则代表全部完成
            if(helper.next == helper){
                System.out.println(one.item);
                break;
            }
            size--;
        }
    }

    public void showList(){
        //如果first节点为空，说明需要初始化
        if(first == null){
            throw new RuntimeException("cycle link list is empty");
        }
        Node<T> curNode = first;
        while (true){
            System.out.println(curNode.item);
            if(curNode.next == first){
                break;
            }
            curNode = curNode.next;
        }
    }

    private class Node<T>{

        //存储的对象
        T item;

        //指向下一个节点
        Node<T> next;


        public Node(Node<T> next,T item) {
            this.item = item;
            this.next = next;
        }
    }
}
