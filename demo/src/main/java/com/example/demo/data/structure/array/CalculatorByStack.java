package com.example.demo.data.structure.array;

/**
 * @description: 计算器，字符串 7*3-(12+5) 类似此类表达式
 * @author: jjxu
 * @time: 2022/9/6
 * @package: com.example.demo.data.structure.array
 */
public class CalculatorByStack {

    //存储符号的栈---符号栈，存储（）+— * /的符号
    private StackArray symbolStack = new StackArray(10);
    //存储表达式中数字的---数据栈
    private StackArray numStack = new StackArray(10);


    public int calculator(String expression){
        char[] chars = expression.toCharArray();
        int num1;
        int num2;
        char symbol;
        String keepNum = "";
        for(int i = 0; i < chars.length ; i++){
            //如果小于0 则说明是数字
            char ch = chars[i];
            if(rank(ch) <0 ){
                keepNum = keepNum+ch;
                if(i+1==chars.length || rank(chars[i+1])>0){
                    numStack.add(Integer.parseInt(keepNum));
                    keepNum="";
                }
            }else{
                //循环比对，看当前字符是不是比栈顶优先级低
                while(true){
                    //入符号栈
                    if(symbolStack.isEmpty()){
                        break;
                    }
                    char peek = (char) symbolStack.peek();
                    //如果符号栈中栈顶的符号的优先级>当前符号，则需要从数据栈中获取 num1 num2 ,符号栈中获取符号进行计算
                    //然后将计算的数据入数据栈，并且将当前符号入符号栈
                    if(rank(ch) <= rank(peek)){
                        //如果符号栈顶符号为 （ 则当前符号直接入栈
                        if(peek == '('){
                            break;
                        }
                        if(numStack.size()<1){
                            break;
                        }
                        //从数据栈中取出两个数字，进行计算
                        num1 = numStack.get();
                        num2 = numStack.get();
                        symbol = (char) symbolStack.get();
                        //进行计算
                        int culatorNum = this.doCalculator(num1, num2, symbol);
                        //将计算出的数据，重新入栈
                        numStack.add(culatorNum);
                        //如果当前字符为）则计算完 还需要从栈中取出'('

                    }else{
                        break;
                    }
                }
                if(ch == ')'){
                    symbolStack.get();
                }else{
                    //符号入栈
                    symbolStack.add(ch);
                }
            }
        }
        while (numStack.size()!=0){
            num1 = numStack.get();
            num2 = numStack.get();
            symbol = (char) symbolStack.get();
            int i = doCalculator(num1, num2, symbol);
            numStack.add(i);
        }

        return numStack.get();
    }


    private int rank(char ch){
        switch (ch){
            case '(':
                return 4;
            case '*':
            case '/':
                return 3;
            case '+':
            case '-':
                return 2;
            case ')':
                return 1;
            default:
                return -1;
        }

    }

    private int doCalculator(int num1, int num2,char symbol){
        switch (symbol){
            case '*':
                return num1 * num2;
            case '/':
                return num2 / num1;
            case '+':
                return num2 + num1;
            case '-':
                return num2 - num1;
            default:
                throw new RuntimeException("symbol is error");
        }
    }
}
