<%@include file="template/header.jsp" %>
<%@include file="template/menu.jsp" %>
<c:set var="categorias" 
       scope="page" 
value="<%=servicio.getCategorias()%>"/>

<c:set var="productos" 
       scope="page" 
value="<%=servicio.getProductos()%>"/>       

<div class="row">
    <div class="col s6 offset-s3">
        <div class="card-panel">
            <h4 class="center-align">Productos</h4>
            <form action="control.do" method="post" enctype="multipart/form-data">
                <label>Nombre</label>
                <input type="text" name="nombre"/>

                <label>Precio</label>
                <input type="text" name="precio"/>

                <label>Unidad</label>
                <input type="text" name="unidad"/>

                <label>Descripcion</label>
                <textarea name="descripcion" class="materialize-textarea">                               
                </textarea>
                
                <label>Categoria</label>
                <select name="idcategoria">
                    <c:forEach items="${pageScope.categorias}" var="c">
                        <option value="${c.idCategoria}">
                            ${c.nombreCategoria}
                        </option>
                    </c:forEach>
                </select>

                <div class="file-field input-field">
                    <div class="btn">
                        <span>Foto</span>
                        <input type="file" name="foto">
                    </div>
                    <div class="file-path-wrapper">
                        <input class="file-path validate" type="text">
                    </div>
                </div>



                <button name="boton" value="nuevoproducto" class="btn">
                    crear
                </button>
            </form>
            <br>${requestScope.msg}
            <hr>
            <table class="bordered">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Precio</th>
                        <th>Unidad</th>
                        <th>Categoria</th>
                        <th>Foto</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${pageScope.productos}" var="p">
                        <tr>
                            <td>${p.idProducto}</td>
                            <td>${p.nombreProducto}</td>
                            <td>${p.precioProducto}</td>
                            <td>${p.unidadesProducto}</td>
                            <td>${p.categoria.nombreCategoria}</td>
                            <td>
                                <x:tagImg array="${p.fotoProducto}"
                                              tam="50"
                                              />


                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>       




<%@include file="template/footer.jsp" %>