package com.klzan.core.util;

import com.klzan.core.PlatformConstant;
import com.klzan.core.setting.WebSetting;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;
import java.util.Map.Entry;

/**
 * @class : WebUtils
 *
 * @author suhao
 * @version 1.0
 */
public class WebUtils {
    private static final Logger logger = LoggerFactory.getLogger(WebUtils.class);
    /**
     * 不可实例化
     */
    private WebUtils() {
    }

    /**
     * 添加Cookie
     *
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @param name
     *            Cookie名称
     * @param value
     *            Cookie值
     * @param maxAge
     *            有效期(单位：秒)
     * @param path
     *            路径
     * @param domain
     *            域
     * @param secure
     *            是否启用加密
     */
    public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value,
                                 Integer maxAge, String path, String domain, Boolean secure) {

        Assert.notNull(request);
        Assert.notNull(response);
        Assert.hasText(name);

        try {
            name = URLEncoder.encode(name, "UTF-8");
            value = URLEncoder.encode(value, "UTF-8");
            Cookie cookie = new Cookie(name, value);
            if (maxAge != null) {
                cookie.setMaxAge(maxAge);
            }
            if (org.apache.commons.lang3.StringUtils.isNotBlank(path)) {
                cookie.setPath(path);
            }
            if (org.apache.commons.lang3.StringUtils.isNotBlank(domain)) {
                cookie.setDomain(domain);
            }
            if (secure != null) {
                cookie.setSecure(secure);
            }
            response.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加Cookie
     *
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @param name
     *            Cookie名称
     * @param value
     *            Cookie值
     * @param maxAge
     *            有效期(单位：秒)
     */
    public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value,
                                 Integer maxAge) {
        addCookie(request, response, name, value, maxAge, "/", "", null);
    }

    /**
     * 添加Cookie
     *
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @param name
     *            Cookie名称
     * @param value
     *            Cookie值
     */
    public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value) {
        addCookie(request, response, name, value, null, "/", "", null);
    }

    /**
     * 获取Cookie
     *
     * @param request
     *            HttpServletRequest
     * @param name
     *            Cookie名称
     * @return 不存在时返回NULL
     */
    public static String getCookie(HttpServletRequest request, String name) {

        Assert.notNull(request);
        Assert.hasText(name);

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            try {
                name = URLEncoder.encode(name, "UTF-8");
                for (Cookie cookie : cookies) {
                    if (name.equals(cookie.getName())) {
                        return URLDecoder.decode(cookie.getValue(), "UTF-8");
                    }
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 移除Cookie
     *
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @param name
     *            Cookie名称
     * @param path
     *            路径
     * @param domain
     *            域
     */
    public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String name, String path,
            String domain) {

        Assert.notNull(request);
        Assert.notNull(response);
        Assert.hasText(name);

        try {
            name = URLEncoder.encode(name, "UTF-8");
            Cookie cookie = new Cookie(name, null);
            cookie.setMaxAge(0);
            if (StringUtils.isNotBlank(path)) {
                cookie.setPath(path);
            }
            if (StringUtils.isNotBlank(domain)) {
                cookie.setDomain(domain);
            }
            response.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 重构Session（防止Session Fixation攻击）
     *
     * @param request
     *            HttpServletRequest
     */
    public static void refactorSession(HttpServletRequest request) {

        // 销毁Session
        HttpSession session = request.getSession();
        Map<String, Object> attributes = new HashMap<String, Object>();
        Enumeration<?> keys = session.getAttributeNames();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            attributes.put(key, session.getAttribute(key));
        }
        session.invalidate();

        // 重造Session
        session = request.getSession();
        for (Entry<String, Object> entry : attributes.entrySet()) {
            session.setAttribute(entry.getKey(), entry.getValue());
        }
    }

    /**
     * 获取参数
     *
     * @param queryString
     *            查询字符串
     * @param encoding
     *            编码格式
     * @param name
     *            参数名称
     * @return 参数
     */
    public static String getParameter(String queryString, String encoding, String name) {
        String[] parameterValues = getParameterMap(queryString, encoding).get(name);
        return parameterValues != null && parameterValues.length > 0 ? parameterValues[0] : null;
    }

    /**
     * 获取参数
     *
     * @param queryString
     *            查询字符串
     * @param encoding
     *            编码格式
     * @param name
     *            参数名称
     * @return 参数
     */
    public static String[] getParameterValues(String queryString, String encoding, String name) {
        return getParameterMap(queryString, encoding).get(name);
    }

    /**
     * 获取参数
     *
     * @param queryString
     *            查询字符串
     * @param encoding
     *            编码格式
     * @return 参数
     */
    public static Map<String, String[]> getParameterMap(String queryString, String encoding) {
        Map<String, String[]> parameterMap = new HashMap<String, String[]>();
        Charset charset = Charset.forName(encoding);
        if (StringUtils.isNotBlank(queryString)) {
            byte[] bytes = queryString.getBytes(charset);
            if (bytes != null && bytes.length > 0) {
                int ix = 0;
                int ox = 0;
                String key = null;
                String value = null;
                while (ix < bytes.length) {
                    byte c = bytes[ix++];
                    switch ((char) c) {
                        case '&':
                            value = new String(bytes, 0, ox, charset);
                            if (key != null) {
                                putMapEntry(parameterMap, key, value);
                                key = null;
                            }
                            ox = 0;
                            break;
                        case '=':
                            if (key == null) {
                                key = new String(bytes, 0, ox, charset);
                                ox = 0;
                            } else {
                                bytes[ox++] = c;
                            }
                            break;
                        case '+':
                            bytes[ox++] = (byte) ' ';
                            break;
                        case '%':
                            bytes[ox++] = (byte) ((convertHexDigit(bytes[ix++]) << 4) + convertHexDigit(bytes[ix++]));
                            break;
                        default:
                            bytes[ox++] = c;
                    }
                }
                if (key != null) {
                    value = new String(bytes, 0, ox, charset);
                    putMapEntry(parameterMap, key, value);
                }
            }
        }
        return parameterMap;
    }

    /**
     * 根据Map获取name键得到值，将值对应字符串数组最后添加一个组件
     *
     * @param map
     *            Map集合
     * @param name
     *            name键
     * @param value
     *            需要添加的组件（字符串）
     */
    private static void putMapEntry(Map<String, String[]> map, String name, String value) {
        String[] newValues = null;
        String[] oldValues = map.get(name);
        if (oldValues == null) {
            newValues = new String[] { value };
        } else {
            newValues = new String[oldValues.length + 1];
            // 从指定源数组中复制一个数组，复制从指定的位置开始，到目标数组的指定位置结束。
            System.arraycopy(oldValues, 0, newValues, 0, oldValues.length);
            newValues[oldValues.length] = value;
        }
        map.put(name, newValues);
    }

    /**
     * 转换为十六进制
     *
     * @param b
     *            需要转换的字节
     * @return 转换后的字节
     */
    private static byte convertHexDigit(byte b) {
        if ((b >= '0') && (b <= '9')) {
            return (byte) (b - '0');
        }
        if ((b >= 'a') && (b <= 'f')) {
            return (byte) (b - 'a' + 10);
        }
        if ((b >= 'A') && (b <= 'F')) {
            return (byte) (b - 'A' + 10);
        }
        throw new IllegalArgumentException();
    }

    public static boolean isAjax(HttpServletRequest request) {
        boolean ajax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
        String ajaxFlag = null == request.getParameter("ajax") ? "false" : request.getParameter("ajax");
        boolean isAjax = ajax || ajaxFlag.equalsIgnoreCase("true");
        return isAjax;
    }

    /**
     * 判断是否ajax请求
     * spring ajax 返回含有 ResponseBody 或者 RestController注解
     * @param handlerMethod HandlerMethod
     * @return 是否ajax请求
     */
    public static boolean isAjax(HandlerMethod handlerMethod) {
        ResponseBody responseBody = handlerMethod.getMethodAnnotation(ResponseBody.class);
        if (null != responseBody) {
            return true;
        }
        RestController restAnnotation = handlerMethod.getBeanType().getAnnotation(RestController.class);
        if (null != restAnnotation) {
            return true;
        }
        return false;
    }

    /**
     * 将数据以 JSON 格式写入响应中
     */
    public static void writeJSON(HttpServletResponse response, Object data) {
        try {
            // 设置响应头
            response.setContentType("application/json"); // 指定内容类型为 JSON 格式
            response.setCharacterEncoding("UTF-8"); // 防止中文乱码
            // 向响应中写入数据
            PrintWriter writer = response.getWriter();
            writer.write(JsonUtils.toJson(data)); // 转为 JSON 字符串
            writer.flush();
            writer.close();
        } catch (Exception e) {
            logger.error("在响应中写数据出错！", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 将数据以 HTML 格式写入响应中（在 JS 中获取的是 JSON 字符串，而不是 JSON 对象）
     */
    public static void writeHTML(HttpServletResponse response, Object data) {
        try {
            // 设置响应头
            response.setContentType("text/html"); // 指定内容类型为 HTML 格式
            response.setCharacterEncoding("UTF-8"); // 防止中文乱码
            // 向响应中写入数据
            PrintWriter writer = response.getWriter();
            writer.write(JsonUtils.toJson(data)); // 转为 JSON 字符串
            writer.flush();
            writer.close();
        } catch (Exception e) {
            logger.error("在响应中写数据出错！", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 从请求中获取所有参数（当参数名重复时，用后者覆盖前者）
     */
    public static Map<String, Object> getRequestParamMap(HttpServletRequest request) {
        Map<String, Object> paramMap = new LinkedHashMap<String, Object>();
        try {
            String method = request.getMethod();
            if (method.equalsIgnoreCase("put") || method.equalsIgnoreCase("delete")) {
                String queryString = EncodeUtils.urlDecode(StreamUtils.getString(request.getInputStream()));
                if (StringUtils.isNotEmpty(queryString)) {
                    String[] qsArray = StringUtils.splitString(queryString, "&");
                    if (ArrayUtils.isNotEmpty(qsArray)) {
                        for (String qs : qsArray) {
                            String[] array = StringUtils.splitString(qs, "=");
                            if (ArrayUtils.isNotEmpty(array) && array.length == 2) {
                                String paramName = array[0];
                                String paramValue = array[1];
                                if (checkParamName(paramName)) {
                                    if (paramMap.containsKey(paramName)) {
                                        paramValue = paramMap.get(paramName) + StringUtils.SEPARATOR + paramValue;
                                    }
                                    paramMap.put(paramName, paramValue);
                                }
                            }
                        }
                    }
                }
            } else {
                Enumeration<String> paramNames = request.getParameterNames();
                while (paramNames.hasMoreElements()) {
                    String paramName = paramNames.nextElement();
                    if (checkParamName(paramName)) {
                        String[] paramValues = request.getParameterValues(paramName);
                        if (ArrayUtils.isNotEmpty(paramValues)) {
                            if (paramValues.length == 1) {
                                paramMap.put(paramName, paramValues[0]);
                            } else {
                                StringBuilder paramValue = new StringBuilder("");
                                for (int i = 0; i < paramValues.length; i++) {
                                    paramValue.append(paramValues[i]);
                                    if (i != paramValues.length - 1) {
                                        paramValue.append(StringUtils.SEPARATOR);
                                    }
                                }
                                paramMap.put(paramName, paramValue.toString());
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("获取请求参数出错！", e);
            throw new RuntimeException(e);
        }
        return paramMap;
    }

    private static boolean checkParamName(String paramName) {
        return !paramName.equals("_"); // 忽略 jQuery 缓存参数
    }

    /**
     * 转发请求
     */
    public static void forwardRequest(String path, HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher(path).forward(request, response);
        } catch (Exception e) {
            logger.error("转发请求出错！", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 重定向请求
     */
    public static void redirectRequest(String path, HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendRedirect(request.getContextPath() + path);
        } catch (Exception e) {
            logger.error("重定向请求出错！", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 发送错误代码
     */
    public static void sendError(int code, String message, HttpServletResponse response) {
        try {
            response.sendError(code, message);
        } catch (Exception e) {
            logger.error("发送错误代码出错！", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 判断是否为 AJAX 请求
     */
    public static boolean isAJAX(HttpServletRequest request) {
        return request.getHeader("X-Requested-With") != null;
    }

    /**
     * 获取请求路径
     */
    public static String getRequestPath(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        String pathInfo = StringUtils.defaultIfEmpty(request.getPathInfo(), "");
        return servletPath + pathInfo;
    }

    /**
     * 下载文件
     */
    public static void downloadFile(HttpServletResponse response, String filePath) {
        try {
            String originalFileName = FilenameUtils.getName(filePath);
            String downloadedFileName = new String(originalFileName.getBytes("GBK"), "ISO8859_1"); // 防止中文乱码

            response.setContentType("application/octet-stream");
            response.addHeader("Content-Disposition", "attachment;filename=\"" + downloadedFileName + "\"");

            InputStream inputStream = new BufferedInputStream(new FileInputStream(filePath));
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            StreamUtils.copyStream(inputStream, outputStream);
        } catch (Exception e) {
            logger.error("下载文件出错！", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 设置 Redirect URL 到 Session 中
     */
    public static void setRedirectUrl(HttpServletRequest request, String sessionKey) {
        if (!isAJAX(request)) {
            String requestPath = getRequestPath(request);
            request.getSession().setAttribute(sessionKey, requestPath);
        }
    }

    /**
     * 创建验证码
     */
    public static String createCaptcha(HttpServletResponse response) {
        StringBuilder captcha = new StringBuilder();
        try {
            // 参数初始化
            int width = 60;                      // 验证码图片的宽度
            int height = 25;                     // 验证码图片的高度
            int codeCount = 4;                   // 验证码字符个数
            int codeX = width / (codeCount + 1); // 字符横向间距
            int codeY = height - 4;              // 字符纵向间距
            int fontHeight = height - 2;         // 字体高度
            int randomSeed = 10;                 // 随机数种子
            char[] codeSequence = {              // 验证码中可出现的字符
                    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
            };
            // 创建图像
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bi.createGraphics();
            // 将图像填充为白色
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, width, height);
            // 设置字体
            g.setFont(new Font("Courier New", Font.BOLD, fontHeight));
            // 绘制边框
            g.setColor(Color.BLACK);
            g.drawRect(0, 0, width - 1, height - 1);
            // 产生随机干扰线（160条）
            g.setColor(Color.WHITE);
            // 创建随机数生成器
            Random random = new Random();
            for (int i = 0; i < 160; i++) {
                int x = random.nextInt(width);
                int y = random.nextInt(height);
                int xl = random.nextInt(12);
                int yl = random.nextInt(12);
                g.drawLine(x, y, x + xl, y + yl);
            }
            // 生成随机验证码
            int red, green, blue;
            for (int i = 0; i < codeCount; i++) {
                // 获取随机验证码
                String validateCode = String.valueOf(codeSequence[random.nextInt(randomSeed)]);
                // 随机构造颜色值
                red = random.nextInt(255);
                green = random.nextInt(255);
                blue = random.nextInt(255);
                // 将带有颜色的验证码绘制到图像中
                g.setColor(new Color(red, green, blue));
                g.drawString(validateCode, (i + 1) * codeX - 6, codeY);
                // 将产生的随机数拼接起来
                captcha.append(validateCode);
            }
            // 禁止图像缓存
            response.setHeader("Cache-Control", "no-store");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
            // 设置响应类型为 JPEG 图片
            response.setContentType("image/jpeg");
            // 将缓冲图像写到 Servlet 输出流中
            ServletOutputStream sos = response.getOutputStream();
            ImageIO.write(bi, "jpeg", sos);
            sos.close();
        } catch (Exception e) {
            logger.error("创建验证码出错！", e);
            throw new RuntimeException(e);
        }
        return captcha.toString();
    }

    /**
     * 是否为 IE 浏览器
     */
    public boolean isIE(HttpServletRequest request) {
        String agent = request.getHeader("User-Agent");
        return agent != null && agent.contains("MSIE");
    }

    public static HttpServletRequest getHttpRequest() {
        return  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 判断当前环境是否是一个httprequest环境
     * @return
     */
    public static boolean underHttpRequestContext() {
        ServletRequestAttributes ra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(ra == null) return false;
        return  ra.getRequest() != null;
    }

    public static Map<String, String> getHttpHeaders(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, String> headerMap = new HashMap<String, String>();
        while(headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headerMap.put(headerName, request.getHeader(headerName));
        }
        return headerMap;
    }

    /**
     * get the real http request scheme.
     * @param request
     * @return
     */
    public static String getHttpScheme(HttpServletRequest request) {
        if(request == null) request = WebUtils.getHttpRequest();
        String forwardScheme = getHttpHeader(request, "x-forwarded-proto");
        if(StringUtils.isBlank(forwardScheme)) {
            forwardScheme = request.getScheme();
        }
        return forwardScheme;
    }

    /**
     * get the real host.
     * @param request
     * @return
     */
    public static String getHost(HttpServletRequest request) {
        String host = getHttpHeader(request, "host");
        if(StringUtils.isBlank(host)) {
            host = request.getRemoteHost();
        }
        return host;
    }

    /**
     * 获取IP
     * @param request
     * @return
     */
    public static String getRemoteIp(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        String remoteIp = request.getHeader("x-forwarded-for");
        if (StringUtils.isBlank(remoteIp) || StringUtils.equalsIgnoreCase(remoteIp, "unknown")) {
            remoteIp = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(remoteIp) || StringUtils.equalsIgnoreCase(remoteIp, "unknown")) {
            remoteIp = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(remoteIp) || StringUtils.equalsIgnoreCase(remoteIp, "unknown")) {
            remoteIp = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(remoteIp) || StringUtils.equalsIgnoreCase(remoteIp, "unknown")) {
            remoteIp = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(remoteIp) || StringUtils.equalsIgnoreCase(remoteIp, "unknown")) {
            remoteIp = request.getRemoteAddr();
        }
        return remoteIp;
    }

    /**
     * get the real port.
     * @param request
     * @return
     */
    public static int getRealPort(HttpServletRequest request) {
        if(request == null) {
            request = getHttpRequest();
        }
        String host = getHttpHeader(request, "host");
        System.out.println(host);
        if(StringUtils.isBlank(host)) {
            return request.getRemotePort();
        }
        String[] hostAndPort = host.split(":");
        if(hostAndPort.length > 1) {
            return Integer.parseInt(hostAndPort[1]);
        }
        return request.getRemotePort();
    }

    public static String getHttpHeader(HttpServletRequest request, String headerName) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            String _header = headerNames.nextElement();
            if(headerName.equalsIgnoreCase(_header)) {
                return request.getHeader(_header);
            }
        }
        return null;
    }

    /**
     * 取得带相同前缀的Request Parameters.
     *
     * 返回的结果的Parameter名已去除前缀.
     */
    @SuppressWarnings({"rawtypes" })
    public static Map<String, Object> getParametersStartingWith(ServletRequest request, String prefix) {
        Assert.notNull(request, "Request must not be null");
        Enumeration paramNames = request.getParameterNames();
        Map<String, Object> params = new TreeMap<String, Object>();
        if (prefix == null) {
            prefix = "";
        }
        while (paramNames != null && paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            if ("".equals(prefix) || paramName.startsWith(prefix)) {
                String unprefixed = paramName.substring(prefix.length());
                String[] values = request.getParameterValues(paramName);
                if (values == null || values.length == 0) {
                    // Do nothing, no values found at all.
                } else if (values.length > 1) {
                    params.put(unprefixed, values);
                } else {
                    params.put(unprefixed, values[0]);
                }
            }
        }
        return params;
    }

    public static WebSetting getWebSetting() {
        return getWebSetting(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest());
    }

    public static WebSetting getWebSetting(HttpServletRequest request) {
        return (WebSetting)request.getServletContext().getAttribute(PlatformConstant.WEB_SETTING);
    }

}