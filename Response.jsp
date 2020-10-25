	<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Map"%>
<%@ page import="ve.com.digitel.scratch.to.CardDataTO"%>
 <%
	//Evita el \"Caching\" en el servidor Proxy
	response.setHeader("Cache-Control", "no-store"); //HTTP 1.0
	response.setHeader("Pragma", "no-cache"); //HTTP 1.1
	response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Consulta de Tarjetas de Recarga</title>
<style type="text/css">
.style2 {
	color: #000000;
	font-size: 12px;
}
.style3 {
	font-family: "Tahoma", Tahoma, monospace;
	font-size: 12px;
	color: #FFFFFF;
}
.style4 {
	font-family: "Tahoma", Tahoma, monospace;
	font-size: 12px;
	color:#FFFFFF;
}

.style6 {
	color: #000000;
	font-family: "Tahoma", Tahoma, monospace;
	font-size: 12px;
}
.style7 {
	color: #274069;
	font-family: "Tahoma", Tahoma, monospace;
	font-size: 18px;
}
.style9 {
	font-family: "Tahoma", Tahoma, monospace;
	font-size: 12px;
}
</style>
</head>
<body>
<form name="vForm" method="post" action="inicio.jsp">
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<table width="337" height="20" border="0" align="center" bgcolor="#FFFFFF">
<tr>
<td colspan="2">
<div align="center" class="style7">Consulta de Tarjetas de Recarga</div>
</td>
</tr>
</table>
<table width="450" height="170" border="0" align="center">
	<tr>
	
		<td bgcolor="#274069">
		<div align="center" class="style3">      GSM      </div>
		</td>
		
		<td bgcolor="#274069">
		<div align="center" class="style3">  Fecha de Recarga </div>
		</td>
	</tr>
	<%
		ArrayList lista = (ArrayList) request.getAttribute("lista");

		CardDataTO cardDataTO = new CardDataTO();

		if (lista != null) {
			int i;

			for (i = 0; i < lista.size(); i++) {
				
				Map cardDataMap = (Map) lista.get(i);
				
				cardDataTO.setSubscriberId((String)cardDataMap.get("SUBSCRIBER_ID"));
				cardDataTO.setRechargeDateTime((String)cardDataMap.get("RECHARGE_DATE_TIME"));
	%>
	<tr>
		<td id='SUBSCRIBER_ID' bgcolor='#E2E2E2' class="style9" align="center"><%=cardDataTO.getSubscriberId()%></td>
		<td id='RECHARGE_DATE_TIME' bgcolor='#E2E2E2' class="style9" align="center"><%=cardDataTO.getRechargeDateTime()%></td>
		
	</tr>


	<%
		}
		}
	%>
	<tr>
		<td colspan="2">
		<div align="center"><label> <input name="Volver"
			type="submit" id="Volver" class="style9" value="Volver" /> </label></div>
		</td>
	</tr>
</table>
</form>
</body>
</html>
