package com.example.demo.data.structure.basicsf;

import java.util.Arrays;

/**
 * @description:  排序的八大算法
 * @author: jjxu
 * @time: 2022/9/10
 * @package: com.example.demo.data.structure.basicsf
 * 交换排序（冒泡排序，快速排序）：两个指针，两个数据进行比较，如果A1 < A2则进行位置交换
 * 选择排序（简单选择排序，堆排序）
 * 插入排序（直接插入排序，希尔排序）
 * 堆排序
 * 归并排序
 * 基数排序
 */
public class SortBy8 {


    /* *
     * 外层循环控制比较次数，内层循环每次将最大那个数后移。即外层每循环一次，那么后面数字就是大数字
     **/
    public void BubbleSort(int [] arr){
        boolean changeFlag = true;
        int tmp;
        for(int i =0;i< arr.length-1;i++){
            //减去i的意义就是 i每增加一次，尾部的数字肯定史最大的，因此没必要去比较了
            for(int j =0;j<arr.length-1-i;j++){
                //进行数据交换
                if(arr[j]>arr[j+1]){
                    tmp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = tmp;
                    //如果有交换，则置为false;
                   changeFlag = false;
                 }
            }
            //如果本次循环，发生交换，重新将changeFlag 变成true;
            if(!changeFlag){
                changeFlag = true;
            }else{
                break;
            }
        }
    }



    /* *
     * 每此处理，都找出最小的那个数，然后放在当前开始比较的下标
     **/
    public void selectSort(int [] arr){
        //选择处理的次数等于，数组长度-1 也就是如果数组长度为5 那么需要4次才能找完
        for(int i =0;i< arr.length-1;i++){
            int minIndex = i;
            int minVal = arr[i];
            for(int j = i+1;j<arr.length;j++){
                //记录此次比较找出来的最小值，以及最小值对应的下标
                if(minVal>arr[j]){
                   minVal = arr[j];
                   minIndex = j;
                }
            }
            //进行交换
            arr[minIndex] = arr[i];
            arr[i] = minVal;
        }
    }


    /* *
     *插入排序：插入排序的思想就是[4,6,2,3] 尝试将第二个数往前面插入，比如6 尝试往前面插入，插入不进去，则保持不动，在将后面的往前面插入
     * 2 往前面插入
     **/
    public void insertSort(int [] arr){
        //定义插入的值
        //插入的下标
        int insertVal;
        int insertValIndex;
        //因为是从第二个数开始进行插入，因此下标从1开始
        //{13,459,23,15,1,0,3}
        for(int i=1;i<arr.length;i++){

         insertVal = arr[i];
         insertValIndex = i;

        while(insertValIndex>0 && arr[insertValIndex-1]>insertVal){
            //前面的值往后面挪
            arr[insertValIndex] = arr[insertValIndex-1];
            insertValIndex --;
        }
           arr[insertValIndex] = insertVal;
        }

    }


    /* *
     * 希尔排序，是在简单插入排序上做了优化，以前跨度是1，先跨度 数组长度/2/2/2 最终变成1
     * 每次数组长度/2 缩短跨度，比如 2,4,6,3,8,7,9,1,0,5 第一次就是10/2，首次跨度就是5
     * 2，4，1，0，5，7，9，6，3，8
     *
     **/
    public void shellSort(int [] arr){
        int tmp = 0;
        //初始跨度为长度/2，循环一次就gap /2
        for(int gap = arr.length/2;gap>0; gap /=2){
            for(int i=gap;i<arr.length;i++){
                for(int j= i-gap;j>=0;j-=gap){
                    //此处可以用交换方式
                     if(arr[j] > arr[j+gap]){
                         tmp = arr[j];
                         arr[j] = arr[j+gap];
                         arr[j+gap] = tmp;
                     }
                }
            }
        }
    }

