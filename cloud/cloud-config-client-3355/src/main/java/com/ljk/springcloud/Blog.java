package com.ljk.springcloud;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @auther: kim
 * @create: 2020-06-03 11:39
 * @description:
 */
@Data
@AllArgsConstructor
public class Blog {
    {
        System.out.println("class blog init");
    }

    private Integer id = 1;
    private String name;
}
