package com.springdemo.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springdemo.model.StudentRecord;
import com.springdemo.repository.StudentManagementRepo;

@Service
public class StudentmanagementService {

	@Autowired
	private StudentManagementRepo studentManagementRepo;

	public void addStudent(StudentRecord studentRecord) throws ClassNotFoundException, SQLException {
		studentManagementRepo.save(studentRecord);
	}
	
	public void addAllStudent(List<StudentRecord> studentRecords) throws ClassNotFoundException, SQLException {
		studentManagementRepo.saveAll(studentRecords);
	}

	public List<StudentRecord> getListOfStudents() throws ClassNotFoundException, SQLException {
		return studentManagementRepo.findAll();
	}
	
	public Optional<StudentRecord> getStudentRecord(int rollNo) {

		try {
			CompletableFuture<StudentRecord> studentRecord1 = studentManagementRepo.findByRollNo(rollNo);
			CompletableFuture.allOf(studentRecord1);
			return Optional.ofNullable(studentRecord1.get());
		} catch (Exception e) {
			return Optional.empty();
		}
	}
	
	public List<StudentRecord> getStudentRecordForRollNos(List<Integer> rollNoList) {
		List<StudentRecord> result = new ArrayList<>();
		try {
			List<CompletableFuture<StudentRecord>> callableTasks = new ArrayList<>();
			for(int rollNo : rollNoList) {
				CompletableFuture<StudentRecord> studentRecord = studentManagementRepo.findByRollNo(rollNo);
				callableTasks.add(studentRecord);
			}
			CompletableFuture.allOf(callableTasks.toArray(new CompletableFuture[callableTasks.size()]));
			callableTasks.forEach(task -> {
				try {
					result.add(task.get());
				} catch (InterruptedException | ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			return result;
		} catch (Exception e) {
			return result;
		}
	}
	
	@Transactional
	public long deleteStudent(int rollNo) {
		try {
			System.out.println(rollNo);
			return studentManagementRepo.deleteByRollNo(rollNo);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}

	public Optional<StudentRecord> editStudentRecord(StudentRecord studentRecord) {
		StudentRecord st = null;
		try {
			st =studentManagementRepo.save(studentRecord);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return Optional.ofNullable(st);
	}
}