    /* *
     *希尔+插入排序法，并且是从gap开始
     **/
    public void shellSort2(int [] arr){
        int tmp = 0;
        //初始跨度为长度/2，循环一次就gap /2
        for(int gap = arr.length/2;gap>0; gap /=2){
            for(int i=gap;i<arr.length;i++){
                int minval= arr[i];
                int minIndex = i;
                //插入法(简单插入法只是跨度为1，而这里的跨度变成了gap)
                while(minIndex-gap >=0 && minval<arr[minIndex-gap]){
                    arr[minIndex] = arr[minIndex-gap];
                    minIndex -=gap;
                }
                arr[minIndex] = minval;
            }
        }
    }

    /* *
     * 快速排序：找一个基准值，比基准值小的放左边，比基准值大的放右边
     * 递归对基准值左边在进行快速排序， 基准值右边的又继续进行排序
     **/
    public void fastSort(int []arr,int left,int right){
        
        int l = left;//记住传进来的左边的值
        int r = right;//记住传进来右边的值
        //取中间值，作为基准值
        int middleVal = arr[(left+right)/2];
        int tmp = 0;

        //如果l > r了也就没有在继续的必要了
        while(l<r){
            //从基准值得左边找，直到找到一个比基准值大的
            while(arr[l]< middleVal){
                l++;
            }
            //从基准值得左边找，直到找到一个比基准值小的
            while(arr[r]> middleVal){
                r--;
            }
            //这个地方说明已经找到了中间位置， 没得必要在进行下去
            if(l>=r){
                break;
            }
            //此时这里就可以进行数据交换
            tmp = arr[l];
            arr[l] = arr[r];
            arr[r] = tmp;

            //如果交换完 arr[r] ==中间值,说明之前左边那个值上来就大于 middleVal，导致l不会右移，出现死循环
            if(arr[r] == middleVal){
               l++;
            }
            //如果交换完 arr[l] ==中间值,说明之前右边那个值上来就小于 middleVal，导致r不会左移，出现死循环
            if(arr[l] == middleVal){
                r--;
            }
        }
        //完成一轮，如果l ==r 说明要相应的右移左移
        if(l == r){
            l++;
            r--;
        }
        if(left<r){
            //中间值进行左边继续进行快速排序
            fastSort(arr,left,r);
        }

        if(right>l){
            //中间值进行右边继续进行快速排序
            fastSort(arr,l,right);
        }
    }

    
    /* * 
     * 归并排序：数组，取中间序号，左右进行拆，直到拆带 左边l < 右边的值。
     **/
    public void mergeSort(int arr[],int left,int right){
        int mid = (left+right)/2;
        int l = left;
        int r = right;
        if(l<r){
            //向左边进行分解
            mergeSort(arr,l,mid);
            //向右边进行分解
            mergeSort(arr,mid+1,right);

            merge(arr,l,mid,r);
        }
    }

    public void merge(int arr[],int left,int mid,int right){
        //定义临时数组，存储有序合并数据
        int []tmp = new int[right-left+1];
        int low = left;

        int j = mid+1;
        int tmpIndex = 0;
        while(low<=mid && j<=right){
            //左右两边的值进行比较
            if(arr[low]<arr[j]){
                tmp[tmpIndex++] = arr[low++];
            }else{
                tmp[tmpIndex++] = arr[j++];
            }
        }
        // 右边剩余元素填充进temp中（因为前面已经归并，剩余的元素必会小于右边剩余的元素）
        while (low <= mid) {
            tmp[tmpIndex++] = arr[low++];
        }
        // 右边剩余元素填充进temp中（因为前面已经归并，剩余的元素必会大于于左边剩余的元素）
        while (j <= right) {
            tmp[tmpIndex++] = arr[j++];
        }
        //将tmp数据在copy回arr
        for(int x =0;x<tmp.length;x++){
            //从传进来左边那个序号开始，因为前面位置可能被占用了
            arr[left+x] = tmp[x];
        }
    }

