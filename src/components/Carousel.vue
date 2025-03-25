<template>
  <div class="carousel-container">
    <swiper
      :modules="modules"
      :slides-per-view="1"
      :space-between="50"
      :pagination="{ clickable: true }"
      :autoplay="{ delay: 3000 }"
      @swiper="onSwiper"
    >
      <swiper-slide v-for="(slide, index) in slides" :key="index">
        <img :src="slide.image" :alt="slide.title" class="slide-image">
        <div class="slide-caption">{{ slide.title }}</div>
      </swiper-slide>
    </swiper>
  </div>
</template>

<script>
import { Swiper, SwiperSlide } from 'swiper/vue'
import { Autoplay, Pagination } from 'swiper'
import 'swiper/css'
import 'swiper/css/pagination'

export default {
  name: 'ImageCarousel',
  components: { Swiper, SwiperSlide },
  setup() {
    return {
      modules: [Autoplay, Pagination],
      slides: [
  {
    image: require('@/assets/350.jpeg'), 
    title: '宠物健康饮食指南'
  },
  {
    image: require('@/assets/351.jpeg'),
    title: '最新宠物用品推荐'
  },
  {
    image: require('@/assets/352.jpeg'),
    title: '萌宠训练技巧分享'
  }
]
    }
  },
  methods: {
    onSwiper(swiper) {
      console.log(swiper)
    }
  }
}
</script>

<style scoped>

.text-container {
  width: 80%; /* 关键设置 */
  margin: 0 auto; /* 水平居中 */
  position: absolute;
  bottom: 20px;
  left: 0;
  right: 0;
}
.carousel-container {
 
  margin: 20px 0;
  z-index: 10;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.slide-image {
  width: 80%;
  height: 500px;
  object-fit: cover;
  
}

.slide-caption {
  width: 79%;
  margin: 0 auto; 
  position: absolute;
  bottom: 20px;
  left: 0;
  right: 0;
  text-align: center;
  color: white;
  font-size: 24px;
  font-weight: bold;
  text-shadow: 1px 1px 3px rgba(0, 0, 0, 0.8);
  padding: 10px;
  background: rgba(0, 0, 0, 0.3);
}

.swiper-pagination-bullet-active {
  background: #42b983 !important;
}
</style>