<template>
  <nav class="navbar">
    <h1 class="logo">宠之屋</h1>
    <div class="nav-links">
      <router-link to="/">首页</router-link>
      <FosterDropdown />
      <router-link to="/pet-circle">宠物圈</router-link>
      <router-link to="/pet-encyclopedia">宠物百科</router-link>
    </div>
  </nav>
</template>

<script>
import FosterDropdown from './FosterDropdown.vue'

export default {
  components: {
    FosterDropdown
  }
}
</script>

<style scoped>
.navbar {
 
  display: flex;
  justify-content: center;
  overflow: visible !important;
  align-items: center;
  padding: 10px 20px;
  background-color: #FDEEEE;
  border-bottom: 1px solid #e7e7e7;
}
.logo {
  margin-right: auto; /* 把导航链接推到右边 */
  color: #95CFEF;
  font-size: 4rem; 
}

.nav-links {
  display: flex;
  gap: 120px; /* 导航链接间距 */
  position: absolute; /* 绝对定位 */
  left: 50%; /* 水平居中 */
  transform: translateX(-50%); /* 修正居中偏移 */
  font-size: 1.6rem; 
}

.nav-links a {
  text-decoration: none;
  color: #95CFEF;
  padding: 0.5rem;
}

.nav-links a:hover {
  color: #1378D4;
}

h1 {
  margin: 0;
  font-size: 1.5rem;
}
</style>