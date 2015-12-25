/**
 * 
 */
package cn.sx.decentworld.utils;

import java.util.Random;

/**
 * @ClassName: AnonymityNickNameCreator.java
 * @Description: 
 * @author: cj
 * @date: 2015年12月1日 下午5:31:32
 */
public class AnonymityNickNameCreator {
	private static String[] names = new String[31];      
	private static Random random=new Random();
    static{
    	names[0]="享受寂寞心";
    	names[1]="体验孤独意";
    	names[2]="戏王照青苔";
    	names[3]="忧欢竹玉馆";
    	names[4]="独霸玩天下";
    	names[5]="弹琴啸风云";
    	names[6]="明月照落花";
    	names[7]="山中隐杀手";
    	names[8]="日暮掩心扉";
    	names[9]="王孙归不归";
    	names[10]="红豆寄相思";
    	names[11]="梦里醉相思";
    	names[12]="双鱼游天下";
    	names[13]="须尽丘壑美";
    	names[14]="暂游桃源里";
    	names[15]="终南望馀雪";
    	names[16]="积雪浮云端";
    	names[17]="为君增暮寒";
    	names[18]="移舟泊烟渚";
    	names[19]="野旷天低树";
    	names[20]="江清月近人";
    	names[21]="问君几多愁";
    	names[22]="天上望明月";
    	names[23]="地下思故乡";
    	names[24]="不知心恨谁";
    	names[25]="古调虽自爱";
    	names[26]="今人输不完";
    	names[27]="孤云将野鹤";
    	names[28]="岂向人间住";
    	names[29]="怀君属秋夜";
    	names[30]="莫教地盤输";
    }
	     
    public static String getRandomName(){
    	int index= random.nextInt(31);
    	return names[index];
    }
  
}
	
