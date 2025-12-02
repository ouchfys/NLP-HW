<template>
  <div class="toast_view" v-show="isShow">
    <div class="msg">{{ msg }}</div>
  </div>
</template>

<script>


import $utils from "@/commons/network";
import $enums from "@/commons/enums";

export default {
  name: 'ToastView',
  /**
   * 组件数据次
   */
  data() {
    return {
      isShow:false,
      msg: '',
      success: null,
      fail: null,
      timerId: null
    }
  },
  /**
   * 方法合集
   */
  methods: {
    /**
     * 显示
     * @param res
     */
    show(res){
      this.showToast(res);
    },
    /**
     * 隐藏
     * @param res
     */
    hide(res){
      this.hideToast(res);
    },
    /**
     * 显示提示框
     * @param res
     * - msg - string - 提示消息
     * - time - number - 显示时间（秒）
     * - success - Function - 成功回调函数
     * - fail - Function - 失败回调函数
     */
    showToast(res) {
      this.msg = res.msg || 'error';
      this.success = res.success || null;
      this.fail = res.fail || null;
      this.isShow = true;
      this.showToastAfter(res.time)
    },
    /**
     * 成功显示后
     * @param time
     */
    showToastAfter(time) {
      if (this.timerId) clearTimeout(this.timerId);
      this.timerId = setTimeout(() => this.hideToast({}), time || 3 * 1000);
    },
    /**
     * 隐藏提示
     * @param res
     */
    hideToast(res = {}) {
      this.isShow = false;
      if (this.timerId) clearTimeout(this.timerId);
      $utils.callback(res.success,$enums.close_modal);
    },
  }
}
</script>

<style>
.toast_view {
  width: 100vw;
  height: 100vh;
  position: fixed;
  top: 0px;
  left: 0px;
  background-color: rgba(0, 0, 0, 0.4);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  z-index: 999;
  
  .msg {
    width: 100%;
    height: 100px;
    text-align: center;
    line-height: 100px;
    background-color: #21a4ff;
    color: #ffffff;
  }
}
</style>