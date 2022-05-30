<template>
	<view >
		<u-navbar :autoBack="true">
			<view class="navbar" slot="center">
			  <u-search placeholder="请输入关键字" 
			  v-model="keyword" 
			  @search="getGoods" 
			  :showAction="false"></u-search> 
			  </view>
			</u-navbar>
		<view v-if="blogList == null">
			<u-empty
			        mode="search"
			        icon="http://cdn.uviewui.com/uview/empty/search.png"
			>
			</u-empty>
		</view>
	    <view class="content" v-else>
			 <u-list height="1200rpx" class="content">		 
				<u-list-item v-for="(blog, index) in blogList"
						:key="index">
					<view>
						<u-skeleton
							    rows="2"
								:loading="false"
								:title="true"
							>
								<u--text :bold="true" 
										:text="blog.blog.title" 
										@click="blogDetail(blog.blog.id)" size="18px"></u--text>
								
								<u--text :lines="2" :text="blog.blog.description"></u--text>	
								<view class="userInfo">
									<u-avatar :src="blog.user.avatar" size="20px"></u-avatar>
									<u-text :text="blog.user.username" size="10px"></u-text>
									<u--text :text="blog.blog.created" size="10px"></u--text>	
								</view>
							</u-skeleton>			
					</view>
					<u-line margin="10px"></u-line>		
				</u-list-item>
				<u-loadmore status="nomore"></u-loadmore>
			</u-list>
	  </view>
	</view>
	
</template>

<script>

	export default {
		data(){
			return{
				keyword: '',
				blogList: []	
			}	
		},
		onLoad(option) {
			this.keyword = option.keyword		
			this.getGoods()
		},
		methods:{
			getGoods(){			
				this.$blogsRequest({
					  url: 'blog/key',
					  data: {
						 keyword: this.keyword
					  },
					  method: 'POST'
					}).then(res => {
					  this.blogList = res.data.data
				})
			},
			Load(){
				
			},
			blogDetail(id){
				uni.navigateTo({
					url: '../../pages/detail/BlogDetail?id='+id
				})
			},
		},
		components:{
			
		}
	}
</script>

<style>
	.navbar{
		width: 70%
	}
	.content{
		margin-top: 100rpx;
	}
</style>
