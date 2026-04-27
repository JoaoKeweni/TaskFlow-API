package com.taskflow.api.repository;

import com.taskflow.api.domain.entity.BoardColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardColumnRepository extends JpaRepository<BoardColumn, Long> {
}
