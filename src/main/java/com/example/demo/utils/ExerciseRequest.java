package com.example.demo.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;

    @Getter
    @NoArgsConstructor
    public class ExerciseRequest {

        private String name;
        private String description;
        private int difficult;
        private int numberOfSeries;

        public ExerciseRequest (String name, String description, int difficult, int numberOfSeries)
        {
            this.name=name;
            this.description=description;
            this.difficult=difficult;
            this.numberOfSeries=numberOfSeries;
        }
    }

