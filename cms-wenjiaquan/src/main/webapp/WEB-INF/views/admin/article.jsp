<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
  	<form class="form-inline" id="queryForm">
	  <div class="form-group mx-sm-3 mb-2">
	    <input type="text" name="name" class="form-control" placeholder="请输入文章标题">
	  </div>
	  <div class="form-group mx-sm-3 mb-2">
	  <select id="inputState" class="form-control" id="channelId" name="channelId">
	        <option value="">请选择频道...</option>
	     	<c:forEach items="${channelList }" var="item">
	     		<c:if test="${item.id==article.channelId }">
			        <option value="${item.id }" selected="selected">${item.name }</option>
	     		</c:if>
	     		<c:if test="${item.id!=article.channelId }">
			        <option value="${item.id }">${item.name }</option>
	     		</c:if>
	     	</c:forEach>
	      </select>
	  </div>
	  <div class="form-group mx-sm-3 mb-2"  id="status" name="status">
	     <select class="form-control" id="status" name="status" >
	        <option value="">请选择审核状态...</option>
	        <option value="0" <c:if test="${article.status==0 }">selected="selected"</c:if>>未审核</option>
	        <option value="1" <c:if test="${article.status==1 }">selected="selected"</c:if>>审核通过</option>
	        <option value="-1" <c:if test="${article.status==-1 }">selected="selected"</c:if>>审核未通过</option>
	      </select>
	  </div>
	  <input type="hidden" name="pageNum" value="1">
	  <button type="button" class="btn btn-primary mb-2" onclick="query()">查询</button>
	</form>
	<table class="table">
  <thead>
    <tr>
      <th scope="col"><input type="checkbox" value="" id="chkALL" name="chkALL"></th>
      <th scope="col">#</th>
      <th scope="col">标题</th>
      <th scope="col">所属频道</th>
      <th scope="col">所属分类</th>
      <th scope="col">是否热点</th>
      <th scope="col">审核状态</th>
      <th scope="col">发布时间</th>
      <th scope="col">操作</th>
    </tr>
  </thead>
  <tbody>
  <c:forEach items="${pageInfo.list }" var="item">
       <tr>
	      <th><input type="checkbox" value="${item.id }" name="chk_list"></th>
	      <th scope="row">${item.id }</th>
	      <td>${item.title }</td>
	      <td>${item.channelName }</td>
	      <td>${item.categoryName }</td>
	      <td>${item.hot>0?"是":"否"}</td>
	      <td>${item.status==1?"已审核":item.status==0?"未审核":"审核未通过"}</td>
	      <td><fmt:formatDate value="${item.created }" pattern="yyyy-MM-dd HH:mm"/></td>
	      <td>
	      	<button type="button" class="btn btn-primary">审核</button>
	      	<button type="button" class="btn btn-primary" onclick="addHot('${item.id}')">加热</button>
	      </td>
	    </tr>
   	</c:forEach>
  </tbody>
</table>
<div class="row">
	<nav aria-label="Page navigation example col-5" style="margin-right: 10px;">
		<button type="button" class="btn btn-primary" onclick="add();">添加</button>
		<button type="button" class="btn btn-primary">批删</button>
	</nav>
   	<nav aria-label="Page navigation example col-4">
	  <ul class="pagination">
	    <li class="page-item"><a class="page-link" href="#">首页</a></li>
	    <li class="page-item"><a class="page-link" href="#">上一页</a></li>
	    <li class="page-item"><a class="page-link" href="#">1</a></li>
	    <li class="page-item"><a class="page-link" href="#">2</a></li>
	    <li class="page-item"><a class="page-link" href="#">3</a></li>
	    <li class="page-item"><a class="page-link" href="#">下一页</a></li>
	    <li class="page-item"><a class="page-link" href="#">尾页</a></li>
	  </ul>
	</nav>
</div>
<jsp:include page="../common/page.jsp"></jsp:include>
<script src="/js/checkbox.js"></script>
<script>
	function query(){
		var params = $("form").serialize();
		reload(params);
	}
	
	function add(){
		openPage("/article/add?content1=content");
	}
	
	function gotoPage(pageNo){
		$("[name=pageNum]").val(pageNo);
		query();
	}
	
	function addHot(id){
		$.post("/admin/article/addHot",{id:id},function(res){
			reload();
		});
	}
	function add(){
		openPage("/article/add?content1=content");
	}
</script> 