package ServerPackage;

import org.apache.poi.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import jxl.write.Label;

import java.io.FileOutputStream;

public class PoiManager {
	private Poi poi = null;
	private String FileName = null;
	private String split = "&";
	PoiManager(String FileName,String Title){
		this.FileName = FileName;
		poi = new Poi(FileName,Title);
	}
	public void add(String Data){
		String[] lotData = Data.split(split);
		for(int i = 0;i < lotData.length;i++){
			poi.add(lotData[i]);
		}
		poi.next();
	}
	public void add(String Data,boolean NewLine){
		String[] lotData = Data.split(split);
		for(int i = 0;i < lotData.length;i++){
			poi.add(lotData[i]);
		}
		if(NewLine){poi.next();}
	}
	public void add(int column,int row,String Data){
		poi.add(column, row, Data);
	}
	public void addTitle(String title){
		poi.addTitle(title);
	}
	public void nextLine(){
		poi.next();
	}
	public void end(){
		poi.close();
		System.out.println(FileName + "文件已成功创建");
	}
	/*
	public class Poi{
		private HSSFWorkbook workbook = null;
		private HSSFSheet sheet = null;
		private HSSFRow headRow = null;
		private HSSFRow singleRow = null;
		private HSSFCellStyle defaultStyle = null;
		private HSSFCell cell = null;
		private FileOutputStream output = null;
		
		private String FileName = null;
		private String FileAdress = null;
		private int titleColumns = 0;
		private int rows = 1;
		private int columns = 0;
		Poi(String FileName,String Title){
			this.FileName = FileName;
			this.FileAdress = "src/" + FileName + ".xls";
			init(Title);
		}
		private void init(String title){
			workbook = new HSSFWorkbook();
			sheet = workbook.createSheet(FileName);
			headRow = sheet.createRow(0);
			
			String[] titles = title.split(split);
			for(int i = titleColumns;i < titles.length;i++){
				cell = headRow.createCell(i);
				cell.setCellValue(titles[i]);
				cell.setCellStyle(defaultStyle);
			}
			titleColumns = titles.length;
		}
		public void add(String Data){
			try{
				if(singleRow == null){
					singleRow = sheet.createRow(rows);
				}
				cell = singleRow.createCell(columns);
				cell.setCellValue(Data);
				cell.setCellStyle(defaultStyle);
				columns++;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		public void add(int column,int row,String Data){
			try{
				singleRow = sheet.createRow(row);
				cell = singleRow.createCell(column);
				cell.setCellValue(Data);
				cell.setCellStyle(defaultStyle);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		public void addTitle(String title){
			try{
				cell = headRow.createCell(titleColumns);
				cell.setCellValue(title);
				cell.setCellStyle(defaultStyle);
				titleColumns++;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		public void next(){
			rows++;
			columns = 0;
			singleRow = null;
		}
		public void close(){
			try{
				output = new FileOutputStream(FileAdress);
				workbook.write(output);
				output.close();
				workbook.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}*/
	public class Poi{
		private XSSFWorkbook workbook = null;
		private XSSFSheet sheet = null;
		private XSSFRow headRow = null;
		private XSSFRow singleRow = null;
		private XSSFCellStyle defaultStyle = null;
		private XSSFCell cell = null;
		private FileOutputStream output = null;
		
		private String FileName = null;
		private String FileAdress = null;
		private int titleColumns = 0;
		private int rows = 1;
		private int columns = 0;
		Poi(String FileName,String Title){
			this.FileName = FileName;
			this.FileAdress = "src/" + FileName + ".xlsx";
			init(Title);
		}
		private void init(String title){
			workbook = new XSSFWorkbook();
			sheet = workbook.createSheet(FileName);
			headRow = sheet.createRow(0);
			
			String[] titles = title.split(split);
			for(int i = titleColumns;i < titles.length;i++){
				cell = headRow.createCell(i);
				cell.setCellValue(titles[i]);
				cell.setCellStyle(defaultStyle);
			}
			titleColumns = titles.length;
		}
		public void add(String Data){
			try{
				if(singleRow == null){
					singleRow = sheet.createRow(rows);
				}
				cell = singleRow.createCell(columns);
				cell.setCellValue(Data);
				cell.setCellStyle(defaultStyle);
				columns++;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		public void add(int column,int row,String Data){
			try{
				singleRow = sheet.createRow(row);
				cell = singleRow.createCell(column);
				cell.setCellValue(Data);
				cell.setCellStyle(defaultStyle);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		public void addTitle(String title){
			try{
				cell = headRow.createCell(titleColumns);
				cell.setCellValue(title);
				cell.setCellStyle(defaultStyle);
				titleColumns++;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		public void next(){
			rows++;
			columns = 0;
			singleRow = null;
		}
		public void close(){
			try{
				output = new FileOutputStream(FileAdress);
				workbook.write(output);
				output.close();
				workbook.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
