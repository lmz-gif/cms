<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript" src="/resource/js/jquery-3.2.1.js">
<!--

//-->
</script>
<div class="container">
<form action="" id="special">
		<label>标题</label>
		<input name="title" id="title"/>
		<br/>
		<label>摘要</label>
		<textarea rows="10" cols="100" name="digest" id="digest"></textarea>
		<br/>
		<input type="button" value="提交" onclick="addData()"> 
		
		<script type="text/javascript">
			function addData(){
				$.post("/special/addSpecial",$("#special").serialize(),function(msg){
					if(msg.result==1){
						alert("处理成功")
						$("#content-wrapper").load("/special/managerSpecial")
					}else{
						alert(msg.errorMsg);
					}
				},"json")
			}
		</script>
</form>

</div>

