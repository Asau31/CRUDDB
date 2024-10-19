package proyectodb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/basedatos";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678";

    public static Connection conectar() {
        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa a la base de datos");
        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
        }
        return conexion;
    }
    
    public static void insertarProducto(int codigo, String nombre, double precio, int cantidad, String fecha) {
        String query = "INSERT INTO producto (codigoProducto, nombreProducto, precioUnitario, cantidadProducto, fechaVencimiento) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection con = ConexionBD.conectar(); PreparedStatement pst = con.prepareStatement(query)) {
            if (nombre == null || nombre.trim().isEmpty()) {
                System.out.println("❌ El nombre del producto no puede estar vacío.");
                return;
            }
            
            if (cantidad < 0) {
                System.out.println("❌ La cantidad no puede ser negativa.");
                return;
            }

            if (fecha == null || fecha.trim().isEmpty()) {
                System.out.println("❌ La fecha de vencimiento no puede estar vacía.");
                return;
            }

            java.sql.Date sqlDate;
            try {
                sqlDate = java.sql.Date.valueOf(fecha);
            } catch (IllegalArgumentException e) {
                System.out.println("❌ Formato de fecha inválido. Usa el formato 'yyyy-MM-dd'.");
                return;
            }

            pst.setInt(1, codigo);
            pst.setString(2, nombre);
            pst.setDouble(3, precio);
            pst.setInt(4, cantidad);
            pst.setDate(5, sqlDate);
            
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✅ Producto insertado correctamente:");
                System.out.printf("📦 Código: %d\n📝 Nombre: %s\n💲 Precio: %.2f\n📊 Cantidad: %d\n📅 Fecha de Vencimiento: %s\n", codigo, nombre, precio, cantidad, fecha);
            } else {
                System.out.println("⚠️ No se insertó ningún producto.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al insertar el producto: " + e.getMessage());
        }
    }

    public static void listarProductos() {
        String query = "SELECT * FROM producto;";
        try (Connection con = ConexionBD.conectar(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(query)) {
            boolean hayResultados = false;
            while (rs.next()) {
                hayResultados = true;
                System.out.println("📦 Producto encontrado:");
                System.out.printf("🔑 Código: %d\n📝 Nombre: %s\n💲 Precio: %.2f\n📊 Cantidad: %d\n📅 Fecha de Vencimiento: %s\n", 
                        rs.getInt("codigoProducto"), 
                        rs.getString("nombreProducto"), 
                        rs.getDouble("precioUnitario"), 
                        rs.getInt("cantidadProducto"), 
                        rs.getString("fechaVencimiento"));
                System.out.println("------------------------------------------------");
            }
            if (!hayResultados) {
                System.out.println("⚠️ No hay productos disponibles.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al listar los productos: " + e.getMessage());
        }
    }

    public static void buscarProducto(int codigoProducto) {
        String query = "SELECT * FROM producto WHERE codigoProducto = ?";
        try (Connection con = ConexionBD.conectar(); PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, codigoProducto);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    System.out.println("✅ Producto encontrado:");
                    System.out.printf("🔑 Código: %d\n📝 Nombre: %s\n💲 Precio: %.2f\n📊 Cantidad: %d\n📅 Fecha de Vencimiento: %s\n", 
                            rs.getInt("codigoProducto"), 
                            rs.getString("nombreProducto"), 
                            rs.getDouble("precioUnitario"), 
                            rs.getInt("cantidadProducto"), 
                            rs.getString("fechaVencimiento"));
                } else {
                    System.out.println("⚠️ Producto no encontrado.");
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al buscar el producto: " + e.getMessage());
        }
    }

    public static void actualizarProducto(int codigoProducto, String nombre, double precio) {
        String query = "UPDATE producto SET nombreProducto = ?, precioUnitario = ? WHERE codigoProducto = ?";
        try (Connection con = ConexionBD.conectar(); PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, nombre);
            pst.setDouble(2, precio);
            pst.setInt(3, codigoProducto);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✅ Producto actualizado correctamente:");
                System.out.printf("🔑 Código: %d\n📝 Nuevo nombre: %s\n💲 Nuevo precio: %.2f\n", codigoProducto, nombre, precio);
            } else {
                System.out.println("⚠️ No se encontró el producto con el código especificado.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al actualizar el producto: " + e.getMessage());
        }
    }

    public static void eliminarProducto(int codigoProducto) {
        String query = "DELETE FROM producto WHERE codigoProducto = ?";
        try (Connection con = ConexionBD.conectar(); PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, codigoProducto);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✅ Producto eliminado correctamente:");
                System.out.printf("🔑 Código: %d\n", codigoProducto);
            } else {
                System.out.println("⚠️ No se encontró el producto con el código especificado.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar el producto: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        int opcion = 0;
        boolean flag = true;
        
        do {
            System.out.println("1) Ingresar Producto");
            System.out.println("2) Consultar productos");
            System.out.println("3) Buscar producto");
            System.out.println("4) Modificar producto");
            System.out.println("5) Eliminar producto");
            System.out.println("6) Salir");
            System.out.println("Ingresa la opción que quieras usar");
            Scanner scaner = new Scanner(System.in);
            opcion = scaner.nextInt();
            
            switch(opcion) {
                case 1: 
                    String name_1, fecha_1;
                    double precio_1;
                    int code_1, cantidad_1;

                    System.out.println("Ingresa lo que se te pedirá a continuación para agregar el producto");
                    System.out.println("-----------------------------------");

                    System.out.println("Ingresa el código del producto");
                    code_1 = scaner.nextInt();
                    scaner.nextLine();

                    System.out.println("Ingresa el nombre del producto");
                    name_1 = scaner.nextLine();

                    System.out.println("Ingresa el precio del producto");
                    precio_1 = scaner.nextDouble();
                    scaner.nextLine();

                    System.out.println("Ingresa la cantidad en stock");
                    cantidad_1 = scaner.nextInt();
                    scaner.nextLine();

                    System.out.println("Ingresa la fecha de ingreso del producto (yyyy-[m]m-[d]d)");
                    fecha_1 = scaner.nextLine();

                    insertarProducto(code_1, name_1, precio_1, cantidad_1, fecha_1);
                    break; 
                    
                case 2:
                    listarProductos();
                    break;
                    
                case 3:
                    int codigoBusqueda;
                    System.out.println("Ingresa el código del producto que quieres buscar:");
                    codigoBusqueda = scaner.nextInt();
                    buscarProducto(codigoBusqueda);
                    break;
                    
                case 4:
                    int code_2;
                    double precio_2;
                    String name_2;
                    System.out.println("Solo podrás modificar el nombre y el precio del producto");

                    System.out.println("Ingresa el código del producto");
                    code_2 = scaner.nextInt();
                    scaner.nextLine();

                    System.out.println("Ingresa el nuevo nombre");
                    name_2 = scaner.nextLine();

                    System.out.println("Ingresa el nuevo precio");
                    precio_2 = scaner.nextDouble();

                    actualizarProducto(code_2, name_2, precio_2);
                    break;
                    
                case 5:
                    int code_3;
                    System.out.println("Ingresa el código del producto que quieres eliminar");
                    code_3 = scaner.nextInt();
                    eliminarProducto(code_3);
                    break;
                    
                case 6:
                    System.out.println("Saliendo del sistema...");
                    flag = false;
                    break;
                    
                default: 
                    System.out.println("Op1ción no válida");
                    break;
            }
        } while(flag);
    }
}
