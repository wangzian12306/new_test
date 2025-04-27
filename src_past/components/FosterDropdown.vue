<template>
  <div class="foster-dropdown">
    <button @click="toggleDropdown" class="dropdown-button">寄养</button>
    <div v-if="isOpen" class="dropdown-content">
      <router-link to="/foster?type=foster" @click="closeDropdown">寄养</router-link>
      <router-link to="/foster?type=adopt" @click="closeDropdown">领养</router-link>
      <router-link to="/foster?type=publish" @click="closeDropdown">我要发布</router-link>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      
      isOpen: false
    }
  },
  methods: {
    toggleDropdown() {
      this.isOpen = !this.isOpen
    },
    closeDropdown() {
      this.isOpen = false
    }
  }
}
</script>

<style scoped>
.foster-dropdown {
  position: relative;
  display: inline-block;
  font-size: 1.6rem; 
  z-index: 1001;
}

.dropdown-menu {
  position: absolute;
  top: 100%;
  left: 0;
  z-index: 1000; /* 关键设置 */
  background: white;
  box-shadow: 0 2px 10px rgba(0,0,0,0.1);
}
.dropdown-button {
  background: #FDEEEE;
  border: none;
  color: #95CFEF;
  cursor: pointer;
  padding: 0.5rem;
  font-size: 1.6rem; 
}

.dropdown-button:hover {
  color: #1378D4;
}

.dropdown-content {
  position: absolute;
  background-color: #FDEEEE;
  min-width: 120px;
  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
  
  z-index: 1002;
}

.dropdown-content a {
  color: black;
  padding: 12px 16px;
  text-decoration: none;
  display: block;
}

.dropdown-content a:hover {
  background-color: #FDEEEE;
}
</style>