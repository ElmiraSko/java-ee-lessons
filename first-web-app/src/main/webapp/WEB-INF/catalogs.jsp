<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="ru">
<jsp:include page="head.jsp"/>
<body>
<jsp:include page="menu.jsp"/>


<div class="container">
    <h1 class="text-center">Catalog</h1>
    <div class="row py-2">
        <div class="col-12">
            <c:url value="/catalog/new" var="newEditCatalogUrl"/>
            <a class="btn btn-primary" href="${newEditCatalogUrl}">Add item</a>
        </div>
    </div>

    <table class="table table-striped table-bordered">
        <thead class="thead-dark">
        <tr>
            <th scope="col">Id</th>
            <th scope="col">Name</th>
            <th scope="col">Actions</th>
        </tr>
        </thead>
        <tbody>

        <c:forEach var="catalogs" items="${requestScope.catalogs}">
            <tr>
                <th scope="row"> <c:out value="${catalogs.id}"/> </th>
                <td> <c:out value="${catalogs.name}"/></td>
                <td>
                    <c:url value="/catalog/edit" var="catalogEditUrl">
                        <c:param name="id" value="${catalogs.id}"/>
                    </c:url>
                    <a class="btn btn-primary"  href="${catalogEditUrl}">
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



