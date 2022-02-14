package com.example.demo.service;

import com.example.demo.model.Exercise;
import com.example.demo.model.TrainingDay;
import com.example.demo.model.TrainingPlan;
import com.example.demo.model.User;
import com.example.demo.repository.ExerciseRepository;
import com.example.demo.repository.TrainingPlanRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.ExerciseRequest;
import com.example.demo.utils.TrainingForm;
import com.example.demo.utils.TrainingRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainingPlanService {
    private final TrainingPlanRepository trainingPlanRepository;
    private final ExerciseRepository exerciseRepository;
    private final ExerciseService exerciseService;
    private final TrainingDayService trainingDayService;
    private final UserRepository userRepository;

    public void saveTra(TrainingRequest trainingRequest)
    {
        TrainingPlan trainingPlan = new TrainingPlan(trainingRequest.getPurpose());
        List<Exercise> list = new ArrayList<>();
        Exercise exercise = new Exercise();
//        exercise.setDescription(trainingRequest.getDescription());
//        exercise.setDifficult(trainingRequest.getDifficult());
//        exercise.setNumberOfSeries(trainingRequest.getNumberOfSeries());
//        exercise.setName(trainingRequest.getName());
        list.add(exercise);
      //  trainingPlan.setListExercise(list);
        trainingPlanRepository.save(trainingPlan);
    }

    public List<TrainingPlan> getA()
    {
        return trainingPlanRepository.findAll();
    }

    public void addExercise(ExerciseRequest exerciseRequest, long l) {
        TrainingPlan training = trainingPlanRepository.findById(l).get();
        List<Exercise> list = new ArrayList<>();
        Exercise exercise = new Exercise();
        exercise.setDescription(exerciseRequest.getDescription());
        exercise.setDifficult(exerciseRequest.getDifficult());
        exercise.setNumberOfSeries(exerciseRequest.getNumberOfSeries());
        exercise.setName(exerciseRequest.getName());
        list.add(exercise);
     //   training.setListExercise(list);
        trainingPlanRepository.save(training);
    }

    public TrainingPlan doTra(TrainingForm trainingForm) {
        Long idd=null;
        final Long howManyDays = trainingForm.getHowManyDays();
        if(trainingForm.getTypeOfTraining().equals("FBW"))
        {
            idd=howManyDays;
        } else if (trainingForm.getTypeOfTraining().equals("SPLIT"))
        {
            idd=howManyDays+4;
        } else if(trainingForm.getTypeOfTraining().equals("PP"))
        {
            idd=howManyDays+8;
        } else
        {
            log.info("Nie udało się przypisać treningu");
        }
        TrainingPlan trainingPlan = trainingPlanRepository.findById(idd).get();
        final int purpose = Integer.valueOf(trainingForm.getPurpose().toString());
        trainingPlan.setPurpose(trainingForm.getPurpose());

        //POWTÓRZENIA

        for(TrainingDay trainingDay : trainingPlan.getTrainingDays())
        {
            for(Exercise exercise : trainingDay.getExercises())
            {
                int dif = exercise.getDifficult();
                switch (purpose) {
                    case 0:
                        if(dif==0) exercise.setNumberOfRepetitions(12);
                        if(dif==1) exercise.setNumberOfRepetitions(8);
                        if(dif==2) exercise.setNumberOfRepetitions(5);
                        break;
                    case 1:
                        if(dif==0) exercise.setNumberOfRepetitions(15);
                        if(dif==1) exercise.setNumberOfRepetitions(12);
                        if(dif==2) exercise.setNumberOfRepetitions(8);
                        break;
                    case 2:
                        if(dif==0) exercise.setNumberOfRepetitions(30);
                        if(dif==1) exercise.setNumberOfRepetitions(16);
                        if(dif==2) exercise.setNumberOfRepetitions(12);
                        break;

                }
            }
        }
        final User user = userRepository.findByLogin(trainingForm.getUserLogin()).get();
        user.setTrainingPlan(trainingPlan);
        userRepository.save(user);

        return trainingPlan;

    }
    public void createAllTrainingForApplication() {
        // 1 FBW 1
        TrainingPlan fbw1 = new TrainingPlan();

        List<Exercise> lista = trainingDayService.buildExercisesList(List.of("Martwy ciąg","Pompka / Uginanie ramion w podporze przodem","Wiosłowanie oburącz z wykorzystaniem wyciągu dolnego w pozycji siedzącej","Podnoszenie sztangi nad głową stojąc","Wyciskanie francuskie","Uginanie ramion z hantlami w pozycji siedzącej","Allachy"));
        TrainingDay trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        fbw1.getTrainingDays().add(trainingDay);

        trainingPlanRepository.save(fbw1);

        // 2 FBW 2
        TrainingPlan fbw2 = new TrainingPlan();

        lista = trainingDayService.buildExercisesList(List.of("Przysiad","Pompka / Uginanie ramion w podporze przodem","Podnoszenie hantli nad głowę w pozycji siedzącej","Allachy","Uginanie ramion z hantlami w pozycji siedzącej","Prostowanie łokcia z hantlem zza głowy"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        fbw2.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Martwy ciąg","Podnoszenie sztangielek leżąc na ławce prostej","Podciąganie się na drążku","Uginanie ramion z hantlami w pozycji siedzącej","Wyciskanie francuskie","Uginanie tułowia leżąc"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        fbw2.getTrainingDays().add(trainingDay);

        trainingPlanRepository.save(fbw2);

        // 3 FBW 3
        TrainingPlan fbw3 = new TrainingPlan();

        lista = trainingDayService.buildExercisesList(List.of("Wykroki nóg z hantlami","Wyciskanie sztangi na ławce płaskiej","Podnoszenie sztangi nad głową stojąc","Wiosłowanie hantlem","Prostowanie łokcia z hantlem zza głowy"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        fbw3.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Przysiad","Pompka / Uginanie ramion w podporze przodem","Podnoszenie hantli nad głowę w pozycji siedzącej","Allachy","Uginanie ramion z hantlami w pozycji siedzącej"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        fbw3.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Martwy ciąg","Podnoszenie sztangielek leżąc na ławce prostej","Podciąganie się na drążku","Uginanie ramion z hantlami w pozycji siedzącej","Wyciskanie francuskie"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        fbw3.getTrainingDays().add(trainingDay);

        trainingPlanRepository.save(fbw3);

        // 4 FBW 4
        TrainingPlan fbw4 = new TrainingPlan();

        lista = trainingDayService.buildExercisesList(List.of("Przysiad","Pompka / Uginanie ramion w podporze przodem","Podnoszenie hantli nad głowę w pozycji siedzącej","Allachy","Uginanie ramion z hantlami w pozycji siedzącej"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        fbw4.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Wykroki nóg z hantlami","Wyciskanie sztangi na ławce płaskiej","Podnoszenie sztangi nad głową stojąc","Wiosłowanie hantlem","Prostowanie łokcia z hantlem zza głowy"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        fbw4.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Martwy ciąg","Podnoszenie sztangielek leżąc na ławce prostej","Podciąganie się na drążku","Uginanie ramion z hantlami w pozycji siedzącej","Wyciskanie francuskie"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        fbw4.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Prostowanie nóg na maszynie","Wiosłowanie oburącz z wykorzystaniem wyciągu dolnego w pozycji siedzącej","Unoszenie nóg na drążku","Wyciskanie francuskie","Uginanie ramion z hantlami w pozycji siedzącej"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        fbw4.getTrainingDays().add(trainingDay);

        trainingPlanRepository.save(fbw4);

        // 5 FBW 5
        TrainingPlan fbw5 = new TrainingPlan();

        lista = trainingDayService.buildExercisesList(List.of("Martwy ciąg","Podnoszenie sztangielek leżąc na ławce prostej","Podciąganie się na drążku","Uginanie ramion z hantlami w pozycji siedzącej"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        fbw5.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Prostowanie nóg na maszynie","Wiosłowanie oburącz z wykorzystaniem wyciągu dolnego w pozycji siedzącej","Unoszenie nóg na drążku","Wyciskanie francuskie"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        fbw5.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Wyciskanie sztangi na ławce płaskiej","Allachy","Unoszenie nóg na drążku","Uginanie przedramienia opartego o kolano"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        fbw5.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Przysiad","Pompka / Uginanie ramion w podporze przodem","Podnoszenie hantli nad głowę w pozycji siedzącej","Uginanie ramion z hantlami w pozycji siedzącej"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        fbw5.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Wykroki nóg z hantlami","Wyciskanie sztangi na ławce płaskiej","Podnoszenie sztangi nad głową stojąc","Prostowanie łokcia z hantlem zza głowy"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        fbw5.getTrainingDays().add(trainingDay);

        trainingPlanRepository.save(fbw5);

        // 6 FBW 6
        TrainingPlan fbw6 = new TrainingPlan();

        lista = trainingDayService.buildExercisesList(List.of("Przysiad","Pompka / Uginanie ramion w podporze przodem","Podnoszenie hantli nad głowę w pozycji siedzącej","Uginanie ramion z hantlami w pozycji siedzącej"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        fbw6.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Wyciskanie sztangi na ławce płaskiej","Allachy","Unoszenie nóg na drążku","Uginanie przedramienia opartego o kolano"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        fbw6.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Prostowanie nóg na maszynie","Wiosłowanie oburącz z wykorzystaniem wyciągu dolnego w pozycji siedzącej","Unoszenie nóg na drążku","Wyciskanie francuskie"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        fbw6.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Martwy ciąg","Podnoszenie sztangielek leżąc na ławce prostej","Podciąganie się na drążku","Uginanie ramion z hantlami w pozycji siedzącej"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        fbw6.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Wykroki nóg z hantlami","Wyciskanie sztangi na ławce płaskiej","Podnoszenie sztangi nad głową stojąc","Prostowanie łokcia z hantlem zza głowy"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        fbw6.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Podciąganie się na drążku","Rozpiętki z wykorzystaniem linek wyciągu górnego w pozycji stojącej","Allachy","Wyciskanie francuskie"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        fbw6.getTrainingDays().add(trainingDay);

        trainingPlanRepository.save(fbw6);

        // 7 SPLIT 3
        TrainingPlan s7 = new TrainingPlan();

        lista = trainingDayService.buildExercisesList(List.of("Wyciskanie sztangi na ławce płaskiej","Podnoszenie sztangielek leżąc na ławce prostej","Rozpiętki z wykorzystaniem linek wyciągu górnego w pozycji stojącej","Pompka / Uginanie ramion w podporze przodem","Wyciskanie francuskie","Prostowanie łokcia z hantlem zza głowy"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        s7.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Martwy ciąg","Wiosłowanie oburącz z wykorzystaniem wyciągu dolnego w pozycji siedzącej","Podciąganie się na drążku","Wiosłowanie hantlem","Uginanie ramion z hantlami w pozycji siedzącej","Uginanie przedramienia opartego o kolano"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        s7.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Przysiad","Wykroki nóg z hantlami","Prostowanie nóg na maszynie","Unoszenie nóg na drążku","Uginanie tułowia leżąc","Allachy"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        s7.getTrainingDays().add(trainingDay);

        trainingPlanRepository.save(s7);

        // 8 SPLIT 4
        TrainingPlan s8 = new TrainingPlan();

        lista = trainingDayService.buildExercisesList(List.of("Wyciskanie sztangi na ławce płaskiej","Podnoszenie sztangielek leżąc na ławce prostej","Rozpiętki z wykorzystaniem linek wyciągu górnego w pozycji stojącej","Pompka / Uginanie ramion w podporze przodem","Wyciskanie francuskie","Prostowanie łokcia z hantlem zza głowy"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        s8.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Martwy ciąg","Wiosłowanie oburącz z wykorzystaniem wyciągu dolnego w pozycji siedzącej","Podciąganie się na drążku","Wiosłowanie hantlem","Uginanie ramion z hantlami w pozycji siedzącej","Uginanie przedramienia opartego o kolano"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        s8.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Przysiad","Wykroki nóg z hantlami","Prostowanie nóg na maszynie","Unoszenie nóg na drążku","Uginanie tułowia leżąc","Allachy"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        s8.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Podnoszenie sztangi nad głową stojąc","Podnoszenie hantli nad głowę w pozycji siedzącej","Przyciąganie sznura wyciągu górnego do czoła"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        s8.getTrainingDays().add(trainingDay);

        trainingPlanRepository.save(s8);

        // 9 SPLIT 5
        TrainingPlan s9 = new TrainingPlan();

        lista = trainingDayService.buildExercisesList(List.of("Wyciskanie sztangi na ławce płaskiej","Podnoszenie sztangielek leżąc na ławce prostej","Rozpiętki z wykorzystaniem linek wyciągu górnego w pozycji stojącej","Pompka / Uginanie ramion w podporze przodem"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        s9.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Martwy ciąg","Wiosłowanie oburącz z wykorzystaniem wyciągu dolnego w pozycji siedzącej","Podciąganie się na drążku","Wiosłowanie hantlem"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        s9.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Przysiad","Wykroki nóg z hantlami","Prostowanie nóg na maszynie","Unoszenie nóg na drążku","Uginanie tułowia leżąc","Allachy"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        s9.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Podnoszenie sztangi nad głową stojąc","Podnoszenie hantli nad głowę w pozycji siedzącej","Przyciąganie sznura wyciągu górnego do czoła"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        s9.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Wyciskanie francuskie","Prostowanie łokcia z hantlem zza głowy","Uginanie ramion z hantlami w pozycji siedzącej","Uginanie przedramienia opartego o kolano"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        s9.getTrainingDays().add(trainingDay);

        trainingPlanRepository.save(s9);

        // 10 PP 2
        TrainingPlan pp10 = new TrainingPlan();

        lista = trainingDayService.buildExercisesList(List.of("Przysiad","Wykroki nóg z hantlami","Wyciskanie sztangi na ławce płaskiej","Pompka / Uginanie ramion w podporze przodem","Podnoszenie sztangi nad głową stojąc","Wyciskanie francuskie"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        pp10.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Martwy ciąg","Podciąganie się na drążku","Wiosłowanie hantlem","Przyciąganie sznura wyciągu górnego do czoła","Uginanie ramion z hantlami w pozycji siedzącej","Unoszenie nóg na drążku"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        pp10.getTrainingDays().add(trainingDay);

        trainingPlanRepository.save(pp10);

        // 11 PP 3
        TrainingPlan pp11 = new TrainingPlan();

        lista = trainingDayService.buildExercisesList(List.of("Przysiad","Wykroki nóg z hantlami","Wyciskanie sztangi na ławce płaskiej","Pompka / Uginanie ramion w podporze przodem","Podnoszenie sztangi nad głową stojąc"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        pp11.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Martwy ciąg","Podciąganie się na drążku","Wiosłowanie hantlem","Przyciąganie sznura wyciągu górnego do czoła","Unoszenie nóg na drążku"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        pp11.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Wyciskanie francuskie","Prostowanie łokcia z hantlem zza głowy","Uginanie ramion z hantlami w pozycji siedzącej","Uginanie przedramienia opartego o kolano"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        pp11.getTrainingDays().add(trainingDay);

        trainingPlanRepository.save(pp11);

        // 12 PP 4
        TrainingPlan pp12 = new TrainingPlan();

        lista = trainingDayService.buildExercisesList(List.of("Przysiad","Wykroki nóg z hantlami","Wyciskanie sztangi na ławce płaskiej","Pompka / Uginanie ramion w podporze przodem","Wyciskanie francuskie"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        pp12.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Martwy ciąg","Podciąganie się na drążku","Wiosłowanie hantlem","Przyciąganie sznura wyciągu górnego do czoła","Unoszenie nóg na drążku"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        pp12.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Przysiad","Wykroki nóg z hantlami","Wyciskanie sztangi na ławce płaskiej","Pompka / Uginanie ramion w podporze przodem","Podnoszenie sztangi nad głową stojąc"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        pp12.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Martwy ciąg","Podciąganie się na drążku","Wiosłowanie hantlem","Przyciąganie sznura wyciągu górnego do czoła","Uginanie ramion z hantlami w pozycji siedzącej"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        pp12.getTrainingDays().add(trainingDay);

        trainingPlanRepository.save(pp12);

        // 13 PP 5
        TrainingPlan pp13 = new TrainingPlan();

        lista = trainingDayService.buildExercisesList(List.of("Przysiad","Wykroki nóg z hantlami","Wyciskanie sztangi na ławce płaskiej","Pompka / Uginanie ramion w podporze przodem","Podnoszenie sztangi nad głową stojąc"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        pp13.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Martwy ciąg","Podciąganie się na drążku","Wiosłowanie hantlem","Przyciąganie sznura wyciągu górnego do czoła","Unoszenie nóg na drążku"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        pp13.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Wyciskanie francuskie","Prostowanie łokcia z hantlem zza głowy","Uginanie ramion z hantlami w pozycji siedzącej","Uginanie przedramienia opartego o kolano"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        pp13.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Przysiad","Wykroki nóg z hantlami","Wyciskanie sztangi na ławce płaskiej","Pompka / Uginanie ramion w podporze przodem","Podnoszenie sztangi nad głową stojąc"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        pp13.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Martwy ciąg","Podciąganie się na drążku","Wiosłowanie hantlem","Przyciąganie sznura wyciągu górnego do czoła","Unoszenie nóg na drążku"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        pp13.getTrainingDays().add(trainingDay);

        trainingPlanRepository.save(pp13);

        // 14 PP 6
        TrainingPlan pp14 = new TrainingPlan();

        lista = trainingDayService.buildExercisesList(List.of("Przysiad","Wykroki nóg z hantlami","Wyciskanie sztangi na ławce płaskiej","Wyciskanie francuskie"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        pp14.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Martwy ciąg","Podciąganie się na drążku","Przyciąganie sznura wyciągu górnego do czoła","Unoszenie nóg na drążku"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        pp14.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Prostowanie nóg na maszynie","Wyciskanie sztangi na ławce płaskiej","Pompka / Uginanie ramion w podporze przodem","Podnoszenie sztangi nad głową stojąc"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        pp14.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Wiosłowanie oburącz z wykorzystaniem wyciągu dolnego w pozycji siedzącej","Podciąganie się na drążku","Wiosłowanie hantlem","Uginanie ramion z hantlami w pozycji siedzącej"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        pp14.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Przysiad","Wyciskanie sztangi na ławce płaskiej","Pompka / Uginanie ramion w podporze przodem","Wyciskanie francuskie"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        pp14.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Martwy ciąg","Podciąganie się na drążku","Wiosłowanie hantlem","Przyciąganie sznura wyciągu górnego do czoła"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        pp14.getTrainingDays().add(trainingDay);

        trainingPlanRepository.save(pp14);

    }





}
