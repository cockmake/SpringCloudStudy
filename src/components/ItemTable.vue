<script setup>
import {onMounted, reactive, ref} from 'vue'
import axios from "axios";
import {get_search_result, init_search_result, search_args} from "../vars.js";
onMounted(() => {
  // 初始化种类、价格区间、地区
  axios.get('/api/product/profile')
      .then((resp) => {
        search_args.categories = []
        search_args.origins = []
        for (let i = 0; i < resp.data.categories.length; i++) {
          search_args.categories.push(
              reactive({
                category: resp.data.categories[i],
                checked: true
              }))
        }
        for (let i = 0; i < resp.data.origins.length; i++) {
          search_args.origins.push(
              reactive({
                origin: resp.data.origins[i],
                checked: true
              }))
        }
        init_search_result()
        // for (let i = 0; i < resp.data.categories.length; i++) {
        //   search_args.categories[i].checked = false
        // }
        // for (let i = 0; i < resp.data.origins.length; i++) {
        //   search_args.origins[i].checked = false
        // }
      })
      .catch((err) => {
        console.log(err)
      })

})

const product = reactive({
  price: null,
  origin: null,
  description: null,
  category: null,
  img_url: null,
  stock: null
})
function addConfirm() {
  axios.post('/api/product/add', product).then(res => {
    get_search_result()
    dialogFormVisible.value = false
  })
}
const dialogFormVisible = ref(false)
function openAddDialog() {
  dialogFormVisible.value = true
}
function closeAddDialog() {
  dialogFormVisible.value = false
  for (let key in product) {
    product[key] = null
  }
}
</script>

<template>
  <el-dialog v-model="dialogFormVisible" title="修改商品信息">
    <el-form :model="product" label-position="left" label-width="100px">
      <el-form-item label="商品描述" >
        <el-input v-model="product.description" autocomplete="off" />
      </el-form-item>
      <el-form-item label="原产地">
        <el-input v-model="product.origin" autocomplete="off" />
      </el-form-item>
      <el-form-item label="类别">
        <el-input v-model="product.category" autocomplete="off" />
      </el-form-item>
      <el-form-item label="价格">
        <el-input-number :min="0" :precision="2" v-model="product.price" autocomplete="off" />
      </el-form-item>
      <el-form-item label="库存">
        <el-input-number :min="0" v-model="product.stock" autocomplete="off" />
      </el-form-item>
      <el-form-item label="图片链接">
        <el-input v-model="product.img_url" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="closeAddDialog">取消</el-button>
        <el-button type="primary" @click="addConfirm">
          确定
        </el-button>
      </span>
    </template>
  </el-dialog>
  <div style="display: flex; flex-direction: column">
    <el-button style="width: 90%; height: 40px" round type="danger" @click="openAddDialog">添加新商品</el-button>
    <div>
      <el-form
          label-position="top"
          label-width="auto">
        <el-form-item label="种类：">
          <el-checkbox size="large" border v-for="category in search_args.categories" :label="category.category" v-model="category.checked"/>
        </el-form-item>
        <el-form-item label="地区：">
          <el-checkbox size="large" border v-for="origin in search_args.origins" :label="origin.origin" v-model="origin.checked"/>
        </el-form-item>
        <el-form-item label="是否有货：">
          <el-checkbox size="large" border label="只看有货" v-model="search_args.remained"></el-checkbox>
        </el-form-item>
      </el-form>
    </div>
    <el-button size="large" style="margin: 0 auto" type="primary" @click="get_search_result">确定</el-button>

  </div>

</template>

<style scoped>

</style>