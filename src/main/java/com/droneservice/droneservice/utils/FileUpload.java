/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.droneservice.droneservice.utils;

import com.droneservice.droneservice.dto.response.FileUploadResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.net.InetAddress;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author HP
 */
@Service
public class FileUpload {
    @Value("${server.port}")
   private String serverPort;

    private FileUploadResponse fileUploadResponse;

    public FileUploadResponse convertBase64ToImage(String file, Long fileName) {
        fileUploadResponse = new FileUploadResponse();

        InetAddress serverIp = null;
        boolean result = false;
        String workingDirectory = System.getProperties().getProperty("user.dir");
        String imageDirectory = "/imageupload";
        checkDirectory();
        try {
           serverIp = InetAddress.getLocalHost();
            byte[] imageByte = Base64.decodeBase64(file);
            imageDirectory += "/file_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
            workingDirectory += imageDirectory;
            String directory = "http//"+serverIp.getHostAddress()+":"+serverPort+imageDirectory;
            new FileOutputStream(workingDirectory).write(imageByte);
            fileUploadResponse.setName(workingDirectory);
            fileUploadResponse.setPath(workingDirectory);
        } catch (Exception e) {
            fileUploadResponse = null;
        }
        return fileUploadResponse;
    }

    private boolean checkDirectory() {
        boolean result = false;
        String workingDirectory = System.getProperties().getProperty("user.dir") + "/imageupload";
        File path = new File(workingDirectory);
        if (!path.exists()) {
            path.mkdir();
            result = true;
        }
        return result;
    }

}
