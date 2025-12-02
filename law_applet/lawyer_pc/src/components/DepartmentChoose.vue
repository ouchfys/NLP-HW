<template>
  <div class="department_choose_c">
    <div class="container">
      <div class="title">科室选择</div>
      <div class="list" v-if="departmentList.length > 0">
        <div class="item" v-for="(item,index) in departmentList" :key="index" :class="{selected:item.selected}"
             @click.stop="changeDepSelected(index)" @dblclick.stop="depSelected(index)">
          {{ item.departName }}
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
  name: 'DepartmentChoose',
  props: {
    /**
     * 科室列表
     */
    depList: {
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
      departmentList: this.depList,
      depItem: null
    }
  },
  methods: {
    /**
     * 可是选择
     * @param index
     */
    changeDepSelected(index) {
      if (this.isSingle) this.clearSelectedAll();
      this.departmentList[index].selected = true;
      if (this.isSingle) this.depItem = this.getDepItem(index);
      else this.updateDepArr(index);
    },
    /**
     * 清楚所有已选择
     */
    clearSelectedAll() {
      this.departmentList.forEach(item => item.selected = false);
    },
    /**
     * 获取科室item
     * @param index
     * @returns {*}
     */
    getDepItem(index) {
      let item = this.departmentList[index];
      item._index = index;
      return item;
    },
    /**
     * 更新科室数组
     * @param index
     */
    updateDepArr(index) {
      if (this.depItem === null) this.depItem = [];
      this.depItem.push(this.getDepItem(index));
    },
    /**
     * 双击选择
     * @param index
     */
    depSelected(index) {
      if (!this.isSingle) return;
      this.callback(this.success, 'confirm', this.getDepItem(index));
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
      if(this.depItem == null) return;
      this.callback(this.success, 'confirm', this.depItem);
    }
  }
}
</script>

<style lang="less">
.department_choose_c {
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