package ru.progwards.tasktracker.util.logging.http;

import org.apache.commons.io.output.TeeOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Oleg Kiselev
 */
public class TeeServletOutputStream extends ServletOutputStream {

    private final TeeOutputStream targetStream;

    public TeeServletOutputStream(OutputStream one, OutputStream two) {
        targetStream = new TeeOutputStream(one, two);
    }

    @Override
    public void write(int arg0) throws IOException {
        targetStream.write(arg0);
    }

    public void flush() throws IOException {
        super.flush();
        targetStream.flush();
    }

    public void close() throws IOException {
        super.close();
        targetStream.close();
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {

    }
}