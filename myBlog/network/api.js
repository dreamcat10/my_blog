const BASE_URL = "http://localhost:8081/"

export function blogsRequest(options){
	return new Promise( (resolve, reject) => {
		uni.request({
			url: BASE_URL + options.url,
			data: options.data || {},
			method:options.method || 'GET',
			header: options.header || {},
			success: (res) =>{
				if(!res){
					return uni.showToast({
						icon:'error',
						title:"获取数据失败"							
					})		
				}
				 resolve(res)
				},
			fail: (err) => {
				return uni.showToast({
					icon:'error',
					title:"网络错误"							
				})
				reject(err)
			}
		})
	})
}