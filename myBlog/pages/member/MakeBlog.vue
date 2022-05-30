<template>
	<view>
		<u-navbar :autoBack="true" title="发表博客" style="margin-bottom: 90rpx;"></u-navbar>
		<u-form labelPosition="top" :model="model1" 
		:rules="rules" ref="form1" labelAlign="center" labelWidth="300px">
			<u-form-item label="标题" prop="blog.title" style="width: 80%;margin-left: 10%;">
				<u--input placeholder="请输入标题"
						border="surround"
						 v-model="model1.blog.title"
						></u--input>
			</u-form-item>
			<u-form-item label="描述" 
			prop="blog.description" style="width: 80%;margin-left: 10%;">
				<u--textarea placeholder="请输入描述" autoHeight v-model="model1.blog.description">	
				</u--textarea>
			</u-form-item>
			<u-form-item label="博客内容" 
			prop="blog.content" style="width: 80%;margin-left: 10%;">
				<u--textarea placeholder="请输入内容" maxlength="300" 
				count v-model="model1.blog.content"></u--textarea>
			</u-form-item>
		</u-form>
		<view class="button">
				  <u-button type="primary" text="发表" @click="submit"></u-button>
				  <u-button type="warning" text="重置" @click="reset"></u-button>
		</view> 
	</view>
</template>

<script>
	export default {
		data(){
			return{
				model1:{
					blog:{
						title:'',
						description: '',
						content: ''
					}	
				},
				rules:{
					'blog.title':[
						{type: 'string',required: true, message: '标题不能为空', trigger: ['blur']}
					],
					'blog.description':[
						{type: 'string',required: true, message: '描述不能为空', trigger: ['blur','change']},
						{ min: 15,  message: '长度要大于15 个字符', trigger: ['blur','change']}
					],
					'blog.content':[
						{type: 'string',required: true, message: '内容不能为空', trigger: ['blur','change']},
						{min: 15,max: 300,  message: '长度在 15 到 300 个字符', trigger: ['blur','change']}
					]
				}
			}
		},
		methods:{
			submit() {
				let id = uni.getStorageSync("user").id
				console.log(this.model1.blog.title)
				this.$refs.form1.validate().then(res => {
					this.$blogsRequest({
						url: 'blog/add',
						method: 'POST',
						data: {
							title: this.model1.blog.title,
							description: this.model1.blog.description,
							content: this.model1.blog.content,
							userId: id
						}
					}).then(res => {
						if(res.data.code == 200){
							uni.showToast({
							icon:'success',
							title: "发表成功"
						  })
						  setTimeout(() =>{
							  uni.switchTab({
								url:'../member/member'
							  })
						  }, 1500)
						}
					  
					  })
				}).catch(errors => {
					uni.$u.toast('校验失败')
				})
			},
			reset(){
			  this.$refs.form1.resetFields()
			  this.$refs.form1.clearValidate()
			},
		}
	}
</script>

<style>
	.button{
		display: flex;
	}
	.button button{
		width: 30%;
	}
</style>
