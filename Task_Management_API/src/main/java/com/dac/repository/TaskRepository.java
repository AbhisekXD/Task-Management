package com.dac.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dac.model.Task;
import com.dac.model.User;

public interface TaskRepository extends JpaRepository<Task, UUID> {

	List<Task> findByAssignedTo(User user);

}
