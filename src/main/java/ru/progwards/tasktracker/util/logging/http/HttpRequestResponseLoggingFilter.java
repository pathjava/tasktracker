package ru.progwards.tasktracker.util.logging.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Логика логирования HTTP запросов (Request) и ответов (Response)
 *
 * @author Oleg Kiselev
 */
@Component
@Order(1)
public class HttpRequestResponseLoggingFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpRequestResponseLoggingFilter.class);

    /**
     * Метод doFilter фильтра вызывается контейнером каждый раз,
     * когда пара запрос / ответ проходит через цепочку из клиентского запроса ресурса в конце цепочки.
     * FilterChain, переданный этому методу, позволяет фильтру передавать запрос и ответ следующему объекту в цепочке.
     * <p>
     * CachedBodyServletRequestWrapper cachedRequest и CachedBodyServletResponseWrapper cachedResponse
     * считывают httpServletRequest и httpServletResponse соответственно.
     * Это необходимо, так как тело HTTP-запроса можно прочитать только один раз.
     * Если прочитать тело запроса в фильтре, целевой сервлет не сможет его перечитать
     * и это также вызовет IllegalStateException.
     *
     * @param request  запрос на обработку
     * @param response ответ, связанный с запросом
     * @param chain    предоставляет доступ к следующему фильтру в цепочке,
     *                 чтобы этот фильтр передавал запрос и ответ для дальнейшей обработки.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (LOGGER.isDebugEnabled()) {
            try {
                HttpServletRequest httpServletRequest = (HttpServletRequest) request;
                HttpServletResponse httpServletResponse = (HttpServletResponse) response;

                Map<String, String> requestMap = getTypesafeRequestMap(httpServletRequest);
                CachedBodyServletRequestWrapper cachedRequest = new CachedBodyServletRequestWrapper(httpServletRequest);
                CachedBodyServletResponseWrapper cachedResponse = new CachedBodyServletResponseWrapper(httpServletResponse);

                /* Request message */
                String messageRequest = "REST Request - " +
                        "[HTTP Method:" +
                        httpServletRequest.getMethod() +
                        "] [Path Info:" +
                        httpServletRequest.getServletPath() +
                        "] [Request Parameters:" +
                        requestMap +
                        "] [Request Body:" +
                        cachedRequest.getRequestBody() +
                        "] [Remote Address:" +
                        httpServletRequest.getRemoteAddr() +
                        "] [Remote User:" +
                        httpServletRequest.getUserPrincipal().getName() +
                        "]";
                LOGGER.debug(messageRequest);

                chain.doFilter(cachedRequest, cachedResponse);

                /* Response message */
                String messageResponse = "REST Response - " +
                        " [Response:" + cachedResponse.getContent() + "]" +
                        " [HTTP Status:" + cachedResponse.getStatus() + "]";
                LOGGER.debug(messageResponse);

            } catch (Throwable a) {
                LOGGER.error(a.getMessage());
            }
        } else
            chain.doFilter(request, response);
    }

    /**
     * Метод, обеспечивающий безопасную передачу данных из запроса в логи.
     * Например, строка запроса http://localhost:8080/rest/user?password=123 содержащая имя параметра password
     * будет обработана и значение заменено на ********
     * Таким образом можно защитить от попадания в логи различных персональных данных
     *
     * @param request запрос на обработку
     * @return Map, где key - это имя параметра, а value - это значение параметра, замененное на ***
     */
    private Map<String, String> getTypesafeRequestMap(HttpServletRequest request) {
        Map<String, String> typesafeRequestMap = new HashMap<>();
        Enumeration<?> requestParamNames = request.getParameterNames();

        while (requestParamNames.hasMoreElements()) {
            String requestParamName = (String) requestParamNames.nextElement();
            String requestParamValue;
            if (requestParamName.equalsIgnoreCase("password")) {
                requestParamValue = "********";
            } else {
                requestParamValue = request.getParameter(requestParamName);
            }
            typesafeRequestMap.put(requestParamName, requestParamValue);
        }
        return typesafeRequestMap;
    }
}