<template>
	<view>
	<u-navbar :autoBack="true" title="设置"> </u-navbar>
	<view class="content">
		<u-cell-group>
			<u-cell  title="消息设置" :isLink="true"></u-cell>
			<u-cell  title="夜间模式" >
				<u-switch slot="value" v-model="flag1"></u-switch>
			</u-cell>
			<u-cell  title="免打扰模式" >
				<u-switch slot="value" v-model="flag2"></u-switch>
			</u-cell>
			<u-cell  title="隐私设置" :isLink="true"></u-cell>
			<u-cell  title="偏好设置" :isLink="true"></u-cell>
			<u-gap height="10" bgColor="#dcdcdc"></u-gap>
			<u-cell  title="账号安全" :isLink="true"></u-cell>
			<u-gap height="10" bgColor="#dcdcdc"></u-gap>
			<u-cell  title="给我们评分" :isLink="true"></u-cell>
			<u-cell  title="推荐好友" :isLink="true"></u-cell>
			<u-cell  title="隐私条款" :isLink="true"></u-cell>
			<u-cell  title="信息收集说明" :isLink="true"></u-cell>
			<u-cell  title="第三方SDK信息说明" :isLink="true"></u-cell>
			<u-cell  title="版本" value="v.5.4.0"></u-cell>
			<u-gap height="40" bgColor="#dcdcdc"></u-gap>
			<u-cell  title="退出登录" @click="logout"></u-cell>
			
		</u-cell-group>
	</view>
	</view>
</template>

<script>
	export default {
		data(){
			return{
				flag1:false,
				flag2:false,
				user:{}
			}
		},
		onLoad() {
			this.user = uni.getStorageSync("user")
		},
		methods:{
			logout(){
				this.$blogsRequest({
					url: 'logout/'+this.user.id,
					// header:{
					// 	 "Authorization":uni.getStorageSync("authorization")
					// }
				}).then(res =>{
					if(res.data.code == 200){
						uni.removeStorageSync("user")
						uni.showToast({
						icon:'success',
						title: "退出成功"
					})
					setTimeout(() =>{
					  uni.switchTab({
						url:'../member/member'
					  })
					}, 1500)
					}
					
				})
			}
		}
	}
</script>

<style>
	.content{
		margin-top: 90rpx;
	}
</style>
