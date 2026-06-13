
USE proyecto_perron;

#Creamos las tablas

-- TABLA EMPLEADO
CREATE TABLE empleado (
    id_empleado INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(15),
    usuario VARCHAR(50) UNIQUE NOT NULL,
    contraseña VARCHAR(255) NOT NULL,
    rol VARCHAR(30) NOT NULL,
    estado BOOLEAN DEFAULT TRUE
);


-- TABLA PRODUCTO

CREATE TABLE producto (
    id_producto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    categoria VARCHAR(50),
    tipo_mascota VARCHAR(50),
    precio_venta DECIMAL(10,2) NOT NULL,
    costo DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL,
    estado VARCHAR(30),
    nivel_rotacion VARCHAR(30),
    codigo_barras VARCHAR(50) UNIQUE
);


-- TABLA INVENTARIO
CREATE TABLE inventario (
    id_inventario INT AUTO_INCREMENT PRIMARY KEY,
    stock_actual INT NOT NULL,
    stock_minimo INT NOT NULL,
    fecha_actualizacion DATE,
    merma INT DEFAULT 0,
    id_producto INT UNIQUE,
    FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
);


-- TABLA PROVEEDOR
CREATE TABLE proveedor (
    id_proveedor INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(15),
    direccion VARCHAR(150),
    tipo_productos VARCHAR(100),
    calificacion DECIMAL(3,2),
    estado VARCHAR(30)
);

-- TABLA VENTA
CREATE TABLE venta (
    id_venta INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    hora TIME NOT NULL,
    subtotal DECIMAL(10,2),
    iva DECIMAL(10,2),
    total DECIMAL(10,2),
    metodo_pago VARCHAR(30),
    estado VARCHAR(30),
    id_empleado INT,
    FOREIGN KEY (id_empleado) REFERENCES empleado(id_empleado)
);


-- TABLA DETALLE_VENTA
CREATE TABLE detalle_venta (
    id_venta INT,
    id_producto INT,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10 , 2 ),
    importe DECIMAL(10 , 2 ),
    PRIMARY KEY (id_venta , id_producto),
    FOREIGN KEY (id_venta)
        REFERENCES venta (id_venta),
    FOREIGN KEY (id_producto)
        REFERENCES producto (id_producto)
);


-- TABLA TICKET
CREATE TABLE ticket (
    id_ticket INT AUTO_INCREMENT PRIMARY KEY,
    numero_ticket VARCHAR(50) UNIQUE,
    fecha_hora DATETIME,
    subtotal DECIMAL(10,2),
    iva DECIMAL(10,2),
    total DECIMAL(10,2),
    metodo_pago VARCHAR(30),
    id_venta INT UNIQUE,
    FOREIGN KEY (id_venta) REFERENCES venta(id_venta)
);


-- TABLA PEDIDO
CREATE TABLE pedido (
    id_pedido INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    estado VARCHAR(30),
    total_pedido DECIMAL(10,2),
    id_proveedor INT,
    id_empleado INT,
    FOREIGN KEY (id_proveedor) REFERENCES proveedor(id_proveedor),
    FOREIGN KEY (id_empleado) REFERENCES empleado(id_empleado)
);


-- TABLA DETALLE_PEDIDO
CREATE TABLE detalle_pedido (
    id_pedido INT,
    id_producto INT,
    cantidad INT NOT NULL,
    costo DECIMAL(10,2),
    subtotal DECIMAL(10,2),
    PRIMARY KEY (id_pedido, id_producto),
    FOREIGN KEY (id_pedido) REFERENCES pedido(id_pedido),
    FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
);


-- TABLA INCIDENCIA
CREATE TABLE incidencia (
    id_incidencia INT AUTO_INCREMENT PRIMARY KEY,
    descripcion TEXT,
    fecha DATE,
    tipo VARCHAR(50),
    gravedad VARCHAR(30),
    id_proveedor INT,
    FOREIGN KEY (id_proveedor) REFERENCES proveedor(id_proveedor)
);

show tables;

-- producto --
INSERT INTO empleado (nombre, telefono, usuario, contraseña, rol, estado) VALUES
('Brenda Tapia',       '5512984371', 'btapia',      'brenda01', 'cajero',      TRUE),
('Ernesto Villalobos', '5523671890', 'evillalobos', 'ernes22',  'cajero',     TRUE),
('Karina Montes',      '5534102938', 'kmontes',     'kari55',   'cajero', TRUE),
('Lupita tik tok',      '5545039281', 'lupita',    'lupita69',  'cajero',      TRUE),
('Daniela Reyes',      '5556748392', 'dreyes',      'dany77',   'cajero',      TRUE),
('tomas Ibarra',       '5567839201', 'tibarra',     'tomas33',  'gerente',     TRUE),
('Fernanda Solís',     '5578920183', 'fsolis',      'fer2025',  'cajero', TRUE),
('Ricardo Aguilar',    '5589013742', 'raguilar',    'rick44',   'cajero',      TRUE),
('Gustambo Campos',     '5590124853', 'guscampos',     'nati99',   'cajero',      TRUE),
('Hectoario Zuñiga',      '5501235964', 'hzuniga',     'hect11',   'gerente',     TRUE);

