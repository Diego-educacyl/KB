


DROP DATABASE IF EXISTS empresa;

-- 1. Crear la base de datos
CREATE DATABASE IF NOT EXISTS empresa;
USE empresa;

-- 2. Crear la tabla 'clientes'
CREATE TABLE clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    direccion VARCHAR(255),
    correo VARCHAR(100) UNIQUE
);

-- 3. Crear la tabla 'empleados'
CREATE TABLE empleados (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    direccion VARCHAR(255),
    puesto VARCHAR(50),
    clave VARCHAR(4) -- Columna para la clave de 4 dígitos
);

-- 4. Crear la tabla 'pedidos'
CREATE TABLE pedidos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT NOT NULL,
    fecha DATE NOT NULL,
    estado VARCHAR(50) NOT NULL, -- Ej: 'Pendiente', 'En proceso', 'Completado', 'Cancelado'
    total DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- 5. Crear la tabla 'insumos' (la que antes se llamaba 'productos' para ingredientes)
-- Suponemos que esta tabla tiene un 'id' numérico auto-incrementado como clave primaria
-- y 'codigo' es una columna única para los códigos INGxxx.
CREATE TABLE insumos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(10) UNIQUE NOT NULL, -- Para 'ING001', 'ING002', etc. Asegura que el código sea único
    nombre VARCHAR(100) NOT NULL,
    precio DECIMAL(10, 2) NOT NULL,
    stock INT NOT NULL DEFAULT 0, -- Asumimos 'stock' en lugar de 'categoria' aquí, basado en la imagen.
    categoria VARCHAR(50) -- Si prefieres que 'insumos' también tenga una categoría
);


-- 6. Crear la tabla 'productos' (la nueva tabla para productos finales de venta)
CREATE TABLE productos (
    codigo VARCHAR(10) PRIMARY KEY, -- Clave primaria para códigos de producto como HAM001, ACOM01, BEB001
    nombre VARCHAR(100) NOT NULL,
    precio DECIMAL(10, 2) NOT NULL,
    categoria VARCHAR(50)
);

-- 7. Crear la tabla 'detalle_pedidos' (tabla de relación entre pedidos y productos/insumos)
-- Aseguramos que 'producto_id' referencie la NUEVA tabla 'productos' por su 'codigo'
CREATE TABLE detalle_pedidos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pedido_id INT NOT NULL,
    producto_id VARCHAR(10) NOT NULL, -- Referencia a productos.codigo
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (pedido_id) REFERENCES pedidos(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (producto_id) REFERENCES productos(codigo) ON DELETE CASCADE ON UPDATE CASCADE
);
-- 8.tabla receta
CREATE TABLE recetas (
    -- La clave primaria compuesta garantiza que un mismo insumo para un mismo producto solo se defina una vez
    producto_codigo VARCHAR(10) NOT NULL,
    insumo_codigo VARCHAR(10) NOT NULL,
    cantidad_necesaria DECIMAL(10, 2) NOT NULL, -- Cuánta cantidad de este insumo necesita este producto (ej: 1 unidad de pan, 0.15 kg de carne)
    PRIMARY KEY (producto_codigo, insumo_codigo),
    FOREIGN KEY (producto_codigo) REFERENCES productos(codigo) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (insumo_codigo) REFERENCES insumos(codigo) ON DELETE CASCADE ON UPDATE CASCADE
);



INSERT INTO clientes (nombre, telefono, direccion, correo) VALUES
('Laura Sánchez', '611223344', 'Calle Sol 9, Bilbao', 'laura.s@example.com'),
('Carlos Ruiz', '622334455', 'Av. Luna 15, Valencia', 'carlos.r@example.com'),
('María García', '633445566', 'Plaza Mayor 1, Sevilla', 'maria.g@example.com');

-- Datos para la tabla 'empleados'
INSERT INTO empleados (nombre, telefono, direccion, puesto, clave) VALUES
('Javier Ruiz', '612334455', 'Calle Norte 21, Málaga', 'administrador', '7890'),
('Elena Martínez', '613344556', 'Calle Sur 33, Granada', 'camarero', '2345'),
('Diego Fernández', '614556677', 'Calle Este 4, Alicante', 'camarero', '6789'),
('Marta Gil', '615667788', 'Calle Río 10, Salamanca', 'camarero', 'AB12'),
('Sergio Navarro', '616778899', 'Av. del Mar 18, Cádiz', 'camarero', 'CD34'),
('Martín Llanos', '611576796', 'Calle Miguel de Prado 13, Valladolid', 'administrador', 'EF56');

