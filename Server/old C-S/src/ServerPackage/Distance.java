package ServerPackage;
//V2018-6-7
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Distance {
	private int sequence_number;					//sequence_number 序列号
	private int reflection_count;					//reflection_count 反射次数
	private float actual_start;						//actual_start 实际开始的值(测量最小距离)
	private float actual_end;						//actual_end 实际结束的值(测量最大距离)
	private String distances;						//distances存放距离信息*int数组 最大范围为5
	private String amplitudes;						//amplitudes存放信号强度信息*int数组 最大范围为5
	
	private long time_stamp;						//time_stamp记录时间戳	*手动记录
	private int marks;								//marks记录次数			*手动记录
	private int type;								//type标记物品材质		*手动记录
	
	public Distance(){}
	public Distance(int n,int c,float start,float end,String d,String a){
		this.sequence_number = n;
		this.reflection_count = c;
		this.actual_start = start;
		this.actual_end = end;
		this.distances = d;
		this.amplitudes = a;
	}
	
	public int getSequence_number() {
		return sequence_number;
	}
	public void setSequence_number(int sequence_number) {
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
	
	public void set(int n,int c,float start,float end,String d,String a,long time,int m,int t){
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
	
	public void set(String data) throws Exception{
		String regex = "[:]([^;]*)[;]";
		if(data == null){
			set(0,0,0,0,"0","0",0,0,0);
		}else{
			Pattern p = Pattern.compile(regex);  
			Matcher m = p.matcher(data);
			int i = 0;
			while(m.find()){
				String result = m.group(1).trim();
				switch(i){
					case 0:
						this.sequence_number = Integer.parseInt(result);
						break;
					case 1:
						this.reflection_count = Integer.parseInt(result);
						break;
					case 2:
						this.actual_start = Float.parseFloat(result);
						break;
					case 3:
						this.actual_end = Float.parseFloat(result);
						break;
					case 4:
						this.distances = result;
						break;
					case 5:
						this.amplitudes = result;
						break;
					case 6:
						this.time_stamp = Long.parseLong(result);
						break;
					case 7:
						this.marks = Integer.parseInt(result);
						break;
					case 8:
						this.type = Integer.parseInt(result);
						break;
				}
				i++;
			}
		}
	}
	
	public boolean isEmpty(){
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
		String res = "SET SQL_SAFE_UPDATES = 0;update " + table + " set sequence_number ='" + this.sequence_number + "',reflection_count ='" + this.reflection_count + "',actual_start='" + this.actual_start + "',actual_end='" + this.actual_end + "',distances='" + this.distances + "',amplitudes='" + this.amplitudes + "',time_stamp='" + this.time_stamp + "',marks='" + this.marks + "',type='" + this.type + "' where sequence_number ='" + this.sequence_number + "';";
		return res;
	}
}
