package com.exercises.githubdata.repositories;

import com.exercises.githubdata.entities.GithubData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GithubDataRedisRepository extends CrudRepository<GithubData, String> {
}
