package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import client.JClient;
import data.*;

public class ConfirmListener implements ActionListener{
	private JClient client = null;
	private ClientFrame mainFrame = null;
	private ConfirmThread co = null;
	private int sleepTime = 1000;						//查询间隔
	private int waitTime = 30000;						//判断在线离线时间
	private int amplitudesNormal = 0;				//预置信号强度 用于检测异物
	private int distancesNormal = 160;					//预置距离 用于检测异物
	private int distancesNormal2 = 175;					//预置距离 用于检测异物
	private int amplitudesN = 0;
	private int distancesN = 0;
	
	ConfirmListener(ClientFrame mainFrame){
		this.mainFrame = mainFrame;
		this.client = mainFrame.client;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(co != null){
			co.cancel();
		}
		co = new ConfirmThread();
		co.start();
	}
	
	class ConfirmThread extends Thread{
		private boolean cancelFlag = false ;				//true为取消
		public void run() {
			try{
				while(!cancelFlag){
					String target = mainFrame.getFaceCombo().getSelectedItem().toString();
					Distance distance = new Distance();
					int percent = 0;
					try{
						distance.set(client.searchDeviceData(target));
					}catch(Exception e){
						e.printStackTrace();
					}
					
					mainFrame.clearText();
					if(distance.isEmpty()){
						mainFrame.setData4("无最新数据");
					}else{
						percent = DistanceToPercent(distance);
						mainFrame.setData1(percent + "");
						mainFrame.setData2(distance.getDistances());
						mainFrame.setData3(distance.getAmplitudes());
						mainFrame.setData4(Method.CTMToDateTime(distance.getTime_stamp()));
						mainFrame.updatePaper(percent + "");
						
						mainFrame.setStatusIcon(isOffline(distance));
					}
					
					if(isBlocked(distance)){
						mainFrame.setGoods(1);
					}else{
						mainFrame.setGoods(0);
					}
					sleep(sleepTime);							//等待t ms后继续更新
				}
		}catch(Exception e){
			e.printStackTrace();
		}
		}
		public void cancel(){
			cancelFlag = true;
		}
		private int DistanceToPercent(Distance distance){
			int num = 9000;					//9000为满
			int per = 100;
			int result = 100 - Method.ExtractInteger(distance.getAmplitudes()) * per  / num;
			return (result>0)?result:0;
		}
		private int isOffline(Distance distance){
			if(System.currentTimeMillis() - distance.getTime_stamp() > waitTime){
				return 1;
			}else{
				return 0;
			}
		}
		private boolean isBlocked(Distance distance){
			distancesN = Integer.parseInt(distance.getDistances());
			amplitudesN = Integer.parseInt(distance.getAmplitudes());
			return amplitudesN > amplitudesNormal && (distancesN < distancesNormal || distancesN > distancesNormal2);
		}
	}
}