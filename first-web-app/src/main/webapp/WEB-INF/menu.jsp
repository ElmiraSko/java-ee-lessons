<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <c:url value="/" var="shopUrl"/>
    <a class="navbar-brand ${requestScope.activePage == 'productPage' ? 'active' : ''}"
       href="${shopUrl}">EShop</a>

    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item ${requestScope.activePage == 'catalogPage' ? 'active' : ''}">
                <c:url value="/catalog" var="catalogUrl"/>
                <a class="nav-link" href="${catalogUrl}">Catalog</a>
            </li>
            <li class="nav-item ${requestScope.activePage == 'cartItemPage' ? 'active' : ''}">
                <c:url value="/cart" var="cartUrl"/>
                <a class="nav-link" href="${cartUrl}">Cart</a>
            </li>
            <li class="nav-item ${requestScope.activePage == 'orderPage' ? 'active' : ''}">
                <c:url value="/order" var="orderUrl"/>
                <a class="nav-link" href="${orderUrl}">Order</a>
            </li>
        </ul>
        <form class="form-inline my-2 my-lg-0" action="#" method="post">
            <button class="btn btn-outline-success my-2 my-sm-0">Logout</button>
        </form>
    </div>
</nav>


