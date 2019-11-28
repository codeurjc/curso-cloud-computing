package es.urjc.code.s3_ejem2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class S3Service {

    public static AmazonS3 s3;

    public S3Service() {
        s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
    }

    public List<Bucket> getAllBuckets() {
        return s3.listBuckets();
    }

    public List<S3ObjectSummary> getBucket(String bucketName) {
        return s3.listObjects(bucketName).getObjectSummaries();
    }

    public void createBucket(String bucketName) {
        s3.createBucket(bucketName);
    }

    public void uploadFile(String bucketName, MultipartFile file) {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        s3.putObject(bucketName, file.getOriginalFilename(), convFile);
    }

    public void deleteBucket(String bucketName){
        s3.deleteBucket(bucketName);
    }

}