<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../common/_meta.jsp" %>
<script type="text/javascript" src="<%=path %>/resources/js/common_add.js"></script>
<script type="text/javascript"	src="<%=path%>/resources/lib/My97DatePicker/WdatePicker.js"></script>
</head>
<body>

<article class="page-container">
	<form class="form form-horizontal" id="form">
 		${select}
 	</form>
</article>
<%@include file="../common/_footer.jsp" %>
	
<script type="text/javascript">
/* 反显时间 示例: */
/* $("#setuptheDate_01").html(formatdate(new Date(参数))); */
</script>
</body>
</html>