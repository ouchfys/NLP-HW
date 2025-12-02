import HeadNav from "@/components/HeadNav.vue";
import SidebarNav from "@/components/SidebarNav.vue";
import LoadingView from "@/components/LoadingView.vue";
import ToastView from "@/components/ToastView.vue";
// import HospitalChoice from "@/components/HospitalChoice.vue";
import RichText from "@/components/RichText.vue";
// import VideoOnline from '@/components/VideoOnline/VideoOnline.vue';
import InquiryDetails from "@/components/InquiryDetails.vue";


/**
 * 全局自定义组件
 */
export default {
    install(app) {
        app.component('HeadNav', HeadNav);
        app.component('SidebarNav', SidebarNav);
        app.component('LoadingView', LoadingView);
        app.component('ToastView', ToastView);
        // app.component('HospitalChoice', HospitalChoice);
        app.component('RichText', RichText);
        // app.component('VideoOnline', VideoOnline);
        app.component('InquiryDetails', InquiryDetails);
    }
}