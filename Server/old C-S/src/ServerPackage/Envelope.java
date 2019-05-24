package ServerPackage;
//V2018-6-1
public class Envelope {
	private int sequence_number;				//sequence_number ���к�
	private int data_size;						//data_size ���ջص���������
	private float actual_start;					//actual_start ʵ�ʿ�ʼ��ֵ��������С���룩
	private float actual_end;					//actual_end ʵ�ʽ�����ֵ�����������룩
	private String data = "";					//distances��ž�����Ϣ*int���� ���ΧΪ4086
	
	private long time_stamp = 0;					//time_stamp��¼ʱ���	*�ֶ���¼
	private int marks = 0;							//marks��¼����			*�ֶ���¼
	private int type = 0;							//type�����Ʒ����		*�ֶ���¼
	
	public Envelope(){}
	public Envelope(int n,int s,float start,float end,String data){
		this.sequence_number = n;
		this.data_size = s;
		this.actual_start = start;
		this.actual_end = end;
		this.data = data;
	}
	
	public int getSequence_number() {
		return sequence_number;
	}
	public void setSequence_number(int sequence_number) {
		this.sequence_number = sequence_number;
	}
	public int getData_size() {
		return data_size;
	}
	public void setData_size(int data_size) {
		this.data_size = data_size;
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
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
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


	public void autoMark(int marks,int type){						//�����Ҫ�ֶ���¼������
		this.marks = marks;
		this.type = type;
		this.time_stamp = System.currentTimeMillis();
	}
	
	public void set(int n,int s,float start,float end,String d,long time,int m,int t){
		this.sequence_number = n;
		this.data_size = s;
		this.actual_start = start;
		this.actual_end = end;
		this.data = d;
		this.time_stamp = time;
		this.marks = m;
		this.type = t;
	}
	
	public String toString(){
		String res = "{Number: " + this.sequence_number + " ;DataSize: " + this.data_size + " ;Start: " + this.actual_start + " ; End : " + this.actual_end + " ;Data: " + this.data + " ;Times: " + this.time_stamp + " ;Marks: " + this.marks + " ;Type: " + this.type + "}";
		return res;
	}
	public String toInsertString(String table){
		String res = "insert into " + table + " values('" + this.sequence_number + "','" + this.data_size + "','" + this.actual_start + "','" + this.actual_end + "','" + this.data + "','" + this.time_stamp + "','" + this.marks + "','" + this.type + "');";
		return res;
	}
	public String toJxlString(String split){
		String res = this.sequence_number + split + this.data_size + split + this.actual_start + split + this.actual_end + split + this.data + split + this.time_stamp + split + this.marks  + split + this.type;
		return res;
	}
}
