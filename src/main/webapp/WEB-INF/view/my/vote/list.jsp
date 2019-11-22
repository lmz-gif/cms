<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
<script type="text/javascript">

function myopen(id){
	window.open("/vote/getVote?arId="+id,"_blank");
	
}

</script>
</head>
<body>

	<c:forEach items="${votes}" var="article">
		<dl>
			<dt><a href="javascript:myopen(${article.id })">${article.title }</a></dt>
		</dl>
		<hr>
	</c:forEach>
	


</body>
<script type="text/javascript">
	
</script>
</html>