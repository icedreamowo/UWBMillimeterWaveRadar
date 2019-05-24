package ServerPackage;
//V18-6-7 去除 成功提示
import java.sql.*;
import java.util.*;

public class JDBC {
	private String url = null;
	private String name = "com.mysql.jdbc.Driver";
	private String usr = null;
	private String pwd = null;
	
    public ResultSet ret = null;
    private ServerConnecter connecter = new ServerConnecter();
    
    public JDBC(String ServerUrl,String User,String Password){
    	 bind(ServerUrl,User,Password);
    	 connecter = new ServerConnecter(this);
    }
    public void bind(String ServerUrl,String User,String Password){
    	url = ServerUrl;
    	usr = User;
    	pwd = Password;
    }
    
    public ResultSet select(String sql){
    	try{
    		connecter.sql(sql);
    		ret = connecter.pst.executeQuery();
    		return ret;
    	}catch(SQLException e){
    		e.printStackTrace();
    	}
    	return null;
    }
    public List selectExist(String table,String column_name,String target){		//查询所有的column_name == target
    	try{
    		String cmd = "select * from " + table + " where " + column_name + " = '" + target + "';";
    		return Method.RetToList(select(cmd));
    	}catch(Exception e){
    		e.printStackTrace();
    		return null;
    	}
    }
    public ResultSet selectExistReturnRet(String table,String column_name,String target){		//查询所有的column_name == target
    	try{
    		String cmd = "select * from " + table + " where " + column_name + " = '" + target + "';";
    		return select(cmd);
    	}catch(Exception e){
    		e.printStackTrace();
    		return null;
    	}
    }
	public List selectAll(String table){					//查询全表  返回List
    	try{
    		connecter.sql("select * from " + table + ";");
    		ret = connecter.pst.executeQuery();
    		return Method.RetToList(ret);
    	}catch(SQLException e){
    		e.printStackTrace();
    		return null;
    	}
    }
    public boolean insertSingleData(String table,List data){
    	try{
    		String cmd = "insert into " + table + " values('";
    		for(int i = 0;i < data.size();i++){
    			cmd += data.get(i);
    			if(i+1 != data.size()){
    				cmd += "','" ;
    			}
    		}
    		cmd += "');";
    		connecter.sql(cmd);
    		switch(connecter.pst.executeUpdate()){
    			case 1:
    	    		return true;
    			default :
    				return false;
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    }
	
    public void insert(String sql){
    	try{
    		connecter.sql(sql);
    		switch(connecter.pst.executeUpdate()){
    			case 1:
//    				System.out.println("Insert Succeed");
    				break;
    			default :
//   				System.out.println("Insert Failed");
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    public void update(String sql){
    	try{
    		connecter.sql(sql);
    		switch(connecter.pst.executeUpdate()){
    			case 1:
//    				System.out.println("Update Succeed");
    				break;
    			default :
//   				System.out.println("Update Failed");
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    public void delete(String sql){
    	try{
    		connecter.sql(sql);
    		switch(connecter.pst.executeUpdate()){
    			case 1:
//   				System.out.println("Delete Succeed");
    				break;
    			default :
//    				System.out.println("Delete Failed");
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    
    public void refresh(){
    	connecter.refresh();
    }
    public void close(){
    	try {
			ret.close();
			connecter.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
    	
    }
    
    
    class ServerConnecter{
    	private JDBC jdbc = null;  	
    	private Connection conn = null;  
    	private PreparedStatement pst = null;  
        ServerConnecter(){}
    	ServerConnecter(JDBC oneJDBC){
    		jdbc = oneJDBC;
    		try{
    			Class.forName(name);//指定连接类型  
                conn = DriverManager.getConnection(jdbc.url,jdbc.usr,jdbc.pwd);//获取连接  
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    	}
    	
    	public void sql(String sql){
    		try {
                pst = conn.prepareStatement(sql);//准备执行语句  
            } catch (Exception e) {  
                e.printStackTrace();  
            }
    	}
    	
    	public void refresh(){					//用于刷新Connection类到数据库的链接 如:JDBC的bind()之后
    		try{
    			Class.forName(name);//指定连接类型  
                conn = DriverManager.getConnection(jdbc.url,jdbc.usr,jdbc.pwd);//获取连接  
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    	}
    	public void close() {  
            try {
                conn.close();  
                pst.close();  
            }catch(SQLException e){  
                e.printStackTrace();  
            }  
    	}
    }
}
