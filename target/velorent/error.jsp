<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="pageTitle" value="Ошибка"/>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<h1>Что-то пошло не так</h1>
<p class="msg error">
    Код ошибки: ${requestScope['jakarta.servlet.error.status_code']}.
    <c:if test="${not empty pageContext.exception}">${pageContext.exception.message}</c:if>
</p>
<p><a class="btn" href="${pageContext.request.contextPath}/main">На главную</a></p>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
