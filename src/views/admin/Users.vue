<template>
  <div class="users">
    <h1>用户管理</h1>
    <div class="action-bar">
      <button class="btn-primary" @click="showAddDialog = true">添加用户</button>
      <div class="filters">
        <select v-model="userType" class="filter-select">
          <option value="all">所有用户</option>
          <option value="normal">普通用户</option>
          <option value="merchant">商户</option>
          <option value="banned">已封禁</option>
        </select>
        <div class="search-box">
          <input 
            type="text" 
            placeholder="搜索用户ID或用户名..." 
            v-model="searchQuery"
            @keyup.enter="handleSearch"
            class="search-input"
          >
          <button class="search-btn" @click="handleSearch({key: 'Enter'})">
            搜索
          </button>
        </div>
      </div>
    </div>
    <div class="users-list">
      <div class="empty-state" v-if="!filteredUsers.length">
        暂无用户数据
      </div>
      <table class="users-table" v-else>
        <thead>
          <tr>
            <th>用户ID</th>
            <th>用户名</th>
            <th>注册时间</th>
            <th>用户类型</th>
            <th>状态</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="user in filteredUsers" :key="user.id">
            <td>{{ user.id }}</td>
            <td>{{ user.username }}</td>
            <td>{{ user.registerDate }}</td>
            <td>{{ user.type === 'normal' ? '普通用户' : '商户' }}</td>
            <td>
              <span :class="['status-badge', user.status]">
                {{ user.status === 'active' ? '正常' : '已封禁' }}
              </span>
            </td>
            <td>
              <button class="btn-action" @click="editUser(user)">编辑</button>
              <button class="btn-action danger" @click="deleteUser(user.id)">
                {{ user.status === 'active' ? '封禁' : '解封' }}
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- 添加/编辑用户弹窗 -->
    <div class="dialog-overlay" v-if="showAddDialog" @click="showAddDialog = false">
      <div class="dialog-content" @click.stop>
        <h2>{{ isEditing ? '编辑用户' : '添加用户' }}</h2>
        <form @submit.prevent="submitUser">
          <div class="form-group">
            <label>用户名</label>
            <input 
              type="text" 
              v-model="formData.username" 
              placeholder="请输入用户名"
              required
            >
          </div>
          <div class="form-group">
            <label>密码</label>
            <input 
              type="password" 
              v-model="formData.password" 
              placeholder="请输入密码"
              :required="!isEditing"
            >
          </div>
          <div class="form-group">
            <label>确认密码</label>
            <input 
              type="password" 
              v-model="formData.confirmPassword" 
              placeholder="请再次输入密码"
              :required="!isEditing"
            >
          </div>
          <div class="form-group">
            <label>用户类型</label>
            <select v-model="formData.type" required>
              <option value="normal">普通用户</option>
              <option value="merchant">商户</option>
            </select>
          </div>
          <div class="form-group">
            <label>状态</label>
            <select v-model="formData.status" required>
              <option value="active">正常</option>
              <option value="banned">已封禁</option>
            </select>
          </div>
          <div class="form-group">
            <label>邮箱</label>
            <input 
              type="email" 
              v-model="formData.email" 
              placeholder="请输入邮箱"
              required
            >
          </div>
          <div class="form-group">
            <label>手机号</label>
            <input 
              type="tel" 
              v-model="formData.phone" 
              placeholder="请输入手机号"
              required
            >
          </div>
          <div class="form-group">
            <label>头像</label>
            <div class="avatar-upload">
              <div class="avatar-preview" v-if="imagePreview">
                <img :src="imagePreview" alt="头像预览">
                <button type="button" class="remove-avatar" @click="removeAvatar">×</button>
              </div>
              <div class="upload-box" v-else>
                <input 
                  type="file" 
                  accept="image/*"
                  @change="handleAvatarUpload"
                  class="file-input"
                  id="avatarUpload"
                >
                <label for="avatarUpload" class="upload-label">
                  <span class="upload-icon">+</span>
                  <span>点击上传头像</span>
                </label>
              </div>
              <div class="upload-tip">支持jpg、png格式，大小不超过1MB</div>
            </div>
          </div>
          <div class="dialog-actions">
            <button type="button" class="btn-secondary" @click="closeDialog">取消</button>
            <button type="submit" class="btn-primary">{{ isEditing ? '保存修改' : '添加用户' }}</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'UsersManagement',
  data() {
    return {
      userType: 'all',
      searchQuery: '',
      searchText: '',
      users: [],
      showAddDialog: false,
      isEditing: false,
      formData: {
        username: '',
        password: '',
        confirmPassword: '',
        type: 'normal',
        status: 'active',
        email: '',
        phone: '',
        avatar: ''
      },
      editingId: null,
      imagePreview: null,
      imageFile: null,
      currentUserId: 1
    }
  },
  computed: {
    filteredUsers() {
      let result = [...this.users]
      
      // 根据用户类型过滤
      if (this.userType !== 'all') {
        result = result.filter(user => user.type === this.userType)
      }
      
      // 根据搜索关键词过滤
      if (this.searchText) {
        const query = this.searchText.toLowerCase()
        result = result.filter(user => 
          user.username.toLowerCase().includes(query) ||
          user.id.toString().includes(query)
        )
      }
      
      return result
    }
  },
  methods: {
    handleSearch(event) {
      if (event.key === 'Enter') {
        this.searchText = this.searchQuery
      }
    },
    generateUserId() {
      this.currentUserId += 1
      return this.currentUserId.toString()
    },
    handleAvatarUpload(event) {
      const file = event.target.files[0]
      if (file) {
        if (!file.type.startsWith('image/')) {
          alert('请上传图片文件')
          return
        }
        
        if (file.size > 1024 * 1024) {
          alert('头像大小不能超过1MB')
          return
        }

        this.imageFile = file
        this.imagePreview = URL.createObjectURL(file)
        const reader = new FileReader()
        reader.onload = (e) => {
          this.formData.avatar = e.target.result
        }
        reader.readAsDataURL(file)
      }
    },
    removeAvatar() {
      this.imageFile = null
      this.imagePreview = null
      this.formData.avatar = ''
    },
    submitUser() {
      if (!this.isEditing && this.formData.password !== this.formData.confirmPassword) {
        alert('两次输入的密码不一致')
        return
      }

      const user = {
        ...this.formData,
        id: this.isEditing ? this.editingId : this.generateUserId(),
        registerDate: this.isEditing ? this.users.find(u => u.id === this.editingId)?.registerDate : new Date().toLocaleString()
      }

      delete user.confirmPassword

      if (this.isEditing) {
        const index = this.users.findIndex(item => item.id === this.editingId)
        if (index !== -1) {
          this.users[index] = user
        }
      } else {
        this.users.unshift(user)
      }

      this.closeDialog()
    },
    editUser(user) {
      this.isEditing = true
      this.editingId = user.id
      this.formData = {
        username: user.username,
        password: '',
        confirmPassword: '',
        type: user.type,
        status: user.status,
        email: user.email || '',
        phone: user.phone || '',
        avatar: user.avatar || ''
      }
      this.imagePreview = user.avatar || null
      this.showAddDialog = true
    },
    deleteUser(id) {
      const user = this.users.find(u => u.id === id)
      if (user) {
        const action = user.status === 'active' ? '封禁' : '解封'
        if (confirm(`确定要${action}该用户吗？`)) {
          user.status = user.status === 'active' ? 'banned' : 'active'
        }
      }
    },
    closeDialog() {
      this.showAddDialog = false
      this.isEditing = false
      this.formData = {
        username: '',
        password: '',
        confirmPassword: '',
        type: 'normal',
        status: 'active',
        email: '',
        phone: '',
        avatar: ''
      }
      this.imageFile = null
      this.imagePreview = null
      this.editingId = null
    }
  }
}
</script>

