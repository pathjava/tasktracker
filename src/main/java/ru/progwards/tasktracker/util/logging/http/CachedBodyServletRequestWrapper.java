package ru.progwards.tasktracker.util.logging.http;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**
 * Кэширование запроса ServletRequest
 * Первоначально в классе HttpRequestResponseLoggingFilter происходит кэширование тела запроса,
 * далее оригинальное тело запроса используется при формировании лога в методе doFilter
 * класса HttpRequestResponseLoggingFilter,
 * далее ранее кэшированное тело запроса передается по цепочке chain.doFilter
 *
 * @author Oleg Kiselev
 */
public class CachedBodyServletRequestWrapper extends HttpServletRequestWrapper {

    private final byte[] buffer;

    public CachedBodyServletRequestWrapper(HttpServletRequest req) throws IOException {
        super(req);
        InputStream is = req.getInputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int read;
        while ((read = is.read(buf)) > 0) {
            baos.write(buf, 0, read);
        }
        buffer = baos.toByteArray();
    }

    @Override
    public ServletInputStream getInputStream() {
        ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
        return new CachedBodyServletInputStream(bais);
    }

    public String getRequestBody() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(getInputStream()));
        String line;
        StringBuilder inputBuffer = new StringBuilder();
        do {
            line = reader.readLine();
            if (null != line) {
                if (line.contains("password"))
                    line = getTypesafePassword(line);
                inputBuffer.append(line.trim());
            }
        } while (line != null);
        reader.close();
        return inputBuffer.toString().trim();
    }

    private String getTypesafePassword(String line) {
        String[] str = line.split(": ");
        return str[0] + ": \"********\",";
    }

}