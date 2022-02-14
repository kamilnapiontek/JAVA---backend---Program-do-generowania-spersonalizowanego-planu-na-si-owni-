package com.example.demo.controller;

import com.example.demo.model.TrainingPlan;
import com.example.demo.service.TrainingPlanService;
import com.example.demo.utils.ExerciseRequest;
import com.example.demo.utils.TrainingForm;
import com.example.demo.utils.TrainingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("trening")


public class TrainingPlanController {

    private final TrainingPlanService trainingPlanService;
    @PostMapping
    public void saveTraining (@RequestBody TrainingRequest trainingRequest)
    {

        trainingPlanService.saveTra(trainingRequest);
    }
    @GetMapping("getall")
    public List<TrainingPlan> getAll()
    {
        return trainingPlanService.getA();
    }

    @PostMapping("/addex")
    public void addExToTraining(@RequestBody ExerciseRequest exerciseRequest){
        trainingPlanService.addExercise(exerciseRequest, 1L);

    }
    @PostMapping("/przygotujtrening")
    public TrainingPlan doTraining(@RequestBody TrainingForm trainingForm)
    {
        return trainingPlanService.doTra(trainingForm);
    }
    @PostMapping("/addexs")
    public void addExercisesToTheBase() {

        trainingPlanService.createAllTrainingForApplication();
    }

}
