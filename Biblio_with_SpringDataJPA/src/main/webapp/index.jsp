<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<body>
<h2>Библиотека</h2>
<br/>

<form action="/people" method="get">
    <input type="submit" value="Список всех людей" />
</form>
<br/>
<form action="/books" method="get">
    <input type="submit" value="Список всех книг" />
</form>
<br/>
<form action="/books/search" method="get">
    <input type="submit" value="Искать книгу" />
</form>

<%--<form action="/test-batch-update/without" method="get">--%>
<%--    <input type="submit" value="without Ordinary update(1000 people) fori" />--%>
<%--</form>--%>

<%--<br/>--%>

<%--<form action="/test-batch-update/with" method="get">--%>
<%--    <input type="submit" value="without Ordinary update(1000 people) packet" />--%>
<%--</form>--%>
<%--<br/>--%>

<br/>
<form action="/people/new" method="get">
    <input type="submit" value="Create People" />
</form>
</body>
</html>
