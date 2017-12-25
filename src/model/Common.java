package model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import javax.websocket.Session;

import model.PlayerPosition;;

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
	
	public static Queue<Integer> matchingPlayers = new LinkedList<Integer>(); // 正在匹配对局的玩家
	public static Map<Integer, PlayerPosition> playerPositionMap = new HashMap<Integer, PlayerPosition>(); // 玩家位置映射
	public static Map<Integer, Session> playerSessionMap=new HashMap<>();
	
	public static Data getStatus(int pid) {
		Data status = new Data();
		// 如果已经在游戏
		if (playerPositionMap.containsKey(pid)) {
			status.status = "Gaming";
			// return pid就可以了，因为有玩家位置映射
			status.data = pid;
		} else if (matchingPlayers.contains(pid)) {
			status.status = "Matching";
			status.data = matchingPlayers.size();
		} else {
			status.status = "Logined";
			status.data = pid;
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
	
	public static int getRid(int pid) {
		if (playerPositionMap.containsKey(pid)) {
			return playerPositionMap.get(pid).rid;
		}else {
			return -1;
		}
	}
	public static int getRoomIndex(int pid) {
		if (playerPositionMap.containsKey(pid)) {
			return playerPositionMap.get(pid).roomIndex;
		}else {
			return -1;
		}
	}
	public static void cancelMatch(int pid) {
		if (matchingPlayers.contains(pid)) {
			matchingPlayers.remove(pid);
		}
	}
	
	public static Cards getMaxCards(int pid) {
		int rid = getRid(pid);
		return rooms[rid].maxCards;
	}
	/**
	 * 获取Player对象
	 * @param pid
	 * @return
	 */
	public static Player getPlayer(int pid) {
		PlayerPosition position=playerPositionMap.get(pid);
		return rooms[position.rid].players[position.roomIndex];
	}
	
	public static Room getRoom(int pid) {
		int rid=getRid(pid);
		return rooms[rid];
	}
	/** 
	 * 返回玩家所在房间的最大牌用户
	 * @param pid
	 * @return
	 */
	 
	public static int getMaxPlayer(int pid) {
		int rid=getRid(pid);
		int maxPlayer=rooms[rid].maxPlayer;
		return maxPlayer;
	}
	
	public static int getTurn(int pid) {
		int rid=getRid(pid);
		int turn=rooms[rid].turn;
		return turn;
	}
	
	public static void nextTurn(int pid) {
		if (playerPositionMap.containsKey(pid)) {
			int index = playerPositionMap.get(pid).roomIndex;
			index=(index+1)%3;
			Common.getRoom(pid).turn=Common.getRoom(pid).pid[index];
		}
	}
	public static void gameOverRelease(int pid) {
		int rid = getRid(pid);
		playerPositionMap.remove(getRoom(pid).pid[0]);
		playerPositionMap.remove(getRoom(pid).pid[1]);
		playerPositionMap.remove(getRoom(pid).pid[2]);
		rooms[rid]=null;
		roomAvailable.add(rid);
	}
	
}
