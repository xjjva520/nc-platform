package com.example.demo.data.structure.basicsf;

/**
 * @description:
 * @author: jjxu
 * @time: 2022/9/17
 * @package: com.example.demo.data.structure.basicsf
 */
public class Search {

    public int findByErFenFa(int arr[],int findVal,int left,int right){
        if(left>right){
            return -1;
        }
        int mid = (left + right) / 2;
        if(findVal>arr[mid]){
             return findByErFenFa(arr,findVal,mid+1,right);
        }else if(findVal < arr[mid]){
             return findByErFenFa(arr,findVal,left,mid-1);
        }else{
            return mid;
        }
    }
}
