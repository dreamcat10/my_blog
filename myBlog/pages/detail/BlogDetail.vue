<template>		      
	<view class="detail">
		<u-navbar :autoBack="true"> </u-navbar>
		<view class="detail-info">
			<view class="title">
				<u-parse :content="blog.title" :selectable="true"></u-parse>
			</view>
			<view class="userInfo">
				<u-avatar :src="user.avatar"></u-avatar>
				<p>{{user.username}}</p>
				<u-button class="button" size="small" :plain="true" text="关注" shape="circle"></u-button>
			</view>
			
			<view class="description">
				本文介绍<u-line></u-line>
				<u-parse :content="blog.description" ></u-parse>
			</view>
			<view class="content">
				内容<u-line></u-line>
				<u-parse :content="blog.content" ></u-parse>
			</view>
			<view class="comment">
				评论
				<u--textarea v-model="comment" 
				placeholder="请留下你的评论" :showConfirmBar="true" count ></u--textarea>
				<u-button type="primary" text="提交" @click="commentup"></u-button>
				<u-line></u-line>
				<u-cell-group>
					<u-cell v-for="item in comments" >
						<view slot="title" class="comment-user">
							<u-avatar :src="item.user.avatar" size="20px"></u-avatar>
							<u-text :text="item.user.username" size="10px"></u-text>
							<u--text :text="item.comment.created" size="10px"></u--text>	
						</view>
						<view slot="label">
							<u-parse :content="item.comment.content"></u-parse>
						</view>
					</u-cell>
				</u-cell-group>
			</view>
		</view>
		
	</view>
</template>

<script>
  export default {
    data() {
      return {
        blog: {},
		user: {},
		comments:[],
        ownBlog: false,
		comment: '',
		id: 0

      }
    },
	onLoad(option) {
		this.details(option.id)
		this.id = option.id
	},
	methods:{
		details(id){
			this.$blogsRequest({
				url: 'blog/'+ id,
			}).then(res =>{
				this.blog = res.data.data.blog
				this.user = res.data.data.user
				this.comments = res.data.data.comments
				if(this.blog == null){
					uni.showToast({
						icon:'error',
						title:res.data.msg
					})
				}
			})
		},
		commentup(){
			let uid = uni.getStorageSync("user").id
			this.$blogsRequest({
				url: 'comment/add',
				method: 'POST',
				data:{
					bid: this.id,
					uid: uid,
					content: this.comment		
				}
			}).then(res => {
				if(res.data.code == 200){
					uni.showToast({
						icon:'success',
						title:res.data.msg
					})
					this.comment = ''
					this.details(this.id)
				}
			})
		}
	}
   
    }
  
</script>

<style lang="scss">
	.detail{
		height: 100vh;
	}
	.detail-info{
		margin-left: 20rpx;
		margin-right: 20rpx;
	}
	.title{
		margin-top:50px ;
		width: 40vh;
		font-size: 35rpx;
		font-family: "黑体";
		font-weight: 800;
	}
	.userInfo{
		margin: 20rpx 15rpx;
		display: flex;
		
	}
	.userInfo p{
		margin-left: 15rpx;
		line-height: 80rpx;
	}
	.button{
		width: 150rpx;
		margin-right: 50rpx;
	}
	.content{
		margin-top: 40rpx;
		margin-bottom: 200rpx;
	}
	.description{
		margin-top: 40rpx;
	}
	.comment button{
		margin-top: 20rpx;
		margin-bottom: 20rpx;
		width: 15%;
		height: 50rpx;
		margin-right: 30rpx;
	}
	.comment-user{
		display: flex;
		margin-bottom: 20rpx;
	}

</style>