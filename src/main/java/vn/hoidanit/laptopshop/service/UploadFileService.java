package vn.hoidanit.laptopshop.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;
import java.io.File;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

@Service
public class UploadFileService {
    private final ServletContext servletContext;

    public UploadFileService(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public String handleSaveUploadFile(MultipartFile file, String targetFolder) {
        String rootPath = this.servletContext.getRealPath("/resources/images");
        String finalFileName = "";
        try {
            // convert fileUpload to byte[]
            byte[] bytes = file.getBytes();
            // create object for targetFolder
            File dir = new File(rootPath + File.separator + targetFolder);

            // if targetFolder is not exists, will create it
            if (!dir.exists())
                dir.mkdirs();

            // create final file name to save in database
            finalFileName = System.currentTimeMillis() + "-" + file.getOriginalFilename();

            // create file to save in server
            File serverFile = new File(dir.getAbsolutePath() + File.separator + finalFileName);

            // create stream to save uploadFile to server
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
            stream.write(bytes);
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return finalFileName;
    }

}
