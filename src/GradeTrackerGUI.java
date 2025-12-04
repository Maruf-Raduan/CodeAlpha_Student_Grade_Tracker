import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GradeTrackerGUI extends JFrame {
    private ArrayList<Student> students;
    private DefaultTableModel tableModel;
    private JTable table;

    // Dashboard Components
    private JLabel totalStudentsLabel;
    private JLabel dashClassAvgLabel;
    private JLabel bestStudentLabel;

    // Detail View Components
    private JLabel detailNameLabel;
    private JLabel detailRegLabel;
    private JLabel detailAvgLabel;
    private JLabel detailStatusLabel;
    private JTextArea detailGradesArea;
    private JPanel detailPanel;

    public GradeTrackerGUI() {
        students = new ArrayList<>();
        setTitle("Student Grade Tracker Pro");
        setSize(1100, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main Tabbed Pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // --- Tab 1: Dashboard ---
        JPanel dashboardPanel = createDashboardPanel();
        tabbedPane.addTab("Dashboard", dashboardPanel);

        // --- Tab 2: Student Management ---
        JPanel managementPanel = createManagementPanel();
        tabbedPane.addTab("Student Management", managementPanel);

        add(tabbedPane);
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel title = new JLabel("Class Overview", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(title);

        // Stats Container
        JPanel statsContainer = new JPanel(new GridLayout(1, 3, 20, 0));

        totalStudentsLabel = createStatCard("Total Students", "0");
        dashClassAvgLabel = createStatCard("Class Average", "0.00");
        bestStudentLabel = createStatCard("Top Performer", "N/A");

        statsContainer.add(totalStudentsLabel);
        statsContainer.add(dashClassAvgLabel);
        statsContainer.add(bestStudentLabel);

        panel.add(statsContainer);

        return panel;
    }

    private JLabel createStatCard(String title, String initialValue) {
        JLabel label = new JLabel(
                "<html><center>" + title + "<br/><font size='6'>" + initialValue + "</font></center></html>",
                SwingConstants.CENTER);
        label.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        return label;
    }

    private JPanel createManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Top: Input Area
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add New Student / Grade"));

        JTextField regField = new JTextField(8);
        JTextField nameField = new JTextField(15);
        JTextField courseField = new JTextField(8);
        JTextField subjectField = new JTextField(12);
        JTextField gradeField = new JTextField(5);
        JButton addButton = new JButton("Add Record");

        inputPanel.add(new JLabel("Reg No:"));
        inputPanel.add(regField);
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Course Code:"));
        inputPanel.add(courseField);
        inputPanel.add(new JLabel("Subject:"));
        inputPanel.add(subjectField);
        inputPanel.add(new JLabel("Grade:"));
        inputPanel.add(gradeField);
        inputPanel.add(addButton);

        panel.add(inputPanel, BorderLayout.NORTH);

        // Center: Split Pane (Table + Details)
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setResizeWeight(0.6);

        // Left: Table
        String[] columnNames = { "Reg No", "Name", "Average", "Performance" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Custom Renderer for Performance Column
        table.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String status = (String) value;
                if ("Excellent".equals(status))
                    c.setForeground(new Color(0, 150, 0)); // Green
                else if ("Needs Improvement".equals(status))
                    c.setForeground(Color.RED);
                else
                    c.setForeground(Color.BLACK);
                return c;
            }
        });

        JScrollPane tableScroll = new JScrollPane(table);
        splitPane.setLeftComponent(tableScroll);

        // Right: Detail View
        detailPanel = new JPanel();
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));
        detailPanel.setBorder(BorderFactory.createTitledBorder("Student Details"));

        detailNameLabel = new JLabel("Select a student");
        detailNameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        detailNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        detailRegLabel = new JLabel("Reg No: -");
        detailRegLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        detailAvgLabel = new JLabel("Average: -");
        detailAvgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        detailStatusLabel = new JLabel("Status: -");
        detailStatusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        detailGradesArea = new JTextArea(10, 30);
        detailGradesArea.setEditable(false);
        JScrollPane gradesScroll = new JScrollPane(detailGradesArea);
        gradesScroll.setBorder(BorderFactory.createTitledBorder("Grade History"));

        detailPanel.add(Box.createVerticalStrut(20));
        detailPanel.add(detailNameLabel);
        detailPanel.add(Box.createVerticalStrut(5));
        detailPanel.add(detailRegLabel);
        detailPanel.add(Box.createVerticalStrut(10));
        detailPanel.add(detailAvgLabel);
        detailPanel.add(Box.createVerticalStrut(10));
        detailPanel.add(detailStatusLabel);
        detailPanel.add(Box.createVerticalStrut(20));
        detailPanel.add(gradesScroll);

        splitPane.setRightComponent(detailPanel);
        panel.add(splitPane, BorderLayout.CENTER);

        // Logic
        addButton.addActionListener(e -> {
            String regNo = regField.getText().trim();
            String name = nameField.getText().trim();
            String course = courseField.getText().trim();
            String subject = subjectField.getText().trim();
            String gradeText = gradeField.getText().trim();

            if (regNo.isEmpty() || name.isEmpty() || course.isEmpty() || subject.isEmpty() || gradeText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.");
                return;
            }

            try {
                double grade = Double.parseDouble(gradeText);
                if (grade >= 0 && grade <= 100) {
                    addOrUpdateStudent(regNo, name, course, subject, grade);
                    // Clear fields except RegNo/Name to allow easy multi-subject entry?
                    // Or clear all. Let's clear all for now.
                    regField.setText("");
                    nameField.setText("");
                    courseField.setText("");
                    subjectField.setText("");
                    gradeField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Grade must be 0-100");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid grade");
            }
        });

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                    showStudentDetails(table.getSelectedRow());
                }
            }
        });

        return panel;
    }

    private void addOrUpdateStudent(String regNo, String name, String course, String subject, double grade) {
        Student student = null;
        for (Student s : students) {
            if (s.getRegistrationNumber().equalsIgnoreCase(regNo)) {
                student = s;
                break;
            }
        }

        if (student == null) {
            student = new Student(name, regNo);
            students.add(student);
        }

        student.addGrade(grade, course, subject);
        refreshData();
    }

    private void refreshData() {
        // Update Table
        tableModel.setRowCount(0);
        double classTotal = 0;
        int totalGrades = 0;
        Student bestStudent = null;
        double highestAvg = -1;

        for (Student s : students) {
            double avg = s.getAverage();
            String status = getPerformanceStatus(avg);
            tableModel.addRow(
                    new Object[] { s.getRegistrationNumber(), s.getName(), String.format("%.2f", avg), status });

            // Stats
            if (!s.getGrades().isEmpty()) {
                for (Grade g : s.getGrades()) {
                    classTotal += g.getScore();
                    totalGrades++;
                }
                if (avg > highestAvg) {
                    highestAvg = avg;
                    bestStudent = s;
                }
            }
        }

        // Update Dashboard
        totalStudentsLabel.setText(
                "<html><center>Total Students<br/><font size='6'>" + students.size() + "</font></center></html>");
        double classAvg = totalGrades > 0 ? classTotal / totalGrades : 0;
        dashClassAvgLabel.setText("<html><center>Class Average<br/><font size='6'>" + String.format("%.2f", classAvg)
                + "</font></center></html>");
        bestStudentLabel.setText("<html><center>Top Performer<br/><font size='6'>"
                + (bestStudent != null ? bestStudent.getName() : "N/A") + "</font></center></html>");

        // Refresh details if selected
        if (table.getSelectedRow() != -1) {
            showStudentDetails(table.getSelectedRow());
        }
    }

    private void showStudentDetails(int row) {
        String regNo = (String) tableModel.getValueAt(row, 0);
        Student selected = null;
        for (Student s : students) {
            if (s.getRegistrationNumber().equals(regNo)) {
                selected = s;
                break;
            }
        }

        if (selected != null) {
            detailNameLabel.setText(selected.getName());
            detailRegLabel.setText("Reg No: " + selected.getRegistrationNumber());
            detailAvgLabel.setText("Average: " + String.format("%.2f", selected.getAverage()));
            String status = getPerformanceStatus(selected.getAverage());
            detailStatusLabel.setText("Status: " + status);

            if ("Excellent".equals(status))
                detailStatusLabel.setForeground(new Color(0, 150, 0));
            else if ("Needs Improvement".equals(status))
                detailStatusLabel.setForeground(Color.RED);
            else
                detailStatusLabel.setForeground(Color.BLACK);

            StringBuilder grades = new StringBuilder();
            for (Grade g : selected.getGrades()) {
                grades.append(g.toString()).append("\n");
            }
            detailGradesArea.setText(grades.toString());
        }
    }

    private String getPerformanceStatus(double avg) {
        if (avg >= 90)
            return "Excellent";
        if (avg >= 75)
            return "Good";
        if (avg >= 60)
            return "Average";
        return "Needs Improvement";
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new GradeTrackerGUI().setVisible(true));
    }
}
