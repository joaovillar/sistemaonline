package com.jornada.server.framework.excel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;


//import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
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
    
    
    public static XSSFCellStyle getStyleHeaderBoletimRotation90Center(XSSFWorkbook wb){
        
        Font font = wb.createFont();
        font.setColor(IndexedColors.BLACK.getIndex());
        font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        font.setFontName("Calibri");  
        font.setFontHeightInPoints((short)10);

        
        XSSFCellStyle styleHeader = wb.createCellStyle();
        styleHeader.setAlignment(CellStyle.ALIGN_CENTER);
        styleHeader.setFillPattern(CellStyle.SOLID_FOREGROUND);
        styleHeader.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        styleHeader.setFont(font);
        styleHeader.setBorderBottom(CellStyle.BORDER_THIN);
        styleHeader.setBorderTop(CellStyle.BORDER_THIN);
        styleHeader.setBorderLeft(CellStyle.BORDER_THIN);
        styleHeader.setBorderRight(CellStyle.BORDER_THIN);   
        styleHeader.setRotation((short)90);
        styleHeader.setWrapText(true);
        styleHeader.setVerticalAlignment(VerticalAlignment.CENTER);
        
        return styleHeader;
    } 
    
    public static XSSFCellStyle getStyleHeaderBoletimRotation90Right(XSSFWorkbook wb){
        
        Font font = wb.createFont();
        font.setColor(IndexedColors.BLACK.getIndex());
        font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        font.setFontName("Calibri");  
        font.setFontHeightInPoints((short)10);

        
        XSSFCellStyle styleHeader = wb.createCellStyle();
        styleHeader.setAlignment(CellStyle.ALIGN_CENTER);
        styleHeader.setFillPattern(CellStyle.SOLID_FOREGROUND);
        styleHeader.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        styleHeader.setFont(font);
        styleHeader.setBorderBottom(CellStyle.BORDER_THIN);
        styleHeader.setBorderTop(CellStyle.BORDER_THIN);
        styleHeader.setBorderLeft(CellStyle.BORDER_THIN);
        styleHeader.setBorderRight(CellStyle.BORDER_THIN);   
        styleHeader.setRotation((short)90);
        styleHeader.setWrapText(true);
        
        return styleHeader;
    }    
    
    public static XSSFCellStyle getStyleTitleBoletim(XSSFWorkbook wb){
        XSSFCellStyle styleHeader = wb.createCellStyle();
        styleHeader.setAlignment(CellStyle.ALIGN_LEFT);
        styleHeader.setFont(getStyleFontBoldBoletim(wb));      
        return styleHeader;
    }
    
    public static XSSFCellStyle getStyleTitleBoletimAno(XSSFWorkbook wb){
        XSSFCellStyle styleHeader = wb.createCellStyle();
        styleHeader.setAlignment(CellStyle.ALIGN_CENTER);
        styleHeader.setFont(getStyleFontBoldBoletim(wb));      
        styleHeader.setVerticalAlignment(VerticalAlignment.CENTER);
        return styleHeader;
    }
    
    
    public static XSSFCellStyle getStyleCellFontBoldCenter(XSSFWorkbook wb){
        XSSFCellStyle styleHeader = wb.createCellStyle();
        styleHeader.setBorderBottom(CellStyle.BORDER_THIN);
        styleHeader.setBorderTop(CellStyle.BORDER_THIN);
        styleHeader.setBorderLeft(CellStyle.BORDER_THIN);
        styleHeader.setBorderRight(CellStyle.BORDER_THIN);
        styleHeader.setAlignment(CellStyle.ALIGN_CENTER); 
        styleHeader.setAlignment(CellStyle.ALIGN_CENTER);
        styleHeader.setFont(getStyleFontBold11(wb));      
        styleHeader.setVerticalAlignment(VerticalAlignment.CENTER);
        return styleHeader;
    }
    
    public static XSSFCellStyle getStyleCellFontBoldCenterNoBorders(XSSFWorkbook wb){
        XSSFCellStyle styleHeader = wb.createCellStyle();
//        styleHeader.setBorderBottom(CellStyle.BORDER_THIN);
//        styleHeader.setBorderTop(CellStyle.BORDER_THIN);
//        styleHeader.setBorderLeft(CellStyle.BORDER_THIN);
//        styleHeader.setBorderRight(CellStyle.BORDER_THIN);
//        styleHeader.setAlignment(CellStyle.ALIGN_CENTER); 
        styleHeader.setAlignment(CellStyle.ALIGN_CENTER);
        styleHeader.setFont(getStyleFontBold11(wb));      
        styleHeader.setVerticalAlignment(VerticalAlignment.CENTER);
        return styleHeader;
    }
    
    public static XSSFCellStyle getStyleCellFontCenterNoBorders(XSSFWorkbook wb){
        XSSFCellStyle styleHeader = wb.createCellStyle();
//        styleHeader.setBorderBottom(CellStyle.BORDER_THIN);
//        styleHeader.setBorderTop(CellStyle.BORDER_THIN);
//        styleHeader.setBorderLeft(CellStyle.BORDER_THIN);
//        styleHeader.setBorderRight(CellStyle.BORDER_THIN);
//        styleHeader.setAlignment(CellStyle.ALIGN_CENTER); 
        styleHeader.setAlignment(CellStyle.ALIGN_CENTER);
//        styleHeader.setFont(getStyleFontBold11(wb));      
        styleHeader.setVerticalAlignment(VerticalAlignment.CENTER);
        return styleHeader;
    }
    
    public static XSSFCellStyle getStyleCellFontBoldLeft(XSSFWorkbook wb){
        XSSFCellStyle styleHeader = wb.createCellStyle();
        styleHeader.setBorderBottom(CellStyle.BORDER_THIN);
        styleHeader.setBorderTop(CellStyle.BORDER_THIN);
        styleHeader.setBorderLeft(CellStyle.BORDER_THIN);
        styleHeader.setBorderRight(CellStyle.BORDER_THIN);
        styleHeader.setAlignment(CellStyle.ALIGN_LEFT); 
        styleHeader.setFont(getStyleFontBold11(wb));      
        styleHeader.setVerticalAlignment(VerticalAlignment.CENTER);
        return styleHeader;
    }
    
    public static XSSFCellStyle getStyleCellFontBoldLeftNoBorders(XSSFWorkbook wb){
        XSSFCellStyle styleHeader = wb.createCellStyle();
//        styleHeader.setBorderBottom(CellStyle.BORDER_THIN);
//        styleHeader.setBorderTop(CellStyle.BORDER_THIN);
//        styleHeader.setBorderLeft(CellStyle.BORDER_THIN);
//        styleHeader.setBorderRight(CellStyle.BORDER_THIN);
        styleHeader.setAlignment(CellStyle.ALIGN_LEFT); 
        styleHeader.setFont(getStyleFontBold11(wb));      
        styleHeader.setVerticalAlignment(VerticalAlignment.CENTER);
        return styleHeader;
    }
    
    public static XSSFCellStyle getStyleCellFontBoletimAluno(XSSFWorkbook wb){
        XSSFCellStyle styleHeader = wb.createCellStyle();
        styleHeader.setAlignment(CellStyle.ALIGN_LEFT);
        styleHeader.setFont(getStyleFontBoletimAluno(wb));      
        styleHeader.setVerticalAlignment(VerticalAlignment.CENTER);
        return styleHeader;
    }
    
    public static XSSFCellStyle getStyleCellFontBoletim(XSSFWorkbook wb){
        XSSFCellStyle styleHeader = wb.createCellStyle();
        styleHeader.setAlignment(CellStyle.ALIGN_CENTER);
        styleHeader.setFont(getStyleFontBoletimAno(wb));      
        styleHeader.setVerticalAlignment(VerticalAlignment.CENTER);
        return styleHeader;
    }
    
    public static XSSFCellStyle getStyleCellFontBoletimDataFinalizacao(XSSFWorkbook wb){
        
        Font font = wb.createFont();
        font.setColor(IndexedColors.BLACK.getIndex());
        font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        font.setFontName("Calibri");  
        font.setFontHeightInPoints((short)8);
        
        XSSFCellStyle styleHeader = wb.createCellStyle();
        
        styleHeader.setBorderBottom(CellStyle.BORDER_THIN);
        styleHeader.setBorderTop(CellStyle.BORDER_THIN);
        styleHeader.setBorderLeft(CellStyle.BORDER_THIN);
        styleHeader.setBorderRight(CellStyle.BORDER_THIN);
        styleHeader.setAlignment(CellStyle.ALIGN_CENTER);  
        
        styleHeader.setAlignment(CellStyle.ALIGN_LEFT);
        styleHeader.setFont(font);      
        styleHeader.setVerticalAlignment(VerticalAlignment.CENTER);
        styleHeader.setWrapText(true);
        return styleHeader;
    }
    
    public static XSSFCellStyle getStyleCellFontAtencao(XSSFWorkbook wb){
        
        Font font = wb.createFont();
        font.setColor(IndexedColors.BLACK.getIndex());
        font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        font.setFontName("Calibri");  
        font.setFontHeightInPoints((short)8);
        
        XSSFCellStyle styleHeader = wb.createCellStyle();
        
        styleHeader.setAlignment(CellStyle.ALIGN_LEFT);
        styleHeader.setFont(font);      
        styleHeader.setVerticalAlignment(VerticalAlignment.CENTER);
        styleHeader.setWrapText(true);
        return styleHeader;
    }
    
    
    public static Font getStyleFontBoletim(XSSFWorkbook wb){
        Font font = wb.createFont();
        font.setColor(IndexedColors.BLACK.getIndex());
        font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        font.setFontName("Calibri");  
        font.setFontHeightInPoints((short)12);
        return font;
    }   
    
    public static Font getStyleFontBoletimAno(XSSFWorkbook wb){
        Font font = wb.createFont();
        font.setColor(IndexedColors.BLACK.getIndex());
        font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        font.setFontName("Calibri");  
        font.setFontHeightInPoints((short)8);
        return font;
    } 
    
    public static Font getStyleFontBoletimAluno(XSSFWorkbook wb){
        Font font = wb.createFont();
        font.setColor(IndexedColors.BLACK.getIndex());
        font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        font.setFontName("Calibri");  
        font.setFontHeightInPoints((short)10);
        return font;
    }  
    
    
    public static Font getStyleFontBoldBoletim(XSSFWorkbook wb){
        Font font = wb.createFont();
        font.setColor(IndexedColors.BLACK.getIndex());
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setFontName("Calibri"); 
        font.setFontHeightInPoints((short)12);
        return font;
    }  
    
    
    public static Font getStyleFontBold11(XSSFWorkbook wb){
        Font font = wb.createFont();
        font.setColor(IndexedColors.BLACK.getIndex());
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setFontName("Calibri"); 
        font.setFontHeightInPoints((short)11);
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
    
    
    public static void createImage(XSSFWorkbook wb, XSSFSheet sheet) {

        try {

            String strFisicalAddress = ConfigJornada.getProperty("config.download");;
            // FileInputStream obtains input bytes from the image file
            InputStream inputStream = new FileInputStream(strFisicalAddress+"/images/logo/logoescola.png");
            System.out.println(strFisicalAddress+"/images/logo/logoescola.png");
            // Get the contents of an InputStream as a byte[].
            byte[] bytes = IOUtils.toByteArray(inputStream);
            // Adds a picture to the workbook
            int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
            // close the input stream
            inputStream.close();

            // Returns an object that handles instantiating concrete classes
            CreationHelper helper = wb.getCreationHelper();

            // Creates the top-level drawing patriarch.
            Drawing drawing = sheet.createDrawingPatriarch();

            // Create an anchor that is attached to the worksheet
            ClientAnchor anchor = helper.createClientAnchor();
            // set top-left corner for the image
            
            anchor.setDx1(1);
            anchor.setDy1(1);

            // Creates a picture
            Picture pict = drawing.createPicture(anchor, pictureIdx);
            // Reset the image to the original size
            pict.resize(0.4);
        } catch (Exception e) {
            System.out.println(e);
        }

    }
    
    
    public static void cleanBeforeMergeOnValidCells(XSSFSheet sheet, CellRangeAddress region, XSSFCellStyle cellStyle )
    {
        for(int rowNum =region.getFirstRow();rowNum<=region.getLastRow();rowNum++){
            XSSFRow row= sheet.getRow(rowNum);
            if(row==null){
                row = sheet.createRow(rowNum);
//                System.out.println("while check row "+rowNum+" was created");
            }
            for(int colNum=region.getFirstColumn();colNum<=region.getLastColumn();colNum++){
                XSSFCell currentCell = row.getCell(colNum); 
               if(currentCell==null){
                   currentCell = row.createCell(colNum);
//                   System.out.println("while check cell "+rowNum+":"+colNum+" was created");
               }    

               currentCell.setCellStyle(cellStyle);

            }
        }


    }

    
    public static void setRegionBorder(XSSFWorkbook wb, XSSFSheet sheet, CellRangeAddress region){
        RegionUtil.setBorderBottom(BorderStyle.THIN.ordinal(), region, sheet, wb);
        RegionUtil.setBorderTop(BorderStyle.THIN.ordinal(), region, sheet, wb);
        RegionUtil.setBorderLeft(BorderStyle.THIN.ordinal(), region, sheet, wb);
        RegionUtil.setBorderRight(BorderStyle.THIN.ordinal(), region, sheet, wb);
    }
    
    public static void setMergeRegionAndSetBorders(XSSFWorkbook wb, XSSFSheet sheet, CellRangeAddress region){
        sheet.addMergedRegion(region);     
        setRegionBorder(wb, sheet, region);
    }
}
