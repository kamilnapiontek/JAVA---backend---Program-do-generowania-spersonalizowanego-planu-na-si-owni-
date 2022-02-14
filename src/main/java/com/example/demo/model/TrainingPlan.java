package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor

public class TrainingPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    private List<TrainingDay> trainingDays=new ArrayList<>();
    private Long purpose;

    public TrainingPlan (Long purpose)
    {
        this.purpose=purpose;
    }

}
