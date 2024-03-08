<template>
    <!-- <header>header</header> -->
    <main>
        <h1>main</h1>
        <!-- <div class="wrap"
            v-for="num in numbers"
            :key="num"> -->
        <section class="s1">
            <div class="box">
                <div class="box-index">{{ numbers[0] }}</div>
                <div class="box-name box-name-basic">box {{ numbers[0] }}</div>
            </div>
            <div class="image image-basic">
                <div class="four-images"><img class="image-thumbnail" src="../assets/logo.png"/></div>
                <div class="four-images"><img class="image-thumbnail" src="../assets/logo.png"/></div>
                <div class="four-images"><img class="image-thumbnail" src="../assets/logo.png"/></div>
                <div class="four-images"><img class="image-thumbnail" src="../assets/logo.png"/></div>
            </div>
            <!-- <div class="text">section1 {{ num }}</div> -->
        </section>
        <section class="s2">
            <div class="box">
                <div class="box-index">{{ numbers[1] }}</div>
                <div class="box-name">box {{ numbers[1] }}</div>
                <div class="box-new"></div>
            </div>
            <div class="image"><img class="image-thumbnail" src="../assets/logo.png"/></div>
            <!-- <div class="text">section2</div> -->
        </section>
        <section class="s3">
            <div class="box">
                <div class="box-index">{{ numbers[2] }}</div>
                <div class="box-name">box {{ numbers[2] }}</div>
            </div>
            <div class="image"><img class="image-thumbnail" src="../assets/logo.png"/></div>
            <!-- <div class="text">section3</div> -->
        </section>
        <section class="s4">
            <div class="box">
                <div class="box-index">{{ numbers[3] }}</div>
                <div class="box-name">box {{ numbers[3] }}</div>
                <div class="box-new"></div>
            </div>
            <div class="image"><img class="image-thumbnail" src="../assets/logo.png"/></div>
            <!-- <div class="text">section4</div> -->
        </section>
        <section class="s5">
            <div class="box">
                <div class="box-index box-index-none"></div>
                <div class="box-name box-name-dashed">box {{ numbers[4] }}</div>
            </div>
            <div class="image image-dashed"><img class="image-thumbnail" src="../assets/logo.png"/></div>
            <!-- <div class="text">section5</div> -->
        </section>
        <!-- </div> -->
    </main>
    <!-- <article>FAQ</article> -->
    <!-- <footer>footer</footer> -->
</template>
<script>
export default {
  data() {
    return {
      numbers: [ 1, 2, 3, 4, 5 ],
      images: [
        "../assets/logo.png",
        "../assets/logo.png",
        "../assets/logo.png",
      ]
    }    
  },
}
</script>
<style scoped>
.s1 {
    position:absolute;
    top:150px;
    left:20px;
    width:250px;
    height:250px;
}
.s2 {
    position:absolute;
    top:150px;
    left:302px;
    width:500px;
    height:500px;
}
.s3 {
    position:absolute;
    top: 450px; 
    left: 20px;
    width:500px;
    height:500px;
}
.s4 {
    position:absolute;
    top: 450px;
    left: 302px;
    width:500px;
    height:500px;
}
.s5 {
    position:absolute;
    top: 750px;
    left: 20px;
    width:500px;
    height:500px;
}

div .box {
    text-align: center;
}

div .box-index {
    width: 25px;
    height: 25px;
    background-color: gray;
    color: #fff;
    font-weight: bolder;
    text-align:center;
    border-radius: 3ch 3ch;
    float:left;
}
div .box-index:hover {
    width: 25px;
    height: 25px;
    background-color: orange;
    color: #fff;
    font-weight: bolder;
    text-align:center;
    border-radius: 3ch 3ch;
    float:left;
}
div .box-index-none {
    width: 25px;
    height: 25px;
    background-color: #fff;
    color: #fff;
    font-weight: bolder;
    text-align:center;
    border-radius: 3ch 3ch;
    float:left;
}
div .box-name {
    width: 100px;
    height: 31px;
    border: 0.1em solid #d3d3d3;
    text-align:center;
    border-radius: 1ch 1ch 0 0;
    display: table-caption;
    float:left;
}
div .box-name-basic {
    width: 100px;
    height: 28px;
    border: 0.3em solid #d3d3d3;
    text-align:center;
    border-radius: 1ch 1ch 0 0;
    display: table-caption;
    float:left;
}
div .box-name-dashed {
    width: 100px;
    height: 31px;
    border: 0.1em solid #008d62;
    text-align:center;
    border-radius: 1ch 1ch 0 0;
    display: table-caption;
    float:left;
}
div .box-name:hover {
    width: 100px;
    height: 30px;
    border: solid orange;
    text-align:center;
    border-radius: 1ch 1ch 0 0;
    display: table-caption;
    float:left;
}
div .box-new {
    width: 50px;
    height: 25px;
    background-image: url(../assets/new.png);
    background-repeat: no-repeat;
    background-position: left bottom;
    background-size: 100%;
    float:left;
}

.image {
    position:absolute;
    top:33px;
    width: 250px;
    height: 250px;
    border: 0.1em solid #d3d3d3;
    text-align:center;
    border-radius: 0 2ch 2ch 2ch;
    box-shadow: 0 0.2em 0.2em gray;
}
.image-basic {
    position:absolute;
    top:33px;
    width: 250px;
    height: 250px;
    border: 0.3em solid #d3d3d3;
    text-align:center;
    border-radius: 0 2ch 2ch 2ch;
    box-shadow: 0 0.2em 0.2em gray;
}

.image:hover {
    width: 250px;
    height: 250px;
    border: solid orange;
    text-align:center;
    border-radius: 0 2ch 2ch 2ch;
    box-shadow: 0px 8px 8px gray;
}

.image-dashed {
    width: 250px;
    height: 250px;
    border: 0.2em dashed #008d62;
    text-align:center;
    border-radius: 2ch 2ch 2ch 2ch;
    box-shadow: 0px 8px 8px gray;
}

div .four-images {
    position:absolute;
    top:0;
    left:0;
    right:50%;
    bottom:50%;
    background-color:red;
    border-radius: 1ch 1ch;
    margin:3px 3px;
}

.four-images:nth-of-type(2) {
    top:0;
    left:50%;
    right:0;
    bottom:50%;
    background-color:blue;
}

.four-images:nth-of-type(3) {
    top:50%;
    left:0;
    right:50%;
    bottom:0;
    background-color:green;
}

.four-images:nth-of-type(4) {
    top:50%;
    left:50%;
    right:0;
    bottom:0;
    background-color:gold;
}

.image-box {
    width:380px;
    height:220px;
    /* overflow:hidden; */
    margin:0 auto;
}

.image-thumbnail {
    width: 100%;
    height: 100%;
    object-fit: cover;
    border-radius: 3ch;
}

div .text {
    position:absolute;
    top:0;
    left:0;
    right:50%;
    bottom:50%;
    background-color: #ffffff;
    opacity: 0.5;
    /* border: solid gray; */
    border-radius: 0ch 1ch 1ch;
}
</style>