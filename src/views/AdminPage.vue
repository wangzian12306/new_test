<template>
  <div class="admin-container">
    <!-- 导航栏 -->
    <nav class="admin-nav">
      <div class="nav-header">
        <h2>宠物管理系统 - 管理者面板</h2>
      </div>
      <ul class="nav-menu">
        <li v-for="item in navItems" :key="item.name" :class="{ active: activeTab === item.name }"
          @click="activeTab = item.name">
          <i :class="item.icon"></i>
          {{ item.label }}
        </li>
      </ul>
      <div class="nav-footer">
        <p>当前用户: {{ currentUser }}</p>
        <button @click="logout" class="logout-btn">退出登录</button>
      </div>
    </nav>

    <!-- 内容区域 -->
    <main class="admin-content">
      <!-- 编辑公告 -->
      <div v-if="activeTab === 'announcement'" class="content-section">
        <h3><i class="fas fa-bullhorn"></i> 编辑公告</h3>
        <textarea v-model="announcement" placeholder="输入系统公告内容..."></textarea>
        <div class="action-buttons">
          <button @click="saveAnnouncement" class="save-btn">保存公告</button>
          <button @click="previewAnnouncement" class="preview-btn">预览公告</button>
        </div>
      </div>

      <!-- 审核帖子 -->
      <div v-if="activeTab === 'post'" class="content-section">
        <h3><i class="fas fa-check-circle"></i> 审核帖子</h3>
        <div class="post-list">
          <div v-for="post in pendingPosts" :key="post.id" class="post-item">
            <div class="post-header">
              <span class="post-title">{{ post.title }}</span>
              <span class="post-author">作者: {{ post.author }}</span>
            </div>
            <div class="post-content">{{ post.content }}</div>
            <div class="post-actions">
              <button @click="approvePost(post.id)" class="approve-btn">通过</button>
              <button @click="rejectPost(post.id)" class="reject-btn">拒绝</button>
            </div>
          </div>
        </div>
      </div>

      <!-- 编辑宠物百科 -->
      <div v-if="activeTab === 'encyclopedia'" class="content-section">
        <h3><i class="fas fa-book"></i> 编辑宠物百科</h3>
        <div class="encyclopedia-actions">
          <button @click="showAddForm" class="add-btn">添加新条目</button>
          <input type="text" v-model="searchTerm" placeholder="搜索宠物百科..." class="search-input">
        </div>

        <!-- 添加/编辑表单 -->
        <div v-if="showForm" class="encyclopedia-form">
          <input type="text" v-model="currentEntry.title" placeholder="宠物名称" class="form-input">
          <select v-model="currentEntry.category" class="form-select">
            <option value="dog">犬类</option>
            <option value="cat">猫类</option>
            <option value="bird">鸟类</option>
            <option value="other">其他</option>
          </select>
          <textarea v-model="currentEntry.content" placeholder="宠物描述" class="form-textarea"></textarea>
          <div class="form-actions">
            <button @click="saveEntry" class="save-btn">保存</button>
            <button @click="cancelEdit" class="cancel-btn">取消</button>
          </div>
        </div>

        <!-- 百科条目列表 -->
        <div class="encyclopedia-list">
          <div v-for="entry in filteredEntries" :key="entry.id" class="encyclopedia-item">
            <div class="entry-header">
              <h4>{{ entry.title }}</h4>
              <span class="entry-category">{{ formatCategory(entry.category) }}</span>
            </div>
            <p class="entry-content">{{ entry.content }}</p>
            <div class="entry-actions">
              <button @click="editEntry(entry)" class="edit-btn">编辑</button>
              <button @click="deleteEntry(entry.id)" class="delete-btn">删除</button>
            </div>
          </div>
        </div>
      </div>

      <!-- 用户管理 -->
      <div v-if="activeTab === 'user'" class="content-section">
        <h3><i class="fas fa-users"></i> 用户管理</h3>
        <table class="user-table">
          <thead>
            <tr>
              <th>用户名</th>
              <th>邮箱</th>
              <th>注册时间</th>
              <th>角色</th>
              <th>状态</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="user in users" :key="user.id">
              <td>{{ user.username }}</td>
              <td>{{ user.email }}</td>
              <td>{{ formatDate(user.registerTime) }}</td>
              <td>{{ user.role }}</td>
              <td :class="{ 'active': user.active, 'inactive': !user.active }">
                {{ user.active ? '活跃' : '禁用' }}
              </td>
              <td>
                <button @click="toggleUserStatus(user.id)" :class="user.active ? 'disable-btn' : 'enable-btn'">
                  {{ user.active ? '禁用' : '启用' }}
                </button>
                <button @click="resetPassword(user.id)" class="reset-btn">重置密码</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </main>
  </div>
</template>

