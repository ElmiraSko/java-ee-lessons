<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="ru">
<jsp:include page="head.jsp"/>
<body>
<jsp:include page="menu.jsp"/>
<div class="container">
    <h1 class="text-center">Product list</h1>
    <div class="row py-2">
        <div class="col-12">
            <c:url value="/new" var="newEditUrl"/>
            <a class="btn btn-primary" href="${newEditUrl}">Add product</a>
        </div>
    </div>

    <table class="table table-striped table-bordered">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Id</th>
            <th scope="col">Name</th>
            <th scope="col">Description</th>
            <th scope="col">Price</th>
            <th scope="col">Actions</th>
        </tr>
        </thead>
        <tbody>

        <c:forEach var="product" items="${requestScope.products}">
            <tr>
                <th scope="row"> <c:out value="${product.id}"/> </th>
                <td> <c:out value="${product.name}"/></td>
                <td> <c:out value="${product.description}"/></td>
                <td> <c:out value="${product.price}"/></td>
                <td>
                    <c:url value="/edit" var="productEditUrl">
                        <c:param name="id" value="${product.id}"/>
                    </c:url>
                    <a class="btn btn-primary"  href="${productEditUrl}">
                        <i class="fas fa-edit"></i></a>
                    <a class="btn btn-danger" href="#">
                        <i class="far fa-trash-alt"></i></a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<jsp:include page="scripts.jsp"/>
</body>
</html>

