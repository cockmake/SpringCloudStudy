import {reactive, ref} from "vue";
import axios from "axios";
export const search_args = reactive({
    search_content: '',
    categories: [],
    origins: [],
    remained: false,
    min_price: 0,
    max_price: 2000000000,
})
export const search_result = ref([])
export function get_search_result() {
    let origins = []
    for(let i = 0; i < search_args.origins.length; i++){
        if(search_args.origins[i].checked){
            origins.push(search_args.origins[i].origin)
        }
    }
    let categories = []
    for(let i = 0; i < search_args.categories.length; i++){
        if(search_args.categories[i].checked){
            categories.push(search_args.categories[i].category)
        }
    }
    axios.post('/api/product/filter', {
        searchContent: search_args.search_content,
        remained: search_args.remained,
        categories: categories,
        min_price: search_args.min_price,
        max_price: search_args.max_price,
        origins: origins
    })
    .then((resp) => {
        search_result.value = resp.data
    })
    .catch((err) => {
        console.log(err)
    })
}
export function init_search_result(){
    let origins = []
    for(let i = 0; i < search_args.origins.length; i++){
        origins.push(search_args.origins[i].origin)
    }
    let categories = []
    for(let i = 0; i < search_args.categories.length; i++){
        categories.push(search_args.categories[i].category)
    }
    axios.post('/api/product/filter', {
        searchContent: search_args.search_content,
        remained: search_args.remained,
        categories: categories,
        origins: origins,
        min_price: search_args.min_price,
        max_price: search_args.max_price,
    })
        .then((resp) => {
            search_result.value = resp.data
        })
        .catch((err) => {
            console.log(err)
        })

}
export const order_table_args = reactive({
    currentPage: 1,
    currentSize: 7,
    searchKey: '',
    status: '全部订单',
    priceOrder: null,
    dateOrder: null,
})
export const total = ref(0)
export const order_table_result = ref([
])
export function order_filter_changed() {
    axios.post('/api/order/filter', {
        currentPage: order_table_args.currentPage,
        currentSize: order_table_args.currentSize,
        searchKey: order_table_args.searchKey,
        status: order_table_args.status,
        priceOrder: order_table_args.priceOrder,
        dateOrder: order_table_args.dateOrder,
        username: localStorage.getItem('username')
    })
    .then(resp => {
        total.value = resp.data.total
        order_table_result.value = resp.data.records
    })
    .catch(err => {
        console.log(err)
    })
}
export function tableHeaderClass({column, columnIndex }) {
   if (column.property === 'createdData'){
       column.order = order_table_args.dateOrder
   }else if(column.property === 'price'){
         column.order = order_table_args.priceOrder
   }
   return 'header-class-demo'
}
export function changeSaleState(row) {
    axios.get('/api/product/changeSaleState', {
        params: {
            product_id: row.product_id,
            on_sale: row.on_sale === 1 ? 0 : 1
        }
    })
        .then((resp) => {
            init_search_result()
        })
        .catch((err) => {
            console.log(err)
        })
}



