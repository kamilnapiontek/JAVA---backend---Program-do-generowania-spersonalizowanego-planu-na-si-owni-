package com.example.demo.service;

import com.example.demo.model.Exercise;
import com.example.demo.model.TrainingPlan;
import com.example.demo.repository.*;
import com.example.demo.utils.ExerciseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;
    private final TrainingPlanRepository trainingRepo;
    public void saveExer(ExerciseRequest exerciseRequest) {
        Exercise exercise = new Exercise(exerciseRequest.getName(), exerciseRequest.getDescription(),
                exerciseRequest.getDifficult(), exerciseRequest.getNumberOfSeries());

       // exercise.setTrainingPlan(trainingRepo.findById(1L).get());
        //findAll.get(0)
        exerciseRepository.save(exercise);
    }

    public List<Exercise> pobierzExer()
    {
        return exerciseRepository.findAll();
    }

    public void saveManyEx(List<ExerciseRequest> exerciseRequestList)
    {
        List<Exercise> exList= new ArrayList<>();
        for(ExerciseRequest exerciseRequest : exerciseRequestList)
        {
            Exercise exercise = new Exercise(exerciseRequest.getName(), exerciseRequest.getDescription(),
                    exerciseRequest.getDifficult(), exerciseRequest.getNumberOfSeries());
            exerciseRepository.save(exercise);
            exList.add(exercise);
        }
//        exerciseRepository.saveAll(exList);
    }
//    public Exercise findExerciseByName(String name)
//    {
//        Optional<Exercise> exerciseOptional=exerciseRepository.findExerciseByName(name);
//        if(exerciseOptional.isEmpty())
//        {
//            throw new NullPointerException("Brak ćwiczenia o danej nazwie");
//        }
//        else {
//            return new Exercise(exerciseOptional.get().getName(),exerciseOptional.get().getDescription(),
//                    exerciseOptional.get().getDifficult(),exerciseOptional.get().getNumberOfSeries());
//
//        }
//    }
}
