package com.example.quiz;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.quiz.vo.BasicRes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.quiz.constants.OptionType;
import com.example.quiz.repository.QuizDao;
import com.example.quiz.service.ifs.QuizService;
import com.example.quiz.vo.CreateReqOrUpdate;
import com.example.quiz.vo.Question;
import org.springframework.util.Assert;

@SpringBootTest
public class QuizServiceTests {

    @Autowired
    private QuizService quizService;

    @Autowired
    private QuizDao quizDao;

    @Test
    public void createTest() {
        List<Question> questionList = new ArrayList<>();
        questionList.add(new Question(1, "便當?", "健康參;不要", //
                OptionType.SINGLE_CHOICE.getType(), true));//
        questionList.add(new Question(1, "當當?", "雞腿;炸於", //
                OptionType.SINGLE_CHOICE.getType(), true));//
        questionList.add(new Question(1, "電話?", "分機;烤雞", //
                OptionType.SINGLE_CHOICE.getType(), true));//
        CreateReqOrUpdate req = new CreateReqOrUpdate("午餐吃?", "午餐吃??", //
                LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 1), questionList, true);
        BasicRes res = quizService.createOrUpdate(req);
        Assert.isTrue(res.getStatusCode() == 200, "create test false");
        // 刪除測試資料 TODO
    }

    @Test
    public void createNameErrorTest() {
        List<Question> questionList = new ArrayList<>();
        questionList.add(new Question(1, "便當?", "健康參;不要", //
                OptionType.SINGLE_CHOICE.getType(), true));//
        questionList.add(new Question(1, "當當?", "雞腿;炸於", //
                OptionType.SINGLE_CHOICE.getType(), true));//
        questionList.add(new Question(1, "電話?", "分機;烤雞", //
                OptionType.SINGLE_CHOICE.getType(), true));//
        CreateReqOrUpdate req = new CreateReqOrUpdate("", "午餐吃??", //
                LocalDate.of(2024, 6, 1), LocalDate.of(2024, 6, 1), questionList, true);
        BasicRes res = quizService.createOrUpdate(req);
        Assert.isTrue(res.getMessage().equalsIgnoreCase("Param name error"), "create test false");
    }

    @Test
    public void createStartDateErrorTest() {
        List<Question> questionList = new ArrayList<>();
        questionList.add(new Question(1, "便當?", "健康參;不要", //
                OptionType.SINGLE_CHOICE.getType(), true));//
        questionList.add(new Question(1, "當當?", "雞腿;炸於", //
                OptionType.SINGLE_CHOICE.getType(), true));//
        questionList.add(new Question(1, "電話?", "分機;烤雞", //
                OptionType.SINGLE_CHOICE.getType(), true));//
        // 今天是2024 5/30，所以開始日期不能是當天或之前
        CreateReqOrUpdate req = new CreateReqOrUpdate("午餐吃??", "午餐吃??", //
                LocalDate.of(2024, 5, 30), LocalDate.of(2024, 5, 30), questionList, true);
        BasicRes res = quizService.createOrUpdate(req);
        Assert.isTrue(res.getMessage().equalsIgnoreCase("Param start date error"),
                "create test false");
    }

    @Test
    public void createTest1() {
        List<Question> questionList = new ArrayList<>();
        questionList.add(new Question(1, "便當?", "健康參;不要", //
                OptionType.SINGLE_CHOICE.getType(), true));//
        // 測試name error
        CreateReqOrUpdate req = new CreateReqOrUpdate("", "午餐吃??", //
                LocalDate.of(2024, 6, 1),
                LocalDate.of(2024, 6, 1), questionList, true);
        BasicRes res = quizService.createOrUpdate(req);
        Assert.isTrue(res.getMessage().equalsIgnoreCase("Param name error"),
                "create test false!!!");
        // 測試start date error
        req = new CreateReqOrUpdate("午餐吃?", "午餐吃??", //
                LocalDate.of(2024, 5, 30),
                LocalDate.of(2024, 6, 1), questionList, true);
        res = quizService.createOrUpdate(req);
        Assert.isTrue(res.getMessage().equalsIgnoreCase("Param start date error"),
                "create test false");
        // 測試End date error
        req = new CreateReqOrUpdate("午餐吃?", "午餐吃??", //
                LocalDate.of(2024, 6, 30),
                LocalDate.of(2024, 5, 20), questionList, true);
        res = quizService.createOrUpdate(req);
        Assert.isTrue(res.getMessage().equalsIgnoreCase("Param end date error"),
                "create test false");
        // 剩餘的邏輯全部判斷完之後，最後才會是測試成功的情境
        req = new CreateReqOrUpdate("午餐吃?", "午餐吃??", //
                LocalDate.of(2024, 6, 1),
                LocalDate.of(2024, 6, 1), questionList, true);
        res = quizService.createOrUpdate(req);
        Assert.isTrue(res.getStatusCode() == 200,
                "create test false");
    }

    @Test
    public void test8() {
        String str = "AABCEEBDCDEEACBDAEE";
        List<String> list = List.of("A", "B", "C", "D", "E");
        // 計算list裡每個字母出現的次數
        // 建立一個map，key是字母，value是出現的次數
        Map<String, Integer> map = new HashMap<>();
        for(String item : list){
            String newStr = str.replaceAll(item, "");
            int count = str.length() - newStr.length();
            map.put(item, count);
            System.out.println(item + " : " + count);
        }


        // 將map裡的value初始化為0
        for (String s : list) {
            map.put(s, 0);
        }
        // 計算字母出現的次數
        for (char c : str.toCharArray()) {
            // 判斷字母是否在list裡
            if (map.containsKey(String.valueOf(c))) {
                // 若在，將map裡的value加1
                map.put(String.valueOf(c), map.get(String.valueOf(c)) + 1);
            }
        }
        System.out.println(map);
    }
}
