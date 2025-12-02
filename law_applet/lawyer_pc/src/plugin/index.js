import customComponents from '../components/Index';
import ToastView from "@/plugin/ToastView";
import LoadingView from '@/plugin/LoadingView';

/**
 * 全局插件 - 自定义
 */
export default {
    install(app){
        // 自定义组件
        app.use(customComponents);
        // toast 插件
        app.use(ToastView);
        // loading插件
        app.use(LoadingView);
        // // 医院选择插件
        // app.use(HospitalChoice);
        // // 可是选择插件
        // app.use(DepartmentChoose);
        // // 职称选择插件
        // app.use(TitleChoose);
        // // 文本编辑插件
        // app.use(RichText);
        // // 视频问诊插件
        // app.use(VideoOnline);
        // // 居民详情信息插件
        // app.use(InhabitantDetails);
        // // 问诊详情信息
        // app.use(InquiryDetails);
    }
}