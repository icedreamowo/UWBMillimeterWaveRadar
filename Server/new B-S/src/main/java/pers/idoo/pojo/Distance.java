package pers.idoo.pojo;
//V2018-6-7
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Distance {
	private String sequence_number;					//sequence_number ���к�
	private int reflection_count;					//reflection_count �������
	private float actual_start;						//actual_start ʵ�ʿ�ʼ��ֵ(������С����)
	private float actual_end;						//actual_end ʵ�ʽ�����ֵ(����������)
	private String distances;						//distances��ž�����Ϣ*int���� ���ΧΪ5
	private String amplitudes;						//amplitudes����ź�ǿ����Ϣ*int���� ���ΧΪ5
	
	private long time_stamp;						//time_stamp��¼ʱ���	*�ֶ���¼
	private int marks;								//marks��¼����			*�ֶ���¼
	private int type;								//type�����Ʒ����		*�ֶ���¼
	
	private static final int EMPTY = 0;
	
	public Distance(){}
	public Distance(String n,int c,float start,float end,String d,String a){
		this.sequence_number = n;
		this.reflection_count = c;
		this.actual_start = start;
		this.actual_end = end;
		this.distances = d;
		this.amplitudes = a;
	}
	
	public String getSequence_number() {
		return sequence_number;
	}
	public void setSequence_number(String sequence_number) {
		this.sequence_number = sequence_number;
	}
	public int getReflection_count() {
		return reflection_count;
	}
	public void setReflection_count(int reflection_count) {
		this.reflection_count = reflection_count;
	}
	public float getActual_start() {
		return actual_start;
	}
	public void setActual_start(float actual_start) {
		this.actual_start = actual_start;
	}
	public float getActual_end() {
		return actual_end;
	}
	public void setActual_end(float actual_end) {
		this.actual_end = actual_end;
	}
	public String getDistances() {
		return distances;
	}
	public void setDistances(String distances) {
		this.distances = distances;
	}
	public String getAmplitudes() {
		return amplitudes;
	}
	public void setAmplitudes(String amplitudes) {
		this.amplitudes = amplitudes;
	}
	public long getTime_stamp() {
		return time_stamp;
	}
	public void setTime_stamp(long time_stamp) {
		this.time_stamp = time_stamp;
	}
	public int getMarks() {
		return marks;
	}
	public void setMarks(int marks) {
		this.marks = marks;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	public void setTime(){
		this.time_stamp = System.currentTimeMillis();
	}
	
	public void set(String n,int c,float start,float end,String d,String a,long time,int m,int t){
		this.sequence_number = n;
		this.reflection_count = c;
		this.actual_start = start;
		this.actual_end = end;
		this.distances = d;
		this.amplitudes = a;
		this.time_stamp = time;
		this.marks = m;
		this.type = t;
	}
	public void set(int dataState) {
		switch (dataState) {
		case EMPTY:
			set("",0,0,0,"","",0,0,0);
			break;

		default:
			break;
		}
	}
	public boolean isEmpty(){
		/*
		 * 如果时间戳为0 则判断此数据为空*/
		return this.time_stamp == 0;
	}
	
	public String toString(){
		String res = "{Number: " + this.sequence_number + " ;Count: " + this.reflection_count + " ;Start: " + this.actual_start + " ; End : " + this.actual_end + " ;Distances: " + this.distances + " ;Amplitudes: " + this.amplitudes + " ;Times: " + this.time_stamp + " ;Marks: " + this.marks + " ;Type: " + this.type + ";}";
		return res;
	}
	
	public String toInsertString(String table){
		String res = "insert into " + table + " values('" + this.sequence_number + "','" + this.reflection_count + "','" + this.actual_start + "','" + this.actual_end + "','" + this.distances + "','" + this.amplitudes + "','" + this.time_stamp + "','" + this.marks + "','" + this.type + "');";
		return res;
	}
	public String toUpdateString(String table){
		String res = "update " + table + " set sequence_number ='" + this.sequence_number + "',reflection_count ='" + this.reflection_count + "',actual_start='" + this.actual_start + "',actual_end='" + this.actual_end + "',distances='" + this.distances + "',amplitudes='" + this.amplitudes + "',time_stamp='" + this.time_stamp + "',marks='" + this.marks + "',type='" + this.type + "' where sequence_number ='" + this.sequence_number + "';";
		return res;
	}

}
