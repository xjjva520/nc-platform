package com.example.demo.suanfa;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: jjxu
 * @time: 2023/2/13
 * @package: com.example.demo.suanfa.day1
 */
public class day1 {
   /*
    给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。
    你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
    你可以按任意顺序返回答案。
    输入：nums = [2,7,11,15], target = 9
    输出：[0,1]
    解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1]
   */
    public static int[] twoNum(int[]nums,int target){
        Map<Integer,Integer> map = new HashMap<>();
        for(int i=0;i<nums.length;i++){
            if(map.containsKey(nums[i])){
               return new int[]{map.get(nums[i]),i} ;
            }
            //将差值为key 存入到hashMap，还有当前下标
            map.put(target-nums[i],i);
        }
        return null;
    }

    public static void main(String[] args) {
        int []nums = {2,2,7,15};
        int[] ints = twoNum(nums, 9);
        String string = Arrays.toString(ints);
        System.out.println(string);
    }
}
