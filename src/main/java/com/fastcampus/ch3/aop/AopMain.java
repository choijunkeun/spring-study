package com.fastcampus.ch3.aop;


import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AopMain {
    public static void main(String[] args) throws Exception{
        MyAdvice myAdvice = new MyAdvice();

        // MyClass 의 클래스객체를 얻어온다
        Class myClass = Class.forName("com.fastcampus.ch3.aop.MyClass");

        // 클래스 객체로부터 객체를 생성한다
        Object obj = myClass.newInstance();

        // 반복문을 이용해 myClass에 있는 메서드들을 하나씩 호출
        for(Method m : myClass.getDeclaredMethods()) {
            myAdvice.invoke(m, obj, null);

        }
    }
}


class MyAdvice {
    Pattern p = Pattern.compile("a.*"); // 패턴추가 , a로 시작하는것만

    boolean matches(Method m) {
        Matcher matcher = p.matcher(m.getName());
        return matcher.matches();
    }
    void invoke(Method m, Object obj, Object... args) throws Exception {
        if(m.getAnnotation(Transactional.class) != null)    //메서드에 에너테이션, Transactional이 붙어있는지?
            System.out.println("[before] {");

        m.invoke(obj, args);

        if(m.getAnnotation(Transactional.class) != null)    //메서드에 에너테이션, Transactional이 붙어있는지?
            System.out.println("}[after]");


//        if(matches(m))
//            System.out.println("}[after]");
    }
}


class MyClass {
    @Transactional
    public void aaa() {
        System.out.println("aaa()is called");
    }

    void aaa2() {
        System.out.println("aaa2()is called");
    }

    void bbb() {
        System.out.println("bbb()is called");
    }
}