package com.example.demo.data.structure.tree;

/**
 * @description:  顺序二叉树：即 左子节点<当前节点<右子节点
 * @author: jjxu
 * @time: 2022/9/24
 * @package: com.example.demo.data.structure.tree
 */
public class BinarySortTree {

    private Node root;

    public BinarySortTree() {
    }


    public Node search(int val){
        if(root == null){
            return null;
        }
        return root.search(val);
    }

    public Node searchParent(int val){
        if(root==null){
            return null;
        }
        return root.searchParent(val);
    }

    public void add(Node node){
        if(root==null){
            this.root = node;
            return;
        }
        root.addNode(node);
    }

    public void delete(int val){
        //删除节点，三种情况：第
        // 一种 直接删除叶子节点
        // 第二种 删除非叶子节点，但是只有一个子节点（左子节点，或右子节点）
        // 第三种 删除非叶子绩点，有两个子节点
        //先找到需要删除的节点
        Node targetNode = this.search(val);
        //如果没有该节点直接返回
        if(targetNode ==null) return;

        //如果父节点没有，说明肯定是根节点，那么直接删除根节点就是
        Node parentNode = this.searchParent(val);

        //第一种删除叶子节点，即当前节点的左右节点都为空
        if(targetNode.right ==null && targetNode.left ==null){
            if(parentNode==null){
                this.root=null;
                return;
            }
            //如果父节点的左子节点与删除目标节点相等
            if(parentNode.left!=null && parentNode.left == targetNode){
                //说明删除的是父节点的左子节点,直接将父节点的左节点置空
                parentNode.left=null;
            }
            if(parentNode.right!=null && parentNode.right == targetNode){
                //说明删除的是父节点的左子节点
                parentNode.right=null;
            }
        }else {
            //此处说明有两个叶子节点
            if (targetNode.right != null && targetNode.left != null) {
                //将要删除的节点，单做根节点，向左子树找到一个最大的节点，或者向右子树找一个最小的节点 将该节点进行删除
               //删除右子树下面的最小节点
                Node node = deleteNodeRightMinNode(targetNode.right);
                //将该节点的值替换要删除节点的值即可
                targetNode.val = node.val;
            } else {
                if(parentNode==null){
                    this.root=null;
                    return;
                }
                //此处就相当于只有一个子节点 并删除的是父节点的左子节点
                if (parentNode.left != null && parentNode.left == targetNode) {
                    //如果删除节点存在左子节点，那么直接将父节点的左子节点指向删除节点的左子节点，否则指向右子节点
                    if (targetNode.left != null) {
                        parentNode.left = targetNode.left;
                    } else {
                        parentNode.left = targetNode.right;
                    }
                }
                //同理--左边处理
                if (parentNode.right != null && parentNode.right == targetNode) {
                    if (targetNode.left != null) {
                        parentNode.right = targetNode.left;
                    } else {
                        parentNode.right = targetNode.right;
                    }
                }
            }
        }
    }

    //删除传进来节点，右子树最小节点，并且返回
    public Node deleteNodeRightMinNode(Node node){
        if(node==root){
            throw new RuntimeException("opr is 非法");
        }
        //当前节点 为根节点的最小值节点
        Node rightMinValNode = node.searchMinValNode();

        this.delete(rightMinValNode.val);

        return rightMinValNode;
    }

    public void middleShow(){
        if(root==null){
            System.out.println("为空树");
            return;
        }
        root.middleShow();
    }

    public static class Node implements Comparable<Node> {
        int val;
        Node left;
        Node right;

        public Node(int val) {
            this.val = val;
        }

        //查找当前节点为根节点情况下，查找该树下的最小节点
        private Node searchMinValNode(){
            if(this.left==null){
                return this;
            }else{
                return this.left.searchMinValNode();
            }
        }

        //通过val 查找节点
        private Node search(int val){
            //如果当前节点的值相等 直接返回
            if(this.val == val){
                return this;
            }
            //如果当前节点的值比寻找的值大，则往右子节点找
            if(this.val < val && this.right!=null){
                return this.right.search(val);
            }

            if(this.val > val && this.left!=null){
                return this.left.search(val);
            }
            return null;
        }

        //通过val 查找父节点
        private Node searchParent(int val){
            //如果当前节点的左值大于搜索值，说明搜索的值在左边
            if(this.val>val){
                if(this.left!=null){
                    //如果当前节点的左子节点 == 那么返回当前节点
                    if(this.left.val == val) return this;

                    //否则递归向左边查找
                    return this.left.searchParent(val);
                }
            }
            if(this.val<val){
                if(this.right!=null){
                    //如果当前节点的右子节点 == 那么返回当前节点
                    if(this.right.val == val) return this;

                    //否则递归向右边查找
                    return this.right.searchParent(val);
                }
            }
            return null;
        }


        //以当前节点为根节点，然后按顺序二叉树添加节点
        private void addNode(Node node){
            //判断与当前节点的值大小，如果大就往右边放，小就往左边放
            if(node.val>this.val){
                //如果当前节点的右子节点为空，则直接天剑
                if(this.right==null) {
                    this.right = node;
                }else{
                    //如果不为空，则往右子节点继续往下找
                    this.right.addNode(node);
                }
            }else{
                //如果当前节点的右子节点为空，则直接添加
                if(this.left==null) {
                    this.left = node;
                }else{
                    //如果不为空，则往右子节点继续往下找
                    this.left.addNode(node);
                }
            }
        }

        //顺序二叉树一般采用中序输出-->因为输出的顺序恰好是有序的
        public void middleShow(){
            if(left!=null){
                left.middleShow();
            }
            System.out.println(this.val);

            if(right!=null){
                right.middleShow();
            }
        }

        public int getVal() {
            return val;
        }

        @Override
        public int compareTo(Node o) {
            return this.val - o.val;
        }
    }
}
