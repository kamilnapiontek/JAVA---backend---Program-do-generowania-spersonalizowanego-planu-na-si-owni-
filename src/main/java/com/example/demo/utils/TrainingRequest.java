package com.example.demo.utils;

import com.example.demo.model.Exercise;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.OneToMany;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TrainingRequest {


    private Long purpose;
    private List<ExerciseRequest> listExercise;


    public TrainingRequest(Long purpose)
    {
        this.purpose=purpose;
    }
}
