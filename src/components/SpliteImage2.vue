<template>
    <!-- <header>header</header> -->
    <main>
        <h1>main</h1>
        <section 
            :class="s1"
            v-for="num in numbers"
            :key="num"
        >
            <div class="image">
                <div class="four-images"><img class="image-thumbnail" src="../assets/logo.png"/></div>
                <div class="four-images"><img class="image-thumbnail" src="../assets/logo.png"/></div>
                <div class="four-images"><img class="image-thumbnail" src="../assets/logo.png"/></div>
                <div class="four-images"><img class="image-thumbnail" src="../assets/logo.png"/></div>
            </div>
            <div class="text">section {{num}}</div>
        </section>
        <section class="s2">
            <div class="image"><img class="image-thumbnail" src="../assets/logo.png"/></div>
            <div class="text">section2</div>
        </section>
        <section class="s3">
            <div class="image"><img class="image-thumbnail" src="../assets/logo.png"/></div>
            <div class="text">section3</div>
        </section>
        <section class="s4">
            <div class="image"><img class="image-thumbnail" src="../assets/logo.png"/></div>
            <div class="text">section4</div>
        </section>
        <section class="s5">
            <div class="image"><img class="image-thumbnail" src="../assets/logo.png"/></div>
            <div class="text">section5</div>
        </section>
    </main>
    <!-- <article>FAQ</article> -->
    <!-- <footer>footer</footer> -->
</template>
<script>
export default {
  data() {
    return {
      numbers: [ 1, 2, 3, 4 ],
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
    top:420px; /* +270 */
    left:20px;
    width:500px;
    height:500px;
}
.s3 {
    position:absolute;
    top:690px; /* +270 */
    left:20px;
    width:500px;
    height:500px;
}
.s4 {
    position:absolute;
    top:960px; /* +270 */
    left:20px;
    width:500px;
    height:500px;
}
.s5 {
    position:absolute;
    top:1230px; /* +270 */
    left:20px;
    width:500px;
    height:500px;
}

div .four-images {
    position:absolute;
    top:0;
    left:0;
    right:50%;
    bottom:50%;
    background-color:red;
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

/* .image {
    width: 250px;
    height: 250px;
    background-image: url(../assets/logo.png);
    background-size: contain;
    background-repeat: no-repeat;
    background-position: center;
    border:1px solid black;
    text-align:center
} */
.image {
    width: 250px;
    height: 250px;
    border:1px solid black;
    text-align:center;
    /* margin:0 auto; */
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
}

div .text {
    position:absolute;
    top:0;
    left:0;
    right:50%;
    bottom:50%;
    background-color: #ffffff;
    opacity: 0.5;
}
</style>