package com.example.demo.contro;

import java.math.BigDecimal;

/**
 * @description:
 * @author: jjxu
 * @time: 2023/2/12
 * @package: com.example.demo.contro
 */
public class ListNode {

    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    public static void build(ListNode l1, int val1) {
        ListNode curNode = l1;
        while(curNode!=null){
            if(curNode.next == null){
                ListNode nextNode = new ListNode(val1);
                curNode.next = nextNode;
                break;
            }else{
                curNode = curNode.next;
            }
        }
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode reserver1 = l1;
        ListNode reserver2 = l2;

        ListNode curNode = new ListNode(-1);
        while (reserver1 !=null || reserver2!=null){
            int val1 = 0;
            int val2 = 0;
            if(reserver1 !=null && reserver2 !=null){
               val1 = reserver1.val;
               val2 = reserver2.val;
            }else if(reserver1 ==null && reserver2 !=null){
              val2 = reserver2.val;
            }else{
               val1 = reserver1.val;
            }
            int sum = val1 + val2;
            if(sum>9){
                build(curNode,sum%10);
                if(reserver1.next==null){
                    reserver1.next = new ListNode(1);
                }else{
                    reserver1.next.val = reserver1.next.val+1;
                }
            }else{
                build(curNode,sum);
            }
            reserver1 = reserver1 == null?null:reserver1.next;
            reserver2 = reserver2 == null?null:reserver2.next;
        }
        return curNode.next;
    }

    public static ListNode reserver(ListNode listNode) {
        ListNode curHead = new ListNode(-1);
        curHead.next = listNode;

        ListNode reserveNode = new ListNode(-1);

        ListNode curNode = curHead.next;
        ListNode nextNode;
        while(true){
            nextNode = curNode.next;

            curNode.next = reserveNode.next;

            reserveNode.next = curNode;

            curNode = nextNode;

            if(curNode == null){
                break;
            }
        }
        curHead.next = reserveNode.next;
        return curHead.next;
    }

    public static String toShow(ListNode listNode) {
       StringBuilder builder = new StringBuilder();
       while (listNode!=null){
           builder.append(listNode.val);
           listNode = listNode.next;
       }
       return builder.toString();
    }

    public static ListNode reserver1(ListNode listNode){
       ListNode head = new ListNode(-1);
       ListNode cur = listNode;
       ListNode next;
       while(true){
           next = cur.next;
           cur.next = head.next;
           head.next = cur;
           cur = next;
           if(cur ==null){
               break;
           }
       }
       return head.next;
    }


    public static void main(String[] args) {

        ListNode listNode5 = new ListNode(8);
        ListNode listNode4 = new ListNode(6,listNode5);
        ListNode listNode3 = new ListNode(5,listNode4);
        ListNode listNode2 = new ListNode(3,listNode3);
        ListNode listNode1 = new ListNode(4,listNode2);
        ListNode listNode = new ListNode(2,listNode1);




        String s2 = toShow(listNode);
        ListNode listNode7 = reserver1(listNode);
        String s1 = toShow(listNode7);

        System.out.println(s2);
        System.out.println(s1);

       /* ListNode listNode6 = addTwoNumbers(listNode, listNode3);

        String s = toShow(listNode6);
        System.out.println(s);*/
    }
}
