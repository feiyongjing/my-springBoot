package com.github.feiyongjing.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Set;

public abstract class CommandTest {
    public abstract HashMap<String, String> getQuestionsAndAnswers();

    public void commandInputOutput(HashMap<String, String> questionsAndAnswers){
        Set<String> questions = questionsAndAnswers.keySet();
        int index = 1;
        int errorNum = 0;
        for (String question : questions) {
            System.out.println("第" + (index++) + "题");
            System.out.println(question);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String keyword = null;
            try {
                keyword = reader.readLine();
            } catch (IOException e) {
                throw new RuntimeException();
            }

            if (Objects.equals(keyword, "下一题")) continue;

            if (Objects.equals(keyword, questionsAndAnswers.get(question))) {
                System.out.println("回答正确");
            } else {
                errorNum++;
                System.out.println("回答错误");
            }
            System.out.println("正确答案：" + questionsAndAnswers.get(question));
        }
        System.out.println("一共" + questionsAndAnswers.size() + "题");
        System.out.println("error Number: " + errorNum);
        int errorRate = (errorNum * 100) % questionsAndAnswers.size() == 0 ? (errorNum * 100) / questionsAndAnswers.size() : (errorNum * 100) / questionsAndAnswers.size() + 1;
        System.out.println("错误率" + errorRate + "%");
    }
}
