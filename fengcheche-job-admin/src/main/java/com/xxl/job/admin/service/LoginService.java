package com.xxl.job.admin.service;

import com.xxl.job.admin.core.conf.FengCheCheAuthProperties;
import com.xxl.job.admin.core.model.XxlJobUser;
import com.xxl.job.admin.core.util.CookieUtil;
import com.xxl.job.admin.core.util.I18nUtil;
import com.xxl.job.admin.core.util.JacksonUtil;
import com.xxl.job.admin.dao.XxlJobUserDao;
import com.xxl.job.core.biz.model.ReturnT;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.util.Map;

/**
 * @author xuxueli 2019-05-04 22:13:264
 */
@Configuration
public class LoginService {

    public static final String LOGIN_IDENTITY_KEY = "XXL_JOB_LOGIN_IDENTITY";

    @Resource
    private XxlJobUserDao xxlJobUserDao;

    @Resource
    private FengCheCheAuthProperties fengCheCheAuthProperties;

    private final RestTemplate restTemplate = new RestTemplate();


    private String makeToken(XxlJobUser xxlJobUser){
        String tokenJson = JacksonUtil.writeValueAsString(xxlJobUser);
        String tokenHex = new BigInteger(tokenJson.getBytes()).toString(16);
        return tokenHex;
    }
    private XxlJobUser parseToken(String tokenHex){
        XxlJobUser xxlJobUser = null;
        if (tokenHex != null) {
            String tokenJson = new String(new BigInteger(tokenHex, 16).toByteArray());      // username_password(md5)
            xxlJobUser = JacksonUtil.readValue(tokenJson, XxlJobUser.class);
        }
        return xxlJobUser;
    }


    public ReturnT<String> login(HttpServletRequest request, HttpServletResponse response, String username, String password, boolean ifRemember){

        // param
        if (username==null || username.trim().length()==0 || password==null || password.trim().length()==0){
            return new ReturnT<String>(500, I18nUtil.getString("login_param_empty"));
        }

        // valid passowrd
        XxlJobUser xxlJobUser = xxlJobUserDao.loadByUserName(username);
        if (xxlJobUser == null) {
            return new ReturnT<String>(500, I18nUtil.getString("login_param_unvalid"));
        }
        String passwordMd5 = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!passwordMd5.equals(xxlJobUser.getPassword())) {
            return new ReturnT<String>(500, I18nUtil.getString("login_param_unvalid"));
        }

        String loginToken = makeToken(xxlJobUser);

        // do login
        CookieUtil.set(response, LOGIN_IDENTITY_KEY, loginToken, ifRemember);
        return ReturnT.SUCCESS;
    }

    /**
     * logout
     *
     * @param request
     * @param response
     */
    public ReturnT<String> logout(HttpServletRequest request, HttpServletResponse response){
        CookieUtil.remove(request, response, LOGIN_IDENTITY_KEY);
        return ReturnT.SUCCESS;
    }

    /**
     * logout
     *
     * @param request
     * @return
     */
    public XxlJobUser ifLogin(HttpServletRequest request, HttpServletResponse response){
        String cookieToken = CookieUtil.getValue(request, LOGIN_IDENTITY_KEY);
        if (cookieToken != null) {
            XxlJobUser cookieUser = null;
            try {
                cookieUser = parseToken(cookieToken);
            } catch (Exception e) {
                logout(request, response);
            }
            if (cookieUser != null) {
                XxlJobUser dbUser = xxlJobUserDao.loadByUserName(cookieUser.getUsername());
                if (dbUser != null) {
                    if (cookieUser.getPassword().equals(dbUser.getPassword())) {
                        return dbUser;
                    }
                }
            }
        }

        return trySystemLogin(request, response);
    }

    /**
     * 接入本系统 Sa-Token 登录态。
     * 校验通过后写入 XXL-JOB Admin 原生 Cookie，后续页面访问仍走 XXL-JOB 原登录逻辑。
     */
    private XxlJobUser trySystemLogin(HttpServletRequest request, HttpServletResponse response) {
        if (!fengCheCheAuthProperties.isEnabled()) {
            return null;
        }

        String systemToken = resolveSystemToken(request);
        if (!StringUtils.hasText(systemToken)) {
            return null;
        }

        if (!checkSystemRole(systemToken)) {
            return null;
        }

        XxlJobUser xxlAdminUser = xxlJobUserDao.loadByUserName(fengCheCheAuthProperties.getXxlAdminUsername());
        if (xxlAdminUser == null) {
            return null;
        }

        CookieUtil.set(response, LOGIN_IDENTITY_KEY, makeToken(xxlAdminUser), fengCheCheAuthProperties.isRememberXxlCookie());
        return xxlAdminUser;
    }

    private String resolveSystemToken(HttpServletRequest request) {
        String tokenName = fengCheCheAuthProperties.getTokenName();
        String token = request.getHeader(tokenName);
        if (!StringUtils.hasText(token)) {
            token = request.getParameter(tokenName);
        }
        if (!StringUtils.hasText(token)) {
            token = request.getParameter("token");
        }
        if (!StringUtils.hasText(token)) {
            token = request.getParameter("satoken");
        }
        if (!StringUtils.hasText(token)) {
            return null;
        }

        String tokenPrefix = fengCheCheAuthProperties.getTokenPrefix();
        if (StringUtils.hasText(tokenPrefix) && !token.startsWith(tokenPrefix + " ")) {
            return tokenPrefix + " " + token;
        }
        return token;
    }

    private boolean checkSystemRole(String systemToken) {
        String requiredRole = fengCheCheAuthProperties.getRequiredRole();
        if (!StringUtils.hasText(requiredRole)) {
            return false;
        }

        String url = UriComponentsBuilder
                .fromHttpUrl(fengCheCheAuthProperties.getSystemAuthBaseUrl() + "/check-role")
                .queryParam("role", requiredRole)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set(fengCheCheAuthProperties.getTokenName(), systemToken);

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<Void>(headers), String.class);
            Map responseBody = JacksonUtil.readValue(responseEntity.getBody(), Map.class);
            if (responseBody == null || !Boolean.TRUE.equals(responseBody.get("success"))) {
                return false;
            }
            return Boolean.TRUE.equals(responseBody.get("data"));
        } catch (RestClientException ex) {
            return false;
        }
    }

}
