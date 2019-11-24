package controller;


import bean.OrderForm;
import bean.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import service.OrderFormService;


import java.util.*;

@Controller
@RequestMapping("/orderForm")
public class OrderFormController {
    private Result result = new Result();
    @Autowired
    private OrderFormService orderFormService;
    @Autowired
    private JedisPool jedisPool;

    //查找所有订单
    @RequestMapping("/findAll")
    public String findAllRoom(ModelMap map) {
        List allOrderForm = orderFormService.findAllOrderForm();
        map.addAttribute("allOrderForm", allOrderForm);
        return "orderForm";
    }

    //添加订单到数据库
    @RequestMapping("/addToSql")
    public String addToSql(ModelMap map, OrderForm orderForm) {
        Result result = orderFormService.addToSql(orderForm);
        Jedis jedis = jedisPool.getResource();
        jedis.del(orderForm.getOrderFormId());
        map.addAttribute("result", result);
        return "result";
    }

    //查找redis中的所有订单
    @RequestMapping("/findFromRedis")
    public String findFromRedis(ModelMap map) {
        List<OrderForm> list = orderFormService.findOrderFormFromRedis();
        if (list.size() > 0) {
            map.addAttribute("list", list);
            return "orderFormRedis";
        } else {
            result.setFlag(false);
            result.setMessage("redis内没有订单！<a href='http://localhost/room/findAll'>返回首页</a>");
            map.addAttribute("result", result);
            return "result";
        }
    }

    //将订单存入redis
    @RequestMapping("/addToRedis")
    public String addToRedis(ModelMap map, int roomId, Date startTime, Date endTime) {
        long startMs = startTime.getTime();
        long endMs = endTime.getTime();
        List<OrderForm> listOfRedis = orderFormService.findOrderFormFromRedisByRoomId(roomId);
        //遍历listRedis
        int intOfRedis = orderFormService.judge(startMs, endMs, listOfRedis);
        if (intOfRedis > 0) {
            //int>0 说明有冲突 预定失败
            result.setFlag(false);
            result.setMessage("时间冲突，预定失败！<a href='http://localhost/room/findAll'>返回首页</a>");
        } else {
            //redis的订单时间不冲突 继续判断数据库内的订单 是否冲突
            List<OrderForm> listOfMySql = orderFormService.findByRoomId(roomId);
            int intOfMySql = orderFormService.judge(startMs, endMs, listOfMySql);
            if (intOfMySql > 0) {
                //int>0 说明有冲突 预定失败
                result.setFlag(false);
                result.setMessage("时间冲突，预定失败！<a href='http://localhost/room/findAll'>返回首页</a>");
            } else {
                //数据库内的订单时间不冲突 预定成功 先将订单存入redis
                result = orderFormService.addOrderFormToRedis(roomId, startTime, endTime);
            }
        }
        map.addAttribute("result", result);
        return "result";
    }
}
