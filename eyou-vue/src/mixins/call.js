import { mapGetters } from "vuex";
import axios from "axios";

const API_SERVER = "http://m-api.devcury.kr";
// const API_SERVER = "http://localhost:8090";

const handleError = (e, errorCallback) => {
  if (
    e.response.status === this.HTTP_UNAUTHORIZED &&
    e.response?.data?.error &&
    e.response.data.error === "Invalid token"
  ) {
    alert("로그인 정보가 만료되었습니다.");

    // 토큰을 비워준다.
    store.dispatch("user/setToken", null);
  } else if (errorCallback) {
    errorCallback(e.response);
  } else if (e.response?.data?.error) {
    console.log(e.response.data.error);
    alert(e.response.data.error);
  }
  return e.response;
};

export default {
  methods: {
    getHeaders(headers) {
      return this.token
        ? {
            Authorization: `Bearer ${this.token}`,
            ...headers,
          }
        : {
            ...headers,
          };
    },

    async $POST(url, body, params, headers, errorCallback) {
      return axios({
        headers: this.getHeaders(headers),
        method: "POST",
        url: `${API_SERVER}${url}`,
        data: body,
        params: params,
      }).catch((e) => {
        handleError(e, errorCallback);
      });
    },

    async $GET(url, params, headers, errorCallback) {
      return axios({
        headers: this.getHeaders(headers),
        method: "GET",
        url: `${API_SERVER}${url}`,
        data: body,
        params: params,
      }).catch((e) => {
        handleError(e, errorCallback);
      });
    },

    async $DELETE(url, params, headers, errorCallback) {
      return axios({
        headers: this.getHeaders(headers),
        method: "DELETE",
        url: `${API_SERVER}${url}`,
        data: body,
        params: params,
      }).catch((e) => {
        handleError(e, errorCallback);
      });
    },

    async $PATCH(url, body, params, headers, errorCallback) {
      return axios({
        headers: this.getHeaders(headers),
        method: "PATCH",
        url: `${API_SERVER}${url}`,
        data: body,
        params: params,
      }).catch((e) => {
        handleError(e, errorCallback);
      });
    },
  },

  computed: {
    ...mapGetters("user", ["token"]),
    $API_SERVER() {
      return API_SERVER;
    },
  },
};
