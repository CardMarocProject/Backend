package com.jobcard.applicatoion.Service;

import com.jobcard.applicatoion.Entity.User;

public interface IUserService {
    User createUser(User user);

    User findByCin(String cin);
}
