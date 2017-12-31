package model;

public class Tools {
	public static String toTimeString(int time) {
		int m=(int)time/60;
		int s=(int)time%60;
		return m+"分"+s+"秒";
	}
}
