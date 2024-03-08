<template>
  <div class="about">
    <h1>사칙연산계산기 사용방법</h1>
    <h4>두 수를 입력한 후 연산버튼(<button>더하기</button><button>빼기</button><button>곱하기</button><button>나누기</button>)을 클릭하면 입력한 두 수 사이에 산술연산자가 표시되고 계산된 결과를 출력합니다.</h4>
    <img src="calc_ret.png"/>
  </div>
</template>

<script>
export default {

}
</script>
<style scoped>
h1 {
  margin-bottom: 30px;
}
button {
    width: 80px;
    color: #fff;
    background-color: midnightblue;
    border-radius: 5px;    
    margin: 0;
    margin-left: 10px;
    padding: 5px 15px;
}

img {
  width: 400px;
  margin-top: 20px;
  margin-left: 10px;
  margin-bottom: 20px;
}
</style>