-- producto --
INSERT INTO producto (nombre, categoria, tipo_mascota, precio_venta, costo, stock, estado, nivel_rotacion, codigo_barras) VALUES
('Purina Pro Plan Adulto Pollo 3kg', 'Alimento',    'Perro', 389.00, 210.00, 34, 'activo', 'alto',  'PPN30001'),
('Royal Canin Kitten 2kg',           'Alimento',    'Gato',  445.00, 240.00, 28, 'activo', 'alto',  'RCK20002'),
('Pedigree Adulto Carne 4kg',        'Alimento',    'Perro', 299.00, 155.00, 52, 'activo', 'alto',  'PDG40003'),
('Whiskas Atún Adulto 85g',          'Alimento',    'Gato',  22.00,  10.00,  95, 'activo', 'alto',  'WSK80004'),
('Frontline Plus Pipeta Perro M',    'Medicamento', 'Perro', 185.00, 98.00,  18, 'activo', 'medio', 'FRL10005'),
('Bravecto Comprimido 250mg',        'Medicamento', 'Perro', 650.00, 380.00, 12, 'activo', 'bajo',  'BRV20006'),
('Kong Classic Juguete M',           'Juguete',     'Perro', 320.00, 145.00, 9,  'activo', 'bajo',  'KNG30007'),
('Catit Fuente Bebedero 3L',         'Accesorio',   'Gato',  580.00, 270.00, 7,  'activo', 'bajo',  'CTF30008'),
('Shampoo Virbac Episoothe 250ml',   'Higiene',     'Perro', 210.00, 115.00, 15, 'activo', 'medio', 'VRB20009'),
('Fancy Feast Pollo Salsa 85g',      'Alimento',    'Gato',  28.00,  12.00,  88, 'activo', 'alto',  'FFP80010');

-- inventario --
INSERT INTO inventario (stock_actual, stock_minimo, fecha_actualizacion, merma, id_producto) VALUES
(34, 10, '2025-05-28', 0, 1),
(28, 8,  '2025-05-29', 1, 2),
(52, 15, '2025-05-30', 0, 3),
(95, 20, '2025-05-30', 3, 4),
(18, 5,  '2025-05-31', 0, 5),
(12, 4,  '2025-06-01', 0, 6),
(9,  3,  '2025-06-01', 1, 7),
(7,  2,  '2025-06-02', 0, 8),
(15, 5,  '2025-06-02', 0, 9),
(88, 20, '2025-06-03', 2, 10);

-- proveedor --
INSERT INTO proveedor (nombre, telefono, direccion, tipo_productos, calificacion) VALUES
('Nestlé Purina México',       '5511002200', 'Av. Insurgentes Sur 1602, CDMX',    'Alimentos',    4.8),
('Royal Canin México',         '5522003300', 'Blvd. Manuel Ávila 56, Naucalpan',  'Alimentos',    4.9),
('Mars Petcare México',        '5533004400', 'Av. Santa Fe 481, CDMX',            'Alimentos',    4.6),
('Boehringer Ingelheim Vet',   '5544005500', 'Periférico Sur 7980, CDMX',         'Medicamentos', 4.7),
('MSD Salud Animal México',    '5555006600', 'Av. Lomas Verdes 480, Naucalpan',   'Medicamentos', 4.5),
('Kong Company Distribuidora', '5566007700', 'Calle Lago Alberto 319, CDMX',      'Juguetes',     4.3),
('Catit Hagen México',         '5577008800', 'Av. Ejército Nacional 843, CDMX',   'Accesorios',   4.4),
('Virbac México',              '5588009900', 'Blvd. Adolfo López 2389, CDMX',     'Higiene',      4.7),
('Distribuidora PetZone',      '5599000011', 'Av. Canal del Norte 28, CDMX',      'Varios',       4.1),
('AlimPet Mayorista',          '5500001122', 'Calle Ferrocarril 77, Tlalnepantla','Alimentos',    3.9);

-- venta --
INSERT INTO venta (fecha, hora, subtotal, iva, total, metodo_pago, estado, id_empleado) VALUES
('2025-05-26', '09:15:00', 389.00, 62.24,  451.24, 'efectivo',      'completada', 1),
('2025-05-27', '10:40:00', 445.00, 71.20,  516.20, 'transferencia',       'completada', 2),
('2025-05-28', '11:05:00', 299.00, 47.84,  346.84, 'efectivo',      'completada', 3),
('2025-05-29', '08:30:00', 44.00,  7.04,   51.04,  'efectivo',      'completada', 4),
('2025-05-30', '13:20:00', 185.00, 29.60,  214.60, 'efectivo',       'completada', 5),
('2025-05-31', '16:00:00', 650.00, 104.00, 754.00, 'efectivo',       'completada', 6),
('2025-06-01', '09:50:00', 320.00, 51.20,  371.20, 'efectivo',      'completada', 7),
('2025-06-01', '12:10:00', 580.00, 92.80,  672.80, 'transferencia', 'completada', 8),
('2025-06-02', '10:25:00', 210.00, 33.60,  243.60, 'efectivo',       'completada', 9),
('2025-06-03', '11:45:00', 56.00,  8.96,   64.96,  'efectivo',      'completada', 10);

