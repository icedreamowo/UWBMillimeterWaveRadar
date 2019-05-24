package gui;

public class MyThread extends Thread{
	private ClientFrame a = new ClientFrame();
	public void run() {
		try {
			//a.setChart();
			a.updatePaper("100");
			//a.setData1("100");
			a.setStatusIcon(0);
			while(true) {
				Thread.sleep(500);
				//a.update();
				double b=100*Math.random();
				String s=Double.toString(b);
				//a.updatePaper(s);
				//a.setData1(s);
			}
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

}
