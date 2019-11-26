
			${bigName}Controller 控制器
			
			1.路径 /${keyType}/${minName}/index 重定向至  /view/${keyType}/${minName}_list.jsp 页面
			 	入参：无
			 	
			2.路径  /${keyType}/${minName}/add 转发至 view/${keyType}/${minName}_add.jsp 页面 -》 带有一个 ${minName} 对象
				 可用 ${minName}.属性 取的其中的属性内容
				 入参： 带fdId值  == update (更新)
				 	 不带fdId值 == save (保存)
				 
			3.路径 /${keyType}/${minName}/list 返回一个list集合接口 
				入参：currentPage 页数 [有默认] -》 分页则需要传入 页码
					pageSize 每页个数 [有默认]
				出参：返回一个result集合接口 	code message
							
			4.路径 /${keyType}/${minName}/save 新增${bigName}对象
				入参：必带不可为空的值
				出参：code message
				
			5.路径 /${keyType}/${minName}/update 更新${bigName}对象,from表单中必带name='fdId',或者说必带${bigName}其主键
				入参：必带fdId值
				出参：code message
				
			6.路径 /${keyType}/${minName}/delete 可删除多条
				入参：{"主键ID值","主键ID值","主键ID值"}
				出参：code message
				
			7.路径 /${keyType}/${minName}/listAll 查询全部
				入参：无
				出参：code message result集合
			
			8.路径 /${keyType}/${minName}/selectKey 查询单个对象
				入参：fdId
				出参：code message result 对象
		
		