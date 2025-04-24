
package ProjectManagerr;

import java.io.*;
import javax.swing.*;
import java.awt.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class LoginPage extends javax.swing.JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox rememberMeCheckBox;

    // Gumagawa ng login window
    public LoginPage() {
        initComponents();
        loadCredentials();
    }

    // Design ng login window
    private void initComponents() {
        setTitle("Log in");
        setSize(400, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(40, 40, 40));
        panel.setLayout(null);

        ImageIcon logo = new ImageIcon("N:\\Documents\\NetBeansProjects\\ProjectManagerr\\src\\logo.png");
        Image scaled = logo.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        JLabel icon = new JLabel(new ImageIcon(scaled));
        icon.setBounds(140, 50, 120, 120);
        panel.add(icon);

        JLabel title = new JLabel("P.M.A");
        title.setBounds(0, 180, 400, 40);
        title.setForeground(new Color(255, 87, 87));
        title.setFont(new Font("SansSerif", Font.BOLD, 32));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(title);

        JLabel subtitle = new JLabel("Project Management Application");
        subtitle.setBounds(0, 220, 400, 20);
        subtitle.setForeground(new Color(255, 87, 87));
        subtitle.setFont(new Font("SansSerif", Font.BOLD, 14));
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(subtitle);

        JLabel groupLabel = new JLabel("By group 6");
        groupLabel.setBounds(0, 240, 400, 20);
        groupLabel.setForeground(Color.LIGHT_GRAY);
        groupLabel.setFont(new Font("SansSerif", Font.BOLD, 10));
        groupLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(groupLabel);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(70, 260, 100, 20);
        usernameLabel.setForeground(Color.LIGHT_GRAY);
        usernameLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        panel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(70, 280, 260, 40);
        usernameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        usernameField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        usernameField.setBackground(new Color(100, 100, 100));
        usernameField.setForeground(Color.WHITE);
        usernameField.setCaretColor(Color.WHITE);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(70, 330, 100, 20);
        passwordLabel.setForeground(Color.LIGHT_GRAY);
        passwordLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(70, 350, 260, 40);
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        passwordField.setBackground(new Color(100, 100, 100));
        passwordField.setForeground(Color.WHITE);
        passwordField.setCaretColor(Color.WHITE);
        panel.add(passwordField);

        rememberMeCheckBox = new JCheckBox("Remember me");
        rememberMeCheckBox.setBounds(70, 400, 150, 20);
        rememberMeCheckBox.setBackground(new Color(40, 40, 40));
        rememberMeCheckBox.setForeground(Color.LIGHT_GRAY);
        rememberMeCheckBox.setFont(new Font("SansSerif", Font.BOLD, 12));
        panel.add(rememberMeCheckBox);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(140, 435, 120, 40);
        loginButton.setBackground(new Color(255, 87, 87));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        loginButton.setBorder(BorderFactory.createEmptyBorder());
        loginButton.addActionListener(e -> login());
        panel.add(loginButton);

        JLabel switchLabel = new JLabel("Don't have an account? Sign up");
        switchLabel.setBounds(90, 490, 190, 20);
        switchLabel.setForeground(Color.LIGHT_GRAY);
        switchLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        panel.add(switchLabel);

        JLabel link = new JLabel("here");
        link.setBounds(280, 490, 40, 20);
        link.setForeground(new Color(255, 87, 87));
        link.setFont(new Font("SansSerif", Font.BOLD, 12));
        link.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        link.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new SignUp().setVisible(true);
                dispose();
            }
        });
        panel.add(link);

        add(panel);
    }

    // Naglo-login gamit credentials
    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (authenticateUser(username, password)) {
            Session.currentUsername = username;

            if (rememberMeCheckBox.isSelected()) {
                saveCredentials(username, password);
            } else {
                deleteCredentials();
            }

            JOptionPane.showMessageDialog(this, "Login Successful!");
            this.dispose();
            new MainAppWindow().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect username or password!");
        }
    }

    // Check kung tama login
    private boolean authenticateUser(String username, String password) {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("src/users.json"));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray users = (JSONArray) jsonObject.get("users");

            for (Object userObj : users) {
                JSONObject user = (JSONObject) userObj;
                String jsonUsername = (String) user.get("username");
                String jsonPassword = (String) user.get("password");

                if (username.equals(jsonUsername) && password.equals(jsonPassword)) {
                    return true;
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Sinesave ang login info
    private void saveCredentials(String username, String password) {
        try (FileWriter writer = new FileWriter("src/credentials.txt")) {
            writer.write(username + "\n");
            writer.write(password + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Kinukuha saved login info
    private void loadCredentials() {
        File file = new File("src/credentials.txt");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String username = reader.readLine();
                String password = reader.readLine();

                usernameField.setText(username);
                passwordField.setText(password);
                rememberMeCheckBox.setSelected(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Binubura ang saved login
    private void deleteCredentials() {
        File file = new File("src/credentials.txt");
        if (file.exists()) {
            file.delete();
        }
    }

    // Ipinapakita ang login window
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginPage().setVisible(true));
    }
}
