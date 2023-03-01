package ethiopiancalendar;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.time.LocalDate;
import java.time.Month;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class EthiopianCalendar extends JFrame {

    static int day, month, year;
    static int currentday, currentmonth, currentyear;
    static String currentmonthname;
    static JButton next, previous, getname;
    static JLabel[] namelabel = new JLabel[7];
    static JButton[][] daysbutton = new JButton[6][7];
    static String[][] holiday = new String[31][14];
    static JLabel date;
    static JPanel p1, p2, p3, p4, p5, tp7;
    static JFrame frame;
    static JTextField nameday, namemonth, nameyear;

    EthiopianCalendar() {

        for (int i = 0; i <= 30; i++) {
            for (int j = 0; j <= 13; j++) {
                holiday[i][j] = "";
            }
        }
        setHoliday();

        LocalDate currentdate = LocalDate.now();

        day = currentdate.getDayOfMonth();
        Month setmonth = currentdate.getMonth();
        currentmonthname = setmonth.toString();
        month = indexOfMonth(currentmonthname);
        year = currentdate.getYear();

        currentday = day;
        currentmonth = month;
        currentyear = year;

        namelabel[0] = new JLabel("  Monday");
        namelabel[1] = new JLabel("  Tuesday");
        namelabel[2] = new JLabel("  Wendsday");
        namelabel[3] = new JLabel("  Thursday");
        namelabel[4] = new JLabel("  Friday");
        namelabel[5] = new JLabel("  Saturday");
        namelabel[6] = new JLabel("  Sunday");

        p1 = new JPanel();
        p1.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
        next = new JButton(">>");
        next.setFont(new Font("Serif", 3, 25));
        previous = new JButton("<<");
        previous.setFont(new Font("Serif", 3, 25));
        date = new JLabel("date");
        date.setFont(new Font("Serif", 2, 30));

        p1.add(date);

        p2 = new JPanel(new GridLayout(1, 7));
        p3 = new JPanel(new GridLayout(6, 7));
        p3.setBackground(Color.WHITE);
        for (int i = 0; i < 7; i++) {
            p2.add(namelabel[i]);
            namelabel[i].setFont(new Font("Serif", 1, 15));
        }

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                daysbutton[i][j] = new JButton("");
                daysbutton[i][j].setBackground(Color.WHITE);
                daysbutton[i][j].setVisible(false);
                p3.add(daysbutton[i][j]);
            }
        }

        p4 = new JPanel(new BorderLayout());

        p4.add(p2, BorderLayout.NORTH);
        p4.add(p3, BorderLayout.CENTER);

        p5 = new JPanel(new BorderLayout());

        p5.add(previous, BorderLayout.WEST);
        p5.add(p4, BorderLayout.CENTER);
        p5.add(next, BorderLayout.EAST);

        tp7 = new JPanel(new GridLayout(4, 2));
        tp7.setBorder(new TitledBorder("Get day name Using Ethiopian Calendar"));
        tp7.add(new JLabel("Day"));
        nameday = new JTextField(10);
        tp7.add(nameday);

        tp7.add(new JLabel("Month "));
        namemonth = new JTextField(10);
        tp7.add(namemonth);

        tp7.add(new JLabel("Year "));
        nameyear = new JTextField(10);
        tp7.add(nameyear);

        tp7.add(new JLabel(" "));
        getname = new JButton("get name");
        getname.setCursor(new Cursor(Cursor.HAND_CURSOR));
        tp7.add(getname);

        add(p1, BorderLayout.NORTH);
        add(p5, BorderLayout.CENTER);
        add(tp7, BorderLayout.SOUTH);

        if (month < 9) {
            year -= 8;
        } else {
            year -= 7;
        }

        month = indexOfMonth(month);
        getCalendar(1, month, year);

        ButtonListener handler = new ButtonListener();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                daysbutton[i][j].addActionListener(handler);
            }
        }

        previous.addActionListener((ActionEvent e) -> {
            previousMonth();
        });

        next.addActionListener((ActionEvent e) -> {
            nextMonth();
        });
        getname.addActionListener((ActionEvent e) -> {
            getEName();
        });

    }

    public static void setHoliday() {
        holiday[1][1] = "New Year";
        holiday[17][1] = "Meskel";
        holiday[29][4] = "Gena";
        holiday[11][5] = "Tmket";
        holiday[23][6] = "Adwa";
        holiday[20][9] = "Gnbot 20";
    }

    class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) {
                    if (event.getSource() == daysbutton[i][j]) {
                        int d = Integer.parseInt(daysbutton[i][j].getText());
                        String string = showG(getEName(d, month, year));
                        if (month < 10) {
                            JOptionPane.showMessageDialog(null, string + "\n" + d + "  0" + month + "  " + year + "\n" + holiday[d][month]);
                        } else {
                            JOptionPane.showMessageDialog(null, string + "\n" + d + "  " + month + "  " + year + "\n" + holiday[d][month]);
                        }
                    }
                }
            }
        }
    }

    public static void getCalendar(int d, int m, int y) {
        int index = getEName(d, m, y);
        int firstday = 1;
        if (m < 10) {
            date.setText("0" + month + " - " + year);
        } else {
            date.setText(month + " - " + year);
        }
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                if (i == 0 && (index + 1) % 7 > j) {
                    continue;
                }
                daysbutton[i][j].setVisible(true);
                daysbutton[i][j].setText("" + firstday);

                checkForHoliday(i, j, firstday, month, year);
                firstday++;

                if (firstday > getNumberOfDay(m, y)) {
                    return;
                }
            }
        }

    }

    public static void main(String[] args) {

        frame = new EthiopianCalendar();

        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Calendar");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    public static void getEName() {

        if (nameday.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "please enter day", "Warning", JOptionPane.ERROR_MESSAGE);
            nameday.requestFocusInWindow();
        } else if (namemonth.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "please enter month", "Warning", JOptionPane.ERROR_MESSAGE);
            namemonth.requestFocusInWindow();
        } else if (nameyear.getText().length() == 0) {
            JOptionPane.showMessageDialog(null, "please enter year", "Warrning", JOptionPane.ERROR_MESSAGE);
            nameyear.requestFocusInWindow();
        } else {
            try {

                int d, m, y, z;

                d = Integer.parseInt(nameday.getText());
                m = Integer.parseInt(namemonth.getText());
                y = Integer.parseInt(nameyear.getText());

                z = y + 1;
                if (y < 1) {
                    JOptionPane.showMessageDialog(null, "invalid year", "Warrning", JOptionPane.ERROR_MESSAGE);
                    nameyear.requestFocusInWindow();
                } else if ((z % 400 == 0) || (z % 4 == 0 && z % 100 != 0)) {
                    if (m > 0 && m < 13) {
                        if (d > 0 && d < 31) {
                            JOptionPane.showMessageDialog(null, showG(getEName(d, m, y)), "", JOptionPane.INFORMATION_MESSAGE);
                            clearField();
                        } else {
                            JOptionPane.showMessageDialog(null, "Invalid day", "Warrning", JOptionPane.ERROR_MESSAGE);
                            nameday.requestFocusInWindow();
                        }
                    } else if (m == 13) {
                        if (d > 0 && d < 7) {
                            JOptionPane.showMessageDialog(null, showG(getEName(d, m, y)), "", JOptionPane.INFORMATION_MESSAGE);
                            clearField();
                        } else {
                            JOptionPane.showMessageDialog(null, "invalid day", "Warrning", JOptionPane.ERROR_MESSAGE);
                            nameday.requestFocusInWindow();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "invalid month", "Warrning", JOptionPane.ERROR_MESSAGE);
                        namemonth.requestFocusInWindow();
                    }
                } else {
                    if (m > 0 && m < 13) {
                        if (d > 0 && d < 31) {
                            JOptionPane.showMessageDialog(null, showG(getEName(d, m, y)), "", JOptionPane.INFORMATION_MESSAGE);
                            clearField();
                        } else {
                            JOptionPane.showMessageDialog(null, "invalid day", "Warrning", JOptionPane.ERROR_MESSAGE);
                            nameday.requestFocusInWindow();
                        }
                    } else if (m == 13) {
                        if (d > 0 && d < 6) {
                            JOptionPane.showMessageDialog(null, showG(getEName(d, m, y)), "", JOptionPane.INFORMATION_MESSAGE);
                            clearField();
                        } else {
                            JOptionPane.showMessageDialog(null, "invalid day", "Warrning", JOptionPane.ERROR_MESSAGE);
                            nameday.requestFocusInWindow();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "invalid Month", "Warrning", JOptionPane.ERROR_MESSAGE);
                        namemonth.requestFocusInWindow();
                    }
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "invalid input", "Warrning", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static int getEName(int d, int m, int y) {
        y--;
        y += (m - 1) * 30 + d + (y / 4);
        return (y % 7);
    }

    public static String showG(int c) {
        String dayname = "";
        switch (c) {
            case 6:
                dayname = "MONDAY";
                break;
            case 0:
                dayname = "TUESDAY";
                break;
            case 1:
                dayname = "WEDNESDAY";
                break;
            case 2:
                dayname = "THURSDAY";
                break;
            case 3:
                dayname = "FRIDAY";
                break;
            case 4:
                dayname = "SATURDAY";
                break;
            case 5:
                dayname = "SUNDAY";
                break;
        }

        return dayname;
    }

    public static int indexOfMonth(String s) {
        int monthindex = 0;
        switch (s) {
            case "JANUARY":
                monthindex = 1;
                break;
            case "FEBRUARY":
                monthindex = 2;
                break;
            case "MARCH":
                monthindex = 3;
                break;
            case "APRIL":
                monthindex = 4;
                break;
            case "MAY":
                monthindex = 5;
                break;
            case "JUNE":
                monthindex = 6;
                break;
            case "JULY":
                monthindex = 7;
                break;
            case "AUGEST":
                monthindex = 8;
                break;
            case "CEPTEMBER":
                monthindex = 9;
                break;
            case "OCTOBER":
                monthindex = 10;
                break;
            case "NOVEMBER":
                monthindex = 11;
                break;
            case "DECEMBER":
                monthindex = 12;
                break;
        }
        return monthindex;
    }

    public static int indexOfMonth(int s) {
        int monthindex = 0;
        switch (s) {
            case 1:
                monthindex = 5;
                break;
            case 2:
                monthindex = 6;
                break;
            case 3:
                monthindex = 7;
                break;
            case 4:
                monthindex = 8;
                break;
            case 5:
                monthindex = 9;
                break;
            case 6:
                monthindex = 10;
                break;
            case 7:
                monthindex = 11;
                break;
            case 8:
                monthindex = 12;
                break;
            case 9:
                monthindex = 1;
                break;
            case 10:
                monthindex = 2;
                break;
            case 11:
                monthindex = 3;
                break;
            case 12:
                monthindex = 4;
                break;
        }
        return monthindex;
    }

    public static int getNumberOfDay(int m, int y) {
        int days = 0;
        if (m < 1) {
            m = 13;
            y--;
        }
        if (m >= 1 && m <= 12) {
            days = 30;
        } else if ((((y + 1) % 100 != 0 && (y + 1) % 4 == 0) || ((y + 1) % 400 == 0)) && m == 13) {
            days = 6;
        } else {
            days = 5;
        }
        return days;
    }

    public static void nextMonth() {
        month++;

        if (month > 13) {
            month = 1;
            year++;
        }
        clearButton();
        getCalendar(1, month, year);
    }

    public static void previousMonth() {
        month--;
        if (month < 1) {
            month = 13;
            year--;
        }

        clearButton();
        getCalendar(1, month, year);
    }

    public static void clearButton() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                daysbutton[i][j].setText("");
                daysbutton[i][j].setBackground(Color.WHITE);
                daysbutton[i][j].setVisible(false);
            }
        }
    }

    public static void clearField() {
        nameday.setText("");
        namemonth.setText("");
        nameyear.setText("");
    }

    public static void checkForHoliday(int i, int j, int d, int m, int y) {

        if (d == 1 & m == 1) {
            daysbutton[i][j].setBackground(new Color(204, 204, 255));
            daysbutton[i][j].setForeground(Color.BLACK);
        } else if (d == 17 & m == 1) {
            daysbutton[i][j].setBackground(new Color(204, 204, 255));
            daysbutton[i][j].setForeground(Color.BLACK);
        } else if (d == 29 & m == 4) {
            daysbutton[i][j].setBackground(new Color(204, 204, 255));
            daysbutton[i][j].setForeground(Color.BLACK);
        } else if (d == 11 & m == 5) {
            daysbutton[i][j].setBackground(new Color(204, 204, 255));
            daysbutton[i][j].setForeground(Color.BLACK);
        } else if (d == 23 & m == 6) {
            daysbutton[i][j].setBackground(new Color(204, 204, 255));
            daysbutton[i][j].setForeground(Color.BLACK);
        } else if (d == 20 & m == 9) {
            daysbutton[i][j].setBackground(new Color(204, 204, 255));
            daysbutton[i][j].setForeground(Color.BLACK);
        }
    }

}
