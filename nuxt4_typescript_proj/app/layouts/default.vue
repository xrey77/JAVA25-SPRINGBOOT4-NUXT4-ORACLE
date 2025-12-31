<template>
    <div>
        <Header/>
        <slot />
    </div>
  </template>

<script setup>
import Header from './Header.vue';

useHead({
  title: 'World Taekwondo',
})

</script>
  
