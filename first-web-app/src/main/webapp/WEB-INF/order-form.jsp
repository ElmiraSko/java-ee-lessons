<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="ru">
<jsp:include page="head.jsp"/>
<body>
<jsp:include page="menu.jsp"/>
<div class="container">
    <h1 class="text-center">Save order</h1>
    <div class="row py-2">
        <c:url value="/order/upsert" var="orderPostUrl"/>
        <form action="${orderPostUrl}" method="post">
            <input type="hidden" id="id" name="id" value="${order.id}">
            <div class="form-group">
                <label for="customerId">CustomerId</label>
                <input type="text" id="customerId" name="customerId" class="form-control"
                       placeholder="Enter customerId" value="${order.customerId}">
            </div>
            <div class="form-group">
                <label for="qty">Qty</label>
                <input type="text" id="qty"  name="qty" class="form-control"
                       placeholder="Enter qty" value="${order.qty}">
            </div>
            <div class="form-group">
                <label for="price">TotalPrice</label>
                <input type="number" id="price" name="totalPrice" class="form-control" step="0.01" min="0"
                       placeholder="0,00" value="${order.totalPrice}">
            </div>
            <div class="form-group">
                <label for="date">Date</label>
                <input type="date" id="date" name="dateParam" class="form-control"
                       placeholder="date" value="${order.date}">
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
        </form>
    </div>
</div>
<jsp:include page="scripts.jsp"/>
</body>
</html>
