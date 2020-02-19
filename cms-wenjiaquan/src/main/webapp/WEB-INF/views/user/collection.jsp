<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<form class="form-inline" id="queryForm">
 <input type="hidden" name="pageNum" value="1">
</form>
<table class="table" bgcolor="white">
  <thead>
    <tr>
      <th scope="col"><input type="checkbox" value="" id="chkALL" name="chkALL"></th>
      <th scope="col">#</th>
      <th scope="col">收藏夹文本</th>
      <th scope="col">地址</th>
      <th scope="col">时间</th>
      <th scope="col">操作</th>
    </tr>
  </thead>
  <tbody>
	<c:forEach items="${pageInfo.list }" var="item">
       <tr>
	      <th><input type="checkbox" value="${item.id }" name="chk_list"></th>
	      <th scope="row">${item.id }</th>
	     <td><a href="/article/${item.title_id }.html"  style="color:black">${item.text }</a></td>
	      <td>${item.url}</td>
	      <td><fmt:formatDate value="${item.created }" pattern="yyyy-MM-dd HH:mm"/></td>
	      <td>
	      <input type="button" value="删除" onclick="del(${item.id })">
	      </td>
	    </tr>
   	</c:forEach>
  </tbody>
</table>
<div class="row">
	<div class="col-10">
		<jsp:include page="../common/page.jsp"></jsp:include>
	</div>
</div>
<script src="<%=request.getContextPath() %>/js/checkbox.js?v1.00"></script>
<script>
function query(){
	var params = $("form").serialize();
	reload(params);
}
function gotoPage(pageNo){
	$("[name=pageNum]").val(pageNo);
	query();
}
function del(id) {
	$.get(
		"/user/delcollect",
		{id:id},
		function(msg){
			if(msg){
				alert("删除成功");
				query();
			}else{
				alert("删除失败");
			}
		}
	)
}
</script>