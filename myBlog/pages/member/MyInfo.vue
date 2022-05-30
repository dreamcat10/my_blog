<template>
	<view>
		<u-navbar :autoBack="true" title="个人资料" style="margin-bottom: 90rpx;"></u-navbar>
		<view class="avatar">
			<u-avatar src="/file/e5c2952d-cec5-41c6-a373-987566bd7766.png" 
			size="60" style="margin-left: 40%;" @click="upLoad"></u-avatar>
			<u-text :text="user.username" style="margin-top: 40rpx;" 
			align="center" type="primary" :bold="true"></u-text>
		</view>
		<view class="info">
			<u-cell-group>
				<u-cell title="邮箱" :value="user.email || '无'"></u-cell>
				<u-cell title="创建日期" :value="user.created"></u-cell>
				<u-cell title="上次登录日期" :value="user.lastLogin"></u-cell>
			</u-cell-group>
		</view>
	</view>
</template>

<script>
	export default {
		data(){
			return{
				user:{}
			}
		},
		onLoad() {
			this.user = uni.getStorageSync("user")
			console.log(this.user.avatar)
		},
		methods:{
			upLoad(){
			uni.chooseImage({
				count: 1,
				sizeType: ['original', 'compressed'],
				sourceType: ['album'],
				success: (res)=> {
					const tempFilePaths = res.tempFilePaths;
					uni.uploadFile({
						url: 'http://localhost:8081/user/upload/' + this.user.id, //仅为示例，非真实的接口地址
						file:{
							filePath: tempFilePaths[0],
							name: 'avatar',
						},
						success: (uploadFileRes) => {
							console.log(uploadFileRes);
						}
					});
				}
				});
			}
		}
		
	}
</script>

<style>
	.avatar{
		margin-top: 150rpx;
		padding: 40rpx;
		border: #E0E3E9 solid 1px;
	}
</style>
