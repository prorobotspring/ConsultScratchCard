<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%
	String mensaje = (String) request.getAttribute("mensaje");
%>
<%
//Evita el \"Caching\" en el servidor Proxy
response.setHeader("Cache-Control","no-store"); //HTTP 1.0
response.setHeader("Pragma","no-cache"); //HTTP 1.1
response.setDateHeader("Expires",0);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Administración de Lista de Seguridad</title>
<script type="text/javascript">
	function validar(form) {

		if ((form.usuario.value == "") && (form.cpass.value == "")) {
			alert("Por favor ingrese su Usuario y su Clave");
			//location.href= 'index.jsp';
			form.usuario.focus();
			return false;
			//self.location.href = 'index.jsp'; 
		} else {

			if (form.usuario.value == "") {
				alert("Por favor ingrese su Usuario");
				form.usuario.focus();
				return false;
			} else {
				if (form.cpass.value == "") {
					alert("Por favor ingrese su Clave");
					form.cpass.focus();
					return false;
				} else {
					form.submit();
					
				}
			}
		}
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
<body onload="document.getElementById('usuario').focus()">
<table width="700px" border="0" align="center">
<tr>
<td>
<form id="form1" name="form1" method="post"
	onsubmit="return validar(this);" action="AutenticadorServlet">
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<label></label>
<div align="justify">
<table width="337" height="20" border="0" align="center" bgcolor="#FFFFFF">
<tr>
<td colspan="2">
<div align="center" class="style7">Gestión de Activación de Lineas</div>
</td>
</tr>
</table>
<table width="337" height="125" border="0" align="center" bgcolor="#FFFFFF">
	<tr>
		<td colspan="2" bgcolor="#274069">
		<div align="center" class="style3">Datos del usuario</div>
		</td>
	</tr>
	<tr>
		<td width="355" bgcolor="#E2E2E2">
		<div align="left" class="style6">Usuario</div>
		</td>
		<td width="373" bgcolor="#E2E2E2"><label> <input
			name="usuario" type="text" id="usuario" value="" maxlength="30"
			size="30" /> </label></td>
	</tr>
	<tr>
		<td bgcolor="#E2E2E2"><span class="style6">Clave</span></td>
		<td bgcolor="#E2E2E2"><label for="contrasena"></label> <input
			type="password" name="cpass" id="cpass" maxlength="30" size="30" /></td>
	</tr>
	<tr>
		<td colspan="2" class="style9" align="center">
		<div align="center">
		<%
			if (mensaje != null) {
		%><img src="imagenes/error.gif" align="top" width="15" height="15" />
		<%=mensaje%> <%
 	} else {
 %> <%
 	}
 %>
		</div>
		</td>
	</tr>
	<tr>
		<td colspan="2">
		<div align="center"><label> <input name="Aceptar"
			type="submit" id="Aceptar" class="style9" value="Aceptar" /> </label></div>
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
