<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.servicetype.model.*"%>

<%
    ServiceTypeVO serviceTypeVO =
        (ServiceTypeVO) request.getAttribute("serviceTypeVO");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>修改服務類型</title>
</head>
<body>

<h2>修改服務類型</h2>

<% if (serviceTypeVO == null) { %>
    <p style="color:red;">沒有收到 serviceTypeVO，請檢查 Servlet 的 getOne_For_Update。</p>
<% } else { %>

<form method="post" action="<%= request.getContextPath() %>/servicetype/servicetype.do">

    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <td>服務類型編號</td>
            <td>
                <%= serviceTypeVO.getSvcTypeID() %>
                <input type="hidden" name="serviceTypeId"
                       value="<%= serviceTypeVO.getSvcTypeID() %>">
            </td>
        </tr>

        <tr>
            <td>類型名稱</td>
            <td>
                <input type="text" name="typeName"
                       value="<%= serviceTypeVO.getTypeName() %>">
            </td>
        </tr>

        <tr>
            <td>類型描述</td>
            <td>
                <input type="text" name="description"
                       value="<%= serviceTypeVO.getDescrip() %>">
            </td>
        </tr>

        <tr>
            <td>類型狀態</td>
            <td>
                <select name="typeMode">
                    <option value="0" <%= serviceTypeVO.getTypeMode() == 0 ? "selected" : "" %>>
                        動態
                    </option>
                    <option value="1" <%= serviceTypeVO.getTypeMode() == 1 ? "selected" : "" %>>
                        靜態
                    </option>
                </select>
            </td>
        </tr>

        <tr>
            <td>圖片路徑</td>
            <td>
                <input type="text" name="imgURL"
                       value="<%= serviceTypeVO.getImgURL() %>">
            </td>
        </tr>

        <tr>
            <td>圖片預覽</td>
            <td>
                <img src="<%= request.getContextPath() %><%= serviceTypeVO.getImgURL() %>"
                     width="120">
            </td>
        </tr>
    </table>

    <br>

    <input type="hidden" name="action" value="update">
    <input type="submit" value="送出修改">

</form>

<% } %>

<br>
<a href="<%= request.getContextPath() %>/servicetype/servicetype.do?action=getAll">
    回全部服務類型
</a>

</body>
</html>