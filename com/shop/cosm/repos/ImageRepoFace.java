package com.shop.cosm.repos;

import com.shop.cosm.domain.ImageFace;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepoFace extends CrudRepository<ImageFace, Long> {

    Optional<ImageFace> findByFilename(String filename);

}
