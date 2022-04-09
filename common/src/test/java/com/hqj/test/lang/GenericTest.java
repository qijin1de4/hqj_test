package com.hqj.test.lang;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GenericTest {

    @Test
    public void superTest() {
        List<Number> nums = new ArrayList<>();
        add2List(nums);
        readFromList(nums);

        String str = GenericTest.autoRetTypeCast("String");
        System.out.println(str);

        Integer num = GenericTest.autoRetTypeCast("Integer");
        System.out.println(num);
    }

    public static void add2List(List<? super Number> list) {
       list.add(10.0f);
       list.add(1);
    }

    public static void readFromList(List<? extends Number> list) {
        for(Number number : list) {
            System.out.println(number);
        }
    }

    public static <T> T autoRetTypeCast(String type) {
            if("String".equals(type)) {
                return (T) "a sstring";
            }

            if("Integer".equals(type)) {
                return (T)(new Integer("12"));
            }

        return null;

    }


}
