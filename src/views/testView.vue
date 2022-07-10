<template>
<div>
    <a id="custom-login-btn" @click="loginWithKakao">
      <img
        src="//k.kakaocdn.net/14/dn/btroDszwNrM/I6efHub1SN5KCJqLm1Ovx1/o.jpg"
        width="222"
        alt="카카오 로그인 버튼"
      />
    </a>

    <div>
      <p>파일업로드</p>
      <input type="file" name="file" id="file" />
      <button @click="upload">업로드</button>
    </div>
</div>
</template>

<script>
import axios from "axios";

export default {
  methods: {
  
    loginWithKakao() {//1
      console.log("loginWithKakao start");
      Kakao.Auth.login({//2
        success: function (authObj) {//3
          console.log(authObj);
          //console.log("authObj:"+authObj);
          console.log("-----------1----------");

          Kakao.API.request({//4
            url: "/v2/user/me",
             success: function (response) {//4

            //  const kakao_account = response.kakao_account;
            //             const userInfo = {
            //                 email : kakao_account.email,
            //                 password : '',
            //                 account_type : 2,
            //             }

              console.log("-----------3----------");
              console.log(response);
              console.log("response id:"+response.id);
              console.log("response access_token:"+response.access_token);
              console.log("response id_token:"+response.id_token);
            

              axios.post(`http://localhost:8090/user/login`,{
                             access_token: response.access_token
                         })
                         .then(res => {
                            console.log(res);
                            console.log("데이터베이스에 회원 정보가 있음!");
                         })
                         .catch(err => {
                             console.log(err);
                            console.log("데이터베이스에 회원 정보가 없음!");
                         })
                        //console.log(res);
                        alert("로그인 성공!");

              Kakao.Auth.logout(function() {//5
                console.log(
                  Kakao.Auth.getAccessToken(),
                  "카카오 토큰 만료시킴."
                );
              });//5

            },//4
            fail: function (error) {
              console.log(error);
            },
          });//4
        },//3
        fail: function (err) {
          console.log(JSON.stringify(err));
        },
      });//2
    },//1

    upload() {
      const formData = new FormData();
      const file = document.getElementById("file");

      formData.append("file", file.files[0]);
      axios
        // 파일업로드를 위해서는 API 서버를 켜야합니다.
        .post("http://localhost:8090/file/upload", formData, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
          params: {
            contentsId: 1,
          },
        })
        .then(function (res) {});
    },
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
</script>

<style>

</style>