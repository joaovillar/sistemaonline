package com.jornada.server.framework.excel;

import java.io.FileOutputStream;
import java.util.Date;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.jornada.ConfigJornada;

public class ExcelFramework {
    
    
    public static XSSFCellStyle getStyleHeaderBoletim(XSSFWorkbook wb){
        XSSFCellStyle styleHeader = wb.createCellStyle();
        styleHeader.setAlignment(CellStyle.ALIGN_CENTER);
        styleHeader.setFillPattern(CellStyle.SOLID_FOREGROUND);
        styleHeader.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        styleHeader.setFont(getStyleFontBoletim(wb));
        styleHeader.setBorderBottom(CellStyle.BORDER_THIN);
        styleHeader.setBorderTop(CellStyle.BORDER_THIN);
        styleHeader.setBorderLeft(CellStyle.BORDER_THIN);
        styleHeader.setBorderRight(CellStyle.BORDER_THIN);        
        return styleHeader;
    }
    
    public static XSSFCellStyle getStyleTitleBoletim(XSSFWorkbook wb){
        XSSFCellStyle styleHeader = wb.createCellStyle();
        styleHeader.setAlignment(CellStyle.ALIGN_LEFT);
        styleHeader.setFont(getStyleFontBoldBoletim(wb));      
        return styleHeader;
    }
    
    
    public static Font getStyleFontBoletim(XSSFWorkbook wb){
        Font font = wb.createFont();
        font.setColor(IndexedColors.BLACK.getIndex());
        font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        font.setFontName("sans-serif");        
        return font;
    }   
    
    public static Font getStyleFontBoldBoletim(XSSFWorkbook wb){
        Font font = wb.createFont();
        font.setColor(IndexedColors.BLACK.getIndex());
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setFontName("sans-serif");        
        return font;
    }  
    
    
    public static XSSFCellStyle getStyleCellCenterBoletim(XSSFWorkbook wb){
        XSSFCellStyle styleCellCenter = wb.createCellStyle();
        styleCellCenter.setBorderBottom(CellStyle.BORDER_THIN);
        styleCellCenter.setBorderTop(CellStyle.BORDER_THIN);
        styleCellCenter.setBorderLeft(CellStyle.BORDER_THIN);
        styleCellCenter.setBorderRight(CellStyle.BORDER_THIN);
        styleCellCenter.setAlignment(CellStyle.ALIGN_CENTER);         
        return styleCellCenter;
    }
    
    public static XSSFCellStyle getStyleCellLeftBoletim(XSSFWorkbook wb){
        XSSFCellStyle styleCellLeft = wb.createCellStyle();
        styleCellLeft.setBorderBottom(CellStyle.BORDER_THIN);
        styleCellLeft.setBorderTop(CellStyle.BORDER_THIN);
        styleCellLeft.setBorderLeft(CellStyle.BORDER_THIN);
        styleCellLeft.setBorderRight(CellStyle.BORDER_THIN);
        styleCellLeft.setAlignment(CellStyle.ALIGN_LEFT);         
        return styleCellLeft;
    }
    
    
    public static String getExcelAddress(XSSFWorkbook wb, String strNameExcel){
        String strLong = "";
        try {
            Date data = new Date();
            strLong += strNameExcel + Long.toString(data.getTime()) + ".xlsx";
            FileOutputStream out = new FileOutputStream(ConfigJornada.getProperty("config.download") + ConfigJornada.getProperty("config.download.excel") + strLong);
            wb.write(out);
            out.close();
            out.flush();
        } catch (Exception ex) {
            System.out.print("Error Excel:" + ex.getMessage());
        }

        return ConfigJornada.getProperty("config.download.excel") + strLong;
    }
    
    


}
