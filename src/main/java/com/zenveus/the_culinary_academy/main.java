package com.zenveus.the_culinary_academy;

import com.zenveus.the_culinary_academy.config.FactoryConfiguration;
import com.zenveus.the_culinary_academy.entity.Program;
import com.zenveus.the_culinary_academy.entity.Student;
import org.hibernate.Session;

import java.util.HashSet;

public class main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");

        //save student
        Session session = FactoryConfiguration.getInstance().getSession();
        session.beginTransaction();
        Program program = new Program();
        program.setProgramName("Diploma in Culinary Arts");
        program.setDuration("6 months");
        program.setFee(1500.00);
        program.setStudentPrograms(new HashSet<>()); // Assuming no student programs initially

        session.save(program);
        session.getTransaction().commit();
        session.close();
    }
}
