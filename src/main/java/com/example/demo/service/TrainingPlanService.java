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
            log.info("Nie uda??o si?? przypisa?? treningu");
        }
        TrainingPlan trainingPlan = trainingPlanRepository.findById(idd).get();
        final int purpose = Integer.valueOf(trainingForm.getPurpose().toString());
        trainingPlan.setPurpose(trainingForm.getPurpose());

        //POWT??RZENIA

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

        List<Exercise> lista = trainingDayService.buildExercisesList(List.of("Martwy ci??g","Pompka / Uginanie ramion w podporze przodem","Wios??owanie obur??cz z wykorzystaniem wyci??gu dolnego w pozycji siedz??cej","Podnoszenie sztangi nad g??ow?? stoj??c","Wyciskanie francuskie","Uginanie ramion z hantlami w pozycji siedz??cej","Allachy"));
        TrainingDay trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        fbw1.getTrainingDays().add(trainingDay);

        trainingPlanRepository.save(fbw1);

        // 2 FBW 2
        TrainingPlan fbw2 = new TrainingPlan();

        lista = trainingDayService.buildExercisesList(List.of("Przysiad","Pompka / Uginanie ramion w podporze przodem","Podnoszenie hantli nad g??ow?? w pozycji siedz??cej","Allachy","Uginanie ramion z hantlami w pozycji siedz??cej","Prostowanie ??okcia z hantlem zza g??owy"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        fbw2.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Martwy ci??g","Podnoszenie sztangielek le????c na ??awce prostej","Podci??ganie si?? na dr????ku","Uginanie ramion z hantlami w pozycji siedz??cej","Wyciskanie francuskie","Uginanie tu??owia le????c"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        fbw2.getTrainingDays().add(trainingDay);

        trainingPlanRepository.save(fbw2);

        // 3 FBW 3
        TrainingPlan fbw3 = new TrainingPlan();

        lista = trainingDayService.buildExercisesList(List.of("Wykroki n??g z hantlami","Wyciskanie sztangi na ??awce p??askiej","Podnoszenie sztangi nad g??ow?? stoj??c","Wios??owanie hantlem","Prostowanie ??okcia z hantlem zza g??owy"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        fbw3.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Przysiad","Pompka / Uginanie ramion w podporze przodem","Podnoszenie hantli nad g??ow?? w pozycji siedz??cej","Allachy","Uginanie ramion z hantlami w pozycji siedz??cej"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        fbw3.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Martwy ci??g","Podnoszenie sztangielek le????c na ??awce prostej","Podci??ganie si?? na dr????ku","Uginanie ramion z hantlami w pozycji siedz??cej","Wyciskanie francuskie"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        fbw3.getTrainingDays().add(trainingDay);

        trainingPlanRepository.save(fbw3);

        // 4 FBW 4
        TrainingPlan fbw4 = new TrainingPlan();

        lista = trainingDayService.buildExercisesList(List.of("Przysiad","Pompka / Uginanie ramion w podporze przodem","Podnoszenie hantli nad g??ow?? w pozycji siedz??cej","Allachy","Uginanie ramion z hantlami w pozycji siedz??cej"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        fbw4.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Wykroki n??g z hantlami","Wyciskanie sztangi na ??awce p??askiej","Podnoszenie sztangi nad g??ow?? stoj??c","Wios??owanie hantlem","Prostowanie ??okcia z hantlem zza g??owy"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        fbw4.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Martwy ci??g","Podnoszenie sztangielek le????c na ??awce prostej","Podci??ganie si?? na dr????ku","Uginanie ramion z hantlami w pozycji siedz??cej","Wyciskanie francuskie"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        fbw4.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Prostowanie n??g na maszynie","Wios??owanie obur??cz z wykorzystaniem wyci??gu dolnego w pozycji siedz??cej","Unoszenie n??g na dr????ku","Wyciskanie francuskie","Uginanie ramion z hantlami w pozycji siedz??cej"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        fbw4.getTrainingDays().add(trainingDay);

        trainingPlanRepository.save(fbw4);

        // 5 FBW 5
        TrainingPlan fbw5 = new TrainingPlan();

        lista = trainingDayService.buildExercisesList(List.of("Martwy ci??g","Podnoszenie sztangielek le????c na ??awce prostej","Podci??ganie si?? na dr????ku","Uginanie ramion z hantlami w pozycji siedz??cej"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        fbw5.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Prostowanie n??g na maszynie","Wios??owanie obur??cz z wykorzystaniem wyci??gu dolnego w pozycji siedz??cej","Unoszenie n??g na dr????ku","Wyciskanie francuskie"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        fbw5.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Wyciskanie sztangi na ??awce p??askiej","Allachy","Unoszenie n??g na dr????ku","Uginanie przedramienia opartego o kolano"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        fbw5.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Przysiad","Pompka / Uginanie ramion w podporze przodem","Podnoszenie hantli nad g??ow?? w pozycji siedz??cej","Uginanie ramion z hantlami w pozycji siedz??cej"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        fbw5.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Wykroki n??g z hantlami","Wyciskanie sztangi na ??awce p??askiej","Podnoszenie sztangi nad g??ow?? stoj??c","Prostowanie ??okcia z hantlem zza g??owy"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        fbw5.getTrainingDays().add(trainingDay);

        trainingPlanRepository.save(fbw5);

        // 6 FBW 6
        TrainingPlan fbw6 = new TrainingPlan();

        lista = trainingDayService.buildExercisesList(List.of("Przysiad","Pompka / Uginanie ramion w podporze przodem","Podnoszenie hantli nad g??ow?? w pozycji siedz??cej","Uginanie ramion z hantlami w pozycji siedz??cej"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        fbw6.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Wyciskanie sztangi na ??awce p??askiej","Allachy","Unoszenie n??g na dr????ku","Uginanie przedramienia opartego o kolano"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        fbw6.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Prostowanie n??g na maszynie","Wios??owanie obur??cz z wykorzystaniem wyci??gu dolnego w pozycji siedz??cej","Unoszenie n??g na dr????ku","Wyciskanie francuskie"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        fbw6.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Martwy ci??g","Podnoszenie sztangielek le????c na ??awce prostej","Podci??ganie si?? na dr????ku","Uginanie ramion z hantlami w pozycji siedz??cej"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        fbw6.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Wykroki n??g z hantlami","Wyciskanie sztangi na ??awce p??askiej","Podnoszenie sztangi nad g??ow?? stoj??c","Prostowanie ??okcia z hantlem zza g??owy"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        fbw6.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Podci??ganie si?? na dr????ku","Rozpi??tki z wykorzystaniem linek wyci??gu g??rnego w pozycji stoj??cej","Allachy","Wyciskanie francuskie"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        fbw6.getTrainingDays().add(trainingDay);

        trainingPlanRepository.save(fbw6);

        // 7 SPLIT 3
        TrainingPlan s7 = new TrainingPlan();

        lista = trainingDayService.buildExercisesList(List.of("Wyciskanie sztangi na ??awce p??askiej","Podnoszenie sztangielek le????c na ??awce prostej","Rozpi??tki z wykorzystaniem linek wyci??gu g??rnego w pozycji stoj??cej","Pompka / Uginanie ramion w podporze przodem","Wyciskanie francuskie","Prostowanie ??okcia z hantlem zza g??owy"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        s7.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Martwy ci??g","Wios??owanie obur??cz z wykorzystaniem wyci??gu dolnego w pozycji siedz??cej","Podci??ganie si?? na dr????ku","Wios??owanie hantlem","Uginanie ramion z hantlami w pozycji siedz??cej","Uginanie przedramienia opartego o kolano"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        s7.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Przysiad","Wykroki n??g z hantlami","Prostowanie n??g na maszynie","Unoszenie n??g na dr????ku","Uginanie tu??owia le????c","Allachy"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        s7.getTrainingDays().add(trainingDay);

        trainingPlanRepository.save(s7);

        // 8 SPLIT 4
        TrainingPlan s8 = new TrainingPlan();

        lista = trainingDayService.buildExercisesList(List.of("Wyciskanie sztangi na ??awce p??askiej","Podnoszenie sztangielek le????c na ??awce prostej","Rozpi??tki z wykorzystaniem linek wyci??gu g??rnego w pozycji stoj??cej","Pompka / Uginanie ramion w podporze przodem","Wyciskanie francuskie","Prostowanie ??okcia z hantlem zza g??owy"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        s8.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Martwy ci??g","Wios??owanie obur??cz z wykorzystaniem wyci??gu dolnego w pozycji siedz??cej","Podci??ganie si?? na dr????ku","Wios??owanie hantlem","Uginanie ramion z hantlami w pozycji siedz??cej","Uginanie przedramienia opartego o kolano"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        s8.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Przysiad","Wykroki n??g z hantlami","Prostowanie n??g na maszynie","Unoszenie n??g na dr????ku","Uginanie tu??owia le????c","Allachy"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        s8.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Podnoszenie sztangi nad g??ow?? stoj??c","Podnoszenie hantli nad g??ow?? w pozycji siedz??cej","Przyci??ganie sznura wyci??gu g??rnego do czo??a"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        s8.getTrainingDays().add(trainingDay);

        trainingPlanRepository.save(s8);

        // 9 SPLIT 5
        TrainingPlan s9 = new TrainingPlan();

        lista = trainingDayService.buildExercisesList(List.of("Wyciskanie sztangi na ??awce p??askiej","Podnoszenie sztangielek le????c na ??awce prostej","Rozpi??tki z wykorzystaniem linek wyci??gu g??rnego w pozycji stoj??cej","Pompka / Uginanie ramion w podporze przodem"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        s9.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Martwy ci??g","Wios??owanie obur??cz z wykorzystaniem wyci??gu dolnego w pozycji siedz??cej","Podci??ganie si?? na dr????ku","Wios??owanie hantlem"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        s9.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Przysiad","Wykroki n??g z hantlami","Prostowanie n??g na maszynie","Unoszenie n??g na dr????ku","Uginanie tu??owia le????c","Allachy"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        s9.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Podnoszenie sztangi nad g??ow?? stoj??c","Podnoszenie hantli nad g??ow?? w pozycji siedz??cej","Przyci??ganie sznura wyci??gu g??rnego do czo??a"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        s9.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Wyciskanie francuskie","Prostowanie ??okcia z hantlem zza g??owy","Uginanie ramion z hantlami w pozycji siedz??cej","Uginanie przedramienia opartego o kolano"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        s9.getTrainingDays().add(trainingDay);

        trainingPlanRepository.save(s9);

        // 10 PP 2
        TrainingPlan pp10 = new TrainingPlan();

        lista = trainingDayService.buildExercisesList(List.of("Przysiad","Wykroki n??g z hantlami","Wyciskanie sztangi na ??awce p??askiej","Pompka / Uginanie ramion w podporze przodem","Podnoszenie sztangi nad g??ow?? stoj??c","Wyciskanie francuskie"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        pp10.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Martwy ci??g","Podci??ganie si?? na dr????ku","Wios??owanie hantlem","Przyci??ganie sznura wyci??gu g??rnego do czo??a","Uginanie ramion z hantlami w pozycji siedz??cej","Unoszenie n??g na dr????ku"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        pp10.getTrainingDays().add(trainingDay);

        trainingPlanRepository.save(pp10);

        // 11 PP 3
        TrainingPlan pp11 = new TrainingPlan();

        lista = trainingDayService.buildExercisesList(List.of("Przysiad","Wykroki n??g z hantlami","Wyciskanie sztangi na ??awce p??askiej","Pompka / Uginanie ramion w podporze przodem","Podnoszenie sztangi nad g??ow?? stoj??c"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        pp11.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Martwy ci??g","Podci??ganie si?? na dr????ku","Wios??owanie hantlem","Przyci??ganie sznura wyci??gu g??rnego do czo??a","Unoszenie n??g na dr????ku"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        pp11.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Wyciskanie francuskie","Prostowanie ??okcia z hantlem zza g??owy","Uginanie ramion z hantlami w pozycji siedz??cej","Uginanie przedramienia opartego o kolano"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        pp11.getTrainingDays().add(trainingDay);

        trainingPlanRepository.save(pp11);

        // 12 PP 4
        TrainingPlan pp12 = new TrainingPlan();

        lista = trainingDayService.buildExercisesList(List.of("Przysiad","Wykroki n??g z hantlami","Wyciskanie sztangi na ??awce p??askiej","Pompka / Uginanie ramion w podporze przodem","Wyciskanie francuskie"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        pp12.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Martwy ci??g","Podci??ganie si?? na dr????ku","Wios??owanie hantlem","Przyci??ganie sznura wyci??gu g??rnego do czo??a","Unoszenie n??g na dr????ku"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        pp12.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Przysiad","Wykroki n??g z hantlami","Wyciskanie sztangi na ??awce p??askiej","Pompka / Uginanie ramion w podporze przodem","Podnoszenie sztangi nad g??ow?? stoj??c"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        pp12.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Martwy ci??g","Podci??ganie si?? na dr????ku","Wios??owanie hantlem","Przyci??ganie sznura wyci??gu g??rnego do czo??a","Uginanie ramion z hantlami w pozycji siedz??cej"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        pp12.getTrainingDays().add(trainingDay);

        trainingPlanRepository.save(pp12);

        // 13 PP 5
        TrainingPlan pp13 = new TrainingPlan();

        lista = trainingDayService.buildExercisesList(List.of("Przysiad","Wykroki n??g z hantlami","Wyciskanie sztangi na ??awce p??askiej","Pompka / Uginanie ramion w podporze przodem","Podnoszenie sztangi nad g??ow?? stoj??c"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        pp13.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Martwy ci??g","Podci??ganie si?? na dr????ku","Wios??owanie hantlem","Przyci??ganie sznura wyci??gu g??rnego do czo??a","Unoszenie n??g na dr????ku"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        pp13.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Wyciskanie francuskie","Prostowanie ??okcia z hantlem zza g??owy","Uginanie ramion z hantlami w pozycji siedz??cej","Uginanie przedramienia opartego o kolano"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        pp13.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Przysiad","Wykroki n??g z hantlami","Wyciskanie sztangi na ??awce p??askiej","Pompka / Uginanie ramion w podporze przodem","Podnoszenie sztangi nad g??ow?? stoj??c"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        pp13.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Martwy ci??g","Podci??ganie si?? na dr????ku","Wios??owanie hantlem","Przyci??ganie sznura wyci??gu g??rnego do czo??a","Unoszenie n??g na dr????ku"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        pp13.getTrainingDays().add(trainingDay);

        trainingPlanRepository.save(pp13);

        // 14 PP 6
        TrainingPlan pp14 = new TrainingPlan();

        lista = trainingDayService.buildExercisesList(List.of("Przysiad","Wykroki n??g z hantlami","Wyciskanie sztangi na ??awce p??askiej","Wyciskanie francuskie"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        pp14.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Martwy ci??g","Podci??ganie si?? na dr????ku","Przyci??ganie sznura wyci??gu g??rnego do czo??a","Unoszenie n??g na dr????ku"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        pp14.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Prostowanie n??g na maszynie","Wyciskanie sztangi na ??awce p??askiej","Pompka / Uginanie ramion w podporze przodem","Podnoszenie sztangi nad g??ow?? stoj??c"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        pp14.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Wios??owanie obur??cz z wykorzystaniem wyci??gu dolnego w pozycji siedz??cej","Podci??ganie si?? na dr????ku","Wios??owanie hantlem","Uginanie ramion z hantlami w pozycji siedz??cej"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        pp14.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Przysiad","Wyciskanie sztangi na ??awce p??askiej","Pompka / Uginanie ramion w podporze przodem","Wyciskanie francuskie"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        pp14.getTrainingDays().add(trainingDay);

        lista = trainingDayService.buildExercisesList(List.of("Martwy ci??g","Podci??ganie si?? na dr????ku","Wios??owanie hantlem","Przyci??ganie sznura wyci??gu g??rnego do czo??a"));
        trainingDay = trainingDayService.bindExercisesToTrainingDay(lista);
        pp14.getTrainingDays().add(trainingDay);

        trainingPlanRepository.save(pp14);

    }





}
