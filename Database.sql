create database basedatos;
use basedatos;

CREATE TABLE producto (
    codigoProducto INT PRIMARY KEY,
    nombreProducto VARCHAR(50),
    precioUnitario DECIMAL(10, 2),
    cantidadProducto INT,
    fechaVencimiento VARCHAR(11)
);
-- consultar los datos
select * from producto;


INSERT INTO producto (codigoProducto, nombreProducto, precioUnitario, cantidadProducto, fechaVencimiento)
VALUES
(1, 'Cámara DSLR Canon EOS 90D', 1200.00, 10, '2026-12-31'),
(2, 'Lente Canon EF 50mm f/1.8', 125.00, 25, '2027-01-01'),
(3, 'Trípode Manfrotto Compact Action', 75.50, 15, '2026-11-15'),
(4, 'Drone DJI Mavic Air 2', 850.00, 5, '2026-10-20'),
(5, 'Estabilizador Gimbal DJI Ronin-S', 600.00, 8, '2026-12-01'),
(6, 'Micrófono Rode VideoMic Pro+', 300.00, 20, '2026-09-30'),
(7, 'Luz LED Neewer 660 RGB', 150.00, 12, '2026-08-15'),
(8, 'Flash Godox V860II', 220.00, 10, '2026-11-01'),
(9, 'Tarjeta SD SanDisk Extreme 128GB', 45.00, 50, '2027-01-31'),
(10, 'Monopod Sirui P-326', 130.00, 9, '2026-07-25'),
(11, 'Reflector 5-en-1 Neewer', 30.00, 18, '2026-08-05'),
(12, 'Cámara Sony Alpha 7 IV', 2500.00, 6, '2026-12-25'),
(13, 'Lente Sony FE 24-70mm f/2.8', 2000.00, 7, '2027-02-10'),
(14, 'Monitor Portátil Feelworld F6 Plus', 260.00, 4, '2026-10-05'),
(15, 'Mochila Fotográfica Lowepro ProTactic 450 AW II', 180.00, 10, '2026-12-10'),
(16, 'Batería Canon LP-E6N', 60.00, 30, '2026-09-25'),
(17, 'Estudio de Iluminación Softbox Godox', 170.00, 5, '2026-11-20'),
(18, 'Cámara GoPro Hero 10 Black', 500.00, 12, '2027-03-01'),
(19, 'Lente Sigma 18-35mm f/1.8', 799.00, 8, '2027-01-20'),
(20, 'Control Remoto para Cámara Neewer', 20.00, 40, '2026-10-30'),
(21, 'Grabadora de Audio Zoom H6', 350.00, 9, '2026-12-15'),
(22, 'Kit de Limpieza para Cámara Altura Photo', 25.00, 50, '2026-07-31'),
(23, 'Cargador Dual Canon LC-E6', 70.00, 15, '2026-11-10'),
(24, 'Tarjeta CFexpress 128GB Sony', 150.00, 20, '2027-02-28'),
(25, 'Monitor de Campo Atomos Ninja V', 399.00, 3, '2027-04-01');

-- Actualizar el precio y la cantidad de productos específicos
UPDATE producto
SET precioUnitario = 1300.00, cantidadProducto = 12
WHERE codigoProducto = 1;

UPDATE producto
SET precioUnitario = 2000.00, cantidadProducto = 10
WHERE codigoProducto = 12;

UPDATE producto
SET precioUnitario = 125.00, cantidadProducto = 45
WHERE codigoProducto = 9;

UPDATE producto
SET precioUnitario = 850.00
WHERE codigoProducto = 4;

UPDATE producto
SET nombreProducto = 'Estabilizador Gimbal DJI Ronin-SC'
WHERE codigoProducto = 5;

-- Eliminar 5 productos específicos
DELETE FROM producto WHERE codigoProducto = 21;
DELETE FROM producto WHERE codigoProducto = 22;
DELETE FROM producto WHERE codigoProducto = 23;
DELETE FROM producto WHERE codigoProducto = 24;
DELETE FROM producto WHERE codigoProducto = 25;


