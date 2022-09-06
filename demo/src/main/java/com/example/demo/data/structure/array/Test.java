package com.example.demo.data.structure.array;

import com.example.demo.data.structure.entity.Student;

/**
 * @description:
 * @author: jjxu
 * @time: 2022/9/3
 * @package: com.example.demo.data.structure.array
 */
public class Test {

    public static void main(String[] args) {
        String express = "6*(8/2-4*3)-30/(7+8)";
        CalculatorByStack calculator = new CalculatorByStack();
        int calculator1 = calculator.calculator(express);
        System.out.println(calculator1);

    }
}
