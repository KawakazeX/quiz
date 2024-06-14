package com.example.quiz.repository;

import com.example.quiz.entity.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResponseDao extends JpaRepository<Response, Integer> {

   public boolean existsByQuizIdAndPhone(int quizId, String phone);

   public List<Response> findByQuizId(int quizId);

}
