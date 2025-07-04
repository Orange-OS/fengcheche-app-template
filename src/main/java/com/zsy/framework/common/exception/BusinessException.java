package com.zsy.framework.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 业务异常
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BusinessException extends RuntimeException {
    
    /**
     * 错误码
     */
    private Integer code;
    
    /**
     * 错误消息
     */
    private String message;
    
    /**
     * 构造函数
     * @param message 错误消息
     */
    public BusinessException(String message) {
        super(message);
        this.code = 500;
        this.message = message;
    }
    
    /**
     * 构造函数
     * @param code 错误码
     * @param message 错误消息
     */
    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
    
    /**
     * 快速创建业务异常
     * @param message 错误消息
     * @return 业务异常
     */
    public static BusinessException of(String message) {
        return new BusinessException(message);
    }
    
    /**
     * 快速创建业务异常
     * @param code 错误码
     * @param message 错误消息
     * @return 业务异常
     */
    public static BusinessException of(Integer code, String message) {
        return new BusinessException(code, message);
    }
}
