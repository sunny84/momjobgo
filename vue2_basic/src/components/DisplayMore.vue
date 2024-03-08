<template>
    <article>
        <div class="h_area">
            <h2>Recent News</h2>
        </div>
        <span>{{news}}</span>
        <ul>
            <li v-for="(item, index) in news" :key="index">
                <a :href="item.url">
                    <dl>
                        <dt>[{{item.published_on}}] {{item.title}} </dt>
                        <dd>
                            <p>{{item.body}}</p>
                        </dd>
                    </dl>
                </a>
            </li>
        </ul>
        <button
            type="button"
            v-on:click="appendNews()"
            :disabled="this.dataFull === true"
            :class="{disabled : dataFull}"
        >
            More ({{cntNews}}/{{totNews}})
        </button>
    </article>

    <!-- <div class="wrap_select" id="more_list">
        <ul v-for="(box, index) in recipeBoxes.slice(0,3)" :key="index">
            <li class="menu fl"
                :class="{on : tempBoxId.includes(box.id)}"
                @click="callMoveRecipeBox(box.id)">
                {{ box.name }}
            </li>
        </ul>
        <div class="arr_right" @click="more('more_list',totalCnt)">더보기</div>
    </div> -->
</template>
<script>
import axios from "axios"
export default {
    // 데이터 객체
    data() {
        return {
            newsAll: {}, // 전체 뉴스 데이터
            news: {}, // 화면에 노출되는 뉴스 데이터
            totNews: 0, // 전체 뉴스 데이터 수
            cntNews: 5, // 화면에 노출할 뉴스 데이터 수 (초기 세팅 = 5)
            dataFull: false, // 전체 데이터보다 많은 데이터 호출 여부
            // moment: true,
            // totalCnt: 0,
            // tempBoxId: [],
            // recipeBoxes: [ 
            //     { id:1, name:'box1' },
            //     { id:2, name:'box2' },
            //     { id:3, name:'box3' },
            //     { id:4, name:'box4' },
            //     { id:5, name:'box5' },
            // ],
        }
    },
    // Vue 인스턴스에 추가할 메소드
    methods: {
        // more(id, cnt) {
        //     var list_length = $("#"+id+"li").length; // li 갯수 구하기
        //     var aname = id+"_btn";
        //     var callLength = list_length;

        //     $('#startCount').val(callLength);
        //     $('#viewCount').val(cnt);


        //     var listMore = document.arr_right.listMore.value;
        //     this.moreCnt++;
        // },
        // getMoreList(list){
        //     var content = "";
        //     var length = list.length;
        //     for(i=0;i<list.length;i++){

        //     }
        // },
    // 정보 처리
        bindNews() {
            this.newsAll = [{id: 1, url: 'http://localhost:8080', imageurl: '../assets/images/icon_back.png', body:'test1 code body', published_on : true, title: 'test1'},
            {id: 2, url: 'http://localhost:8080', imageurl: '../assets/images/icon_back.png', body:'test2 code body', published_on : true, title: 'test2'},
            {id: 3, url: 'http://localhost:8080', imageurl: '../assets/images/icon_back.png', body:'test3 code body', published_on : false, title: 'test3'},
            {id: 4, url: 'http://localhost:8080', imageurl: '../assets/images/icon_back.png', body:'test4 code body', published_on : true, title: 'test4'},
            {id: 5, url: 'http://localhost:8080', imageurl: '../assets/images/icon_back.png', body:'test5 code body', published_on : false, title: 'test5'},
            {id: 6, url: 'http://localhost:8080', imageurl: '../assets/images/icon_back.png', body:'test6 code body', published_on : true, title: 'test6'},
            {id: 7, url: 'http://localhost:8080', imageurl: '../assets/images/icon_back.png', body:'test7 code body', published_on : false, title: 'test7'},
            {id: 8, url: 'http://localhost:8080', imageurl: '../assets/images/icon_back.png', body:'test8 code body', published_on : true, title: 'test8'}];

            this.totNews = this.newsAll.length;
            let data = []
            for(var i=0;i<this.cntNews;i++){
                data.push(this.newsAll[i])
            }
            this.news = data;

            // axios.get(`http://localhost:8090/Recipe/0`, {
            // }).then(res=>{
            //     let data = []
            //     for(var i=0;i<this.cntNews;i++){
            //         data.push(res.data.Data[i])
            //     }
            
            //     this.newsAll = res.data.Data
            //     this.news = data
            //     this.totNews = this.newsAll.length

            //     //this.list = res.data;
            //     console.log(res.data);
            // }).catch(error=>{
            //     console.error(error);
            // })
        },
        appendNews(){
            // 전체 뉴스 개수보다 노출되는 뉴스 개수가 작은 경우
            if(this.cntNews < this.totNews){
                this.cntNews += 5 // 노출 뉴스 개수 5개 증가
                let data = []
                for(var i=0;i<this.cntNews;i++){
                    if(this.newsAll[i])
                        data.push(this.newsAll[i]) // 전체 뉴스에서 노출 뉴스 개수만큼 데이터 추출하여 data 배열에 추가
                    else
                        this.cntNews = i
                }
                this.news = data // news 객체에 data 배열 업데이트
            // 전체 뉴스 개수와 노출되는 뉴스 개수가 같으면
            }else{
                this.dataFull = true // dataFull 객체를 true 상태로 변경
                alert('List items are fully loaded!') // 모든 데이터 출력 알림
            }
        }
    },
    //컴포넌트, 템플릿, 렌더링된 돔에 접근할 수 있는 상태 (인스턴스 라이프사이클에 속함)
    mounted() {
        this.bindNews()
    },
    watch: {
        cntNews(newValue){
            console.log("cntNews:", newValue)
        },
        dataFull(newValue){
            console.log("dataFull:", newValue)
        },
        news(newValue){
            console.log("news:", newValue)
        }
    }
}
</script>
<style lang="scss" scoped>
// npm install --save-dev sass-loader sass webpack
button {
 position: fixed;
 left: 0;
 bottom: 0;
 width: 100%;
 margin: 0;
 padding: 20px 0 19px;
 border: 0;
 border-top: 2px solid #1b212f;
 background: #1d2943;
 color: #fff;
 font-size: 14px;
 cursor: pointer;
}
.disabled{
 border-top: 2px solid #e1e1e1;
 background:#e9e9e9;
 color:#777 
}
</style>