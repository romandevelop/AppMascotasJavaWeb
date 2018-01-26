<%@include file="template/header.jsp" %>

<%@include file="template/menu.jsp" %>


<c:set var="categorias" scope="page" value="<%= servicio.getCategorias() %>"/>


<div class="row">
    <div class="col s6 offset-s3">
        <div class="card-panel">
            <form action="control.do" method="POST">
                <label>Nombre Categoria</label>
                <input type="text" name="nombre"/>
                <button name="boton" value="nuevacategoria" class="btn">crear</button>
            </form>  
            <br>
            ${requestScope.msg}
            <hr>
            <table class="bordered">
                <thead>
                    <tr><th>ID</th> <th>Nombre</th></tr>
                </thead>
                <tbody>
                    <c:forEach items="${pageScope.categorias}" var="c">
                        <tr>
                            <td>${c.idCategoria}</td>   <td>${c.nombreCategoria}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>




<%@include file="template/footer.jsp" %>