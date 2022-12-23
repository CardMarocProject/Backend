package com.jobcard.applicatoion.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobcard.applicatoion.Entity.User;

public interface UserRepository extends JpaRepository<User,Long> {
    
}
