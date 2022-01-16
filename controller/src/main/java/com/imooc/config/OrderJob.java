package com.imooc.config;

//import com.imooc.service.OrderService;
import com.imooc.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class OrderJob {

	@Autowired
//	OrderService orderService;

	/**
	 * 使用定时任务关闭未支付订单弊端：
	 * 1, 不支持集群
	 * 	  解决方式:只单机执行定时任务
	 * 2, 会对数据库全表搜索, 影响性能.select * from orders
	 *
	 * 后续用:消息队列MQ->RabbitMQ, RocketMQ, Kafka, ZeroMQ...
	 * 	  延时任务(队列)
	 */

//	@Scheduled(cron = "0/3 * * * * ?")
	public void autoCloseOrder() {
//		orderService.scheduleCloseOrder();
		System.out.println("定时任务:" + DateUtil.dateToString(new Date()));
	}
}
