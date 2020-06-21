package main.java.calculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Controller {
    @FXML
    public TextField calcInput;

    @FXML
    public void buttonTextField(ActionEvent actionEvent) {
        String text = (((Button) actionEvent.getSource()).getText());
        calcInput.appendText(text);
    }

    @FXML
    public void buttonClear() {
        calcInput.clear();
    }

    @FXML
    public void buttonEquals() {
        String calcInputText = calcInput.getText();
        CalculatorParseTree calculatorParseTree = new CalculatorParseTree(calcInputText);
        calcInput.setText(calculatorParseTree.calculate());
    }
}
