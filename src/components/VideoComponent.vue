<template>
<div class="navbar">
<div class="search-box">
 <!-- 在这里添加搜索框的代码 -->
 <div class="search-input">
  <input type="text" placeholder="搜索视频...">
  <button><i class="fas fa-search"></i></button>
</div>
</div>
<div class="user-avatar">
 <!-- 在这里添加用户头像的代码 -->
</div>
</div>
<div class="video-main">
 <div class="video-list-container">
   <div class="video-list">
<div class="video-secode-list" v-for="(videoCollection, collectionIndex) in videoList" :key="collectionIndex" @click="currentVideoCollectionIndex = collectionIndex; currentVideoIndex = 0" :class="{ 'active': collectionIndex === currentVideoCollectionIndex }">
 {{ videoCollection.title }}
</div>
</div>
 </div>
 <div class="video-container">
   <h2>{{ currentVideo.title }}</h2>
   <div class="video-wrapper">
     <video :src="currentVideo.src" controls></video>
   </div>
<div class="like-section" :class="{ 'liked': currentVideo.liked }"  @click="toggleLike" @mouseover="changeCursor" @mouseleave="resetCursor"> 
   <div class="like-button">
     <i class="fas fa-heart" :class="{ 'liked': currentVideo.liked }" @click="toggleLike"></i>
     <span class="like-count">{{ formattedLikes  }}</span>
   </div>
 </div>
</div>   
</div>

</template>

<script>
// import axios from 'axios';  
const currentVideoCollectionIndex = 0;

export default {
data() {
 return {
   videoList: [
     {
       title: '热门视频',
       videos: [
         {
           id: 1,
           src: '',
           title: '',
           likes: 11000,
           liked: false
         }
       ]
     },
     {
       title: '体育频道',
       videos: [{
          id:'',
          src:'',
          title:'',
          likes:'',
          liked:''
       }]
     },
     {
       title: '音乐频道',
       videos: [{
          id:'',
          src:'',
          title:'',
          likes:'',
          liked:''
       }]
     },
      {
       title: '美妆频道',
       videos: [{
          id:'',
          src:'',
          title:'',
          likes:'',
          liked:''
       }]
     },
       {
       title: '时尚频道',
       videos: [{
          id:'',
          src:'',
          title:'',
          likes:'',
          liked:''
       }]
     },
      {
       title: '二次元频道',
       videos: [{
          id:'',
          src:'',
          title:'',
          likes:'',
          liked:''
       }]
     },
       {
       title: '娱乐频道',
       videos: [{
          id:'',
          src:'',
          title:'',
          likes:'',
          liked:''
       }]
     },
      {
       title: '知识频道',
       videos: [{
          id:'',
          src:'',
          title:'',
          likes:'',
          liked:''
       }]
     }
   ],
   currentVideoCollectionIndex,
   currentVideoIndex: 0
 };
},
computed: {
 currentVideoCollection() {
   return this.videoList[this.currentVideoCollectionIndex];
 },
 currentVideo() {
   return this.currentVideoCollection.videos[this.currentVideoIndex];
 },
 formattedLikes() {
   if (this.currentVideo.likes >= 10000) {
     return (this.currentVideo.likes / 10000).toFixed(1) + 'w';
   } else if (this.currentVideo.likes >= 1000) {
     return (this.currentVideo.likes / 1000).toFixed(1) + 'k';
   } else {
     return this.currentVideo.likes.toString();
   }
 }
},
methods: {
 selectVideo(index) {
   this.currentVideoIndex = index;
 },
 selectVideoCollection(index) {
this.currentVideoCollectionIndex = index;
this.currentVideoIndex = 0; // 切换视频集合时，将当前视频索引重置为0
},
 toggleLike() {
   if (this.currentVideo.liked) {
     if (this.currentVideo.likes > 0) {
       this.currentVideo.likes--;
     }
   } else {
     if (this.currentVideo.likes < 9999) {
       this.currentVideo.likes++;
     }
   }
   this.currentVideo.liked = !this.currentVideo.liked;
 },
 fetchVideoList() {
//       // 使用API从后端获取视频数据
//       // 可以使用axios或其他库发送异步请求，获取视频数据，并更新videoList
//       axios
//         .get('/biz/videos/videos')
//         .then(response => {
//           const data = response.data;

//           this.videoList = data.map(collection => {
//             return {
//               videos: collection.videos
//                 ? collection.videos.map(video => {
//                     return {
//                       id: video.id ? video.id : '',
//                       src: video.src ? video.src : '',
//                       title: video.title ? video.title : '',
//                       likes: video.likes ? video.likes : 0,
//                       liked: false
//                     };
//                   })
//                 : []
//             };
//           });
//         })
//         .catch(error => {
//           console.log(error);
//         });
    },
},
mounted() {
this.fetchVideoList(); // 在组件挂载后调用fetchVideoList方法，获取视频数据
 document.addEventListener('keydown', (event) => {
   if (event.key === 'ArrowUp') {
     this.currentVideoIndex = this.currentVideoIndex > 0 ? this.currentVideoIndex - 1 : this.currentVideoCollection.videos.length - 1;
   } else if (event.key === 'ArrowDown') {
     this.currentVideoIndex = this.currentVideoIndex < this.currentVideoCollection.videos.length - 1 ? this.currentVideoIndex + 1 : 0;
   }
 });
}
};
</script>

