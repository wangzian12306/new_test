package com.example.bigevent.mapper;

import com.example.bigevent.pojo.FosterOrder;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FosterOrderMapper {
    @Insert("INSERT INTO foster_order VALUES(" +
            "#{orderId}, #{createTime}, #{updateTime}, " +
            "#{startTime}, #{endTime}, #{fosterDays}, " +
            "#{totalAmount}, #{address}, #{fosterMode}, " +
            "#{petType}, #{creatorId}, #{acceptorId}, #{status})")
    void insert(FosterOrder order);

    @Delete("DELETE FROM foster_order WHERE order_id = #{orderId} AND creator_id = #{creatorId}")
    int delete(@Param("orderId") String orderId, @Param("creatorId") Integer creatorId);

    @Update("UPDATE foster_order SET " +
            "start_time=#{startTime}, end_time=#{endTime}, " +
            "total_amount=#{totalAmount}, address=#{address}, " +
            "foster_mode=#{fosterMode}, pet_type=#{petType}, " +
            "status=#{status}, acceptor_id=#{acceptorId} " +
            "WHERE order_id=#{orderId}")
    int update(FosterOrder order);

    @Select("SELECT * FROM foster_order WHERE creator_id = #{userId}")
    List<FosterOrder> listByCreator(Integer userId);

    @Select("SELECT * FROM foster_order WHERE order_id = #{orderId} " +
            "AND (creator_id = #{userId} OR acceptor_id = #{userId})")
    FosterOrder selectByIdAndUser(@Param("orderId") String orderId,
                                  @Param("userId") Integer userId);
}