<template>
  <div class="pet-encyclopedia">
    <h1>宠物百科管理</h1>
    <div class="action-bar">
      <button class="btn-primary" @click="showAddDialog = true">添加新条目</button>
      <div class="search-box">
        <input type="text" placeholder="搜索宠物百科..." v-model="searchQuery">
      </div>
    </div>
    <div class="encyclopedia-list">
      <div v-if="filteredEntries.length" class="entry-items">
        <div v-for="entry in filteredEntries" :key="entry.id" class="entry-item">
          <div class="entry-header">
            <h3>{{ entry.name }}</h3>
            <span class="entry-category">{{ entry.category }}</span>
          </div>
          <div class="entry-preview">
            <div class="entry-image" v-if="entry.image">
              <img :src="entry.image" :alt="entry.name">
            </div>
            <div class="entry-info">
              <p class="entry-brief">{{ entry.brief }}</p>
              <div class="entry-tags">
                <span v-for="tag in entry.tags" :key="tag" class="tag">{{ tag }}</span>
              </div>
            </div>
          </div>
          <div class="entry-actions">
            <button class="btn-text" @click="editEntry(entry)">编辑</button>
            <button class="btn-text danger" @click="deleteEntry(entry.id)">删除</button>
          </div>
        </div>
      </div>
      <div class="empty-state" v-else>
        暂无百科条目
      </div>
    </div>

    <!-- 添加/编辑条目弹窗 -->
    <div class="dialog-overlay" v-if="showAddDialog" @click="showAddDialog = false">
      <div class="dialog-content" @click.stop>
        <h2>{{ isEditing ? '编辑百科条目' : '添加新条目' }}</h2>
        <form @submit.prevent="submitEntry">
          <div class="form-group">
            <label>宠物名称</label>
            <input 
              type="text" 
              v-model="formData.name" 
              placeholder="请输入宠物名称"
              required
            >
          </div>
          <div class="form-group">
            <label>所属类目</label>
            <select v-model="formData.category" required>
              <option value="">请选择类目</option>
              <option value="狗狗">狗狗</option>
              <option value="猫猫">猫猫</option>
              <option value="小宠">小宠</option>
              <option value="水族">水族</option>
              <option value="鸟类">鸟类</option>
              <option value="爬虫">爬虫</option>
              <option value="其他">其他</option>
            </select>
          </div>
          <div class="form-group">
            <label>简介</label>
            <textarea 
              v-model="formData.brief" 
              placeholder="请输入宠物简介"
              rows="3"
              required
            ></textarea>
          </div>
          <div class="form-group">
            <label>详细介绍</label>
            <textarea 
              v-model="formData.description" 
              placeholder="请输入详细介绍"
              rows="6"
              required
            ></textarea>
          </div>
          
          <div class="form-group">
            <label>图片</label>
            <div class="image-upload">
              <div class="image-preview" v-if="imagePreview">
                <img :src="imagePreview" alt="预览图">
                <button type="button" class="remove-image" @click="removeImage">×</button>
              </div>
              <div class="upload-box" v-else>
                <input 
                  type="file" 
                  accept="image/*"
                  @change="handleImageUpload"
                  class="file-input"
                  id="imageUpload"
                >
                <label for="imageUpload" class="upload-label">
                  <span class="upload-icon">+</span>
                  <span>点击上传图片</span>
                </label>
              </div>
              <div class="upload-tip">支持jpg、png格式，大小不超过2MB</div>
            </div>
          </div>
          <div class="dialog-actions">
            <button type="button" class="btn-secondary" @click="closeDialog">取消</button>
            <button type="submit" class="btn-primary">{{ isEditing ? '保存修改' : '添加条目' }}</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'PetEncyclopediaManagement',
  data() {
    return {
      searchQuery: '',
      selectedCategory: '全部',
      categories: ['全部', '狗狗', '猫猫', '小宠', '水族', '鸟类', '爬虫', '其他'],
      entries: [],
      showAddDialog: false,
      isEditing: false,
      tagInput: '',
      formData: {
        name: '',
        category: '',
        brief: '',
        description: '',
        characteristics: '',
        careGuide: '',
        tags: [],
        image: ''
      },
      editingId: null,
      imagePreview: null,
      imageFile: null
    }
  },
  computed: {
    filteredEntries() {
      let result = this.entries
      
      if (this.selectedCategory !== '全部') {
        result = result.filter(entry => entry.category === this.selectedCategory)
      }
      
      if (this.searchQuery) {
        const query = this.searchQuery.toLowerCase()
        result = result.filter(entry => 
          entry.name.toLowerCase().includes(query) ||
          entry.brief.toLowerCase().includes(query) ||
          entry.tags.some(tag => tag.toLowerCase().includes(query))
        )
      }
      
      return result
    }
  },
  methods: {
    addTag() {
      if (this.tagInput.trim() && !this.formData.tags.includes(this.tagInput.trim())) {
        this.formData.tags.push(this.tagInput.trim())
      }
      this.tagInput = ''
    },
    removeTag(tag) {
      this.formData.tags = this.formData.tags.filter(t => t !== tag)
    },
    handleImageUpload(event) {
      const file = event.target.files[0]
      if (file) {
        if (!file.type.startsWith('image/')) {
          alert('请上传图片文件')
          return
        }
        
        if (file.size > 2 * 1024 * 1024) {
          alert('图片大小不能超过2MB')
          return
        }

        this.imageFile = file
        this.imagePreview = URL.createObjectURL(file)
        const reader = new FileReader()
        reader.onload = (e) => {
          this.formData.image = e.target.result
        }
        reader.readAsDataURL(file)
      }
    },
    removeImage() {
      this.imageFile = null
      this.imagePreview = null
      this.formData.image = ''
    },
    submitEntry() {
      const entry = {
        ...this.formData,
        id: this.isEditing ? this.editingId : Date.now(),
        createTime: new Date().toLocaleString()
      }

      if (this.isEditing) {
        const index = this.entries.findIndex(item => item.id === this.editingId)
        if (index !== -1) {
          this.entries[index] = entry
        }
      } else {
        this.entries.unshift(entry)
      }

      this.closeDialog()
    },
    editEntry(entry) {
      this.isEditing = true
      this.editingId = entry.id
      this.formData = {
        name: entry.name,
        category: entry.category,
        brief: entry.brief,
        description: entry.description,
        characteristics: entry.characteristics,
        careGuide: entry.careGuide,
        tags: [...entry.tags],
        image: entry.image
      }
      this.imagePreview = entry.image
      this.showAddDialog = true
    },
    deleteEntry(id) {
      if (confirm('确定要删除这个百科条目吗？')) {
        this.entries = this.entries.filter(item => item.id !== id)
      }
    },
    closeDialog() {
      this.showAddDialog = false
      this.isEditing = false
      this.formData = {
        name: '',
        category: '',
        brief: '',
        description: '',
        characteristics: '',
        careGuide: '',
        tags: [],
        image: ''
      }
      this.imageFile = null
      this.imagePreview = null
      this.tagInput = ''
      this.editingId = null
    }
  }
}
</script>

