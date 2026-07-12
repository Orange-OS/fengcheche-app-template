package com.zsy.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回结果
 *
 * @param <T> 数据泛型
 */
@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 成功标志
     */
    private boolean success;

    /**
     * 返回代码
     */
    private Integer code;

    /**
     * 返回消息
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 私有构造函数，防止直接实例化
     */
    private Result() {
    }

    /**
     * 构建结果对象
     */
    public static <T> Result<T> build(boolean success, Integer code, String msg, T data) {
        Result<T> result = new Result<>();
        result.setSuccess(success);
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    /**
     * 成功结果
     */
    public static <T> Result<T> success() {
        return build(true, 200, "操作成功", null);
    }

    /**
     * 成功结果（带数据）
     */
    public static <T> Result<T> success(T data) {
        return build(true, 200, "操作成功", data);
    }

    /**
     * 成功结果（自定义消息和数据）
     */
    public static <T> Result<T> success(String msg, T data) {
        return build(true, 200, msg, data);
    }

    /**
     * 失败结果
     */
    public static <T> Result<T> error() {
        return build(false, 500, "操作失败", null);
    }

    /**
     * 失败结果（自定义消息）
     */
    public static <T> Result<T> error(String msg) {
        return build(false, 500, msg, null);
    }

    /**
     * 失败结果（自定义代码和消息）
     */
    public static <T> Result<T> error(Integer code, String msg) {
        return build(false, code, msg, null);
    }

    /**
     * 失败结果（自定义消息和数据）
     */
    public static <T> Result<T> error(String msg, T data) {
        return build(false, 500, msg, data);
    }

    /**
     * 失败结果（自定义代码、消息和数据）
     */
    public static <T> Result<T> error(Integer code, String msg, T data) {
        return build(false, code, msg, data);
    }

    /**
     * 判断是否成功
     */
    @JsonIgnore
    public boolean isSuccess() {
        return success;
    }

    /**
     * 判断是否失败
     */
    @JsonIgnore
    public boolean isError() {
        return !success;
    }

    // -------------------------------------------- 警告 --------------------------------------------

    /**
     * 警告结果（自定义消息）
     */
    public static <T> Result<T> warning(String msg) {
        return build(false, 400, msg, null);
    }

    public static <T> Result<T> warning(String msg, T data) {
        return build(false, 400, msg, data);
    }


}
