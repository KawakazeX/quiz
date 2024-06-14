package com.example.quiz.controller;

import com.example.quiz.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.quiz.service.ifs.FillinService;
import com.example.quiz.service.ifs.QuizService;

@CrossOrigin
@RestController
public class QuizController {

    @Autowired
    private QuizService quizService;
    
    @Autowired
    private FillinService fillinService;

    @PostMapping(value = "quiz/create_update")
    public BasicRes create(@RequestBody CreateReqOrUpdate req) {
        return quizService.createOrUpdate(req);
    }

    @PostMapping(value = "quiz/search")
    public SearchRes search(@RequestBody SearchReq req) {
        return quizService.search(req);
    }

    @PostMapping(value = "quiz/delete")
    public BasicRes delete(@RequestBody DeleteReq req){
        return quizService.delete(req);
    }

    @PostMapping(value = "quiz/fillin")
    public BasicRes fillin(@RequestBody FillinReq req) {
        return fillinService.fillin(req);
    }

    @PostMapping(value = "quiz/feedback")
    public FeedbackRes feedback(@RequestBody FeedbackReq req){
        return fillinService.feedback(req);
    }

    @PostMapping(value = "quiz/statistics")
    public StatisticsRes statistics(@RequestBody FeedbackReq req){
        return fillinService.statistics(req);
    }

}
