package dao;

import bean.OrderForm;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderFormDao {
    @Select("select * from orderform")
    List<OrderForm> findAllOrderForm();
    @Insert("insert into orderform values(#{orderFormId},#{roomId},#{orderTime},#{startTime},#{endTime})")
    void add(OrderForm orderForm);
    @Select("select * from orderform where roomId=#{id}")
    List<OrderForm> findByRoomId(int roomId);
}
