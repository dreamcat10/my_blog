<template>
  <view>
	  <u--form :rules="rules" :model="ruleForm" ref="form1" labelPosition="left"> 
		  <u-form-item label="用户名称" prop="username" borderBottom labelWidth="70">
			  <u-input  placeholder="请输入用户名"
						border="false"
						v-model="ruleForm.username"
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
		  <u-button type="primary" text="注册" @click="toInlogin"></u-button>
		  <u-button type="warning" text="重置" @click="reset"></u-button>
	  </view> 
  </view>
</template>

<script>
  export default {
    name: "Login",
    data() {
      return {
        ruleForm: {
          username: '',
          password: ''
        },
        rules: {
          username: [
            {type: 'string',required: true, message: '用户名格式错误', trigger: ['blur', 'change'] },
            { min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: ['blur', 'change'] }
          ],
          password: [
            {type: 'number', required: true, message: '密码格式错误', trigger: ['blur', 'change']}
          ]
        }
      };
    },
    methods: {
		submit() {
			this.$refs.form1.validate().then(res => {
				this.$blogsRequest({
					url: 'login',
					method: 'POST',
					data: {
						username: this.ruleForm.username,
						password: this.ruleForm.password
					}
				}).then(res => {
				  const jwt = res.header['authorization']
				  const userInfo = res.data.data
				
				  // 把数据共享出去
				  uni.setStorageSync("authorization", jwt)
				  uni.setStorageSync("user", userInfo)
				  uni.showToast({
				  	icon:'success',
					title: "登录成功"
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
		  this.$refs.form1.clearValidate()
	  },
		toInlogin(){
			uni.navigateTo({
				url:'./Inlogin'
			})
		}
      },
	 
    }
  
</script>

<style>
	.button{
		width: 60%;
		margin-left: 20%;
	}
	.button button{
		margin-top: 20rpx;
	}

</style>