-- detalle venta --
INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario, importe) VALUES
(1,  1,  1, 389.00, 389.00),
(2,  2,  1, 445.00, 445.00),
(3,  3,  1, 299.00, 299.00),
(4,  4,  2, 22.00,  44.00),
(5,  5,  1, 185.00, 185.00),
(6,  6,  1, 650.00, 650.00),
(7,  7,  1, 320.00, 320.00),
(8,  8,  1, 580.00, 580.00),
(9,  9,  1, 210.00, 210.00),
(10, 10, 2, 28.00,  56.00);

-- ticket --
INSERT INTO ticket (numero_ticket, fecha_hora, subtotal, iva, total, metodo_pago, id_venta) VALUES
('TK-2025-001', '2025-05-26 09:15:00', 389.00, 62.24,  451.24, 'efectivo',      1),
('TK-2025-002', '2025-05-27 10:40:00', 445.00, 71.20,  516.20, 'tarjeta',       2),
('TK-2025-003', '2025-05-28 11:05:00', 299.00, 47.84,  346.84, 'efectivo',      3),
('TK-2025-004', '2025-05-29 08:30:00', 44.00,  7.04,   51.04,  'efectivo',      4),
('TK-2025-005', '2025-05-30 13:20:00', 185.00, 29.60,  214.60, 'tarjeta',       5),
('TK-2025-006', '2025-05-31 16:00:00', 650.00, 104.00, 754.00, 'tarjeta',       6),
('TK-2025-007', '2025-06-01 09:50:00', 320.00, 51.20,  371.20, 'efectivo',      7),
('TK-2025-008', '2025-06-01 12:10:00', 580.00, 92.80,  672.80, 'transferencia', 8),
('TK-2025-009', '2025-06-02 10:25:00', 210.00, 33.60,  243.60, 'tarjeta',       9),
('TK-2025-010', '2025-06-03 11:45:00', 56.00,  8.96,   64.96,  'efectivo',      10);

-- pedido --
INSERT INTO pedido (fecha, estado, total_pedido, id_proveedor, id_empleado) VALUES
('2025-05-20', 'completado', 2100.00, 1,  1),
('2025-05-21', 'completado', 1340.00, 2,  2),
('2025-05-22', 'pendiente',  900.00,  3,  3),
('2025-05-23', 'completado', 200.00,  4,  4),
('2025-05-24', 'cancelado',  490.00,  5,  5),
('2025-05-25', 'completado', 1740.00, 6,  6),
('2025-05-27', 'pendiente',  870.00,  7,  7),
('2025-05-29', 'completado', 1080.00, 8,  8),
('2025-06-01', 'completado', 560.00,  9,  9),
('2025-06-02', 'cancelado',  336.00,  10, 10);

-- detalle pedido --
INSERT INTO detalle_pedido (id_pedido, id_producto, cantidad, costo, subtotal) VALUES
(1,  1,  10, 210.00, 2100.00),
(2,  2,  7,  240.00, 1680.00),
(3,  3,  6,  155.00, 930.00),
(4,  4,  20, 10.00,  200.00),
(5,  5,  5,  98.00,  490.00),
(6,  6,  3,  380.00, 1140.00),
(7,  7,  6,  145.00, 870.00),
(8,  8,  4,  270.00, 1080.00),
(9,  9,  4,  140.00, 560.00),
(10, 10, 28, 12.00,  336.00);

-- incidencia --
INSERT INTO incidencia (descripcion, fecha, tipo, gravedad, id_proveedor) VALUES
('Bolsa de croquetas llegó rota',           '2025-05-21', 'calidad',   'baja',  1),
('Pedido llegó 3 días tarde',               '2025-05-22', 'logística', 'media', 2),
('Remitieron 5 latas menos de lo pedido',   '2025-05-23', 'entrega',   'media', 3),
('Pipetas próximas a vencer al recibirlas', '2025-05-25', 'calidad',   'alta',  4),
('Factura con RFC incorrecto',              '2025-05-27', 'factura',   'baja',  5),
('Caja de juguetes llegó aplastada',        '2025-05-28', 'calidad',   'baja',  6),
('Bebedero con pieza faltante',             '2025-05-30', 'calidad',   'media', 7),
('Shampoo sin etiqueta en español',         '2025-06-01', 'calidad',   'baja',  8),
('Proveedor cambió precio sin avisar',      '2025-06-02', 'factura',   'alta',  9),
('No trajeron el 30% del pedido',           '2025-06-03', 'entrega',   'alta',  10);

Select * from empleado;