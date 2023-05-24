package com.example.demo.suanfa;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @description:
 * @author: jjxu
 * @time: 2023/2/14
 * @package: com.example.demo.suanfa
 */
public class day3 {

    /**
     *给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
     * 输入: s = "abcabcbb"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     */
    public static int lengthOfLongestSubstring(String s){
        //存储非重复字符
        Set<Character> set = new HashSet<>();
        int addIndex =0;
        int removeIndex =0;
        //记录一次循环，获取最大的字节长度
        int max=0;
        //添加的长度小于字符长度-一直循环
        while(addIndex < s.length()){
            if(!set.contains(s.charAt(addIndex))){
                set.add(s.charAt(addIndex));
                addIndex++;
            }else{
                set.remove(s.charAt(removeIndex));
                removeIndex++;
            }
            //循环一次，看那个max最大
            max = Math.max(max,set.size());
        }
        return max;
    }

    public static void main(String[] args) {
        int acsxxasddd = lengthOfLongestSubstring("abcabcbb");
        System.out.println(acsxxasddd);
    }
}
