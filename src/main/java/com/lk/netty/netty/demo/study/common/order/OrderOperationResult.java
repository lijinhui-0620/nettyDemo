package com.lk.netty.netty.demo.study.common.order;

import com.lk.netty.netty.demo.study.common.OperationResult;

/**
 * @auther: kim
 * @create: 2020-03-05 11:17
 * @description:
 */
public class OrderOperationResult extends OperationResult {

    private final int tableId;
    private final String dish;
    private final boolean complete;

    public OrderOperationResult(int tableId, String dish, boolean complete) {
        this.tableId = tableId;
        this.dish = dish;
        this.complete = complete;
    }

    public int getTableId() {
        return tableId;
    }

    public String getDish() {
        return dish;
    }

    public boolean isComplete() {
        return complete;
    }

    @Override
    public String toString() {
        return "OrderOperationResult{" +
                "tableId=" + tableId +
                ", dish='" + dish + '\'' +
                ", complete=" + complete +
                '}';
    }
}
