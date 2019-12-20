<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cms后台登录</title>
<link href="<%=request.getContextPath() %>/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=request.getContextPath() %>/css/cms.css" rel="stylesheet">
</head>
<form>
 <input type="hidden" name="pageNum" value="1">
</form>
<body>
<table class="table" bgcolor="white">
  <thead>
    <tr>
      <th scope="col">#</th>
      <th scope="col">文章标题</th>
      <th scope="col">评论内容</th>
      <th scope="col">评论时间</th>
    </tr>
  </thead>
  <tbody>
    <c:forEach items="${list}" var="el">
    <tr>
    	<td>${el.id}</td>
    	<td>
    	<c:if test="${USER_SESSION_ID!=null && USER_SESSION_ID.headimg!=null }">
				<img src="${USER_SESSION_ID.headimg }" style="border-radius: 15px" width="30" height="30" alt="">
				${el.title}
		</c:if>
    	</td>
    	<td>${el.content}</td>
    	<td>${el.created}</td>
    </tr>
    </c:forEach>
  </tbody>
</table>
<div class="col-10">
		<jsp:include page="../common/page.jsp"></jsp:include>
</div>	
<script type="text/javascript" src="<%=request.getContextPath() %>/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-1.8.2.min.js"></script>
<script src="<%=request.getContextPath() %>/js/checkbox.js?v1.00"></script>
<script type="text/javascript">
function gotoPage(pageNo){
	$("[name=pageNum]").val(pageNo);
	reload($("form").serialize());
}
</script>
</body>
</html>