<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>

<%@include file="../common/_meta.jsp"%>
<link rel="stylesheet"	href="<%=path%>/resources/layui/css/layui.css" media="all">
<script type="text/javascript" src="<%=path %>/resources/js/common_list.js"></script>

</head>
<body>
	<nav class="breadcrumb">
		<i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span>
		${title} <span class="c-gray en">&gt;</span> ${title}列表 <a
			class="btn btn-success radius r"
			style="line-height: 1.6em; margin-top: 3px"
			href="javascript:location.replace(location.href);" title="刷新"><i
			class="Hui-iconfont">&#xe68f;</i></a>
	</nav>
	<div class="page-container">
		<form id="query" method="post">
			<input name="currentPage" id="currentPage" type="hidden" value="1">
			<input name="totalPage" id="totalPage" type="hidden" value="1">
			<div class="text-c">
				${jssaititle}
				<button type="button" onclick="earcs()" class="btn btn-success"
					id="" name="">
					<i class="Hui-iconfont">&#xe665;</i>搜索
				</button>
			</div>
		</form>
		<div class="cl pd-5 bg-1 bk-gray mt-20">
			<span class="l">
			<shiro:hasPermission name="${permissions_del_list}">  
				<a href="javascript:;" onclick="common_delAll('<%=path %>','admin','${minName}')" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i>批量删除</a> 
			</shiro:hasPermission>
			<shiro:hasPermission name="${permissions_add_list}">  
				<a href="javascript:;" onclick="com('${title}添加','')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i>添加</a>
			</shiro:hasPermission>
			</span> 
			<span class="r">共有数据：<strong class="totalRecord">0</strong>条
			</span>
		</div>
		<table id="listTable"
			class="table table-border table-bordered table-bg">
			<thead>
				<tr>
					<th scope="col" colspan="100">${title}列表</th>
				</tr>
				<tr class="text-c">
					<th width="25"><input type="checkbox" name="" value=""></th>
					${jsname}
					<th width="150">操作</th>
				</tr>
			</thead>
			<tbody id="tbody"></tbody>
		</table>
		<div id="commonpage" class="text-r"></div>
	</div>

</body>
</html>
<body>
<%@include file="../common/_footer.jsp"%>
<!--分页JS-->
<script src="<%=request.getContextPath()%>/resources/layui/layui.js" charset="utf-8"></script>  
<script src="<%=request.getContextPath()%>/resources/layui/lay/modules/laypage.js" charset="utf-8"></script> 

<script id="test" type="text/html">
{{each result.resultList as value i}}
<tr class="text-c" >
	<td><input type="checkbox" value="{{result.resultList[i].id}}" name="ids"></td>
		${jsvlaue}
	<td class="td-manage">
	<shiro:hasPermission name="${permissions_update_list}">  
		<a title="编辑" href="javascript:;" onclick="com('${title}编辑','{{result.resultList[i].id}}')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i></a>
	</shiro:hasPermission>
	<shiro:hasPermission name="${permissions_del_list}">  
		<a title="删除" href="javascript:;" onclick="common_del(this,'{{result.resultList[i].id}}','<%=path %>','admin','${minName}')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a>
	</shiro:hasPermission>
	${jslook}
	</td>
</tr>
{{/each}}
</script>

<script>
var url='<%=request.getContextPath() %>'; 
var bag="admin";	//模块 ${type}
var service='${minName}';

/* 赛选 */
function earcs(){
	demo(url,bag,service);
}

/* 刷新 */
$(document).ready(function(){ 
	demo(url,bag,service);
});

/* 编辑 */
function com(title,id){
	common(url,title,bag,service,id,"1000","500");
}

/* 查看 */
function com_look(title,id){
	comlook(url,title,bag,service,id,"1200","800");
}

</script>
</body>
</html>