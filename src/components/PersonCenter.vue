<script setup>
import router from "../routers.js";
import {onMounted, ref} from "vue";

onMounted(() => {
  if (router.currentRoute.value.name === 'order'){
    button_desc.value = '商城主页'
  }else{
    button_desc.value = '订单管理'
  }
})
function logout() {
  console.log('退出登录')
}
function to_center() {
  console.log('用户中心')
}
const button_desc = ref('订单管理')
function convertPage() {
  if (button_desc.value === '订单管理'){
    to_order_page()
    button_desc.value = '商城主页'
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
const username = ref(localStorage.getItem('username'))
</script>

<template>
<div>
  <el-page-header :icon="null">
    <template #title>
      <div @click="to_center" class="flex items-center">
        <el-avatar
            :size="32"
            class="mr-3"
            src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"/>
          {{username}}
      </div>
    </template>
    <template #content>
      <div class="flex items-center">
        <el-button type="primary" @click="convertPage">{{button_desc}}</el-button>
        <el-button @click="logout" type="danger">退出</el-button>
      </div>
    </template>
  </el-page-header>
</div>
</template>

<style scoped>

</style>