<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<script type="text/javascript">

	function myopen(id){
		// alert(id)
		window.open("/article/getDetail?aId="+id,"_blank");
		
	}
	
	
	function toDel(cid) {         // flag为确认是否为一个用户,1为真,0为假

		if (confirm("您确认要删除此评论吗?")) {
			$.ajax({
				url:"/comment/delComment",
				data:{comId:cid},
				method:"post",
				success:function(res){
					if (res.result == 0) {
						alert(res.message);
					} else if (res.result == 1) {
						alert(res.message);
						$("#commentList").load("/comment/getComList?articleId=${article.id}" );
						history.go(0);
					}
				}	
			});
		}
		
	}
</script>
</head>
<body>
	<c:forEach items="${commenPage.list}" var="comment">
		<dl>
			<%-- <dt>文章标题：<a href="javascript:myopen(${comment.articleId })">${comment.articleTitle}</a></dt> --%>
			<dt>${comment.user.username}：${comment.content}</dt>
			<dd>发布时间:
			  <fmt:formatDate value="${comment.created}"/>&emsp;
			  ${SESSION_USER_KEY.id}aa
			  ${comment.userId }
			  <c:if test="${SESSION_USER_KEY.id == comment.userId}">
			  <a href="javascript:toDel(${comment.id})">删除</a>
			  </c:if>
			</dd>
		</dl>
		<hr>
	</c:forEach>
	${pageUtil}
</body>

<script type="text/javascript">
	$(function(){
	    $('.page-link').click(function (e) {
	    	//获取点击的的url
	        var url = $(this).attr('data');
	    
	       //在中间区域显示地址的内容
	       $('#mycommentList').load(url);
	    });
		
	})
	
</script>
</html>