package es.urjc.code.s3_ejem2;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3ObjectSummary;

@RestController
@RequestMapping("/buckets")
public class BucketsController {

	@Autowired
	S3Service s3service;

	@GetMapping("/")
	public List<Bucket> getBuckets() {
		return s3service.getAllBuckets();
	}

	@GetMapping("/{bucketName}")
	public List<S3ObjectSummary> getBucket(@PathVariable String bucketName) {
		return s3service.getBucket(bucketName);
	}

	@PostMapping("/{bucketName}")
	public ResponseEntity<String> createBucket(@PathVariable String bucketName) {
		s3service.createBucket(bucketName);
		return new ResponseEntity<>("Bucket created!", HttpStatus.CREATED);
	}

	@PostMapping("/{bucketName}/uploadFile")
    public ResponseEntity<String> uploadFile(@PathVariable String bucketName, @RequestParam("file") MultipartFile file) {
        s3service.uploadFile(bucketName, file);
		return new ResponseEntity<>("File "+file.getOriginalFilename()+" created in bucket "+bucketName, HttpStatus.CREATED);
    }

	@DeleteMapping("/{bucketName}")
	public ResponseEntity<String> deleteBucket(@PathVariable String bucketName) {
		return new ResponseEntity<>("Bucket created!", HttpStatus.NO_CONTENT);
	}

}
