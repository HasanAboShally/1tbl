<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!--<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>-->
    
    <%
      // String  fff = request.getAttribute("key").toString();
      //String str = request.getParameter("key").toString();
      //String str2 = request.get
      //String str = ${it.user};
       //String stttr ="sdfsfdfs";
        String name = (String)request.getAttribute("key"); 
        String dgvd = (String) pageContext.findAttribute("key");
        //String name = (String)request.getAttribute("myname"); 
        String str="dsfsdf";
    %>

    
 <script type="text/javascript">
       String str = ${it.user};
</script>
    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1>

    </h1>
    <h1>Welcome ${it.user}!</h1>
    <p>
    items in your cart :<br />
    <c:forEach var="item" items="${it.items}">
         ${item}  <br/>
    </c:forEach>
  </p>
  
  <p>
  <%=pageContext.findAttribute("it")%>
  <%=pageContext.findAttribute("key")%>
   <%= name%>
  </p>
    
    
</body>
</html>