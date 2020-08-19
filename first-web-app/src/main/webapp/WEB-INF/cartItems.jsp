<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="ru">
<jsp:include page="head.jsp"/>
<body>
<jsp:include page="menu.jsp"/>


<div class="container">
    <h1 class="text-center">Cart</h1>
    <div class="row py-2">
        <div class="col-12">
            <c:url value="/cart/new" var="newCartEditUrl"/>
            <a class="btn btn-primary" href="${newCartEditUrl}">Add item</a>
        </div>
    </div>

    <table class="table table-striped table-bordered">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Id</th>
            <th scope="col">Name</th>
            <th scope="col">Qty</th>
            <th scope="col">Price</th>
            <th scope="col">Actions</th>
        </tr>
        </thead>
        <tbody>

        <c:forEach var="cartItems" items="${requestScope.cartItems}">
            <tr>
                <th scope="row"> <c:out value="${cartItems.id}"/> </th>
                <td> <c:out value="${cartItems.prodName}"/></td>
                <td> <c:out value="${cartItems.qty}"/></td>
                <td> <c:out value="${cartItems.price}"/></td>
                <td>
                    <c:url value="/cart/edit" var="cartEditUrl">
                        <c:param name="id" value="${cartItems.id}"/>
                    </c:url>
                    <a class="btn btn-primary"  href="${cartEditUrl}">
                        <i class="fas fa-edit"></i></a>
                    <a class="btn btn-danger" href='<c:url value="/cart/delete?id=${cartItems.id}"/>'>
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


