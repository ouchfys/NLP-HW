<template>
  <div class="home_page">
    <div class="tip_box">
      <div class="tip title">欢迎使用</div>
      <div class="tip">律师咨询服务平台</div>
    </div>
    <div class="into_room" @click.stop="jumpToInquiryRoom()">进入主界面</div>
  </div>
</template>

<script>

export default {
  name: 'HomePage',
  data() {
    return {
      tipList: []
    }
  },
  mounted() {
  },
  methods: {
    jumpToInquiryRoom(){
      let role = localStorage.getItem('role');
      role === 1 ? this.$router.push('/legal/info/manage') : this.$router.push('/legal/advice/order')
    },
  }
}
</script>

<style lang="less">
.home_page {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  .tip {
    color: rgba(0, 0, 0, 1);
    margin-top: 20px;
    font-size: 34px;
  }

  .title {
    font-weight: bold;
    width: 100%;
    text-align: center;
    font-size: 32px;
  }
  .into_room{
    width: 100px;
    height: 35px;
    text-align: center;
    background-color: #21a4ff;
    line-height: 35px;
    color: #ffffff;
    border-radius: 10px;
    margin-top: 30px;
    cursor: pointer;
    &:active{
      background-color: rgba(0,0,0,0.1);
      color: #999999;
    }
  }
}
</style>