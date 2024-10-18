package com.mycompany.studentapi;


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

    private static List<Student> listStudents = new ArrayList<>();
    private static Integer studentId = 0;

    static {
        listStudents.add(new Student(++studentId, "Nam Ha Minh"));
        listStudents.add(new Student(++studentId, "Alex Steveson"));
    }

    @GetMapping
    public ResponseEntity<?> list(){
        if(listStudents.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(listStudents, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Student student){
        student.setId(++studentId);
        listStudents.add(student);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> replace(@RequestBody Student student){
        if(listStudents.contains(student)){
            int index = listStudents.indexOf(student);
            listStudents.set(index, student);
            return new ResponseEntity<>(student, HttpStatus.OK);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") @Positive(message = "Student ID must be grater than zero") Integer id){
        Student student = new Student(id);

        if(listStudents.contains(student)){
            listStudents.remove(student);
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }
}
