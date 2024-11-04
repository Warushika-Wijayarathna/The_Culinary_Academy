package com.zenveus.the_culinary_academy;

public class main {
    public static void main(String[] args) {
        // Get a session from the FactoryConfiguration
        Session session = FactoryConfiguration.getInstance().getSession();

        try {
            // Begin a transaction
            session.beginTransaction();

            // Your database operations here
            // For example, saving a new student
            Student newStudent = new Student();
            newStudent.setName("John Doe");
            // Set other properties as needed

            session.save(newStudent);

            // Commit the transaction
            session.getTransaction().commit();
        } catch (Exception e) {
            // Handle exceptions and rollback if needed
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            // Close the session
            session.close();
        }
    }
}