<style scoped>
.users {
  padding: 20px;
}

h1 {
  margin-bottom: 20px;
  color: #1a2b3c;
}

.action-bar {
  margin-bottom: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.filters {
  display: flex;
  gap: 15px;
  align-items: center;
}

.filter-select, .search-input {
  padding: 8px 12px;
  border: 1px solid #ddd;
  border-radius: 4px;
  background-color: white;
  color: #1a2b3c;
}

.search-input {
  width: 300px;
}

.users-table {
  width: 100%;
  border-collapse: collapse;
  background: white;
  border-radius: 4px;
  overflow: hidden;
}

.users-table th,
.users-table td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #eee;
}

.users-table th {
  background-color: #f8f9fa;
  font-weight: 600;
  color: #1a2b3c;
}

.status-badge {
  padding: 4px 8px;
  border-radius: 12px;
  font-size: 12px;
}

.status-badge.active {
  background-color: #e3fcef;
  color: #0a6c3b;
}

.status-badge.banned {
  background-color: #fee2e2;
  color: #991b1b;
}

.btn-action {
  padding: 6px 12px;
  border: 1px solid #1a2b3c;
  border-radius: 4px;
  background: white;
  color: #1a2b3c;
  cursor: pointer;
  margin-right: 8px;
  transition: all 0.3s;
}

