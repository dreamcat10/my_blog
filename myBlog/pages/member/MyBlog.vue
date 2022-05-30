<template>
	<view>
		<u-navbar :autoBack="true" title="我发表的博客" style="margin-bottom: 90rpx;"></u-navbar>
		<Blogs :blogs=blogs status="nomore" v-if="blogs.length > 0"></Blogs>
		<u-empty v-else icon="http://cdn.uviewui.com/uview/empty/data.png">
			<u-button type="primary" text="去发表博客" style="margin-top: 90rpx;" @click="makeblog"></u-button>
		</u-empty>
	</view>
	
</template>

<script>
	import Blogs from '../../components/blog/Blogs.vue'
	export default {
		data(){
			return{
				blogs:[]
			}
		},
		components:{
			Blogs
		},
		onLoad(option) {
			this.$blogsRequest({
					url: 'myblog/'+ option.id	
				}).then(res => {
					this.blogs = res.data.data
				  })
		},
		methods:{
			makeblog(){
				uni.navigateTo({
					url: './MakeBlog'
				})
			}
		}
	}
</script>

<style>
</style>
