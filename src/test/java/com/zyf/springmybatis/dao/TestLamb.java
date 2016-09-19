package com.zyf.springmybatis.dao;

import java.util.stream.Stream;

import org.junit.Test;

import com.zyf.springmybatis.base.TestBase;

public class TestLamb extends TestBase {
    @Test
    public void go() {
        Stream.iterate(1, item -> item + 1).limit(10)
            .forEach(System.out::println);
    }
}
