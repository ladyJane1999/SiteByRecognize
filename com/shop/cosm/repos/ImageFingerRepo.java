package com.shop.cosm.repos;


import com.shop.cosm.domain.ImageFinger;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ImageFingerRepo extends CrudRepository<ImageFinger, Long>  {
}
