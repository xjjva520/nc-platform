package com.example.demo.data.structure.tree;

import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 哈夫曼编码：利用哈夫曼树的规则：wpl的数值最小特点，将通讯信息变成huffManCode 将大大的压缩字节传输长度
 *
 * 比如： i like like you 如果转成byte[]数组，那么 105,32，108,105，107,101,32，108,105，107,101,32，121,111,117
 * 变成二进制 110100110000011011001101001110101111001011000001101100110100111010111100101100000111100111011111110101
 * 正常长度就是去掉空格: 102长度
 *哈夫曼编码规则：
 * 第一步：将i like like you 统计字符出现的次数 i:3 l:2 k:2 e:2,y:1 o:1 u:1 空格3
 * 根据统计的次数形成huffMan树，然后按照哈夫曼树带权路径长度 按左边是路径是0  右边是1 组合得到新的haff码 i(105)-->00 空格（32）-->111 l(108)->101
 * 进行haff后：得到二进制字符串：00111101 00100011 11110100 10001111 11101010 1100 长度44 压缩率 (102-44)/102  56%
 * 然后再将上面二进制字符串，转成压缩byte数组
 *
 * */
public class HuffManCode {

    //human码表
    private Map<Byte,String> huffmanCode = new HashMap<>();


    public String unzip(byte[] bytes){
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<bytes.length;i++){
            if(i == bytes.length-1){
                builder.append(decode(bytes[i], false));
            }else{
                builder.append(decode(bytes[i], true));
            }
        }
        //huffmancode 表转成码->byte 将key 和value 进行个转换
        Map<String, Byte> collect = huffmanCode.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
        List<Byte> bts = new ArrayList<>();
        System.out.println(builder.toString());
        //拿着上面解码出来的二进制字符串，去反匹配字符 00111101001000111111010010001111111010101100
        for(int i=0;i<builder.length();){
            int count =1 ;
            while(true){
                String key = builder.substring(i, i + count);
                Byte aByte = collect.get(key);
                if(aByte==null){
                  count++;
                }else{
                    bts.add(aByte);
                    break;
                }
            }
            i += count;
        }
        byte[] btss = new byte[bts.size()];
       for(int i=0;i<bts.size();i++){
           btss[i]=bts.get(i);
       }
       return new String(btss);
    }
    
    /* * 
     * @Author jjxu
     * @Description 通过haffMan 编码获取到压缩后的byte 数组，先转成对应的二进制字符串
     * @Date 14:45 2022/9/23
     * @Param [bytes]
     *
     * 负数转二进制：先将负数获取源码 即对应正数的二进制，然后取反码 在对反码+1
     * 负数二进制转十进制  先负数的二进制码 -1 在取反码- 然后高位不动，高位之前的数据变成十进制 在加上符号
     * 例如：-3 对应的二进制 00000011 --->11111100 --->在加1 11111101
     *
     * 11111101 转十进制 对应的byte
     *  11111101 -1 --> 11111100 -> 10000011 -> -3
     **/
    public String decode(byte bt,boolean needPolish){
        //如果是负数 转二进制后 后面会自动补1 补齐32位，因此要截取后八位
        //如果是整数，可能还要进行补码
        //如果是最后一个数是整数，就不要进行补码了
        int val = bt;
        if(needPolish){
            //256 二进制 100000000 | 1100 = 100001100
           val = val | 256;
        }
        String binaryStr = Integer.toBinaryString(val);

        if(needPolish){
            return  binaryStr.substring(binaryStr.length() - 8);
        }
        return binaryStr;
    }

    //重新编码后，返回一个byte数组
    public byte[] huffManZip(byte[]bytes){
        //进行统计
        Node huffManTree = createHuffManTree(bytes);
        //获取哈夫曼编码
        Map<Byte, String> codes = encode(huffManTree);

        byte[] zip = zip(bytes, codes);

        return zip;

    }


    //将原始数组byte 然后获取对应的 哈夫曼码，组成一个长串二进制字符串
    //然后再将二进制字符串 按8个bit 组成一个byte 换成byte数组
    private byte[] zip(byte[] bytes,Map<Byte, String> huffmanCode){
        StringBuilder builder = new StringBuilder();
        for(byte bt:bytes){
            builder.append(huffmanCode.get(bt));
        }

        String bitStr = builder.toString();
        System.out.println(bitStr);
        //按8bit 截取 ，获取压缩的byte数组长度
        int zipLength = bitStr.length() % 8 == 0 ? bitStr.length() % 8 : bitStr.length() / 8 + 1;
        byte[] zipByte = new byte[zipLength];
        int index = 0;
        for(int i=0;i<bitStr.length();i+=8){
            String bit8;
            if(i+8>bitStr.length()){
                bit8 = bitStr.substring(i);
            }else{
                bit8 = bitStr.substring(i, i+8);
            }
            //8个字节对应的十进制int 值
            int val = Integer.parseInt(bit8, 2);
            zipByte[index++] = (byte) val;
        }
        return zipByte;
    }


    private Map<Byte,String> encode(Node huffmanTree){
         //创建huffMan 树
        StringBuilder builder = new StringBuilder();

        //组成map i（105）->00  key 字符对应的byte字节 val对应的haffman码
        createHuffManCode(huffmanTree,"",builder);

        return huffmanCode;

    }

    private void createHuffManCode(Node node,String code,StringBuilder builder){
        StringBuilder bd = new StringBuilder(builder);
        bd.append(code);
        if(node.c==null){
            createHuffManCode(node.left,"0",bd);

            createHuffManCode(node.right,"1",bd);
        }else{
            huffmanCode.put(node.c,bd.toString());
        }
    }



    private Node createHuffManTree(byte[] bytes){
        Map<Byte,Integer> reportMap = new HashMap<>();
        for(byte bt:bytes){
            Integer integer = reportMap.get(bt);
            if(integer==null){
                reportMap.put(bt,1);
            }else{
                integer++;
                reportMap.put(bt,integer);
            }
        }
        //创建huffMan树
        List<Node> nodes = reportMap
                .entrySet()
                .stream()
                .map(g -> new Node(g.getKey(), g.getValue()))
                .collect(Collectors.toList());
        while(nodes.size()>1){
            //先按照从小到大排序
            Collections.sort(nodes);
            Node left = nodes.get(0);
            Node right = nodes.get(1);
            //构建新节点
            Node newNode = new Node(null,left.val+right.val);
            nodes.add(newNode);
            newNode.left=left;
            newNode.right=right;
            nodes.remove(left);
            nodes.remove(right);
        }
        return nodes.get(0);
    }


    //前置输出 先输出父节点 ->左节点->右节点
    public void preShow(Node node){
        node.preShow();
    }


    public class Node implements Comparable<Node>{
        //字符对应的byte
        Byte c;
        //该字符出现的次数
        int val;
        Node left;
        Node right;

        public Node(Byte c, int val) {
            this.c = c;
            this.val = val;
        }

        //前置输出 先输出父节点 ->左节点->右节点
        public void preShow(){
            System.out.println(this.val);
            if(left!=null){
                left.preShow();
            }
            if(right!=null){
                right.preShow();
            }
        }

        @Override
        public int compareTo(Node o) {
            return this.val-o.val;
        }
    }
}
