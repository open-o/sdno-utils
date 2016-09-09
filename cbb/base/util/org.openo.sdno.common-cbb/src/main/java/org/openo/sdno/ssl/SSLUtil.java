/*
 * Copyright 2016 Huawei Technologies Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.openo.sdno.ssl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Public methods to help SSL operation. <br>
 * 
 * @author
 * @version SDNO 0.5 2016-4-13
 */
public class SSLUtil {

    private static final String ALGORITHM_SUNX509 = "SunX509";

    private static final String PROTOCOL_TLS = "TLS";

    private static final String JKS = "JKS";

    private static final Logger LOGGER = LoggerFactory.getLogger(SSLUtil.class);

    private SSLUtil() {
        // constructor.
    }

    /**
     * Use password to analyze keystore file. <br>
     * 
     * @param filePath file path to keystore file.
     * @param key needed to parsing the keystore file.
     * @return keystore object parsed from file.
     * @since SDNO 0.5
     */
    private static KeyStore getKeyStore(String filePath, String keyPass) {
        InputStream is = null;
        try {
            KeyStore keyStore = KeyStore.getInstance(JKS);
            is = new FileInputStream(filePath);
            char[] passw = EncryptionUtil.decode(keyPass.toCharArray());
            keyStore.load(is, passw);
            EncryptionUtil.clear(passw);
            return keyStore;
        } catch(FileNotFoundException e) {
            LOGGER.warn("The store file is not found", e);
        } catch(KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
            LOGGER.warn(e.getMessage(), e);
        } finally {
            try {
                if(is != null) {
                    is.close();
                }
            } catch(IOException e) {
                LOGGER.warn(e.getMessage(), e);
            }
        }
        return null;
    }

    /**
     * Initialize SSLContext with kerstore, TrustStore and protocol. <br>
     * 
     * @param keyStoreKeyPass encrypted keystore keyword string.
     * @param keyStoreFilePath file path to keystore file.
     * @param trustStoreKeyPass encrypted truststore keyword string.
     * @param trustStoreFilePath file path to truststore file.
     * @return SSLContext object created.
     * @since SDNO 0.5
     */
    public static SSLContext getSSLContext(String keyStoreKeyPass, String keyStoreFilePath, String trustStoreKeyPass,
            String trustStoreFilePath) {
        try {
            SSLContext sslContext = SSLContext.getInstance(PROTOCOL_TLS);

            KeyStore ksKeys = getKeyStore(keyStoreFilePath, keyStoreKeyPass);
            KeyStore tksKeys = getKeyStore(trustStoreFilePath, trustStoreKeyPass);
            if((ksKeys != null) && (tksKeys != null)) {
                KeyManagerFactory kmf = KeyManagerFactory.getInstance(ALGORITHM_SUNX509);
                char[] keyPass = EncryptionUtil.decode(keyStoreKeyPass.toCharArray());
                kmf.init(ksKeys, keyPass);
                EncryptionUtil.clear(keyPass);

                TrustManagerFactory tmf = TrustManagerFactory.getInstance(ALGORITHM_SUNX509);
                tmf.init(tksKeys);

                sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
                return sslContext;
            }
        } catch(UnrecoverableKeyException | KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
            LOGGER.warn(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Get default SSLContext object. <br>
     * 
     * @return default SSLContext object.
     * @since SDNO 0.5
     */
    public static SSLContext getDefaultSSLContext() {
        TrustManager[] tm = {new DefaultX509TrustManager()};
        try {
            SSLContext sslContext = SSLContext.getInstance(PROTOCOL_TLS);
            sslContext.init(null, tm, new SecureRandom());
            return sslContext;
        } catch(NoSuchAlgorithmException | KeyManagementException e) {
            LOGGER.warn(e.getMessage(), e);
        }
        return null;
    }
}

/**
 * Default X509TrustManager.<br>
 * 
 * @author
 * @version SDNO 0.5 2016-4-13
 */
class DefaultX509TrustManager implements X509TrustManager {

    @Override
    public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
        // method need to override.
    }

    @Override
    public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
        // method need to override.
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }
}
