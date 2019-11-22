<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>用户列表</title>
<script type="text/javascript" src="/resource/js/cms.js"></script>
<script type="text/javascript">

function modify(id){
	$("#content-wrapper").load("/special/updateSpecial?id="+id)
}

function add(){
	$("#content-wrapper").load("/special/addSpecial")
}


function addArticleToSpecial(id){
	$("#content-wrapper").load("/special/addArticleToSpecial?id="+id)
}
function delSpecial(id){
	$("#content-wrapper").load("/special/delSpecial?id="+id)
}

</script>


</head>
<body>
<div class="container-fluid">
		<button type="button" class="btn btn-info" onclick="add()">添加专题</button>
		</div>

		<table class="table table-sm  table-hover table-bordered ">
			<thead class="thead-light">
				<tr align="center">
					<td>标题</td>
					<td>文章个数</td>
					<td>操作</td>
				</tr>
			</thead>
			<c:forEach items="${specials}" var="special" varStatus="index">
				<tr align="center">
					<td>${special.title}</td>
					<td>${special.articleNum}</td>
					<td>
						<button type="button" class="btn btn-info" onclick="addArticleToSpecial(${special.id})">追加文章</button> 
					    <button type="button" class="btn btn-info" onclick="modify(${special.id})">修改专题</button> 
					    <button type="button" class="btn btn-info" onclick="delSpecial(${special.id})">删除专题</button> 
					
					</td>
				</tr>

			</c:forEach>

		</table>
		<div>
			${page}
		</div>
	</div>
</body>
</html>