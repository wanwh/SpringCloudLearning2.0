package com.wanwh.api.util;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by wanwh on 2018/9/3 0003.
 */

@Slf4j
public class ExcelUtil {

    private static final int SHEET_NUM_MAX = 65500;//一个sheet最多纪录数


    /*easyPoi  代码start */

    /**
     * easyPoi 导出
     * @param list
     * @param title
     * @param sheetName
     * @param pojoClass
     * @param fileName
     * @param isCreateHeader
     * @param response
     */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass,String fileName,boolean isCreateHeader, HttpServletResponse response){
        ExportParams exportParams = new ExportParams(title, sheetName);
        exportParams.setCreateHeadRows(isCreateHeader);
        defaultExport(list, pojoClass, fileName, response, exportParams);

    }

    /**
     * easyPoi 导出
     * @param list
     * @param title
     * @param sheetName
     * @param pojoClass
     * @param fileName
     * @param response
     */
    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass,String fileName, HttpServletResponse response){
        defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName));
    }

    /**
     * easyPoi 导出
     * @param list
     * @param fileName
     * @param response
     */
    public static void exportExcel(List<Map<String, Object>> list, String fileName, HttpServletResponse response){
        defaultExport(list, fileName, response);
    }

    /**
     * easyPoi 多Sheet导出
     * @param list have to (title,entity,data)
     * @param fileName
     * @param response
     */
    public static void exportExcelMoreSheet(List<Map<String, Object>> list,String fileName, HttpServletResponse response){
        defaultExport(list, fileName, response);
    }

    private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response, ExportParams exportParams) {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams,pojoClass,list);
        if (workbook != null);
        downLoadExcel(fileName, response, workbook);
    }

    private static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    private static void defaultExport(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
        Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
        if (workbook != null);
        downLoadExcel(fileName, response, workbook);
    }

    public static <T> List<T> importExcel(String filePath,Integer titleRows,Integer headerRows, Class<T> pojoClass){
        if (StringUtils.isBlank(filePath)){
            return null;
        }
        ImportParams params = new ImportParams();
        //params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(new File(filePath), pojoClass, params);
        }catch (NoSuchElementException e){
            throw new RuntimeException("模板不能为空");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return list;
    }
    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass){
        if (file == null){
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
        }catch (NoSuchElementException e){
            throw new RuntimeException("excel文件不能为空");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    //欧菲光外检数据导出
    public static void exportWJDataExcel(String title, String[] headers,
                                         String[] fileds, List<Map> dataset, OutputStream out,
                                         String pattern) {
        HSSFWorkbook wb = new HSSFWorkbook();// 声明一个工作薄
        int sheetNum = dataset.size() / SHEET_NUM_MAX + 1;
        for (int num = 0; num < sheetNum; num++) {
            HSSFSheet sheet = wb.createSheet(title + (num==0?"":"-"+num));// 生成一个表格
            sheet.setDefaultColumnWidth(15);// 设置表格默认列宽度为15个字节

            // 设置这些样式
            HSSFCellStyle style = wb.createCellStyle();
            style.setFillBackgroundColor(HSSFColor.LIGHT_BLUE.index);
            style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            style.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

            // 生成一个字体
            HSSFFont font = wb.createFont();
            font.setColor(HSSFColor.VIOLET.index);
            font.setFontHeightInPoints((short) 12);
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

            // 把字体应用到当前的样式
            style.setFont(font);

            // 生成并设置另一个样式
            HSSFCellStyle style2 = wb.createCellStyle();
            style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
            style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

            // 生成另一个字体
            HSSFFont font2 = wb.createFont();
            font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
            // 把字体应用到当前的样式
            style2.setFont(font2);

            // 声明一个画图的顶级管理器
            HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
            // 定义注释的大小和位置,详见文档
            HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(
                    0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
            // 设置注释内容
            comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
            // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
            comment.setAuthor("leno");
            // 产生表格标题行
            HSSFRow row = sheet.createRow(0);
            for (short i = 0; i < headers.length; i++) {
                HSSFCell cell = row.createCell(i);
                // cell.setCellStyle(style);
                HSSFRichTextString text = new HSSFRichTextString(headers[i]);
                HSSFFont font3 = wb.createFont();
                font3.setColor(HSSFColor.BLUE.index);
                font3.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                text.applyFont(font3);
                cell.setCellValue(text);
            }// 遍历集合数据，产生数据行

            for(int index = 0; index < (num==(sheetNum-1) ? (dataset.size()-(SHEET_NUM_MAX*(sheetNum-1))): SHEET_NUM_MAX); index++){
                row = sheet.createRow(index+1);
                Map map = dataset.get(index + (SHEET_NUM_MAX*num));
                for (int i = 0; i < fileds.length; i++) {
                    HSSFCell cell = row.createCell(i);
                    // cell.setCellStyle(style2);
                    try {
                        String field  = fileds[i];

                        String value = map.get(fileds[i]) == null ? "" : map
                                .get(fileds[i]) + "";
                        Pattern p = Pattern.compile("^\\d+(\\.\\d+)?$");
                        Matcher matcher = p.matcher(value);
                        if (matcher.matches()) {
                            // 判断值的类型后进行强制类型转换
                            Double val = Double.parseDouble(value);
                            if(field.equals("WEIGHTZS")) {
                                if(val > 24) {
                                    cell.setCellStyle(style2);
                                }
                            } else if(field.equals("EYEL") || field.equals("EYER") || field.equals("EYE2L") || field.equals("EYE2R")) {
                                if(val < 0.5) {
                                    cell.setCellStyle(style2);
                                }
                            } else if(field.equals("GBZAM") || field.equals("GCZAM")) {
                                if(val > 40) {
                                    cell.setCellStyle(style2);
                                }
                            }
                        }
                        if(field.equals("YJ")) {
                            if(value.equals("阳性")) {
                                cell.setCellStyle(style2);
                            }
                        }
                        HSSFRichTextString richString = new HSSFRichTextString(
                                value);
                        cell.setCellValue(richString);
                        // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
//						if (value != null) {
//							Pattern p = Pattern.compile("^\\d+(\\.\\d+)?$");
//							Matcher matcher = p.matcher(value);
//							if (matcher.matches()) {
//								// 是数字当作double处理
//								cell.setCellValue(Double.parseDouble(textValue));
//							} else {
//
//								// HSSFFont font3 = wb.createFont();
//								// font3.setColor(HSSFColor.BLUE.index);
//								// richString.applyFont(font3);
//								cell.setCellValue(richString);
//							}
//						}
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    } finally {
                        // 清理资源
                    }
                }
            }
        }
        try {
            wb.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
