package com.example.quiz.service.impl;

import com.example.quiz.constants.OptionType;
import com.example.quiz.constants.ResponseMessage;
import com.example.quiz.entity.Quiz;
import com.example.quiz.repository.QuizDao;
import com.example.quiz.service.ifs.QuizService;
import com.example.quiz.vo.*;

import java.time.LocalDate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class QuizServiceImpl implements QuizService {

	@Autowired
	private QuizDao quizDao;

	@Override
	public BasicRes createOrUpdate(CreateReqOrUpdate req) {
		// 檢查參數
		BasicRes checkResult = checkParams(req);
		// checkResult = null 代表沒有錯誤，可以繼續執行下一步
		if (checkResult != null) {
			return checkResult;
		}
		// 因為Quiz中questions的資料格式是String,所以要將的List<Question>轉成String
		// 透過0objectMapper可以把物件(類別)轉成JSON格式的字串
		ObjectMapper mapper = new ObjectMapper();
		try {
			String questionStr = mapper.writeValueAsString(req.getQuestionList());
			// 若req.getId()>0,代表是更新,否則是新增
//            Optional<Quiz> op = quizDao.findById(req.getId());
			if (req.getId() > 0 && !quizDao.existsById(req.getId())) {
				return new BasicRes(ResponseMessage.UPDATE_ID_NOT_FOUND.getCode(),
						ResponseMessage.UPDATE_ID_NOT_FOUND.getMessage());
				}

				// 方法1: 透過 existingById: 回傳一個 bit 的值來更新
				// 方法2: 這邊要判斷從 req 帶進來的id是否真的有存在於DB中,
				// 因為若id不存在,後續程式碼在呼叫JPA的save方法時,會變成新增
			
			// 下面跟上面功能一樣
			// =================================================
//				boolean boo = quizDao.existsById(req.getId());
//				if (boo) {
//					return new BasicRes(ResponseMessage.UPDATE_ID_NOT_FOUND.getCode(),
//							ResponseMessage.UPDATE_ID_NOT_FOUND.getMessage());
//				}
//			}
//			Quiz quiz = new Quiz(req.getName(), req.getDescription(), req.getStartDate(), req.getEndDate(), questionStr,
//					req.isPublished());
			
			
			// ==================================================
			
			// newQuiz()中帶入req.getld()是PK,在呼叫save時,會先去檢查PK是否有存在於DB中、
			// 若存在一一>更新;不存在一一>新增|
			// //ceq中沒有該欄位時,預設是0,因為id的資料型態是|
			quizDao.save(new Quiz(req.getId(), req.getName(), req.getDescription(), req.getStartDate(),
					req.getEndDate(), questionStr, req.isPublished()));
		} catch (JsonProcessingException e) {
			return new BasicRes(ResponseMessage.JSON_PROCESSING_EXCEPTION.getCode(),
					ResponseMessage.JSON_PROCESSING_EXCEPTION.getMessage());
		}
		return new BasicRes(ResponseMessage.SUCCESS.getCode(), ResponseMessage.SUCCESS.getMessage());
	}

	private BasicRes checkParams(CreateReqOrUpdate req) {
		if (!StringUtils.hasText(req.getName())) {
			return new BasicRes(ResponseMessage.PARAM_QUIZ_NAME_ERROR.getCode(),
					ResponseMessage.PARAM_QUIZ_NAME_ERROR.getMessage());
		}

		if (!StringUtils.hasText(req.getDescription())) {
			return new BasicRes(ResponseMessage.PARAM_DESCRIPTION_ERROR.getCode(),
					ResponseMessage.PARAM_DESCRIPTION_ERROR.getMessage());
		}

		// LocalDate.now(): 取得系統當前時間
		// !req.getStartDate().isAfter(LocalDate.now()): 開始日期必須在系統當前時間之後
		// 判斷開始日期是否為空值或在系統當前時間(含)之前
		if (req.getStartDate() == null || !req.getStartDate().isAfter(LocalDate.now())) {
			return new BasicRes(ResponseMessage.PARAM_START_DATE_ERROR.getCode(),
					ResponseMessage.PARAM_START_DATE_ERROR.getMessage());
		}
		// 判斷結束日期是否為空值或在系統當前時間(含)之前
		if (req.getEndDate() == null || req.getEndDate().isBefore(req.getStartDate())) {
			return new BasicRes(ResponseMessage.PARAM_END_DATE_ERROR.getCode(),
					ResponseMessage.PARAM_END_DATE_ERROR.getMessage());
		}

		if (CollectionUtils.isEmpty(req.getQuestionList())) {
			return new BasicRes(ResponseMessage.PARAM_QUESTION_LIST_NOT_FOUND.getCode(),
					ResponseMessage.PARAM_QUESTION_LIST_NOT_FOUND.getMessage());
		}

		for (Question item : req.getQuestionList()) {
			if (item.getId() <= 0) {
				return new BasicRes(ResponseMessage.PARAM_QUESTION_ID_ERROR.getCode(),
						ResponseMessage.PARAM_QUESTION_ID_ERROR.getMessage());
			}

			if (!StringUtils.hasText(item.getTitle())) {
				return new BasicRes(ResponseMessage.PARAM_TITLE_ERROR.getCode(),
						ResponseMessage.PARAM_TITLE_ERROR.getMessage());
			}

			if (!StringUtils.hasText(item.getType())) {
				return new BasicRes(ResponseMessage.PARAM_TYPE_ERROR.getCode(),
						ResponseMessage.PARAM_TYPE_ERROR.getMessage());
			}

			// 當OptionType為單選或多選時，必須有選項
			// 但當OptionType為填空題時，選項可以為空值
			// 以下檢查OptionType為單選或多選時，選項是否為空值
			if (item.getType().equals(OptionType.SINGLE_CHOICE.getType())
					|| item.getType().equals(OptionType.MULTIPLE_CHOICE.getType())) {
				if (!StringUtils.hasText(item.getOptions())) {
					return new BasicRes(ResponseMessage.PARAM_OPTIONS_ERROR.getCode(),
							ResponseMessage.PARAM_OPTIONS_ERROR.getMessage());
				}

			}
		}
		return null;
	}

	@Override
	public SearchRes search(SearchReq req) {
		String name = req.getName();
		LocalDate start = req.getStartDate();
		LocalDate end = req.getEndDate();
		// 假設沒有name, start, end就把name設定為空字串
		// 然後空字串用jap的 containing()方法查詢，會導致所有資料都被查詢到
		// 總結就是，如果沒有輸入任何條件，就把name設定為空字串，查詢所有資料，所以要把name設定為null或空字串才行
		if (!StringUtils.hasText(name)) {
			name = "";
		}

		if (start == null) {
			start = LocalDate.of(1970, 1, 1);
		}

		if (end == null) {
			end = LocalDate.of(2999, 12, 31);
		}
		return new SearchRes(ResponseMessage.SUCCESS.getCode(), ResponseMessage.SUCCESS.getMessage(),
				quizDao.findByNameContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqual(name, start, end));
	}

	@Override
	public BasicRes delete(DeleteReq req) {
		// 參數檢查
		try {
			quizDao.deleteAllById(req.getIdlist());
		} catch (Exception e) {
			// deleteAllById方法中,id的值不存在時JPA會報錯
			// 因為在刪除之前，JPA會先檢查id是否存在，不存在就報錯
			// 但實際上也沒刪除無任何東西,所以就不需要就這個Exception處理
		}

//        if (!CollectionUtils.isEmpty(req.getIdlist())) {
//            quizDao.deleteAllById(req.getIdlist());
//        }
		return new BasicRes(ResponseMessage.SUCCESS.getCode(), ResponseMessage.SUCCESS.getMessage());
	}
}
