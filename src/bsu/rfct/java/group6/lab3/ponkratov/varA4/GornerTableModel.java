package bsu.rfct.java.group6.lab3.ponkratov.varA4;


import javax.swing.table.AbstractTableModel;

public class GornerTableModel extends AbstractTableModel {
    private Double[] coefficients;
    private Double from;
    private Double to;
    private Double step;

    public GornerTableModel(Double from, Double to, Double step, Double[] coefficients) {
        this.from = from;
        this.to = to;
        this.step = step;
        this.coefficients = coefficients;
    }

    public Double getFrom() {
        return this.from;
    }

    public Double getTo() {
        return this.to;
    }

    public Double getStep() {
        return this.step;
    }

    public int getColumnCount() {
        return 3;
    }

    public int getRowCount() {
        return (new Double(Math.ceil((this.to - this.from) / this.step))).intValue() + 1;
    }

    public Object getValueAt(int row, int col) {
        double x = this.from + this.step * (double)row;
        if (col == 0) {
            return x;
        } else {
            Double result;
            int i;
            if (col == 1) {
                result = 0.0;

                for(i = 0; i < this.coefficients.length; ++i) {
                    result = result + this.coefficients[i] * Math.pow(x, (double)(this.coefficients.length - i - 1));
                }

                return result;
            } else {
                result = 0.0;

                for(i = 0; i < this.coefficients.length; ++i) {
                    result = result + this.coefficients[i] * Math.pow(x, (double)(this.coefficients.length - i - 1));
                }

                return Math.floor(result) == 0.0;
            }
        }
    }

    public String getColumnName(int col) {
        switch (col) {
            case 0 -> {
                return "Значение X";
            }
            case 1 -> {
                return "Значение многочлена";
            }
            default -> {
                return "Малое число?";
            }
        }
    }

    public Class<?> getColumnClass(int col) {
        return col == 2 ? Boolean.class : Double.class;
    }
}

