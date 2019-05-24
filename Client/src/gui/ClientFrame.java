package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.block.BlockContainer;
import org.jfree.chart.block.BorderArrangement;
import org.jfree.chart.block.EmptyBlock;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.CompositeTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleEdge;

import client.JClient;
public class ClientFrame extends JFrame{
	public JClient client = new JClient();
	private ClientKeeper ckeeper = new ClientKeeper(this);
	
	static String name = "img";
	private static final ImageIcon  bgimg = new ImageIcon("src/"+name+"/bg.jpg");
	private static final ImageIcon PaperBg = new ImageIcon("src/"+name+"/PaperBg.jpg");
	private static final ImageIcon PaperBg1 = new ImageIcon("src/"+name+"/PaperBg1.jpg");
	
	private JLabel goodsLabel=new JLabel("�Ƿ�������:");      //�����Ƿ�������ı�ǩ
	private JLabel goodsDescribe=new JLabel();               //�ж��Ƿ�������ı�ǩ
	private JLabel goodsState=new JLabel();
	
	private ImageIcon img=new ImageIcon("src/"+ name +"/3.jpg");
	private JLabel imgLabel = new JLabel();
	
	private JLabel background=new JLabel(bgimg);
	
	private Dialog dialog = new Dialog();
	
	private List<Integer> values= new ArrayList<Integer>();     //����ź�ǿ�ȵ�����
	private JPanel panel2=new JPanel();  //������ͼ�����
	
	private XYSeries series=new XYSeries("Data");   //����ͼ������
	
	
	private JLabel state=new JLabel();


	private JPanel panel1=new JPanel();   //��ҳ1������
	
	private MyLabel  label=new MyLabel(400,100);
	private MyLabel1 label1=new MyLabel1();
	
	private JComboBox<String> faceCombo= new JComboBox<String>(); 			//�����б�
	
	private JButton update=new JButton("����");								//���°�ť
	private JButton confirm=new JButton("ȷ��");								//ȷ�ϰ�ť
	private JButton close=new JButton("Close");
	
	private JLabel MachineName=new JLabel("��  ��  :");    //��ʾ����������
	private JLabel bg=new JLabel(PaperBg);				//��ʾ��ֽ�ı���
	private JLabel word=new JLabel("��ֽ��Ч��ʾ:");
	private JLabel Percentage=new JLabel("%"); //��ʾ�ٷֱȷ���
	
	private JLabel StateLabel=new JLabel("����������״̬ : ");
	private JLabel StateDescribe =new JLabel();
	
	//������ʾ������
	private JLabel DataName1=new JLabel("ʣ���� :");	//��ʾ����1������
	private JLabel Data1=new JLabel();
	
	private JLabel DataName2=new JLabel("��  �� :");
	private JLabel Data2=new JLabel();
	
	private JLabel DataName3=new JLabel("�ź�ǿ��:");
	private JLabel Data3=new JLabel();
	
