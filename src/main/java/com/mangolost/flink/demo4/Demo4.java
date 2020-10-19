package com.mangolost.flink.demo4;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import scala.concurrent.java8.FuturesConvertersImpl;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Demo4 {

    private static List<Person> genData() {
        List<Person> list = new ArrayList<>();
        list.add(new Person("lichen", 33));
        list.add(new Person("wuwei", 30));
        list.add(new Person("nianping", 29));
        list.add(new Person("ziqin", 13));
        list.add(new Person("xige", 32));
        list.add(new Person("xiaozao", 4));
        return list;
    }

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        List<Person> personList = genData();

        DataStream<Person> persons = env
                .fromCollection(personList)
                .name("persons");;

        DataStream<Person> adults = persons
                .filter((FilterFunction<Person>) person -> person.getAge() >= 18)
                .name("adults");

        adults.print();

        env.execute("adult filter");

    }

}
