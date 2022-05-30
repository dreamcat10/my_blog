<template>
	<view class="member">
		<view class="header">
			<u-icon name="setting" class="setting" @click="toSetting"></u-icon>
			<u-cell title="去登录" 
			:isLink="true" 
			size="large" v-if="user.username == null" 
			@click="toLogin">
				<u-avatar slot="icon" size="52" :src="user.avatar"></u-avatar>
			</u-cell>
			<u-cell :title="user.username"
			:isLink="true" 
			size="large" v-else @click="toMy" :disabled="flag">
				<u-avatar slot="icon" size="52" :src="user.avatar"></u-avatar>
			</u-cell>
		</view>
		<view class="body">
			<u-grid col=4 :border="false" >
			<u-grid-item v-for="item in grid">
				<u-text class="grid-text">{{item.number}}</u-text>
				<u-text class="grid-text">{{item.title}}</u-text>
			</u-grid-item>
			</u-grid>
		</view>
		<view class="button">
			<u-cell title="查看已发表的博客" :isLink="true" 
			@click="myblog" style="margin-bottom: 44rpx;" :disabled='flag'></u-cell>
			<u-button type="primary" text="发表文章" 
			style="width: 60%;" @click="makeblog" :disabled="flag"></u-button>
		</view>
	</view>
</template>

<script>
	export default {
		data(){
			return{
				user:{},
				flag: false,
				grid:[{
					number: 10,
					title: '关注'
				}, {
					number: 2,
					title:'粉丝'
				},{
					number: 29,
					title: '访问'
				},{
					number: '--',
					title: '排名'}]
			}
	},
	onShow(){
		this.user = uni.getStorageSync("user")
		if(this.user.username == null){
			this.flag = true
		}else{
			this.flag = false
		}
	},
	methods:{
		toSetting(){
			uni.navigateTo({
				url:'../setting/setting'
			})
		},
		toLogin(){
			uni.navigateTo({
				url:'../login/Login'
			})
		},
		myblog(){
			uni.navigateTo({
				url: './MyBlog?id='+ this.user.id
			})
		},
		makeblog(){
			uni.navigateTo({
				url: './MakeBlog'
			})
		},
		toMy(){
			uni.navigateTo({
				url:'./MyInfo'
			})
		}
	}
	
}
</script>

<style>
	.setting{
		margin-left: 90%;
		margin-top: 20rpx;
	}
	.body{
		margin-top: 44rpx;
	}
	 .grid-text {
		font-size: 14px;
		color: #909399;
		padding: 10rpx 0 20rpx 0rpx;
		/* #ifndef APP-PLUS */
		box-sizing: border-box;
		/* #endif */
	}
	.button{
		margin-top: 44rpx;
	}
</style>
