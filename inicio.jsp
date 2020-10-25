<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" session="true"%>
<%
	HttpSession sesion = request.getSession(false);

	if (sesion == null) {
		System.out.println("************************* null");
%>
		<jsp:forward page="index.jsp"></jsp:forward>
<%
	}

	String mensaje = (String) request.getAttribute("mensaje");
	
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
<script type="text/javascript">
	function validarCampo(form) {

		if (form.scratch.value == ""){
			alert("Por favor ingrese el código de la tarjeta que desea consultar");
			//location.href= 'index.jsp';
			form.scratch.focus();
			//return false;
			//self.location.href = 'index.jsp'; 
		} 
		else
		if(isNaN(form.scratch.value)){
				alert("El codigo ingresado no es un dato numerico")
				form.scratch.focus();
				return false;
		}
		else
			form.submit();
	}
</script>
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
	color: #FFFFFF;
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
<table width="700px" border="0" align="center">
	<tr>
		<td>
		<form id="form1" name="form1" method="post"
			onsubmit="return validarCampo(this);" action="BuscarScratchServlet">
		<p>&nbsp;</p>
		<p>&nbsp;</p>
		<p>&nbsp;</p>
		<label></label>
		<div align="justify">
		<table width="337" height="20" border="0" align="center"
			bgcolor="#FFFFFF">
			<tr>
				<td colspan="2">
				<div align="center" class="style7">Consulta de Tarjetas de Recarga</div>
				</td>
			</tr>
		</table>
		<table width="337" height="125" border="0" align="center"
			bgcolor="#FFFFFF">
			<tr>
				<td colspan="2" bgcolor="#274069">
				<div align="center" class="style3">Datos de la tarjeta a
				consultar</div>
				</td>
			</tr>
			<tr>
				<td width="355" bgcolor="#E2E2E2">
				<div align="left" class="style6">Codigo de la Tarjeta (Scratch
				Card):</div>
				</td>
				<td width="373" bgcolor="#E2E2E2"><label> <input
					name="scratch" type="text" id="scratch" value="" maxlength="10"
					size="30" /> </label></td>
			</tr>
			<tr>
				<td colspan="2" class="style9" align="center">
				<div align="center">
				<%
					if (mensaje != null) {
				%><img src="error.gif" align="top" width="15" height="15" /> <%=mensaje%>
				<%
					} else {
				%> <%
 	}
 %>
				</div>
				</td>
			</tr>
			<tr>
				<td colspan="2">
				<div align="center"><label> <input name="Buscar"
					type="submit" id="Buscar" class="style9" value="Buscar" /> </label></div>
				</td>
			</tr>

		</table>
		</div>
		</form>
		</td>
	</tr>
</table>
</body>
</html>