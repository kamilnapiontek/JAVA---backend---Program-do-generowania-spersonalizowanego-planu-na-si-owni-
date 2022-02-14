package com.example.demo.service;

import com.example.demo.model.Exercise;
import com.example.demo.model.TrainingDay;
import com.example.demo.repository.ExerciseRepository;
import com.example.demo.repository.TrainingPlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service

public class TrainingDayService {
    private final ExerciseRepository exerciseRepository;
    private final TrainingPlanRepository trainingPlanRepository;

    public List<Exercise> buildExercisesList(List<String> names) {
        List<Exercise> lista=new ArrayList<>();
        for(String name : names){
            Exercise exerciseOptional=exerciseRepository.findExerciseByName(name).get(0).get();
            Exercise exForPlan = new Exercise();
            exForPlan.setName(exerciseOptional.getName());
            exForPlan.setDescription(exerciseOptional.getDescription());
            exForPlan.setDifficult(exerciseOptional.getDifficult());
            exForPlan.setNumberOfSeries(exerciseOptional.getNumberOfSeries());
            lista.add(exForPlan);
        }
        return lista;
    }
    public TrainingDay bindExercisesToTrainingDay(List<Exercise> exercises){
        TrainingDay trainingDay = new TrainingDay();
        trainingDay.setExercises(exercises);
        return trainingDay;
    }
}
