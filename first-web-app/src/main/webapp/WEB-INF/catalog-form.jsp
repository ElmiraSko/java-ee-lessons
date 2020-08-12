<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<jsp:include page="head.jsp"/>
<body>
<jsp:include page="menu.jsp"/>
<div class="container">
    <h1 class="text-center">Save lineItem</h1>
    <div class="row py-2">
        <c:url value="/catalog/catalogPost" var="catalogPostUrl"/>
        <form action="${catalogPostUrl}" method="post">
            <input type="hidden" id="id" name="id" value="${catalog.id}">
            <div class="form-group">
                <label for="name">Name</label>
                <input type="text" id="name" name="name" class="form-control"
                       placeholder="Enter name" value="${catalog.name}">
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
        </form>
    </div>
</div>
<jsp:include page="scripts.jsp"/>
</body>
</html>

