package com.jobcard.applicatoion.Service.Imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobcard.applicatoion.Entity.Image;
import com.jobcard.applicatoion.Repository.ImageRepository;
import com.jobcard.applicatoion.Service.IImageService;

@Service
public class imageService implements IImageService{
@Autowired 
private ImageRepository imageRepository;
    @Override
    public Image uploadeImage(Image image) {
        // TODO Auto-generated method stub
        return imageRepository.save(image);
    }
    
}
