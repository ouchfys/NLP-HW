<template>
  <div class="rich_text_c" v-show="isShow">
    <div class="container">
      <div class="title">{{ title }}</div>
      <textarea class="textarea" cols="30" rows="10" v-model="content"></textarea>
      <div class="btn_list">
        <div class="btn cancel" @click.stop="btnClick('cancel')">取消</div>
        <div class="btn confirm" @click.stop="btnClick('confirm')">确认</div>
      </div>
    </div>
  </div>
</template>
<script>
import $utils from "@/commons/utils";

export default {
  name: 'RichText',
  data() {
    return {
      isShow:false,
      title: '',
      content: '',
      success:null,
      fail:null
    }
  },
  /**
   * 方法合集
   */
  methods: {
    /**
     * 显示输入框
     * @param options
     */
    show(options){
      this.title = options.title || '文本输入';
      this.content = options.content || '';
      this.success = options.success || null;
      this.fail = options.fail || null;
      this.isShow = true;
    },
    /**
     * 隐藏输入框
     * @param options
     */
    hide(options = {}){
      this.isShow = false;
    },
    /**
     * 按钮点击事件
     * @param type
     */
    btnClick(type) {
      this.hide();
      $utils.callback(this.success, this.buildResult(type,this.content));
    },
    /**
     * 构建返回结果
     * @param type
     * @param data
     * @returns {{data, type}}
     */
    buildResult(type,data){
      return {type,data}
    },
  }
}
</script>
<style lang="less">
.rich_text_c {
  width: 100vw;
  height: 100vh;
  position: fixed;
  top: 0px;
  left: 0px;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: rgba(0,0,0,0.4);

  .container {
    background-color: rgba(255, 255, 255, 1);
    width: 900px;
    max-height: 600px;
    border-radius: 10px;
    padding: 10px;
    display: flex;
    flex-direction: column;
    align-items: center;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }

  .title {
    font-weight: bold;
    width: 100%;
    border-bottom: 1px solid rgba(0, 0, 0, 0.1);
    padding-bottom: 10px;
  }

  .textarea {
    width: 100%;
    min-width: 100%;
    max-width: 100%;
    margin-top: 10px;
    outline: none;
    border: 1px solid rgba(0, 0, 0, .1);
    border-radius: 10px;
    padding: 10px;
    letter-spacing: 2px;
    box-sizing: border-box;
  }

  .btn_list {
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: end;
    margin-top: 10px;
    border-top: 0.5px solid rgba(0, 0, 0, .1);
    padding-top: 10px;

    .btn {
      width: 100px;
      height: 35px;
      text-align: center;
      line-height: 35px;
      border-radius: 10px;
      cursor: pointer;
    }

    .cancel {
      background-color: rgba(0, 0, 0, 0.07);
      color: rgba(0, 0, 0, 0.5);
      margin-right: 40px;
    }

    .confirm {
      background-color: #21a4ff;
      color: #ffffff;
    }
  }
}


</style>