<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<%@include file="../common/_meta.jsp" %>
<script type="text/javascript" src="<%=path %>/resources/js/common_add.js"></script>
${ContentJS}
</head>
<body>


<article class="page-container">
	<form class="form form-horizontal" id="form-article-add">
		<input type="hidden" id="fdId" name="fdId" value="${${minName}.id}"> ${ecommendADD}
		${jstitle}
		<div class="row cl">
			<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2">
				<button onClick="com_addUpdate()" class="btn btn-primary radius" id="saveBtn" type="button"><i class="Hui-iconfont">&#xe632;</i> 保存</button>
				<button onClick="layerclose()" class="btn btn-default radius" type="button">&nbsp;&nbsp;关闭&nbsp;&nbsp;</button>
			</div>
		</div>
	</form>
</article>
<%@include file="../common/_footer.jsp" %>
<%@include file="../common/_check.jsp" %>
<script type="text/javascript">
var url='<%=request.getContextPath() %>'; 
var bag="admin";	//模块 ${type}
var service='${minName}';
function com_addUpdate(){
	${isImg}
}

//图片反显
${imgJS}

//选牌
${ecommendJs}

//外键
${voutilSelecyt}
</script>
</body>
</html>