package com.example.ftpclient;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockftpserver.fake.FakeFtpServer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FtpClientApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void givenRemoteFile_whenDownloading_thenItIsOnTheLocalFilesystem() throws IOException {
        FakeFtpServer fakeFtpServer = new FakeFtpServer();
        String ftpUrl = String.format(
                "ftp://user:password@localhost:%d/foobar.txt", fakeFtpServer.getServerControlPort()
        );

        URLConnection urlConnection = new URL(ftpUrl).openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        Files.copy(inputStream, new File("downloaded_buz.txt").toPath());
        inputStream.close();

        // ファイル系に関してはAssertJが優れている
        assertThat(new File("downloaded_buz.txt")).exists();

//        new File("downloaded_buz").delete(); // cleanup
    }
}
