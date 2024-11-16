package bsu.rfct.java.group6.lab3.ponkratov.varA4;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class MainFrame extends JFrame {
    private static final int WIDTH = 700;
    private static final int HEIGHT = 500;
    private Double[] coefficients;
    private JFileChooser fileChooser = null;
    private JMenuItem saveToTextMenuItem;
    private JMenuItem saveToGraphicsMenuItem;
    private JMenuItem searchValueMenuItem;
    private JMenuItem aboutMenuItem;
    private JTextField textFieldFrom;
    private JTextField textFieldTo;
    private JTextField textFieldStep;
    private Box hBoxResult;
    private GornerTableCellRenderer renderer = new GornerTableCellRenderer();
    private GornerTableModel data;

    public MainFrame(Double[] coefficients) {
        this.coefficients = coefficients;
        this.setSize(700, 500);
        Toolkit kit = Toolkit.getDefaultToolkit();
        this.setLocation((kit.getScreenSize().width - 700) / 2, (kit.getScreenSize().height - 500) / 2);
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("Файл");
        menuBar.add(fileMenu);
        JMenu tableMenu = new JMenu("Таблица");
        menuBar.add(tableMenu);
        JMenu aboutMenu = new JMenu("Справка");
        menuBar.add(aboutMenu);
        Action aboutInfoAction = new AbstractAction("О программе") {
            public void actionPerformed(ActionEvent event) {
                JOptionPane.showMessageDialog(MainFrame.this, "Понкратов Алексей, 6 группа", "О программе", 1);
            }
        };
        this.aboutMenuItem = aboutMenu.add(aboutInfoAction);
        Action saveToTextAction = new AbstractAction("Сохранить в текстовый файл") {
            public void actionPerformed(ActionEvent event) {
                if (MainFrame.this.fileChooser == null) {
                    MainFrame.this.fileChooser = new JFileChooser();
                    MainFrame.this.fileChooser.setCurrentDirectory(new File("."));
                }

                if (MainFrame.this.fileChooser.showSaveDialog(MainFrame.this) == 0) {
                    MainFrame.this.saveToTextFile(MainFrame.this.fileChooser.getSelectedFile());
                }

            }
        };
        this.saveToTextMenuItem = fileMenu.add(saveToTextAction);
        this.saveToTextMenuItem.setEnabled(false);
        Action saveToGraphicsAction = new AbstractAction("Сохранить данные для построения графика") {
            public void actionPerformed(ActionEvent event) {
                if (MainFrame.this.fileChooser == null) {
                    MainFrame.this.fileChooser = new JFileChooser();
                    MainFrame.this.fileChooser.setCurrentDirectory(new File("."));
                }

                if (MainFrame.this.fileChooser.showSaveDialog(MainFrame.this) == 0) {
                    MainFrame.this.saveToGraphicsFile(MainFrame.this.fileChooser.getSelectedFile());
                }

            }
        };
        this.saveToGraphicsMenuItem = fileMenu.add(saveToGraphicsAction);
        this.saveToGraphicsMenuItem.setEnabled(false);
        Action searchValueAction = new AbstractAction("Найти значение многочлена") {
            public void actionPerformed(ActionEvent event) {
                String value = JOptionPane.showInputDialog(MainFrame.this, "Введите значение для поиска", "Поиск значения", 3);
                MainFrame.this.renderer.setNeedle(value);
                MainFrame.this.getContentPane().repaint();
            }
        };
        this.searchValueMenuItem = tableMenu.add(searchValueAction);
        this.searchValueMenuItem.setEnabled(false);
        JLabel labelForFrom = new JLabel("X изменяется на интервале от:");
        this.textFieldFrom = new JTextField("0.0", 10);
        this.textFieldFrom.setMaximumSize(this.textFieldFrom.getPreferredSize());
        JLabel labelForTo = new JLabel("до:");
        this.textFieldTo = new JTextField("1.0", 10);
        this.textFieldTo.setMaximumSize(this.textFieldTo.getPreferredSize());
        JLabel labelForStep = new JLabel("с шагом:");
        this.textFieldStep = new JTextField("0.1", 10);
        this.textFieldStep.setMaximumSize(this.textFieldStep.getPreferredSize());
        Box hboxRange = Box.createHorizontalBox();
        hboxRange.setBorder(BorderFactory.createBevelBorder(1));
        hboxRange.add(Box.createHorizontalGlue());
        hboxRange.add(labelForFrom);
        hboxRange.add(Box.createHorizontalStrut(10));
        hboxRange.add(this.textFieldFrom);
        hboxRange.add(Box.createHorizontalStrut(20));
        hboxRange.add(labelForTo);
        hboxRange.add(Box.createHorizontalStrut(10));
        hboxRange.add(this.textFieldTo);
        hboxRange.add(Box.createHorizontalStrut(20));
        hboxRange.add(labelForStep);
        hboxRange.add(Box.createHorizontalStrut(10));
        hboxRange.add(this.textFieldStep);
        hboxRange.add(Box.createHorizontalGlue());
        hboxRange.setPreferredSize(new Dimension((new Double(hboxRange.getMaximumSize().getWidth())).intValue(), (new Double(hboxRange.getMinimumSize().getHeight())).intValue() * 2));
        this.getContentPane().add(hboxRange, "North");
        JButton buttonCalc = new JButton("Вычислить");
        buttonCalc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {
                    Double from = Double.parseDouble(MainFrame.this.textFieldFrom.getText());
                    Double to = Double.parseDouble(MainFrame.this.textFieldTo.getText());
                    Double step = Double.parseDouble(MainFrame.this.textFieldStep.getText());
                    MainFrame.this.data = new GornerTableModel(from, to, step, MainFrame.this.coefficients);
                    JTable table = new JTable(MainFrame.this.data);
                    table.setDefaultRenderer(Double.class, MainFrame.this.renderer);
                    table.setRowHeight(30);
                    MainFrame.this.hBoxResult.removeAll();
                    MainFrame.this.hBoxResult.add(new JScrollPane(table));
                    MainFrame.this.getContentPane().validate();
                    MainFrame.this.saveToTextMenuItem.setEnabled(true);
                    MainFrame.this.saveToGraphicsMenuItem.setEnabled(true);
                    MainFrame.this.searchValueMenuItem.setEnabled(true);
                } catch (NumberFormatException var6) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Ошибка в формате записи числа с плавающей точкой", "Ошибочный формат числа", 2);
                }

            }
        });
        JButton buttonReset = new JButton("Очистить поля");
        buttonReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                MainFrame.this.textFieldFrom.setText("0.0");
                MainFrame.this.textFieldTo.setText("1.0");
                MainFrame.this.textFieldStep.setText("0.1");
                MainFrame.this.hBoxResult.removeAll();
                MainFrame.this.hBoxResult.add(new JPanel());
                MainFrame.this.saveToTextMenuItem.setEnabled(false);
                MainFrame.this.saveToGraphicsMenuItem.setEnabled(false);
                MainFrame.this.searchValueMenuItem.setEnabled(false);
                MainFrame.this.getContentPane().validate();
            }
        });
        Box hboxButtons = Box.createHorizontalBox();
        hboxButtons.setBorder(BorderFactory.createBevelBorder(1));
        hboxButtons.add(Box.createHorizontalGlue());
        hboxButtons.add(buttonCalc);
        hboxButtons.add(Box.createHorizontalStrut(30));
        hboxButtons.add(buttonReset);
        hboxButtons.add(Box.createHorizontalGlue());
        hboxButtons.setPreferredSize(new Dimension((new Double(hboxButtons.getMaximumSize().getWidth())).intValue(), (new Double(hboxButtons.getMinimumSize().getHeight())).intValue() * 2));
        this.getContentPane().add(hboxButtons, "South");
        this.hBoxResult = Box.createHorizontalBox();
        this.hBoxResult.add(new JPanel());
        this.getContentPane().add(this.hBoxResult, "Center");
    }

    protected void saveToGraphicsFile(File selectedFile) {
        try {
            DataOutputStream out = new DataOutputStream(new FileOutputStream(selectedFile));

            for(int i = 0; i < this.data.getRowCount(); ++i) {
                out.writeDouble((Double)this.data.getValueAt(i, 0));
                out.writeDouble((Double)this.data.getValueAt(i, 1));
            }

            out.close();
        } catch (Exception var4) {
        }

    }

    protected void saveToTextFile(File selectedFile) {
        try {
            PrintStream out = new PrintStream(selectedFile);
            out.print("Многочлен: ");

            Double var10001;
            int i;
            for(i = 0; i < this.coefficients.length; ++i) {
                var10001 = this.coefficients[i];
                out.print("" + var10001 + "*X^" + (this.coefficients.length - i - 1));
                if (i != this.coefficients.length - 1) {
                    out.print(" + ");
                }
            }

            out.println("");
            var10001 = this.data.getFrom();
            out.println("Интервал от " + var10001 + " до " + this.data.getTo() + " с шагом " + this.data.getStep());
            out.println("====================================================");

            for(i = 0; i < this.data.getRowCount(); ++i) {
                String var5 = String.valueOf(this.data.getValueAt(i, 0));
                out.println("Значение в точке " + var5 + " равно " + String.valueOf(this.data.getValueAt(i, 1)));
            }

            out.close();
        } catch (FileNotFoundException var4) {
        }

    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Невозможно табулировать многочлен, для которого не задано ни одного коэффициента!");
            System.exit(-1);
        }

        Double[] coefficients = new Double[args.length];
        int i = 0;

        try {
            String[] var3 = args;
            int var4 = args.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String arg = var3[var5];
                coefficients[i++] = Double.parseDouble(arg);
            }
        } catch (NumberFormatException var7) {
            System.out.println("Ошибка преобразования строки '" + args[i] + "' в число типа Double");
            System.exit(-2);
        }

        MainFrame frame = new MainFrame(coefficients);
        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);
    }
}
