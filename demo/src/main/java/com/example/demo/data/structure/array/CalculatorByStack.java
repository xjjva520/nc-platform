package com.example.demo.data.structure.array;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @description: 计算器，字符串 7*3-(12+5) 类似此类表达式
 * @author: jjxu
 * @time: 2022/9/6
 * @package: com.example.demo.data.structure.array
 * 1、创建两个数据栈：数据栈，符号栈。利用栈得特点，先进后出，定义好符号得优先级。
 * 2、如果当前字符是符号，则需要自旋 进行判断直到当前符号得优先级  高于栈顶得优先级
 * 3、如果栈顶优先级高于当前计算符号，则需要取出数据栈 num1 num2 symbol 进行计算。计算完之后得数据继续入数据栈，然后符号入符号栈，
 * 需要忽略 右括号 ）
 * 4、数据入数据栈得时候需要判断十位以上得数字
 */
public class CalculatorByStack {

    //存储符号的栈---符号栈，存储（）+— * /的符号
    private StackArray symbolStack = new StackArray(10);
    //存储表达式中数字的---数据栈
    private StackArray numStack = new StackArray(10);



    /* *
     *  普通计算->输入数据表达式
     **/
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

    /* *
     * 逆波兰表达式---将中缀表达式 转换成后缀表达式，进行计算器计算
     * 1、将表达式-->按字符分割7*3-(12+5)  --> 7 * - ( 12 + 5 ) 存入到list集合中
     * 2、准备一个符号栈 Stack ,数据存放list
     * 3、遍历上述集合，将数字直接入数据存放list,符号入符号栈
     * 3.1 符号入符号栈，如果符号栈为空，直接入栈，如果不为空，需要比较如果当前符号 小于等于符号栈顶优先级，则取出符号栈顶入，重复处理 数据存储list
     * 3.2 如果符号栈顶是( 取出栈顶，不处理
     * 3.3 如果当前符号是 ) 则将栈顶数据存储到数据存储，直到栈顶为( 取出，不处理
     * 4，此时数据list 中的数据从index 0 -> size 就形成后缀表达式
     **/
    public int calculatorInversePolishExpression(String expression){
        //将表达式解析成list 12*4+(3+1)-39 --> [12, *, 4, +, (, 3, +, 1, ), -, 39]
        //获取逆波兰表达式-->[12, *, 4, +, (, 3, +, 1, ), -, 39]  ---> [12, 4, *, 3, 1, +, 3, *, +, 39, -, 1, -]
        List<String> inversePolishExpression = covertInversePolishExpression(expression);
        Stack<Integer> dataStack = new Stack();
        int result = 0;
        for(String str:inversePolishExpression){
            if(str.matches("\\d+")){
                dataStack.push(Integer.parseInt(str));
            }else{
                Integer pop1 = dataStack.pop();
                Integer pop2 = dataStack.pop();
                result = doCalculator(pop1, pop2, str.charAt(0));
                dataStack.push(result);
            }
        }
        return result;
    }


    public List<String> covertInversePolishExpression(String expression){
        //构建符号栈
        Stack<String> symbolStack = new Stack<>();
        List<String> dataList = new ArrayList<>();
        //将表达式解析成list 12*4+(3+1)-39 --> [12, *, 4, +, (, 3, +, 1, ), -, 39]
        List<String> strs = parseExpression(expression);
        //循环处理strs,通过符号栈，转成后缀表达式
        for(int i=0;i<strs.size();i++){
            String s = strs.get(i);
            //如果是数字，则直接入数据存储
            if(s.matches("\\d+")){
                dataList.add(s);
                continue;
            }
            //否则是符号，如果符号栈为空，或者是右括号 则直接入栈
            if(symbolStack.empty()||"(".equals(s)){
                symbolStack.push(s);
            }else{
              //如果符号栈不为空，则需要对符号栈顶符号 与当前字符进行比较，如果当前符号优先级低于栈顶 则需要循环pop栈顶 直到优先级低于当前字符
               while(!symbolStack.empty()&&rank(symbolStack.peek().charAt(0)) >= rank(s.charAt(0))){
                   //如果当前字符是) 并且栈顶是(括号了 则不处理
                   if("(".equals(symbolStack.peek()) && ")".equals(s)){
                       symbolStack.pop();
                       break;
                   }
                   //如果是左括号，当前字符直接入符号栈，(优先级最高
                   if("(".equals(symbolStack.peek())){
                       break;
                   }
                   dataList.add(symbolStack.pop());
               }
                //如果不是右括号 入符号栈
                if(!")".equals(s)){
                    symbolStack.push(s);
                }
            }
        }
        //处理完成，需将符号栈得数据存储到符号入数据存储
        while(!symbolStack.empty()){
            dataList.add(symbolStack.pop());
        }
        return dataList;
    }

    public List<String> parseExpression(String expression){
        List<String> charList = new ArrayList<>();
        int i=0;
        //解析表达式--存入到list中
        while(i<expression.length()){
            String c = expression.charAt(i)+"";
            i++;
            //此处针对连续数字解析 70*3 要把70 解析成一个数字
            if(c.matches("\\d+")){
                while(i<expression.length()){
                    String nextC = expression.charAt(i)+"";
                    if(!nextC.matches("\\d+")){
                        break;
                    }
                    c = c+expression.charAt(i);
                    i++;
                }
            }
            charList.add(c);
        }
        return  charList;
    }

    //进行优先级设置
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
