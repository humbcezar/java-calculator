package calculator;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalculatorParseTree {
    private CalculatorNodeTree root = new CalculatorNodeTree("");
    private CalculatorNodeTree currentNode = root;

    public CalculatorParseTree(String input) {
        build(input);
    }

    private void build(String input) {
        Scanner scanner = new Scanner(parenthesize(input));
        while (scanner.hasNext()) {
            String token = scanner.findInLine("\\(|\\)|\\+|-|/|\\*|\\d+(\\.\\d+)?");
            if (token.matches("\\)")) {
                continue;
            }
            if (token.matches("\\(")) {
                parseParenthesis();
                continue;
            }
            if (token.matches("[+\\-*/]")) {
                parseSymbol(token);
                continue;
            }
            parseNumber(token);
        }
    }

    private void parseParenthesis() {
        CalculatorNodeTree node = new CalculatorNodeTree("");
        node.setParent(currentNode);
        if (currentNode.getLeft() == null || currentNode.getLeft().isEmpty()) {
            currentNode.setLeft(node);
            currentNode = node;
            return;
        }
        if (currentNode.getRight() == null || currentNode.getRight().isEmpty()) {
            currentNode.setRight(node);
            currentNode = node;
        }
    }

    private String parenthesize(String input) {
        Pattern pattern;
        Matcher matcher;

        pattern = Pattern.compile("(?<![(^.\\d]|^-)-?\\d+(\\.\\d+)?([*/]-?\\d+(\\.\\d+)?)+");
        matcher = pattern.matcher(input);
        while (matcher.find()) {
            if (matcher.start() == 0) {
                continue;
            }
            input = matcher.replaceFirst(mr -> "(".concat(mr.group()).concat(")"));
            matcher = pattern.matcher(input);
        }

        pattern = Pattern.compile("(?<!^)([*/])(-\\d+(\\.\\d+)?)");
        matcher = pattern.matcher(input);
        input = matcher.replaceAll(mr -> mr.group(1).concat("(0").concat(mr.group(2)).concat(")"));

        pattern = Pattern.compile("((?<=^)-\\d+(\\.\\d+)?)");
        matcher = pattern.matcher(input);
        input = matcher.replaceFirst(mr -> "0".concat(mr.group()));

        pattern = Pattern.compile("(\\D+)(-)(\\D+)");
        matcher = pattern.matcher(input);
        input = matcher.replaceFirst(mr -> mr.group(1).concat("0-").concat(mr.group(3)));

        return input;
    }

    private void parseSymbol(String symbol) {
        while (currentNode.getParent() != null) {
            currentNode = currentNode.getParent();
            if (currentNode.isEmpty()) {
                currentNode.setValue(symbol);
                return;
            }
        }
        CalculatorNodeTree nodeTree = new CalculatorNodeTree(symbol);
        nodeTree.setLeft(currentNode);
        currentNode = nodeTree;
        root = currentNode;
    }

    private void parseNumber(String number) {
        if (currentNode.getLeft() == null || currentNode.getLeft().isEmpty()) {
            CalculatorNodeTree nodeTree = new CalculatorNodeTree(number);
            nodeTree.setParent(currentNode);
            currentNode.setLeft(nodeTree);
            currentNode = currentNode.getLeft();
            return;
        }
        if (currentNode.getRight() == null || currentNode.getRight().isEmpty()) {
            CalculatorNodeTree nodeTree = new CalculatorNodeTree(number);
            nodeTree.setParent(currentNode);
            currentNode.setRight(nodeTree);
            currentNode = currentNode.getRight();
        }
    }

    @Override
    public String toString() {
        return toStringHelper(root);
    }

    private String toStringHelper(CalculatorNodeTree node) {
        StringBuilder value = new StringBuilder();
        if (node.getLeft() != null) {
            value.append(toStringHelper(node.getLeft()));
        }
        value.append(node.getValue());
        if (node.getRight() != null) {
            value.append(toStringHelper(node.getRight()));
        }
        return value.toString();
    }

    public String calculate() {
        return calculateHelper(root);
    }

    private String calculateHelper(CalculatorNodeTree node) {
        String leftValue = "";
        String rightValue = "";
        if (node.getLeft() != null) {
            leftValue = calculateHelper(node.getLeft());
        }
        if (node.getRight() != null) {
            rightValue = calculateHelper(node.getRight());
        }
        BigDecimal value;
        switch (node.getValue()) {
            case "+":
                value = sum(leftValue, rightValue);
                break;
            case "-":
                value = subtract(leftValue, rightValue);
                break;
            case "*":
                value = multiply(leftValue, rightValue);
                break;
            case "/":
                value = divide(leftValue, rightValue);
                break;
            default:
                value = new BigDecimal(node.getValue(), MathContext.DECIMAL128);
        }
        return value.toString();
    }

    private BigDecimal divide(String leftValue, String rightValue) {
        BigDecimal value = new BigDecimal("1", MathContext.DECIMAL128);
        if (!leftValue.isEmpty()) {
            value = new BigDecimal(leftValue, MathContext.DECIMAL128);
        }
        if (!rightValue.isEmpty()) {
            value = value.divide(new BigDecimal(rightValue, MathContext.DECIMAL128), MathContext.DECIMAL128);
        }
        return value;
    }

    private BigDecimal multiply(String leftValue, String rightValue) {
        BigDecimal value = new BigDecimal("1", MathContext.DECIMAL128);
        if (!leftValue.isEmpty()) {
            value = new BigDecimal(leftValue, MathContext.DECIMAL128);
        }
        if (!rightValue.isEmpty()) {
            value = value.multiply(new BigDecimal(rightValue, MathContext.DECIMAL128));
        }
        return value;
    }

    private BigDecimal subtract(String leftValue, String rightValue) {
        BigDecimal value = new BigDecimal("0", MathContext.DECIMAL128);
        if (!leftValue.isEmpty()) {
            value = new BigDecimal(leftValue, MathContext.DECIMAL128);
        }
        if (!rightValue.isEmpty()) {
            value = value.subtract(new BigDecimal(rightValue, MathContext.DECIMAL128));
        }
        return value;
    }

    private BigDecimal sum(String leftValue, String rightValue) {
        BigDecimal value = new BigDecimal("0", MathContext.DECIMAL128);
        if (!leftValue.isEmpty()) {
            value = value.add(new BigDecimal(leftValue, MathContext.DECIMAL128));
        }
        if (!rightValue.isEmpty()) {
            value = value.add(new BigDecimal(rightValue, MathContext.DECIMAL128));
        }
        return value;
    }
}
