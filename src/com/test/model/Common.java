package com.test.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import com.test.model.PlayerPosition;;

public class Common {
	public final static int ROOM_MAX = 10;
	public static Room[] rooms = new Room[ROOM_MAX]; // 房间列表
	public static Queue<Integer> roomAvailable = new LinkedList<>();
	
	static {
		// offer 添加一个元素并返回true 如果队列已满，则返回false
		// poll 移除并返问队列头部的元素 如果队列为空，则返回null
		for (int i = 0; i < ROOM_MAX; i++) {
			roomAvailable.offer(i);
			System.out.println("room["+i+"]可用");
		}
		System.out.println("rooms初始化完毕");
	}
	
	// 剩余可用房间号队列
	public static Queue<Integer> matchingPlayers = new LinkedList<Integer>(); // 正在匹配对局的玩家
	public static Map<Integer, PlayerPosition> playerPositionMap = new HashMap<Integer, PlayerPosition>(); // 玩家位置映射
	
	public static StatusType getStatus(int pid) {
		StatusType status = new StatusType();
		// 如果已经在游戏
		if (playerPositionMap.containsKey(pid)) {
			status.status = "Gaming";
			// return pid就可以了，因为有玩家位置映射
			status.data = pid;
		} else if (matchingPlayers.contains(pid)) {
			status.status = "Matching";
			status.data = matchingPlayers.size();
		}
		return status;
	}

	public static void matchGame(int pid) {
		if (!matchingPlayers.contains(pid)) {
			matchingPlayers.offer(pid);
			System.out.println(pid+"正在匹配...");
		}
		if (matchingPlayers.size() >= 3) {
			// 取出一个房间
			int rid = (int)roomAvailable.poll();
			System.out.println("取出房间"+rid);
			
			int p[] = new int[3];
			for(int i=0;i<=2;i++) {
				// 取出三个用户
				p[i]=(int)matchingPlayers.poll();
				// 增加玩家位置映射
				PlayerPosition position=new PlayerPosition(rid, i);
				playerPositionMap.put(p[i], position);
			}
			// 创建房间
			rooms[rid] = new Room(p);		
		}
		
	}
	public static void cancelMatch(int pid) {
		if (matchingPlayers.contains(pid)) {
			matchingPlayers.remove(pid);
		}
	}
}
