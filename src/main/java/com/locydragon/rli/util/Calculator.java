package com.locydragon.rli.util;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.Stack;

public final class Calculator {
    private final Stack<String> postfixStack  = new Stack<String>();//后缀式栈
    private final Stack<Character> opStack  = new Stack<Character>();//运算符栈
    private final int [] operatPriority  = new int[] {0,3,2,1,-1,1,0,2};//运用运算符ASCII码-40做索引的运算符优先级
    /**
     * 按照给定的表达式计算
     * @param expression 要计算的表达式例如:5+12*(3+5)/7
     * @return
     */
    public double calculate(Player who, String expression) {
        expression = PlaceholderAPI.setBracketPlaceholders(who, expression);
        //return Double.valueOf(new CalculateStack().Calculate(expression, 10));
        Stack<String> resultStack  = new Stack<String>();
        prepare(expression);
        Collections.reverse(postfixStack);//将后缀式栈反转
        String firstValue  ,secondValue,currentValue;//参与计算的第一个值，第二个值和算术运算符
        while(!postfixStack.isEmpty()) {
            currentValue  = postfixStack.pop();
            if(!isOperator(currentValue.charAt(0))) {//如果不是运算符则存入操作数栈中
                resultStack.push(currentValue);
            } else {//如果是运算符则从操作数栈中取两个值和该数值一起参与运算
                 secondValue  = resultStack.pop();
                 firstValue  = resultStack.pop();
                 String tempResult  = calculate(firstValue, secondValue, currentValue.charAt(0));
                 resultStack.push(tempResult);
            }
        }
        return Double.valueOf(resultStack.pop());
    }

    public double calculateWithOutPlayer(String expression) {
        //return Double.valueOf(new CalculateStack().Calculate(expression, 10));
        Stack<String> resultStack  = new Stack<String>();
        prepare(expression);
        Collections.reverse(postfixStack);//将后缀式栈反转
        String firstValue  ,secondValue,currentValue;//参与计算的第一个值，第二个值和算术运算符
        while(!postfixStack.isEmpty()) {
            currentValue  = postfixStack.pop();
            if(!isOperator(currentValue.charAt(0))) {//如果不是运算符则存入操作数栈中
                resultStack.push(currentValue);
            } else {//如果是运算符则从操作数栈中取两个值和该数值一起参与运算
                secondValue  = resultStack.pop();
                firstValue  = resultStack.pop();
                String tempResult  = calculate(firstValue, secondValue, currentValue.charAt(0));
                resultStack.push(tempResult);
            }
        }
        return Double.valueOf(resultStack.pop());
    }
    
    /**
     * 数据准备阶段将表达式转换成为后缀式栈
     * @param expression
     */
    private void prepare(String expression) {
        opStack.push(',');//运算符放入栈底元素逗号，此符号优先级最低
        char[] arr  = expression.toCharArray();
        int currentIndex  = 0;//当前字符的位置
        int count = 0;//上次算术运算符到本次算术运算符的字符的长度便于或者之间的数值
        char currentOp  ,peekOp;//当前操作符和栈顶操作符
        for(int i=0;i<arr.length;i++) {
            currentOp = arr[i];
            if(isOperator(currentOp)) {//如果当前字符是运算符
                if(count > 0) {
                    postfixStack.push(new String(arr,currentIndex,count));//取两个运算符之间的数字
                }
                peekOp = opStack.peek();
                if(currentOp == ')') {//遇到反括号则将运算符栈中的元素移除到后缀式栈中直到遇到左括号
                    while(opStack.peek() != '(') {
                        postfixStack.push(String.valueOf(opStack.pop()));
                    }
                    opStack.pop();
                } else {
                    while(currentOp != '(' && peekOp != ',' && compare(currentOp,peekOp) ) {
                        postfixStack.push(String.valueOf(opStack.pop()));
                        peekOp = opStack.peek();
                    }
                    opStack.push(currentOp);
                }
                count = 0;
                currentIndex = i+1;
            } else {
                count++;
            }
        }
        if(count > 1 || (count == 1 && !isOperator(arr[currentIndex]))) {//最后一个字符不是括号或者其他运算符的则加入后缀式栈中
            postfixStack.push(new String(arr,currentIndex,count));
        } 
        
        while(opStack.peek() != ',') {
            postfixStack.push(String.valueOf( opStack.pop()));//将操作符栈中的剩余的元素添加到后缀式栈中
        }
    }
    
    /**
     * 判断是否为算术符号
     * @param c
     * @return
     */
    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '(' ||c == ')' || c == '^' || c == '%' || c == '&';
    }
    
    /**
     * 利用ASCII码-40做下标去算术符号优先级
     * @param cur
     * @param peek
     * @return
     */
    public  boolean compare(char cur,char peek) {// 如果是peek优先级高于cur，返回true，默认都是peek优先级要/// 低
        if (cur == '&') {
            return false;
        }
        if (peek == '&') {
            return false;
        }
        boolean result  = false;
        if(operatPriority[(peek)-40] >= operatPriority[(cur) - 40]) {
           result = true;
        }
        return result;
    }
    
    /**
     * 按照给定的算术运算符做计算
     * @param firstValue
     * @param secondValue
     * @param currentOp
     * @return
     */
    private String calculate(String firstValue,String secondValue,char currentOp) {
        String result  = "";
        switch(currentOp) {
            case '+':
                result = String.valueOf(ArithHelper.add(firstValue, secondValue));
                break;
            case '-':
                result = String.valueOf(ArithHelper.sub(firstValue, secondValue));
                break;
            case '*':
                result = String.valueOf(ArithHelper.mul(firstValue, secondValue));
                break;
            case '/':
                result = String.valueOf(ArithHelper.div(firstValue, secondValue));
                break;
            case '^':
                result = String.valueOf(ArithHelper.doubleTime(firstValue, secondValue));
            case '%':
                result = String.valueOf(Integer.valueOf(firstValue) % Integer.valueOf(secondValue));
            case '&':
                result = String.valueOf(pow(Double.valueOf(firstValue), Double.valueOf(secondValue)));
        }
        return result;
    }

    private double pow(double key, double value) {
        if (key == 0) {
            return 1.0;
        } else {
            return Math.pow(key, value);
        }
    }
}