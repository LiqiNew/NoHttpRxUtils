/*
 * Copyright 2015 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.liqi.nohttputils.nohttp;

import android.annotation.SuppressLint;

import com.yanzhenjie.nohttp.ssl.SSLUtils;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * Https协议证书工具类
 *
 * @author LiQi
 */
public class SSLContextUtil {

    static final HostnameVerifier HOSTNAME_VERIFIER = SSLUtils.defaultHostnameVerifier();

    /**
     * 拿到https证书, SSLContext (NoHttp已经修补了系统的SecureRandom的bug)。
     */
    @SuppressLint("TrulyRandom")
    static SSLContext getSSLContext(InputStream inputStream) {
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            //InputStream inputStream = Application.getInstance().getAssets().open("srca.cer");
            CertificateFactory cerFactory = CertificateFactory.getInstance("X.509");
            Certificate cer = cerFactory.generateCertificate(inputStream);

            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(null, null);
            keyStore.setCertificateEntry("trust", cer);

            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, null);

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);

            sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sslContext;
    }

    /**
     * 如果不需要https证书.(NoHttp已经修补了系统的SecureRandom的bug)。
     */
    static SSLSocketFactory getDefaultSLLContext() {
        return SSLUtils.defaultSSLSocketFactory();
    }

}
