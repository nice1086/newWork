<template>
  <div class="video-container">
    <h1 class="title">欢迎来到我的短视频网站</h1>
    <form @submit.prevent="uploadVideo">
      <div class="form-group">
        <label for="videoTitle">标题:</label>
        <input type="text" id="videoTitle" v-model="title" required>
      </div>
      <div class="form-group">
        <label for="videoFile">选择视频文件:</label>
        <input type="file" id="videoFile" ref="fileInput" accept="video/*" @change="onFileInputChange">
      </div>
      <div class="form-group">
        <button type="submit" :disabled="!canUpload">上传</button>
      </div>
    </form>
    <hr>
    <h2>视频:</h2>
    <div class="video-grid">
      <div class="video-card" v-for="video in videos" :key="video.id">
        <video class="video-player" :src="video.url" controls></video>
        <div class="video-info">
          <h3 class="video-title">{{ video.title }}</h3>
          <p class="video-description">{{ video.description }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      title: '',
      file: null,
      videos: []
    };
  },
  created() {
    this.getAllVideos();
  },
  computed: {
    canUpload() {
      return this.title && this.file;
    }
  },
  methods: {
    getAllVideos() {
      axios.get('/biz/videos/videos')
        .then(response => {
          const videoFiles = response.data;
          videoFiles.forEach(file => {
            this.getVideoData(file);
          });
        })
        .catch(error => {
          console.error(error);
        });
    },
    getVideoData(fileId) {
      axios.get(`/biz/videos/videos/${fileId}`)
        .then(response => {
          const videoData = response.data;
          this.videos.push({ id: fileId, title: videoData.title, description: videoData.description, url: videoData.url });
        })
        .catch(error => {
          console.error(error);
        });
    },
    onFileInputChange() {
      this.file = this.$refs.fileInput.files[0];
    },
    uploadVideo() {
      const formData = new FormData();
      formData.append('title', this.title);
      formData.append('file', this.file);

      axios.post('/biz/videos/videos', formData)
        .then(response => {
          const fileId = response.data.id;
          this.getVideoData(fileId);
          this.title = '';
          this.file = null;
        })
        .catch(error => {
          console.error(error);
        });
    }
  }
};
</script>

<style scoped>
.video-container {
  max-width: 960px;
  margin: 0 auto;
  padding: 20px;
}

.title {
  text-align: center;
  font-size: 24px;
  margin-bottom: 20px;
}

.video-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
}

.video-card {
  background-color: #f2f2f2;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.video-player {
  width: 100%;
  height: 0;
  padding-top: 56.25%;
}

.video-info {
  padding: 10px;
}

.video-title {
  font-size: 18px;
  margin: 0;
}

.video-description {
  margin: 8px 0;
  color: #666;
}

.form-group {
  margin-bottom: 10px;
}

form label {
  display: block;
  margin-bottom: 5px;
}

form input[type="text"],
form input[type="file"] {
  display: block;
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 16px;
  margin-top: 5px;
}

form button[type="submit"] {
  background-color: #0275d8;
  border: none;
  color: #fff;
  padding: 10px 20px;
  font-size: 16px;
  border-radius: 4px;
  cursor: pointer;
}

form button[type="submit"]:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

</style>