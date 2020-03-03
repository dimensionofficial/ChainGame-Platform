package com.magicorange.pojo;

import lombok.Data;

@Data
public class TransferData {
    private String from;         //付款账户
    private String to;              //收款账户
    private String quantity;    //金额
    private String memo;       //将存放orderId
    /**交易状态
     * 1. Pushed
     * 2. Executed
     * 3. In-block
     * 4. handoff: 1 (最多7s) 可以适用于小额
     * 5. handoff: 2 (最多13s) 可以适用于中额
     * 5. Finality
     * defuse还给了
     * soft_fail，hard_fail，expired，canceled
     */
    private String transaction_status; //交易状态
    //哪怕transaction_status是executed，也只有当execution_irreversible为true时才是绝对的成功，这需要155s，所以才需要相关置信
    private String execution_irreversible; //交易是否可逆
}
