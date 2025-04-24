package ProjectManagerr;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainAppWindow extends JFrame {

    public MainAppWindow() {
        // Pangalan ng app window
        setTitle("Project Management Application");
        setSize(540, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel sa loob
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(24, 26, 34));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Pangalan ng app
        JLabel appName = new JLabel("P.M.A");
        appName.setFont(new Font("Segoe UI", Font.BOLD, 38));
        appName.setForeground(new Color(230, 230, 255));
        appName.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(appName);

        // Subtitle ng app
        JLabel subtitle = new JLabel("Project Management Application");
        subtitle.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        subtitle.setForeground(new Color(170, 170, 190));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createVerticalStrut(4));
        mainPanel.add(subtitle);

        // Welcome message
        JLabel greeting = new JLabel("WELCOME!");
        greeting.setFont(new Font("Segoe UI", Font.BOLD, 20));
        greeting.setForeground(new Color(110, 145, 255));
        greeting.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createVerticalStrut(14));
        mainPanel.add(greeting);

        // XP bar dito (design only not progressive)
        mainPanel.add(Box.createVerticalStrut(12));
        JPanel xpPanel = new JPanel();
        xpPanel.setBackground(new Color(30, 32, 40));
        xpPanel.setLayout(new BorderLayout());
        xpPanel.setMaximumSize(new Dimension(400, 20));
        xpPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        xpPanel.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 120), 2));

        // Bar na green (design only not progressive)
        JPanel xpBar = new JPanel();
        xpBar.setBackground(new Color(130, 220, 120));
        xpBar.setPreferredSize(new Dimension(240, 16));
        xpPanel.add(xpBar, BorderLayout.WEST);
        mainPanel.add(xpPanel);

        // Achievements title (design only not progressive)
        JLabel achvTitle = new JLabel("() Achievements");
        achvTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        achvTitle.setForeground(new Color(255, 215, 120));
        achvTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createVerticalStrut(16));
        mainPanel.add(achvTitle);

        // Panel ng badges (design only not progressive)
        JPanel badgePanel = new JPanel();
        badgePanel.setBackground(new Color(24, 26, 34));
        badgePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 12, 6));

        // Mga badge icon
        String[] badges = {"🌟", "🔥", "🎯", "⏳"};
        for (String badge : badges) {
            JPanel badgeCircle = new JPanel();
            badgeCircle.setPreferredSize(new Dimension(42, 42));
            badgeCircle.setBackground(new Color(255, 255, 255, 80));
            badgeCircle.setLayout(new GridBagLayout());

            JLabel label = new JLabel(badge);
            label.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
            badgeCircle.add(label);
            badgePanel.add(badgeCircle);
        }
        mainPanel.add(badgePanel);

        // Goals panel dito
        JPanel goalsPanel = new JPanel();
        goalsPanel.setBackground(new Color(30, 32, 40));
        goalsPanel.setLayout(new BoxLayout(goalsPanel, BoxLayout.Y_AXIS));
        goalsPanel.setBorder(BorderFactory.createEmptyBorder(14, 20, 14, 20));

        // Title ng goals
        JLabel goalsTitle = new JLabel("-> Your Goals <-");
        goalsTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        goalsTitle.setForeground(Color.WHITE);
        goalsTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        goalsPanel.add(goalsTitle);
        goalsPanel.add(Box.createVerticalStrut(10));

        // Goals list dito (design only not progressive)
        String[] goals = {
            "* Finish 3 tasks today",
            "*️ Complete 1 Pomodoro session",
            "* Update 1 task status",
            "* Plan tomorrow's tasks"
        };

        // Gawa goal cards
        for (String goal : goals) {
            JPanel card = new JPanel();
            card.setBackground(new Color(45, 48, 60));
            card.setMaximumSize(new Dimension(380, 36));
            card.setAlignmentX(Component.CENTER_ALIGNMENT);
            card.setLayout(new FlowLayout(FlowLayout.LEFT));
            card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(90, 90, 130), 1),
                BorderFactory.createEmptyBorder(4, 12, 4, 12)
            ));

            JLabel goalText = new JLabel(goal);
            goalText.setForeground(Color.WHITE);
            goalText.setFont(new Font("Segoe UI", Font.PLAIN, 15));
            card.add(goalText);

            goalsPanel.add(card);
            goalsPanel.add(Box.createVerticalStrut(8));
        }

        mainPanel.add(goalsPanel);

        // Buttons sa baba
        mainPanel.add(Box.createVerticalStrut(10));
        JPanel grid = new JPanel(new GridLayout(3, 2, 18, 18));
        grid.setOpaque(false);

        // Add ng mga buttons
        grid.add(createSoftCardButton("Create Task", new Color(54, 48, 35), new Color(255, 230, 140), () -> {
            this.dispose();
            new CreateTaskFrame().setVisible(true);
        }));
        grid.add(createSoftCardButton("View Tasks", new Color(40, 56, 66), new Color(120, 200, 255), () -> {
            this.dispose();
            new ViewTasksFrame().setVisible(true);
        }));
        grid.add(createSoftCardButton("Update Task", new Color(43, 58, 46), new Color(170, 255, 200), () -> {
            this.dispose();
            new UpdateTaskFrame().setVisible(true);
        }));
        grid.add(createSoftCardButton("Delete Task", new Color(60, 34, 38), new Color(255, 156, 156), () -> {
            this.dispose();
            new DeleteTaskFrame().setVisible(true);
        }));
        grid.add(createSoftCardButton("Pomodoro Timer", new Color(60, 55, 80), new Color(180, 170, 255), () -> {
            this.dispose();
            new PomodoroFrame().setVisible(true);
        }));
        grid.add(createSoftCardButton("Logout", new Color(64, 60, 36), new Color(255, 230, 110), () -> {
            this.dispose();
            new LoginPage().setVisible(true);
        }));

        mainPanel.add(grid);

        // Scroll bar sa frame
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(80, 80, 110);
            }
        });

        setContentPane(scrollPane);
        setVisible(true);
    }

    // Gawa ng button
    private JButton createSoftCardButton(String text, Color shadow, Color bgColor, Runnable action) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(shadow);
                g2.fillRoundRect(8, 10, getWidth() - 16, getHeight() - 16, 36, 36);
                g2.setColor(getModel().isArmed() ? bgColor.darker() : bgColor);
                g2.fillRoundRect(0, 0, getWidth() - 8, getHeight() - 8, 28, 28);
                g2.dispose();
                super.paintComponent(g);
            }
        };

        // Button style basic
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btn.setForeground(new Color(34, 34, 34));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(180, 78));
        btn.setMargin(new Insets(16, 16, 16, 16));
        btn.setHorizontalAlignment(SwingConstants.CENTER);

        // Hover effect lang (design2 lang)
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setForeground(new Color(70, 130, 255));
                btn.repaint();
            }
            public void mouseExited(MouseEvent e) {
                btn.setForeground(new Color(34, 34, 34));
                btn.repaint();
            }
        });

        // Pag-click ng button
        btn.addActionListener(e -> action.run());
        return btn;
    }

    // Simula ng program
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainAppWindow::new);
    }
}
