package com.snoopy.result;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * ClassName: DataResult <br/>
 * Function: 分页结果集. <br/>
 * date: 2018年7月17日 下午5:29:22 <br/>
 * 
 * @author LiHaiqing
 */
@ApiModel(description = "分页结果集")
public class DataResult<T> {

    /**
     * 结果数据列表
     */
    @ApiModelProperty(value = "结果数据列表", required = true, example = "6003")
    private List<T> data;
    /**
     * 总数量
     */
    @ApiModelProperty(value = "总数量", required = true, example = "10")
    private int count = -1;

    /**
     * 查询是否成功
     */
    @ApiModelProperty(value = "查询是否成功", required = true, example = "true")
    private boolean isSuccess = true;

    /**
     * 操作编码
     */
    @ApiModelProperty(value = "操作编码", required = true, example = "6003")
    private String code;

    /**
     * 错误消息
     */
    @ApiModelProperty(value = "错误消息", required = true, example = "查询异常")
    private String message;

    public DataResult() {}

    public DataResult(boolean isSuccess, String code, String message, int totalCount, List<T> data) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
        this.count = totalCount;
        this.data = data;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public boolean hasError() {
        return !isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 获取成功的结果
     *
     * @param dataList 结果数据列表
     * @return 结果
     */
    public static <T> DataResult<T> getSuccessResult(List<T> dataList) {
        return new DataResult<>(true, "op_success", "成功", -1, dataList);
    }

    /**
     * 获取成功的结果
     *
     * @param dataList 结果数据列表
     * @param totalCount 总数量
     * @return 结果
     */
    public static <T> DataResult<T> getSuccessResult(List<T> dataList, int totalCount) {
        return new DataResult<>(true, "op_success", "成功", totalCount, dataList);
    }

    /**
     * 获取失败的结果
     *
     * @param errMsg 失败信息
     * @return 结果
     */
    public static <T> DataResult<T> getErrorResult(String code, String errMsg) {
        return new DataResult<T>(false, code, errMsg, -1, null);
    }

    /**
     * 获取失败的结果
     *
     * @param errMsg 失败信息
     * @return 结果
     */
    public static <T> DataResult<T> getErrorResult(String errMsg) {
        return new DataResult<T>(false, "op_failed", errMsg, -1, null);
    }
}