.btn-action:hover {
  background: #1a2b3c;
  color: white;
}

.btn-action.danger {
  border-color: #dc2626;
  color: #dc2626;
}

.btn-action.danger:hover {
  background: #dc2626;
  color: white;
}

.btn-primary {
  background-color: #1a2b3c;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.btn-primary:hover {
  background-color: #2c3e50;
}

.btn-secondary {
  background-color: #e5e7eb;
  color: #1a2b3c;
  border: none;
  padding: 10px 20px;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.btn-secondary:hover {
  background-color: #d1d5db;
}

.empty-state {
  text-align: center;
  padding: 40px;
  color: #666;
  background: #f8f9fa;
  border-radius: 4px;
}

/* 弹窗样式 */
.dialog-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.dialog-content {
  background: white;
  padding: 24px;
  border-radius: 8px;
  width: 90%;
  max-width: 500px;
  max-height: 90vh;
  overflow-y: auto;
}

.dialog-content h2 {
  margin-bottom: 20px;
  color: #1a2b3c;
}

.form-group {
  margin-bottom: 16px;
}

.form-group label {
  display: block;
  margin-bottom: 8px;
  color: #1a2b3c;
  font-weight: 500;
}

.form-group input,
.form-group select {
  width: 100%;
  padding: 8px 12px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 14px;
}

.dialog-actions {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
  margin-top: 24px;
}

.search-box {
  display: flex;
  gap: 8px;
  align-items: center;
}

.search-input {
  width: 300px;
  padding: 8px 12px;
  border: 1px solid #ddd;
  border-radius: 4px;
  background-color: white;
  color: #1a2b3c;
}

.search-btn {
  padding: 8px 16px;
  background-color: #1a2b3c;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.search-btn:hover {
  background-color: #2c3e50;
}

.avatar-upload {
  margin-top: 8px;
}

.avatar-preview {
  position: relative;
  width: 100px;
  height: 100px;
  border: 2px dashed #ddd;
  border-radius: 50%;
  overflow: hidden;
}

.avatar-preview img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.remove-avatar {
  position: absolute;
  top: 4px;
  right: 4px;
  width: 20px;
  height: 20px;
  border-radius: 50%;
  background: rgba(0, 0, 0, 0.5);
  color: white;
  border: none;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 14px;
}

.upload-box {
  width: 100px;
  height: 100px;
  border: 2px dashed #ddd;
  border-radius: 50%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: border-color 0.3s;
}

.upload-box:hover {
  border-color: #1a2b3c;
}

.file-input {
  display: none;
}

.upload-label {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 4px;
  color: #666;
  font-size: 12px;
}

.upload-icon {
  font-size: 20px;
  color: #1a2b3c;
}

.upload-tip {
  margin-top: 8px;
  font-size: 12px;
  color: #666;
}
</style> 