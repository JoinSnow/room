package controller;

import bean.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import service.RoomService;

import java.util.List;

@Controller
@RequestMapping("/room")
public class RoomController {
    @Autowired
    private RoomService roomService;
    @RequestMapping("/findAll")
    public String findAllRoom(ModelMap map,String username){
        List allRoom = roomService.findAllRoom();
        map.addAttribute("allRoom",allRoom);
        return "index";
    }
    @RequestMapping("/findById")
    public String findById(ModelMap map,int roomId){
        Room room = roomService.findById(roomId);
        map.addAttribute("room",room);
        return "orderRoom";
    }
}
