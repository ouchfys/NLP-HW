import ToastView from "@/components/ToastView.vue";
import {createApp} from "vue";

export default {
    /**
     * 插件安装
     * @param app
     */
    install(app) {
        app.config.globalProperties.$toast = this.createInstance();
    },
    /**
     * 创建实例
     */
    createInstance() {
        let domEl = window.document.createElement('div');
        domEl.id = ToastView.name;
        window.document.body.appendChild(domEl)
        return createApp(ToastView,).mount(`#${ToastView.name}`);
    },
}