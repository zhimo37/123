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
        s1+=s;  //s1��ʾ���Աȵ�ĸ��
        p1+=p;  //p1��ʾ�����Աȵ��Ӵ�e[i]�ĳ��ȵ��ַ������



        // System.out.println("��������Ҫ���ҵ��ִ����Ⱥ��ִ�:");


        int[] ne  = new int[100010]; //��ʾ��iΪ�յ㣬ǰne[i]�ͺ�n
        //  System.out.println("�����뱻���ҵ�ģ���ַ������Ⱥ��ִ�:");

        // System.out.println("ע����������ַ����±��1��ʼ����");
        // ��ne[]
        int i ,j ;
        for(i = 2,j = 0 ; i <= p.length() ; i++){
            while(j!=0 && p1.charAt(i) != p1.charAt(j+1)) j = ne[j];
            if(p1.charAt(i) == p1.charAt(j+1)) j ++ ;
            ne[i] = j ;

        }
        // ����
        for(i = 1,j = 0 ; i <= s.length() ; i ++ ){
            while(j != 0 && s1.charAt(i) != p1.charAt(j+1)) j = ne[j];
            if(s1.charAt(i) == p1.charAt(j+1)) j++;
            if(j == p.length() ){
                res += ("��"+k+"��"+"�Ӵ����±�Ϊ:"+(i-p1.length()+1)+'\n');
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

        // ��������
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(arr, n, i);

        // �����ȡ����Ԫ�ز������������ĩβ
        for (int i=n-1; i>=0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            //�ָ��ѵ�����
            heapify(arr, i, 0);

        }
        String words2[] = new String[words.length];

        for (int i = 0; i < words.length; i++){
            words2[i] = String.valueOf(arr[i]);
            ans=ans+" "+words2[i];
        }
        return ans;
    }

    // ������
    static void heapify(int arr[], int n, int i) {
        int largest = i;
        int l = 2*i + 1;
        int r = 2*i + 2;

        // �������ӽڵ���ڸ��ڵ�
        if (l < n && arr[l] > arr[largest])
            largest = l;

        // ����Ҳ��ӽڵ���ڸ��ڵ�
        if (r < n && arr[r] > arr[largest])
            largest = r;

        // ������Ԫ�ز��Ǹ��ڵ�
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            // �������ݹ�ִ�жѻ�����
            heapify(arr, n, largest);
        }



    /*public static void main(String[] args) {
        String expression = "(2+3)/3";
        System.out.println(evaluate(expression));
    }*/
    }}
