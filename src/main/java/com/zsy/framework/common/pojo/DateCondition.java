package com.zsy.framework.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateCondition {
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date start;
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date end;
}
