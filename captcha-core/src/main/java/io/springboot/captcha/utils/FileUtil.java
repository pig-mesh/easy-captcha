package io.springboot.captcha.utils;

import java.io.*;
import java.util.Objects;
/**
 * @author lengleng
 * @date 2023/8/23
 */
public class FileUtil {
    public static final int DEFAULT_BUFFER_SIZE = 8192;
    public static final int EOF = -1;

    /**
     * 文件流复制
     * @param inputStream
     * @param file
     * @throws IOException
     */
    public static void copyToFile(final InputStream inputStream, final File file) throws IOException {
        try (OutputStream out = openOutputStream(file)) {
            copy(inputStream, out);
        }
    }

    public static FileOutputStream openOutputStream(final File file) throws IOException {
        return openOutputStream(file, false);
    }

    public static FileOutputStream openOutputStream(final File file, final boolean append) throws IOException {
        Objects.requireNonNull(file, "file");
        if (file.exists()) {
            requireFile(file, "file");
            requireCanWrite(file, "file");
        } else {
            createParentDirectories(file);
        }
        return new FileOutputStream(file, append);
    }

    private static File requireFile(final File file, final String name) {
        Objects.requireNonNull(file, name);
        if (!file.isFile()) {
            throw new IllegalArgumentException("Parameter '" + name + "' is not a file: " + file);
        }
        return file;
    }

    private static void requireCanWrite(final File file, final String name) {
        Objects.requireNonNull(file, "file");
        if (!file.canWrite()) {
            throw new IllegalArgumentException("File parameter '" + name + " is not writable: '" + file + "'");
        }
    }

    public static File createParentDirectories(final File file) throws IOException {
        return mkdirs(getParentFile(file));
    }

    private static File mkdirs(final File directory) throws IOException {
        if ((directory != null) && (!directory.mkdirs() && !directory.isDirectory())) {
            throw new IOException("Cannot create directory '" + directory + "'.");
        }
        return directory;
    }

    private static File getParentFile(final File file) {
        return file == null ? null : file.getParentFile();
    }

    public static int copy(final InputStream inputStream, final OutputStream outputStream) throws IOException {
        final long count = copyLarge(inputStream, outputStream);
        if (count > Integer.MAX_VALUE) {
            return EOF;
        }
        return (int) count;
    }

    public static long copyLarge(final InputStream inputStream, final OutputStream outputStream)
            throws IOException {
        return copy(inputStream, outputStream, DEFAULT_BUFFER_SIZE);
    }

    public static long copy(final InputStream inputStream, final OutputStream outputStream, final int bufferSize)
            throws IOException {
        return copyLarge(inputStream, outputStream, byteArray(bufferSize));
    }

    public static long copyLarge(final InputStream inputStream, final OutputStream outputStream, final byte[] buffer)
            throws IOException {
        Objects.requireNonNull(inputStream, "inputStream");
        Objects.requireNonNull(outputStream, "outputStream");
        long count = 0;
        int n;
        while (EOF != (n = inputStream.read(buffer))) {
            outputStream.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    public static byte[] byteArray(final int size) {
        return new byte[size];
    }
}
