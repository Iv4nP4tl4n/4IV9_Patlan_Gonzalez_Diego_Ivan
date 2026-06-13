DROP DATABASE IF EXISTS proyecto_perron;
CREATE DATABASE proyecto_perron;
USE proyecto_perron;

CREATE TABLE rol (
    id_rol INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) UNIQUE NOT NULL,
    descripcion VARCHAR(100)
);

CREATE TABLE permiso (
    id_permiso INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) UNIQUE NOT NULL,
    descripcion VARCHAR(100)
);

CREATE TABLE rol_permiso (
    id_rol INT,
    id_permiso INT,
    PRIMARY KEY (id_rol, id_permiso),
    FOREIGN KEY (id_rol) REFERENCES rol(id_rol),
    FOREIGN KEY (id_permiso) REFERENCES permiso(id_permiso)
);

CREATE TABLE empleado (
    id_empleado INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(15),
    usuario VARCHAR(50) UNIQUE NOT NULL,
    contraseña VARCHAR(255) NOT NULL,
    estado BOOLEAN DEFAULT TRUE,
    id_rol INT,
    FOREIGN KEY (id_rol) REFERENCES rol(id_rol)
);

CREATE TABLE categoria (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE tipo_mascota (
    id_tipo_mascota INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE producto (
    id_producto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    precio_venta DECIMAL(10,2) NOT NULL,
    costo DECIMAL(10,2) DEFAULT 0,
    stock INT NOT NULL DEFAULT 0,
    stock_minimo INT DEFAULT 5,
    codigo_barras VARCHAR(50) UNIQUE,
    estado BOOLEAN DEFAULT TRUE,
    id_categoria INT,
    id_tipo_mascota INT,
    FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria),
    FOREIGN KEY (id_tipo_mascota) REFERENCES tipo_mascota(id_tipo_mascota)
);

CREATE TABLE tipo_producto_proveedor (
    id_tipo INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE proveedor (
    id_proveedor INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(15),
    direccion VARCHAR(150),
    estado BOOLEAN DEFAULT TRUE,
    id_tipo INT,
    FOREIGN KEY (id_tipo) REFERENCES tipo_producto_proveedor(id_tipo)
);

CREATE TABLE metodo_pago (
    id_metodo INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) UNIQUE NOT NULL
);

-- ✅ ESTRUCTURA CORREGIDA PARA QUE FUNCIONE CON TU CÓDIGO JAVA
CREATE TABLE venta (
    id_venta INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    id_empleado INT NOT NULL,
    id_metodo INT NOT NULL DEFAULT 1,
    observaciones TEXT NULL,
    estado TINYINT(1) NOT NULL DEFAULT 1,
    subtotal DECIMAL(10,2),
    iva DECIMAL(10,2),
    total DECIMAL(10,2),
    FOREIGN KEY (id_empleado) REFERENCES empleado(id_empleado),
    FOREIGN KEY (id_metodo) REFERENCES metodo_pago(id_metodo)
);

CREATE TABLE detalle_venta (
    id_detalle INT PRIMARY KEY AUTO_INCREMENT,
    id_venta INT NOT NULL,
    id_producto INT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    importe DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_venta) REFERENCES venta(id_venta) ON DELETE CASCADE,
    FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
);

CREATE TABLE ticket (
    id_ticket INT PRIMARY KEY AUTO_INCREMENT,
    numero_ticket VARCHAR(100) NOT NULL UNIQUE,
    fecha_hora DATETIME NOT NULL,
    id_venta INT NOT NULL,
    id_empleado INT NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_venta) REFERENCES venta(id_venta),
    FOREIGN KEY (id_empleado) REFERENCES empleado(id_empleado)
);

