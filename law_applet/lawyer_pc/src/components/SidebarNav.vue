<template>
  <div class="sidebar_nav">
    <div class="item" v-for="(item, index) in menuList" :key="index">
      <div
        class="title"
        :class="{ selected: item.focus }"
        @click.stop="menuClick(item, index)"
      >
        {{ item.title }}
      </div>
      <div
        class="list"
        v-if="item.children && item.children.length > 0 && item.showChildren"
      >
        <div
          class="title"
          v-for="(itemSub, indexSub) in item.children"
          :key="indexSub"
          :class="{ selected: itemSub.focus }"
          @click.stop="menuClick(itemSub, index, indexSub)"
        >
          {{ itemSub.title }}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "SidebarNav",
  /**
   * 组件数据池
   */
  data() {
    return {
      index: null,
      indexSub: null,
      menuList: [],
    };
  },
  /**
   * 组件挂载完成
   */
  mounted() {
    this.initComponentData();
  },
  /**
   * 组件方法合集
   */
  methods: {
    /**
     * 初始化组件数据
     */
    initComponentData() {
      let role = localStorage.getItem('role');
      // console.log(JSON.parse(userInfo))
      this.menuList = role == 1 ? this.buildMenuList() : this.buildMenuList_2();
      // this.menuList = this.buildMenuList();
    },
    /**
     * 构建菜单列表
     */
    buildMenuList() {
      return [
        {
          title: "法律信息管理",
          url: "/legal/info/manage",
          focus: true,
          children: [],
        },
        {
          title: "律师成员管理",
          url: "/lawyer/member/manage",
          focus: false,
          children: [],
        },
        {
          title: "用户管理",
          url: "/user/manage",
          focus: false,
          children: [],
        },
        {
          title: "评论管理",
          url: "/comment",
          focus: false,
          children: [],
        }
      ];
    },
    buildMenuList_2() {
      return [
        {
          title: "法律咨询订单",
          url: "/legal/advice/order",
          focus: false,
          children: [],
        },
      ];
    },
    /**
     * 菜单点击事件
     * @param item
     * @param index
     * @param indexSub
     */
    menuClick(item, index, indexSub) {
      this.index = index;
      this.indexSub = indexSub;
      if (indexSub) this.jumpToPage(item.url);
      else
        this.checkHasChildren(item)
          ? this.showChildrenMenus(index)
          : this.jumpToPage(item.url);
    },
    /**
     * 检查是否有子菜单
     * @param item
     * @returns {boolean}
     */
    checkHasChildren(item) {
      return Array.isArray(item.children) && item.children.length > 0;
    },
    /**
     * 显示子菜单
     * @param index
     */
    showChildrenMenus(index) {
      this.menuList.forEach((item) => (item.showChildren = false));
      this.menuList[index].showChildren = true;
    },
    /**
     * 页面跳转
     * @param url
     */
    jumpToPage(url) {
      if (url) {
        this.$router.push(url);
        this.updateMenuFocus(this.index, this.indexSub);
      } else this.showToast("暂未开放，敬请期待~");
    },
    /**
     * 更新菜单选中效果
     */
    updateMenuFocus(index, indexSub) {
      this.menuList.forEach((item) => {
        item.focus = false;
        Array.isArray(item.children) &&
          item.children.forEach((itemSub) => (itemSub.focus = false));
      });
      if (indexSub) this.menuList[index].children[indexSub].focus = true;
      else this.menuList[index].focus = true;
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
  },
};
</script>

<style lang="less">
.sidebar_nav {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  border-radius: 10px;
  box-shadow: 0px 0px 10px 10px rgba(0, 0, 0, 0.1);
  overflow: hidden;

  .item {
    width: 100%;

    .title {
      width: 100%;
      text-align: center;
      padding: 10px 0px;
      border-bottom: 0.5px dashed rgba(0, 0, 0, 0.1);
      cursor: pointer;
      color: rgba(0, 0, 0, 0.5);

      &:hover {
        background-color: rgba(0, 0, 0, 0.1);
        color: rgba(0, 0, 0, 1);
      }

      &:active {
        color: #999999;
      }
    }

    .selected {
      background-color: rgba(0, 0, 0, 0.1);
    }

    &:last-child {
      border-bottom: 0px;
    }
  }
}
</style>