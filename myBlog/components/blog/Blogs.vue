<template>
 <view> 
	 <u-list @scrolltolower="Load" height="1080rpx" class="content">		 
		<u-list-item v-for="(blog, index) in blogs"
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
		<u-loadmore :status="status"></u-loadmore>
	</u-list>
	
 </view>
</template>

<script>
  export default {  
    data() {
      return {
			
      }
    },
	props:{
		blogs:{
			type:Array,
			default(){
				return []
			}
		},
		status:{
			type:String,
			default(){
				return ''
			}
		}
	},
	methods:{
		blogDetail(id){
			uni.navigateTo({
				url: '../../pages/detail/BlogDetail?id='+id
			})
		},
		Load(){
			this.$emit('Load')
		}
	}    

        
   
    
  }
</script>

<style lang="scss">
	.content{
		margin-left:20rpx;
		margin-right:20rpx;
	}
	.userInfo{
		display: flex;
	}
</style>