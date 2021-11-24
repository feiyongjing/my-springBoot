package com.github.feiyongjing.service.EnglishWordsFactoryImpl;

import com.github.feiyongjing.service.factory.EnglishWordsFactory;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class ThreeEnglishWords implements EnglishWordsFactory {
    @Override
    public HashMap<String, String> getEnglishWords() {
//        LinkedHashMap<String, String> questionsAndAnswers = new LinkedHashMap<>();
        HashMap<String, String> questionsAndAnswers = new HashMap<>();
        //// unit 1
//        questionsAndAnswers.put("钢笔", "pen");
//        questionsAndAnswers.put("铅笔", "pencil");
//        questionsAndAnswers.put("铅笔盒", "pencil-case or pencil box");
//        questionsAndAnswers.put("尺子", "ruler");
//        questionsAndAnswers.put("橡皮", "eraser");
//        questionsAndAnswers.put("蜡笔", "crayon");
//        questionsAndAnswers.put("书", "book");
//        questionsAndAnswers.put("包", "bag");
//        questionsAndAnswers.put("卷笔刀", "sharpener");
//        questionsAndAnswers.put("学校", "school");
//        questionsAndAnswers.put("名字", "name");
//
//        // unit 2
//        questionsAndAnswers.put("头", "head");
//        questionsAndAnswers.put("脸", "face");
//        questionsAndAnswers.put("鼻子", "nose");
//        questionsAndAnswers.put("嘴", "mouth");
//        questionsAndAnswers.put("眼睛", "eye");
//        questionsAndAnswers.put("耳朵", "ear");
//        questionsAndAnswers.put("胳膊", "arm");
//        questionsAndAnswers.put("手指", "finger");
//        questionsAndAnswers.put("手", "hand");
//        questionsAndAnswers.put("腿", "leg");
//        questionsAndAnswers.put("脚", "foot");
//        questionsAndAnswers.put("身体", "body");
//        questionsAndAnswers.put("武器；纹章；手臂的复数", "arms");
//        questionsAndAnswers.put("支架；腿的复数", "legs");
//        questionsAndAnswers.put("膝盖", "knee");
//        questionsAndAnswers.put("脚趾", "toe");
//        questionsAndAnswers.put("早晨好；上午好", "good morning");
//        questionsAndAnswers.put("这；这个", "this");
//        questionsAndAnswers.put("精密的；美好的", "nice");
//        questionsAndAnswers.put("见面；遇见", "meet");
//        questionsAndAnswers.put("去", "go");
//        questionsAndAnswers.put("好；行", "ok");
//        questionsAndAnswers.put("给", "to");
//        questionsAndAnswers.put("下午好；午后好", "good afternoon");
//        questionsAndAnswers.put("也；太", "too");
//
//        // unit 3
//        questionsAndAnswers.put("红色；红色的", "red");
//        questionsAndAnswers.put("绿色；绿色的", "green");
//        questionsAndAnswers.put("黄色；黄色的", "yellow");
//        questionsAndAnswers.put("黑色；黑色的", "black");
//        questionsAndAnswers.put("蓝色；蓝色的", "blue");
//        questionsAndAnswers.put("棕色；棕色的", "brown");
//        questionsAndAnswers.put("白色；白色的", "white");
//        questionsAndAnswers.put("橙色；橙色的", "orange");
//        questionsAndAnswers.put("粉红色", "pink");
//        questionsAndAnswers.put("紫色", "purple");
//        questionsAndAnswers.put("彩虹；五彩缤纷的；幻想", "rainbow");
//        questionsAndAnswers.put("变色", "colour");
//        questionsAndAnswers.put("颜色", "colours");
//        questionsAndAnswers.put("妈妈（口语）", "mom or mum");
//        questionsAndAnswers.put("爸爸", "dad");
//
//        // unit 4
//        questionsAndAnswers.put("鸭子", "duck");
//        questionsAndAnswers.put("猪", "pig");
//        questionsAndAnswers.put("猫", "cat");
//        questionsAndAnswers.put("熊；承受；容忍", "bear");
//        questionsAndAnswers.put("狗", "dog");
//        questionsAndAnswers.put("大象", "elephant");
//        questionsAndAnswers.put("猴子", "monkey");
//        questionsAndAnswers.put("鸟", "bird");
//        questionsAndAnswers.put("老虎", "tiger");
//        questionsAndAnswers.put("大熊猫", "panda");
//        questionsAndAnswers.put("蚂蚁", "ant");
//        questionsAndAnswers.put("动物园", "zoo");
//        questionsAndAnswers.put("动物","animal");
//        questionsAndAnswers.put("滑稽的，好笑的", "funny");
//
//        // unit 5
//        questionsAndAnswers.put("面包", "bread");
//        questionsAndAnswers.put("果汁", "juice");
//        questionsAndAnswers.put("蛋", "egg");
//        questionsAndAnswers.put("牛奶", "milk");
//        questionsAndAnswers.put("水", "water");
//        questionsAndAnswers.put("蛋糕", "cake");
//        questionsAndAnswers.put("鱼", "fish");
//        questionsAndAnswers.put("米饭", "rice");
//        questionsAndAnswers.put("冰", "ice");
//        questionsAndAnswers.put("奶油", "cream");
//        //// unit 6
//        questionsAndAnswers.put("一", "one");
//        questionsAndAnswers.put("二", "two");
//        questionsAndAnswers.put("三", "three");
//        questionsAndAnswers.put("四", "four");
//        questionsAndAnswers.put("五", "five");
//        questionsAndAnswers.put("六", "six");
//        questionsAndAnswers.put("七", "seven");
//        questionsAndAnswers.put("八", "eight");
//        questionsAndAnswers.put("九", "nine");
//        questionsAndAnswers.put("十", "ten");
//        questionsAndAnswers.put("盘子", "plate");
////        // 扩展词汇
//        // 熟悉的
        questionsAndAnswers.put("喂", "hello or hi");
        questionsAndAnswers.put("我是", "I'm or I am");
        questionsAndAnswers.put("我的", "my");
        questionsAndAnswers.put("再见", "goodbye or bye");
        questionsAndAnswers.put("什么", "what");
        questionsAndAnswers.put("是", "is");
        questionsAndAnswers.put("你的；你们的", "your");
        questionsAndAnswers.put("你；你们", "you");
        questionsAndAnswers.put("有", "have");
        questionsAndAnswers.put("嗡嗡声；急速上升", "zoom");
        questionsAndAnswers.put("单元", "unit");
        questionsAndAnswers.put("允许；让", "let");
        questionsAndAnswers.put("学习", "learn");
        questionsAndAnswers.put("唱歌", "sing");
        questionsAndAnswers.put("歌", "song");
        questionsAndAnswers.put("现在", "now");
        questionsAndAnswers.put("能；可以", "can");
        questionsAndAnswers.put("打开", "open");
        questionsAndAnswers.put("显示", "show");
        questionsAndAnswers.put("拉链；活力；精力", "zip");
        questionsAndAnswers.put("赞颂；儿歌", "chant");
        questionsAndAnswers.put("十足的；非常的", "very");
        questionsAndAnswers.put("礼物", "gift");
        questionsAndAnswers.put("做；制造", "make");
        questionsAndAnswers.put("主意；想法", "idea");
        questionsAndAnswers.put("和；与", "and");
        questionsAndAnswers.put("检查", "check");
        questionsAndAnswers.put("接触", "touch");
        questionsAndAnswers.put("故事；小说", "story");
        questionsAndAnswers.put("时间；时代", "time");
        questionsAndAnswers.put("感谢", "thank");
        questionsAndAnswers.put("哭声；叫声", "wow");
        questionsAndAnswers.put("苹果", "apple");
        questionsAndAnswers.put("长的；长时间","long");
        questionsAndAnswers.put("大的；重要的","big");
        questionsAndAnswers.put("单词", "word");
        questionsAndAnswers.put("谁", "who");
        questionsAndAnswers.put("安静一点；嘘", "shh");
        questionsAndAnswers.put("大约；四周", "around");
        questionsAndAnswers.put("地面；土地", "ground");
        questionsAndAnswers.put("高的；高级的","high");


//        questionsAndAnswers.put("看见；理解", "see");
//        questionsAndAnswers.put("看；期待；注意", "look");
//        questionsAndAnswers.put("听；倾听", "listen");
//
//        questionsAndAnswers.put("读", "read");
//        questionsAndAnswers.put("说；谈话", "talk");
//        questionsAndAnswers.put("讲；说明", "say");
//        questionsAndAnswers.put("写", "write");
//
//
//        questionsAndAnswers.put("这；那", "the");
//        questionsAndAnswers.put("在那里", "there");
//        questionsAndAnswers.put("在这里；此时","here");
//        questionsAndAnswers.put("然后；那么；于是；此外", "then");
//
//        questionsAndAnswers.put("玩耍；扮演", "play");
//        questionsAndAnswers.put("表现；行动；表演", "act");
//        questionsAndAnswers.put("优良的；健康的", "fine");
//        questionsAndAnswers.put("满意的；适当的", "well");
//        questionsAndAnswers.put("伟大的；重大的", "great");
//        questionsAndAnswers.put("摇动；怎", "shake");
//        questionsAndAnswers.put("摇动；波动", "wave");
//        questionsAndAnswers.put("用；支持", "with");
//        questionsAndAnswers.put("邮票；印记", "stamp");
//        questionsAndAnswers.put("木偶；傀儡", "puppet");
//        questionsAndAnswers.put("鼓掌", "clap");
//
//        questionsAndAnswers.put("在外；出现；出局", "out");
//
//        questionsAndAnswers.put("非常；很", "much");
//        questionsAndAnswers.put("使```重复利用", "recycle");
//        questionsAndAnswers.put("大声的", "aloud");
//        questionsAndAnswers.put("油漆；描绘；装饰", "paint");
//        questionsAndAnswers.put("字母；书信", "letters");
//        questionsAndAnswers.put("使发声；声音", "sound");
//        questionsAndAnswers.put("重复；复制", "repeat");
//        questionsAndAnswers.put("位于；站立", "stand");
//        questionsAndAnswers.put("位于；坐", "sit");
//        questionsAndAnswers.put("向下；下去", "down");
//
//        questionsAndAnswers.put("十字；交叉", "cross");
//        questionsAndAnswers.put("转动；转弯", "turn");
//        questionsAndAnswers.put("是（be的第二人称单复数现在时）", "are");
//        questionsAndAnswers.put("圆；周期；循环", "circle");
//        questionsAndAnswers.put("标记", "tick");
//        questionsAndAnswers.put("花；精华", "flower");
//        questionsAndAnswers.put("相似的；相同的","like");
//        questionsAndAnswers.put("肥的；胖的","fat");
//        questionsAndAnswers.put("天空","sky");
//        questionsAndAnswers.put("恋爱；亲爱的","love");
//        questionsAndAnswers.put("它；他","it");
//
//        questionsAndAnswers.put("又；再一次","again");
//
//        questionsAndAnswers.put("卡车；交易","truck");
//        questionsAndAnswers.put("年老的；陈旧的","old");
//        questionsAndAnswers.put("种田；务农；经营农场","farm");

//        questionsAndAnswers.put("跳跃；暴涨","jump");
//        questionsAndAnswers.put("女孩；少女","girl");
//        questionsAndAnswers.put("有；使","had");
//        questionsAndAnswers.put("一些；大约；某一","some");

//        questionsAndAnswers.put("兄；弟", "brother");
//        questionsAndAnswers.put("处处；到处","everywhere");
//        questionsAndAnswers.put("每个；各自", "each");
//        questionsAndAnswers.put("有用的；有帮助的", "useful");
//        questionsAndAnswers.put("表达", "expression");
//        questionsAndAnswers.put("","");
//        questionsAndAnswers.put("","");
//        questionsAndAnswers.put("","");
//        questionsAndAnswers.put("","");
//        questionsAndAnswers.put("","");
//        questionsAndAnswers.put("","");
//        questionsAndAnswers.put("","");
//        questionsAndAnswers.put("","");
//        questionsAndAnswers.put("","");
//        questionsAndAnswers.put("","");
//        questionsAndAnswers.put("","");
//        questionsAndAnswers.put("","");
//        questionsAndAnswers.put("","");
//        questionsAndAnswers.put("","");
//        questionsAndAnswers.put("","");
//        questionsAndAnswers.put("","");
//        questionsAndAnswers.put("","");
//        questionsAndAnswers.put("","");
//        questionsAndAnswers.put("","");
//        questionsAndAnswers.put("","");
//        questionsAndAnswers.put("","");
//        questionsAndAnswers.put("","");
//        questionsAndAnswers.put("","");
//        questionsAndAnswers.put("","");
//        questionsAndAnswers.put("","");
//        questionsAndAnswers.put("","");
//        questionsAndAnswers.put("","");
//        questionsAndAnswers.put("","");
//        questionsAndAnswers.put("","");
//        questionsAndAnswers.put("","");
//        questionsAndAnswers.put("","");
//        questionsAndAnswers.put("","");
//        questionsAndAnswers.put("","");
//        questionsAndAnswers.put("","");
//        questionsAndAnswers.put("","");
//        questionsAndAnswers.put("","");
//        questionsAndAnswers.put("","");
//        questionsAndAnswers.put("","");
//        questionsAndAnswers.put("","");
//        questionsAndAnswers.put("","");
//        questionsAndAnswers.put("","");
//        questionsAndAnswers.put("","");
//        questionsAndAnswers.put("","");
//        questionsAndAnswers.put("","");
//        questionsAndAnswers.put("","");
//        questionsAndAnswers.put("","");
//        questionsAndAnswers.put("","");
//        questionsAndAnswers.put("","");
//        questionsAndAnswers.put("","");
//        questionsAndAnswers.put("","");

        return questionsAndAnswers;
    }
}
