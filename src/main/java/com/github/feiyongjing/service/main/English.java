package com.github.feiyongjing.service.main;

import com.github.feiyongjing.service.CommandTest;
import com.github.feiyongjing.service.EnglishWordsFactoryImpl.ComputerWords;
import com.github.feiyongjing.service.EnglishWordsFactoryImpl.FourEnglishWords;
import com.github.feiyongjing.service.EnglishWordsFactoryImpl.ThreeEnglishWords;
import com.github.feiyongjing.service.factory.EnglishWordsFactory;

import java.util.HashMap;

public class English extends CommandTest {
    public static void main(String[] args){
        CommandTest commandTest=new English();
        commandTest.commandInputOutput(commandTest.getQuestionsAndAnswers());
    }

    @Override
    public HashMap<String, String> getQuestionsAndAnswers() {
//        EnglishWordsFactory englishWordsFactory = new ComputerWords();
        EnglishWordsFactory englishWordsFactory=new ThreeEnglishWords();
//        EnglishWordsFactory englishWordsFactory = new FourEnglishWords();
        return englishWordsFactory.getEnglishWords();
    }
}
