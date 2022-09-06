package com.example.demo.data.structure.array;

import com.example.demo.DemoApplication;
import org.springframework.boot.SpringApplication;

/**
 * @description: 稀疏数组--五子棋
 * @author: jjxu
 * @time: 2022/9/2
 * @package: com.example.demo.data.structure.array
 */
public class SparseArray {

    //数组转换稀疏数组
    /* *
     * 稀疏数组：将初始的值进行压缩表示
     * 第一行的含义代表的是 整个二位数组 有多少行，有多少列，有效数据的值的个数
     * 其它行的含义代表的是 第几行 第几列的那个数值是几：如 第5行 第4列 的那个位置的值是8
     *     row colm val
     *   0  11  12  6
     *   1  5   4   8
     **/
    public static int[][] arrayCovertSparseArray(int[][] array){
        //计算有多少个有效数值
        int sum = 0;
        for(int [] arr:array){
            for(int item:arr){
                if(item!=0){
                    sum++;
                }
            }
        }
        //稀疏数组的初始化 有效数字+1 为行数，固定为3列
        int[][] sparseArray = new int[sum+1][3];
        //初始化稀疏数组的第一行 多少行，多少列，有效数字
        sparseArray[0][0] = array.length;
        sparseArray[0][1] = array[0].length;
        sparseArray[0][2] = sum;

        int count = 1;
        for(int i =1 ;i< array.length;i++){
            for(int j =0;j< array[i].length;j++){
                if(array[i][j]>0){
                    sparseArray[count][0] = i;
                    sparseArray[count][1] = j;
                    sparseArray[count][2] = array[i][j];
                    count++;
                }
            }
        }
        return sparseArray;
    }


   /* *
    * 稀疏数组进行恢复成正常二位数组
    **/
    public static int[][] sparseArrayCovertArray(int[][] sparesArray){
        int[][] array = new int[sparesArray[0][0]][sparesArray[0][1]];
        for(int i =1;i<sparesArray.length;i++){
            array[sparesArray[i][0]][sparesArray[i][1]] = sparesArray[i][2];
        }
        return array;
    }
}
