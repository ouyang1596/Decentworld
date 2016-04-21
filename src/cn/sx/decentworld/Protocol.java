/**
 * 
 */
package cn.sx.decentworld;


/**
 * @author Sammax
 *
 *	协议结构
 *	|----------head----------|--------------------body--------------------|
 *
 *	其中head为定长，包括如下结构：
 *	
 *		 4 		1	 8	  6	   6	 4		  4			   4			4
 *	|magic_num|type|mid|from| to |offset|check_length|extra_length|body_length|
 *
 *	body为变长，包括如下结构：
 *	|	check	|	extra	|	body	|
 *
 *	各段说明
 *	magic_num：魔数0xFEEDBEEF
 *	type：消息类型
 *	mid：消息ID
 *	from：消息发送方，为dwID.getBytes()
 *	to：消息接收方，为dwID.getBytes()
 *	offset：验证起始位置偏移量（0~body_length-4)
 *	check_length:验证码长度
 *	body_length:消息体长度
 *	check：校验码内容
 *	extra：消息附加内容
 *	body：消息内容
 *
 *	校验说明：
 *	从body中随机选取一个0~body_length-4的其实位置，向后读取4字节，用用户aes秘钥加密产生check字段
 *
 */
final public class Protocol {
//	final public static int MAGIC_NUM=ProtocolConfig.MAGIC_NUM;
    
	//此时from 和 to为固定的6 byte若以后业务增长可能需要修改协议或者对ID进行压缩
	final public static int HEAD_LENGTH=41; 
	final public static int FROM_LENGTH=6;
	final public static int TO_LENGTH=6;
	final public static int TYPE_LENGTH=1;
	final public static int MID_LENGTH=8;
	final public static int OFFSET_LENGTH=4;
	final public static int CHECK_LENGTH=4;
	final public static int EXTRA_LENGTH=4;
	final public static int BODY_LENGTH=4;

	
	
	public enum MessageType{
		//
		SERVER_CHALLENGE((byte)0),
		CLIENT_CHALLENGE((byte)1),
		SERVER_CR((byte)2),
		CLIENT_CR((byte)3),
		ERROR((byte)4),
		/**
		 * 收费字段
		 */
		TXT((byte)5),
		AUDIO((byte)6),
		IMG((byte)7),
		CARD((byte)8),
		TXT_A((byte)9),
		AUDIO_A((byte)10),
		IMG_A((byte)11),
		CARD_A((byte)12),
		TXT_G((byte)13),
		AUDIO_G((byte)14),
		IMG_G((byte)15),
		CARD_G((byte)16);
		
		private byte type;
		private MessageType(byte _type){
			this.type=_type;
		}
		public byte getTypeByte(){
			return this.type;
		}
		public static MessageType getType(byte type){
			for(MessageType value:MessageType.values()){
				if(value.getTypeByte()==type){
					return value;
				}
			}
			return null;
		}
	} 
}
