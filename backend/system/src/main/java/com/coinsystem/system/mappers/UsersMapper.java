package com.coinsystem.system.mappers;

import com.coinsystem.system.DTO.InstitutionEducationDTO;
import com.coinsystem.system.DTO.PartnerCompanyDTO;
import com.coinsystem.system.DTO.StudentDTO;
import com.coinsystem.system.DTO.TeacherDTO;
import com.coinsystem.system.DTO.UsersDTO;
import com.coinsystem.system.enums.UsersType;
import com.coinsystem.system.model.InstitutionEducation;
import com.coinsystem.system.model.PartnerCompany;
import com.coinsystem.system.model.Student;
import com.coinsystem.system.model.Teacher;
import com.coinsystem.system.model.Users;

public class UsersMapper {

    // Acho que vale a pena adicionar nesses mappers a validação dos dados já que ele é bastante usado
    // Centralizar as validações aqui me parece uma boa prática
    public static Users UserDtoToModel(UsersDTO userDTO) {
        return new Users(userDTO.name(), userDTO.email(), UsersType.USERS,userDTO.phone_number(), userDTO.password(),userDTO.address());
    }

    public static Student StudentDtoToModel(StudentDTO studentDTO) {
        return new Student(studentDTO.name(), studentDTO.email(), UsersType.STUDENT,studentDTO.phone_number(), studentDTO.password(),studentDTO.address(),studentDTO.cpf(),studentDTO.rg());
    }

    public static Teacher TeacherDtoToModel(TeacherDTO teacherDTO) {
        return new Teacher(teacherDTO.name(), teacherDTO.email(), UsersType.TEACHER,teacherDTO.phone_number(), teacherDTO.password(),teacherDTO.address(),teacherDTO.salary(),teacherDTO.department());
    }

    public static PartnerCompany PartnerCompanyDtoToModel(PartnerCompanyDTO partnerCompanyDTO) {
        return new PartnerCompany(partnerCompanyDTO.name(), partnerCompanyDTO.email(), UsersType.PARTNERCOMPANY,partnerCompanyDTO.phone_number(), partnerCompanyDTO.password(),partnerCompanyDTO.address(),partnerCompanyDTO.cnpj());
    }

    public static InstitutionEducation InstitutionEducationDtoToModel(InstitutionEducationDTO institutionEducationDTO) {
        return new InstitutionEducation(institutionEducationDTO.name(), institutionEducationDTO.email(), UsersType.INSTITUTIONEDUCATION,institutionEducationDTO.phone_number(), institutionEducationDTO.password(),institutionEducationDTO.address(),institutionEducationDTO.cnpj());
    }
    
}
