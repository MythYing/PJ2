package model;

public class ShopItem {
	public int id;
	public String name;
	public String fileName;
	public int price;
	
	public ShopItem(int id,String name,String fileName,int price) {
		this.id=id;
		this.name=name;
		this.fileName=fileName;
		this.price=price;
	}
}
