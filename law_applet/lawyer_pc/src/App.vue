<template>
  <router-view></router-view>
</template>

<script>
export default {
  name: 'App',
}
</script>

<style lang="less">
* {
  padding: 0px;
  margin: 0px;
  box-sizing: border-box;
  scrollbar-width: none;
  -ms-overflow-style: none; 
  font-size: 14px;

  &::-webkit-scrollbar {
    display: none
  }
}
</style>
