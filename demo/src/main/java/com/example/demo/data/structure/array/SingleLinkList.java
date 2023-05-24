package com.example.demo.data.structure.array;

/**
 * @description: 自定义单链表
 * @author: jjxu
 * @time: 2022/9/3
 * @package: com.example.demo.data.structure.array
 */
public class SingleLinkList<T extends Comparable<T>> {

    //头部节点
    private Node<T> head;

    //记录有效值
    private int size;


    public void add(T item){
        if(item == null){
          throw new NullPointerException("item is null");
        }
       if(head == null || head.next == null){
           head = new Node<T>(null,null);
           head.next = new Node(null,item);
           size++;
           return;
       }
       Node<T> tmp = head.next;
       while(true){
         if(tmp.item.equals(item)){
             System.out.println("存在重复节点---hashcode = "+item.hashCode());
             return;
         }
         //说明当前节点的next节点指向为null
         if(tmp.next == null){
            tmp.next = new Node(null,item);
            size++;
            return;
         }
         //处理下一个节点
         tmp = tmp.next;
       }
    }

    public void addBySort(T item){
        if(item == null){
            throw new NullPointerException("item is null");
        }
        if(head == null || head.next == null){
            head = new Node<T>(null,null);
            head.next = new Node(null,item);
            size++;
            return;
        }
        Node<T> cur = head;
        boolean flag = false;
        while(true){
            Node<T> next = cur.next;
            if (next == null) {
              break;
            }
            if(next.item.compareTo(item)>0){
               flag = true;
               break;
            }
            //当前节点指向下个节点
            cur = next;
        }
        if(flag){
            cur.next = new Node<>(cur.next,item);
        }else{
            cur.next = new Node<>(null,item);
        }
        size++;
    }


    public boolean remove(T t){
        if(t ==null || size == 0){
            throw new NullPointerException("val is null or size = "+ size);
        }
        Node<T> cur = head;
        boolean flag = false;
        while(true){
            if(cur.next == null){
                break;
            }
            if(cur.next.item.equals(t)){
                flag = true;
                break;
            }
            cur = cur.next;
        }
        if(flag){
            //因为上面分析的是当前节点的下一个节点去比对移除的节点，那么也就是当前节点的下个节点直接指向
            //当前节点的下下个节点即可
            Node next = cur.next;
            cur.next = cur.next.next;
            //把移除节点的next指针排除
            next.next=null;
            size -- ;
        }
        return flag;
    }

    public boolean remove(int index){
        if(size ==0?index < 0:index>size) {
            throw new ArrayIndexOutOfBoundsException("size = "+size+" index = "+ index);
        }
        Node cur = head;
        //找到对应有效值的第几个即可。就是循环index次的当前节点的next节点
        for(int i =1;i<index;i++){
            cur = cur.next;
        }
        cur.next = cur.next.next;
        size --;
       return true;
    }

    //进行单链表指向反转及，最后一个
    //head.next ->node1->node2->node3  -> head.next->node3->node2->node1
    public void reverseSingleLink(){
        if(size == 0 || size ==1){
            return;
        }
        //定义一个反转的头部
        Node reverseHead = new Node<T>(null,null);
        Node cur = head.next;
        Node next;
        while(true){
            //记住当前节点的前一个节点
            next = cur.next;

            //当前节点指向反转节点的下个节点--此处就可以理解为头部插入法
            //首次进来reverseHead.next 为null 及断开了node1节点的next指向null
            //第二次进来就是把第二节点的next --->
            // node1节点。因为第一次已经把node1节点赋值给reverseHead.next节点了

            cur.next = reverseHead.next;

            //将反转的头部节点指向当前节点
            reverseHead.next = cur;
            cur = next;

            if(cur == null){
                break;
            }
        }
        head.next = reverseHead.next;
    }


    public void combineAndSortSingleLink(SingleLinkList<T> list){
        if(list == null||list.size == 0){
            throw new ArrayIndexOutOfBoundsException("非法合并");
        }
        Node<T> node = list.head;
        while(node.next!=null){
            addBySort(node.next.item);
            node = node.next;
        }
    }

    public void showList(){
        if(head == null || head.next == null){
            throw new RuntimeException("cur single link list is null");
        }
        Node<T> firstNode = head.next;
        while(firstNode!=null){
            System.out.println(firstNode.item);
            firstNode =  firstNode.next;
        }
    }


    public void r(){
        if(head==null ||size==0){
            throw new RuntimeException("非法");
        }
        Node rvHead = new<T> Node(null,null);
        Node cur = head.next;
        Node next;
        //进行反转
        while(true){
            //先记录下一个节点，下一个节点在下次循环处理的时候，要变成当前节点
            next = cur.next;
            //当前节点的next节点指向反转头部的下一个节点(如果是node1节点，因为要反转变成最后一个节点，也就是next 指向为null,而首次rvHead.next恰好为空)
            //第二次进来，rvHead。next,当前节点的前一个节点，也就完成了反转就变成了node2->node1
            cur.next = rvHead.next;
            //将当前节点赋值给新头部的next节点
            rvHead.next = cur;

            //当前节点后移以为
            cur = next;

            if(cur == null){
                break;
            }
        }
        head.next = rvHead.next;
    }

    public int getSize() {
        return size;
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
