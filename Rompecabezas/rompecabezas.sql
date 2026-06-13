CREATE DATABASE IF NOT EXISTS rompecabezas;
USE rompecabezas;

CREATE TABLE categorias (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre_categoria VARCHAR(50)
);

CREATE TABLE rompecabezas (
    id_rompecabezas INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    piezas INT,
    dificultad VARCHAR(20),
    estado VARCHAR(20),
    id_categoria INT,
    FOREIGN KEY (id_categoria) REFERENCES categorias(id_categoria)
);

CREATE TABLE avance (
    id_avance INT AUTO_INCREMENT PRIMARY KEY,
    id_rompecabezas INT,
    fecha DATE,
    porcentaje INT,
    FOREIGN KEY (id_rompecabezas) REFERENCES rompecabezas(id_rompecabezas)
);


INSERT INTO categorias (nombre_categoria)
VALUES
('Anime'),
('Películas'),
('Cómics'),
('Videojuegos'),
('Paisajes');

INSERT INTO rompecabezas
(nombre, piezas, dificultad, estado, id_categoria)
VALUES
('Naruto Shippuden', 1000, 'Media', 'En proceso', 1),
('Dragon Ball Z', 1500, 'Difícil', 'Terminado', 1),
('Avengers Endgame', 1000, 'Media', 'Pendiente', 2),
('Spider-Man', 500, 'Fácil', 'Terminado', 3),
('The Legend of Zelda', 2000, 'Difícil', 'En proceso', 4);

INSERT INTO avance
(id_rompecabezas, fecha, porcentaje)
VALUES
(1, '2026-05-20', 25),
(2, '2026-05-15', 100),
(3, '2026-05-30', 0),
(4, '2026-05-10', 100),
(5, '2026-06-01', 60);

SELECT r.id_rompecabezas,
       r.nombre,
       r.piezas,
       r.dificultad,
       r.estado,
       c.nombre_categoria
FROM rompecabezas r
INNER JOIN categorias c
ON r.id_categoria = c.id_categoria;

SELECT nombre, piezas
FROM rompecabezas
WHERE estado = 'Terminado';

SELECT r.nombre,
       a.fecha,
       a.porcentaje
FROM rompecabezas r
INNER JOIN avance a
ON r.id_rompecabezas = a.id_rompecabezas;

SELECT *
FROM rompecabezas
WHERE piezas > 1000;