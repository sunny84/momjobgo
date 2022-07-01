<!-- <template>
<div class="hello">
  <h1>{{msg}}</h1>
  <button v-on:click="getData1">getData1 </button> 
  <button v-on:click="getData2">getData2 </button> 
  <button v-on:click="getData3">getData3 </button> 
  <button v-on:click="postData1">postData1 </button> 
  <button v-on:click="deleteData1">deleteData1 </button> 
  <button v-on:click="KakaoLogin">KakaoLogin </button> 
</div>
</template>

<script>
import Vue from "vue";
import axios from "axios";

Vue.prototype.$http=axios;

export default{
  data: () => ({
    b1: '1',
    a: '3', 
    msg: 'Test start!!'
  }),
  

  methods:{
    getData1: function(){ //get url로 호출 :ok
      this.$http.get('http://localhost:8090/test/getData1')
          .then((result) => {
            console.log(result)
            this.msg = result.data
          }).catch(err => {
            console.log(err)
          })
          
    },

    getData2: function(){ //get url에 parameter로 호출 :ok
      this.$http.get('http://localhost:8090/test/getData2',{params:{tEst: this.b1 }})
          .then((result) => {
            console.log(result.data)
            this.msg = result.data
          }).catch(err => {
            console.log(err)
          })         
    }, 

    getData3: function(){
      var id=1;
      this.$http.get("http://localhost:8090/test/test/"+id)
          .then((response) => {
            console.log(response.data)
            //this.msg = response.data
          }).catch(err => {
            console.log(err)
          })        
    }, 

    postData1: function(){ //post('api주소',넘길값)로 작성하여 호출:ok
      //const fd = new FormData()
      //fd.append('demo',this.b1)
      // this.$http.post('http://localhost:8090/test/postData1',fd) 

       this.data = { //backend로 전송될 POST 데이터
				id:20
        ,name:"20etst"
        }

        this.$http.post('http://localhost:8090/test/test2',this.data)
          .then((result) => {
            console.log(result.data)
            this.msg = result.data
          }).catch(err => {
            console.log(err)
          })
    },

    deleteData1: function(){ //post('api주소',넘길값)로 작성하여 호출:ok
       
				var id=12;
        

        this.$http.delete('http://localhost:8090/test/test/'+id)
          .then((result) => {
            console.log(result.data)
            this.msg = result.data
          }).catch(err => {
            console.log(err)
          })
    },  
  },

  loginWithKakao() {
      console.log("loginWithKakao start");
      Kakao.Auth.login({
        success: function (authObj) {
          console.log(authObj);
          console.log("authObj:"+authObj);

          Kakao.API.request({
            url: "/v2/user/me",
            data:{
              property_keys:[
                'kakao_account.email'
              ]
            },
            success: function (response) {
              console.log(response);
              //console.log("response:"+response);
              Kakao.Auth.logout(function () {
                console.log(
                  Kakao.Auth.getAccessToken(),
                  "카카오 토큰 만료시킴."
                );
              });
            },
            fail: function (error) {
              console.log(error);
            },
          });
        },
        fail: function (err) {
          console.log(JSON.stringify(err));
        },
      });
    },

  mounted() {
    console.log("mounted start")
    if (typeof Kakao === "undefined") {
      console.log("001")
      const script = document.createElement("script");
      script.onload = () => {
        console.log("002")
        Kakao.init("1259143e223d59d6de3d44e96cbca60e");
        // SDK 초기화 여부를 판단합니다.
        console.log(Kakao.isInitialized()); //true
        console.log("003")
      };
      script.src = `https://developers.kakao.com/sdk/js/kakao.js`;
      console.log("004")
      document.head.appendChild(script);
      console.log("mounted end")
    }
  },
}

</script> -->

<template>
<div class="hello">
  <h1>{{msg}}</h1>
  <button v-on:click="getData1">getData1 </button> 
  <button v-on:click="getData2">getData2 </button> 
  <button v-on:click="getData3">getData3 </button> 
  <button v-on:click="postData1">postData1 </button> 
  <button v-on:click="deleteData1">deleteData1 </button> 
  <button v-on:click="KakaoLogin">KakaoLogin </button> 
</div>
</template>

<script>
import Vue from "vue";
import axios from "axios";

script.src = `https://developers.kakao.com/sdk/js/kakao.js`;

Kakao.init("1259143e223d59d6de3d44e96cbca60e");

methods: {
    kakaoLogin() {
      const params = {
        redirectUri:'http://localhost:8090/test/test2'
      }
      window.Kakao.Auth.authorize(params)
      
    }
  }

</script>
<style>
</style>