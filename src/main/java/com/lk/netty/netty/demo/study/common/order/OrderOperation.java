package com.lk.netty.netty.demo.study.common.order;

import com.alibaba.fastjson.JSON;
import com.lk.netty.netty.demo.study.common.Operation;
import com.lk.netty.netty.demo.study.common.RequestMessage;
import com.lk.netty.netty.demo.study.util.IdUtil;
import com.lk.netty.netty.demo.study.util.JsonUtil;

/**
 * @auther: kim
 * @create: 2020-03-05 11:17
 * @description:
 */
public class OrderOperation extends Operation {
    private int tableId;
    private String dish;

    public OrderOperation(int tableId, String dish) {
        this.tableId = tableId;
        this.dish = dish;
    }

    public static void main(String[] args) {
        System.out.println(JSON.toJSONString( new OrderOperation(1001, "tudou")));
        System.out.println(JsonUtil.toJson( new OrderOperation(1001, "tudou")));
        System.out.println(JSON.toJSONString(new RequestMessage(IdUtil.nextId(), new OrderOperation(1001, "tudou"))));
    }

    @Override
    public OrderOperationResult execute() {
        System.out.println("order's executing startup with orderRequest: " + toString());
        //execute order logic
        System.out.println("order's executing complete");
        OrderOperationResult orderResponse = new OrderOperationResult(tableId, dish, true);
        return orderResponse;
    }

//    public int getTableId() {
//        return tableId;
//    }
//
//    public String getDish() {
//        return dish;
//    }
}