<script>
export default {
  name: 'AdminPage',
  data() {
    return {
      currentUser: '管理员',
      activeTab: 'announcement',
      navItems: [
        { name: 'announcement', label: '编辑公告', icon: 'fas fa-bullhorn' },
        { name: 'post', label: '审核帖子', icon: 'fas fa-check-circle' },
        { name: 'encyclopedia', label: '编辑宠物百科', icon: 'fas fa-book' },
        { name: 'user', label: '用户管理', icon: 'fas fa-users' }
      ],
      // 编辑公告数据
      announcement: '',
      // 审核帖子数据
      pendingPosts: [
        { id: 1, title: '如何照顾新生小狗', author: '用户A', content: '分享一些照顾新生小狗的经验...' },
        { id: 2, title: '猫咪饮食指南', author: '用户B', content: '介绍猫咪健康饮食的方法...' }
      ],
      // 宠物百科数据
      showForm: false,
      searchTerm: '',
      currentEntry: {
        id: null,
        title: '',
        category: 'dog',
        content: ''
      },
      encyclopediaEntries: [
        { id: 1, title: '金毛寻回犬', category: 'dog', content: '金毛寻回犬是一种大型犬种，性格温顺友善...' },
        { id: 2, title: '波斯猫', category: 'cat', content: '波斯猫是最古老的猫种之一，以其长而华丽的毛发闻名...' }
      ],
      // 用户管理数据
      users: [
        { id: 1, username: 'user1', email: 'user1@example.com', registerTime: '2023-01-15', role: '普通用户', active: true },
        { id: 2, username: 'user2', email: 'user2@example.com', registerTime: '2023-02-20', role: '普通用户', active: false },
        { id: 3, username: 'user3', email: 'user3@example.com', registerTime: '2023-03-10', role: 'VIP用户', active: true }
      ]
    };
  },
  computed: {
    filteredEntries() {
      if (!this.searchTerm) return this.encyclopediaEntries;
      return this.encyclopediaEntries.filter(entry =>
        entry.title.includes(this.searchTerm) ||
        entry.content.includes(this.searchTerm)
      );
    }
  },
  methods: {
    logout() {
      // 退出登录逻辑
      this.$router.push('/login');
    },
    saveAnnouncement() {
      // 保存公告逻辑
      alert('公告已保存');
    },
    previewAnnouncement() {
      // 预览公告逻辑
      alert(this.announcement || '暂无公告内容');
    },
    approvePost(postId) {
      // 通过帖子逻辑
      this.pendingPosts = this.pendingPosts.filter(post => post.id !== postId);
      alert(`帖子ID: ${postId} 已通过`);
    },
    rejectPost(postId) {
      // 拒绝帖子逻辑
      this.pendingPosts = this.pendingPosts.filter(post => post.id !== postId);
      alert(`帖子ID: ${postId} 已拒绝`);
    },
    showAddForm() {
      this.currentEntry = { id: null, title: '', category: 'dog', content: '' };
      this.showForm = true;
    },
    editEntry(entry) {
      this.currentEntry = { ...entry };
      this.showForm = true;
    },
    saveEntry() {
      if (this.currentEntry.id) {
        // 更新现有条目
        const index = this.encyclopediaEntries.findIndex(e => e.id === this.currentEntry.id);
        this.encyclopediaEntries.splice(index, 1, { ...this.currentEntry });
      } else {
        // 添加新条目
        const newId = Math.max(...this.encyclopediaEntries.map(e => e.id), 0) + 1;
        this.encyclopediaEntries.push({ ...this.currentEntry, id: newId });
      }
      this.showForm = false;
    },
    cancelEdit() {
      this.showForm = false;
    },
    deleteEntry(id) {
      if (confirm('确定要删除这个条目吗？')) {
        this.encyclopediaEntries = this.encyclopediaEntries.filter(entry => entry.id !== id);
      }
    },
    toggleUserStatus(userId) {
      const user = this.users.find(u => u.id === userId);
      if (user) {
        user.active = !user.active;
      }
    },
    resetPassword(userId) {
      // 重置密码逻辑
      alert(`用户ID: ${userId} 的密码已重置为默认密码`);
    },
    formatCategory(category) {
      const map = {
        dog: '犬类',
        cat: '猫类',
        bird: '鸟类',
        other: '其他'
      };
      return map[category] || category;
    },
    formatDate(dateString) {
      return new Date(dateString).toLocaleDateString();
    }
  }
};
</script>

<style scoped>
/* 墨蓝色主题 */
:root {
  --primary-dark: #1a2a3a;
  --primary: #2c3e50;
  --primary-light: #3d566e;
  --secondary: #3498db;
  --secondary-light: #5dade2;
  --text-light: #ecf0f1;
  --text-dark: #2c3e50;
  --success: #27ae60;
  --warning: #f39c12;
  --danger: #e74c3c;
  --gray: #95a5a6;
  --light-gray: #bdc3c7;
}

.admin-container {
  display: flex;
  min-height: 100vh;
  background-color: #f5f5f5;
}

/* 导航栏样式 */
.admin-nav {
  width: 250px;
  background-color: var(--primary-dark);
  color: var(--text-light);
  display: flex;
  flex-direction: column;
}

.nav-header {
  padding: 20px;
  background-color: var(--primary);
  border-bottom: 1px solid var(--primary-light);
}

.nav-header h2 {
  margin: 0;
  font-size: 1.2rem;
}

