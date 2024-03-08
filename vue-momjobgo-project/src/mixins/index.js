import axios from '@/plugins/axios'
import store from '@/store'
import Code from '@/utils/Code'

export default {

    data: () => ({
        ...Code
    }),

    methods: {
        /**
         * @param {*} url 
         * @param {*} method 
         * @param {*} data paramter 
         * @returns 
         */
        async $api(url, method, data, errorCallback) {

            return axios({
                headers: {
                    Authorization: `Bearer ${store.state.user.token}`
                },
                method: method
                , url
                , data
            }).catch(e => {
                if(e.response.status === Code.HTTP_UNAUTHORIZED 
                    && e.response?.data?.error 
                    && e.response.data.error === 'Invalid token'){

                    alert('로그인 정보가 만료되었습니다.'); 
                    store.dispatch('setToken', null);

                } else if(errorCallback){
                    errorCallback(e.response);
                } else if(e.response?.data?.error){
                    alert(e.response.data.error);
                }
            });
            
        },

        $loadKakaoMap(callback) {
            if (typeof kakao === 'undefined') {
                const script = document.createElement('script');
                script.onload = () => kakao.maps.load(callback);
                script.src = `//dapi.kakao.com/v2/maps/sdk.js?appkey=${process.env.VUE_APP_KAKAOMAP_KEY}&autoload=false&libraries=services`;
                document.head.appendChild(script);
            } else {
                callback();
            }
        },

    },

}

function toYmdHms(dateObj){

    const year = String(dateObj.getFullYear());
    const month = String(dateObj.getMonth() + 1).padStart(2,0);
    const date = String(dateObj.getDate()).padStart(2,0);
    const hour = String(dateObj.getHours()).padStart(2,0);
    const minute = String(dateObj.getMinutes()).padStart(2,0);
    const second = String(dateObj.getSeconds()).padStart(2,0);

    return `${year}-${month}-${date} ${hour}:${minute}:${second}`;
}