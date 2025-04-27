<template>
  <div class="posts">
    <h1>帖子审核</h1>
    <div class="action-bar">
      <div class="filters">
        <select v-model="status" class="filter-select">
          <option value="pending">待审核</option>
          <option value="approved">已通过</option>
          <option value="rejected">已拒绝</option>
        </select>
      </div>
    </div>
    <div class="posts-list">
      <div class="empty-state" v-if="!posts.length">
        暂无待审核的帖子
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'PostsManagement',
  data() {
    return {
      status: 'pending',
      posts: []
    }
  }
}
</script>

<style scoped>
.posts {
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

.filter-select {
  padding: 8px 12px;
  border: 1px solid #ddd;
  border-radius: 4px;
  background-color: white;
  color: #1a2b3c;
}

.empty-state {
  text-align: center;
  padding: 40px;
  color: #666;
  background: #f8f9fa;
  border-radius: 4px;
}
</style> 