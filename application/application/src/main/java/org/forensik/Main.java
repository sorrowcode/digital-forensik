package org.forensik;

import javax.net.ssl.*;
import java.net.Socket;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.time.Duration;

public class Main {
    public static void main(String[] args) {
        try {
            TrustManager[] trustAllCerts = new TrustManager[] {new X509ExtendedTrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s, Socket socket) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s, Socket socket) throws CertificateException {

                }

                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s, SSLEngine sslEngine) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s, SSLEngine sslEngine) throws CertificateException {

                }

                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }};

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            HostnameVerifier allHostsValid = (s, sslSession) -> true;

            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofMillis(10 * 1000))
                    .sslContext(sc)
                    .build();


            HttpRequest request = HttpRequest.newBuilder()
                    .header("Authorization", "Splunk eca6d36b-f760-42b5-8f4b-66ded536ad59")
                    .uri(new URI("https://0.0.0.0:8088/services/collector"))
                    .POST(HttpRequest.BodyPublishers.ofString(
                            "{\"event\": {\"log from application\":\"myapplication\"}, \"sourcetype\": \"_json\"}"
                    ))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}