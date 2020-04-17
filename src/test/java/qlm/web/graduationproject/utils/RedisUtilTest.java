package qlm.web.graduationproject.utils;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.test.context.junit4.SpringRunner;
import qlm.web.graduationproject.GraduationProjectApplication;
import qlm.web.graduationproject.entity.good.Options;
import qlm.web.graduationproject.entity.manager.User;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author qlm
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes={GraduationProjectApplication.class})
class RedisUtilTest {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private KeyGenerator keyGenerator;

    @Test
    public void test(){
        Person person = new Person();
        person.setAge(23);
        person.setId("001");
        person.setName("zhangsan");
        redisUtil.set("persion-001",person);
        System.out.println(redisUtil.get("persion-001"));

    }
    @Test
    public void test1(){
        redisUtil.remove("persions");
        Person person = new Person();
        person.setAge(22);
        person.setId("001");
        person.setName("zhangsan");
        Person person1 = new Person();
        person1.setAge(23);
        person1.setId("002");
        person1.setName("lisi");
        Person person2 = new Person();
        person2.setAge(33);
        person2.setId("003");
        person2.setName("wangwu");
        Person person3 = new Person();
        person3.setAge(63);
        person3.setId("004");
        person3.setName("qiulangmeng");
        ArrayList<Object> persions = new ArrayList<>();
        persions.add(person);
        persions.add(person1);
        persions.add(person2);
        redisUtil.lSet("persions",persions);
        List<Object> objects = redisUtil.lGet("persions", 0, redisUtil.lGetListSize("persions"));
        for (Object o:objects) {
            System.out.println("object"+o);
        }
        redisUtil.lUpdateIndex("persions",0,person3);
        List<Object> objects1 = redisUtil.lGet("persions", 0, redisUtil.lGetListSize("persions"));
        for (Object o:objects1) {
            System.out.println("object1"+o);
        }


    }
    static class Person implements Serializable {
        private final long serialVersionUID = 1L;

        private String id;
        private String name;

        public Person() {
        }

        private int age;
        private String gender;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", age=" + age +
                    ", gender='" + gender + '\'' +
                    '}';
        }
    }
}