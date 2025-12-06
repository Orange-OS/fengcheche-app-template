package com.zsy.framework.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import static com.zsy.framework.common.exception.common.ExceptionUtil.formatMessage;

/**
 * 业务异常
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DataNotExistException extends RuntimeException {

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
    public DataNotExistException(String message) {
        super(message);
        this.code = 400;
        this.message = message;
    }

    // 新增支持模板的构造方法
    public DataNotExistException(String template, Object... args) {
        super(formatMessage(template, args));
        this.code = 400;
        this.message = formatMessage(template, args);
    }

//    /**
//     * 构造函数
//     * @param code 错误码
//     * @param message 错误消息
//     */
//    public DataExistException(Integer code, String message) {
//        super(message);
//        this.code = code;
//        this.message = message;
//    }
    
    /**
     * 快速创建业务异常
     * @param message 错误消息
     * @return 业务异常
     */
    public static DataNotExistException of(String message) {
        return new DataNotExistException(message);
    }
    
//    /**
//     * 快速创建业务异常
//     * @param code 错误码
//     * @param message 错误消息
//     * @return 业务异常
//     */
//    public static DataExistException of(Integer code, String message) {
//        return new DataExistException(code, message);
//    }
}
