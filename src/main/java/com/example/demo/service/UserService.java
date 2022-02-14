package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.RejestracjaRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service

public class UserService {

    private final UserRepository userRepository;

    public void reqister(RejestracjaRequest rejestracjaRequest) {
        Optional<User> user = userRepository.findByLogin(rejestracjaRequest.getLogin());
        if (user.isEmpty()) {
            User nUser = new User(rejestracjaRequest.getLogin(), rejestracjaRequest.getPassword());
            userRepository.save(nUser);
        } else    //Użytkownik istnieje
        {
            throw new RuntimeException("Użytkownik już istnieje");

        }

    }
    public User findById(Long id){
        return userRepository.findById(id).get();
    }
    public List<User> findAll(){
        return userRepository.findAll();
    }

   public void log_in(RejestracjaRequest rejestracjaRequest)
    {
        Optional<User> user = userRepository.findByLogin(rejestracjaRequest.getLogin());
        if (user.isEmpty())
        {
            throw new RuntimeException("Błędny login lub hasło");
        }
        else
        {
            if(!user.get().getPassword().equals(rejestracjaRequest.getPassword()))
            {
                throw new RuntimeException("Błędny login lub hasło");
            }
        }
    }
}
