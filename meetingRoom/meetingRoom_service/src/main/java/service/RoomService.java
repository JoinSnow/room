package service;

import bean.Room;
import dao.RoomDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    @Autowired
    private RoomDao roomDao;

    public List findAllRoom() {
        List<Room> allRoom = roomDao.findAllRoom();
        return allRoom;
    }

    public Room findById(int id) {
        Room room = roomDao.findById(id);
        return room;
    }


}
