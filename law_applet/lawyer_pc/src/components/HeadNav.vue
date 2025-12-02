<template>
  <div class="head_nav">
    <div class="left">
      <img class="logo" src="../assets/logo.png" alt="logo.png">
      <div class="title">律师咨询服务平台</div>
    </div>
    <div class="right">
      <div class="select" @mouseenter="showUserOpt()">
        <img class="avatar" :src="buildImg($store.state.userInfo.imgUrl)" alt="avatar.png">
        <div class="title">{{ $store.state.userInfo.userName || '测试用户' }}</div>
        <div class="select_list" v-if="isShowUserOpt" @mouseenter="enterUserOpt()"
             @mouseleave="isShowUserOpt = false">
          <div class="item" v-for="(item,index) in userOptList" :key="index" @click.stop="dropDownClick(item)">
            {{ item.title }}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>

import $network from "@/commons/network";
import { ElMessage } from "element-plus";

export default {
  name: 'HeadNav',
  data() {
    return {
      userOptList: [],
      isShowUserOpt: false,
      timerIdUserOpt: null,
    }
  },
  mounted() {
    this.initComponentsData();
  },
  methods: {
    /**
     * 初始化组件也页面
     */
    initComponentsData() {
      this.userOptList = this.buildUserOptList();
    },
    /**
     * 构建用户选项列表
     */
    buildUserOptList() {
      return [{
        title: '修改密码',
        type: 'password_change',
        url: '/password/change'
      }, {
        title: '个人资料',
        type: 'user_info',
        url: '/user/info'
      }, {
        title: '退出登录',
        type: 'logout',
      }]
    },
    showUserOpt() {
      this.isShowUserOpt = true;
      if (this.timerIdUserOpt) clearTimeout(this.timerIdUserOpt);
      this.timerIdUserOpt = setTimeout(() => this.isShowUserOpt = false, 1000);
    },
    enterUserOpt() {
      clearTimeout(this.timerIdUserOpt);
      this.isShowUserOpt = true;
    },
    dropDownClick(item) {
      if (item.url) this.jumpPageByUrl(item.url);
      else this.jumpPageByType(item);
    },
    jumpPageByUrl(url) {
      this.$router.push(url);
    },
    jumpPageByType(item) {
      switch (item.type) {
        case 'logout':
          this.userLogout();
          return;

      }
    },
    userLogout() {
      this.$store.state.userInfo = {};
      this.$store.state.userTel = '';
      this.$store.state.authorization = '';
      this.$store.state.titleList = [];
      localStorage.removeItem('role');
      this.$store.commit('clear');
      this.$router.push('/login');
      window.location.reload()
      // const userId = this.$store.state.userInfo.userId;
      // $network.userLoginOut(
      //   userId,
      //   (res) => {
      //     res.code === 200 ? this.loginSuccess(res) : this.showToast(res.msg);
      //   },
      //   (res) => this.showToast(res)
      // );
      
    },
    loginSuccess() {
      sessionStorage.removeItem('receptionStatus')
      this.$store.commit('clear');
      this.$router.push('/login');
    },
    buildImg(img) {
      if(!img) img = '../assets/logo.png'
      return `http://127.0.0.1:8088/${img}`
    },
    /**
     * 显示提示框
     * @param {string | {}} msg
     */
     showToast(msg) {
      this.$toast.show({
        msg: typeof msg === "string" ? msg : JSON.stringify(msg),
      });
    },
  }
}
</script>

<style lang="less">
.head_nav {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20px;
  box-shadow: 0px 0px 10px 10px rgba(0, 0, 0, 0.1);
  border-radius: 10px;

  .left {
    display: flex;
    align-items: center;

    .logo {
      width: 40px;
      height: 40px;
    }

    .title {
      font-weight: bold;
      margin-left: 30px;
    }
  }

  .middle{
    font-weight: bold;
  }

  .right {
    display: flex;
    align-items: center;
    z-index: 99;

    .select {
      display: flex;
      align-items: center;
      position: relative;
      margin-left: 50px;


      .avatar {
        width: 30px;
        height: 30px;
        margin-right: 10px;
        border-radius: 50%;
      }

      .select_list {
        position: absolute;
        top: 35px;
        left: -55px;
        width: 150px;
        background-color: rgba(255, 255, 255, 1);
        box-shadow: 0px 0px 10px 10px rgba(0, 0, 0, 0.1);
        border-radius: 10px;

        .item {
          width: 100%;
          padding: 10px 0px;
          text-align: center;
          border-bottom: 0.5px dashed rgba(0, 0, 0, 0.1);
          cursor: pointer;

          &:hover {
            background-color: rgba(0, 0, 0, 00.1);
          }

          &:active {
            color: #999999;
          }

          &:first-child {
            border-radius: 10px 10px 0px 0px;
          }

          &:last-child {
            border-radius: 0px 0px 10px 10px;
            border-bottom: none;
          }
        }
      }
    }
  }
}
</style>