package com.ruoyi.biz.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IVideoService {
    CompletableFuture<List<String>> listVideoFiles();

    CompletableFuture<String> getVideoUrl(String fileId);
}
