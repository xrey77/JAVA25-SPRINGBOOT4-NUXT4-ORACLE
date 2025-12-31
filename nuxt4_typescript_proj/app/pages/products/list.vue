<template>
  <div class="container-fluid bg">
    <div class="container">
    <h3 class="text-center mt-2 mb-2">Product List</h3>

    <table class="table table-striped table-danger table-hover">
    <thead class="table-primary">
        <tr>
        <th class="bg-warning" scope="col">#</th>
        <th class="bg-warning" scope="col">Descriptions</th>
        <th class="bg-warning" scope="col">Stocks</th>
        <th class="bg-warning" scope="col">Unit</th>
        <th class="bg-warning" scope="col">Price</th>
        </tr>
    </thead>
    <tbody class="table-group-divider" v-for="item in vardata.prods" key="item.id">
        <tr>
        <td>{{ item.id }}</td>
        <td>{{item.descriptions}}</td>
        <td>{{item.qty}}</td>
        <td>{{item.unit}}</td>
        <td><span class="text-danger">&#8369;</span>{{formatNumberWithCommaDecimal(item.sellprice)}}</td>
        </tr>
    </tbody>
    </table>

    <div class="align-middle text-center text-warning" v-if="vardata.listMsg">{{ vardata.listMsg }}</div>

    <!-- <div v-if="vardata.listMsg === ''"> -->
     <nav aria-label="Page navigation example">
        <ul class="pagination">
          <li class="page-item"><a @click="lastPage" class="page-link" href="#">Last</a></li>
          <li class="page-item"><a @click="prevPage" class="page-link" href="#">Previous</a></li>
          <li id="next" class="page-item"><a @click="nextPage" class="page-link" href="#">Next</a></li>
          <li class="page-item"><a @click="firstPage" class="page-link" href="#">First</a></li>
          <li class="page-item page-link text-danger">Page&nbsp;{{vardata.page}} of&nbsp;{{vardata.totpage}}</li>
        </ul>
      </nav>
    </div>

        <div class="text-center fixed-bottom mb-2 text-white">Copyright 2025, All rights reserved. World Taekwondo</div>

    
  </div>

</template>

<style lang="scss" scoped>

.disabled-li {
    pointer-events: none; /* Prevents click events */
    opacity: 0.6; /* Makes it appear faded */
    cursor: not-allowed; /* Changes cursor to indicate no interaction */
  }
  .bg-footer {
    color: white !important;
    background-color: #000 !important;
  }
  .bg {
      height: 88vh !important;    
      background-color: black;
  }
  
</style>

<script setup lang="ts">
    import $ from 'jquery';
    import {reactive, onMounted, ref} from 'vue'
    import axios from 'axios';
    import Footer from '~/layouts/Footer.vue';

    const api = axios.create({
        baseURL: "http://localhost:9090",
        headers: {'Accept': 'application/json',
                'Content-Type': 'application/json'}
    })

const formatNumberWithCommaDecimal = (number: any) => {
  const formatter = new Intl.NumberFormat('en-US', {
    minimumFractionDigits: 2,
    maximumFractionDigits: 2,
  });
  return formatter.format(number);
};

let ln: number = 0;

export interface Proddata{
    page: number,
    totpage: number
    products: Products
}

export interface Products {
    id: string,
    category: string,
    descriptions: string,
    qty: number,
    unit: string,
    sellprice: number
}

    const vardata = reactive({
        page: 1,
        totpage: 0,
        prods: [],
        listMsg: ''
    });

    const fetchProducts = async (pg: any) => {
        api.get(`/take/products/list/${pg}`)
            .then((res: any) => {        
                vardata.prods = res.data.products;
                vardata.totpage = res.data.totpage;
                vardata.page = res.data.page;
            }, (error: any) => {
                if (error.response) {
                    vardata.listMsg = error.response.data.message;
                } else {
                    vardata.listMsg = error.message;
                }
                window.setTimeout(() => {
                    vardata.listMsg = '';
                }, 3000);
                
            });
    }

    onMounted(() => {
        vardata.listMsg = 'loading data, please wait..';
        fetchProducts(vardata.page);
        vardata.listMsg = '';
    });

    const firstPage = (event: any) => {
        event.preventDefault();
            vardata.page = 1;
            fetchProducts(vardata.page);
            return;
    }

    const nextPage = (event: any) => {        
        event.preventDefault();
        if (vardata.page === vardata.totpage) {
            return;
        } 
            vardata.page += 1;
            fetchProducts(vardata.page);
    }

    const prevPage = (event: any) => {
        event.preventDefault();
            vardata.page -= 1;
            fetchProducts(vardata.page);
            return;
    }

    const lastPage = (event: any) => {
        event.preventDefault();
        vardata.page = vardata.totpage;
        fetchProducts(vardata.page);
        return;

    }

</script>
