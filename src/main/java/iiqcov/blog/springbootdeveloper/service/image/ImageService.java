package iiqcov.blog.springbootdeveloper.service.image;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


@Service
@RequiredArgsConstructor
public class ImageService {
    @Autowired
    private AmazonS3 s3Client;

    @Value("${amazonProperties.bucketName}")
    private String bucketName;

    @Value("${amazonProperties.cloudFrontDomainName}")
    private String cloudFrontDomainName;

    public String addImage(MultipartFile image) {
        String fileName = image.getOriginalFilename();
        String fileUrl="";
        try {
            File file = convertMultiPartToFile(image);
            uploadFileToS3Bucket(fileName, file);
            file.delete();
            fileUrl=cloudFrontDomainName+"/"+fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileUrl;
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private void uploadFileToS3Bucket(String fileName, File file) {
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, file));
    }
}
