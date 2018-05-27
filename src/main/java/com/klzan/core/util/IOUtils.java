package com.klzan.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public final class IOUtils extends org.apache.commons.io.IOUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(IOUtils.class);

    public static void close(InputStream stream) {
        try {
            if (stream != null) stream.close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public static void close(OutputStream stream) {
        try {
            if (stream != null) stream.close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public static void close(Reader reader) {
        try {
            if (reader != null) reader.close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public static void close(Writer writer) {
        try {
            if (writer != null) writer.close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public static String getTempFolderPath() {
        return System.getProperty("java.io.tmpdir");
    }

    private IOUtils() {
    }
}
