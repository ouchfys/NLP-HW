<template>
  <!--  容器页面-->
  <div class="container_page">
    <HeadNav class="head_nav_c"></HeadNav>
    <div class="container_work">
      <SidebarNav></SidebarNav>
      <router-view class="router_view"></router-view>
    </div>
  </div>
</template>
<script>

import $network from "@/commons/network";


export default {
  name: 'ContainerPage',
  mounted() {
    this.initPageData();
  },
  methods: {
    /**
     * 初始化页面数据
     */
    initPageData() {

    },
    /**
     * 获取职称列表
     */
    getTitleList() {
      $network.getTitleList({}, res => {
        res.code === 200 ? this.getTitleListSuccess(res) : this.getTitleListFail(res.msg);
      }, res => this.getTitleListFail(res));
    },
    /**
     * 获取职称列表成功
     * @param res
     */
    getTitleListSuccess(res) {
      this.$store.commit('setTitleList',res.data);
    },
    /**
     * 获取职称列表失败
     * @param errMsg
     */
    getTitleListFail(res) {
      console.error(res)
      this.showToast(res);
    },
    /**
     * 显示提示框
     * @param {string | {}} msg
     */
    showToast(msg) {
      console.log('showToast -------------->',this.$toast.show)
      // this.$toast.show({
      //   msg: typeof msg === 'string' ? msg : JSON.stringify(msg)
      // })
    },
  }
}
</script>

<style lang="less">
.container_page {
  width: 100vw;
  height: 100vh;
  display: grid;
  grid-template-rows: 50px auto;
  padding: 10px;
  row-gap: 10px;

  .container_work {
    display: grid;
    grid-template-columns: 150px auto;
    column-gap: 10px;

    .router_view {
      width: 100%;
      height: 100%;
      border-radius: 10px;
      box-shadow: 0px 0px 10px 10px rgba(0, 0, 0, 0.1);
      padding: 10px;
      overflow: hidden;
    }
  }
}
</style>