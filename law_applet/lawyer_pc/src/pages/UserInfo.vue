<template>
  <div class="user_info_page">
    <div class="title">个人信息</div>
    <div class="info_box">
      <div class="row">
        <div class="key">头像</div>
        <div class="avatar_box">
          <img class="user_avatar" :src="buildImg(userInfo.imgUrl) || 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg'" alt="user_avatar.png" >
          <div class="replace_avatar" @click.stop="chooseAvatar()">更新头像</div>
        </div>
      </div>
      <div class="row">
        <div class="key">ID</div>
        <div class="value">{{ userInfo.id || '--' }}</div>
      </div>
      <div class="row">
        <div class="key">姓名</div>
        <div class="value">{{ userInfo.userName || '--' }}</div>
      </div>
      <div class="row">
        <div class="key">账号</div>
        <div class="value">{{ userInfo.account || '--' }}</div>
      </div>
      <div class="row">
        <div class="key">密码</div>
        <div class="value">{{ userInfo.password || '--' }}</div>
      </div>
      <div class="row">
        <div class="key">职位</div>
        <div class="value">{{ userInfo.position || '--' }}</div>
      </div>
      <div class="row">
        <div class="key">创建时间</div>
        <div class="value">{{ userInfo.insertTime || '--' }}</div>
      </div>
      <div class="row">
        <div class="key">更新时间</div>
        <div class="value">{{ userInfo.updateTime || '--' }}</div>
      </div>
      <div class="row">
        <div class="key">简介</div>
        <div class="value">{{ userInfo.introduction || '--' }}</div>
      </div>
      <div class="row">
        <div class="key">擅长</div>
        <div class="value">{{ userInfo.adept || '--' }}</div>
      </div>
      <div class="row">
        <div class="key">咨询价格</div>
        <div class="value">{{ userInfo.consultPrice || '--' }}</div>
      </div>
    </div>
  </div>
</template>

<script>
import $network from "@/commons/network";
import $utils from '@/commons/utils';

export default {
  name: 'UserInfoPage',
  data() {
    return {
      userInfo: {}
    }
  },
  /**
   * 页面加载时
   */
  mounted() {
    this.initPageData();
  },
  /**
   * 页面方法合集
   */
  methods: {
    /**
     * 初始化页面数据
     */
    initPageData() {
      this.userInfo = this.$store.state.userInfo;
    },
    /**
     * 构建图片地址
     * @param url
     * @return {*}
     */
    buildImageUrl(url){

      return $utils.buildImageUrl(url);
    },
    /**
     * 用户头像加载失败
     * @param res
     */
    userAvatarLoadFail(res) {
      res.target.src = require('../assets/logo.png')
    },
    /**
     * 选择用户头像
     */
    chooseAvatar(){
      $utils.chooseImage({
        success:res=>  this.updateAvatar(res.file),
        fail:res=> console.error(res)
      })
    },
    /**
     * 更新头像
     * @param base64
     */
    updateAvatar(file){
      this.$loading.show('更新中');
      $network.uploadImage(file,res=>{
        res.code === 200 ? this.updateUserInfo(res.data) : this.uploadAvatarFail(res.msg);
      },fail=>this.uploadAvatarFail(res,errMsg));
    },
    /**
     * 头像上传失败
     * @param msg
     */
    uploadAvatarFail(msg){
      this.$loading.hide();
      this.showToast(msg);
    },
    /**
     * 更新用户信息
     * @param newAvatar
     */
    updateUserInfo(newAvatar){
      $network.userUpdate(this.buildUpdateParams(newAvatar),res=>{
        res.code === 200 ? this.parseUpdateResult() : this.updateFail(res.msg);
      },res=>this.updateFail(res.errMsg))
    },
    /**
     * 更新更新参数
     * @param newAvatar
     * @return {{}}
     */
     buildUpdateParams(newAvatar) {
      return {
        id: this.$store.state.userInfo.id,
        imgUrl: newAvatar,
      };
    },
    /**
     * 更新失败
     * @param msg
     */
    updateFail(msg){
      this.$loading.hide();
      this.showToast(msg);
    },
    /**
     * 处理更新结果
     */
    parseUpdateResult(){
      $network.userInfo(
        null,
        (res) => {
          res.code === 200 ? this.getUserSuccess(res.data) : this.updateFail(res.msg || '获取用户信息失败！');
        },
        (res) => this.updateFail(res)
      );
      
    },
    /**
     * 获取用户信息
     */
     getUserSuccess(userInfo) {
      this.$store.commit("setUserInfo", userInfo);
      this.userInfo = this.$store.state.userInfo;
      this.$loading.hide();
    },
    buildImg(img) {
      if(!img) return '';
      else return `http://127.0.0.1:8088${img}`;
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
.user_info_page {
  width: 100%;
  height: 100%;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  align-items: center;
  box-sizing: border-box;

  .title {
    font-weight: bold;
    padding: 30px;
  }

  .info_box {
    min-width: 700px;
    columns: 2;
    border-top: 0.5px solid rgba(0, 0, 0, 0.1);
    border-bottom: 0.5px solid rgba(0, 0, 0, 0.1);
    padding: 30px;

    .row {
      display: flex;
      align-items: center;
      padding: 10px 0px;
      border-bottom: 1px dashed rgba(0, 0, 0, 0.1);

      .key {
        width: 100px;
        color: rgba(0, 0, 0, 0.5);
      }

      .value {
        width: 300px;
        word-wrap: break-word;
        text-align: left;
      }

      .avatar_box {
        width: 100px;
        height: 100px;
        position: relative;

        .user_avatar {
          width: 100%;
          height: 100%;
          cursor: pointer;
        }

        .replace_avatar {
          width: 100%;
          height: 100%;
          position: absolute;
          top: 0px;
          left: 0px;
          display: flex;
          flex-direction: column;
          align-items: center;
          justify-content: center;
          cursor: pointer;
          background-color: rgba(33,164,255,0.5);
          border-radius: 10px;
          color: #ffffff;
          opacity: 0;
          &:hover{
            opacity: 1;
          }
        }
      }


      .enable, .online {
        color: #2ecc71;
      }

      .disabled, .outline {
        color: #bdc3c7;
      }

    }
  }

}
</style>