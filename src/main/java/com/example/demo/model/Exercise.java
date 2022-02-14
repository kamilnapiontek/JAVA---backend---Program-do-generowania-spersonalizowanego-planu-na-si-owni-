package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Data

public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(length = 1000)
    private String description;
    private int difficult;
    private int numberOfSeries;
    private int numberOfRepetitions=0;

    public Exercise (String name, String description, int difficult, int numberOfSeries)
    {
        this.name=name;
        this.description=description;
        this.difficult=difficult;
        this.numberOfSeries=numberOfSeries;
    }
}
