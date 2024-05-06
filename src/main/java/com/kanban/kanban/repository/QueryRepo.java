package com.kanban.kanban.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kanban.kanban.entity.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QueryRepo extends JpaRepository<Query, Long> {
}
