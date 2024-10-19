package com.mycompany.studentapi;


import com.mycompany.studentapi.repository.Student;
import com.mycompany.studentapi.repository.StudentRepository;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/students")
@Validated
public class StudentApiController {

    private final StudentRepository studentRepository;

    public StudentApiController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public ResponseEntity<?> list(@RequestParam("pageSize")
                                      @Min(value = 10, message = "Page size minimum is 10")
                                      @Max(value = 50, message = "Page size maximum is 50") Integer pageSize,
                                  @Positive(message = "Page number must be positive") Integer pageNum){

        List<Student> listStudents = studentRepository.findAll();
        if(listStudents.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(listStudents, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Student student){
        studentRepository.save(student);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> replace(@RequestBody Student student){

        if(studentRepository.existsById(student.getId())){
            studentRepository.save(student);
            return new ResponseEntity<>(student, HttpStatus.OK);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") @Positive(message = "Student ID must be grater than zero") Integer id){
        Student student = new Student(id);

        if(studentRepository.existsById(student.getId())){
            studentRepository.deleteById(student.getId());
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }
}
