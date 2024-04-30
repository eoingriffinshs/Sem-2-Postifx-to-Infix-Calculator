//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import javax.swing.JOptionPane;//dialog box libary

public class Main {

    public static char[] QuestionBox() {//question box method
        String InputSum;//string to store entered info
        char[] Whitelist = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '-', '*', '/', '^', '(', ')'};//whitelist which are the only characters allowed

        InputSum = JOptionPane.showInputDialog("Please enter an influx numerical expression between 3 and 20 characters:");//dialog box which asks user for their input which will store it as the InputSum String


        while (InputSum.length() < 3 || InputSum.length() > 20) {//while loop to check if the lenght of the string is between 3 and 20 letters
            JOptionPane.showMessageDialog(null, "Please enter a sum that is between 3 and 20 letters!!!");//error message if true
            InputSum = "";//resets the string to have no characters.
            QuestionBox();//restarts the method.
        }
        for (int i = 0; i < InputSum.length(); i++) {//for loop that with the help of the ContainsOnly method, checks the entered notation against characters in the whitelist to ensure all entered characters are valid.
            if (!ContainsOnly(InputSum.charAt(i), Whitelist)) {
                JOptionPane.showMessageDialog(null, "Incorrect notation has been detected ");//error message to be produced if incorrect notation detected.
                InputSum = "";//resets the string to have nothing in it
                QuestionBox();//restarts this method from the begining.



            }
        }
        char[] EnteredEquation = InputSum.toCharArray();//if entered sum passes the above checks, the string with the sum is converted into a character array.
        return EnteredEquation;//return what is stored in the character array.
    }

    public static int precidencecheck(int selected) {//method that allocates the precedence of each operation

        if (selected == '^')
            return 3;
        else if (selected == '/' || selected == '*')
            return 2;
        else if (selected == '+' || selected == '-')
            return 1;
        else
            return -1;
    }


    public static String InfixEquation(char[] EnteredEquation) {
        ArrayStack stack = new ArrayStack(); // Stack for operators.
        StringBuilder output = new StringBuilder(); // Store postfix expression.

        for (int i = 0; i < EnteredEquation.length; i++) {//1
            char c = EnteredEquation[i];

            if (Character.isDigit(c)) {
                output.append(c); // 2Append operand to the output.
            } else if (c == '(') {
                stack.push(c); // 3Push '(' to stack.
            } else if (c == ')') {//3.1
                while (!stack.isEmpty() && !stack.top().equals('(')) {
                    output.append(stack.pop()); // Pop until '(' is encountered.
                }
                stack.pop(); // Remove '(' from stack.
            } else { // Operator encountered
                while (!stack.isEmpty() && precidencecheck((char)stack.top()) >= precidencecheck(c)) {//check precidence of the stack and the scanned character
                    output.append(stack.pop());
                }
                stack.push(c);
            }
        }

        while (!stack.isEmpty()) { // Pop remaining operators to the string
            output.append(stack.pop());
        }

        return output.toString();
    }

    public static double PostfixEvaluation(String PostfixExpression) {
        ArrayStack stack = new ArrayStack();//create a new stack

        for (int i = 0; i < PostfixExpression.length(); i++) {//for loop to run for length of the PostfixExpression string length is completed
            char c = PostfixExpression.charAt(i);//changes whatever scanned character to the character c

            if (Character.isDigit(c)) {//check if scanned item is a digit.
                stack.push((double) (c - '0')); // Push operand as double.
            } else {
                // Pop two operands for the operation.
                double operand2 = (double) stack.pop();
                double operand1 = (double) stack.pop();
                double result = SumFunction(c, operand1, operand2);
                stack.push(result); // Push the result back to the stack.
            }
        }

        return (double) stack.pop(); // Final result.
    }

public static double SumFunction(char x, double OpperendOne, double Opperendtwo) {//This method will tell the system what to do with the operands depending on which of the valid characters are discovered
    if (x == '+') {
        return OpperendOne+Opperendtwo;
            }

    else if (x == '-') {
        return OpperendOne-Opperendtwo;
    }
    else if (x == '*') {
        return OpperendOne*Opperendtwo;
    }
    else if (x == '/') {
        return OpperendOne/Opperendtwo;
    }
    else if (x == '^') {
        return Math.pow(OpperendOne,Opperendtwo);
    }
    else{
        return 0;
    }
}


private static boolean ContainsOnly(char c, char[] validChars) {//this is to check if the characters been scanned into the program are all on the whitelist. If not the error message will be produced.
    for (char validChar : validChars) {
        if (c == validChar) {
            return true;
        }
    }
    return false;
}


public static void main(String[] args) {//main which calls the methods in the desired order for this program to run smooth.
    char[] EnteredEquation = QuestionBox();
    String InfixPart= InfixEquation(EnteredEquation);
    double Result =PostfixEvaluation(InfixPart);
    JOptionPane.showMessageDialog(null, "<html>The results of this expression is:<br/><b>Infix Equation:</b> " + String.valueOf(EnteredEquation) + "<br/><b>Postfix Expression:</b> " + InfixPart + "<br/><b>Result:</b> " + Result + "</html>");//output with sum, postfix and the total of the sum when steps compelted

}
}