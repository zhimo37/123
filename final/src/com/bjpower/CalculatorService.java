package com.bjpower;

import java.util.Stack;

public class CalculatorService {
    public static String calculate(String num1, String num2, String operator) throws NumberFormatException {
        switch (operator) {
            case "A":
                return evaluate(num1);
            case "B":
                return sort(num1);
            case "C":
                return search(num1,num2);
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

    private static int precedence(char operator) {
        if (operator == '+' || operator == '-') {
            return 1;
        } else if (operator == '*' || operator == '/') {
            return 2;
        } else if (operator == '^') {
            return 3;
        } else {
            return -1;
        }
    }

    private static double applyOp(char operator, double a, double b) {
        switch(operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0)
                    throw new UnsupportedOperationException("Cannot divide by zero");
                return a / b;
            case '^':
                return Math.pow(a, b);
        }
        return 0;
    }

    public static String evaluate(String expression) {
        char[] tokens = expression.toCharArray();

        Stack<Double> values = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i] == ' ') {
                continue;
            } else if (tokens[i] >= '0' && tokens[i] <= '9') {
                StringBuilder sb = new StringBuilder();
                while (i < tokens.length && (tokens[i] >= '0' && tokens[i] <= '9' || tokens[i] == '.')) {
                    sb.append(tokens[i++]);
                }
                values.push(Double.parseDouble(sb.toString()));
                i--;
            } else if (tokens[i] == '(') {
                operators.push(tokens[i]);
            } else if (tokens[i] == ')') {
                while (operators.peek() != '(') {
                    double val2 = values.pop();
                    double val1 = values.pop();
                    char op = operators.pop();

                    values.push(applyOp(op, val1, val2));
                }
                operators.pop();
            } else if (tokens[i] == '+' || tokens[i] == '-' || tokens[i] == '*' || tokens[i] == '/' || tokens[i] == '^') {
                while (!operators.empty() && precedence(tokens[i]) <= precedence(operators.peek())) {
                    double val2 = values.pop();
                    double val1 = values.pop();
                    char op = operators.pop();
                    values.push(applyOp(op, val1, val2));
                }
                operators.push(tokens[i]);
            }
        }

        while (!operators.empty()) {
            double val2 = values.pop();
            double val1 = values.pop();
            char op = operators.pop();

            values.push(applyOp(op, val1, val2));
        }
        return String.valueOf(values.pop());
    }


    public static String search(String s,String p) {
        int k=1;
        String res = "\n";
        String s1="0";
        String p1="0";
        s1+=s;  //s1表示被对比的母串
        p1+=p;  //p1表示用来对比的子串e[i]的长度的字符串相等



        // System.out.println("请输入需要查找的字串长度和字串:");


        int[] ne  = new int[100010]; //表示以i为终点，前ne[i]和后n
        //  System.out.println("请输入被查找的模板字符串长度和字串:");

        // System.out.println("注：本程序的字符串下标从1开始计算");
        // 算ne[]
        int i ,j ;
        for(i = 2,j = 0 ; i <= p.length() ; i++){
            while(j!=0 && p1.charAt(i) != p1.charAt(j+1)) j = ne[j];
            if(p1.charAt(i) == p1.charAt(j+1)) j ++ ;
            ne[i] = j ;

        }
        // 主体
        for(i = 1,j = 0 ; i <= s.length() ; i ++ ){
            while(j != 0 && s1.charAt(i) != p1.charAt(j+1)) j = ne[j];
            if(s1.charAt(i) == p1.charAt(j+1)) j++;
            if(j == p.length() ){
                res += ("第"+k+"个"+"子串的下标为:"+(i-p1.length()+1)+'\n');
                j = ne[j];
                k++;
            }
        }
        return res;
   /* public static void main(String[] args) {
        KMP.search("abababababa","aba");
    } */

    }


    public static String sort(String str) {

        String regex = "[\\s]+";
        String words[] = str.split(regex);
        String ans=new String();
        int arr[] = new int[words.length];
        for (int i = 0; i < words.length; i++)
            arr[i] = Integer.parseInt(words[i]);//

        int n = arr.length;

        // 构建最大堆
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(arr, n, i);

        // 逐个提取最大的元素并将其放在数组末尾
        for (int i=n-1; i>=0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            //恢复堆的性质
            heapify(arr, i, 0);

        }
        String words2[] = new String[words.length];

        for (int i = 0; i < words.length; i++){
            words2[i] = String.valueOf(arr[i]);
            ans=ans+" "+words2[i];
        }
        return ans;
    }

    // 调整堆
    static void heapify(int arr[], int n, int i) {
        int largest = i;
        int l = 2*i + 1;
        int r = 2*i + 2;

        // 如果左侧子节点大于父节点
        if (l < n && arr[l] > arr[largest])
            largest = l;

        // 如果右侧子节点大于父节点
        if (r < n && arr[r] > arr[largest])
            largest = r;

        // 如果最大元素不是根节点
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            // 对子树递归执行堆化操作
            heapify(arr, n, largest);
        }



    /*public static void main(String[] args) {
        String expression = "(2+3)/3";
        System.out.println(evaluate(expression));
    }*/
    }}
