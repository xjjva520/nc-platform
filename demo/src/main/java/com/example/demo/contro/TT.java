package com.example.demo.contro;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: jjxu
 * @time: 2023/2/11
 * @package: com.example.demo.contro
 */
public class TT {


    public static void main(String[] args) {
       String abc = "abc";
       String bbc = "abc";
        System.out.println(abc == bbc);
    }

    public static String fillCups(int[] amount,int target) {
        String b = null;
        Map<Integer,Integer> map = new HashMap<>();
        for(int i = 0;i<amount.length;i++){
            map.put(amount[i],i);
        }

        for(int i = 0;i<amount.length;i++){
            if(map.containsKey(target-amount[i])){
                b=b+"  "+i;
            }
        }
        return b;
    }

    public static int[] twoSum(int[] nums, int target) {
        int[] bb= new int[2];
        Map<Integer,Integer> map = new HashMap<>();
        for(int i = 0;i<nums.length;i++){
            if(map.containsKey(nums[i])){
                return new int[]{map.get(target-nums[i]),i};
            }
            map.put(nums[i],i);
        }
        return bb;
    }
}