<style scoped>
.pet-encyclopedia {
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

.search-box input {
  padding: 8px 12px;
  border: 1px solid #ddd;
  border-radius: 4px;
  width: 300px;
}

.encyclopedia-list {
  margin-bottom: 20px;
}

.entry-items {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 20px;
}

.entry-item {
  background: white;
  border-radius: 8px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  padding: 16px;
}

.entry-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12px;
}

.entry-header h3 {
  margin: 0;
  color: #1a2b3c;
}

.entry-category {
  font-size: 12px;
  padding: 4px 8px;
  background: #e5e7eb;
  border-radius: 12px;
  color: #1a2b3c;
}

.entry-preview {
  margin-bottom: 12px;
}

.entry-image {
  width: 100%;
  height: 200px;
  margin-bottom: 12px;
  border-radius: 4px;
  overflow: hidden;
}

.entry-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.entry-brief {
  color: #4b5563;
  margin-bottom: 8px;
  line-height: 1.5;
}

.entry-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.tag {
  font-size: 12px;
  padding: 4px 8px;
  background: #f3f4f6;
  border-radius: 12px;
  color: #1a2b3c;
}

.entry-actions {
  display: flex;
  justify-content: flex-end;
  border-top: 1px solid #eee;
  padding-top: 12px;
  margin-top: 12px;
}

.btn-text {
  background: none;
  border: none;
  color: #1a2b3c;
  cursor: pointer;
  padding: 4px 8px;
  margin-left: 8px;
}

.btn-text.danger {
  color: #dc2626;
}

.btn-text:hover {
  text-decoration: underline;
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
  max-width: 600px;
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

.tags-input {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.tags-container {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.tag {
  display: inline-flex;
  align-items: center;
  gap: 4px;
}

.remove-tag {
  background: none;
  border: none;
  color: #666;
  cursor: pointer;
  padding: 0 4px;
}

.remove-tag:hover {
  color: #dc2626;
}

.dialog-actions {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
  margin-top: 24px;
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

.image-upload {
  margin-top: 8px;
}

.image-preview {
  position: relative;
  width: 200px;
  height: 200px;
  border: 2px dashed #ddd;
  border-radius: 4px;
  overflow: hidden;
}

.image-preview img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.remove-image {
  position: absolute;
  top: 8px;
  right: 8px;
  width: 24px;
  height: 24px;
  border-radius: 50%;
  background: rgba(0, 0, 0, 0.5);
  color: white;
  border: none;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 16px;
}

.upload-box {
  width: 200px;
  height: 200px;
  border: 2px dashed #ddd;
  border-radius: 4px;
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
  gap: 8px;
  color: #666;
}

.upload-icon {
  font-size: 24px;
  color: #1a2b3c;
}

.upload-tip {
  margin-top: 8px;
  font-size: 12px;
  color: #666;
}
</style> 