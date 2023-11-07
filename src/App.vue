<template>
    <!-- <home-component/> -->
    <router-view/>
</template>

<script>
// import HomeComponent from './components/HomeComponent.vue';

window.addEventListener('error', function onError(e) {
    // Ignore ResizeObserver error
    if (e.message === 'ResizeObserver loop limit exceeded') {
      e.stopPropagation();
      e.stopImmediatePropagation();
    }
  });

// export default {
//   name: 'App',
//   components: {
//     HomeComponent
// }
// }
</script>

<style>

</style>