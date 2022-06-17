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
    loginWithKakao() {
      Kakao.Auth.login({
        success: function (authObj) {
          console.log(authObj);

          Kakao.API.request({
            url: "/v2/user/me",
            success: function (response) {
              console.log(response);
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
    if (typeof Kakao === "undefined") {
      const script = document.createElement("script");
      script.onload = () => {
        Kakao.init("1259143e223d59d6de3d44e96cbca60e");
        // SDK 초기화 여부를 판단합니다.
        console.log(Kakao.isInitialized());
      };
      script.src = `https://developers.kakao.com/sdk/js/kakao.js`;
      document.head.appendChild(script);
    }
  },
}
</script>

<style>

</style>