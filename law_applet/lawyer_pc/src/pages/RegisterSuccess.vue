<template>
  <div class="register_success_page">
    <div class="container">
      <img class="icon" src="../../src/assets/icon_002.png" alt="success"/>
      <div class="title">注册成功</div>
      <div class="remark">注册信息已提交，请耐心等待管理员审核！或请联系管理员！</div>
      <div class="btn" @click.stop="jumpToLoginPage()">确认</div>
    </div>
  </div>
</template>

<script>
import $utils from "@/commons/utils";

export default {
  name: 'RegisterSuccess',
  methods:{
    /**
     * 跳转到登录页面
     */
    jumpToLoginPage(){
      $utils.reloadApp();
    }

  }
}
</script>

<style lang="less">
.register_success_page {
  width: 100vw;
  height: 100vh;
  display: grid;
  grid-template-columns: 1fr;
  align-items: center;
  justify-items: center;
  .container{
    display: grid;
    grid-template-columns: 1fr;
    gap: 30px;
    justify-items: center;
    .icon{
      width: 100px;
      height: 100px;
    }
    .remark{
      color: #999999;
    }
    .btn{
      width: 100px;
      height: 40px;
      text-align: center;
      line-height: 40px;
      background-color: #1cc163;
      border-radius: 10px;
      color: #ffffff;
      cursor: pointer;

      &:active{
        background-color: rgba(0,0,0,0.1);
        color: #999999;
      }
    }
  }

}
</style>

