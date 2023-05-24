package com.example.demo.data.structure.basicsf;

import java.util.Arrays;

/**
 * @description: 计算满足8皇后游戏代码---利用递归回溯算法
 * @author: jjxu
 * @time: 2022/9/9
 * @package: com.example.demo.data.structure.basicsf
 *
 * 规则：在8X8格上的国际象棋上放置8个皇后，即 ：任意两个皇后都不能处于同一行，同一列或者同一斜线上，有多少种摆法？
 *
 */
public class Queen8 {

    //保存符合规则的位置
    int[] queen = new int[8];
    public static int count = 0;


    public void check(int n){
        if( n == queen.length){
            print();
            return;
        }
       for(int i =0;i<queen.length;i++){
           //假设第n个皇后的位置为i
           queen[n] = i;
           //如果当前值不行
           if(checkIsOk(n)){
               check(n+1);
           }
       }
    }


    /* *
     *  n代表现在的皇后，j 代表当前皇后之前皇后
     **/
    public boolean checkIsOk(int n){
        //庞大当前皇后之前节点不在同一行，同一列，和对角线
        for(int j = 0;j < n;j++){
                                        //对角线判断
            if(queen[j] == queen [n] || Math.abs(j-n) == Math.abs(queen[n]-queen[j])){
                return false;
            }
        }
        return true;
    }

    public void print(){
        count++;
        for (int i =0;i < queen.length;i++){
            System.out.print(queen[i] +" ");
        }
        System.out.println(" ");
    }
}
