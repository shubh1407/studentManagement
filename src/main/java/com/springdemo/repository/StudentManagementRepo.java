package com.springdemo.repository;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import com.springdemo.model.StudentRecord;
@Repository
public interface StudentManagementRepo extends JpaRepository<StudentRecord, Integer>{
	
	@Async
	public CompletableFuture<StudentRecord> findByRollNo(int rollNo);
	
	long deleteByRollNo(Integer rollNo);
}
