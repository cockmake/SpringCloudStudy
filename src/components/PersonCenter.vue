<script setup>
import router from "../routers.js";
import {onMounted, reactive, ref} from "vue";
import axios from "axios";
import {ElMessage} from "element-plus";
import {get_search_result, isRoot} from "../vars.js";
onMounted(() => {
  if (router.currentRoute.value.name === 'order'){
    button_desc.value = '商城主页'
  }else{
    button_desc.value = '订单管理'
  }
})
function to_center() {
  console.log('用户中心')
}
const button_desc = ref('订单管理')
function convertPage() {

  if (button_desc.value === '订单管理'){
    to_order_page()
    if (router.currentRoute.value.path === '/order') button_desc.value = '商城主页'
  }else{
    to_home()
    button_desc.value = '订单管理'
  }
  
}
function to_order_page() {
  router.push('/order')
}
function to_home() {
  router.push('/home')
}

const textTip = ref('登录')
function log() {
  if (textTip.value === '登录'){
    user.username = ''
    user.password = ''
    centerDialogVisible.value = true
  }else if(textTip.value === '退出'){
    logout()
  }
}

const user = reactive({
  username: '',
  password: ''
})
function logout() {
  localStorage.removeItem("root")
  localStorage.removeItem("username")
  current_username.value = null
  isRoot.value = false
  textTip.value = "登录"
  router.push('/home')
}
function login(){
  if(user.username === '' || user.password === '') return
  axios.post('/api/user/login', user)
      .then((resp) => {
        if(resp.data.username !== undefined){
          ElMessage({
            type: 'success',
            message: '登录成功！',
            duration: 3000
          })
          localStorage.setItem("username", resp.data.username)
          localStorage.setItem("root", resp.data.root)
          current_username.value  = localStorage.getItem("username")
          isRoot.value = resp.data.root === "root";
          // 重新进行一次请求
          get_search_result()
          textTip.value = "退出"
          centerDialogVisible.value = false
        }else{
          ElMessage({
            type: "warning",
            message: '账号或密码错误',
            duration: 3000
          })
        }
      })
      .catch(err => {

      })
}
const current_username = ref(null)
const centerDialogVisible = ref(false)
const registerDialogVisible = ref(false)
function register() {
  registerForm.username = ''
  registerForm.password = ''
  registerForm.re_password = ''
  registerDialogVisible.value = true
}
function confirmRegister() {
  if(registerForm.username === '' || registerForm.password === '' || registerForm.re_password === ''){
    ElMessage({
      type: 'warning',
      message: "输入框不允许为空！",
      duration: 3000
    })
    return
  }
  if(registerForm.password !== registerForm.re_password){
    ElMessage({
      type: 'success',
      message: "两次密码输入不一致！",
      duration: 3000
    })
    return
  }
  axios.post("/api/user/register", {
    username: registerForm.username,
    password: registerForm.password
  })
      .then(resp => {
        if(resp.data === "注册成功"){
          ElMessage({
            type: 'success',
            message: "注册成功！欢迎登录。",
            duration: 3000
          })
          registerDialogVisible.value = false
        }else{
          ElMessage({
            type: 'error',
            message: "注册失败！服务器出错。",
            duration: 3000
          })
        }
      })
      .catch(err => {

      })
  // 先进行校验

}
const registerForm = reactive({
  username: '',
  password: '',
  re_password: ''
})
onMounted(() => {
  current_username.value = localStorage.getItem("username")
  if (current_username.value === null){
    textTip.value = "登录"
  }else{
    textTip.value = "退出"
  }
})
</script>

<template>
<div>
  <el-dialog
      v-model="centerDialogVisible"
      title="请先登录！"
      width="40%"
      align-center>
    <el-form
        label-position="top"
        label-width="100px"
        :model="user">
      <el-form-item label="用户名：">
        <el-input size="large" v-model="user.username" />
      </el-form-item>
      <el-form-item label="密码：">
        <el-input size="large" v-model="user.password"  type="password"/>
      </el-form-item>
    </el-form>

    <el-dialog
        v-model="registerDialogVisible"
        title="注册"
        width="45%"
        append-to-body
        align-center>
      <el-form
          label-position="top"
          label-width="100px"
          :model="registerForm">
        <el-form-item label="用户名：">
          <el-input size="large" v-model="registerForm.username" />
        </el-form-item>
        <el-form-item label="密码：">
          <el-input size="large" v-model="registerForm.password"  type="password"/>
        </el-form-item>
        <el-form-item label="确认密码：">
          <el-input size="large" v-model="registerForm.re_password"  type="password"/>
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
          <el-button size="large" type="primary" @click="confirmRegister">
            确定注册
          </el-button>
      </span>
      </template>

    </el-dialog>

    <template #footer>
      <span class="dialog-footer">
          <el-button size="large" type="info" @click="register" style="margin-right: 40%;">注册</el-button>
          <el-button size="large" @click="centerDialogVisible = false">取消</el-button>
          <el-button size="large" type="primary" @click="login">
            登录
          </el-button>
      </span>
    </template>
  </el-dialog>

  <el-page-header :icon="null">
    <template #title>
      <div @click="to_center" class="flex items-center">
        <el-avatar
            :size="32"
            class="mr-3"
            src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"/>
        {{current_username}}
      </div>
    </template>
    <template #content>
      <div class="flex items-center">
        <el-button type="primary" @click="convertPage">{{button_desc}}</el-button>
        <el-button @click="log" type="danger">{{textTip}}</el-button>
      </div>
    </template>
  </el-page-header>
</div>
</template>

<style scoped>
.dialog-footer button:first-child {
  margin-right: 10px;
}
</style>