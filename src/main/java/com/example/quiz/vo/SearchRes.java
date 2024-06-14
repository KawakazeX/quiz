package com.example.quiz.vo;

import com.example.quiz.entity.Quiz;

import java.util.List;

public class SearchRes extends BasicRes {

    private List<Quiz> quizList;

    public SearchRes() {
        super();
    }

    public SearchRes(int statusCode, String message, List<Quiz> quizList) {
        super(statusCode, message);
        this.quizList = quizList;
    }

    public List<Quiz> getQuizList() {
        return quizList;
    }
}
