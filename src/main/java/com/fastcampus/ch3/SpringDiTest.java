package com.fastcampus.ch3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;


//@Component("engine")
class Engine {}    // == <bean id="engine" class="com.fastcampus.ch3.Engine"/>
@Component class SuperEngine extends Engine {}
@Component class TurboEngine extends Engine {}
@Component class Door {}
@Component
class Car {
    @Value("red") String color;
    @Value("100") int oil;

//    @Autowired                    // @Autowired는 byType -> 타입으로 먼저 검색하고, 여러개면 이름으로 검색
//    @Qualifier("superEngine")     // Autowired로 먼저 찾고 해당 이름으로 찾는다.
    @Resource(name="superEngine")   // byName
    Engine engine;
    @Autowired Door[] doors;

    public Car() {}
    public Car(String color, int oil, Engine engine, Door[] doors) {
        this.color = color;
        this.oil = oil;
        this.engine = engine;
        this.doors = doors;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setOil(int oil) {
        this.oil = oil;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public void setDoors(Door[] doors) {
        this.doors = doors;
    }

    @Override
    public String toString() {
        return "Car{" +
                "color='" + color + '\'' +
                ", oil=" + oil +
                ", engine=" + engine +
                ", doors=" + Arrays.toString(doors) +
                '}';
    }
}




public class SpringDiTest {
    public static void main(String[] args) {
        ApplicationContext ac = new GenericXmlApplicationContext("config.xml");
//        Car car         = (Car) ac.getBean("car");      // byName
        Car car         = ac.getBean("car", Car.class);      // byName, 위와 같음
//        Car car2         = ac.getBean(Car.class);               // byType
//        Engine engine   = (Engine) ac.getBean("engine");
//        Engine engine   = (Engine) ac.getBean("superEngine");    // byType
////        Engine engine   = (Engine) ac.getBean(Engine.class);    // byType , 같은 타입이 3개라서 에러
//        Door door       = (Door) ac.getBean("door");

//        car.setColor("red");
//        car.setOil(100);
//        car.setEngine(engine);
//        car.setDoors(new Door[]{ ac.getBean("door", Door.class), (Door) ac.getBean("door"),});

        System.out.println("car = " + car);

    }
}
