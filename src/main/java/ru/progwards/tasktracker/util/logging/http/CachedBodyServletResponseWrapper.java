package ru.progwards.tasktracker.util.logging.http;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

/**
 * Кэширование ответа ServletResponse
 * Первоначально в классе HttpRequestResponseLoggingFilter происходит кэширование тела ответа,
 * далее оригинальное тело ответа используется при формировании лога в методе doFilter
 * класса HttpRequestResponseLoggingFilter,
 * далее ранее кэшированное тело ответа передается по цепочке chain.doFilter
 *
 * @author Oleg Kiselev
 */
public class CachedBodyServletResponseWrapper implements HttpServletResponse {

    private final HttpServletResponse originalResponse;
    private TeeServletOutputStream tee;
    private ByteArrayOutputStream bos;

    public CachedBodyServletResponseWrapper(HttpServletResponse response) {
        originalResponse = response;
    }

    public String getContent() {
        if (bos == null) {
            return String.format("called %s too early", CachedBodyServletResponseWrapper.class.getCanonicalName());
        }
        byte[] bytes = bos.toByteArray();
        return new String(Arrays.copyOf(bytes, 5000));
    }

    public PrintWriter getWriter() throws IOException {
        return originalResponse.getWriter();
    }

    public ServletOutputStream getOutputStream() throws IOException {
        if (tee == null) {
            bos = new ByteArrayOutputStream();
            tee = new TeeServletOutputStream(originalResponse.getOutputStream(), bos);
        }
        return tee;

    }

    @Override
    public String getCharacterEncoding() {
        return originalResponse.getCharacterEncoding();
    }

    @Override
    public String getContentType() {
        return originalResponse.getContentType();
    }

    @Override
    public void setCharacterEncoding(String charset) {
        originalResponse.setCharacterEncoding(charset);
    }

    @Override
    public void setContentLength(int len) {
        originalResponse.setContentLength(len);
    }

    @Override
    public void setContentLengthLong(long l) {
        originalResponse.setContentLengthLong(l);
    }

    @Override
    public void setContentType(String type) {
        originalResponse.setContentType(type);
    }

    @Override
    public void setBufferSize(int size) {
        originalResponse.setBufferSize(size);
    }

    @Override
    public int getBufferSize() {
        return originalResponse.getBufferSize();
    }

    @Override
    public void flushBuffer() throws IOException {
        tee.flush();
    }

    @Override
    public void resetBuffer() {
        originalResponse.resetBuffer();
    }

    @Override
    public boolean isCommitted() {
        return originalResponse.isCommitted();
    }

    @Override
    public void reset() {
        originalResponse.reset();
    }

    @Override
    public void setLocale(Locale loc) {
        originalResponse.setLocale(loc);
    }

    @Override
    public Locale getLocale() {
        return originalResponse.getLocale();
    }

    @Override
    public void addCookie(Cookie cookie) {
        originalResponse.addCookie(cookie);
    }

    @Override
    public boolean containsHeader(String name) {
        return originalResponse.containsHeader(name);
    }

    @Override
    public String encodeURL(String url) {
        return originalResponse.encodeURL(url);
    }

    @Override
    public String encodeRedirectURL(String url) {
        return originalResponse.encodeRedirectURL(url);
    }

    @SuppressWarnings("deprecation")
    @Override
    public String encodeUrl(String url) {
        return originalResponse.encodeUrl(url);
    }

    @SuppressWarnings("deprecation")
    @Override
    public String encodeRedirectUrl(String url) {
        return originalResponse.encodeRedirectUrl(url);
    }

    @Override
    public void sendError(int sc, String msg) throws IOException {
        originalResponse.sendError(sc, msg);
    }

    @Override
    public void sendError(int sc) throws IOException {
        originalResponse.sendError(sc);
    }

    @Override
    public void sendRedirect(String location) throws IOException {
        originalResponse.sendRedirect(location);
    }

    @Override
    public void setDateHeader(String name, long date) {
        originalResponse.setDateHeader(name, date);
    }

    @Override
    public void addDateHeader(String name, long date) {
        originalResponse.addDateHeader(name, date);
    }

    @Override
    public void setHeader(String name, String value) {
        originalResponse.setHeader(name, value);
    }

    @Override
    public void addHeader(String name, String value) {
        originalResponse.addHeader(name, value);
    }

    @Override
    public void setIntHeader(String name, int value) {
        originalResponse.setIntHeader(name, value);
    }

    @Override
    public void addIntHeader(String name, int value) {
        originalResponse.addIntHeader(name, value);
    }

    @Override
    public void setStatus(int sc) {
        originalResponse.setStatus(sc);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void setStatus(int sc, String sm) {
        originalResponse.setStatus(sc, sm);
    }

    @Override
    public String getHeader(String arg0) {
        return originalResponse.getHeader(arg0);
    }

    @Override
    public Collection<String> getHeaderNames() {
        return originalResponse.getHeaderNames();
    }

    @Override
    public Collection<String> getHeaders(String arg0) {
        return originalResponse.getHeaders(arg0);
    }

    @Override
    public int getStatus() {
        return originalResponse.getStatus();
    }

}