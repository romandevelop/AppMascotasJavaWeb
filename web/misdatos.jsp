<%@include file="template/header.jsp" %>

<%@include file="template/menu.jsp" %>


<c:if test="${not empty sessionScope.admin}">
    
    <div class="row">
        <div class="col s6 offset-s3">
            <div class="card-panel">
                <h4 class="center-align">Mis Datos</h4>
                <form action ="control.do" method="post">
                    <label>Rut</label>
                    <input type="text" name="rut" value="${sessionScope.admin.rutUser}" readonly/>
                    <label>Nombre</label>
                    <input type="text" name="nombre" value="${sessionScope.admin.nombreUser}" readonly/>
                    <label>Apellido</label>
                    <input type="text" name="apellido" value="${sessionScope.admin.apellidoUser}" readonly/>
                    <label>Correo Actual: ${sessionScope.admin.emailUser}</label>
                    <input type="text" name="mail" value=""/>
                    <label>Clave</label>
                    <input type="password" name="clave1" value=""/>
                    <label>Confirmar Clave</label>
                    <input type="password" name="clave2" value=""/>
                    
                    <button class="btn" name="boton" value="editardatos">
                        actualizar
                    </button>
                    <br>
                    ${requestScope.msg}
                    
                </form>
            </div>
        </div>
    </div>
    
</c:if>


<%@include file="template/footer.jsp" %>