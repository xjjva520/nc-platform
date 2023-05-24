package com.example.demo.contro;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test {

    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        List<String> list = new LinkedList<>();
        list.add("dadasd");
        list.add("1111");
        for(String str:list){
            if(str.equals("1111")){
                list.remove(str);
            }
        }
    }


    public static void hw2() {
        Scanner in = new Scanner(System.in);
        //接收输入的整数
        int n = in.nextInt();
        int[] nums = new int[n];
        int size = 0;
        for (int i = 0; i < n; i++) {
            int num = (int) (Math.random() * 500 + 1);

            boolean findval = findval(nums, num, 0, size);
            if (!findval) {
                nums[size++] = num;
                sort1(nums, 0, size);
            }
            System.out.println(num);
        }
        System.out.println("------------");

        for (int i = 0; i < size; i++) {
            System.out.println(nums[i]);
        }
    }

    public static void sort1(int nums[], int left, int right) {
        int r = right;
        int l = left;
        int middleVal = nums[(left + right) / 2];
        while (l < r) {
            while (nums[l] < middleVal) {
                l++;
            }
            while (nums[r] > middleVal) {
                r--;
            }
            if (l >= r) {
                break;
            }
            int tmp = nums[r];
            nums[r] = nums[l];
            nums[l] = tmp;

            if (nums[l] == middleVal) {
                r--;
            }
            if (nums[r] == middleVal) {
                l++;
            }

        }
        if (l == r) {
            l++;
            r--;
        }
        if (l < right) {
            sort1(nums, l, right);
        }
        if (r > left) {
            sort1(nums, left, r);
        }
    }

    public static void sort2(byte nums[], int left, int right) {
        int r = right;
        int l = left;
        int middleVal = nums[(left + right) / 2];
        while (l < r) {
            while (nums[l] < middleVal) {
                l++;
            }
            while (nums[r] > middleVal) {
                r--;
            }
            if (l >= r) {
                break;
            }
            byte tmp = nums[r];
            nums[r] = nums[l];
            nums[l] = tmp;

            if (nums[l] == middleVal) {
                r--;
            }
            if (nums[r] == middleVal) {
                l++;
            }

        }
        if (l == r) {
            l++;
            r--;
        }
        if (l < right) {
            sort2(nums, l, right);
        }
        if (r > left) {
            sort2(nums, left, r);
        }
    }


    private static void addwater() {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNextInt()) { // 注意 while 处理多个 case
            int a = in.nextInt();
            int f = 0;
            while (a/3 >= 1) {
                f += a / 3;
                a = a / 3 + a % 3;
            }
            if (a % 3 == 2) {
                f++;
            }
            System.out.println(f);
        }
    }

    public static boolean findval(int[] nums, int matchVal, int left, int right) {
        if (left > right) {
            return false;
        }
        int mid = (left + right) / 2;
        if (matchVal > nums[mid]) {
            return findval(nums, matchVal, mid + 1, right);
        } else if (matchVal < nums[mid]) {
            return findval(nums, matchVal, left, mid - 1);
        } else {
            return true;
        }
    }

    public static int findval1(int[] nums, int matchVal, int left, int right) {
       int count =0 ;
        if (left > right) {
            return count;
        }
        int mid = (left + right) / 2;
        if (matchVal > nums[mid]) {
            return findval1(nums, matchVal, mid + 1, right);
        } else if (matchVal < nums[mid]) {
            return findval1(nums, matchVal, left, mid - 1);
        } else {
            count =1;
            int tmpIndex = mid -1;
            while(true){
                if(tmpIndex<left||nums[tmpIndex]!=matchVal){
                    break;
                }
                tmpIndex--;
                count++;
            }

            tmpIndex = mid+1;
            while(true){
                if(tmpIndex>right||nums[tmpIndex]!=matchVal){
                    break;
                }
                tmpIndex++;
                count++;
            }
            return count;
        }
    }

    public static int findval2(byte[] nums, int matchVal, int left, int right) {
        int count =0 ;
        if (left > right) {
            return count;
        }
        int mid = (left + right) / 2;
        if (matchVal > nums[mid]) {
            return findval2(nums, matchVal, mid + 1, right);
        } else if (matchVal < nums[mid]) {
            return findval2(nums, matchVal, left, mid - 1);
        } else {
            count =1;
            int tmpIndex = mid -1;
            while(true){
                if(tmpIndex<left||nums[tmpIndex]!=matchVal){
                    break;
                }
                tmpIndex--;
                count++;
            }

            tmpIndex = mid+1;
            while(true){
                if(tmpIndex>right||nums[tmpIndex]!=matchVal){
                    break;
                }
                tmpIndex++;
                count++;
            }
            return count;
        }
    }
}