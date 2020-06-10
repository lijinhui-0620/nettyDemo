package com.ljk.springcloud;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @auther: kim
 * @create: 2020-06-03 11:39
 * @description:
 */
public class test {
    public static void main(String[] args) throws Exception {
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        Unsafe o = (Unsafe) theUnsafe.get(null);

        Object o1 = o.allocateInstance(Blog.class);
        System.out.println(o1);
    }
}
