<script setup>
import {Search} from "@element-plus/icons-vue";
import {search_args, search_result, get_search_result} from "../vars.js";
import router from "../routers.js";
import PersonCenter from "./PersonCenter.vue";
</script>

<template>
  <div class="header_content">
    <div style="width: 100%; height: 100%; display: flex; flex-direction: row; justify-content: space-between">
      <div style="flex: 1; display: flex; flex-direction: row;">
        <div style="flex: 1; display: flex; flex-direction: row;">
          <el-input
              style="height: 70px; flex: 1"
              v-model="search_args.search_content"
              placeholder="搜索商品"
              class="search_area">
            <template #append>
              <el-button @click="get_search_result" style="color: black" :icon="Search"/>
            </template>
          </el-input>
        </div>

        <div style="align-self: center; justify-content: center">
          价格区间
          <el-input-number style="height: 40px; width: 180px"
                           v-model="search_args.min_price"></el-input-number> —
          <el-input-number style="height: 40px; width: 180px"
                           v-model="search_args.max_price"></el-input-number>
        </div>


      </div>
      <PersonCenter/>
    </div>
  </div>
</template>

<style scoped>
.search_area {
  width: 35%;
  padding: 15px;
}
.header_content{
  width: 100%;
  height: 100%;
}
</style>