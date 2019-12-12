<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
request.setCharacterEncoding("UTF-8");
String htmlData = request.getParameter("content1") != null ? request.getParameter("content1") : "";
%>
  	<form class="form-inline">
	  <div class="form-group mx-sm-3 mb-2">
	    <input type="text" name="name" class="form-control" placeholder="请输入文章标题">
	  </div>
	  
	  <div class="form-group mx-sm-3 mb-2">
	     <select id="inputState" class="form-control">
	        <option selected>请选择频道...</option>
	        <option>科技</option>
	        <option>财经</option>
	        <option>国际</option>
	      </select>
	  </div>
	  <div class="form-group mx-sm-3 mb-2">
	     <select id="inputState" class="form-control">
	        <option selected>请选择审核状态...</option>
	        <option>已审核</option>
	        <option>未审核</option>
	      </select>
	  </div>
	  <button type="button" class="btn btn-primary mb-2" onclick="query()">查询</button>
	</form>
  
  	<table class="table">
  <thead>
    <tr>
      <th scope="col"><input type="checkbox" value="" id="defaultCheck1"></th>
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
    <tr>
      <th><input type="checkbox" value="" id="defaultCheck1"></th>
      <th scope="row">1</th>
      <td>卖电动车赚钱有多难？</td>
      <td>国际</td>
      <td>股票</td>
      <td>是</td>
      <td>已审核</td>
      <td>2019-11-20 09:58</td>
      <td>
      	<button type="button" class="btn btn-primary">审核</button>
      	<button type="button" class="btn btn-primary">加热</button>
      </td>
    </tr>
    <tr>
      <td><input type="checkbox" value="" id="defaultCheck1"></td>
      <td scope="row">2</td>
      <td>卖电动车赚钱有多难？</td>
      <td>国际</td>
      <td>股票</td>
      <td>是</td>
      <td>已审核</td>
      <td>2019-11-20 09:58</td>
      <td>
      	<button type="button" class="btn btn-primary">审核</button>
      	<button type="button" class="btn btn-primary">加热</button>
      </td>
    </tr>
    <tr>
      <td><input type="checkbox" value="" id="defaultCheck1"></td>
      <td scope="row">3</td>
      <td>卖电动车赚钱有多难？</td>
      <td>国际</td>
      <td>股票</td>
      <td>是</td>
      <td>已审核</td>
      <td>2019-11-20 09:58</td>
      <td>
      	<button type="button" class="btn btn-primary">审核</button>
      	<button type="button" class="btn btn-primary">加热</button>
      </td>
    </tr>
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
<script>
	function query(){
		var params = $("form").serialize();
		reload(params);
	}
	
	function add(){
		openPage("/article/add?content1=content");
	}
</script>