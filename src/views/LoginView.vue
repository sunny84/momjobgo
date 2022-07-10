<template>
  <div class="hello">
    <a id="custom-login-btn" @click="loginWithKakao">
      <img
        src="//k.kakaocdn.net/14/dn/btroDszwNrM/I6efHub1SN5KCJqLm1Ovx1/o.jpg"
        width="222"
        alt="카카오 로그인 버튼"
      />      
    </a>
  </div>
</template>

<script>
import Vue from "vue";
import axios from "axios";

import { mapActions, mapGetters } from "vuex"


export default {
  name: 'HelloWorld',
  props: {
    msg: String
  },

  computed : {  
    ...mapGetters('user', ['snsId', 'token'])
  },

  methods : {
    ...mapActions('user', ['setToken', 'setsnsId']),

    loginWithKakao() {
      //로그인
      const _this = this;

      console.log("this", this);

      Kakao.Auth.login({
        success: function(authObj) {
          console.log(authObj)
          //사용자정보 가져오기
          Kakao.API.request({
              url: '/v2/user/me', //계정 정보를 가져오는 request url             
              success: function(response) {
                console.log(response)
                console.log("response.id:"+response.id);
                let KsnsId = 'K'+response.id; 
                console.log("KsnsId:"+KsnsId);                 
                  // let kid=response.id //카카오 계정 정보
                  // console.log("kid:"+kid);
                this.data = { //backend로 전송될 POST 데이터
				          snsId:KsnsId
                }
                  axios.post('http://localhost:8090/user/login', this.data)
                  .then((response) => {
                    console.log("response", response);
                  console.log(response.data)

                if(response?.status === _this.HTTP_OK){
                    const token = response.data.token;
                    console.log("this (in)", this);
                    console.log("token: "+token)
                    _this.setToken(token);
                    // location.href=this.basePath;
                }   
                  
                  }).catch(err => {
                    console.log(err)
                  })
              },
              fail: function(error) {
                  console.log(error);
              }
          });
        },
        fail: function(err) {
          console.log(JSON.stringify(err))
        },
      })
    },
  },




  mounted() {
    if (typeof Kakao === 'undefined') {
      const script = document.createElement('script');
      script.onload = () => {
        ////로그인할때 sdk 초기화함/사용할 앱의 javascript key로 설정
        Kakao.init('c45a020ee3dc6a62a6971ee87e00d20b');
        // Kakao.init('1259143e223d59d6de3d44e96cbca60e');
        // SDK 초기화 여부를 판단합니다.
        console.log(Kakao.isInitialized()); //true
        console.log("003")
      };
      script.src = `https://developers.kakao.com/sdk/js/kakao.js`; //사용자 인증, 로그인, 로그아웃 등의 기능을 사용가능하도록함
      document.head.appendChild(script);
      console.log("mounted end")
    }
  }
}
</script>
