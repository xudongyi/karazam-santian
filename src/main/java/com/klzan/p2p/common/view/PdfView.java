/*
 * Copyright 2015-2017 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */
package com.klzan.p2p.common.view;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.util.Map;

/**
* Pdf视图
*
* @author Karazam Team
* @version 1.0
*/
public class PdfView extends AbstractView {

   /** 内容类型 */
   private static final String CONTENT_TYPE = "application/pdf";

   /** 文件名称 */
   private String filename;

   /** 内容 */
   private String cont;

   public PdfView() {
       setContentType(CONTENT_TYPE);
   }

   /**
    * @param filename
    *            文件名
    * @param cont
    *            内容
    */
   public PdfView(String filename, String cont) {
       this.filename = filename;
       this.cont = cont;
   }

   /**
    * 是否生成下载内容
    *
    * @return 是
    */
   @Override
   protected boolean generatesDownloadContent() {
       return true;
   }

   /**
    * 读取合并输出模型
    *
    * @param model
    *            数据
    * @param request
    *            HttpServletRequest
    * @param response
    *            HttpServletResponse
    * @throws Exception
    *             读取失败的异常
    */
   @Override
   protected final void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
           HttpServletResponse response) throws Exception {

       // IE workaround: write into byte array first.
       ByteArrayOutputStream baos = createTemporaryOutputStream();

       // Apply preferences and build metadata.
       Document document = newDocument();
       PdfWriter writer = newWriter(document, baos);
       prepareWriter(model, writer, request);
       buildPdfMetadata(model, document, request);

       // Build PDF document.
       document.open();
       buildPdfDocument(model, document, writer, request, response);
       document.close();

       // Flush to HTTP response.
       writeToResponse(response, baos);
   }

   /**
    * 新建文档
    *
    * @return 文档
    */
   protected Document newDocument() {
       return new Document(PageSize.A4);
   }

   /**
    * 新建PdfWriter
    *
    * @param document
    *            文档
    * @param baos
    *            输出流
    * @return PdfWriter
    * @throws DocumentException
    *             新建失败的异常
    */
   protected PdfWriter newWriter(Document document, ByteArrayOutputStream baos) throws DocumentException {
       return PdfWriter.getInstance(document, baos);
   }

   /**
    * 准备Writer
    *
    * @param model
    *            数据
    * @param writer
    *            PdfWriter
    * @param request
    *            HttpServletRequest
    * @throws DocumentException
    *             准备失败的异常
    */
   protected void prepareWriter(Map<String, Object> model, PdfWriter writer, HttpServletRequest request)
           throws DocumentException {
       writer.setViewerPreferences(getViewerPreferences());
   }

   /**
    * 获取PdfWriter选项
    *
    * @return PdfWriter选项
    */
   protected int getViewerPreferences() {
       return PdfWriter.ALLOW_PRINTING | PdfWriter.PageLayoutSinglePage;
   }

   /**
    * 生成PDF元数据
    *
    * @param model
    *            数据
    * @param document
    *            文档
    * @param request
    *            HttpServletRequest
    */
   protected void buildPdfMetadata(Map<String, Object> model, Document document, HttpServletRequest request) {
   }

   /**
    * 生成Pdf文档
    *
    * @param model
    *            数据
    * @param document
    *            文档
    * @param request
    *            HttpServletRequest
    * @param response
    *            HttpServletResponse
    */
   protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
           HttpServletRequest request, HttpServletResponse response) throws Exception {

       ByteArrayInputStream bais = new ByteArrayInputStream(cont.getBytes());

       // HTML、CSS设置
       HtmlPipelineContext htmlContext = new HtmlPipelineContext(null);
       htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
       CSSResolver cssResolver = XMLWorkerHelper.getInstance().getDefaultCssResolver(true);
       CssResolverPipeline cssPipeline = new CssResolverPipeline(cssResolver, new HtmlPipeline(htmlContext,
               new PdfWriterPipeline(document, writer)));

       // HTML解析
       XMLWorker worker = new XMLWorker(cssPipeline, false);
       XMLParser parser = new XMLParser(worker);
       parser.parse(bais);
       parser.flush();

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

   public String getFilename() {
       return filename;
   }

   public void setFilename(String filename) {
       this.filename = filename;
   }

   public String getCont() {
       return cont;
   }

   public void setCont(String cont) {
       this.cont = cont;
   }

}