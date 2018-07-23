package com.snoopy.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * ClassName: ActionResult <br/>
 * Function: action结果集. <br/>
 * date: 2018年7月17日 下午5:20:06 <br/>
 * 
 * @author LiHaiqing
 */
@ApiModel(description = "对象结果集")
public class ActionResult<T> {

    /**
     * 操作编码
     */
    @ApiModelProperty(value = "操作编码", required = true, example = "6003")
    private String code;

    /**
     * 返回值
     */
    @ApiModelProperty(value = "返回值", required = true, example = "user")
    private T data;

    /**
     * 操作是否成功
     */
    @ApiModelProperty(value = "操作是否成功", required = true, example = "true")
    private boolean isSuccess = true;

    /**
     * 操作消息
     */
    @ApiModelProperty(value = "操作消息", required = true, example = "成功")
    private String message;

    public ActionResult() {}

    /**
     * @param isSuccess 初始化是否成功
     */
    public ActionResult(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    /**
     * @param isSuccess 初始化是否成功
     */
    public ActionResult(boolean isSuccess, String message, String code, T retValue) {
        super();
        this.isSuccess = isSuccess;
        this.message = message;
        this.code = code;
        this.data = retValue;
    }

    /**
     * @param errCode
     * @param message
     * @param retValue
     */
    public ActionResult(String errCode, String message, T retValue) {
        this.isSuccess = false;
        this.code = errCode;
        this.message = message;
        this.data = retValue;
    }

    /**
     * @param isSuccess 初始化是否成功
     * @param message 初始化消息
     */
    public ActionResult(boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
    }

    /**
     * 构造返回数据信息方法
     *
     * @param isSuccess
     * @param retValue
     */

    public ActionResult(boolean isSuccess, T retValue) {
        this.data = retValue;
        this.isSuccess = isSuccess;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public boolean hasError() {
        return !isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    /**
     * 获取成功的结果
     *
     * @param code 操作编码
     * @param message 操作消息
     * @param retValue 返回结果
     * @return 结果
     */
    public static <T> ActionResult<T> getSuccessResult(String code, String message, T retValue) {
        return new ActionResult<T>(true, message, code, retValue);
    }

    /**
     * 获取成功的结果
     *
     * @param message 操作消息
     * @param retValue 返回结果
     * @return 结果
     */
    public static <T> ActionResult<T> getSuccessResult(String message, T retValue) {
        return getSuccessResult("op_success", message, retValue);
    }

    /**
     * 获取成功的结果
     *
     * @param retValue 返回结果
     * @return 结果
     */
    public static <T> ActionResult<T> getSuccessResult(T retValue) {
        return getSuccessResult("op_success", "成功", retValue);
    }

    /**
     * 获取错误的结果
     *
     * @param code 操作编码
     * @param message 操作消息
     * @return 结果
     */
    public static <T> ActionResult<T> getErrorResult(String code, String message) {
        return new ActionResult<T>(false, message, code, null);
    }

    /**
     * 获取错误的结果
     *
     * @param message 操作消息
     * @return 结果
     */
    public static <T> ActionResult<T> getErrorResult(String message) {
        return getErrorResult("op_failed", message);
    }
}