CREATE TABLE estado_pedido (
    id_estado INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE pedido (
    id_pedido INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    total_pedido DECIMAL(10,2),
    id_proveedor INT,
    id_empleado INT,
    id_estado INT,
    FOREIGN KEY (id_proveedor) REFERENCES proveedor(id_proveedor),
    FOREIGN KEY (id_empleado) REFERENCES empleado(id_empleado),
    FOREIGN KEY (id_estado) REFERENCES estado_pedido(id_estado)
);

CREATE TABLE detalle_pedido (
    id_pedido INT,
    id_producto INT,
    cantidad INT NOT NULL,
    costo_unitario DECIMAL(10,2),
    subtotal DECIMAL(10,2),
    PRIMARY KEY (id_pedido, id_producto),
    FOREIGN KEY (id_pedido) REFERENCES pedido(id_pedido),
    FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
);

CREATE TABLE reporte_incidencias (
  id_reporte INT PRIMARY KEY AUTO_INCREMENT,
  fecha DATETIME,
  id_empleado INT,
  producto VARCHAR(150),
  tipo_incidencia VARCHAR(100),
  titulo VARCHAR(200),
  descripcion TEXT,
  estado VARCHAR(50) DEFAULT 'Pendiente',
  FOREIGN KEY (id_empleado) REFERENCES empleado(id_empleado)
);

-- 🔽 DATOS INSERTADOS 🔽

INSERT INTO rol (nombre, descripcion) VALUES
('gerente', 'Acceso total al sistema'),
('cajero', 'Acceso a ventas y tickets');

INSERT INTO permiso (nombre, descripcion) VALUES
('ver_inicio','Ver pantalla de inicio'),
('ver_ventas','Acceder al modulo de ventas'),
('ver_productos','Acceder al modulo de productos'),
('ver_proveedores','Acceder al modulo de proveedores'),
('ver_pedidos','Acceder al modulo de pedidos'),
('ver_reportes','Acceder al modulo de reportes'),
('ver_usuarios','Acceder al modulo de usuarios'),
('ver_tickets','Acceder al modulo de tickets'),
('crear_venta','Crear nuevas ventas'),
('cancelar_venta','Cancelar ventas'),
('crear_producto','Agregar productos'),
('editar_producto','Modificar productos'),
('eliminar_producto','Eliminar productos'),
('crear_proveedor','Agregar proveedores'),
('editar_proveedor','Modificar proveedores'),
('eliminar_proveedor','Eliminar proveedores'),
('crear_pedido','Crear pedidos'),
('editar_pedido','Modificar pedidos'),
('eliminar_pedido','Eliminar pedidos'),
('crear_usuario','Agregar usuarios'),
('editar_usuario','Modificar usuarios'),
('eliminar_usuario','Eliminar usuarios'),
('generar_ticket','Generar tickets'),
('ver_reporte_ventas','Ver reporte de ventas');

INSERT INTO rol_permiso
SELECT 1, id_permiso FROM permiso;

INSERT INTO rol_permiso (id_rol,id_permiso)
SELECT 2,id_permiso
FROM permiso
WHERE nombre IN (
'ver_inicio',
'ver_ventas',
'ver_tickets',
'crear_venta',
'cancelar_venta',
'generar_ticket'
);

INSERT INTO categoria (nombre) VALUES
('Alimento'),
('Medicamento'),
('Juguete'),
('Accesorio'),
('Higiene');

INSERT INTO tipo_mascota (nombre) VALUES
('Perro'),
('Gato'),
('Ave'),
('Roedor'),
('General');

INSERT INTO tipo_producto_proveedor (nombre) VALUES
('Alimentos'),
('Medicamentos'),
('Juguetes'),
('Accesorios'),
('Higiene'),
('Varios');

INSERT INTO metodo_pago (nombre) VALUES
('Efectivo'),
('Transferencia');

INSERT INTO estado_pedido (nombre) VALUES
('Pendiente'),
('Completado'),
('Cancelado');

INSERT INTO empleado (nombre, telefono, usuario, contraseña, estado, id_rol) VALUES
('Administrador', '5500000000', 'admin', 'admin123', TRUE, 1),
('Ivan Gerente', '5512345678', 'ivan', '12345', TRUE, 1),
('Cajero Demo', '5500000001', 'cajero', 'cajero123', TRUE, 2);

INSERT INTO producto (nombre, precio_venta, costo, stock, stock_minimo, codigo_barras, estado, id_categoria, id_tipo_mascota) VALUES
('Croquetas Pro Plan Adulto 20kg', 650.00, 500.00, 25, 5, '750100000001', TRUE, 1, 1),
('Croquetas Royal Canin Cachorro 15kg', 780.00, 610.00, 18, 5, '750100000002', TRUE, 1, 1),
('Alimento Gatitos Purina 7.5kg', 420.00, 310.00, 22, 5, '750100000003', TRUE, 1, 2),
('Alimento Gato Adulto Hills 8kg', 510.00, 380.00, 14, 5, '750100000004', TRUE, 1, 2),
('Semillas Canarias 1kg', 45.00, 25.00, 30, 5, '750100000005', TRUE, 1, 3),
('Mezcla Aves Premium 5kg', 120.00, 75.00, 16, 5, '750100000006', TRUE, 1, 3),
('Alimento Hámster 500g', 35.00, 18.00, 28, 5, '750100000007', TRUE, 1, 4),
('Arena Sanitaria 20kg', 180.00, 120.00, 15, 5, '750100000008', TRUE, 5, 2),
('Shampoo Antipulgas 500ml', 95.00, 55.00, 20, 5, '750100000009', TRUE, 5, 1),
('Desodorante Ambiental 1L', 60.00, 35.00, 25, 5, '750100000010', TRUE, 5, 5),
('Pelota de Goma Resistente', 60.00, 30.00, 40, 10, '750100000011', TRUE, 3, 1),
('Hueso de Juguete Nylon', 85.00, 45.00, 32, 10, '750100000012', TRUE, 3, 1),
('Ratón de Peluche', 25.00, 12.00, 50, 10, '750100000013', TRUE, 3, 2),
('Jaula Pájaro Mediana', 350.00, 220.00, 8, 3, '750100000014', TRUE, 4, 3),
('Cama Perro Grande', 420.00, 280.00, 12, 3, '750100000015', TRUE, 4, 1),
('Collar Ajustable Mediano', 120.00, 70.00, 20, 5, '750100000016', TRUE, 4, 1),
('Correa Extensible 5m', 150.00, 90.00, 18, 5, '750100000017', TRUE, 4, 1),
('Vitaminas Perros 60tabl', 150.00, 90.00, 12, 3, '750100000018', TRUE, 2, 1),
('Desparasitante Gatos', 85.00, 50.00, 24, 5, '750100000019', TRUE, 2, 2),
('Vacuna Quintuple Perro', 220.00, 150.00, 10, 2, '750100000020', TRUE, 2, 1);

INSERT INTO proveedor (nombre, telefono, direccion, estado, id_tipo) VALUES
('NutriMascotas S.A.', '5511223344', 'Av. Animal 45, CDMX', 1, 1),
('Distribuidora Canina', '5522334455', 'Calle Perro 78, EdoMex', 1, 1),
('Alimentos Felinos', '5533445566', 'Blvd. Gato 123, Méx', 1, 1),
('Accesorios Peludos', '5544556677', 'Av. Mascota 56, CDMX', 1, 4),
('Higiene Animal', '5555667788', 'Calle Limpieza 90, EdoMex', 1, 5),
('Juguetes y Diversión', '5566778899', 'Parque Canino 22, CDMX', 1, 3),
('Camas y Comodidad', '5577889900', 'Av. Descanso 34, Hgo', 1, 4),
('Farmacia Veterinaria', '5588990011', 'Calle Salud 67, CDMX', 1, 2),
('Aves y Compañía', '5599001122', 'Blvd. Pájaro 89, EdoMex', 1, 1),
('Peces y Acuarios', '5500112233', 'Av. Agua 11, Méx', 1, 6),
('Roedores y Jaulas', '5511334455', 'Calle Pequeño 44, CDMX', 1, 4),
('Alimentos Naturales', '5522446688', 'Camino Orgánico 76, EdoMex', 1, 1),
('Suplementos y Vitaminas', '5533557799', 'Av. Energía 98, CDMX', 1, 2),
('Ropa y Accesorios', '5544668800', 'Calle Moda 15, Hgo', 1, 4),
('Transportadoras Seguras', '5555779911', 'Av. Viaje 27, CDMX', 1, 6),
('Cuidado y Estética', '5566880022', 'Calle Belleza 39, EdoMex', 1, 5),
('Equipo Veterinario', '5577991133', 'Av. Médico 51, Méx', 1, 2),
('Semillas y Forrajes', '5588002244', 'Camino Campo 63, CDMX', 1, 1),
('Mascotas y Más', '5599113355', 'Av. Central 200, EdoMex', 1, 6),
('Mundo Animal', '5500224466', 'Calle Principal 100, CDMX', 1, 6);

INSERT INTO venta (fecha, hora, id_empleado, id_metodo, observaciones, estado, subtotal, iva, total) VALUES
('2026-01-12', '10:30:00', 2, 1, 'Cliente frecuente', 1, 780.00, 124.80, 904.80),
('2026-01-25', '14:15:00', 3, 2, 'Compra grande', 1, 1240.00, 198.40, 1438.40),
('2026-02-03', '11:40:00', 2, 1, '', 1, 245.00, 39.20, 284.20),
('2026-02-18', '16:20:00', 3, 1, 'Para regalo', 1, 420.00, 67.20, 487.20),
('2026-03-05', '09:50:00', 2, 2, '', 1, 960.00, 153.60, 1113.60),
('2026-03-22', '12:10:00', 3, 1, 'Lleva para dos mascotas', 1, 310.00, 49.60, 359.60),
('2026-04-01', '17:30:00', 2, 1, '', 1, 680.00, 108.80, 788.80),
('2026-04-15', '10:00:00', 3, 2, 'Compra mensual', 1, 1520.00, 243.20, 1763.20),
('2026-04-30', '13:45:00', 2, 1, '', 1, 180.00, 28.80, 208.80),
('2026-05-08', '15:25:00', 3, 1, 'Descuento aplicado', 1, 540.00, 86.40, 626.40),
('2026-05-19', '08:30:00', 2, 2, '', 1, 890.00, 142.40, 1032.40),
('2026-05-27', '11:15:00', 3, 1, 'Solo accesorios', 1, 235.00, 37.60, 272.60),
('2026-06-02', '14:50:00', 2, 1, '', 1, 720.00, 115.20, 835.20),
('2026-06-07', '16:40:00', 3, 2, 'Pedido especial', 1, 1150.00, 184.00, 1334.00),
('2026-06-09', '09:20:00', 2, 1, '', 1, 340.00, 54.40, 394.40),
('2026-06-10', '12:35:00', 3, 1, 'Lleva medicina', 1, 460.00, 73.60, 533.60),
('2026-06-11', '10:10:00', 2, 2, '', 1, 980.00, 156.80, 1136.80),
('2026-06-11', '15:55:00', 3, 1, 'Juguetes y comida', 1, 610.00, 97.60, 707.60),
('2026-06-12', '08:45:00', 2, 1, '', 1, 275.00, 44.00, 319.00),
('2026-06-12', '11:30:00', 3, 2, 'Compra grande de inventario', 1, 1850.00, 296.00, 2146.00);

INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario, importe) VALUES
(1, 2, 1, 780.00, 780.00),
(2, 1, 1, 650.00, 650.00), (2, 8, 2, 180.00, 360.00), (2, 15, 1, 420.00, 420.00),
(3, 11, 2, 60.00, 120.00), (3, 16, 1, 120.00, 120.00),
(4, 3, 1, 420.00, 420.00),
(5, 1, 1, 650.00, 650.00), (5, 18, 2, 150.00, 300.00),
(6, 9, 1, 95.00, 95.00), (6, 10, 2, 60.00, 120.00),
(7, 4, 1, 510.00, 510.00), (7, 17, 1, 150.00, 150.00),
(8, 2, 2, 780.00, 1560.00),
(9, 8, 1, 180.00, 180.00),
(10, 12, 2, 85.00, 170.00), (10, 19, 2, 85.00, 170.00),
(11, 5, 4, 45.00, 180.00), (11, 14, 2, 350.00, 700.00),
(12, 13, 5, 25.00, 125.00), (12, 16, 1, 120.00, 120.00),
(13, 6, 3, 120.00, 360.00), (13, 15, 1, 420.00, 420.00),
(14, 7, 10, 35.00, 350.00), (14, 20, 3, 220.00, 660.00),
(15, 17, 2, 150.00, 300.00),
(16, 18, 1, 150.00, 150.00), (16, 19, 2, 85.00, 170.00),
(17, 1, 1, 650.00, 650.00), (17, 4, 1, 510.00, 510.00),
(18, 11, 3, 60.00, 180.00), (18, 12, 4, 85.00, 340.00),
(19, 9, 2, 95.00, 190.00), (19, 10, 1, 60.00, 60.00),
(20, 1, 2, 650.00, 1300.00), (20, 2, 1, 780.00, 780.00);

