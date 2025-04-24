package ProjectManagerr;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class UpdateTaskFrame extends JFrame {

    private JSONObject taskData = new JSONObject();
    private JSONArray userTasks = new JSONArray();

    public UpdateTaskFrame() {

        // I-set ang window properties
        setTitle("Update Task");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(30, 30, 30));
        setLayout(new BorderLayout());

        // Gumawa ng header label
        JLabel header = new JLabel("UPDATE TASK");
        header.setFont(new Font("Segoe UI", Font.BOLD, 28));
        header.setForeground(new Color(255, 204, 102));
        header.setHorizontalAlignment(SwingConstants.CENTER);
        header.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(header, BorderLayout.NORTH);

        // I-setup ang content panel
        JPanel formPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        formPanel.setBackground(new Color(45, 45, 45));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JComboBox<String> taskSelector = new JComboBox<>();
        JTextField descField = new JTextField();
        JTextField dueField = new JTextField();
        JComboBox<String> statusBox = new JComboBox<>(new String[]{"Not Started", "In Progress", "Completed"});

        styleComponent(taskSelector);
        styleComponent(descField);
        styleComponent(dueField);
        styleComponent(statusBox);

        formPanel.add(styledLabel("Select Task:"));
        formPanel.add(taskSelector);
        formPanel.add(styledLabel("Description:"));
        formPanel.add(descField);
        formPanel.add(styledLabel("Due Date (YYYY-MM-DD):"));
        formPanel.add(dueField);
        formPanel.add(styledLabel("Status:"));
        formPanel.add(statusBox);

        add(formPanel, BorderLayout.CENTER);

        // I-setup ang bottom panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(30, 30, 30));

        JButton updateBtn = new JButton("Update");
        JButton backBtn = new JButton("Back");

        styleButton(updateBtn, new Color(102, 255, 178));
        styleButton(backBtn, new Color(255, 102, 102));

        bottomPanel.add(updateBtn);
        bottomPanel.add(backBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        // I-load ang tasks mula file
        String username = Session.currentUsername;
        File file = new File("N:\\Documents\\NetBeansProjects\\ProjectManagerr\\src\\tasks.json");

        if (username == null || username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "User not logged in!");
            dispose();
            return;
        }

        try {
            if (file.exists()) {
                JSONParser parser = new JSONParser();
                FileReader reader = new FileReader(file);
                taskData = (JSONObject) parser.parse(reader);
                reader.close();

                userTasks = (JSONArray) taskData.get(username);
                if (userTasks != null) {
                    for (Object obj : userTasks) {
                        JSONObject task = (JSONObject) obj;
                        taskSelector.addItem((String) task.get("title"));
                    }
                } else {
                    userTasks = new JSONArray();
                }
            }
        } catch (IOException | ParseException ex) {
            ex.printStackTrace();
        }

        // Piliin ang task
        taskSelector.addActionListener(e -> {
            int index = taskSelector.getSelectedIndex();
            if (index >= 0 && userTasks != null && index < userTasks.size()) {
                JSONObject selectedTask = (JSONObject) userTasks.get(index);
                descField.setText((String) selectedTask.get("description"));
                dueField.setText((String) selectedTask.get("dueDate"));
                statusBox.setSelectedItem((String) selectedTask.get("status"));
            }
        });

        // I-update ang task
        updateBtn.addActionListener(e -> {
            if (userTasks.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select a task to update.");
            }
            int index = taskSelector.getSelectedIndex();
            if (index >= 0 && userTasks != null && index < userTasks.size()) {
                JSONObject selectedTask = (JSONObject) userTasks.get(index);
                selectedTask.put("description", descField.getText());
                selectedTask.put("dueDate", dueField.getText());
                selectedTask.put("status", statusBox.getSelectedItem());

                taskData.put(username, userTasks);
                try {
                    FileWriter writer = new FileWriter(file);
                    writer.write(taskData.toJSONString());
                    writer.flush();
                    writer.close();
                    JOptionPane.showMessageDialog(this, "Task updated!");
                    MainAppWindow x = new MainAppWindow();
                    x.setVisible(true);
                    setVisible(false);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error updating task.");
                }
            }
        });

        // Action ng back button
        backBtn.addActionListener(e -> {
            this.dispose();
            new MainAppWindow().setVisible(true);
        });

        setVisible(true);
    }

    // I-style ang label
    private JLabel styledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return label;
    }

    // I-style ang component
    private void styleComponent(JComponent comp) {
        comp.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comp.setBackground(new Color(60, 60, 60));
        comp.setForeground(Color.WHITE);
        comp.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(90, 90, 90)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }

    // I-style ang button
    private void styleButton(JButton btn, Color bgColor) {
        btn.setBackground(bgColor);
        btn.setForeground(Color.BLACK);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }
}
