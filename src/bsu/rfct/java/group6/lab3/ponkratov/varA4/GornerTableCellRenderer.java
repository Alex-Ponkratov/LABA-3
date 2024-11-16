package bsu.rfct.java.group6.lab3.ponkratov.varA4;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class GornerTableCellRenderer implements TableCellRenderer {
    private JPanel panel = new JPanel();
    private JLabel label = new JLabel();
    private String needle = null;
    private DecimalFormat formatter = (DecimalFormat)NumberFormat.getInstance();

    public GornerTableCellRenderer() {
        this.formatter.setMaximumFractionDigits(5);
        this.formatter.setGroupingUsed(false);
        DecimalFormatSymbols dottedDouble = this.formatter.getDecimalFormatSymbols();
        dottedDouble.setDecimalSeparator('.');
        this.formatter.setDecimalFormatSymbols(dottedDouble);
        this.panel.add(this.label);
        this.panel.setLayout(new FlowLayout(0));
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
        String formattedDouble = this.formatter.format(value);
        this.label.setText(formattedDouble);
        if (col == 1 && this.needle != null && this.needle.equals(formattedDouble)) {
            this.panel.setBackground(Color.RED);
        } else {
            this.panel.setBackground(Color.WHITE);
        }

        return this.panel;
    }

    public void setNeedle(String needle) {
        this.needle = needle;
    }
}

