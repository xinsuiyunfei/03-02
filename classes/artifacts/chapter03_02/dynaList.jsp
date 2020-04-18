<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ page import="com.xtgj.j2ee.chapter03.car.beans.*"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>dynaList.jsp</title>
		<script type="text/javascript">
    	var xmlHttp;
    	//����XMLHttpRequest����
    	function createXMLHttpRequest(){
    		if(window.XMLHttpRequest){
    			xmlHttp = new XMLHttpRequest();
    		}
    		else if(window.ActiveXObject){
    			try{
    				xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
    			}catch(e){
    				try{
    				xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
    				}catch(e){}
    			}
    		}
    	}
    	//��ճ����б�
    	function clearModelList(){
    		var models = document.getElementById("model");
    		while(models.childNodes.length>0){
    			models.removeChild(models.firstChild);
    		}
    	}
    	//������ѯ�ַ���
    	function createQueryString(year,make){
    		var queryStr = "year=" +year +"&make=" +make;
    		return queryStr;
    	}
    	//�����첽����(ˢ�³����б�)
    	function refreshModels(){
    		var year = document.getElementById("year").value;
    		var make = document.getElementById("make").value;
    		if(year==""||make==""){
    			clearModelList();
    			return;
    		}
    		createXMLHttpRequest();
    		var url = "SearchModel?" + createQueryString(year,make);
    		xmlHttp.onreadystatechange = handleStateChange;
    		xmlHttp.open("get",url,true);
    		xmlHttp.send(null);
    	}
    	//�����صĽ��
    	function handleStateChange(){
    		if(xmlHttp.readyState==4 && xmlHttp.status==200){
    			clearModelList();
    			var res = xmlHttp.responseXML
                           .getElementsByTagName("model");
    			var model = document.getElementById("model");
    			var option = null;
    			var text = null;
    			for(var i=0;i<res.length;i++){
    				option = document.createElement("option");
    				text = document.createTextNode(res[i]
                            .firstChild.nodeValue);
    				option.appendChild(text);
    				model.appendChild(option);
    			}
    		}
    	}
    </script>
	</head>

	<body>
		<%
			DBOperator db = new DBOperator();
			List years = db.findYears();
			List makes = db.findMakes();
		%>

		<h1>
			ѡ����ݣ�Ʒ�ƺͳ���
		</h1>
		<form action="index.jsp">
			<b>���:</b>
			<select id="year" style="width: 150px;" onchange="refreshModels();">
				<option value="">
					��ѡ��
				</option>
				<%
					for (Iterator it = years.iterator(); it.hasNext();) {
						Integer year = (Integer) it.next();
				%>
				<option value="<%=year.intValue()%>">
					<%=year.intValue()%></option>
				<%
					}
				%>
			</select>
			<br />
			<br />

			<b>Ʒ��:</b>
			<select id="make" style="width: 150px" onchange="refreshModels();">
				<option value="">
					��ѡ��
				</option>
				<%
					for (Iterator it = makes.iterator(); it.hasNext();) {
						String make = (String) it.next();
				%>
				<option value="<%=make%>"><%=make%></option>
				<%
					}
				%>
			</select>
			<br />
			<br />

			<b>����</b>
			<select id="model" size="6" style="width: 150px;">

			</select>
			<br>
			<br>
			<input type="submit" value="����">
			&nbsp;&nbsp;
			<a href="#">�鿴����</a>
		</form>
	</body>
</html>




