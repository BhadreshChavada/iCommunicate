package com.icommunicate.apiCall;


import android.content.Context;
import android.util.Log;

import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class ServiceGeneratorRetro {
    public static <S> S createService(Class<S> serviceClass, Context mContext, boolean isGraphQl) {
        Retrofit retrofit = null;

        try {
                Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl(ApiConstant.BASE_URL)
                        .client(getUnsafeOkHttpClients().build())
                        .addConverterFactory(GsonConverterFactory.create());

                retrofit = builder.build();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return retrofit.create(serviceClass);

    }


    public static OkHttpClient.Builder getUnsafeOkHttpClients() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });


            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

           /* if (BuildConfig.DEBUG) {
                builder.addInterceptor(new LoggingInterceptor.Builder()
                        .setLevel(Level.BASIC)
                        .log(Log.VERBOSE)
                        .build());
            }*/

            builder.addInterceptor(logging);

            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
