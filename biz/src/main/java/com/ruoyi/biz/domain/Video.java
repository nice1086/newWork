package com.ruoyi.biz.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import nonapi.io.github.classgraph.json.Id;

import java.util.Date;

@Data
public class Video {
    @Id
    @TableId(type = IdType.AUTO)
    private Long id;

    private String titile;

    private String videoUrl;

    private String coverUrl;

    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private Date creatime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitile() {
        return titile;
    }

    public void setTitile(String titile) {
        this.titile = titile;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatime() {
        return creatime;
    }

    public void setCreatime(Date creatime) {
        this.creatime = creatime;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", titile='" + titile + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", coverUrl='" + coverUrl + '\'' +
                ", description='" + description + '\'' +
                ", creatime=" + creatime +
                '}';
    }
}
