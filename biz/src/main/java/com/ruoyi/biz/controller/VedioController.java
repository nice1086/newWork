package com.ruoyi.biz.controller;

import com.ruoyi.biz.domain.Vedio;
import com.ruoyi.biz.service.IVedioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/biz/videos")
public class VedioController {
    @Autowired
    private IVedioService iVedioService;

//    public VedioController(IVedioService iVedioService){
//        this.iVedioService = iVedioService;
//    }
    @GetMapping
    public List<Vedio> getAllVideos(){
        return iVedioService.findAll();
    }
}