<style scoped>
#app{
   background-color: black;
}
.navbar {
background-color:black;
height: 60px;
display: flex;
align-items: center;
justify-content: space-between;
padding: 0 20px;
border-radius: 12px;
}

.search-box {
border-radius: 20px;
overflow: hidden;
}
.search-input input:focus {
  outline: none;
  border-color: #ccc;
  box-shadow: 0 0 5px #ccc;
}
.search-input {
  position: relative;
}

.search-input input {
  width: 100%;
  height: 40px;
  padding: 10px;
  font-size: 16px;
  border: none;
  border-radius: 20px;
  background-color: #f0f0f0;
  transition: all 0.3s ease-in-out;
}

.search-input button {
  position: absolute;
  top: 50%;
  right: 10px;
  transform: translateY(-50%);
  width: 30px;
  height: 30px;
  border: none;
  background-color: transparent;
  cursor: pointer;
  transition: all 0.3s ease-in-out;
}

.search-input button:hover {
  transform: translateY(-50%) scale(1.2);
}

.search-input button i {
  font-size: 16px;
  color: #888;
  transition: all 0.3s ease-in-out;
}

.search-input input:focus + button i {
  color: #333;
}

.search-input input:focus {
  outline: none;
  border: none;
  background-color: transparent;
  box-shadow: none;
}

.search-input input:focus + button i {
  color: #333;
}

.search-input input::placeholder {
  font-style: italic;
  opacity: 0.5;
}

.search-input input:focus::placeholder {
  opacity: 1;
}
.user-avatar {
width: 40px;
height: 40px;
border-radius: 50%;
background-image: url(''); /* 替换成用户头像图片路径 */
background-size: cover;
background-color: white;
cursor: pointer;
}  
.search-box {
width: 200px;
height: 40px;
background-color: white;
display: flex;
align-items: center;
padding: 0 10px;
}  
.video-main {
display: flex;
height:  89vh;
}

.video-list-container {
background-color: black;
color: white;
cursor: pointer;
padding: 20px;
border-radius:12px;
height: 93%;
}

.video-list {
list-style: none;
padding: 0;
margin: 0;
font-size:18px;
}

.video-secode-list {
margin-bottom: 30px;
background-color: rgba(0, 0, 0, 0.5);
border-radius: 12px;
}

.video-secode-list:hover {
text-decoration: underline;
background-color: rgba(255, 255, 255, 0.5);
border-radius: 12px;
}

.video-container {
flex-grow: 1;
display: flex;
flex-direction: column;
align-items: center;
justify-content: center;
position: relative;
}

h2 {
margin-top: 0;
}

.video-wrapper {
width: 100%;
height: 100%;
position: relative;
background-color: black;
border-radius: 12px; /* 设置为需要的圆弧大小 */
overflow: hidden;
}

video {
width: 98%;
height: 100%;
object-fit: cover;
}

.like-section {
display: flex;
align-items: center;
justify-content: center;
position: relative;
bottom: 50%;
right: -48%;
transform: translate(-50%, 50%);
cursor: pointer;
width: 40px;
height: 40px;
border-radius: 50%;
background-color: #fff;
}

.like-section.liked {
background-color: red;
}
.like-button {
margin-right: 5px;
}

.fa-heart {
font-size: 32px;
color: #ccc;
cursor: pointer;
margin-right: 5px;
}

.fa-heart.liked {
color: red;
}

.like-count {
font-size: 18px;
position: absolute;
bottom: -85%;
right: 0;
display: inline-block;
text-align: center;
color: white;
}

/* 禁用滚动条样式 */
.video-list-container::-webkit-scrollbar {
width: 0.4em;
}

.video-list-container::-webkit-scrollbar-track {
background-color: black;
}

.video-list-container::-webkit-scrollbar-thumb {
background-color: #888;
border-radius: 2px;
}

.video-list-container::-webkit-scrollbar-thumb:hover {
background-color: #555;
}
</style>