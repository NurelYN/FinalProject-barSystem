package com.barsystem.bar.repository;

import com.barsystem.bar.model.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableRepository extends JpaRepository<Table,Long> {

}
