package com.jobcard.applicatoion.Service.Imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobcard.applicatoion.Entity.User;
import com.jobcard.applicatoion.Repository.UserRepository;
import com.jobcard.applicatoion.Service.IUserService;

@Service
public class userService implements IUserService{
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        // TODO Auto-generated method stub
        return userRepository.save(user);
    }
}
