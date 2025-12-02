import RichText from "@/components/RichText.vue";
import {createApp} from "vue";

export default {
    /**
     * 当前组件实例
     */
    instance: null,
    /**
     * 插件安装
     * @param app
     */
    install(app) {
        this.instance = this.renderDom();
        app.config.globalProperties.$richText = this.instance;
    },
    /**
     *
     * @param params
     * @returns {ComponentPublicInstance}
     */
    renderDom(params) {
        let domEl = window.document.createElement('div');
        domEl.id = RichText.name;
        window.document.body.appendChild(domEl);
      return  createApp(RichText, {...params}).mount(`#${RichText.name}`);
    },
}