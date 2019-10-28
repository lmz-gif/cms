<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript" src="/resource/js/jquery-3.2.1.js"></script>

<div class="container">
	<label><b>专题名称:</b></label>${special.title}
	<br/>
	<label><b>专题摘要:</b></label>${special.digest}
	<br/>
	<label><b>专题文章:</b></label>
	<table border="1" style="width: 800px;text-align: center;border-radius: 20px;border: solid 1px black;" >
		<tr>
			<td>文章id</td>
			<td>文章标题</td>
			<td>发布时间</td>
			<td>操作</td>
		</tr>
		<c:forEach items="${special.articleList}" var="article">
			<tr>
				<td>${article.id}</td>
				<td>${article.title}</td>
				<td>${article.created}</td>
				<td><a href="javascript:remove(${special.id},${article.id})">移除</a></td>
			</tr>
		</c:forEach>
	</table>
	<br/>
	
	<label><b>添加新的文章</b></label><br> 
	<select name="articleId" id="articleId">
		<c:forEach items="${articles }" var="article">
			<option value="${article.id }">${article.title }</option>
		</c:forEach>
	</select>  
	<input type="button" onclick="addArticle()" value="添加文章">
	<br/>
	
	<script type="text/javascript">
		
	   function addArticle(){
		   
		   $.post("/special/addArticleToSpecial",{specialId:${special.id},articleId:$("#articleId").val()},function(msg){
				if(msg.result==1){
					alert("处理成功")
					$("#content-wrapper").load("/special/addArticleToSpecial?id="+${special.id});
				}else{
					alert(msg.errorMsg);
				}
			},"json")
	   }
	
		function remove(specialId,articleId){
			
			$.post("/special/delArticleFromSpecial",{sid:specialId,aid:articleId},function(msg){
				if(msg.result==1){
					$("#content-wrapper").load("/special/addArticleToSpecial?id="+specialId);
				}else{
					alert(msg.errorMsg);
				}
			},"json")
			
		}
	</script>
			

</div>