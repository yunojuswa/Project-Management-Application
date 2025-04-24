
package ProjectManagerr;

import java.awt.*;
import javax.swing.*;
import java.io.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class ViewTasksFrame extends JFrame {
    public ViewTasksFrame() {
        
        // I-set ang window properties
        setTitle("View Tasks");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(30, 30, 30));
        setLayout(new BorderLayout());

        // Gumawa ng header label
        JLabel header = new JLabel("VIEW TASKS");
        header.setFont(new Font("Segoe UI", Font.BOLD, 28));
        header.setForeground(new Color(102, 178, 255));
        header.setHorizontalAlignment(SwingConstants.CENTER);
        header.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(header, BorderLayout.NORTH);

        // Gumawa ng text area
        JTextArea taskArea = new JTextArea();
        taskArea.setEditable(false);
        taskArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        taskArea.setBackground(new Color(50, 50, 50));
        taskArea.setForeground(Color.WHITE);
        taskArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        taskArea.setLineWrap(true);
        taskArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(taskArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        scrollPane.setBackground(new Color(45, 45, 45));
        add(scrollPane, BorderLayout.CENTER);

        // Bottom panel with back button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(30, 30, 30));
        JButton backBtn = new JButton("Back");
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        backBtn.setBackground(new Color(102, 178, 255));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFocusPainted(false);
        backBtn.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        bottomPanel.add(backBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        // I-load at ipakita ang tasks
        String username = Session.currentUsername;

        if (username == null || username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "User not logged in!");
            return;
        }

        try {
            File file = new File("N:\\Documents\\NetBeansProjects\\ProjectManagerr\\src\\tasks.json");

            if (!file.exists()) {
                taskArea.setText("No tasks found.");
            } else {
                JSONParser parser = new JSONParser();
                FileReader reader = new FileReader(file);
                JSONObject taskData = (JSONObject) parser.parse(reader);
                reader.close();

                JSONArray tasks = (JSONArray) taskData.get(username);

                if (tasks != null && !tasks.isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    for (Object obj : tasks) {
                        JSONObject task = (JSONObject) obj;
                        sb.append("📌 Title: ").append(task.get("title")).append("\n");
                        sb.append("📝 Description: ").append(task.get("description")).append("\n");
                        sb.append("📅 Due Date: ").append(task.get("dueDate")).append("\n");
                        sb.append("✅ Status: ").append(task.get("status")).append("\n");
                        sb.append("───────────────────────────────\n");
                    }
                    taskArea.setText(sb.toString());
                } else {
                    taskArea.setText("No tasks available.");
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error reading tasks.");
        }

        // Action ng back button
        backBtn.addActionListener(e -> {
            this.dispose();
            new MainAppWindow().setVisible(true);
        });

        setVisible(true);
    }
}
