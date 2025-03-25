<template>
  <div class="foster">
    <h2>{{ currentType }}</h2>
    <!-- 这里可以添加寄养/领养/发布的内容 -->
  </div>
</template>

<script>
export default {
  name: 'FosterPage',  // 修改组件名
  computed: {
    currentType() {
      const type = this.$route.query.type
      if (type === 'foster') return '寄养'
      if (type === 'adopt') return '领养'
      if (type === 'publish') return '我要发布'
      return '寄养服务'
    }
  }
}
</script>

<style scoped>
.foster {
  padding: 20px;
}
</style>