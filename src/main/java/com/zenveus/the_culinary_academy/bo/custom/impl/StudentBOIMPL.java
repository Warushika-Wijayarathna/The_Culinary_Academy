package com.zenveus.the_culinary_academy.bo.custom.impl;

import com.zenveus.the_culinary_academy.bo.custom.ProgramBO;
import com.zenveus.the_culinary_academy.bo.custom.StudentBO;
import com.zenveus.the_culinary_academy.dao.DAOFactory;
import com.zenveus.the_culinary_academy.dao.custom.ProgramDAO;
import com.zenveus.the_culinary_academy.dao.custom.StudentDAO;
import com.zenveus.the_culinary_academy.dao.custom.UserDAO;
import com.zenveus.the_culinary_academy.dto.ProgramDto;
import com.zenveus.the_culinary_academy.dto.StudentDto;
import com.zenveus.the_culinary_academy.dto.StudentProgramDto;
import com.zenveus.the_culinary_academy.dto.UserDto;
import com.zenveus.the_culinary_academy.entity.Student;
import com.zenveus.the_culinary_academy.entity.User;

import java.util.ArrayList;
import java.util.List;

public class StudentBOIMPL implements StudentBO {
    StudentDAO studentDAO = (StudentDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.STUDENT);
    ProgramDAO programDAO = (ProgramDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.PROGRAM);
    @Override
    public void saveStudent(StudentDto studentDTO) {

    }

    @Override
    public void deleteStudent(String id) {

    }

    @Override
    public void updateStudent(StudentDto studentDTO) {

    }

    @Override
    public StudentDto searchStudentByStudentId(String id) {
        return null;
    }

    @Override
    public List<StudentDto> getAllStudents() {
        ArrayList<StudentDto> studentDtoList = new ArrayList<>();
        List<Student> allStudents = null;
        try {
            allStudents = studentDAO.getAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        for (Student student : allStudents) {
            studentDtoList.add(new StudentDto(student.getStudentId(), student.getStudentNic(), student.getDob(), student.getFullName(), student.getAddress(), student.getEmail(), student.getPhone(), student.getRegistrationDate(), student.getRegistrationTime()));
        }

        return studentDtoList;
    }

    @Override
    public boolean saveStudentAndPrograms(StudentDto studentDto, String[][] programDetailsArray, String[][] paymentDetailsArray) {
        Student student = new Student(studentDto.getStudentId(), studentDto.getStudentNic(), studentDto.getDob(), studentDto.getFullName(), studentDto.getAddress(), studentDto.getEmail(), studentDto.getPhone(), studentDto.getRegistrationDate(), studentDto.getRegistrationTime());

        String [][] combinedDetailsArray = combineProgramAndPaymentDetails(programDetailsArray, paymentDetailsArray);

        // print combinedDetailsArray
        for (int i = 0; i < combinedDetailsArray.length; i++) {
            for (int j = 0; j < combinedDetailsArray[i].length; j++) {
                System.out.print(combinedDetailsArray[i][j] + " ");
            }
            System.out.println();
        }

        // get the program fee and calculate the total due and the installment fee and enter data into a new StudentProgramDto object
        List<StudentProgramDto> studentProgramDto = setStudentProgramDetails(studentDto, combinedDetailsArray);





        return false;
    }

    private List<StudentProgramDto> setStudentProgramDetails(StudentDto student, String[][] combinedDetailsArray) {
        List<StudentProgramDto> studentProgramDtoList = new ArrayList<>();
        for (int i = 0; i < combinedDetailsArray.length; i++) {
            String programId = combinedDetailsArray[i][0];
            String paymentOption = combinedDetailsArray[i][1];
            String amount = combinedDetailsArray[i][2];

            // get the program fee and calculate the total due and the installment fee and enter data into a new StudentProgramDto object
            ProgramDto programDto;
            try {
                programDto = (ProgramDto) programDAO.exist(programId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            double programFee = programDto.getFee();
            double totalDue = programFee-Double.parseDouble(amount);
            double installmentFee = switch (paymentOption) {
                case "3 inst" -> totalDue / 3;
                case "6 inst" -> totalDue / 6;
                case "12 inst" -> totalDue / 12;
                default -> totalDue;
            };

            // enter data into a new StudentProgramDto object
            StudentProgramDto studentProgramDto = new StudentProgramDto();
            studentProgramDto.setStudent(student);
            studentProgramDto.setProgram(programDto);
            studentProgramDto.setPayOption(paymentOption);
            studentProgramDto.setInstallmentFee(installmentFee);
            studentProgramDto.setTotalDue(totalDue);

            studentProgramDtoList.add(studentProgramDto);
        }
        return studentProgramDtoList;
    }

    public String[][] combineProgramAndPaymentDetails(String[][] programDetailsArray, String[][] paymentDetailsArray) {
        // Assuming both arrays have the same length
        int length = programDetailsArray.length;
        String[][] combinedDetailsArray = new String[length][3]; // Create a new array with 3 columns

        for (int i = 0; i < length; i++) {
            // Extract details from programDetailsArray
            String programId = programDetailsArray[i][0];
            String paymentOption = programDetailsArray[i][2]; // Assuming payment option is at index 0 in paymentDetailsArray
            String amount = paymentDetailsArray[i][1]; // Assuming amount is at index 1 in paymentDetailsArray

            // Populate the combined array
            combinedDetailsArray[i][0] = programId;
            combinedDetailsArray[i][1] = paymentOption;
            combinedDetailsArray[i][2] = amount;
        }

        return combinedDetailsArray;
    }

}
