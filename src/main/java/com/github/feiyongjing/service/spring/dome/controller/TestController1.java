package com.github.feiyongjing.service.spring.dome.controller;

import com.github.feiyongjing.service.spring.annotation.Controller;
import com.github.feiyongjing.service.spring.annotation.mvc.*;

@Controller("/user")
public class TestController1 {
    @GetMapping
    public User get(@RequestParam(value = "name", require = true, defaultValue = "default name") String name,
                    @RequestParam("des") String des,
                    @RequestParam("age") Integer age) {
        return new User(name, des, age);
    }

    @GetMapping("/{age}")
    public User get(@PathVariable("age") Integer age) {
        return new User("aaa","bbb",age);
    }

    @PostMapping("/postTest")
    public UserDto create(@RequestBody User user) {
        return new UserDto(user.name,user.age);
    }

    static class UserDto{
        public String userName;
        public Integer age;

        public UserDto(String userName, Integer age) {
            this.userName = userName;
            this.age = age;
        }
    }

    static class User {
        public String name;
        public String des;
        public Integer age;

        public User() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public User(String name, String des, Integer age) {
            this.name = name;
            this.des = des;
            this.age = age;
        }
    }
}
