<script setup>
import OrderTable from "../components/OrderTable.vue";
import {onMounted} from "vue";

onMounted(()=>{
  localStorage.setItem('username', 'wjw')
})

</script>
<template>
<div>
  <OrderTable/>
</div>
</template>

<style scoped>

</style>