package bsu.rfct.java.group6.lab3.ponkratov.varA4;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
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
        return from;
    }

    public Double getTo() {
        return to;
    }

    public Double getStep() {
        return step;
    }

    public int getColumnCount() {
        // В данной модели три столбца
        return 3;
    }

    public int getRowCount() {
        // Вычислить количество точек между началом и концом отрезка
        // исходя из шага табулирования
        return (new Double(Math.ceil((to - from) / step))).intValue() + 1;
    }

    public Object getValueAt(int row, int col) {
        // Вычислить значение X как НАЧАЛО_ОТРЕЗКА + ШАГ * НОМЕР_СТРОКИ
        double x = from + step * row;
        if (col == 0) {
            // Если запрашивается значение 1-го столбца, то это X
            return x;
        } else {
            // Если запрашивается значение 2-го столбца, то это значение многочлена
            double result = 0.0;
            for (int i = 0; i < coefficients.length; ++i) {
                result += coefficients[i] * Math.pow(x, (double) (coefficients.length - i - 1));
            }

            if (col == 1) {
                return result;
            } else {
                // Проверка, является ли дробная часть нечётным целым числом
                double fractionalPart = result - Math.floor(result);
                int fractionalAsInteger = (int) Math.round(fractionalPart * 100); // Умножаем на 100 для более точной проверки
                return (fractionalAsInteger % 2 != 0);
            }
        }
    }

    public String getColumnName(int col) {
        switch (col) {
            case 0:
                return "Значение X";
            case 1:
                return "Значение многочлена";
            default:
                return "Дробная часть нечётная";
        }
    }

    public Class<?> getColumnClass(int col) {
        return col == 2 ? Boolean.class : Double.class;
    }
}