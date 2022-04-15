package com.springdemo.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springdemo.model.StudentRecord;
import com.springdemo.service.StudentmanagementService;

@RestController(value = "controller")
public class ControllerClass {

	@Autowired
	private StudentmanagementService studentmanagementService;

	@GetMapping(path = "/studentRecord/{rollNo}")
	public ResponseEntity<Object> getStudentRecordForRollNo( @PathVariable int rollNo) {
		try {
			
			Optional<StudentRecord> student = studentmanagementService.getStudentRecord(rollNo);
			if (student.isPresent()) {
				return ResponseEntity.status(HttpStatus.OK).body(student.get());
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("student record not found");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@GetMapping(path = "/studentRecord")
	public ResponseEntity<Object> getListOfStudentRecordForRollNos( @RequestParam("rollNos") List<Integer> list) {
		try {
			
			List<StudentRecord> students = studentmanagementService.getStudentRecordForRollNos(list);
			if (!students.isEmpty()) {
				return ResponseEntity.status(HttpStatus.OK).body(students);
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("student record not found");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@GetMapping(path = "/studentRecords")
	public ResponseEntity<Object> getStudentRecords() {
		try {
			List<StudentRecord> studentList = studentmanagementService.getListOfStudents();
			if (studentList != null && !studentList.isEmpty()) {
				return ResponseEntity.status(HttpStatus.OK).body(studentList);
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("student record not found");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@PostMapping(path = "/studentRecord/add",consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> AddStudentRecords(@Valid @RequestBody StudentRecord studentRecord) {
		try {
			studentmanagementService.addStudent(studentRecord);
			return ResponseEntity.status(HttpStatus.OK).body("Student Record added successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@PostMapping(path = "/studentRecord/addbatch",consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> AddBatchStudentRecords() {
		try {
			List<StudentRecord> studentRecords = Arrays.asList(new StudentRecord(1,"demo",1234)
					,new StudentRecord(2,"demo2",1234)
					,new StudentRecord(3,"demo3",1234)
					,new StudentRecord(4,"demo4",1234));
			studentmanagementService.addAllStudent(studentRecords);
			return ResponseEntity.status(HttpStatus.OK).body("Student Record added successfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@DeleteMapping("/studentRecord/delete")
	public ResponseEntity<Object> deleteStudentRecord(@RequestParam int rollNo) {
		try {
			long num = studentmanagementService.deleteStudent(rollNo);
			if (num>0) {
				return ResponseEntity.status(HttpStatus.OK).body("student record deleted successfully");
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("student record not found");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@PutMapping("/studentRecord/edit/{rollNo}")
	public ResponseEntity<Object> editStudentRecord(@PathVariable int rollNo, @RequestBody StudentRecord studentRecord) {
		try {
			Optional<StudentRecord> student = studentmanagementService.editStudentRecord(studentRecord);
			if (student.isPresent()) {
				return ResponseEntity.status(HttpStatus.OK).body("student record updated successfully");
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("student record not found");
			}
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}
