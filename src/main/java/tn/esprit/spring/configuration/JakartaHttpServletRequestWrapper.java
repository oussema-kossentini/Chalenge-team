package tn.esprit.spring.configuration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;



import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class JakartaHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private HttpServletRequest request;
    private Map<String, String> headerMap;

    public JakartaHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        this.request = request;
        this.headerMap = new HashMap<>();
    }

    @Override
    public String getHeader(String name) {
        String headerValue = headerMap.get(name);
        if (headerValue != null) {
            return headerValue;
        }
        return request.getHeader(name);
    }

 /*   @Override
    public Enumeration<String> getHeaderNames() {
        Enumeration<String> originalHeaderNames = request.getHeaderNames();
        return new CombinedEnumeration<>(originalHeaderNames, Collections.enumeration(headerMap.keySet()));
    }*/

    @Override
    public String getParameter(String name) {
        return request.getParameter(name);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return request.getParameterMap();
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return request.getParameterNames();
    }

    @Override
    public String[] getParameterValues(String name) {
        return request.getParameterValues(name);
    }

    public void addHeader(String name, String value) {
        headerMap.put(name, value);
    }
}