	private JLabel DataName4=new JLabel("ʱ  �� :");
	private JLabel Data4=new JLabel();
	
	
	public ClientFrame(String adr,int port,String clientName){
		super();
		initComponents();
		client.ready(adr, port, clientName);
		ckeeper.start();
	}
	ClientFrame(){
		super();
		initComponents();
	}
	public void initComponents() {	
		//���ñ���ͼ
		bgimg.setImage(bgimg.getImage().getScaledInstance(1750,1050,Image.SCALE_DEFAULT)); //ͼƬ����Ӧlabel
		background.setBounds(0, 0, 1750,1050);
		this.getLayeredPane().add(background,new Integer(Integer.MIN_VALUE));
		JPanel jp=(JPanel)this.getContentPane();
		jp.setOpaque(false);
		
		state.setBounds(550,50,100,100);
		
		StateLabel.setBounds(670,50,200,45);
		StateLabel.setOpaque(true); 
		StateLabel.setBackground(new Color(252,229,205)); 
		StateLabel.setFont(new Font("�꿬��", Font.BOLD, 20));
		StateLabel.setHorizontalAlignment(SwingConstants.CENTER);  //ʹ���������ʾ
		
		StateDescribe.setBounds(670,50+55,200,45);
		//StateDescribe.setOpaque(true); 
		//StateDescribe.setBackground(Color.white); 
		StateDescribe.setFont(new Font("�꿬��", Font.BOLD, 20));
		StateDescribe.setHorizontalAlignment(SwingConstants.CENTER);  //ʹ���������ʾ
		
		
		//���ú�4������
		DataName2.setBounds(50, 250, 100, 50);
		DataName2.setOpaque(true); 
		DataName2.setBackground(new Color(252,229,205)); 
		DataName2.setFont(new Font("�꿬��", Font.BOLD, 20));
		DataName2.setHorizontalAlignment(SwingConstants.CENTER);  //ʹ���������ʾ
		DataName3.setBounds(50, 350, 100, 50);
		DataName3.setOpaque(true); 
		DataName3.setBackground(new Color(252,229,205)); 
		DataName3.setFont(new Font("�꿬��", Font.BOLD, 20));
		DataName3.setHorizontalAlignment(SwingConstants.CENTER);  //ʹ���������ʾ
		DataName4.setBounds(50, 450, 100, 50);
		DataName4.setOpaque(true); 
		DataName4.setBackground(new Color(252,229,205)); 
		DataName4.setFont(new Font("�꿬��", Font.BOLD, 20));
		DataName4.setHorizontalAlignment(SwingConstants.CENTER);  //ʹ���������ʾ
		
		Data1.setBounds(200,150,250,50);
		Data1.setOpaque(true); 
		Data1.setBackground(Color.white); 
		Data1.setFont(new Font("�꿬��", Font.BOLD, 20));
		Data1.setHorizontalAlignment(SwingConstants.CENTER);  //ʹ���������ʾ
		
		Data2.setBounds(200, 250, 250, 50);
		Data2.setOpaque(true); 
		Data2.setBackground(Color.white); 
		Data2.setFont(new Font("�꿬��", Font.BOLD, 20));
		Data2.setHorizontalAlignment(SwingConstants.CENTER);  //ʹ���������ʾ
		
		Data3.setBounds(200, 350, 250, 50);
		Data3.setOpaque(true); 
		Data3.setBackground(Color.white); 
		Data3.setFont(new Font("�꿬��", Font.BOLD, 20));
		Data3.setHorizontalAlignment(SwingConstants.CENTER);  //ʹ���������ʾ
		Data4.setBounds(200, 450, 250, 50);
		Data4.setOpaque(true); 
		Data4.setBackground(Color.white); 
		Data4.setFont(new Font("�꿬��", Font.BOLD, 20));
		Data4.setHorizontalAlignment(SwingConstants.CENTER);  //ʹ���������ʾ
		
	
		
		
		//�༭�ٷֱȷ���
		Percentage.setFont(new Font("�꿬��", Font.BOLD, 30));
		Percentage.setBounds(470, 150, 50, 50);
		
		
		
		//�༭˵������ֽ��Ч��������
		word.setFont(new Font("�꿬��", Font.BOLD, 20));
		word.setBounds(810+400, 65, 150, 20);
		
		
		//���û������������
		MachineName.setBounds(50, 50,100,50);
		MachineName.setOpaque(true); 
		MachineName.setBackground(new Color(252,229,205));
		//MachineName.setBorder(BorderFactory.createLineBorder(Color.black)); 
		MachineName.setFont(new Font("�꿬��", Font.BOLD, 20));
		MachineName.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		
		//����������������
		DataName1.setBounds(50, 150, 100, 50);
		DataName1.setOpaque(true); 
		DataName1.setBackground(new Color(252,229,205)); 
		//DataName1.setBorder(BorderFactory.createLineBorder(Color.black));
		DataName1.setFont(new Font("�꿬��", Font.BOLD, 20));
		DataName1.setHorizontalAlignment(SwingConstants.CENTER);  //ʹ���������ʾ
		
		//���þ�ֽ����ɫ
		bg.setBounds(799+400,49,500,500);
		//bg.setOpaque(true); 
		//bg.setBackground(new Color(16,232,42));
		bg.setBorder(BorderFactory.createLineBorder(Color.black,1) );
		
		//���þ�ֽ��Ч
		label.setBounds(800+400, 50, 500,500);
		label1.setBounds(200+800+400, 90+50, 100, 20);
		
		
		//����������
		faceCombo.setBounds(200,50,300,50);
		faceCombo.setEditable(false);
		faceCombo.setEnabled(true);
	    faceCombo.setFont(new Font("�꿬��", Font.BOLD, 20));
	    
	    //���ø��£�ȷ�ϰ�ť
	    update.setBounds(550, 450, 100, 50);
	    update.setName("update");
	    update.setFont(new Font("�꿬��", Font.BOLD, 20));
	    confirm.setBounds(800, 450, 100, 50);
	    confirm.setName("confirm");
	    confirm.setFont(new Font("�꿬��", Font.BOLD, 20));
	    close.setBounds(1050, 450, 100, 50);
	    close.setFont(new Font("�꿬��", Font.BOLD, 20));
	    
	    
	    close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	       
	    
	    faceCombo.addItem("δ����");
	    
	    //���º�ȷ�ϰ�ť��Ӽ����¼�
	    update.addActionListener(new UpdateListener(this));
	    confirm.addActionListener(new ConfirmListener(this));
	    
	    
	    
	    
	    /*-------------����ͼ��--------------------*/
	    
	    StandardChartTheme mChartTheme = new StandardChartTheme("CN");  
	    mChartTheme.setLargeFont(new Font("�꿬��",  Font.PLAIN, 15));  
	    mChartTheme.setExtraLargeFont(new Font("�꿬��", Font.PLAIN, 15));  
	    mChartTheme.setRegularFont(new Font("�꿬��", Font.PLAIN, 15));  
	    ChartFactory.setChartTheme(mChartTheme); 
		XYSeriesCollection dataset1 = new XYSeriesCollection();
        dataset1.addSeries(series);
        XYDataset datasetDataset1 = dataset1;
        JFreeChart chart1 = createChart1(datasetDataset1,"�ź�ǿ��");
        ChartPanel chartPanel1= new ChartPanel(chart1);
        chartPanel1.setBounds(1,1,1598+50,398);
        
        chart1.setBackgroundPaint(ChartColor.white);
        
        
        //����panel2
        panel2.add(chartPanel1); 
        panel2.setLayout(null);
        panel2.setBounds(50, 560,1600+50, 400);
       
	    
	   
	    
	    //����panel1
        panel1.setOpaque(false);
	  	panel1.setLayout(null);
	  	panel1.setBounds(0,0,1750,1050);
	  	//panel1.setBackground(new Color(187, 255, 255));
	  	panel1.add(word);
	  	panel1.add(DataName1);
	  	panel1.add(Data1);
	  	panel1.add(faceCombo);
	  	panel1.add(update);
	  	panel1.add(confirm);
	  	panel1.add(MachineName);
	  	panel1.add(label1);
	    panel1.add(label);
	    panel1.add(bg);
	    panel1.add(DataName2);
	    panel1.add(DataName3);
	    panel1.add(DataName4);
	    panel1.add(Data2);
	    panel1.add(Data3);
	    panel1.add(Data4);	
	    panel1.add(Percentage);
	    panel1.add(state);
	    panel1.add(StateLabel);
	    panel1.add(StateDescribe);
	    panel1.add(close);
	    
	    goodsState.setBounds(550,200,550,200);	
		state.setBounds(550,50,100,100);
		
		StateLabel.setBounds(670,70,200,60);
		StateLabel.setOpaque(true); 
		StateLabel.setBackground(new Color(252,229,205)); 
		StateLabel.setFont(new Font("�꿬��", Font.BOLD, 20));
		StateLabel.setHorizontalAlignment(SwingConstants.CENTER);  //ʹ���������ʾ
		
		StateDescribe.setBounds(900,70,200,60);
		StateDescribe.setOpaque(true); 
		StateDescribe.setBackground(Color.white); 
		StateDescribe.setFont(new Font("�꿬��", Font.BOLD, 20));
		StateDescribe.setHorizontalAlignment(SwingConstants.CENTER);  //ʹ���������ʾ
		
		img.setImage(img.getImage().getScaledInstance(550,200,Image.SCALE_DEFAULT));
		imgLabel.setIcon(img);
		imgLabel.setBounds(550, 200, 550, 200);
		this.add(imgLabel);
		imgLabel.setVisible(false);
		//��������ͼƬ
	    
	   
	    panel1.add(goodsState);
	    
	    this.setGoods(1);
	    
	    this.add(panel2);
	    this.add(panel1);
		this.setLayout(null);
		this.setVisible(true);
		this.setBounds(50,50,1750,1050);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setStatusIcon(1);
	}
	
