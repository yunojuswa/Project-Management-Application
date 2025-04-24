
package ProjectManagerr;

import java.io.*;
import javax.swing.*;
import java.awt.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class SignUp extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    // Gumagawa ng signup window
    public SignUp() {
        initComponents();
    }

    // Design ng signup window
    private void initComponents() {
        setTitle("Register");
        setSize(400, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(40, 40, 40));
        panel.setLayout(null);

        // Logo setup dito
        ImageIcon originalIcon = new ImageIcon("N:\\Documents\\NetBeansProjects\\ProjectManagerr\\src\\logo.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        JLabel checkIcon = new JLabel(new ImageIcon(scaledImage));
        checkIcon.setBounds(140, 50, 120, 120);
        panel.add(checkIcon);

        // Pangalan ng app
        JLabel title = new JLabel("P.M.A");
        title.setBounds(0, 180, 400, 40);
        title.setForeground(new Color(255, 87, 87));
        title.setFont(new Font("SansSerif", Font.BOLD, 32));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(title);

        // Subtitle ng app
        JLabel subtitle = new JLabel("Project Management Application");
        subtitle.setBounds(0, 220, 400, 20);
        subtitle.setForeground(new Color(255, 87, 87));
        subtitle.setFont(new Font("SansSerif", Font.BOLD, 14));
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(subtitle);

        // Group number display
        JLabel groupLabel = new JLabel("By group 6");
        groupLabel.setBounds(0, 240, 400, 20);
        groupLabel.setForeground(Color.LIGHT_GRAY);
        groupLabel.setFont(new Font("SansSerif", Font.BOLD, 10));
        groupLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(groupLabel);

        // Label para username
        JLabel usernameLabel = new JLabel("Create Username:");
        usernameLabel.setBounds(70, 260, 100, 20);
        usernameLabel.setForeground(Color.LIGHT_GRAY);
        usernameLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        panel.add(usernameLabel);

        // Input ng username
        usernameField = new JTextField();
        usernameField.setBounds(70, 280, 260, 40);
        usernameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        usernameField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        usernameField.setBackground(new Color(100, 100, 100));
        usernameField.setForeground(Color.WHITE);
        usernameField.setCaretColor(Color.WHITE);
        usernameField.setText("");
        panel.add(usernameField);

        // Label para password
        JLabel passwordLabel = new JLabel("Create Password:");
        passwordLabel.setBounds(70, 330, 100, 20);
        passwordLabel.setForeground(Color.LIGHT_GRAY);
        passwordLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        panel.add(passwordLabel);

        // Input ng password
        passwordField = new JPasswordField();
        passwordField.setBounds(70, 350, 260, 40);
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        passwordField.setBackground(new Color(100, 100, 100));
        passwordField.setForeground(Color.WHITE);
        passwordField.setCaretColor(Color.WHITE);
        passwordField.setText("");
        panel.add(passwordField);

        // Placeholder behavior password
        passwordField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (String.valueOf(passwordField.getPassword()).equals("Create a Password")) {
                    passwordField.setText("");
                    passwordField.setEchoChar('•');
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (String.valueOf(passwordField.getPassword()).isEmpty()) {
                    passwordField.setEchoChar((char)0);
                    passwordField.setText("Create a Password");
                }
            }
        });

        // Sign up button design
        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(140, 435, 120, 40);
        signUpButton.setBackground(new Color(255, 87, 87));
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setFocusPainted(false);
        signUpButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        signUpButton.setBorder(BorderFactory.createEmptyBorder());
        signUpButton.addActionListener(e -> handleSignUp());
        panel.add(signUpButton);

        // Text para mag-login
        JLabel switchLabel = new JLabel("Already have an account? Log in ");
        switchLabel.setBounds(90, 490, 190, 20);
        switchLabel.setForeground(Color.LIGHT_GRAY);
        panel.add(switchLabel);

        // Login link papunta login
        JLabel link = new JLabel("here");
        link.setBounds(280, 490, 40, 20);
        link.setForeground(new Color(255, 87, 87));
        link.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        link.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                new LoginPage().setVisible(true);
                dispose();
            }
        });
        panel.add(link);

        add(panel);
    }

    // Nagse-save ng account
    private void handleSignUp() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();

        if (username.isEmpty() || username.equals("Create a Username") ||
            password.isEmpty() || password.equals("Create a Password")) {
            JOptionPane.showMessageDialog(this, "Invalid Input!");
            return;
        }

        String filePath = "src/users.json";
        JSONParser parser = new JSONParser();

        try {
            File file = new File(filePath);

            JSONObject jsonObject;
            JSONArray usersArray;

            if (file.exists()) {
                FileReader reader = new FileReader(filePath);
                jsonObject = (JSONObject) parser.parse(reader);
                usersArray = (JSONArray) jsonObject.get("users");
                reader.close();
            } else {
                jsonObject = new JSONObject();
                usersArray = new JSONArray();
                jsonObject.put("users", usersArray);
            }

            // Check kung may username
            for (Object obj : usersArray) {
                JSONObject user = (JSONObject) obj;
                if (user.get("username").equals(username)) {
                    JOptionPane.showMessageDialog(this, "Username already exists!");
                    return;
                }
            }

            // Nilalagay bagong user
            JSONObject newUser = new JSONObject();
            newUser.put("username", username);
            newUser.put("password", password);
            usersArray.add(newUser);

            // Sinesave sa JSON file
            FileWriter writer = new FileWriter(filePath);
            writer.write(jsonObject.toJSONString());
            writer.flush();
            writer.close();

            JOptionPane.showMessageDialog(this, "Account Created Successfully!");
            usernameField.setText("Create a Username");
            passwordField.setEchoChar((char)0);
            passwordField.setText("Create a Password");

        } catch (IOException | ParseException e) {
            JOptionPane.showMessageDialog(this, "An error occurred while saving the account.");
            e.printStackTrace();
        }
    }

    // Pinapakita sign up window
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SignUp().setVisible(true));
    }
}
