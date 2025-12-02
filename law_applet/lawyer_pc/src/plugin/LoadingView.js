import LoadingView from "@/components/LoadingView.vue";
import {createApp} from "vue";

export default {
    /**
     * 组件实例
     */
    instance: null,
    /**
     * 插件加载
     * @param app
     */
    install(app) {
        this.instance = this.renderLoadingDom()
        app.config.globalProperties.$loading = this.instance;
    },
    /**
     * 加载loadin框
     */
    renderLoadingDom() {
        let domEl = window.document.getElementById(LoadingView.name) || window.document.createElement('div');
        domEl.id = LoadingView.name;
        window.document.body.appendChild(domEl);
        return createApp(LoadingView).mount(`#${LoadingView.name}`);
    },
}