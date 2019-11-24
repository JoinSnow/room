package dao;

import bean.Room;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomDao {
    @Select("select * from room")
    List<Room> findAllRoom();

    @Select("select * from room where roomId=#{roomId}")
    Room findById(int roomId);

}
