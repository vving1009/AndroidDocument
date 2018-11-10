import android.content.Context;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class OkhttpSslUtils {

    private static final String TAG = "SslUtils";

    public static SSLSocketFactory getSSLSocketFactoryNoVerify() {
        // 生成TrustManager
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        //未真正实现校验服务器端证书
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }
        };
        // 由TrustManager生成SSLSocketFactory
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, null);
            return sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HostnameVerifier getHostnameVerifierNoVerify() {
        return new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                // 未真正实现校验服务器证书域名是否相符
                return true;
            }
        };
    }

    /**
     * new TrustManager 与证书对比
     *
     * @param context
     * @param certName
     * @return
     */
    public static SSLSocketFactory getSSLSocketFactory(Context context, String certName) {
        try {
            BufferedInputStream is = new BufferedInputStream(context.getAssets().open(certName));
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate serverCert = (X509Certificate) cf.generateCertificate(is);
            // 生成TrustManager
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                            if (chain == null) {
                                throw new IllegalArgumentException("Check server X509Certificate is null");
                            }
                            if (chain.length == 0) {
                                throw new IllegalArgumentException("Check server X509Certificate is empty");
                            }
                            for (X509Certificate cert : chain) {
                                //检查服务器端证书签名是否有问题
                                cert.checkValidity();
                                try {
                                    //和app预埋证书对比
                                    cert.verify(serverCert.getPublicKey());
                                } catch (NoSuchAlgorithmException e) {
                                    e.printStackTrace();
                                } catch (InvalidKeyException e) {
                                    e.printStackTrace();
                                } catch (NoSuchProviderException e) {
                                    e.printStackTrace();
                                } catch (SignatureException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }
                    }
            };
            // 由TrustManager生成SSLSocketFactory
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, null);
            return sslContext.getSocketFactory();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 由证书生成一个TrustManager
     *
     * @param context
     * @param certName
     * @return
     */
    public static SSLSocketFactory getSSLSocketFactory2(Context context, String certName) {
        try {
            // 以X.509格式获取证书
            BufferedInputStream is = new BufferedInputStream(context.getAssets().open(certName));
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            Certificate cert = cf.generateCertificate(is);
            Log.d(TAG, "cert key = " + ((X509Certificate) cert).getPublicKey().toString());

            // 生成一个包含服务器端证书的KeyStore
            String keyStoreType = KeyStore.getDefaultType();
            Log.d(TAG, "keyStoreType = " + keyStoreType);
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("cert", cert);

            // 用包含服务器证书的keystore生成一个trustmanager
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            Log.d(TAG, "TrustManagerFactory.getDefaultAlgorithm = " + tmfAlgorithm);
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(tmfAlgorithm);
            trustManagerFactory.init(keyStore);

            // 由TrustManager生成SSLSocketFactory
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
            return sslContext.getSocketFactory();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static HostnameVerifier getHostnameVerifier(String hostName) {
        return new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                HostnameVerifier verifier = HttpsURLConnection.getDefaultHostnameVerifier();
                //return verifier.verify("*.washington.edu", session);
                return verifier.verify(hostName, session);
            }
        };
    }

    public static OkHttpClient getOkHttpClient(Context context, String certName, String hostName) {
        return new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                //设置出现错误进行重新连接
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(60 * 1000, TimeUnit.MILLISECONDS)
                /*.addInterceptor(chain ->
                        chain.proceed(chain.request()
                                .newBuilder()
                                .addHeader("Content-Type", "application/json;charset=UTF-8")
                                .addHeader("User-Agent", "android")
                                .build())
                )
                .addNetworkInterceptor(chain -> null)
                .cache(new Cache())*/
                .hostnameVerifier(getHostnameVerifier(hostName))
                .sslSocketFactory(getSSLSocketFactory2(context, certName))
                .build();
        //让Glide能用HTTPS
        // gradle 加入compile 'com.github.bumptech.glide:okhttp3-integration:1.4.0@aar'
        //Glide.get(context).register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(okhttpClient));
    }
}
