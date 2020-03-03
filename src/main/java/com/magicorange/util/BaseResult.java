package com.magicorange.util;

/**
 * @ClassNameResultbase
 * @Description
 * @Author
 * @Date2019/11/22 14:59
 **/
public class BaseResult {

    private Integer code = 200;
    private String message = "success";
    private Object modle = null;

    /**
     * 返回状态码/信息/Bean
     *
     * @param code    状态码
     * @param message 信息
     * @param modle   实体类
     * @return
     */
    public static BaseResult success(Integer code, String message, Object modle) {
        BaseResult resultBase = new BaseResult();
        resultBase.setCode(code);
        resultBase.setMessage(message);
        resultBase.setModle(modle);
        return resultBase;
    }

    /**
     * 返回状态码与实体
     *
     * @param code  状态码
     * @param modle 实体
     * @return
     */
    public static BaseResult success(Integer code, Object modle) {
        BaseResult resultBase = new BaseResult();
        resultBase.setCode(code);
        resultBase.setModle(modle);
        return resultBase;
    }

    /**
     * 返回状态码与信息
     *
     * @param code    状态码
     * @param message 信息
     * @return
     */
    public static BaseResult success(Integer code, String message) {
        BaseResult resultBase = new BaseResult();
        resultBase.setCode(code);
        resultBase.setMessage(message);
        return resultBase;
    }

    /**
     * 错误:返回状态码与信息
     *
     * @param code    状态码
     * @param message 信息
     * @return
     */
    public static BaseResult error(Integer code, String message) {
        BaseResult resultBase = new BaseResult();
        resultBase.setCode(code);
        resultBase.setMessage(message);
        return resultBase;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getModle() {
        return modle;
    }

    public void setModle(Object modle) {
        this.modle = modle;
    }
}
