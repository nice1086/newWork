package com.ruoyi.biz.service.impl;

import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import com.ruoyi.biz.service.IVideoService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class VideoServiceImpl implements IVideoService {

    private static final String ACCESS_KEY = "your access key";
    private static final String SECRET_KEY = "your secret key";
    private static final String BUCKET_NAME = "your bucket name";

    @Async
    public CompletableFuture<List<String>> listVideoFiles() {
        List<String> videoFiles = new ArrayList<>();
        Configuration cfg = new Configuration(Region.region0());
        BucketManager bucketManager = new BucketManager(Auth.create(ACCESS_KEY, SECRET_KEY), cfg);
        // 每次最多返回1000条记录
        int limit = 1000;
        String delimiter = "";
        String prefix = "";
        BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(BUCKET_NAME, prefix, limit, delimiter);
        while (fileListIterator.hasNext()) {
            FileInfo[] items = fileListIterator.next();
            for (FileInfo item : items) {
                if (item.key.endsWith(".mp4")) {
                    videoFiles.add(item.key);
                }
            }
        }
        return CompletableFuture.completedFuture(videoFiles);
    }

    @Async
    @Cacheable("videoUrlCache")
    public CompletableFuture<String> getVideoUrl(String fileId){
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        // 假设文件访问路径为http://your-domain.com/file.mp4
        String fileName = null;
        try {
            fileName = URLEncoder.encode(fileId, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String videoUrl = String.format("http://%s/%s", "your-domain.com", fileName);
        String urlWithToken = auth.privateDownloadUrl(videoUrl);
        return CompletableFuture.completedFuture(urlWithToken);
    }

}
