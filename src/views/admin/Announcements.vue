<template>
  <div class="announcements">
    <h1>公告管理</h1>
    <div class="action-bar">
      <button class="btn-primary" @click="showAddDialog = true">发布新公告</button>
      <div class="search-box">
        <input 
          type="text" 
          placeholder="搜索公告标题..." 
          v-model="searchQuery"
          @keyup.enter="handleSearch"
          class="search-input"
        >
        <button class="search-btn" @click="handleSearch({key: 'Enter'})">
          搜索
        </button>
      </div>
    </div>
    <div class="announcements-list">
      <!-- 公告列表 -->
      <div v-if="filteredAnnouncements.length" class="announcement-items">
        <div v-for="item in filteredAnnouncements" :key="item.id" class="announcement-item">
          <div class="announcement-header">
            <h3>{{ item.title }}</h3>
            <span class="announcement-date">{{ item.publishTime }}</span>
          </div>
          <p class="announcement-content">{{ item.content }}</p>
          <div class="announcement-actions">
            <button class="btn-text" @click="editAnnouncement(item)">编辑</button>
            <button class="btn-text danger" @click="deleteAnnouncement(item.id)">删除</button>
          </div>
        </div>
      </div>
      <div class="empty-state" v-else>
        暂无公告
      </div>
    </div>

    <!-- 发布新公告弹窗 -->
    <div class="dialog-overlay" v-if="showAddDialog" @click="showAddDialog = false">
      <div class="dialog-content" @click.stop>
        <h2>{{ isEditing ? '编辑公告' : '发布新公告' }}</h2>
        <form @submit.prevent="submitAnnouncement">
          <div class="form-group">
            <label>标题</label>
            <input 
              type="text" 
              v-model="formData.title" 
              placeholder="请输入公告标题"
              required
            >
          </div>
          <div class="form-group">
            <label>内容</label>
            <textarea 
              v-model="formData.content" 
              placeholder="请输入公告内容"
              rows="6"
              required
            ></textarea>
          </div>
          <div class="dialog-actions">
            <button type="button" class="btn-secondary" @click="closeDialog">取消</button>
            <button type="submit" class="btn-primary">{{ isEditing ? '保存修改' : '发布公告' }}</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'AnnouncementsManagement',
  data() {
    return {
      announcements: [],
      showAddDialog: false,
      isEditing: false,
      searchQuery: '',
      searchText: '',
      formData: {
        title: '',
        content: ''
      },
      editingId: null
    }
  },
  computed: {
    filteredAnnouncements() {
      if (!this.searchText) {
        return this.announcements
      }
      const query = this.searchText.toLowerCase()
      return this.announcements.filter(announcement => 
        announcement.title.toLowerCase().includes(query)
      )
    }
  },
  methods: {
    handleSearch(event) {
      if (event.key === 'Enter') {
        this.searchText = this.searchQuery
      }
    },
    submitAnnouncement() {
      const announcement = {
        ...this.formData,
        id: this.isEditing ? this.editingId : Date.now(),
        publishTime: this.isEditing ? this.announcements.find(a => a.id === this.editingId)?.publishTime : new Date().toLocaleString()
      }

      if (this.isEditing) {
        const index = this.announcements.findIndex(item => item.id === this.editingId)
        if (index !== -1) {
          this.announcements[index] = announcement
        }
      } else {
        this.announcements.unshift(announcement)
      }

      this.closeDialog()
    },
    editAnnouncement(announcement) {
      this.isEditing = true
      this.editingId = announcement.id
      this.formData = {
        title: announcement.title,
        content: announcement.content
      }
      this.showAddDialog = true
    },
    deleteAnnouncement(id) {
      if (confirm('确定要删除这条公告吗？')) {
        this.announcements = this.announcements.filter(item => item.id !== id)
      }
    },
    closeDialog() {
      this.showAddDialog = false
      this.isEditing = false
      this.formData = {
        title: '',
        content: ''
      }
      this.editingId = null
    }
  }
}
</script>

<style scoped>
.announcements {
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

.btn-text {
  background: none;
  border: none;
  color: #1a2b3c;
  cursor: pointer;
  padding: 4px 8px;
  margin-right: 8px;
}

.btn-text.danger {
  color: #dc2626;
}

.btn-text:hover {
  text-decoration: underline;
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
.form-group textarea,
.form-group select {
  width: 100%;
  padding: 8px 12px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 14px;
}

.form-group textarea {
  resize: vertical;
}

.dialog-actions {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
  margin-top: 24px;
}

/* 公告列表样式 */
.announcement-items {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.announcement-item {
  background: white;
  padding: 16px;
  border-radius: 8px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.announcement-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8px;
}

.announcement-header h3 {
  margin: 0;
  color: #1a2b3c;
}

.announcement-date {
  color: #666;
  font-size: 14px;
}

.announcement-content {
  color: #4b5563;
  margin-bottom: 12px;
  line-height: 1.5;
}

.announcement-actions {
  display: flex;
  justify-content: flex-end;
  border-top: 1px solid #eee;
  padding-top: 12px;
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
</style> 