<template>
  <div class="home-page">
    <!-- 导航标题 -->
    <header class="app-header">
     
    </header>

    <!-- 分隔线 -->
    <hr class="divider">

    <!-- 轮播图 -->
    <Carousel />

    <!-- 分隔线 -->
    <hr class="divider">

    <!-- 热门帖子区域 -->
    <section class="hot-posts">
      <h2>热门帖子</h2>
      <div class="post-grid">
        
        <div 
        v-for="(post, index) in posts" 
        :key="index"
        class="post-card"
        @click="goToPetCircle" 
      >
          {{ post }}
        </div>
      </div>
    </section>
  </div>
</template>

<script>
import Carousel from '@/components/Carousel.vue'

export default {
  name: 'HomePage',
  components: { Carousel },
  methods: {
    goToPetCircle() {
      this.$router.push('/pet-circle').catch(err => {
        console.error('跳转失败:', err)
      }); // 跳转到宠物圈
    }
  },
  data() {
    return {
      posts: [
        "123456755195654163",
        "123456755195654163",
        "123456755195654163",
        "123456755195654163"
      ]
    }
  }
}
</script>

<!-- 原有样式保持不变 -->