package com.example.charttest.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import android.annotation.SuppressLint;
import com.example.charttest.entity.OHLCV;


public class ExcelUtil {
	
	@SuppressLint("NewApi")
	public static List<OHLCV> readOHLCVFromExcel(String fpath,String sheetName){
		if(fpath == null || sheetName == null){
			return null;
		}
		List<OHLCV> result = new ArrayList<OHLCV>();
		try {
			FileInputStream fin = new FileInputStream(fpath);
			HSSFWorkbook  hssf = new HSSFWorkbook (fin);
			HSSFSheet sheet = hssf.getSheet(sheetName);
			int rowIndex = sheet.getFirstRowNum();
			HSSFRow row = sheet.getRow(rowIndex);
			HSSFCell cell;
			while(null != row){
				cell = row.getCell(0);
				String date = cell.getStringCellValue();
				if(null != date && date.isEmpty()){
					OHLCV data = new OHLCV();
					data.setDate(date);
					data.setOpen_price((float) row.getCell(1).getNumericCellValue());
					data.setHigh_price((float) row.getCell(2).getNumericCellValue());
					data.setLow_price((float) row.getCell(3).getNumericCellValue());
					data.setClose_price((float) row.getCell(4).getNumericCellValue());
					data.setVolume((long) row.getCell(5).getNumericCellValue());
					result.add(data);
					
				}else{
					continue;
				}
			}
			
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}

}
