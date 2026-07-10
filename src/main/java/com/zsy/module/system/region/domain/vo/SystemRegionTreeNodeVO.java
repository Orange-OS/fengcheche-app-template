package com.zsy.module.system.region.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "地域信息")
public class SystemRegionTreeNodeVO extends SystemRegionVO {
    @Schema(description = "子节点")
    private List<SystemRegionTreeNodeVO> children;
}
