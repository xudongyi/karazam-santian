/*
 * Copyright 2016 klzan.com. All rights reserved.
 * Support: http://www.klzan.com
 */
package com.klzan.p2p.freemarker.directive;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模板指令 - 分页
 * 
 * @author Karazam Team
 * @version 1.0
 */
@Component("paginationDirective")
public class PaginationDirective extends BaseDirective {
//
//    /** "模式"参数名称 */
//    private static final String PATTERN_PARAMETER_NAME = "pattern";
//
//    /** "页码"参数名称 */
//    private static final String PAGE_NUMBER_PARAMETER_NAME = "pageNumber";
//
//    /** "总页数"参数名称 */
//    private static final String TOTAL_PAGES_PARAMETER_NAME = "totalPages";
//
//    /** "中间段页码数"参数名称 */
//    private static final String SEGMENT_COUNT_PARAMETER_NAME = "segmentCount";
//
//    /** "模式"变量名称 */
//    private static final String PATTERN_VARIABLE_NAME = "pattern";
//
//    /** "页码"变量名称 */
//    private static final String PAGE_NUMBER_VARIABLE_NAME = "pageNumber";
//
//    /** "总页数"变量名称 */
//    private static final String PAGE_COUNT_VARIABLE_NAME = "totalPages";
//
//    /** "中间段页码数"变量名称 */
//    private static final String SEGMENT_COUNT_VARIABLE_NAME = "segmentCount";
//
//    /** "是否存在上一页"变量名称 */
//    private static final String HAS_PREVIOUS_VARIABLE_NAME = "hasPrevious";
//
//    /** "是否存在下一页"变量名称 */
//    private static final String HAS_NEXT_VARIABLE_NAME = "hasNext";
//
//    /** "是否为首页"变量名称 */
//    private static final String IS_FIRST_VARIABLE_NAME = "isFirst";
//
//    /** "是否为末页"变量名称 */
//    private static final String IS_LAST_VARIABLE_NAME = "isLast";
//
//    /** "上一页页码"变量名称 */
//    private static final String PREVIOUS_PAGE_NUMBER_VARIABLE_NAME = "previousPageNumber";
//
//    /** "下一页页码"变量名称 */
//    private static final String NEXT_PAGE_NUMBER_VARIABLE_NAME = "nextPageNumber";
//
//    /** "首页页码"变量名称 */
//    private static final String FIRST_PAGE_NUMBER_VARIABLE_NAME = "firstPageNumber";
//
//    /** "末页页码"变量名称 */
//    private static final String LAST_PAGE_NUMBER_VARIABLE_NAME = "lastPageNumber";
//
//    /** "中间段页码"变量名称 */
//    private static final String SEGMENT_VARIABLE_NAME = "segment";

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        String pattern = String.valueOf(params.get("pattern"));
        Integer pageNumber = Integer.valueOf(String.valueOf(params.get("pageNumber")));
        Integer totalPages = Integer.valueOf(String.valueOf(params.get("totalPages")));
        Integer segmentCount = 0;//Integer.valueOf(String.valueOf(params.get("segmentCount")==null?"":params.get("segmentCount")));

        if (pageNumber == null || pageNumber < 1) {
            pageNumber = 1;
        }
        if (totalPages == null || totalPages < 1) {
            totalPages = 1;
        }
        if (segmentCount == null || segmentCount < 1) {
            segmentCount = 5;
        }
        boolean hasPrevious = pageNumber > 1;
        boolean hasNext = pageNumber < totalPages;
        boolean isFirst = pageNumber == 1;
        boolean isLast = pageNumber.equals(totalPages);
        int previousPageNumber = pageNumber - 1;
        int nextPageNumber = pageNumber + 1;
        int firstPageNumber = 1;
        int lastPageNumber = totalPages;
        int startSegmentPageNumber = pageNumber - (int) Math.floor((segmentCount - 1) / 2D);
        int endSegmentPageNumber = pageNumber + (int) Math.ceil((segmentCount - 1) / 2D);
        if (startSegmentPageNumber < 1) {
            startSegmentPageNumber = 1;
        }
        if (endSegmentPageNumber > totalPages) {
            endSegmentPageNumber = totalPages;
        }
        List<Integer> segment = new ArrayList<Integer>();
        for (int i = startSegmentPageNumber; i <= endSegmentPageNumber; i++) {
            segment.add(i);
        }

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("pattern", pattern);
        variables.put("pageNumber", pageNumber);
        variables.put("totalPages", totalPages);
        variables.put("segmentCount", segmentCount);
        variables.put("hasPrevious", hasPrevious);
        variables.put("hasNext", hasNext);
        variables.put("isFirst", isFirst);
        variables.put("isLast", isLast);
        variables.put("previousPageNumber", previousPageNumber);
        variables.put("nextPageNumber", nextPageNumber);
        variables.put("firstPageNumber", firstPageNumber);
        variables.put("lastPageNumber", lastPageNumber);
        variables.put("segment", segment);
        setLocalVariables(variables, env, body);
    }

}