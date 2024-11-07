package com.zenveus.the_culinary_academy.bo.custom;

import com.zenveus.the_culinary_academy.bo.SuperBO;
import com.zenveus.the_culinary_academy.dto.StudentDto;

public interface StudentBO extends SuperBO {
    void saveStudent(StudentDto studentDTO);

    void deleteStudent(String id);

    void updateStudent(StudentDto studentDTO);

    StudentDto searchStudentByStudentId(String id);
}
