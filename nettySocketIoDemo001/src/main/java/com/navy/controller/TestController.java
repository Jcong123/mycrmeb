package com.navy.controller;

import java.util.HashMap;
import java.util.Map;

import com.navy.requestbean.PushBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.navy.component.SocketClientComponent;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/netty")
public class TestController {
	
	@Autowired
	private SocketClientComponent socketClientComponent;
	
	@GetMapping("/push/{eventName}/{userId}/{pageSign}")
	public void push(@PathVariable("eventName") String eventName,@PathVariable( "userId") String userId,@PathVariable("pageSign") String pageSign) {
		/*String eventName = (String) data.get("eventName");
		String userId = (String) data.get("userId");
		String pageSign = (String) data.get("pageSign");*/
		HashMap<String, Object> map = new HashMap<>();
		map.put("eventName",eventName);
		map.put("userId",userId);
		map.put("pageSign",pageSign);
		socketClientComponent.send(userId,pageSign ,eventName, map);
	}
}
