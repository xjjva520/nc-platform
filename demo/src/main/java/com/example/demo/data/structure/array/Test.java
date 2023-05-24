package com.example.demo.data.structure.array;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.nlp.v20190408.NlpClient;
import com.tencentcloudapi.nlp.v20190408.models.TextSimilarityProRequest;
import com.tencentcloudapi.nlp.v20190408.models.TextSimilarityProResponse;

import java.util.Arrays;

/**
 * @description:
 * @author: jjxu
 * @time: 2022/9/3
 * @package: com.example.demo.data.structure.array
 */
public class Test {

    private static int h = 0;

    public static void main(String[] args) throws InterruptedException {
         while(true){

         }
    }

    //6,1,0,4
    //1,0,4,6
    //0,1,4,6
    private static void sort1(int [] arr){
        for(int i=0;i<arr.length-1;i++){
            boolean changeFlag = false;
            for(int j=0;j<arr.length-1-i;j++){
                if(arr[j]>arr[j+1]){
                    int tmp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = tmp;
                    changeFlag = true;
                }
            }
            if(!changeFlag){
                break;
            }
            System.out.println("第"+i+"次："+ Arrays.toString(arr));
        }
    }
    //6,1,0,4
    //0,1,6,4
    //0,1,6,4
    //0,1,4,6
    private static void sort2(int [] arr){
         for(int i=0;i<arr.length-1;i++){
             int minIndex = i;
             int minVal = arr[i];
             for(int j =i+1;j<arr.length;j++){
                 if(arr[j]<minVal){
                     minIndex = j;
                     minVal = arr[j];
                 }
             }
             arr[minIndex] = arr[i];
             arr[i]=minVal;
             System.out.println("第"+i+"次："+ Arrays.toString(arr));
         }
    }

    private static void sort3(int [] arr){
       for(int i=1;i<arr.length;i++){
           int insertVal = arr[i];
           int insertIndex = i;
           while(insertIndex>0&&arr[insertIndex-1]<insertVal){
               arr[insertIndex] = arr[insertIndex-1];
               insertIndex--;
           }
           arr[insertIndex] = insertVal;
       }
    }


    private static void sort4(int [] arr){
        for(int gap = arr.length/2;gap>0;gap/=2){
            for(int i=gap;i<arr.length;i++){
                for(int j=i-gap;j>=0;j-=gap){
                    if(arr[j]>arr[j+gap]){
                        int tmp = arr[j];
                        arr[j] = arr[j+gap];
                        arr[j+gap] = tmp;
                    }
                }
            }
        }
    }


    private static void sort5(int [] arr){
        for(int gap=arr.length/2;gap>0;gap/=2){
            for(int i=gap;i<arr.length;i++){
                int insertVal = arr[i];
                int insertIndex = i;
                while(insertIndex-gap>=0&&arr[insertIndex-gap]<insertVal){
                    arr[insertIndex] = arr[insertIndex-gap];
                    insertIndex-=gap;
                }
                arr[insertIndex] = insertVal;
            }
        }
    }

    private static void sort6(int []arr,int left,int right){
        int l = left;
        int r = right;
        int midVal = arr[l+r/2];
        while(l<r){
            while(arr[l]<midVal){
                l++;
            }

            while(arr[r]>midVal){
                r--;
            }

            //没必要循环了
            if(l>=r){
                break;
            }
            int tmp = arr[l];
            arr[l] = arr[r];
            arr[r] = tmp;

            if(arr[l] == midVal){
                r--;
            }

            if(arr[r] == midVal){
                l++;
            }
        }

        if(r==l){
            r--;
            l++;
        }
        if(left<r){
            sort6(arr,left,r);
        }

        if(l<right){
            sort6(arr,l,right);
        }
    }
}
