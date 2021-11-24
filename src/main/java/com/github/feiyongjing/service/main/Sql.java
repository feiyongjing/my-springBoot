package com.github.feiyongjing.service.main;

import com.github.feiyongjing.service.CommandTest;

import java.util.*;

public class Sql extends CommandTest {
    public static void main(String[] args){
        CommandTest commandTest=new Sql();
        commandTest.commandInputOutput(commandTest.getQuestionsAndAnswers());
    }

    @Override
    public HashMap<String, String> getQuestionsAndAnswers() {
        LinkedHashMap<String, String> questionsAndAnswers = new LinkedHashMap<>();
        //数据定义语言DDL
//        questionsAndAnswers.put("查看全部数据库", "show databases");
//        questionsAndAnswers.put("创建名字叫Java的数据库", "create database Java");
//        questionsAndAnswers.put("删除名字叫Java的数据库", "drop database Java");
//        questionsAndAnswers.put("选择名字叫Java的数据库", "use Java");
//        questionsAndAnswers.put("创建名字叫class的数据表，列有主键ID、\uFEFF\u200B类名、包名、版本、备注、创建与修改时间,\n" +
//                        "条件约束是主键自增、类名不为空不可重复、版本检查大于0默认值是1, 设置创建和修改默认时间",
//                "create table class(" +
//                        "id bigint auto_increment, " +
//                        "class_name varchar(100) not null unique, " +
//                        "package_name varchar(5000), " +
//                        "version int default 1, " +
//                        "remarks text, " +
//                        "created_at timestamp default now(), " +
//                        "updated_at timestamp default now(), " +
//                        "primary key (id), " +
//                        "check (version>0)" +
//                        ")");
//        questionsAndAnswers.put("删除名字叫class的数据表", "drop table class");
//        questionsAndAnswers.put("查看名字叫class的数据表结构", "show columns from class");
//
//        questionsAndAnswers.put("给class数据表id列添加主键索引", "alter table class add primar y key (id)");
//        questionsAndAnswers.put("普通索引的创建，对class表的class_name创建索引", "alter table class add index class_name_index (class_name)");
//        questionsAndAnswers.put("唯一索引的创建，对class表的class_name创建索引", "alter table class add unique index class_name_index (class_name)");
//        questionsAndAnswers.put("删除索引，删除class数据表中名为class_name_index索引", "drop index class_name_index on class");
//        questionsAndAnswers.put("查看class数据表中的索引", "show index from class");
//        questionsAndAnswers.put("修改表名，将class数据表的名字修改为java_class", "alter table class rename to java_class");
//        questionsAndAnswers.put("删除数据列,删除class数据表的package_name", "alter table class drop package_name");
//        questionsAndAnswers.put("增加数据列，对class数据表添加package_name列", "alter table class add package_name varchar(5000)");

//        数据操纵语言DML
        questionsAndAnswers.put("在class数据表插入一条数据，class_name、package_name的值分别是String、java.lang",
                "insert into class (class_name, package_name) values ('String', 'java.lang')");
        questionsAndAnswers.put("删除class数据表中class_name、package_name的值同时分别是String、java.lang的数据",
                "delete from class where class_name='String' and package_name='java.lang'");
        questionsAndAnswers.put("修改class数据表中class_name的值是String或者package_name的值是String、java.lang的version为2",
                "update class set version=2 where class_name='String' or package_name='java.lang'");
        questionsAndAnswers.put("选择查询，查询class数据表中class_name的值不是String或者值不是Long\n"+
                        "但是package_name的值是java.lang的数据",
                "select * from class where class_name not in ('String', 'Long') and package_name in (‘java.lang’)");
        questionsAndAnswers.put("分页查询，查询class数据表的第5页，每页有10条数据",
                "select * from class limit 40, 10");
        questionsAndAnswers.put("查询结果排序，查询class数据表的数据，先按照id升序排序，在按照version降序排列",
                "select * from class order by id asc, version desc");
        questionsAndAnswers.put("查询结果分组，查询class数据表中除了package_name分组列之外的class_name列的数据和条数，\n" +
                        "先按照package_name分组，在按照version分组,然后获取package_name分组数据数大于等于2条的组",
                "select package_name, group_concat(class_name), count(class_name) from class group by package_name, version having count(package_name)>=2");
        questionsAndAnswers.put("查询去重，查询class_name和package_name列，相同的数据去除重复的数据",
                "select distinct class_name, package_name from class");
        questionsAndAnswers.put("两表内连接，只查询order和goods表连接列数据相等的数据",
                "select * from `order` join goods on `order`.goods_id=goods.id");
        questionsAndAnswers.put("order和goods表左连接，查询order表中的所有数据，结果列中goods表不存在的数据为null",
                "select * from `order` left join goods on `order`.goods_id=goods.id");
        questionsAndAnswers.put("order和goods表左连接，查询order表中的除goods表数据之外所有数据",
                "select * from `order` left join goods on `order`.goods_id=goods.id where goods.id is null");
        return questionsAndAnswers;
    }
}
