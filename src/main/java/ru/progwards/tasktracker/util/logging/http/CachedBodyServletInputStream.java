package ru.progwards.tasktracker.util.logging.http;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.ByteArrayInputStream;

/**
 * @author Oleg Kiselev
 */
public class CachedBodyServletInputStream extends ServletInputStream {

    private final ByteArrayInputStream bais;

    public CachedBodyServletInputStream(ByteArrayInputStream bais) {
        this.bais = bais;
    }

    @Override
    public int available() {
        return bais.available();
    }

    @Override
    public int read() {
        return bais.read();
    }

    @Override
    public int read(byte[] buf, int off, int len) {
        return bais.read(buf, off, len);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener readListener) {
    }
}