	//����������
	public void setFaceCombo(List<String> s) {
		for(int i=0;i<=s.size()-1;i++)
		{
			faceCombo.addItem(s.get(i));
		}	
	}
	
	//�õ�������
	public JComboBox<String> getFaceCombo(){
		return faceCombo;
	}
	
	//��������������
	public void clearFaceCombo(){
		faceCombo.removeAllItems();
	}
	//����Բ���Ŀ�Ⱥ͸߶�
	public void update(int width,int height) {
		label.setWidthandHeight(width, height);
	}
	
	
	/*------------------------����ͼ��-------------------------------*/
	private static JFreeChart createChart1(XYDataset dataset,String titlename ) {
        JFreeChart chart = ChartFactory.createXYLineChart(
            "ʵʱ"+titlename+"����ͼ", 
            "����", 
            titlename,
            dataset, 
            PlotOrientation.VERTICAL,
            false,     // legend? no, we'll create our own
            false,      // tooltips?
            false      // URLs?
        );
        
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setNoDataMessage("Waiting...");// û��������ʾ��ʱ����ʾ�����ʾ
        plot.setNoDataMessageFont(new Font("����", Font.PLAIN, 25));      
        plot.setNoDataMessagePaint(Color.RED);  //û������ʱ��ʾ����Ϣ��ɫ       
        plot.setBackgroundPaint(Color.white);
        // ����������ɫ
        plot.setDomainGridlinePaint(Color.pink);
        // ���������ɫ
        plot.setRangeGridlinePaint(Color.pink);

        
        
        chart.getTitle().setFont(new Font("����", Font.BOLD, 25));
        /*---------����y��-------------*/
        NumberAxis axis1 = (NumberAxis) plot.getRangeAxis();
        //ɾ��С�������ʾ      
        axis1.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        axis1.setLabelFont(new Font("����", Font.PLAIN, 20)); 
        axis1.setLowerMargin(0);
        axis1.setUpperBound(2000);
        /*------����X��------------*/  
        ValueAxis domainAxis=plot.getDomainAxis();  
        domainAxis.setLowerMargin(0);
        domainAxis.setUpperBound(1850);
        
        domainAxis.setLabelFont(new Font("����", Font.PLAIN, 20)); 

        
        
        
        axis1.setAutoRangeIncludesZero(false);
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
        BlockContainer container = new BlockContainer(new BorderArrangement());
        container.add(new EmptyBlock(100, 0));
        renderer.setSeriesPaint(0, Color.BLACK);
        CompositeTitle legends = new CompositeTitle(container);
        legends.setPosition(RectangleEdge.BOTTOM);
        chart.addSubtitle(legends);
        return chart;
    }
	public void setChart() {
		for(int i=0;i<1818;i++) {
			series.add(i,i);
		}
	}
	public void update() {
		series.clear();
		for(int i=0;i<1818;i++) {
			series.add(i,i*Math.random());
		}
	}
	public void setList(List<Integer> values) {
		this.values=values;
	}
	public void setData1(String data) {
		Data1.setText(data);
	}
	public void clearText(){
		Data1.setText("");
		Data2.setText("");
		Data3.setText("");
		Data4.setText("");
		updatePaper("100");
	}
	public void setData2(String data){
		Data2.setText(data);
	}
	public void setData3(String data){
		Data3.setText(data);
	}
	public void setData4(String data){
		Data4.setText(data);
	}
	public void updatePaper(String s) {
		if(s.equals(""))
		{
			label.setWidthandHeight(400, 100);
			label.repaint();
		}
		else
		{
			double number=Double.parseDouble(s);
			if(number!=0)
			{
				double num1=100+300*number/100;
				int w=(int)num1;
				double num=80*number/100+20;
				int h=(int)num;
				label.setWidthandHeight(w, h);
				label.repaint();
			}
			else
			{
				label.setWidthandHeight(100,20);
				label.repaint();
			}
			
		}
	}
	public void setStatusIcon(int type) 
	{
		if(type==0) 
		{
			StateDescribe.setText("��      ��");
		}
		else 
		{
			StateDescribe.setText("��       ��");
		}
		ImageIcon img=new ImageIcon("src/"+name+"/"+type+".jpg");
		img.setImage(img.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT));
		state.setIcon(img);
	}
	public void showError(String ErrorMsg) {
		dialog.setMessage(ErrorMsg);
		dialog.showDialog();
	}
	public void setGoods(int type) {
		if(type == 0)      //������
		{
			imgLabel.setVisible(false);
		}else{
			imgLabel.setVisible(true);
		}
	}
}
