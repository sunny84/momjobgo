<template>

  <!-- role 속성은 웹 접근성에 필요할 경우 넣는 속성이므로 swiper의 필수값은 아닙니다 -->
  <swiper ref="filterSwiper" :options="swiperOption" role="tablist">
    <swiper-slide role="tab">111111</swiper-slide>
    <swiper-slide role="tab">222222</swiper-slide>
    <swiper-slide role="tab">333333</swiper-slide>
    <swiper-slide role="tab">444444</swiper-slide>
    <swiper-slide role="tab">555555</swiper-slide>
    <swiper-slide role="tab">666666</swiper-slide>
    <swiper-slide role="tab">777777</swiper-slide>
    <swiper-slide role="tab">888888</swiper-slide>
    <swiper-slide role="tab">999999</swiper-slide>
    <swiper-slide role="tab">101010101010</swiper-slide>
    <swiper-slide role="tab">111111111111</swiper-slide>
    <swiper-slide role="tab">121212121212</swiper-slide>
  </swiper>
</template>
<script>
import { Swiper, SwiperSlide, directive } from "vue-awesome-swiper";
import "swiper/css/swiper.css";

export default {
	name: "FilterSwiper",
	components:	{
       Swiper,
       SwiperSlide
	},
  directives: {
    swiper: directive
  },
  data () {
    const _vm = this
    return {
      swiperOption: {
        slidesPerView: 'auto',
        spaceBetween: 6, // swiper-slide 사이의 간격 지정
        slidesOffsetBefore: 0, // slidesOffsetBefore는 첫번째 슬라이드의 시작점에 대한 변경할 때 사용
        slidesOffsetAfter: 0, // slidesOffsetAfter는 마지막 슬라이드 시작점 + 마지막 슬라이드 너비에 해당하는 위치의 변경이 필요할 때 사용
        freeMode: true, // freeMode를 사용시 스크롤하는 느낌으로 구현 가능
        centerInsufficientSlides: true, // 컨텐츠의 수량에 따라 중앙정렬 여부를 결정함
        on: {
          click: function () {
            _vm.slideMoveTo()	
            _vm.activeTab()						
          },
          tap: function () {
            _vm.slideMoveTo()	
            _vm.activeTab()
          },
          resize: function () {
            this.allowTouchMove = !_vm.isOverview
          }
        }
      }
    }
  },
  methods: {
    swiperInit: function () {
       this.swiper.allowTouchMove = !this.isOverview
       this.activeTab()
    },
    activeTab: function (swiper) {
      swiper = swiper || this.swiper
      if (swiper.hasOwnProperty('clickedSlide') && !swiper.clickedSlide) return

      const slideSelector = `.${swiper.params.slideClass}`
      const selectedEl = swiper.clickedSlide || swiper.slides[swiper.params.initialSlide]
      const swiperArr = document.querySelectorAll(slideSelector)
      Array.from(swiperArr).forEach((el) => {
        el.setAttribute('aria-selected', 'false')
        selectedEl.setAttribute('aria-selected', 'true')
      })
    },
    slideMoveTo: function (swiper = this.swiper) {
      if (!swiper.clickedSlide) return

      const activeIndex = swiper.clickedIndex
      swiper.slideTo(activeIndex)
    }
  },
  computed: {
    swiper: function () {
      return this.$refs.filterSwiper.swiper
    },
    isOverview: function () {
      return window.innerWidth >= this.swiper.virtualSize
    }
  },
  mounted () {
    this.swiperInit()
  },
};
</script>

<style lang="scss" scoped>
.swiper-container {
  padding: 0 20px;
  &:before,
  &:after {
    display: block;
    position: absolute;
    top: 0;
    width: 20px;
    height: 100%;
    z-index: 10;
    content: "";
  }
  &:before {
    left: 0;
    background: linear-gradient(90deg, #fff -20.19%, rgba(255, 255, 255, 0.8) 18.31%, rgba(255, 255, 255, 0) 75%);
  }
  &:after {
    right: 0;
    background: linear-gradient(270deg, #fff -20.19%, rgba(255, 255, 255, 0.8) 18.31%, rgba(255, 255, 255, 0) 75%);
  }
  .swiper-wrapper {
    .swiper-slide {
      width: auto;
      min-width: 56px;
      padding: 0px 14px;
      font-size: 14px;
      line-height: 36px;
      text-align: center;
      color: #84868c;
      border: 0;
      border-radius: 18px;
      background: #f3f4f7;
      appearance: none;
      cursor: pointer;
      &[aria-selected="true"] {
        color: #fff;
        background: #000;
      }
    }
  }
}

</style>