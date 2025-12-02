import { createApp } from 'vue'
import App from './App.vue'
import $CustomStore from './store/index';
import $CustomPlugin from "@/plugin/index";
import $router from './router/index';
import $utils from "@/commons/utils";
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/es/locale/lang/zh-cn';
import { createI18n } from 'vue-i18n';

// 配置 i18n 选项
const i18n = createI18n({
  locale: 'zh-CN', // 设置语言环境
  fallbackLocale: 'en', // 设置备用语言环境
  messages: {
    'zh-CN': zhCn,
    // 可以在这里添加其他语言的配置 ...
  },
});

/**
 * 创建应用并挂载
 */

let app = createApp(App)
app.use(ElementPlus, {
  locale: zhCn, // 设置 ElementPlus 组件库使用中文
});
app.use(i18n);
app.use($CustomStore);
app.use($CustomPlugin);
app.use($router);
app.mount('#app');

/**
 * 初始化工具包
 */
$utils.init(app);
const debounce = (fn, delay) => {
  let timer = null;
  return function () {
    let context = this;
    let args = arguments;
    clearTimeout(timer);
    timer = setTimeout(function () {
      fn.apply(context, args);
    }, delay);
  }
}

const _ResizeObserver = window.ResizeObserver;
window.ResizeObserver = class ResizeObserver extends _ResizeObserver {
  constructor(callback) {
    callback = debounce(callback, 16);
    super(callback);
  }
}


