package com.example.quiz.constants;

public enum ResponseMessage {
	SUCCESS(200, "Success!"),
	PARAM_QUIZ_NAME_ERROR(400, "Param quiz name error"),
	PARAM_DESCRIPTION_ERROR(400,"Param description error"),
	PARAM_START_DATE_ERROR(400,"Param start date error"),
	PARAM_END_DATE_ERROR(400,"Param end date error"),//
	PARAM_QUESTION_LIST_NOT_FOUND(404,"請填寫問題列表"),
	PARAM_QUESTION_ID_ERROR(400,"Param question id error"),
	PARAM_QUIZ_ID_ERROR(400,"Param quiz id error"),
	PARAM_TITLE_ERROR(400,"Param title error"),
	PARAM_OPTIONS_ERROR(400,"Param options error"),
	PARAM_TYPE_ERROR(400,"Param type error"),
	JSON_PROCESSING_EXCEPTION(400,"Json processing exception"),
	UPDATE_ID_NOT_FOUND(404,"Update id not found"),
	PARAM_NAME_IS_REQUIRED(400, "Param name is required"),
	PARAM_PHONE_IS_REQUIRED(400, "Param phone is required"),
	PARAM_EMAIL_IS_REQUIRED(400, "Param email is required"),
	PARAM_AGE_NOT_QUALIFIED(400, "Param age not qualified"),
	QUIZ_NOT_FOUND(404,"Quiz not found"),
	ANSWER_IS_REQUIRED(400,"Answer is required"),
	ANSWER_IS_NOT_MATCH(400,"Answer is not match"),
	ANSWER_OPTION_TYPE_IS_NOT_MATCH(400,"Answer option type is not match"),
	DUPLICATE_FILLIN(400,"Duplicate fillin"),
	FILLIN_LIST_SIZE_IS_NOT_MATCH(400,"Fillin list size is not match"),
	INVALID_QUESTION_ID(400,"Invalid question id"),
	;
	
	
	private int code;

	private String message;

	private ResponseMessage(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
	
	

}
