package hr.fer.zemris.java.gui.calc;

import java.awt.Container;
import java.util.EmptyStackException;
import java.util.Stack;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.components.Display;
import hr.fer.zemris.java.gui.calc.components.NumberButton;
import hr.fer.zemris.java.gui.calc.components.OperationButton;
import hr.fer.zemris.java.gui.calc.components.OperationButton.Invert;
import hr.fer.zemris.java.gui.calc.components.OperationButton.Press;
import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;
import hr.fer.zemris.java.gui.layouts.CalcLayout;

/**
 * Klasa koja predstavlja kalkulator.
 */
public class Calculator extends JFrame {

    /**
     * Funkcija iz koje se pokreće program.
     * 
     * @param args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Calculator().setVisible(true);
        });
    }

    Stack<Double> stack = new Stack<>();
    boolean inverted = false;

    CalcModelImpl model = new CalcModelImpl();

    /**
     * Funkcija koja inicializira kalulator.
     * 
     * @param cp
     */
    public Calculator() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        initGUI();

        pack();
    }

    /**
     * Funkcija koja inicializira GUI.
     * 
     * @param cp
     */
    private void initGUI() {
        Container cp = getContentPane();
        cp.setLayout(new CalcLayout(3));

        final var disp = new Display();

        cp.add(disp, "1,1");

        model.addCalcValueListener(
                e -> disp.setText(model.toString()));

        // inserting buttons

        addNumbers(cp);
        addOperations(cp);
        addInvertCheckbox(cp);
    }

    /**
     * Dodaje invert checkbox.
     * 
     * @param cp
     */
    private void addInvertCheckbox(Container cp) {
        JCheckBox invert = new JCheckBox("Inv");

        invert.addActionListener(e -> {
            inverted = !inverted;

            invertAll();
        });

        cp.add(invert, "5,7");
    }

    /**
     * Dodaje brojeve.
     * 
     * @param cp
     */
    private void addNumbers(Container cp) {
        String[] locations = { "5,3",
                "4,3", "4,4", "4,5",
                "3,3", "3,4", "3,5",
                "2,3", "2,4", "2,5" };

        for (int i = 0; i < 10; i++) {
            final int j = i;

            cp.add(new NumberButton(Integer.toString(i), () -> {
                try {
                    model.insertDigit(j);
                } catch (Exception e) {
                    System.out.println(e.getMessage());

                    // if number can be edite right after, use
                    // model.clear();
                    // model.insertDigit(j);
                }

            }), locations[i]);
        }
    }

    private Invert[] invertors;

    /**
     * Doda operacije.
     * 
     * @param cp
     */
    private void addOperations(Container cp) {

        String[] locations = { "2,1", "2,2", "3,1", "3,2", "4,1", "4,2", "5,1",
                "5,2", "1,6", "1,7", "2,6", "2,7", "3,6", "3,7", "4,6", "4,7", "5,6", "5,5", "5,4" };

        String[] text = { "1/x", "sin", "log", "cos", "ln", "tan", "x^n",
                "ctg", "=", "clr", "/", "reset", "*", "push", "-", "pop", "+", ".", "+-" };

        String[] textInv = { "1/x", "arcsin", "10^x", "arccos", "e^x", "arctan",
                "x^(1/n)", "arcctg", "=", "clr", "/", "reset", "*", "push", "-", "pop", "+", ".", "+-" };

        final Press[] operations = {
                () -> { // 1/x
                    checkFrozenValue();

                    // if (inverted) does nothing
                    model.setValue(1 / model.getValue());
                },
                () -> { // sin
                    checkFrozenValue();

                    if (inverted) {
                        model.setValue(Math.asin(model.getValue()));
                    } else {
                        model.setValue(Math.sin(model.getValue()));
                    }
                },
                () -> { // log
                    checkFrozenValue();

                    if (inverted) {
                        model.setValue(Math.pow(10, model.getValue()));
                    } else {
                        model.setValue(Math.log10(model.getValue()));
                    }
                },
                () -> { // cos
                    checkFrozenValue();

                    if (inverted) {
                        model.setValue(Math.acos(model.getValue()));
                    } else {
                        model.setValue(Math.cos(model.getValue()));
                    }
                },
                () -> { // ln
                    checkFrozenValue();

                    if (inverted) {
                        model.setValue(Math.exp(model.getValue()));
                    } else {
                        model.setValue(Math.log(model.getValue()));
                    }
                },
                () -> { // tan
                    checkFrozenValue();

                    if (inverted) {
                        model.setValue(Math.atan(model.getValue()));
                    } else {
                        model.setValue(Math.tan(model.getValue()));
                    }
                },
                () -> { // x^n
                    checkFrozenValue();

                    if (inverted) {
                        model.setPendingBinaryOperation((a, b) -> Math.pow(a, 1 / b));
                    } else {
                        model.setPendingBinaryOperation((a, b) -> Math.pow(a, b));
                    }
                },
                () -> { // ctg
                    checkFrozenValue();

                    if (inverted) {
                        model.setValue(Math.PI / 2 - Math.atan(model.getValue()));
                    } else {
                        model.setValue(1 / Math.tan(model.getValue()));
                    }
                },
                () -> { // =
                    checkFrozenValue();

                    model.setValue(model.getPendingBinaryOperation().applyAsDouble(
                            model.getActiveOperand(), model.getValue()));

                    // model.clearActiveOperand(); // might not be needed
                },
                () -> { // clr
                    model.clear();
                },
                () -> { // /
                    model.setPendingBinaryOperation((a, b) -> a / b);
                },
                () -> { // reset
                    model.clearAll();
                },
                () -> { // *
                    model.setPendingBinaryOperation((a, b) -> a * b);
                },
                () -> { // push
                    stack.push(model.getValue());

                    model.clear(); // might not be needed
                },
                () -> { // -
                    model.setPendingBinaryOperation((a, b) -> a - b);
                },
                () -> { // pop

                    try {
                        model.setValue(stack.pop());
                    } catch (EmptyStackException e) {
                        System.out.println("Stack is empty");
                    }

                    model.clearActiveOperand(); // might not be needed
                },
                () -> { // +
                    model.setPendingBinaryOperation((a, b) -> a + b);
                },
                () -> { // .
                    checkFrozenValue();

                    model.insertDecimalPoint();
                },
                () -> { // +-
                    checkFrozenValue();

                    model.swapSign();
                }
        };

        invertors = new Invert[operations.length];

        for (int i = 0; i < locations.length; i++) {
            final int j = i;

            var btn = new OperationButton(text[i], textInv[i], () -> {
                try {
                    operations[j].pressed();
                } catch (RuntimeException e) {
                    System.out.println(e.getMessage());
                }
            });

            cp.add(btn, locations[i]);

            invertors[i] = btn.getInvertor();
        }
    }

    /**
     * Invertira sve operacije.
     */
    private final void invertAll() {
        for (var invertor : invertors) {
            invertor.invert(this.inverted);
        }
    }

    /**
     * Provjerava je li vrijednost zamrznuta.
     */
    private void checkFrozenValue() {
        if (model.getFrozenValue()) {
            throw new RuntimeException("Frozen value");
        }
    }
}
