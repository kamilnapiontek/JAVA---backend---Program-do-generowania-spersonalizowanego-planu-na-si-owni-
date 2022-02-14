package com.example.demo.controller;

import com.example.demo.model.Exercise;
import com.example.demo.service.ExerciseService;
import com.example.demo.utils.ExerciseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // zapytania z API
@RequestMapping("exercise")  // ścieżka dostępów
@RequiredArgsConstructor

public class ExerciseController
{
    private final ExerciseService exerciseService;

    // ZAPISYWANIE ĆWICZENIA
    @PostMapping
    public void saveExercise(@RequestBody ExerciseRequest exerciseRequest)
    {
        exerciseService.saveExer(exerciseRequest);
    }
    @PostMapping("savemany")
    public void saveManyExercises(@RequestBody List<ExerciseRequest> exerciseRequestList)
    {
        exerciseService.saveManyEx(exerciseRequestList);
    }
    @GetMapping
    public List<Exercise> getExercises()
    {
        return exerciseService.pobierzExer();
    }
}
