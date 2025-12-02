<template>
  <div class="title_choose_c">
    <div class="container">
      <div class="title">职称选择</div>
      <div class="list" v-if="title_List.length > 0">
        <div class="item" v-for="(item,index) in title_List" :key="index" :class="{selected:item.selected}"
             @click.stop="changeTitleSelected(index)" @dblclick.stop="titleSelected(index)">
          {{ item.titlesName }}
        </div>
      </div>
      <img class="no_data" v-else src="../assets/image_001.png" alt="image_001.png">
      <div class="btn_list">
        <div class="btn cancel" @click.stop="cancelSelected()">取消</div>
        <div class="btn confirm" @click.stop="confirmSelected()">确认</div>
      </div>
    </div>
  </div>
</template>
<script>

export default {
  name: 'TitleChoose',
  props: {
    /**
     * 科室列表
     */
    titleList: {
      type: Array,
      default: () => []
    },
    /**
     * 是否单选
     */
    isSingle: {
      type: Boolean,
      default: () => true
    },
    /**
     * 成功回调
     */
    success: {
      type: Function,
      default: null
    },
    /**
     * 失败回调
     */
    fail: {
      type: Function,
      default: null
    },
  },
  data() {
    return {
      title_List: this.titleList,
      titleItem: null
    }
  },
  methods: {
    /**
     * 可是选择
     * @param index
     */
    changeTitleSelected(index) {
      if (this.isSingle) this.clearSelectedAll();
      this.title_List[index].selected = true;
      if (this.isSingle) this.titleItem = this.getTitleItem(index);
      else this.updateTitleArr(index);
    },
    /**
     * 清楚所有已选择
     */
    clearSelectedAll() {
      this.title_List.forEach(item => item.selected = false);
    },
    /**
     * 获取科室item
     * @param index
     * @returns {*}
     */
    getTitleItem(index) {
      let item = this.title_List[index];
      item._index = index;
      return item;
    },
    /**
     * 更新科室数组
     * @param index
     */
    updateTitleArr(index) {
      if (this.titleItem === null) this.titleItem = [];
      this.titleItem.push(this.getTitleItem(index));
    },
    /**
     * 双击选择
     * @param index
     */
    titleSelected(index) {
      if (!this.isSingle) return;
      this.callback(this.success, 'confirm', this.getTitleItem(index));
    },
    /**
     * 函数回调
     * @param _callback
     * @param type
     * @param data
     */
    callback(_callback, type, data) {
      typeof _callback === 'function' && _callback({type, data});
    },
    /**
     * 取消选择
     */
    cancelSelected() {
      this.callback(this.success, 'cancel', {});
    },
    /**
     * 确认选择
     */
    confirmSelected() {
      if(this.titleItem == null) return;
      this.callback(this.success, 'confirm', this.titleItem);
    }
  }
}
</script>

<style lang="less">
.title_choose_c {
  width: 100vw;
  height: 100vh;
  position: fixed;
  top: 0px;
  left: 0px;
  backdrop-filter: blur(1px);
  background-color: rgba(255, 255, 255, 0.2);
  display: flex;
  align-items: center;
  justify-content: center;

  .container {
    background-color: rgba(255, 255, 255, 1);
    width: 900px;
    max-height: 600px;
    border-radius: 10px;
    padding: 10px;
    display: flex;
    flex-direction: column;
    align-items: center;
    box-shadow: 0px 0px 10px 10px rgba(0,0,0,0.1);

    .title {
      font-weight: bold;
      width: 100%;
      border-bottom: 1px solid rgba(0, 0, 0, 0.1);
      padding-bottom: 10px;
    }

    .list {
      width: 100%;
      margin-top: 30px;
      display: flex;
      align-items: center;
      flex-wrap: wrap;

      .item {
        background-color: rgba(0, 0, 0, 0.1);
        padding: 10px 20px;
        margin: 10px 10px;
        border-radius: 10px;
        cursor: pointer;
        box-sizing: border-box;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;

        &:hover {
          border: 0.5px dashed #21a4ff;
          color: #21a4ff;
        }
      }

      .selected {
        border: 0.5px dashed #21a4ff;
        color: #21a4ff;
      }
    }
    .no_data {
      width: 300px;
      margin-top: 100px;
    }

    .btn_list {
      width: 100%;
      display: flex;
      align-items: center;
      justify-content: end;
      margin-top: 13px;
      border-top: 1px solid rgba(0, 0, 0, 0.1);
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
}
</style>