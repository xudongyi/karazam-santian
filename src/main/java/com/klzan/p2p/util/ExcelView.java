/*
 * Copyright 2015-2017 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */
package com.klzan.p2p.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.util.Assert;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * Excel视图
 * 
 * @author Karazam Team
 * @version 1.0
 */
public class ExcelView extends AbstractExcelView {

    /** 默认日期格式配比 */
    private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /** 文件名称 */
    private String filename;

    /** 表名称 */
    private String sheetName;

    /** 属性 */
    private String[] properties;

    /** 标题 */
    private String[] titles;

    /** 列宽 */
    private Integer[] widths;

    /** 类型转换 */
    private Converter[] converters;

    /** 数据 */
    private Collection<?> data;

    /** 附加内容 */
    private String[] contents;

    static {
        // 日期转换
        DateConverter dateConverter = new DateConverter();
        // 设置日期格式
        dateConverter.setPattern(DEFAULT_DATE_PATTERN);
        // 注册日期转换
        ConvertUtils.register(dateConverter, Date.class);
    }

    /**
     * @param filename
     *            文件名称
     * @param sheetName
     *            表名称
     * @param properties
     *            属性
     * @param titles
     *            标题
     * @param widths
     *            列宽
     * @param converters
     *            类型转换
     * @param data
     *            数据
     * @param contents
     *            附加内容
     */
    public ExcelView(String filename, String sheetName, String[] properties, String[] titles, Integer[] widths,
                     Converter[] converters, Collection<?> data, String[] contents) {
        this.filename = filename;
        this.sheetName = sheetName;
        this.properties = properties;
        this.titles = titles;
        this.widths = widths;
        this.converters = converters;
        this.data = data;
        this.contents = contents;
    }

    /**
     * @param properties
     *            属性
     * @param titles
     *            标题
     * @param data
     *            数据
     * @param contents
     *            附加内容
     */
    public ExcelView(String[] properties, String[] titles, Collection<?> data, String[] contents) {
        this.properties = properties;
        this.titles = titles;
        this.data = data;
        this.contents = contents;
    }

    /**
     * @param properties
     *            属性
     * @param titles
     *            标题
     * @param data
     *            数据
     */
    public ExcelView(String[] properties, String[] titles, Collection<?> data) {
        this.properties = properties;
        this.titles = titles;
        this.data = data;
    }

    /**
     * @param properties
     *            属性
     * @param data
     *            数据
     */
    public ExcelView(String[] properties, Collection<?> data) {
        this.properties = properties;
        this.data = data;
    }

