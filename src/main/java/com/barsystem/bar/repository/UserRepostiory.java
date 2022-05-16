package com.barsystem.bar.repository;

import com.barsystem.bar.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepostiory extends JpaRepository<User,Long> {


}
