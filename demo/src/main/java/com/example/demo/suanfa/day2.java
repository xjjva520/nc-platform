package com.example.demo.suanfa;

import java.util.Arrays;


public class day2 {

    /**
     * 输入文件最多包含 10 组测试数据，每个数据占一行，仅包含一个正整数 n（ 1<=n<=100 ），
     * 表示小张手上的空汽水瓶数。n=0 表示输入结束，你的程序不应当处理这一行。
     */
    public static int changeWater(int n){
        int sum = 0;
        while (n>=3){
           //除以3代表本次可以喝多少瓶
           sum = sum + n / 3 ;
           //代表循环一次还能剩下多少个空瓶子
            n =  n / 3 + n % 3;
        }
        if(n == 2){
            sum = sum+1;
        }

        return sum;
    }

    /**
     *明明生成了
     * N个1到500之间的随机整数。请你删去其中重复的数字，即相同的数字只保留一个，把其余相同的数去掉，
     * 然后再把这些数从小到大排序，按照排好的顺序输出。
     */
    public static int[] randomData(int n){
        int [] arr = new int[n];
        int count =1 ;
        for(int i=0;i<n;i++){
            int random = (int) (Math.random() * 500 + 1);
            if(i==0){
                arr[0] = random;
            }else{
                quickSort(arr,0,count-1);
                boolean val = findVal(arr, 0, count-1, random);
                if(!val){
                    count++;
                    arr[count-1] = random;
                }
            }

        }
         quickSort(arr,0,count-1);
         return arr;
    }

    public static boolean findVal(int []nums,int left,int right,int targetVal){
        if(left>right){
            return false;
        }
        int r = right;
        int l = left;
        int mid = (r+l)/2;
        if(targetVal>nums[mid]){
            return findVal(nums,mid+1,r,targetVal);
        }else if (targetVal < nums[mid]){
            return findVal(nums,l,mid-1,targetVal);
        }else{
            return true;
        }
    }

    public static void quickSort(int[]nums,int left,int right){
       int r = right;
       int l = left;
       int midVal = nums[(left+right)/2];
       while(l<r){
           //左边找到一个比中间值大的
           while(nums[l]<midVal){
               l++;
           }
           //右边找到一个比中间值小的
           while(nums[r]>midVal){
               r--;
           }
           //都找到中间位置了，也就没必要找了
           if(l>=r){
               break;
           }
           //进行交换
           int tmp = nums[r];
           nums[r] = nums[l];
           nums[l] = tmp;

           //交换之后说明和中间值一样，l没进行位置移动
           if(nums[r] == midVal){
               l++;
           }
           //交换之后说明跟
           if(nums[l] == midVal){
               r--;
           }
       }
       //进行移动
       if(l == r){
           l++;
           r--;
       }
       if(l<right){
           quickSort(nums,l,right);
       }
       if(left<r){
           quickSort(nums,left,r);
       }
    }

    public static String findVal1(int []nums,int left,int right,int targetVal){
        if (left > right) {
            return "";
        }
        int mid = (left+right)/2;
        if(targetVal>nums[mid]){
            return findVal1(nums,mid+1,right,targetVal);
        }else if (targetVal< nums[mid]){
            return findVal1(nums,left,mid-1,targetVal);
        }else{
           StringBuffer buffer = new StringBuffer(mid+",");
           int r = mid+1;
           int l = mid-1;
           while(r< nums.length-1 && nums[r]==targetVal){
               buffer.append(r+",");
               r++;
           }
            while(l >=0 && nums[l]==targetVal){
                buffer.append(l+",");
                l--;
            }
            return buffer.toString();
        }
    }



    public static void main(String[] args) {
        int[] ints = randomData(10);
        System.out.println(Arrays.toString(ints));
    }
}
