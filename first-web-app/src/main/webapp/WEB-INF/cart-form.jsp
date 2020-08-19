<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="ru">
<jsp:include page="head.jsp"/>
<body>
<jsp:include page="menu.jsp"/>
<div class="container">
    <h1 class="text-center">Save lineItem</h1>
    <div class="row py-2">
        <c:url value="/cart/cartUpsert" var="cartPostUrl"/>
        <form action="${cartPostUrl}" method="post">
            <input type="hidden" id="id" name="id" value="${cartItem.id}">
            <div class="form-group">
                <label for="name">Name</label>
                <input type="text" id="name" name="name" class="form-control"
                       placeholder="Enter name" value="${cartItem.prodName}">
            </div>
            <div class="form-group">
                <label for="qty">Qty</label>
                <input type="text" id="qty"  name="qty" class="form-control"
                       placeholder="Enter qty" value="${cartItem.qty}">
            </div>
            <div class="form-group">
                <label for="price">Price</label>
                <input type="number" id="price" name="price" class="form-control" step="0.01" min="0"
                       placeholder="0,00" value="${cartItem.price}">
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
        </form>
    </div>
</div>
<jsp:include page="scripts.jsp"/>
</body>
</html>
