<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${article.title}</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1">
	
<meta name="keywords" content="${article.keywords }" />
<meta name="description" content="${article.summary }" />
<link rel="stylesheet"
	href="/resource/css/bootstrap.min.css">
<script src="${pageContext.request.contextPath}/resource/js/jquery-3.2.1.js"></script>
<script type="text/javascript" src="/resource/js/bootstrap.min.js"></script>
</head>
<body>  
<div class="container">
		
		<dl>
		    <dt><a href="javascript:window.close()">关闭窗口</a></dt>
			<dt>
			<h2>${article.title}</h2><br>
			标签：${article.tags}<br>
			来源：${article.original}
			</dt>
			<hr>
			
			<dd>${article.content}</dd>
			<dd><ul class="pagination"><li class="page-item">${adjStr}</li></ul></dd>
			
			<dd><div>
				<form action="">
					<input type="text" name="content">
					<input type="button" value="评论" onclick="commnent()">
				</form>
			</div>
				<hr>
				评论数量：${article.commentCnt}
			</dd>
			<dd><div id="mycommentList"></div></dd>
			
		</dl>
	

</div>
<script type="text/javascript">

	// 获取评论列表
	$(function(){
		$("#mycommentList").load("/comment/getComList?articleId=${article.id}");
	});
	
	
	
	function commnent(){
		
		var retext = $("[name='content']").val();
		//alert(retext)
		var id = ${article.id}
		//alert(id)
		if(retext != ""){
		$.ajax({ 
			type:"post",
			data:{content:retext,articleId:id},
			url:"/comment/addComment",
			success:function(res){
				if(res.result == 1){ 
					alert("发表成功!");
					$("#commentList").load("/comment/getComList?articleId=${article.id}" );
					history.go(0);
				} else {
					alert(res.message);
				}
			}
		})
		}else{
			alert("请输入评论内容");
		}
	}
</script>


</body>
</html>