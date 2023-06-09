package com.devops.webapp;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepo extends CrudRepository<Post,Long> {
    List<Post> findByProfileId(Long id);
}
