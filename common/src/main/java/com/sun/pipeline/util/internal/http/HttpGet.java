package com.sun.pipeline.util.internal.http;

import com.sun.pipeline.util.internal.StringUtil;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.DefaultHttpParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zksun on 07/06/2017.
 */
public final class HttpGet extends AbstractHttpGet {

    private static Map<String, HttpGet> cache = new HashMap<String, HttpGet>();

    private List<NameValuePair> parameters;

    private String url;

    private int port = 80;

    private int retry = 3;

    private GetMethod getMethod;

    private HttpClient httpClient;

    private volatile boolean active = false;

    HttpClient getHttpClient() {
        return httpClient;
    }

    String getUrl() {
        return url;
    }

    int getPort() {
        return port;
    }

    public HttpGet addParameters(String parameterName, String value) {
        if (StringUtils.isBlank(parameterName) || StringUtils.isBlank(value)) {
            throw new NullPointerException();
        }

        synchronized (this) {
            if (null == parameters) {
                this.parameters = new ArrayList<>();
            }
            parameters.add(new NameValuePair(parameterName, value));
        }
        return this;
    }

    List<NameValuePair> getParameters() {
        return this.parameters;
    }

    public HttpGet addPath(String path) {
        if (StringUtils.isBlank(path)) {
            throw new NullPointerException("no path");
        }
        this.getMethod.setPath(this.getMethod.getPath() + path);
        addCache(this);
        return this;
    }

    void setQueryString() {
        if (parameterSize() > 0) {
            this.getMethod.setQueryString(getParameters().toArray(new NameValuePair[parameterSize()]));
        }
    }

    boolean isActive() {
        return active;
    }

    byte[] queryBytes() {
        setQueryString();
        try {
            int code = this.httpClient.executeMethod(this.getMethod);
            if (code != HttpStatus.SC_OK) {
                throw new RuntimeException("http error");
            }
            return this.getMethod.getResponseBody();
        } catch (Exception e) {
            this.getMethod.releaseConnection();
            this.active = false;
            throw new RuntimeException("http query error", e);
        } finally {
            parameters.clear();
        }
    }

    /**
     * @param url   prefix url
     * @param port  address port
     * @param path  query url
     * @param retry retry time
     * @return
     */
    public static HttpGet getHttpGetInstance(String url, int port, String path, int retry) {
        if (StringUtils.isBlank(url)) {
            throw new NullPointerException("no url");
        }
        if (StringUtils.isBlank(path)) {
            throw new NullPointerException("no path");
        }

        return getInstance(url, port, path, retry);
    }

    private static HttpGet getInstance(String url, int port, String path, int retry) {
        String methodPath = createMethodPath(url, port, path);
        if (cache.containsKey(methodPath)) {
            HttpGet httpGet = cache.get(methodPath);
            if (retry != httpGet.retry) {
                httpGet.getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(retry, false));//todo maybe another get
            }
            return httpGet;
        }
        HttpGet httpGet = new HttpGet();
        httpGet.url = url;
        httpGet.port = port;
        httpGet.retry = retry;
        httpGet.httpClient = new HttpClient();
        httpGet.getMethod = new GetMethod(httpGet.createMethodPath(url, port, path));
        httpGet.getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(retry, false));
        httpGet.active = true;
        httpGet.addCache(httpGet);
        return httpGet;
    }

    private void addCache(HttpGet httpGet) {
        synchronized (cache) {
            try {
                String uri = httpGet.getMethod.getURI().toString();
                HttpGet get = cache.get(uri.trim().toUpperCase());
                if (null == get) {
                    cache.put(uri.trim().toUpperCase(), httpGet);
                }
            } catch (URIException e) {
                //ignore
            }
        }
    }

    private static String createMethodPath(String url, int port, String path) {
        if (!url.startsWith("http://")) {
            url = "http://" + url + ":" + port;
        }

        if (StringUtils.isNoneBlank(path)) {
            url = url + path;
        }
        return url;
    }


    private HttpGet() {
    }

    static {
        DefaultHttpParams.getDefaultParams().setParameter("http.protocol.cookie-policy", CookiePolicy.BROWSER_COMPATIBILITY);
    }

}