INSERT INTO ticket (numero_ticket, fecha_hora, id_venta, id_empleado, total) VALUES
('TKT-2026-0001', '2026-01-12 10:30:00', 1, 2, 904.80),
('TKT-2026-0002', '2026-01-25 14:15:00', 2, 3, 1438.40),
('TKT-2026-0003', '2026-02-03 11:40:00', 3, 2, 284.20),
('TKT-2026-0004', '2026-02-18 16:20:00', 4, 3, 487.20),
('TKT-2026-0005', '2026-03-05 09:50:00', 5, 2, 1113.60),
('TKT-2026-0006', '2026-03-22 12:10:00', 6, 3, 359.60),
('TKT-2026-0007', '2026-04-01 17:30:00', 7, 2, 788.80),
('TKT-2026-0008', '2026-04-15 10:00:00', 8, 3, 1763.20),
('TKT-2026-0009', '2026-04-30 13:45:00', 9, 2, 208.80),
('TKT-2026-0010', '2026-05-08 15:25:00', 10, 3, 626.40),
('TKT-2026-0011', '2026-05-19 08:30:00', 11, 2, 1032.40),
('TKT-2026-0012', '2026-05-27 11:15:00', 12, 3, 272.60),
('TKT-2026-0013', '2026-06-02 14:50:00', 13, 2, 835.20),
('TKT-2026-0014', '2026-06-07 16:40:00', 14, 3, 1334.00),
('TKT-2026-0015', '2026-06-09 09:20:00', 15, 2, 394.40),
('TKT-2026-0016', '2026-06-10 12:35:00', 16, 3, 533.60),
('TKT-2026-0017', '2026-06-11 10:10:00', 17, 2, 1136.80),
('TKT-2026-0018', '2026-06-11 15:55:00', 18, 3, 707.60),
('TKT-2026-0019', '2026-06-12 08:45:00', 19, 2, 319.00),
('TKT-2026-0020', '2026-06-12 11:30:00', 20, 3, 2146.00);

