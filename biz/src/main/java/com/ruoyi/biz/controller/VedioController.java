package com.ruoyi.biz.controller;

import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import com.ruoyi.biz.service.IVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/biz/videos")
public class VedioController {

    @Autowired
    private IVideoService IVideoService;

    @GetMapping("/videos")
    public ResponseEntity<List<String>> listVideoFiles() throws ExecutionException, InterruptedException {
        List<String> videoFiles = IVideoService.listVideoFiles().get();
        return new ResponseEntity<>(videoFiles, HttpStatus.OK);
    }

    @GetMapping("/videos/{category}")
    public ResponseEntity<String> getVideoUrl(@PathVariable("category") String category) throws ExecutionException, InterruptedException {
        String videoUrl = IVideoService.getVideoUrl(category).get();
        return new ResponseEntity<>(videoUrl, HttpStatus.OK);
    }


//    @Value("${qiniu.accessKey}")
//    private String accessKey;
//
//    @Value("${qiniu.secretKey}")
//    private String secretKey;
//
//    @Value("${qiniu.bucket}")
//    private String bucket;
//
//    @GetMapping
//    public List<Map<String, String>> listVideos() {
//        Configuration cfg = new Configuration();
//        Auth auth = Auth.create(accessKey, secretKey);
//        BucketManager bucketManager = new BucketManager(auth, cfg);
//
//        try {
//            BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(bucket, null, 1000, null);
//            List<Map<String, String>> videos = new ArrayList<>();
//            while (fileListIterator.hasNext()) {
//                FileInfo[] items = fileListIterator.next();
//
//                for (FileInfo fileInfo : items) {
//                    String key = fileInfo.key;
//                    String url = generatePrivateUrl(key);
//
//                    Map<String, String> video = new HashMap<>();
//                    video.put("key", key);
//                    video.put("url", url);
//                    videos.add(video);
//                }
//            }
//
//            return videos;
//        } catch (QiniuException e) {
//            e.printStackTrace();
//            // 返回合适的错误信息给客户端
//            throw new RuntimeException("Failed to list videos");
//        }
//    }
//
//    @GetMapping("/{key}")
//    public Map<String, String> getVideoUrl(@PathVariable String key) {
//        Configuration cfg = new Configuration();
//        Auth auth = Auth.create(accessKey, secretKey);
//        BucketManager bucketManager = new BucketManager(auth, cfg);
//
//        try {
//            String url = generatePrivateUrl(key);
//
//            Map<String, String> result = new HashMap<>();
//            result.put("key", key);
//            result.put("url", url);
//
//            return result;
//        } catch (QiniuException e) {
//            e.printStackTrace();
//            // 返回合适的错误信息给客户端
//            throw new RuntimeException("Failed to get video URL");
//        }
//    }
//
//    private String generatePrivateUrl(String key) throws QiniuException {
//        Auth auth = Auth.create(accessKey, secretKey);
//        long expireInSeconds = 3600;
//        String downloadUrl = auth.privateDownloadUrl("your_bucket_domain" + "/" + key, expireInSeconds);
//
//        try {
//            String encodedUrl = URLEncoder.encode(downloadUrl, "UTF-8");
//            return encodedUrl.replace("+", "%20");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//            // 返回合适的错误信息给客户端
//            throw new RuntimeException("Failed to generate private URL");
//        }
//    }
}
