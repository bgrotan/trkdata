package no.ngu.trkdata;

import org.apache.commons.io.FilenameUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * Utility class to download a file from remote URL and store as local file
 */
public class IOUtils {
    public static void download(String source,String target) throws ClientProtocolException, IOException {
        Path filePath = Paths.get(target);
        if (!Files.exists(filePath.getParent())) {
            try {
                Files.createDirectories(filePath.getParent());
            } catch (IOException e) {
                throw new IOException(e.getMessage());
            }
        }
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet request = new HttpGet(source);
        CloseableHttpResponse response = httpclient.execute(request);
        HttpEntity entity = response.getEntity();
        int responseCode = response.getStatusLine().getStatusCode();
        InputStream is = entity.getContent();
        FileOutputStream fos = new FileOutputStream(new File(target));
        int inByte;
        while ((inByte = is.read()) != -1) {
            fos.write(inByte);
        }
        is.close();
        fos.close();
        httpclient.close();
    }
}
