package com.zzkj.dcsystem.config;

import com.zzkj.dcsystem.utils.wxpay.IWXPayDomain;
import com.zzkj.dcsystem.utils.wxpay.WXPayConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author litianfu
 * @version 1.0
 * @email 1035869369@qq.com
 * @date 2019-07-30 14:05
 */
@Component
public  class WxConfig extends WXPayConfig {
    private byte[] certData;
    @Value("${wx.appId}")
    private String appId;
    @Value("${wx.key}")
    private String key;
    @Value("${wx.mchId}")
    private String mchId;
//    @Value("${wx.certPath}")
//    private String certPathLs;
    
    

    public WxConfig() throws Exception {
//        //证书的路径
//        String certPath = certPathLs;
//        File file = new File(certPath);
//        InputStream certStream = new FileInputStream(file);
//        this.certData = new byte[(int) file.length()];
//        certStream.read(this.certData);
//        certStream.close();
        getAppID();
    }
    /**
     * 获取 App ID
     *
     * @return App ID
     */
    @Override
    public String getAppID(){
        return appId;
    }


    /**
     * 获取 Mch ID
     *商户号
     * @return Mch ID
     */
    @Override
    public String getMchID() {
        return mchId;
    }


    /**
     * 获取 API 密钥
     *
     * @return API密钥
     */
    @Override
    public String getKey() {
        return key;
    }


    /**
     * 获取商户证书内容
     *
     * @return 商户证书内容
     */
    @Override
    public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    /**
     * 获取WXPayDomain, 用于多域名容灾自动切换
     *
     * @return
     */
    @Override
    public IWXPayDomain getWXPayDomain() {
        return null;
    }
    @Override
    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 10000;
    }

}
