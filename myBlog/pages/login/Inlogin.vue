<template>
	<view>
		<u-navbar :autoBack="true" title="注册" class="navbar"></u-navbar>
		<u--form :rules="rules" :model="ruleForm" ref="form1" labelPosition="left">
			  <u-form-item label="用户名称" prop="username" borderBottom labelWidth="70">
				  <u-input  placeholder="请输入用户名"
							border="false"
							v-model="ruleForm.username"
							></u-input>
			  </u-form-item>
			  <u-form-item label="邮箱" prop="email" borderBottom labelWidth="70">
				  <u-input  placeholder="请输入邮箱"
							border="false"
							v-model="ruleForm.email"
							></u-input>
			  </u-form-item>
			  <u-form-item label="密码" prop="password" borderBottom labelWidth="70">
				  <u-input 	placeholder="请输入密码"
							border="false"
							type="password"
							v-model="ruleForm.password"
							></u-input>
			   </u-form-item>
		</u--form>
		<view class="button">
				  <u-button type="primary" text="提交" @click="submit"></u-button>
				  <u-button type="warning" text="重置" @click="reset"></u-button>
		</view> 
	</view>
</template>

<script>
	export default {
		data() {
		  return {
		    ruleForm: {
		      username: '',
		      password: '',
			  email:'',
		    },
		    rules: {
		      username: [
		        {type: 'string',required: true, message: '用户名不能为空', trigger: ['blur', 'change'] },
		        { min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: ['blur', ] }
		      ],
		      password: [
		        {type: 'number', required: true, message: '密码格式错误', trigger: ['blur', 'change']}
		      ],
			  email: [
				  {type: 'string', message: '邮箱格式错误', trigger: ['blur']}
			  ]
		    }
		  }
		},
		methods:{
			submit() {
				this.$refs.form1.validate().then(res => {
					this.$blogsRequest({
						url: 'inLogin',
						method: 'POST',
						data: {
							username: this.ruleForm.username,
							password: this.ruleForm.password,
							email: this.ruleForm.email
						}
					}).then(res => {
					  const jwt = res.header['authorization']
					  const userInfo = res.data.data
					
					  // 把数据共享出去
					  uni.setStorageSync("authorization", jwt)
					  uni.setStorageSync("user", userInfo)
					  uni.showToast({
						icon:'success',
						title: "注册成功"
					  })
					  setTimeout(() =>{
						  uni.switchTab({
							url:'../member/member'
						  })
					  }, 1500)
					  })
				}).catch(errors => {
					uni.$u.toast('校验失败')
				})
			},
			 reset(){
			  this.$refs.form1.resetFields()
		}
		}
	}
</script>

<style>
	.navbar{
		margin-bottom: 90rpx;
	}
	.button{
		width: 60%;
		margin-left: 20%;
	}
	.button button{
		margin-top: 20rpx;
	}
	
</style>
