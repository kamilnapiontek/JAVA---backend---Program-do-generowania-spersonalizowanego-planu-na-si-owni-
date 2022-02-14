package com.example.demo.repository;

import com.example.demo.model.TrainingDay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingDayRepository extends JpaRepository<TrainingDay,Long> {
}
