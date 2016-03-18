package cn.sx.decentworld.bean;

public enum MessageType {
	NOMAL(0),AUDIO(1),IMAGE(2);
	private int value;
	private MessageType (int i)
	{
		this.value=i;
	}
	public int getValue(){
		return this.value;
	}
	
	public static MessageType getType(int value){
		switch (value) {
		case 0:
			return NOMAL;
		case 1:
			return AUDIO;
		default:
			return IMAGE;
		}
	}
}
