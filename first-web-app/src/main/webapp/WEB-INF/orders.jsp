<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="ru">
<jsp:include page="head.jsp"/>
<body>
<jsp:include page="menu.jsp"/>


<div class="container">
    <h1 class="text-center">Order list</h1>
    <div class="row py-2">
        <div class="col-12">
            <c:url value="/order/new" var="newEditOrderUrl"/>
            <a class="btn btn-primary" href="${newEditOrderUrl}">Add order</a>
        </div>
    </div>

    <table class="table table-striped table-bordered">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Id</th>
            <th scope="col">CustomerId</th>
            <th scope="col">Qty</th>
            <th scope="col">TotalPrice</th>
            <th scope="col">Date</th>
            <th scope="col">Actions</th>
        </tr>
        </thead>
        <tbody>

        <c:forEach var="orders" items="${requestScope.orders}">
            <tr>
                <th scope="row"> <c:out value="${orders.id}"/> </th>
                <td> <c:out value="${orders.customerId}"/></td>
                <td> <c:out value="${orders.qty}"/></td>
                <td> <c:out value="${orders.totalPrice}"/></td>
                <td> <c:out value="${orders.date}"/></td>
                <td>
                    <c:url value="/order/edit" var="orderEditUrl">
                        <c:param name="id" value="${orders.id}"/>
                    </c:url>
                    <a class="btn btn-primary"  href="${orderEditUrl}">
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


