<template>
    <div class="container-fluid bg ht-50">
      <h2 class="top-10 text-white">Search Product</h2>

      <form class="row g-3" @submit.prevent="submitSearchForm" autocomplete="off">
          <div class="col-auto">
            <input type="text" required class="form-control-sm" v-model="vardata.search" name="search" placeholder="enter description">
            <div class="align-middle text-left text-warning">{{ vardata.listMsg }}</div>
          </div>
          <div class="col-auto">
            <button type="submit" class="btn btn-primary btn-sm mb-3">search</button>
          </div>
      </form>

      <div class="container-fluid bg-dark">
        <div class="card-group">
          <div v-for="product in vardata.prods" class="col-md-4">
            <div class="card mb-3 mx-3">
              <img :src="`http://localhost:9090/products/${product.productpicture}`" class="card-img-top" alt="...">
              <div class="card-body card-text">
                <h5 class="card-title">Descriptions</h5>
                <p class="card-text">{{product.descriptions}}</p>
              </div>
              <div class="card-footer">
                  <p class="card-text text-danger"><span class="text-dark">PRICE :</span>&nbsp;&#8369;<strong class="text-dark">{{formatNumberWithCommaDecimal(product.sellprice) }}</strong></p>
              </div>  
            </div>
          </div>
        </div>    
        </div>
          <div v-if="vardata.totpage !== 0">
            <nav aria-label="Page navigation example">
                <ul class="pagination mx-4 mt-2">
                    <li class="page-item"><a @click="firstPage($event)" class="page-link" href="#">First</a></li>
                    <li class="page-item"><a @click="prevPage($event)" class="page-link" href="#">Previous</a></li>
                    <li class="page-item"><a @click="nextPage($event)" class="page-link" href="#">Next</a></li>
                    <li class="page-item"><a @click="lastPage($event)" class="page-link" href="#">Last</a></li>              
                    <li class="page-item page-link text-danger">Page&nbsp;{{vardata.page}} of&nbsp;{{vardata.totpage}}</li>
                </ul>
              </nav>
          </div>

          <div v-if="vardata.totpage > 1">
            <div class="text-center mt-3 mb-1 text-white">Copyright 2025, All rights reserved. World Taekwondo</div>
          </div>
          <div v-else>
            <div class="text-center fixed-bottom mb-2 text-white">Copyright 2025, All rights reserved. World Taekwondo</div>
          </div>          
          
    </div>
</template>

<script setup lang="ts">
    // import Footer from '../../layouts/Footer.vue';
    import {reactive} from 'vue'
    import axios from 'axios';

    const api = axios.create({
      baseURL: "http://localhost:9090",
      headers: {'Accept': 'application/json',
              'Content-Type': 'application/json'}
    })

    export type Products = {
        id: number;
        descriptions: string;
        qty: number;
        unit: string;
        sellPrice: number;
        productPicture: string;
    }

    export type Productdata = {
        products: Products[];
    }

  const vardata = reactive({
      search: '',
      prods: [],
      // isfound: false,
      listMsg: '',
      page: 1,
      totpage: 0,
      totRecs: 0
  });

  const formatNumberWithCommaDecimal = (number: any) => {
    const formatter = new Intl.NumberFormat('en-US', {
      minimumFractionDigits: 2,
      maximumFractionDigits: 2,
    });
    return formatter.format(number);
  };

  const searchProducts = (page: any, key: any) => {
      // vardata.isfound=true;
      // vardata.listMsg = 'searching, please wait..';

      // await api.get(`/api/searchproducts/${pg}/${key}`)
                const formData = JSON.stringify({ search: key});
                 api.get(`take/products/search/${page}/${key}`)
                .then((res: any) => {
                    vardata.prods = res.data.products;
                    vardata.totpage = res.data.totpage;
                    vardata.page = res.data.page;
                    // vardata.isfound = true;
                    setTimeout(() => {
                      vardata.listMsg = '';
                    }, 3000);
                }, (error: any) => {
                    // vardata.isfound = false;
                    if (error.response) {
                      vardata.listMsg = error.response.data.message;
                    } else {
                      vardata.listMsg = error.message;
                    }
                    vardata.prods = [];
                    setTimeout(() => {
                      vardata.listMsg = '';
                    }, 3000);
                    return;
                });    
  }

  const submitSearchForm = () => {
    searchProducts(vardata.page, vardata.search);
  }

  const nextPage = async (event: any) => {
            event.preventDefault();    
            if (vardata.page === vardata.totpage) {
                return;
            }
            vardata.page = vardata.page + 1;
            await searchProducts(vardata.page, vardata.search);
            return;
  }

  const prevPage = async(event: any) => {
            event.preventDefault();    
            if (vardata.page === 1) {
            return;
            }
            vardata.page = vardata.page - 1;
            await searchProducts(vardata.page, vardata.search);
            return;    
  }
  const firstPage = async(event: any) => {
          event.preventDefault();    
          vardata.page = 1;
          await searchProducts(vardata.page, vardata.search);
          return;    
  }

  const lastPage = async (event: any) => {
      event.preventDefault();    
      vardata.page = vardata.totpage;
      await searchProducts(vardata.page, vardata.search);
      return;    
  }           

</script>

<style scoped>
.top-10 {
    margin-top: 10px;
}

.product-size {
    width: 300px;
    height: auto;
}
.bg {
  height: 90vh !important;    
  background-color: black;
}
.ht-50 {
    width: 100% !important;
    height: 190vh !important;
}

</style>