package service;

import bean.OrderForm;
import bean.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.OrderFormDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.*;

@Service
public class OrderFormService {
    private String contextPath = "";
    private Result result = new Result();
    private ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private OrderFormDao orderFormDao;
    @Autowired
    private JedisPool jedisPool;

    //查找所有订单
    public List<OrderForm> findAllOrderForm() {
        List<OrderForm> list = orderFormDao.findAllOrderForm();
        return list;
    }

    //添加订单
    public Result addToSql(OrderForm orderForm) {
        orderFormDao.add(orderForm);
        result.setFlag(true);
        result.setMessage("付款成功！<a href='" + contextPath + "/room/findAll'>返回首页</a>");
        return result;
    }

    //根据roomId 查找订单
    public List<OrderForm> findByRoomId(int roomId) {
        List<OrderForm> list = orderFormDao.findByRoomId(roomId);
        return list;
    }

    //查找redis中的所有订单
    public List<OrderForm> findOrderFormFromRedis() {
        Jedis jedis = jedisPool.getResource();
        Set<String> keys = jedis.keys("*");
        List<OrderForm> list = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        for (String key : keys) {
            String value = jedis.get(key);
            OrderForm orderForm = null;
            try {
                orderForm = mapper.readValue(value, OrderForm.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            list.add(orderForm);
        }
        return list;
    }

    //根据roomId查找redis中的所有订单
    public List<OrderForm> findOrderFormFromRedisByRoomId(int roomId) {
        Jedis jedis = jedisPool.getResource();
        Set<String> keys = jedis.keys("*");
        List<OrderForm> list = new ArrayList<>();
        for (String key : keys) {
            String value = jedis.get(key);
            OrderForm orderFormRedis = null;
            try {
                orderFormRedis = mapper.readValue(value, OrderForm.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (roomId == orderFormRedis.getRoomId()) {
                list.add(orderFormRedis);
            }
        }
        return list;
    }

    //判断预定时间和已有订单的时间是否冲突
    public int judge(long startMs, long endMs, List<OrderForm> list) {
        int i = 0;
        for (OrderForm orderForm : list) {
            long startRedisMs = orderForm.getStartTime().getTime();
            long endRedisMs = orderForm.getEndTime().getTime();
            if (!(startMs >= endRedisMs || endMs <= startRedisMs)) {
                //每冲突一次 i++
                i++;
            }
        }
        return i;
    }

    //将订单存入redis
    public Result addOrderFormToRedis(int roomId, Date startTime, Date endTime) {
        OrderForm orderForm = new OrderForm();
        String uuid = UUID.randomUUID().toString();
        orderForm.setOrderFormId(uuid);
        orderForm.setRoomId(roomId);
        orderForm.setOrderTime(new Date());
        orderForm.setStartTime(startTime);
        orderForm.setEndTime(endTime);
        String json = null;
        try {
            json = mapper.writeValueAsString(orderForm);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Jedis jedis = jedisPool.getResource();
        jedis.setex(uuid, 60, json);
        result.setFlag(true);
        result.setMessage("预订成功！<a href='" + contextPath + "/room/findAll'>返回首页</a>");
        return result;
    }
}
