package calculator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorParseTreeTest {

    private static Stream<Arguments> calculateProvider() {
        return Stream.of(
                Arguments.of("10", "5+5"),
                Arguments.of("11288", "332*34"),
                Arguments.of("102", "3*34"),
                Arguments.of("5", "15/3"),
                Arguments.of("12", "15-3"),
                Arguments.of("13.0824507019394627931510184208390813", "3.2*23.2/123.23+3.2*3.9"),
                Arguments.of("37.84894651539708265802269043760130", "5+6*3-4-3/5+8*3/1.234"),
                Arguments.of("37.84894651539708265802269043760130", "5+6*3-4-3/5+8*-3/-1.234"),
                Arguments.of("-1.04894651539708265802269043760130", "5+6*3-4-3/5+8*3/-1.234)"),
                Arguments.of("-10", "-5-5"),
                Arguments.of("-2.5", "5*-0.5"),
                Arguments.of("-2.5", "-0.5*5"),
                Arguments.of("-25", "-5*5"),
                Arguments.of("-25", "5*-5"),
                Arguments.of("25", "-5*-5"),
                Arguments.of("-1", "-5/5"),
                Arguments.of("-1", "5/-5"),
                Arguments.of("1", "-5/-5"),
                Arguments.of("0.3333333333333333333333333333333333", "5/5/3"),
                Arguments.of("-0.3333333333333333333333333333333333", "5/-5/3"),
                Arguments.of("-0.3333333333333333333333333333333333", "5/5/-3"),
                Arguments.of("-2.4999999999999999999999999999999999", "5/6*-3")
        );
    }

    @ParameterizedTest
    @MethodSource("calculateProvider")
    void calculate(String expected, String input) {
        CalculatorParseTree calculatorParseTree = new CalculatorParseTree(input);
        assertEquals(expected, calculatorParseTree.calculate());
    }
}