-- Datos para la tabla 'insumos' (ingredientes)
INSERT INTO insumos (codigo, nombre, precio, stock, categoria) VALUES
('ING001', 'Pan de hamburguesa', 0.50, 100, 'Panaderia'),
('ING002', 'Carne de vacuno (150g)', 1.20, 50, 'Carne'),
('ING003', 'Tomate (unidad)', 0.30, 200, 'Verdura'),
('ING004', 'Lechuga (ración)', 0.20, 150, 'Verdura'),
('ING005', 'Cebolla (ración)', 0.15, 180, 'Verdura'),
('ING006', 'Queso Cheddar (loncha)', 0.40, 90, 'Lacteos'),
('ING007', 'Bacon (ración)', 0.60, 70, 'Carne'),
('ING008', 'Pan integral (unidad)', 0.60, 60, 'Panaderia'),
('ING009', 'Carne de pollo (150g)', 1.00, 40, 'Carne'),
('ING010', 'Hamburguesa Vegetal', 1.50, 30, 'Vegetal'),
('ING011', 'Aceite de oliva (ml)', 0.05, 5000, 'Aderezo'),
('ING012', 'Vinagre (ml)', 0.03, 3000, 'Aderezo'),
('ING013', 'Patatas (kg)', 1.00, 80, 'Verdura'),
('ING014', 'Sal (gramos)', 0.01, 1000, 'Aderezo'),
('ING015', 'Refresco (lata)', 0.80, 200, 'Bebida Base'),
('ING016', 'Agua mineral (botella)', 0.60, 150, 'Bebida Base'),
('ING017', 'Cerveza (botella)', 1.20, 100, 'Bebida Base');

-- Datos para la tabla 'productos' (productos finales para la venta)
INSERT INTO productos (codigo, nombre, precio, categoria) VALUES
('HAM001', 'Hamburguesa Clásica', 7.50, 'Hamburguesas'),
('HAM002', 'Hamburguesa Doble Queso', 9.00, 'Hamburguesas'),
('HAM003', 'Hamburguesa de Pollo Crispy', 8.00, 'Hamburguesas'),
('HAM004', 'Hamburguesa Vegetal Gourmet', 8.50, 'Hamburguesas'),
('ACOM01', 'Patatas Fritas Grandes', 3.00, 'Acompañamientos'),
('ACOM02', 'Ensalada Mixta Fresca', 4.50, 'Acompañamientos'),
('ACOM03', 'Aros de Cebolla (6 und.)', 3.50, 'Acompañamientos'),
('BEB001', 'Coca-Cola (Lata)', 2.00, 'Bebidas'),
('BEB002', 'Fanta Limón (Lata)', 2.00, 'Bebidas'),
('BEB003', 'Agua Mineral (500ml)', 1.50, 'Bebidas'),
('BEB004', 'Cerveza Lager (Botella)', 2.50, 'Bebidas');

-- Datos para la tabla 'pedidos' (necesita clientes_id existentes)
INSERT INTO pedidos (cliente_id, fecha, estado, total) VALUES
(1, '2025-05-18', 'Completado', 15.00), -- Pedido de Laura Sánchez
(2, '2025-05-19', 'Pendiente', 9.50),   -- Pedido de Carlos Ruiz
(3, '2025-05-19', 'En proceso', 11.00);  -- Pedido de María García

-- Datos para la tabla 'detalle_pedidos' (necesita pedido_id y producto_id existentes)
INSERT INTO detalle_pedidos (pedido_id, producto_id, cantidad, precio_unitario) VALUES
(1, 'HAM001', 1, 7.50), -- Hamburguesa Clásica para Pedido 1
(1, 'ACOM01', 1, 3.00), -- Patatas Fritas para Pedido 1
(1, 'BEB001', 2, 2.00), -- 2 Coca-Colas para Pedido 1 (2*2.00=4.00)
(2, 'HAM003', 1, 8.00), -- Hamburguesa de Pollo para Pedido 2
(2, 'BEB003', 1, 1.50), -- Agua Mineral para Pedido 2
(3, 'HAM002', 1, 9.00), -- Hamburguesa Doble Queso para Pedido 3
(3, 'BEB004', 1, 2.50); -- Cerveza para Pedido 3

-- Datos de ejemplo para la tabla 'recetas' (relaciona productos con insumos)

-- **** HAMBURGUESAS ****

-- Hamburguesa Clásica (HAM001): Pan, Carne Vacuno, Tomate, Lechuga, Cebolla, Queso Cheddar (opcional), Ketchup/Mostaza
INSERT INTO recetas (producto_codigo, insumo_codigo, cantidad_necesaria) VALUES
('HAM001', 'ING001', 1.0),   -- 1 unidad de Pan de hamburguesa
('HAM001', 'ING002', 1.0),   -- 1 ración de Carne de vacuno
('HAM001', 'ING003', 0.5),   -- 0.5 unidad de Tomate (ej. 1/2 tomate o varias rodajas)
('HAM001', 'ING004', 1.0),   -- 1 ración de Lechuga
('HAM001', 'ING005', 0.5);   -- 0.5 ración de Cebolla

