<script setup lang="ts">
import HeaderContent from "../components/HeaderContent.vue";
import {onMounted} from "vue";
import ItemTable from "../components/ItemTable.vue";
import MainContent from "../components/MainContent.vue";
onMounted(() => {
  document.getElementById("page_main").style.height =
      document.body.clientHeight - document.getElementById("page_header").clientHeight + 'px'
})
</script>
<template>
  <div class="common-layout">
    <el-container style="padding: 0">
      <el-header id="page_header">
        <HeaderContent/>
      </el-header>
      <el-container style="padding: 0">
        <el-aside id="page_aside" width="25%">
          <ItemTable/>
        </el-aside>
        <el-main id="page_main">
          <MainContent/>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>


<style scoped>
#page_header{
  padding: 0;
  height: auto;

}
#page_aside{

}
#page_main{

}

</style>
