package hr.fer.zemris.java.gui.calc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

/**
 * Implementacija CalcModela.
 */
public class CalcModelImpl implements CalcModel {

    boolean isEditable = true;
    boolean isNegative = false;
    String inputtedString = "";
    double value = 0;

    String frozenValue = null;

    DoubleBinaryOperator pendingOperation = null;
    Double activeOperand = null;

    List<CalcValueListener> listeners = new ArrayList<>();

    /**
     * Vraca je li kalkulator smrznut.
     * 
     * @return boolean
     */
    public boolean getFrozenValue() {
        return frozenValue != null;
    }

    /**
     * Dodaje listenera.
     * 
     * @param l
     */
    @Override
    public void addCalcValueListener(CalcValueListener l) {
        listeners.add(l);
    }

    /**
     * Uklanja listenera.
     * 
     * @param l
     */
    @Override
    public void removeCalcValueListener(CalcValueListener l) {
        listeners.remove(l);
    }

    private void notifyListeners() {
        for (CalcValueListener l : listeners) {
            l.valueChanged(this);
        }
    }

    /**
     * Vraća vrijednost kalkulatora.
     * 
     * @return double
     */
    @Override
    public double getValue() {
        return (isNegative ? -1 : 1) * value;
    }

    /**
     * Postavlja vrijednost kalkulatora.
     * 
     * @param value
     */
    @Override
    public void setValue(double value) {
        this.isEditable = false;
        this.isNegative = value < 0;
        this.value = Math.abs(value);
        this.inputtedString = Double.toString(this.value);
        this.frozenValue = null;

        notifyListeners();
    }

    /**
     * Vraca je li kalkulator editable.
     * 
     * @return boolean
     */
    @Override
    public boolean isEditable() {
        return isEditable;
    }

    /**
     * Brise trenutni operand.
     */
    @Override
    public void clear() {
        isEditable = true;
        isNegative = false;
        inputtedString = "";
        value = 0;

        notifyListeners();
    }

    /**
     * Brise svo stanje.
     */
    @Override
    public void clearAll() {
        clearActiveOperand();
        clear();

        this.pendingOperation = null;
        this.frozenValue = null;

        notifyListeners();
    }

    /**
     * Zamjenjuje predznak vrijednosti.
     * 
     * @throws CalculatorInputException
     */
    @Override
    public void swapSign() throws CalculatorInputException {
        if (!isEditable) {
            throw new CalculatorInputException("Calculator is not editable");
        }

        isNegative = !isNegative;
        frozenValue = null;

        notifyListeners();
    }

    /**
     * Umece decimalnu tocku.
     * 
     * @throws CalculatorInputException
     */
    @Override
    public void insertDecimalPoint() throws CalculatorInputException {
        if (!isEditable) {
            throw new CalculatorInputException("Calculator is not editable");
        }

        if (inputtedString.isEmpty()) {
            throw new CalculatorInputException("Inputted string is empty");
        }

        if (inputtedString.contains(".")) {
            throw new CalculatorInputException("Already contains decimal point");
        }

        String newInputtedString = inputtedString + ".";

        try {
            value = Double.parseDouble(newInputtedString);
            inputtedString = newInputtedString;
            frozenValue = null;

        } catch (NumberFormatException e) {
            throw new CalculatorInputException("Inputted string is not parseable");
        }

        notifyListeners();
    }

    /**
     * Umece znamenku na aktivni operand.
     * 
     * @param digit
     * @throws CalculatorInputException
     * @throws IllegalArgumentException
     */
    @Override
    public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
        if (!isEditable) {
            throw new CalculatorInputException("Calculator is not editable");
        }

        if (digit < 0 || digit > 9) {
            throw new IllegalArgumentException("Digit must be between 0 and 9");
        }

        if (digit == 0 && inputtedString.equals("0")) {
            return;
        }

        if (digit != 0 && inputtedString.equals("0")) {
            inputtedString = "";
        }

        String newInputtedString = inputtedString + digit;

        try {
            value = Double.parseDouble(newInputtedString);

            if (value == Double.POSITIVE_INFINITY || value == Double.NEGATIVE_INFINITY) {
                throw new CalculatorInputException("Inputted string is not parseable");
            }

            inputtedString = newInputtedString;
            frozenValue = null;

        } catch (NumberFormatException e) {
            throw new CalculatorInputException("Inputted string is not parseable");
        }

        notifyListeners();
    }

    /**
     * Vraca je li postavljen aktivni operand.
     * 
     * @return boolean
     */
    @Override
    public boolean isActiveOperandSet() {
        return activeOperand != null;
    }

    /**
     * Vraca aktivni operand.
     * 
     * @return double
     * @throws IllegalStateException
     */
    @Override
    public double getActiveOperand() throws IllegalStateException {
        if (!isActiveOperandSet()) {
            throw new IllegalStateException("Active operand is not set");
        }

        return activeOperand;
    }

    /**
     * Postavlja aktivni operand.
     * 
     * @param activeOperand
     */
    @Override
    public void setActiveOperand(double activeOperand) {
        this.activeOperand = activeOperand;
    }

    /**
     * Brise aktivni operand.
     */
    @Override
    public void clearActiveOperand() {
        activeOperand = null;
    }

    /**
     * Vraca pending binarnu operaciju.
     * 
     * @return DoubleBinaryOperator
     */
    @Override
    public DoubleBinaryOperator getPendingBinaryOperation() {
        if (pendingOperation == null) {
            throw new IllegalStateException("Pending operation is not set");
        }

        return pendingOperation;
    }

    /**
     * Postavlja pending binarnu operaciju.
     * 
     * @param op
     */
    @Override
    public void setPendingBinaryOperation(DoubleBinaryOperator op) {
        pendingOperation = op;

        if (op != null) {

            activeOperand = getValue();
            clear();
            frozenValue = toString();

        } else {
            frozenValue = null;
        }

        notifyListeners();
    }

    /**
     * Vraca string reprezentaciju vrijednosti kalulatora.
     * 
     * @return String
     */
    @Override
    public String toString() {
        if (frozenValue != null) {
            return frozenValue;
        }

        if (inputtedString.isEmpty()) {
            return (isNegative ? "-" : "") + "0";
        }

        if (value == Double.POSITIVE_INFINITY || value == Double.NEGATIVE_INFINITY) {
            return (isNegative ? "-" : "") + "Infinity";
        }

        if (value == Double.NaN) {
            return (isNegative ? "-" : "") + "NaN";
        }

        if (inputtedString.isEmpty() || inputtedString.equals("0")) {
            return (isNegative ? "-" : "") + "0";
        }

        return (isNegative ? "-" : "") + inputtedString;
    }

}
