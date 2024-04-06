import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StudentManagementSystem extends JFrame {
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField studentIdField, sectionField, firstNameField, lastNameField, dobField, cgpaField, addressField, emailField, phoneField;
    private JComboBox<String> genderComboBox;
    private JButton addButton, viewButton, updateButton, deleteButton, backButton;

    public StudentManagementSystem() {
        setTitle("Student Management System");
        setSize(1080, 1080);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initializeComponents();
        createGUI();
        setVisible(true);
    }

    private void initializeComponents() {
        // Text fields for student details
        studentIdField = new JTextField(10);
        sectionField = new JTextField(10);
        firstNameField = new JTextField(20);
        lastNameField = new JTextField(20);
        dobField = new JTextField(10);
        cgpaField = new JTextField(5);
        addressField = new JTextField(50);
        emailField = new JTextField(30);
        phoneField = new JTextField(15);

        //Combo box for gender
        String[] genders = {"Male", "Female", "Others"};
        genderComboBox = new JComboBox<>(genders);

        //Buttons for various actions
        addButton = new JButton("Add");
        viewButton = new JButton("View");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        backButton = new JButton("Back");

        //Action listeners for buttons
        addButton.addActionListener(e -> addStudent());
        viewButton.addActionListener(e -> viewStudents());
        updateButton.addActionListener(e -> updateStudent());
        deleteButton.addActionListener(e -> deleteStudent());
        backButton.addActionListener(e -> backToMainScreen());

        //Table model and table
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
    }

    private void createGUI() {
        //Panels for input, buttons, and table
        JPanel inputPanel = new JPanel(new GridBagLayout());
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        JScrollPane scrollPane = new JScrollPane(table);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        //Adding components to input panel
        inputPanel.add(new JLabel("Student ID: "), gbc);
        gbc.gridx = 1;
        inputPanel.add(studentIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Section: "), gbc);
        gbc.gridx = 1;
        inputPanel.add(sectionField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("First Name: "), gbc);
        gbc.gridx = 1;
        inputPanel.add(firstNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Last Name: "), gbc);
        gbc.gridx = 1;
        inputPanel.add(lastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Date of Birth (MM-DD-YYYY): "), gbc);
        gbc.gridx = 1;
        inputPanel.add(dobField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Gender: "), gbc);
        gbc.gridx = 1;
        inputPanel.add(genderComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("CGPA: "), gbc);
        gbc.gridx = 1;
        inputPanel.add(cgpaField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Address: "), gbc);
        gbc.gridx = 1;
        inputPanel.add(addressField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Email: "), gbc);
        gbc.gridx = 1;
        inputPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Phone Number: "), gbc);
        gbc.gridx = 1;
        inputPanel.add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        inputPanel.add(addButton, gbc);

        gbc.gridy++;
        inputPanel.add(viewButton, gbc);

        // Adding update and delete buttons to input panel
        gbc.gridy++;
        inputPanel.add(updateButton, gbc);

        gbc.gridy++;
        inputPanel.add(deleteButton, gbc);

        // Adding back button to button panel
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(30, 5, 5, 5); // Adding some space between view and update buttons
        buttonPanel.add(backButton, gbc);

        // Setting layout and adding panels to frame
        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);

        // Shifting the table down by 10 pixels
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        add(scrollPane, BorderLayout.CENTER);

        add(buttonPanel, BorderLayout.SOUTH);

        // Hiding table and back button initially
        table.setVisible(false);
        backButton.setVisible(false);
    }


    // Method to add a new student
    private void addStudent() {
        String studentId = studentIdField.getText();
        String section = sectionField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String dobText = dobField.getText(); // Get the date text from the JTextField
        String gender = genderComboBox.getSelectedItem().toString();
        double cgpa = Double.parseDouble(cgpaField.getText());
        String address = addressField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();

        // Convert the date text to 'YYYY-MM-DD' format
        LocalDate dobDate = LocalDate.parse(dobText, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        String dob = dobDate.toString();

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/studenthub", "kundan", "kiit");
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO students1 (student_id, section, first_name, last_name, dob, gender, cgpa, address, email, phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

            // Setting parameters and executing query
            preparedStatement.setString(1, studentId);
            preparedStatement.setString(2, section);
            preparedStatement.setString(3, firstName);
            preparedStatement.setString(4, lastName);
            preparedStatement.setString(5, dob);
            preparedStatement.setString(6, gender);
            preparedStatement.setDouble(7, cgpa);
            preparedStatement.setString(8, address);
            preparedStatement.setString(9, email);
            preparedStatement.setString(10, phone);
            preparedStatement.executeUpdate();

            // Showing success message
            JOptionPane.showMessageDialog(this, "Student added successfully!");
        } catch (SQLException e) {
            // Showing error message if any
            JOptionPane.showMessageDialog(this, "Error adding student: " + e.getMessage());
        }
    }


    // Method to view all students
    private void viewStudents() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/studenthub", "kundan", "kiit");
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM students1");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            // Clearing table model
            tableModel.setRowCount(0);

            // Adding columns if not added already
            if (tableModel.getColumnCount() == 0) {
                tableModel.addColumn("Student ID");
                tableModel.addColumn("Section");
                tableModel.addColumn("First Name");
                tableModel.addColumn("Last Name");
                tableModel.addColumn("DOB");
                tableModel.addColumn("Gender");
                tableModel.addColumn("CGPA");
                tableModel.addColumn("Address");
                tableModel.addColumn("Email");
                tableModel.addColumn("Phone Number");
            }

            // Populating table with data
            while (resultSet.next()) {
                String studentId = resultSet.getString("student_id");
                String section = resultSet.getString("section");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String dob = resultSet.getString("dob");
                String gender = resultSet.getString("gender");
                double cgpa = resultSet.getDouble("cgpa");
                String address = resultSet.getString("address");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");

                tableModel.addRow(new Object[]{studentId, section, firstName, lastName, dob, gender, cgpa, address, email, phone});
            }

            // Showing table and back button, hiding other components
            table.setVisible(true);
            backButton.setVisible(true);
            clearInputFields();
            setFieldsVisibility(false);
        } catch (SQLException e) {
            // Showing error message if any
            JOptionPane.showMessageDialog(this, "Error viewing students: " + e.getMessage());
        }
    }

    // Method to update student details
    private void updateStudent() {
        String studentId = studentIdField.getText();
        String section = sectionField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String dobText = dobField.getText(); // Get the date text from the JTextField
        String gender = genderComboBox.getSelectedItem().toString();
        String cgpaText = cgpaField.getText(); // Get the CGPA text from the JTextField
        double cgpa = 0.0; // Initialize CGPA to a default value

        // Check if the CGPA field is not empty before parsing its value
        if (!cgpaText.isEmpty()) {
            cgpa = Double.parseDouble(cgpaText);
        }

        // Convert the date text to 'YYYY-MM-DD' format
        LocalDate dobDate = LocalDate.parse(dobText, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        String dob = dobDate.toString();

        String address = addressField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/studenthub", "kundan", "kiit");
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE students1 SET section = ?, first_name = ?, last_name = ?, dob = ?, gender = ?, cgpa = ?, address = ?, email = ?, phone = ? WHERE student_id = ?")) {

            // Setting parameters and executing query
            preparedStatement.setString(1, section);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, lastName);
            preparedStatement.setString(4, dob);
            preparedStatement.setString(5, gender);
            preparedStatement.setDouble(6, cgpa);
            preparedStatement.setString(7, address);
            preparedStatement.setString(8, email);
            preparedStatement.setString(9, phone);
            preparedStatement.setString(10, studentId);
            int affectedRows = preparedStatement.executeUpdate(); // Get the number of affected rows

            if (affectedRows > 0) {
                // Showing success message
                JOptionPane.showMessageDialog(this, "Student updated successfully!");
            } else {
                // Showing error message if no rows were updated
                JOptionPane.showMessageDialog(this, "Error updating student: Student ID not found!");
            }
        } catch (SQLException e) {
            // Showing error message if any
            JOptionPane.showMessageDialog(this, "Error updating student: " + e.getMessage());
        }
    }



    private void deleteStudent() {
        String studentId = studentIdField.getText();

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/studenthub", "kundan", "kiit");
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM students1 WHERE student_id = ?")) {

            // Setting parameter and executing query
            preparedStatement.setString(1, studentId);
            int affectedRows = preparedStatement.executeUpdate(); // Get the number of affected rows

            if (affectedRows > 0) {
                // Showing success message
                JOptionPane.showMessageDialog(this, "Student deleted successfully!");
            } else {
                // Showing error message if no rows were deleted
                JOptionPane.showMessageDialog(this, "Error deleting student: Student ID not found!");
            }
        } catch (SQLException e) {
            // Showing error message if any
            JOptionPane.showMessageDialog(this, "Error deleting student: " + e.getMessage());
        }
    }


    // Method to go back to main screen
    private void backToMainScreen() {
        // Hiding table and back button, showing other components
        table.setVisible(false);
        backButton.setVisible(false);
        clearInputFields();
        setFieldsVisibility(true);
    }


    // Method to clear input fields
    private void clearInputFields() {
        studentIdField.setText("");
        sectionField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        dobField.setText("");
        cgpaField.setText("");
        addressField.setText("");
        emailField.setText("");
        phoneField.setText("");
    }

    // Method to set visibility of input fields
    private void setFieldsVisibility(boolean visible) {
        studentIdField.setVisible(visible);
        sectionField.setVisible(visible);
        firstNameField.setVisible(visible);
        lastNameField.setVisible(visible);
        dobField.setVisible(visible);
        genderComboBox.setVisible(visible);
        cgpaField.setVisible(visible);
        addressField.setVisible(visible);
        emailField.setVisible(visible);
        phoneField.setVisible(visible);
        addButton.setVisible(visible);
        viewButton.setVisible(visible);
        updateButton.setVisible(visible);
        deleteButton.setVisible(visible);
    }

    // Main method to start the application
    public static void main(String[] args) {
        // Loading JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Creating and showing the student management system
        new StudentManagementSystem();
    }
}
