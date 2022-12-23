package com.jobcard.applicatoion.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jobcard.applicatoion.Entity.Image;

public interface ImageRepository extends JpaRepository<Image,Long>{
     
}