-- Hamburguesa Doble Queso (HAM002): Pan, Carne Vacuno x2, Queso Cheddar x2, Tomate, Lechuga, Cebolla
INSERT INTO recetas (producto_codigo, insumo_codigo, cantidad_necesaria) VALUES
('HAM002', 'ING001', 1.0),   -- 1 unidad de Pan de hamburguesa
('HAM002', 'ING002', 2.0),   -- 2 raciones de Carne de vacuno
('HAM002', 'ING006', 2.0),   -- 2 lonchas de Queso Cheddar
('HAM002', 'ING003', 0.5),
('HAM002', 'ING004', 1.0),
('HAM002', 'ING005', 0.5);

-- Hamburguesa de Pollo Crispy (HAM003): Pan, Carne Pollo, Lechuga, Tomate, Mayonesa (no tenemos ingrediente específico, asumimos)
INSERT INTO recetas (producto_codigo, insumo_codigo, cantidad_necesaria) VALUES
('HAM003', 'ING001', 1.0),   -- 1 unidad de Pan de hamburguesa
('HAM003', 'ING009', 1.0),   -- 1 ración de Carne de pollo
('HAM003', 'ING004', 1.0),   -- 1 ración de Lechuga
('HAM003', 'ING003', 0.5);   -- 0.5 unidad de Tomate

-- Hamburguesa Vegetal Gourmet (HAM004): Pan Integral, Hamburguesa Vegetal, Tomate, Lechuga, Cebolla
INSERT INTO recetas (producto_codigo, insumo_codigo, cantidad_necesaria) VALUES
('HAM004', 'ING008', 1.0),   -- 1 unidad de Pan integral
('HAM004', 'ING010', 1.0),   -- 1 Hamburguesa Vegetal
('HAM004', 'ING003', 0.5),
('HAM004', 'ING004', 1.0),
('HAM004', 'ING005', 0.5);

-- **** ACOMPAÑAMIENTOS ****

-- Patatas Fritas Grandes (ACOM01): Patatas, Sal, Aceite (asumido para cocción)
INSERT INTO recetas (producto_codigo, insumo_codigo, cantidad_necesaria) VALUES
('ACOM01', 'ING013', 0.3),   -- 0.3 kg de Patatas (por ejemplo)
('ACOM01', 'ING014', 0.01),  -- 0.01 kg de Sal
('ACOM01', 'ING011', 0.05);  -- 0.05 litros de Aceite (solo como estimación)

-- Ensalada Mixta Fresca (ACOM02): Lechuga, Tomate, Cebolla, Aceite, Vinagre
INSERT INTO recetas (producto_codigo, insumo_codigo, cantidad_necesaria) VALUES
('ACOM02', 'ING004', 1.0),   -- 1 ración de Lechuga
('ACOM02', 'ING003', 0.5),   -- 0.5 unidad de Tomate
('ACOM02', 'ING005', 0.2),   -- 0.2 ración de Cebolla
('ACOM02', 'ING011', 0.01),  -- 0.01 litros de Aceite
('ACOM02', 'ING012', 0.005); -- 0.005 litros de Vinagre

-- Aros de Cebolla (ACOM03): Cebolla (el resto ingredientes de rebozado no los tenemos explícitos)
INSERT INTO recetas (producto_codigo, insumo_codigo, cantidad_necesaria) VALUES
('ACOM03', 'ING005', 1.0);   -- 1 unidad/ración de Cebolla (asumiendo que es la materia principal)

-- **** BEBIDAS ****
-- Estas son generalmente insumos directos, pero se listan para completar la relación.
-- La 'cantidad_necesaria' sería 1.0 porque es 1 unidad del insumo final.

-- Coca-Cola (BEB001): Refresco (lata)
INSERT INTO recetas (producto_codigo, insumo_codigo, cantidad_necesaria) VALUES
('BEB001', 'ING015', 1.0);

-- Fanta Limón (BEB002): Refresco (lata)
INSERT INTO recetas (producto_codigo, insumo_codigo, cantidad_necesaria) VALUES
('BEB002', 'ING015', 1.0); -- Asumimos que ING015 (Refresco) cubre cualquier tipo de refresco en lata. Si tienes distintos tipos de refrescos en insumos, usa el código específico.

-- Agua Mineral (BEB003): Agua mineral (botella)
INSERT INTO recetas (producto_codigo, insumo_codigo, cantidad_necesaria) VALUES
('BEB003', 'ING016', 1.0);

-- Cerveza Lager (BEB004): Cerveza (botella)
INSERT INTO recetas (producto_codigo, insumo_codigo, cantidad_necesaria) VALUES
('BEB004', 'ING017', 1.0);