.nav-menu {
  list-style: none;
  padding: 0;
  margin: 0;
  flex-grow: 1;
}

.nav-menu li {
  padding: 15px 20px;
  cursor: pointer;
  transition: all 0.3s;
  border-left: 4px solid transparent;
}

.nav-menu li:hover {
  background-color: var(--primary-light);
}

.nav-menu li.active {
  background-color: var(--primary);
  border-left: 4px solid var(--secondary);
}

.nav-menu li i {
  margin-right: 10px;
  width: 20px;
  text-align: center;
}

.nav-footer {
  padding: 15px;
  background-color: var(--primary);
  border-top: 1px solid var(--primary-light);
}

.logout-btn {
  background-color: var(--danger);
  color: white;
  border: none;
  padding: 8px 15px;
  border-radius: 4px;
  cursor: pointer;
  margin-top: 10px;
  width: 100%;
}

.logout-btn:hover {
  background-color: #c0392b;
}

/* 内容区域样式 */
.admin-content {
  flex-grow: 1;
  padding: 20px;
  background-color: white;
  overflow-y: auto;
}

.content-section {
  background-color: white;
  padding: 20px;
  border-radius: 5px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;
}

.content-section h3 {
  color: var(--primary);
  border-bottom: 1px solid var(--light-gray);
  padding-bottom: 10px;
  margin-top: 0;
}

.content-section h3 i {
  margin-right: 10px;
}

/* 编辑公告样式 */
textarea {
  width: 100%;
  min-height: 150px;
  padding: 10px;
  border: 1px solid var(--light-gray);
  border-radius: 4px;
  margin: 15px 0;
  resize: vertical;
}

.action-buttons {
  display: flex;
  gap: 10px;
}

.save-btn,
.preview-btn,
.approve-btn,
.reject-btn,
.add-btn,
.edit-btn,
.delete-btn,
.enable-btn,
.disable-btn,
.reset-btn {
  padding: 8px 15px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-weight: bold;
  transition: all 0.3s;
}

.save-btn {
  background-color: var(--success);
  color: white;
}

.preview-btn {
  background-color: var(--secondary);
  color: white;
}

.approve-btn {
  background-color: var(--success);
  color: white;
}

.reject-btn {
  background-color: var(--danger);
  color: white;
}

.add-btn {
  background-color: var(--success);
  color: white;
  margin-right: 10px;
}

.edit-btn {
  background-color: var(--secondary);
  color: white;
  margin-right: 5px;
}

.delete-btn {
  background-color: var(--danger);
  color: white;
}

.enable-btn {
  background-color: var(--success);
  color: white;
  margin-right: 5px;
}

.disable-btn {
  background-color: var(--warning);
  color: white;
  margin-right: 5px;
}

.reset-btn {
  background-color: var(--secondary-light);
  color: white;
}

/* 审核帖子样式 */
.post-list {
  margin-top: 20px;
}

.post-item {
  border: 1px solid var(--light-gray);
  border-radius: 4px;
  padding: 15px;
  margin-bottom: 15px;
}

.post-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}

.post-title {
  font-weight: bold;
  color: var(--primary);
}

.post-author {
  color: var(--gray);
}

.post-content {
  color: var(--text-dark);
  margin-bottom: 10px;
}

.post-actions {
  display: flex;
  gap: 10px;
}

/* 宠物百科样式 */
.encyclopedia-actions {
  display: flex;
  justify-content: space-between;
  margin: 20px 0;
}

.search-input {
  padding: 8px 15px;
  border: 1px solid var(--light-gray);
  border-radius: 4px;
  width: 300px;
}

.encyclopedia-form {
  background-color: #f9f9f9;
  padding: 15px;
  border-radius: 4px;
  margin-bottom: 20px;
}

.form-input,
.form-select,
.form-textarea {
  width: 100%;
  padding: 8px 10px;
  margin-bottom: 10px;
  border: 1px solid var(--light-gray);
  border-radius: 4px;
}

.form-textarea {
  min-height: 100px;
  resize: vertical;
}

.form-actions {
  display: flex;
  gap: 10px;
}

.encyclopedia-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 20px;
}

.encyclopedia-item {
  border: 1px solid var(--light-gray);
  border-radius: 4px;
  padding: 15px;
  background-color: #f9f9f9;
}

.entry-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}

.entry-header h4 {
  margin: 0;
  color: var(--primary);
}

.entry-category {
  background-color: var(--secondary);
  color: white;
  padding: 2px 8px;
  border-radius: 10px;
  font-size: 0.8rem;
}

.entry-content {
  color: var(--text-dark);
  margin-bottom: 10px;
}

.entry-actions {
  display: flex;
  justify-content: flex-end;
}

/* 用户管理样式 */
.user-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

.user-table th,
.user-table td {
  padding: 12px 15px;
  text-align: left;
  border-bottom: 1px solid var(--light-gray);
}

.user-table th {
  background-color: var(--primary);
  color: white;
}

.user-table tr:hover {
  background-color: #f5f5f5;
}

.active {
  color: var(--success);
}

.inactive {
  color: var(--danger);
}
</style>