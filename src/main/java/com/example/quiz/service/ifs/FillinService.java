package com.example.quiz.service.ifs;

import com.example.quiz.vo.*;

public interface FillinService {

    public BasicRes fillin(FillinReq req);

    public FeedbackRes feedback(FeedbackReq req);

    public StatisticsRes statistics(FeedbackReq req);
}
