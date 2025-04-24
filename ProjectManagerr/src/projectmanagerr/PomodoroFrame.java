
/* 
Pomodoro technique kung saan magtatrabaho ka ng 30 minutes
pagkatapos ay magpapahinga ng 5 minuto, at paulit-ulit ito. Maaari mong i-start,
i-pause, at i-reset ang timer, at ipinapakita nito kung nasa "WORK" o "BREAK" mode ka. 
Kapag tapos na, maaari kang bumalik sa main window gamit ang "Back" button.
*/

package ProjectManagerr;

import java.awt.*;
import javax.swing.*;
import javax.swing.Timer;

public class PomodoroFrame extends JFrame {
    private Timer timer;
    private int timeLeft = 30 * 60; // 30 minutes
    private boolean isRunning = false;
    private boolean isBreak = false;

    private JLabel timerLabel = new JLabel("30:00");
    private JLabel modeLabel = new JLabel("WORK");
    private JButton startPauseButton = new JButton("Start");
    private JButton resetButton = new JButton("Reset");
    private JButton backButton = new JButton("← Back");

    public PomodoroFrame() {
        setTitle("Pomodoro Timer");
        setSize(400, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(30, 30, 30));
        setLayout(new BorderLayout());

        // Panel para sa Mode
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(30, 30, 30));

        modeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        modeLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        modeLabel.setForeground(new Color(102, 255, 178));
        modeLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        topPanel.add(modeLabel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // Timer Label
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timerLabel.setFont(new Font("Consolas", Font.BOLD, 72));
        timerLabel.setForeground(Color.WHITE);
        timerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(timerLabel, BorderLayout.CENTER);

        // Panel para sa Buttons
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(new Color(30, 30, 30));

        // Center Panel (Start at Reset Buttons)
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        centerPanel.setBackground(new Color(30, 30, 30));
        styleButton(startPauseButton, new Color(102, 255, 178));
        styleButton(resetButton, new Color(255, 102, 102));
        centerPanel.add(startPauseButton);
        centerPanel.add(resetButton);

        // Right Panel (Back button)
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        rightPanel.setBackground(new Color(30, 30, 30));
        styleButton(backButton, new Color(200, 200, 200)); // gray style
        backButton.addActionListener(e -> {
            dispose();
            new MainAppWindow().setVisible(true); // back to main
        });
        rightPanel.add(backButton);

        // I-assemble ang button panel
        buttonPanel.add(centerPanel, BorderLayout.CENTER);
        buttonPanel.add(rightPanel, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);

        // Setup ng timer
        timer = new Timer(1000, e -> updateTimer());

        // Start/Pause button action
        startPauseButton.addActionListener(e -> {
            if (isRunning) {
                timer.stop();
                startPauseButton.setText("Start");
            } else {
                timer.start();
                startPauseButton.setText("Pause");
            }
            isRunning = !isRunning;
        });

        // Reset button action
        resetButton.addActionListener(e -> {
            timer.stop();
            isRunning = false;
            isBreak = false;
            timeLeft = 30 * 60;
            modeLabel.setText("WORK");
            modeLabel.setForeground(new Color(102, 255, 178));
            startPauseButton.setText("Start");
            updateLabel();
        });

        updateLabel();
        setVisible(true);
    }

    // Update ang timer
    private void updateTimer() {
        if (timeLeft > 0) {
            timeLeft--;
            updateLabel();
        } else {
            timer.stop();
            isRunning = false;

            // Kung tapos na ang work, mag-break
            if (!isBreak) {
                JOptionPane.showMessageDialog(this, "Work session complete! Time for a 5-minute break.");
                timeLeft = 5 * 60; // 5 minute break
                isBreak = true;
                modeLabel.setText("BREAK");
                modeLabel.setForeground(new Color(255, 204, 102));
            } else {
                JOptionPane.showMessageDialog(this, "Break over! Back to work.");
                timeLeft = 30 * 60; // 30 minutes work
                isBreak = false;
                modeLabel.setText("WORK");
                modeLabel.setForeground(new Color(102, 255, 178));
            }

            updateLabel();
            timer.start();
            isRunning = true;
            startPauseButton.setText("Pause");
        }
    }

    // I-update ang label ng oras
    private void updateLabel() {
        int minutes = timeLeft / 60;
        int seconds = timeLeft % 60;
        timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }

    // I-style ang button
    private void styleButton(JButton btn, Color bgColor) {
        btn.setBackground(bgColor);
        btn.setForeground(Color.BLACK);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    // Main na function
    public static void main(String[] args) {
        new PomodoroFrame();
    }
}
