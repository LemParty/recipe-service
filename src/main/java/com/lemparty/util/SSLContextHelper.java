package com.lemparty.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class SSLContextHelper {

    @Value( "${mongo.privatekey.name}" )
    private String DEFAULT_SSL_CERTIFICATE;

    private final String SSL_CERTIFICATE = "sslCertificate";
    private final String KEY_STORE_TYPE = "JKS";
    private final String KEY_STORE_PROVIDER = "SUN";
    private final String KEY_STORE_FILE_PREFIX = "sys-connect-via-ssl-test-cacerts";
    private final String KEY_STORE_FILE_SUFFIX = ".jks";
    private final String DEFAULT_KEY_STORE_PASSWORD = "changeit";
    private final String SSL_TRUST_STORE = "javax.net.ssl.trustStore";
    private final String SSL_TRUST_STORE_PASSWORD = "javax.net.ssl.trustStorePassword";
    private final String SSL_TRUST_STORE_TYPE = "javax.net.ssl.trustStoreType";


    public void setSslProperties(String DEFAULT_SSL_CERTIFICATE) {

        try {
            String sslCertificate = System.getProperty(SSL_CERTIFICATE);
            if (StringUtils.isEmpty(sslCertificate)) {
                sslCertificate = DEFAULT_SSL_CERTIFICATE;
            }
            System.out.println(" ssl certificate path {}" + sslCertificate);
            System.setProperty(SSL_TRUST_STORE, createKeyStoreFile(sslCertificate));
            System.setProperty(SSL_TRUST_STORE_TYPE, KEY_STORE_TYPE);
            System.setProperty(SSL_TRUST_STORE_PASSWORD, DEFAULT_KEY_STORE_PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String createKeyStoreFile(String sslCertificate) throws Exception {
        return createKeyStoreFile(createCertificate(sslCertificate)).getPath();
    }

    private X509Certificate createCertificate(String sslCertificate) throws Exception {
        CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
        URL url = new File(sslCertificate).toURI().toURL();
        if (url == null) {
            throw new Exception();
        }
        try (InputStream certInputStream = url.openStream()) {
            return (X509Certificate) certFactory.generateCertificate(certInputStream);
        }
    }

    private File createKeyStoreFile(X509Certificate rootX509Certificate) throws Exception {
        File keyStoreFile = File.createTempFile(KEY_STORE_FILE_PREFIX, KEY_STORE_FILE_SUFFIX);
        try (FileOutputStream fos = new FileOutputStream(keyStoreFile.getPath())) {
            KeyStore ks = KeyStore.getInstance(KEY_STORE_TYPE, KEY_STORE_PROVIDER);
            ks.load(null);
            ks.setCertificateEntry("rootCaCertificate", rootX509Certificate);
            ks.store(fos, DEFAULT_KEY_STORE_PASSWORD.toCharArray());
        }
        return keyStoreFile;
    }
}
