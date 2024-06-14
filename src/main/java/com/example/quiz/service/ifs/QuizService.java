package com.example.quiz.service.ifs;

import com.example.quiz.vo.*;

public interface QuizService {


    public BasicRes createOrUpdate(CreateReqOrUpdate req);

    public SearchRes search(SearchReq req);

    public BasicRes delete(DeleteReq req);

}
