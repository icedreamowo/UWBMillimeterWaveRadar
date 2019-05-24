package ServerPackage;
//V18-6-1
public class Main {
	public static void main(String[] args){
		JServer ser1 = null;
		JRServer ser2 = null;
		ser1 = new JServer(8000);
		ser2 = new JRServer(8001);
		ser1.start();
		ser2.start();
	}
}