package com.example.demo.data.structure.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @description: 赫夫曼树 树的路径长度是从树根到每一结点的路径长度之和，记为WPL=（W1*L1+W2*L2+W3*L3+...+Wn*Ln）
 * 拥有最大权值得最大叶子节点，离根节点最近
 */
public class HuffManTree {


    /* *
     * 假设 数组是传的是节点的权值，6,8,12,14,17
     * 第一步：先将年龄构建学生节点，然后按照学生年龄排序
     * 第二步，对排序好的节点 取出第一个，第二个，进行相加构成新节点，放入到集合，重新排序
     * 重复第二步
     *
     **/
    public TreeNode<Integer> createHuffManTree(int arr[]){

        List<TreeNode<Integer>> treeNodes = new ArrayList<>();

        for(int i=0;i<arr.length;i++){
           TreeNode<Integer> treeNode = new TreeNode(arr[i]);
           treeNodes.add(treeNode);
        }

        while (treeNodes.size()>1){
            Collections.sort(treeNodes,Comparator.comparing(TreeNode::getT));
            TreeNode<Integer> treeNode = treeNodes.get(0);
            TreeNode<Integer> treeNode1 = treeNodes.get(1);

            TreeNode treeNode2 = new TreeNode(treeNode.t+treeNode1.t);
            treeNode2.left = treeNode;
            treeNode2.right = treeNode1;

           treeNodes.remove(treeNode);
           treeNodes.remove(treeNode1);
           treeNodes.add(treeNode2);
        }
        return treeNodes.get(0);
    }




    public static class TreeNode<T extends Comparable> implements Comparable<T>{
        //存储对象
        private T t;
        //当前节点的左节点
        private TreeNode<T> left;
        //当前节点的右节点
        private TreeNode<T> right;

        public TreeNode(T t) {
            this.t = t;
        }

        //前置输出 先输出父节点 ->左节点->右节点
        public void preShow(){
            System.out.println(this.t);
            if(left!=null){
                left.preShow();
            }
            if(right!=null){
                right.preShow();
            }
        }

        //中序输出
        public void middleShow(){
            if(left!=null){
                left.middleShow();
            }
            System.out.println(this.t);

            if(right!=null){
                right.middleShow();
            }
        }

        public TreeNode<T> getLeft() {
            return left;
        }

        public void setLeft(TreeNode<T> left) {
            this.left = left;
        }

        public TreeNode<T> getRight() {
            return right;
        }

        public void setRight(TreeNode<T> right) {
            this.right = right;
        }

        public T getT() {
            return t;
        }

        @Override
        public int compareTo(T o) {
            return t.compareTo(o);
        }
    }
}
