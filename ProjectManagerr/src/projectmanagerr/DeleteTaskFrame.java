package ProjectManagerr;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class DeleteTaskFrame extends JFrame {

    private JSONObject taskData = new JSONObject();
    private JSONArray userTasks = new JSONArray();
    private JComboBox<String> taskSelector = new JComboBox<>();

    public DeleteTaskFrame() {

        // Window setup
        setTitle("Delete Task");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(30, 30, 30));

        // Main container panel
        JPanel cardPanel = new JPanel();
        cardPanel.setBackground(new Color(45, 45, 45));
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Header label
        JLabel header = new JLabel("DELETE TASK");
        header.setForeground(new Color(255, 102, 102));
        header.setFont(new Font("Segoe UI", Font.BOLD, 28));
        header.setAlignmentX(Component.CENTER_ALIGNMENT);
        header.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0));

        styleComboBox(taskSelector);

        // Mga buttons
        JButton deleteBtn = createButton("Delete");
        JButton backBtn = createButton("Back");

        // Add UI elements
        cardPanel.add(header);
        cardPanel.add(taskSelector);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        cardPanel.add(deleteBtn);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        cardPanel.add(backBtn);
        add(cardPanel);

        // Load tasks sa combo box
        loadTasksIntoSelector();

        // Delete button action
        deleteBtn.addActionListener(e -> {
            String selectedTask = (String) taskSelector.getSelectedItem();
            if (selectedTask != null) {
                deleteTask(selectedTask); // Delete task
                taskSelector.removeItem(selectedTask); // Remove sa UI
                JOptionPane.showMessageDialog(this, "Task deleted successfully!");
                MainAppWindow x = new MainAppWindow();
                x.setVisible(true);
                setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a task to delete.");
            }
        });

        // Back button action
        backBtn.addActionListener(e -> {
            this.dispose();
            new MainAppWindow().setVisible(true);
        });

        setVisible(true);
    }

    // Load tasks sa selector
    private void loadTasksIntoSelector() {
        String username = Session.currentUsername;

        if (username == null || username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "User not logged in!");
            return;
        }

        try {
            // Load JSON file
            File file = new File("N:\\Documents\\NetBeansProjects\\ProjectManagerr\\src\\tasks.json");

            if (file.exists()) {
                JSONParser parser = new JSONParser();
                FileReader reader = new FileReader(file);
                taskData = (JSONObject) parser.parse(reader);
                reader.close();
            }

            // Kunin tasks ng user
            userTasks = (JSONArray) taskData.get(username);
            if (userTasks == null) {
                userTasks = new JSONArray();
                return;
            }

            // Ilista lahat ng task titles
            for (Object obj : userTasks) {
                JSONObject task = (JSONObject) obj;
                taskSelector.addItem((String) task.get("title"));
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading tasks.");
        }
    }

    // Delete ang selected task
    private void deleteTask(String selectedTitle) {
        JSONArray updatedTasks = new JSONArray();

        for (Object obj : userTasks) {
            JSONObject task = (JSONObject) obj;

            // Kung hindi ito ‘yon
            if (!task.get("title").equals(selectedTitle)) {
                updatedTasks.add(task);
            }
        }

        // Update JSON data
        taskData.put(Session.currentUsername, updatedTasks);

        try {
            // Save updated file
            FileWriter writer = new FileWriter("N:\\Documents\\NetBeansProjects\\ProjectManagerr\\src\\tasks.json");
            writer.write(taskData.toJSONString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving updated tasks.");
        }
    }

    // Gawa ng button
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(new Color(255, 102, 102));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }

    // Ayos combo box style
    private void styleComboBox(JComboBox<String> box) {
        box.setFont(new Font("Segoe UI", Font.BOLD, 14));
        box.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        box.setBackground(new Color(60, 60, 60));
        box.setForeground(Color.WHITE);
        box.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(255, 102, 102)),
                "Select Task",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 12),
                Color.LIGHT_GRAY
        ));
    }
}
