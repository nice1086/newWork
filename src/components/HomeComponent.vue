<template>
    <div>
        这是Home
    </div>
    <el-form-item class="button">
        <el-button @click="gotoLogin">
            去往login页
          </el-button> 
    </el-form-item>
</template>
<script>

export default {
    methods:{
        gotoLogin(){
            this.$router.push('/login')
        }
    }
}
</script>
