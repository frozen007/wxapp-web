package com.myz.wxapp.ticket.dao;

import com.myz.inf.datasource.DataSource;
import com.myz.wxapp.ticket.Ticket;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TicketDao
 * Created by myz
 * Date 2020/12/6 00:01
 */
@Repository
@DataSource("dbuser")
public interface TicketDao {

    @Select("select ticket_name as name, current_id as currentId, step from id_ticket")
    List<Ticket> getAllTickets();

    @Select("select ticket_name as name, current_id as currentId, step " +
            "from id_ticket " +
            "where ticket_name=#{ticket_name}")
    Ticket getByName(@Param("ticket_name") String ticketName);

    @Update("update id_ticket set current_id=#{new_current_id} " +
            "where ticket_name=#{ticket_name} and current_id=#{pre_current_id}")
    int updateTicketForSync(@Param("ticket_name") String ticketName,
                            @Param("new_current_id") long newCurrentId,
                            @Param("pre_current_id") long preCurrentId);
}
