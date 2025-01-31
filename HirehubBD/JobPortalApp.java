import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class JobPortalApp {

    private final HashMap<String, User> userData = new HashMap<>();
    private final ArrayList<Job> jobListings = new ArrayList<>();
    private final ArrayList<Application> applications = new ArrayList<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JobPortalApp app = new JobPortalApp();
            app.addSampleJobs(); // Add sample jobs
            app.showLoginPage(); // Start the login page
        });
    }

    private void addSampleJobs() {
        jobListings.add(new Job("Software Engineer", "TechCorp", "Develop cutting-edge software", 85000, "2025-01-20"));
        jobListings.add(new Job("Data Analyst", "DataSolutions", "Analyze complex data sets", 65000, "2025-02-15"));
        jobListings.add(new Job("Product Manager", "Innovate Inc.", "Lead product development teams", 95000, "2025-03-01"));
    }

    private void showLoginPage() {
        JFrame frame = new JFrame("Job Portal - Login");
        frame.setSize(700, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new GradientPanel();
        mainPanel.setLayout(new GridBagLayout());
        frame.add(mainPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Welcome to Job Portal");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(titleLabel, gbc);

        JLabel usernameLabel = createLabel("Username:");
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(usernameLabel, gbc);

        JTextField usernameField = createTextField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(usernameField, gbc);

        JLabel passwordLabel = createLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(passwordLabel, gbc);

        JPasswordField passwordField = createPasswordField();
        gbc.gridx = 1;
        gbc.gridy = 2;
        mainPanel.add(passwordField, gbc);

        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton loginButton = createButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(loginButton, gbc);

        JButton signupButton = createButton("Sign Up");
        gbc.gridy = 4;
        mainPanel.add(signupButton, gbc);

        JButton resetButton = createButton("Reset");
        gbc.gridy = 5;
        mainPanel.add(resetButton, gbc);

        JLabel feedbackLabel = new JLabel("", SwingConstants.CENTER);
        feedbackLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        feedbackLabel.setForeground(Color.YELLOW);
        gbc.gridy = 6;
        mainPanel.add(feedbackLabel, gbc);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (userData.containsKey(username) && userData.get(username).password.equals(password)) {
                User user = userData.get(username);
                frame.dispose();
                if ("Job Seeker".equals(user.userType)) {
                    showJobSeekerHome(user);
                } else {
                    showRecruiterHome(user);
                }
            } else {
                feedbackLabel.setText("Invalid credentials. Try again.");
                feedbackLabel.setForeground(Color.RED);
            }
        });

        signupButton.addActionListener(e -> {
            frame.dispose();
            showSignupPage();
        });

        resetButton.addActionListener(e -> {
            usernameField.setText("");
            passwordField.setText("");
            feedbackLabel.setText("");
        });

        frame.setVisible(true);
    }

    private void showSignupPage() {
        JFrame frame = new JFrame("Job Portal - Sign Up");
        frame.setSize(700, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new GradientPanel();
        mainPanel.setLayout(new GridBagLayout());
        frame.add(mainPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Sign Up");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(titleLabel, gbc);

        JTextField usernameField = createTextField();
        JLabel usernameLabel = createLabel("Username:");
        addField(mainPanel, usernameLabel, usernameField, gbc, 1);

        JPasswordField passwordField = createPasswordField();
        JLabel passwordLabel = createLabel("Password:");
        addField(mainPanel, passwordLabel, passwordField, gbc, 2);

        JComboBox<String> userTypeBox = new JComboBox<>(new String[]{"Job Seeker", "Recruiter"});
        userTypeBox.setFont(new Font("Arial", Font.PLAIN, 16));
        JLabel userTypeLabel = createLabel("");
        addField(mainPanel, userTypeLabel, userTypeBox, gbc, 3);

        JButton signupButton = createButton("Sign Up");
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridy = 4;
        mainPanel.add(signupButton, gbc);

        JButton backButton = createButton("Back to Login");
        gbc.gridy = 5;
        mainPanel.add(backButton, gbc);

        JButton resetButton = createButton("Reset");
        gbc.gridy = 6;
        mainPanel.add(resetButton, gbc);

        JLabel feedbackLabel = new JLabel("", SwingConstants.CENTER);
        feedbackLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        feedbackLabel.setForeground(Color.YELLOW);
        gbc.gridy = 7;
        mainPanel.add(feedbackLabel, gbc);

        signupButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String userType = (String) userTypeBox.getSelectedItem();

            if (!username.isEmpty() && !password.isEmpty() && !userData.containsKey(username)) {
                userData.put(username, new User(username, password, userType));
                feedbackLabel.setText("Account created successfully!");
                feedbackLabel.setForeground(Color.GREEN);
            } else {
                feedbackLabel.setText("Invalid details or user already exists.");
                feedbackLabel.setForeground(Color.RED);
            }
        });

        backButton.addActionListener(e -> {
            frame.dispose();
            showLoginPage();
        });

        resetButton.addActionListener(e -> {
            usernameField.setText("");
            passwordField.setText("");
            userTypeBox.setSelectedIndex(0);
            feedbackLabel.setText("");
        });

        frame.setVisible(true);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setForeground(Color.WHITE);
        return label;
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Arial", Font.PLAIN, 16));
        return textField;
    }

    private JPasswordField createPasswordField() {
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        return passwordField;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        return button;
    }

    private void addField(JPanel panel, JLabel label, JComponent field, GridBagConstraints gbc, int y) {
        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(label, gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void showJobSeekerHome(User user) {
        JFrame frame = new JFrame("Job Portal - Job Seeker Home");
        frame.setSize(700, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    
        JPanel mainPanel = new GradientPanel();
        mainPanel.setLayout(new BorderLayout());
        frame.add(mainPanel);
    
        // Title
        JLabel welcomeLabel = new JLabel("Welcome, " + user.username, SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE);
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);
    
        // Job Listings Table
        String[] columnNames = {"Job Title", "Company", "Description", "Salary", "Deadline"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for (Job job : jobListings) {
            model.addRow(new Object[]{job.title, job.company, job.description, "$" + job.salary, job.deadline});
        }
    
        JTable jobTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(jobTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
    
        // Bottom Panel with Buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    
        // Apply Button
        JButton applyButton = createButton("Apply for Job");
        applyButton.addActionListener(e -> {
            int selectedRow = jobTable.getSelectedRow();
            if (selectedRow >= 0) {
                String jobTitle = (String) jobTable.getValueAt(selectedRow, 0);
                showJobApplicationForm(jobTitle);
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a job to apply.");
            }
        });
        bottomPanel.add(applyButton);
    
        // Logout Button
        JButton logoutButton = createButton("Logout");
        logoutButton.addActionListener(e -> {
            frame.dispose();
            showLoginPage();
        });
        bottomPanel.add(logoutButton);
    
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
    

    private void showJobApplicationForm(String jobTitle) {
        Job job = null;
        for (Job j : jobListings) {
            if (j.title.equals(jobTitle)) {
                job = j;
                break;
            }
        }

        if (job == null) {
            JOptionPane.showMessageDialog(null, "Job not found.");
            return;
        }

        JFrame frame = new JFrame("Apply for " + jobTitle);
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new GradientPanel();
        mainPanel.setLayout(new GridBagLayout());
        frame.add(mainPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Apply for " + jobTitle);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(titleLabel, gbc);

        JLabel nameLabel = createLabel("Name:");
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(nameLabel, gbc);

        JTextField nameField = createTextField();
        gbc.gridx = 1;
        mainPanel.add(nameField, gbc);

        JLabel cvLinkLabel = createLabel("CV Link:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(cvLinkLabel, gbc);

        JTextField cvLinkField = createTextField();
        gbc.gridx = 1;
        mainPanel.add(cvLinkField, gbc);

        JButton submitButton = new JButton("Submit Application");
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridwidth = 2;
        gbc.gridy = 3;
        mainPanel.add(submitButton, gbc);

        submitButton.addActionListener(e -> {
            String name = nameField.getText();
            String cvLink = cvLinkField.getText();
            if (!name.isEmpty() && !cvLink.isEmpty()) {
                applications.add(new Application(jobTitle, name, cvLink));
                JOptionPane.showMessageDialog(frame, "Application submitted successfully!");
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(frame, "Please fill out all fields.");
            }
        });

        frame.setVisible(true);
    }

    private void showRecruiterHome(User user) {
        JFrame frame = new JFrame("Job Portal - Recruiter Home");
        frame.setSize(700, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new GradientPanel();
        mainPanel.setLayout(new BorderLayout());
        frame.add(mainPanel);

        JTable jobTable = createJobTable();
        JScrollPane jobScrollPane = new JScrollPane(jobTable);
        mainPanel.add(jobScrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton postJobButton = createButton("Post a Job");
        postJobButton.addActionListener(e -> showPostJobForm(user));
        bottomPanel.add(postJobButton);

        JButton viewApplicantsButton = createButton("View Applicants");
        viewApplicantsButton.addActionListener(e -> {
            int selectedRow = jobTable.getSelectedRow();
            if (selectedRow >= 0) {
                String jobTitle = (String) jobTable.getValueAt(selectedRow, 0);
                showApplicantsForJob(jobTitle);
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a job to view applicants.");
            }
        });
        bottomPanel.add(viewApplicantsButton);

        JButton logoutButton = createButton("Logout");
        logoutButton.addActionListener(e -> {
            frame.dispose();
            showLoginPage();
        });
        bottomPanel.add(logoutButton);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private JTable createJobTable() {
        String[] columnNames = {"Job Title", "Company", "Description", "Salary", "Deadline"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        for (Job job : jobListings) {
            model.addRow(new Object[]{job.title, job.company, job.description, "$" + job.salary, job.deadline});
        }
        return new JTable(model);
    }
    private void showPostJobForm(User user) {
        JFrame frame = new JFrame("Post a Job");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    
        JPanel mainPanel = new GradientPanel();
        mainPanel.setLayout(new GridBagLayout());
        frame.add(mainPanel);
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
    
        JLabel titleLabel = new JLabel("Post a New Job");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(titleLabel, gbc);
    
        // Job title input
        mainPanel.add(createLabel("Job Title:"), setConstraints(gbc, 0, 1));
        JTextField jobTitleField = createTextField();
        jobTitleField.setPreferredSize(new Dimension(400, 30));
        mainPanel.add(jobTitleField, setConstraints(gbc, 1, 1));
    
        // Company input
        mainPanel.add(createLabel("Company:"), setConstraints(gbc, 0, 2));
        JTextField companyField = createTextField();
        companyField.setPreferredSize(new Dimension(400, 30));
        mainPanel.add(companyField, setConstraints(gbc, 1, 2));
    
        // Job description input - Increased size and visibility
        mainPanel.add(createLabel("Job Description:"), setConstraints(gbc, 0, 3));
        JTextArea descriptionField = new JTextArea(10, 45); // Increased rows and columns for better visibility
        descriptionField.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionField.setLineWrap(true);
        descriptionField.setWrapStyleWord(true);
        JScrollPane descriptionScroll = new JScrollPane(descriptionField);
        descriptionScroll.setPreferredSize(new Dimension(400, 150)); // Set a preferred size for better visibility
        mainPanel.add(descriptionScroll, setConstraints(gbc, 1, 3));
    
        // Salary input
        mainPanel.add(createLabel("Salary:"), setConstraints(gbc, 0, 4));
        JTextField salaryField = createTextField();
        salaryField.setPreferredSize(new Dimension(400, 30));
        mainPanel.add(salaryField, setConstraints(gbc, 1, 4));
    
        // Application Deadline input
        mainPanel.add(createLabel("Application Deadline (YYYY-MM-DD):"), setConstraints(gbc, 0, 5));
        JTextField deadlineField = createTextField();
        deadlineField.setPreferredSize(new Dimension(400, 30));
        mainPanel.add(deadlineField, setConstraints(gbc, 1, 5));
    
        // Submit button
        JButton submitButton = createButton("Submit Job Posting");
        gbc.gridwidth = 2;
        gbc.gridy = 6;
        gbc.gridx = 0;
        mainPanel.add(submitButton, gbc);
    
        submitButton.addActionListener(e -> {
            if (!validateFields(jobTitleField, companyField, descriptionField, salaryField, deadlineField, frame)) return;
    
            try {
                double salary = Double.parseDouble(salaryField.getText().trim());
                if (salary < 0) throw new NumberFormatException("Negative salary");
    
                jobListings.add(new Job(
                    jobTitleField.getText().trim(), 
                    companyField.getText().trim(),  
                    descriptionField.getText().trim(), 
                    salary, 
                    deadlineField.getText().trim()
                ));
                JOptionPane.showMessageDialog(frame, "Job posted successfully!");
                frame.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid salary.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    
        frame.setVisible(true);
    }
    
    private boolean validateFields(JTextField jobTitle, JTextField company, JTextArea description, JTextField salary, JTextField deadline, JFrame frame) {
        if (jobTitle.getText().trim().isEmpty() || company.getText().trim().isEmpty() || 
            description.getText().trim().isEmpty() || salary.getText().trim().isEmpty() || 
            deadline.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill out all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    private GridBagConstraints setConstraints(GridBagConstraints gbc, int x, int y) {
        gbc.gridwidth = 1;
        gbc.gridx = x;
        gbc.gridy = y;
        return gbc;
    }
    
    

    private void showApplicantsForJob(String jobTitle) {
        JFrame frame = new JFrame("Applicants for " + jobTitle);
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new GradientPanel();
        mainPanel.setLayout(new BorderLayout());
        frame.add(mainPanel);

        String[] columnNames = {"Applicant Name", "CV Link", "Application Time"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Application app : applications) {
            if (app.jobTitle.equals(jobTitle)) {
                model.addRow(new Object[]{app.applicantName, app.cvLink, app.applicationTime});
            }
        }

        JTable applicantTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(applicantTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(new Color(0, 102, 204));
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    class User {
        String username;
        String password;
        String userType;

        User(String username, String password, String userType) {
            this.username = username;
            this.password = password;
            this.userType = userType;
        }
    }

    class Job {
        String title;
        String company;
        String description;
        double salary;
        String deadline;

        Job(String title, String company, String description, double salary, String deadline) {
            this.title = title;
            this.company = company;
            this.description = description;
            this.salary = salary;
            this.deadline = deadline;
        }
    }

    class Application {
        String jobTitle;
        String applicantName;
        String cvLink;
        String applicationTime;

        Application(String jobTitle, String applicantName, String cvLink) {
            this.jobTitle = jobTitle;
            this.applicantName = applicantName;
            this.cvLink = cvLink;
            this.applicationTime = java.time.LocalDateTime.now().toString();
        }
    }
}