    /**
     * 生成Excel文档
     * 
     * @param model
     *            数据
     * @param workbook
     *            工作簿
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     */
    @Override
    public void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {

        Assert.notEmpty(properties);

        // 页签
        HSSFSheet sheet;
        if (StringUtils.isNotBlank(sheetName)) {
            sheet = workbook.createSheet(sheetName);
        } else {
            sheet = workbook.createSheet();
        }

        // 行号
        int rowNumber = 0;

        // 表头行
        if (titles != null && titles.length > 0) {

            // 创建行（表头）
            HSSFRow header = sheet.createRow(rowNumber);
            // 高度
            header.setHeight((short) 400);

            // 遍历一次生成单元格
            for (int i = 0; i < properties.length; i++) {
                // 单元格
                HSSFCell cell = header.createCell(i);

                // 设置样式
                HSSFCellStyle cellStyle = workbook.createCellStyle();
                cellStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
                cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
                // 字体
                HSSFFont font = workbook.createFont();
                font.setFontHeightInPoints((short) 11);
                font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                cellStyle.setFont(font);
                cell.setCellStyle(cellStyle);

                if (i == 0) {
                    // 创建人
                    HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
                    // 注释
                    HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 1, 1,
                            (short) 4, 4));
                    comment.setString(new HSSFRichTextString("卡拉赞信息技术有限公司提供"));
                    cell.setCellComment(comment);
                }

                // 设置单元格值
                if (titles.length > i && titles[i] != null) {
                    cell.setCellValue(titles[i]);
                } else {
                    cell.setCellValue(properties[i]);
                }

                // 设置列宽
                if (widths != null && widths.length > i && widths[i] != null) {
                    // 固定列宽
                    sheet.setColumnWidth(i, widths[i]);
                } else {
                    // 自动列宽
                    sheet.autoSizeColumn(i);
                }
            }
            rowNumber++;
        }

        // 内容行
        if (data != null) {

            // 遍历一次生成行
            for (Object item : data) {

                // 创建行
                HSSFRow row = sheet.createRow(rowNumber);

                // 遍历一次生成单元格
                for (int i = 0; i < properties.length; i++) {
                    // 单元格
                    HSSFCell cell = row.createCell(i);

                    if (converters != null && converters.length > i && converters[i] != null) {
                        Class<?> clazz = PropertyUtils.getPropertyType(item, properties[i]);
                        // 注册
                        ConvertUtils.register(converters[i], clazz);
                        // 设置单元格值
                        cell.setCellValue(BeanUtils.getProperty(item, properties[i]));
                        // 注销
                        ConvertUtils.deregister(clazz);

                        // 为日期转换时
                        if (clazz.equals(Date.class)) {
                            // 日期转换
                            DateConverter dateConverter = new DateConverter();
                            // 设置日期格式
                            dateConverter.setPattern(DEFAULT_DATE_PATTERN);
                            // 注册日期转换
                            ConvertUtils.register(dateConverter, Date.class);
                        }

                    } else {
                        // 设置单元格值
                        cell.setCellValue(BeanUtils.getProperty(item, properties[i]));
                    }

                    // 设置列宽
                    if (rowNumber == 0 || rowNumber == 1) {
                        if (widths != null && widths.length > i && widths[i] != null) {
                            // 固定列宽
                            sheet.setColumnWidth(i, widths[i]);
                        } else {
                            // 自动列宽
                            sheet.autoSizeColumn(i);
                        }
                    }
                }
                rowNumber++;
            }
        }

        // 附加内容行
        if (contents != null && contents.length > 0) {

            rowNumber++;

            // 内容
            for (String content : contents) {

                // 创建行
                HSSFRow row = sheet.createRow(rowNumber);

                // 创建单元格
                HSSFCell cell = row.createCell(0);

                // 设置样式
                HSSFCellStyle cellStyle = workbook.createCellStyle();
                // 设置样式 - 字体
                HSSFFont font = workbook.createFont();
                font.setColor(HSSFColor.GREY_50_PERCENT.index);
                cellStyle.setFont(font);
                cell.setCellStyle(cellStyle);

                // 设置单元格值
                cell.setCellValue(content);

                rowNumber++;
            }
        }

        // 设置HTTP响应内容内容
        response.setContentType("application/force-download");

        // 设置HTTP响应头部信息
        if (StringUtils.isNotBlank(filename)) {
            // 处理方式: 附件、保存指定文件名
            response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
        } else {
            // 处理方式: 附件
            response.setHeader("Content-disposition", "attachment");
        }

    }

    public String getFileName() {
        return filename;
    }

    public void setFileName(String filename) {
        this.filename = filename;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String[] getProperties() {
        return properties;
    }

    public void setProperties(String[] properties) {
        this.properties = properties;
    }

    public String[] getTitles() {
        return titles;
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    public Integer[] getWidths() {
        return widths;
    }

    public void setWidths(Integer[] widths) {
        this.widths = widths;
    }

    public Converter[] getConverters() {
        return converters;
    }

    public void setConverters(Converter[] converters) {
        this.converters = converters;
    }

    public Collection<?> getData() {
        return data;
    }

    public void setData(Collection<?> data) {
        this.data = data;
    }

    public String[] getContents() {
        return contents;
    }

    public void setContents(String[] contents) {
        this.contents = contents;
    }

}