INSERT INTO pedido (fecha, total_pedido, id_proveedor, id_empleado, id_estado) VALUES
('2026-01-08', 4500.00, 1, 1, 2),
('2026-01-20', 2800.00, 2, 2, 2),
('2026-02-05', 1200.00, 8, 1, 1),
('2026-02-22', 3500.00, 3, 2, 2),
('2026-03-10', 1800.00, 6, 1, 2),
('2026-03-28', 5200.00, 4, 2, 1),
('2026-04-05', 950.00, 9, 1, 2),
('2026-04-18', 4100.00, 7, 2, 2),
('2026-04-30', 750.00, 5, 1, 3),
('2026-05-12', 3200.00, 10, 2, 2),
('2026-05-25', 2100.00, 12, 1, 1),
('2026-06-01', 4800.00, 11, 2, 2),
('2026-06-03', 1500.00, 15, 1, 2),
('2026-06-05', 3800.00, 14, 2, 1),
('2026-06-07', 600.00, 17, 1, 2),
('2026-06-08', 2900.00, 16, 2, 2),
('2026-06-09', 1700.00, 18, 1, 1),
('2026-06-10', 4300.00, 13, 2, 2),
('2026-06-11', 850.00, 19, 1, 2),
('2026-06-12', 5000.00, 20, 2, 1);

