<template>
    <div class="body">
          <div class="main">
            <div class="login">
    <!-- 卡片 -->
    <el-card class="box-card">
      <div class="lgoinWord" style="font-size: 30px;font-weight: bold;padding:0 30% 0 40%;">请登陆</div>
      <!-- 登录 or 注册 -->
      <el-radio-group v-model="labelPosition" class="radioGroup" size="large">
        <el-radio-button label="login" >登录</el-radio-button>
        <el-radio-button label="signIn">注册</el-radio-button>
      </el-radio-group>
      <!-- user输入表单 -->
      <el-form label-position="right" label-width="80px" :model="user">
        <el-form-item
            label="用户名"
            prop="name"
            :rules="[ { required: true, message: '请输入用户名', trigger: 'blur' } ]">
          <el-input style="height: 30px; border: 1px solid rgba(0, 0, 0, 0.4);border-radius: 5px;" v-model="user.name"></el-input>
        </el-form-item>
        <el-form-item
            label="密码"
            prop="password"
            :rules="[ { required: true, message: '请输入密码', trigger: 'blur' } ]">
          <el-input style="height: 30px; border: 1px solid rgba(0, 0, 0, 0.4);border-radius: 5px;" type="password" v-model="user.password" show-password></el-input>
        </el-form-item>
        <el-form-item
            v-if="labelPosition==='signIn'"
            label="确认密码"
            prop="checkPassword"
            :rules="[ { required: true, message: '请输入再次输入密码', trigger: 'blur' } ]">
          <el-input style="height: 30px; border: 1px solid rgba(0, 0, 0, 0.4);border-radius: 5px;" type="password" v-model="user.checkPassword" show-password></el-input>
        </el-form-item>
        <el-form-item
          label="验证码"
          prop="captchaImage"
          :rules="[{required: true, message: '请填写验证码', trigger: 'blur' }]" >
          <el-row :span="24">
            <el-col :span="12">
              <el-input style="height: 30px; width: 100%; border: 1px solid rgba(0, 0, 0, 0.4);border-radius: 5px;" v-model="user.captchaImage"></el-input>
            </el-col>
            <el-col :span="12">
              <div class="login-code" @click="refreshCode" style="cursor: pointer;">
                <captcha-component>
                  <img v-if="captcha" :src="captcha.img" alt="验证码">
                </captcha-component>
              </div>
            </el-col>
          </el-row>  
        </el-form-item>
        <!--按钮-->
        <el-form-item class="button">
          <el-button v-if="labelPosition==='login'" type="warning" @click="login"
                     :disabled="user.name===''||user.password===''||user.captchaImage===''" round>登录
          </el-button>
          <el-button v-if="labelPosition==='signIn'" type="warning" @click="signIn"
                     :disabled="user.name===''||user.password===''||user.checkPassword===''||user.captchaImage===''" round>注册
          </el-button>
          <el-button @click="resetForm" round>重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
          </div>
    </div>
</template>


<script>
import CaptchaComponent from './CaptchaComponent.vue'
import { ref, reactive} from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

export default {
  components:{
    CaptchaComponent
  },
  setup() {
    
    const router = useRouter()

    const labelPosition = ref('login')
    const user = reactive({
      name: '',
      password: '',
      checkPassword: '',
      captchaImage: '',
    })

    function login() {
      router.push('/video')
    }

    function signIn() {
      if (user.checkPassword !== user.password) {
        ElMessage.error("两次输入的密码不一致!")
      } else {
        ElMessage.success("注册成功！")
        router.push("/login")
        labelPosition.value = 'login'
      }
    }

    function resetForm() {
      user.name = ""
      user.password = ""
      user.checkPassword = ""
      user.captchaImage = ""
    }

    return {
      labelPosition,
      user,
      login,
      signIn,
      resetForm
    }
  }
}
</script>


<style scoped >
*, *::after, *::before {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  user-select: none;
}

.body {
  width: 100%;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  font-family: "Montserrat", sans-serif;
  background-color: #ecf0f3;
  color: #a0a5a8;
}

.main {
  position: relative;
  width: 1000px;
  min-width: 1000px;
  min-height: 600px;
  height: 600px;
  padding: 25px;
  background-color: #ecf0f3;
  box-shadow: 10px 10px 10px #d1d9e6, -10px -10px 10px #f9f9f9;
  border-radius: 20px;
  overflow: hidden;
}
@media (max-width: 1200px) {
  .main {
    transform: scale(0.7);
  }
}
@media (max-width: 1000px) {
  .main {
    transform: scale(0.6);
  }
}
@media (max-width: 800px) {
  .main {
    transform: scale(0.5);
  }
}
@media (max-width: 600px) {
  .main {
    transform: scale(0.4);
  }
}

.login{
  position: absolute;
  width: 50%;
  height: 50%;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: #fdfbfb;
  box-shadow: 10px 10px 10px #d1e6d4, -10px -10px 10px rgb(144, 183, 168);
  border-radius: 20px;
  overflow: hidden;
}

.box-card {
  width: 100%;
  height: 100%;
}


</style>
