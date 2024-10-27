package com.coinsystem.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coinsystem.system.DTO.StudentDTO;
import com.coinsystem.system.controller.ApiResponse.ApiResponse;
import com.coinsystem.system.model.Student;
import com.coinsystem.system.service.interfaces.IStudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private IStudentService studentService;

    // Acho que os metodos register e register with teacher poderiam ser unificaods para evitar repetição de código
    // Considero essa uma refatoração relativamente fácil, só adicionar a verificação na camada de serviço
    // O codigo aqui na camada de controller está práticamente igual
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Student>> register(@RequestBody @Valid StudentDTO studentDTO) {
        try {
            Student student = studentService.register(studentDTO);
            ApiResponse<Student> response = new ApiResponse<Student>(true, "User registered succesfully", student);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            ApiResponse<Student> errorResponse = new ApiResponse<Student>(false, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    // Acho que não é uma boa prática utilizar camelCase para o nome do endpoint
    @PostMapping("/registerWithTeacher")
    public ResponseEntity<ApiResponse<Student>> registerWithTeacher(@RequestBody @Valid StudentDTO studentDTO) {
        try {
            Student student = studentService.registerWithTeacher(studentDTO);
            ApiResponse<Student> response = new ApiResponse<>(true, "User registered successfully with teacher",
                    student);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            ApiResponse<Student> errorResponse = new ApiResponse<>(false, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    // Nomenclatura: getAllStudents -> plural já que é uma lista    
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Student>>> getAllStudent() {
        try {
            List<Student> Student = studentService.getAllStudent();
            ApiResponse<List<Student>> response = new ApiResponse<>(true, "All Student fetched successfully", Student);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<Student>> errorResponse = new ApiResponse<>(false, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Student>> getUserById(@PathVariable Long id) {
        try {
            Student student = studentService.getStudentById(id);
            if (student != null) {
                ApiResponse<Student> response = new ApiResponse<>(true, "Student found successfully", student);
                return ResponseEntity.ok(response);
            } else {
                ApiResponse<Student> response = new ApiResponse<>(false, "Student not found", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            ApiResponse<Student> errorResponse = new ApiResponse<>(false, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    // A nomenclatura do método quebra o padrão das outras classes, sugiro renomear para updateStudent
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Student>> update(@PathVariable Long id,
            @RequestBody @Valid StudentDTO studentDTO) {
        try {
            Student updatedStudent = studentService.update(id, studentDTO);
            if (updatedStudent != null) {
                ApiResponse<Student> response = new ApiResponse<>(true, "Student updated successfully", updatedStudent);
                return ResponseEntity.ok(response);
            } else {
                ApiResponse<Student> response = new ApiResponse<>(false, "Student not found", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            ApiResponse<Student> errorResponse = new ApiResponse<>(false, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    // Nomenclatura para deleteStudent
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        try {
            boolean isDeleted = studentService.delete(id);
            if (isDeleted) {
                ApiResponse<Void> response = new ApiResponse<>(true, "Student deleted successfully", null);
                return ResponseEntity.ok(response);
            } else {
                ApiResponse<Void> response = new ApiResponse<>(false, "Student not found", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            ApiResponse<Void> errorResponse = new ApiResponse<>(false, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

}
