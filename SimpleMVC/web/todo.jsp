<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spfn" uri="http://www.sitepoint.com/jsp/taglibs/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>To-Do List</title>
        <meta http-equiv="content-type"
              content="text/html; charset=iso-8859-1" />
        <link rel="stylesheet" type="text/css"
              href="<c:url value="/styles.css"/>" />
        <style type="text/css">
            body { background-color:gray; font-size=10pt; }
            H1 {font-size:20pt;}
            table {background-color:white;}
        </style>
    </head>
    <body>
        <H1>Entrevistas Online</H1>
        <hr/><p/>
        <% // Scriptlet 1: check whether the book list is ready
        %>
        <c:if test="${fn:length(toDoItems) > 0}">
            <form action="<c:url value="/DeleteItem.do"/>" method="post">
                <select name="deleteid" size="${spfn:max(2,fn:length(toDoItems))}">
                    <c:forEach var="toDoItem" items="${toDoItems}">
                        <option value="${toDoItem.tel}">${fn:escapeXml(toDoItem)}</option>
                    </c:forEach>
                </select><br>
                <input type="submit" value="Delete Selected Item"/>
            </form>
        </c:if>
        <form action="<c:url value="/additem.jsp"/>" method="post">
            <input type="submit" value="Add New Item"/>
        </form>
    </body>
</html>
