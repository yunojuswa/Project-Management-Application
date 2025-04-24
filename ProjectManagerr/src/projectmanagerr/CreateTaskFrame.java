package ProjectManagerr;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CreateTaskFrame extends JFrame {

    public CreateTaskFrame() {

        // Pangalan ng window
        setTitle("Create Task");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(30, 30, 30));

        // Card panel container
        JPanel cardPanel = new JPanel();
        cardPanel.setBackground(new Color(45, 45, 45));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Header title text
        JLabel header = new JLabel("CREATE TASK");
        header.setForeground(new Color(102, 153, 255));
        header.setFont(new Font("Segoe UI", Font.BOLD, 28));
        header.setAlignmentX(Component.CENTER_ALIGNMENT);
        header.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0));

        // Input fields dito
        JTextField titleField = createTextField("Title");
        JTextField descField = createTextField("Description");
        JTextField dueField = createTextField("Due Date (YYYY-MM-DD)");
        JComboBox<String> statusBox = new JComboBox<>(new String[]{"Not Started", "In Progress", "Completed"});
        styleComboBox(statusBox);

        // Mga buttons
        JButton saveBtn = createButton("Save");
        JButton backBtn = createButton("Back");

        // Add components here
        cardPanel.add(header);
        cardPanel.add(titleField);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        cardPanel.add(descField);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        cardPanel.add(dueField);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        cardPanel.add(statusBox);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        cardPanel.add(saveBtn);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        cardPanel.add(backBtn);

        add(cardPanel);

        // Pag-click ng Save
        saveBtn.addActionListener(e -> {
            String title = titleField.getText();
            String desc = descField.getText();
            String due = dueField.getText();
            String status = (String) statusBox.getSelectedItem();
            String username = Session.currentUsername;

            // Check kung may user
            if (username == null || username.isEmpty()) {
                JOptionPane.showMessageDialog(this, "User not logged in!");
                return;
            }

            try {
                // JSON file path
                File file = new File("N:\\Documents\\NetBeansProjects\\ProjectManagerr\\src\\tasks.json");
                JSONObject taskData;

                // Load existing JSON
                if (file.exists()) {
                    JSONParser parser = new JSONParser();
                    FileReader reader = new FileReader(file);
                    taskData = (JSONObject) parser.parse(reader);
                    reader.close();
                } else {
                    taskData = new JSONObject();
                }

                if (title.isEmpty() || desc.isEmpty() || due.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill the blanks!");
                    return;
                }

                // Get tasks ng user
                JSONArray tasks = (JSONArray) taskData.get(username);
                if (tasks == null) {
                    tasks = new JSONArray();
                }

                // Gawa bagong task
                JSONObject task = new JSONObject();
                task.put("title", title);
                task.put("description", desc);
                task.put("dueDate", due);
                task.put("status", status);

                tasks.add(task); // Add sa list
                taskData.put(username, tasks); // Save kay user

                // Save to file
                FileWriter writer = new FileWriter(file);
                writer.write(taskData.toJSONString());
                writer.flush();
                writer.close();

                JOptionPane.showMessageDialog(this, "Task saved!");
                MainAppWindow x = new MainAppWindow();
                x.setVisible(true);
                setVisible(false);

            } catch (IOException | ParseException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error saving task.");
            }
        });

        // Pag-click ng Back
        backBtn.addActionListener(e -> {
            this.dispose();
            new MainAppWindow().setVisible(true);
        });

        setVisible(true);
    }

    // Gawa ng text field
    private JTextField createTextField(String placeholder) {
        JTextField field = new JTextField();
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        field.setFont(new Font("Segoe UI", Font.BOLD, 14));
        field.setBackground(new Color(60, 60, 60));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(102, 153, 255)),
                placeholder,
                0, 0,
                new Font("Segoe UI", Font.BOLD, 12),
                Color.LIGHT_GRAY
        ));
        return field;
    }

    // Gawa ng button
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(new Color(102, 153, 255));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }

    // Style ng combo box
    private void styleComboBox(JComboBox<String> box) {
        box.setFont(new Font("Segoe UI", Font.BOLD, 14));
        box.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        box.setBackground(new Color(60, 60, 60));
        box.setForeground(Color.WHITE);
        box.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(102, 153, 255)),
                "Status",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 12),
                Color.LIGHT_GRAY
        ));
    }
}