INSERT INTO detalle_pedido (id_pedido, id_producto, cantidad, costo_unitario, subtotal) VALUES
(1, 1, 5, 500.00, 2500.00), (1, 2, 4, 610.00, 2440.00),
(2, 8, 10, 120.00, 1200.00), (2, 9, 15, 55.00, 825.00),
(3, 18, 10, 90.00, 900.00),
(4, 3, 6, 310.00, 1860.00), (4, 4, 5, 380.00, 1900.00),
(5, 11, 20, 30.00, 600.00), (5, 12, 15, 45.00, 675.00),
(6, 14, 8, 220.00, 1760.00), (6, 15, 10, 280.00, 2800.00),
(7, 5, 20, 25.00, 500.00), (7, 6, 8, 75.00, 600.00),
(8, 16, 12, 70.00, 840.00), (8, 17, 10, 90.00, 900.00),
(9, 10, 10, 35.00, 350.00),
(10, 7, 30, 18.00, 540.00), (10, 13, 25, 12.00, 300.00),
(11, 1, 3, 500.00, 1500.00),
(12, 19, 20, 50.00, 1000.00), (12, 20, 10, 150.00, 1500.00),
(13, 5, 15, 25.00, 375.00), (13, 6, 10, 75.00, 750.00),
(14, 14, 5, 220.00, 1100.00), (14, 15, 8, 280.00, 2240.00),
(15, 9, 10, 55.00, 550.00),
(16, 11, 15, 30.00, 450.00), (16, 12, 12, 45.00, 540.00),
(17, 18, 8, 90.00, 720.00),
(18, 2, 3, 610.00, 1830.00), (18, 3, 4, 310.00, 1240.00),
(19, 7, 20, 18.00, 360.00),
(20, 1, 4, 500.00, 2000.00), (20, 4, 5, 380.00, 1900.00);