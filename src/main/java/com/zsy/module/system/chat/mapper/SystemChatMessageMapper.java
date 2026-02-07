package com.zsy.module.system.chat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsy.module.system.chat.domain.entity.SystemChatMessageEntity;
import com.zsy.module.system.chat.domain.vo.SystemChatListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 聊天消息 Mapper
 */
@Mapper
public interface SystemChatMessageMapper extends BaseMapper<SystemChatMessageEntity> {

    /**
     * 分页查询两个用户之间的聊天记录（按发送时间倒序）
     */
    @Select("SELECT * FROM system_chat_message " +
            "WHERE (from_user_id = #{userId} AND to_user_id = #{peerId}) " +
            "   OR (from_user_id = #{peerId} AND to_user_id = #{userId}) " +
            "ORDER BY send_time DESC")
    IPage<SystemChatMessageEntity> selectChatPage(Page<SystemChatMessageEntity> page,
                                                  @Param("userId") Long userId,
                                                  @Param("peerId") Long peerId);

    /**
     * 查询当前用户的聊天列表
     */
    List<SystemChatListVO> selectChatList(@Param("userId") Long userId);
}


