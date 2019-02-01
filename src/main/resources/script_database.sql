CREATE TABLE tipo_user (
    Id_Tipo_User INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    Tipo_User varchar(50) NOT NULL,
    CONSTRAINT Fk_Id_Tipo_User PRIMARY KEY (Id_Tipo_User)
);

INSERT INTO tipo_user (Tipo_User) VALUES ("Administrador");
INSERT INTO tipo_user (Tipo_User) VALUES ("Empleado");

CREATE TABLE usuarios (
    Id_User INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    Usuario varchar(20) NOT NULL,
    Contra varchar(50) NOT NULL,
    Id_Tipo_User int NOT NULL,
    CONSTRAINT Fk_Id_User PRIMARY KEY (Id_User),
    CONSTRAINT fk_tip_us FOREIGN KEY (Id_Tipo_User) REFERENCES tipo_user (Id_Tipo_User)
);

insert into usuarios (Usuario,Contra,Id_Tipo_User) values("SIANE","siane",1);

CREATE TABLE categoria (
    Id_Categoria INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    Nombre varchar(100) NOT NULL,
    Constraint Pk_Categoria Primary Key (Id_Categoria)
);

CREATE TABLE cliente (
    Id_Cliente INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    Nombre varchar(100) NOT NULL,
    Domicilio varchar(100) NOT NULL,
    Razon_social varchar(100) DEFAULT NULL,
    RFC varchar(60) NOT NULL,
    Correo varchar(80) DEFAULT NULL,
    Telefono int(11) NOT NULL,
    Nombre_Contacto varchar(100) NOT NULL,
    Id_User int NOT NULL,
    Constraint Pk_Cliente PRIMARY KEY (Id_Cliente),
    Constraint fk_use_clie Foreign key (Id_User) references usuarios (Id_User)
);

CREATE TABLE productos_paquetes (
    Id_Producto varchar(182) NOT NULL,
    Nombre varchar(100) NOT NULL,
    Descripcion varchar(255) NOT NULL,
    Marca varchar(50) NOT NULL,
    Precio double NOT NULL,
    Existencias INTEGER NOT NULL,
    Cantidad varchar(50) NOT NULL,
    Id_Categoria int NOT NULL,
    Id_User int NOT NULL,
    CONSTRAINT Fk_Id_Producto_p PRIMARY KEY (Id_Producto),
    Constraint fk_use_prop Foreign key (Id_User) references usuarios (Id_User),
    Constraint fk_cat_prop Foreign key (Id_Categoria) references categoria (Id_Categoria)
);

CREATE TABLE productos_unitarios (
    Id_Producto varchar(182) NOT NULL,
    Nombre varchar(100) NOT NULL,
    Descripcion varchar(255) NOT NULL,
    Marca varchar(50) NOT NULL,
    Precio double NOT NULL,
    Existencias INTEGER NOT NULL,
    Cantidad varchar(50) NOT NULL,
    Id_Categoria int NOT NULL,
    Id_User int NOT NULL,
    CONSTRAINT Fk_Id_Producto_u PRIMARY KEY (Id_Producto),
    CONSTRAINT fk_use_prou Foreign key (Id_User) references usuarios (Id_User),
    CONSTRAINT fk_cat_prou Foreign key (Id_Categoria) references categoria (Id_Categoria)
);

CREATE TABLE proveedor (
    Id_Proveedor INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    Nombre varchar(100) NOT NULL,
    Domicilio varchar(100) NOT NULL,
    Razon_social varchar(100) DEFAULT NULL,
    RFC varchar(60) NOT NULL,
    Correo varchar(80) DEFAULT NULL,
    Telefono int(11) NOT NULL,
    Nombre_Contacto varchar(100) NOT NULL,
    Id_User int NOT NULL,
    CONSTRAINT Fk_Id_Proveedor PRIMARY KEY (Id_Proveedor),
    CONSTRAINT fk_use_prov FOREIGN KEY (Id_User) REFERENCES usuarios (Id_User)
);

CREATE TABLE ventas (
    IdVenta INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    Fecha date NOT NULL,
    Total_Venta double NOT NULL,
    CONSTRAINT Fk_IdVenta PRIMARY KEY (IdVenta)
);

CREATE TABLE venta_cliente (
    IdVenta_E INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    IdCliente int(11) NOT NULL,
    IdProducto_u varchar(182) DEFAULT NULL,
    IdProducto_p varchar(182) DEFAULT NULL,
    Cantidad int(11) NOT NULL,
    Importe double NOT NULL,
    CONSTRAINT Fk_IdVenta_E PRIMARY KEY (IdVenta_E),
    CONSTRAINT fk_prop_ven FOREIGN KEY (IdProducto_p) REFERENCES productos_paquetes (Id_Producto),
    CONSTRAINT fk_clie_ven FOREIGN KEY (IdCliente) REFERENCES cliente (Id_Cliente),
    CONSTRAINT fk_prou_ven FOREIGN KEY (IdProducto_u) REFERENCES productos_unitarios (Id_Producto)
);

CREATE TABLE venta_temp (
    Clave_Pro varchar(182) NOT NULL,
    NombreP varchar(100) NOT NULL,
    Categoria_Pro varchar(100) NOT NULL,
    Precio_uni double NOT NULL,
    Cantidad int(11) NOT NULL,
    Importe double NOT NULL
);