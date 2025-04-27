<template>
  <div class="admin-layout">
    <nav class="admin-nav">
      <div class="logo-container">
        <div class="logo-text">宠之屋</div>
        <div class="logo-subtitle">宠物管理系统</div>
      </div>
      <ul class="nav-items">
        <li><router-link to="/admin/announcements">编辑公告</router-link></li>
        <li><router-link to="/admin/posts">审核帖子</router-link></li>
        <li><router-link to="/admin/pet-encyclopedia">编辑宠物百科</router-link></li>
        <li><router-link to="/admin/users">用户管理</router-link></li>
      </ul>
      <div class="admin-info">
        <span class="admin-name">管理员</span>
        <button class="logout-btn" @click="handleLogout">退出登录</button>
      </div>
    </nav>
    <main class="admin-content">
      <router-view></router-view>
    </main>
  </div>
</template>

<script>
export default {
  name: 'AdminLayout',
  methods: {
    handleLogout() {
      if (confirm('确定要退出登录吗？')) {
        // 清除管理员登录状态
        localStorage.removeItem('adminToken')
        // 跳转到登录页
        this.$router.push('/admin/login')
      }
    }
  }
}
</script>

<style scoped>
.admin-layout {
  min-height: 100vh;
  display: flex;
}

.admin-nav {
  width: 250px;
  background-color: #1a2b3c;
  color: white;
  padding: 20px 0;
  position: relative;
}

.logo-container {
  padding: 0 20px;
  margin-bottom: 30px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
  padding-bottom: 20px;
}

.logo-text {
  font-size: 28px;
  font-weight: bold;
  color: #fff;
  text-align: center;
  margin-bottom: 5px;
  letter-spacing: 2px;
}

.logo-subtitle {
  font-size: 14px;
  color: rgba(255, 255, 255, 0.7);
  text-align: center;
}

.nav-items {
  list-style: none;
  padding: 0;
  margin: 0;
}

.nav-items li {
  padding: 12px 20px;
}

.nav-items a {
  color: #ffffff;
  text-decoration: none;
  display: block;
  transition: all 0.3s;
}

.nav-items a:hover {
  background-color: #2c3e50;
  padding-left: 25px;
}

.admin-content {
  flex: 1;
  padding: 20px;
  background-color: #f5f6fa;
}

.admin-info {
  position: absolute;
  bottom: 20px;
  left: 0;
  right: 0;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
  border-top: 1px solid rgba(255, 255, 255, 0.1);
  padding-top: 20px;
}

.admin-name {
  color: white;
  font-size: 14px;
}

.logout-btn {
  background: none;
  border: 1px solid rgba(255, 255, 255, 0.5);
  color: white;
  padding: 6px 12px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
  transition: all 0.3s;
}

.logout-btn:hover {
  background: rgba(255, 255, 255, 0.1);
  border-color: white;
}
</style> 