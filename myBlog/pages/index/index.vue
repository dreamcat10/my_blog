<template>
	<view>		
		<u-search placeholder="请输入关键字" v-model="keyword" @search="search" :showAction="false"></u-search>
		<Blogs :blogs=blogs :status="status" @Load="Load"></Blogs>
		
	</view>
</template>

<script>
	import Blogs from '@/components/blog/Blogs.vue'
	import Header from '@/components/Header.vue'
	export default {
		data() {
			return {
				blogs: [],
				currentPage: 0,
				status: 'loading',
				keyword: '',
				blogList: []
			}
		},
		onLoad() {
			this.currentPage = 0
			this.page()
		},
		methods: {
		  page() {
			  let currentPage = this.currentPage + 1 
			  this.$blogsRequest({
				  url: 'blogs',
				  data:{
					 'currentPage': currentPage 
				  }
			  }).then( res =>{
				  this.blogs = this.blogs.concat(res.data.data)
				  this.currentPage ++
				  if(res.data.data.length == 0){
					  this.status = 'nomore'
				  }
			  })         
		
		    
		  },
		  Load(){
			  setTimeout(() => {
			     this.page()
			  }, 1500)
		  },
		  search(){
			uni.navigateTo({
				url:'../search/search?keyword='+this.keyword
			})
		  }
		  },
		components:{
			Blogs,
			Header
		}
	}
</script>

<style lang="scss">

</style>
