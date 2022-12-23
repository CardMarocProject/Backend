package com.jobcard.applicatoion.Service;

import java.util.Optional;

import com.jobcard.applicatoion.Entity.Image;

public interface IImageService {
    Image uploadeImage(Image image);

    Optional<Image> findById(Long id);

}