    /* *
     * 桶排序的思想-准备10个桶，先按数据的 个位进行比较，每个位数是几，就将该数放到相对应下标的痛里面
     **/
    public void BucketSort(int arr[]){
        //创建10个桶，存放数据
        int [][]bucket = new int[10][arr.length];
        //记录每个桶中数据的个数
        int[] bucketElementCounts = new int[10];
        //先找出数组中，最大的数，看最高位是多少
        int maxVal = arr[0];
        for(int i =0;i<arr.length;i++){
            if(maxVal<arr[i]){
                maxVal = arr[i];
            }
        }
        //获取到最大数的最高位
        int maxValLen = (maxVal+"").length();


        //求个位的数：x/1%10  十位：x/10%10 百位 x/100%10
        for(int i=0,n=1;i<maxValLen;i++,n*=10){
            int bucketRealValIndex =0 ;
            for(int j=0;j<arr.length;j++){
                //获取比较当前位数的数值，即个位是几或者百位是几，或者千位是几
                int i1 = (Math.abs(arr[j]) / n) % 10;
                //将这个值放入相应的桶里面
                bucket[i1][bucketElementCounts[i1]++] = arr[j];
            }
            //先处理负数桶
            //然后将每个桶里面的值取出来赋值
            for(int b=bucketElementCounts.length-1;b>=0;b--){
                for(int v =0;v<bucketElementCounts[b];v++){
                    if(bucket[b][v] < 0){
                        arr[bucketRealValIndex++] = bucket[b][v];
                    }
                }
            }
            //然后将每个桶里面的值取出来赋值
            for(int b=0;b<bucketElementCounts.length;b++){
                for(int v =0;v<bucketElementCounts[b];v++){
                    if(bucket[b][v] > 0){
                        arr[bucketRealValIndex++] = bucket[b][v];
                    }
                }
                bucketElementCounts[b] =0;
            }
        }
    }


    /* *
     * 堆排序：堆排序 是利用顺序二叉树，然后存入到数组中。 而顺序二叉树 存储的节点对应的数组下标
     * 当前节点的左节点 对应的数组下标 = 2*n +1
     *当前节点的右节点 对应的数组下标 = 2*n +2
     *堆排序 利用的是二叉树的大顶堆 小顶堆的概念进行完成排序的，大顶堆：父节点总是大雨等于 左右节点 小顶堆 父节点总是小于等于左右节点
     * 因此每次对数组进行堆排序的时候，就是要把数组按照顺序二叉树 转变成大顶堆，或者小顶堆。比如 从大到小排序，则先转成大顶堆，然后将root节点放到数组的末尾，继续转成大顶堆，
     * 在将root节点放到次末尾
     *
     */
    public void heapSort(int arr[]){
        //先将原数组调整成一个大顶堆(先从最后一个非叶子节点开始调整)
        //找到最后一个非叶子节点，最后一个非叶子节点：数组长度/2 -1
        for(int i = arr.length/2-1;i>=0;i--){
            changeMaxHeapTree(arr,i,arr.length);
        }

        //System.out.println(Arrays.toString(arr));
        int tmp=0;
        //搞成大顶堆后，将第一个元素与最后一个进行交换
        //交换一次说明最后一个数就是最大数，下次最大数的前一个 。。。。。所以交换时注意 最后一个数是arr[arr.length-i]
        for(int i=1;i<arr.length;i++){
            tmp = arr[arr.length - i];
            arr[arr.length-i] = arr[0];
            arr[0] = tmp;
            //有进行大顶堆转换
            changeMaxHeapTree(arr,0,arr.length-i);
        }

        //System.out.println(Arrays.toString(arr));
    }

    /* *
     * 将数组转成大顶堆
     * arr 数组
     *  nodeIndex 将该节点当做root节点，变成大顶堆
     * 需要处理的数组长度
     **/
    public void changeMaxHeapTree(int arr[],int nodeIndex,int length){
        int temp = arr[nodeIndex];
        int tempIndex = nodeIndex;
        //此处就相当于获取当前节点的左，右节点进行比对，
        for(int k=nodeIndex*2+1;k<length;k=k*2+1){
            //此处就是 左右节点进行比对，如果右节点大，那就k++ 因为右节点= 2n+2
             if(k+1<length && arr[k]<arr[k+1]){
                 k++;
             }
             //如果左右节点大比当前节点大，需要交换
             if(arr[k]>temp){
                 arr[tempIndex] = arr[k];
                 tempIndex = k;
             }else{
                 //说明没有交换
                 //此次 也就是当前节点与左右节点无交换直接Break
                 break;
             }

        }
        arr[tempIndex] = temp;
    }
}
