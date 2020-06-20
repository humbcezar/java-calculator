package calculator.cli;

import calculator.CalculatorParseTree;

public class Main {
    public static void main(String[] args) {
        CalculatorParseTree calculatorParseTree = new CalculatorParseTree(args[0]);
        System.out.println(calculatorParseTree);
        System.out.println(calculatorParseTree.calculate());
    }
}
