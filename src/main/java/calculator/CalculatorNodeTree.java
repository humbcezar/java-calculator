package main.java.calculator;

public class CalculatorNodeTree {
    private CalculatorNodeTree left;
    private CalculatorNodeTree right;
    private CalculatorNodeTree parent;
    private String value;

    public CalculatorNodeTree(String value) {
        this.value = value;
    }

    public boolean isEmpty() {
        return value.isEmpty();
    }

    public CalculatorNodeTree getLeft() {
        return left;
    }

    public void setLeft(CalculatorNodeTree left) {
        this.left = left;
    }

    public CalculatorNodeTree getRight() {
        return right;
    }

    public void setRight(CalculatorNodeTree right) {
        this.right = right;
    }

    public CalculatorNodeTree getParent() {
        return parent;
    }

    public void setParent(CalculatorNodeTree parent) {
        this.parent = parent;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
