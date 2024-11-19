package org.example;

import org.example.ksup.tests.EachClientTest;
import org.example.ksup.tests.MyTest;

public class Main {
    public static void main(String[] args) throws Exception {
        MyTest test = new MyTest("firstClientTest");
        test.runSelectedTest();
    }
}