package com.example.blog.repository;

import com.example.blog.domain.ExternalComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExternalCommentRepository extends JpaRepository<ExternalComment, Long> {
}