import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

/*0. salir
* 1. cargar datos iniciales
* 2. mostrar datos
* 3. guardar datos.
* 4. borrar datos.
* 5. Incorporar datos de fichero.
* 6. Ordenar datos.
*/
public class ListaJugadores {

    static java.sql.Connection con = DBConnection.getConnection("jdbc:mysql://localhost:33060/demo", "chris", "secret");

    static Scanner inputValue = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {


        boolean fin = false;
        while (!fin) {
            menu();
            int valor = obtenerOpcion();
            if (valor != 0) {
                ejecutarOpcion(valor);
            } else {
                System.out.println("Seguro que quieres salir?(Y/n)");
                String respuesta = inputValue.next();
                if (respuesta.equalsIgnoreCase("y") || respuesta.equalsIgnoreCase("yes") || respuesta.equalsIgnoreCase("ye")) {
                    fin = true;
                }
            }
        }
    }

    public static void menu(){
        System.out.println("0. Salir");
        System.out.println("1. Mostrar datos Jugadores");
        System.out.println("2. Mostrar datos Países");
        System.out.println("3. Dar de alta jugador");
        System.out.println("4. Dar de alta país");
        System.out.println("5. Dar de baja jugador");
        System.out.println("6. Dar de baja país");
    }

    public static int obtenerOpcion(){
        Scanner inputValue = new Scanner(System.in);
        System.out.println("Dame una opción: ");
        return inputValue.nextInt();
    }

    public static void ejecutarOpcion(int opcion) throws SQLException {
        switch(opcion){
            case 1:
                LeerDatosJugadores();
                break;
            case 2:
                LeerDatosPaises();
                break;
            case 3:
                DarDeAltaJugador();
                break;
            case 4:
                DarDeAltaPais();
                break;
            case 5:
                DarDeBajaJugador();
                break;
            case 6:
                DarDeBajaPais();
                break;
        }
    }

    private static void DarDeBajaPais() {
        PreparedStatement st = null;

        System.out.print("Código de país que quieres dar de baja: ");
        int input_cod_pais = inputValue.nextInt();

        String sql = "DELETE FROM PAISES WHERE cod_pais = ?;";
        try {
            st = con.prepareStatement(sql);
            st.setInt(1, input_cod_pais);

            st.executeUpdate();
            System.out.println("-------------------------------------\n" +
                               "|¡Se ha eliminado el país con éxito!|\n" +
                               "-------------------------------------");
            System.out.println();

        } catch (SQLException e){
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (st != null && !st.isClosed()){
                    st.close();
                }
            } catch (SQLException e) {
                System.out.println("No se ha podido cerrar el Statement por alguna razón");
            }
        }
    }

    private static void DarDeBajaJugador() {
        PreparedStatement st = null;

        System.out.print("Nombre del jugador que quieres dar de baja: ");
        String input_nombreJugador = inputValue.next();

        String sql = "DELETE FROM JUGADORES WHERE nombre = ?;";
        try {
            st = con.prepareStatement(sql);
            st.setString(1, input_nombreJugador);

            st.executeUpdate();
            System.out.println("----------------------------------------\n" +
                               "|¡Se ha eliminado el jugador con éxito!|\n" +
                               "----------------------------------------");
            System.out.println();

        } catch (SQLException e){
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (st != null && !st.isClosed()){
                    st.close();
                }
            } catch (SQLException e) {
                System.out.println("No se ha podido cerrar el Statement por alguna razón");
            }
        }
    }


    public static void LeerDatosJugadores() throws SQLException {

        Statement st = con.createStatement();

        String JugadoresAll = "SELECT * FROM JUGADORES";

        ResultSet rs_JugadoresAll = st.executeQuery(JugadoresAll);

        System.out.println();
        System.out.printf("|%20s|%20s|%20s|%20s|%20s|%n", "[Código País]", "[Nombre Jugador]", "[Año Nacimiento]", "[Altura]", "[Club]");
        while (rs_JugadoresAll.next()){
            int codPais = rs_JugadoresAll.getInt("cod_pais");
            String nombreJugador = rs_JugadoresAll.getString("nombre");
            String anyoNacimiento = rs_JugadoresAll.getString("anyoNacimiento");
            int altura = rs_JugadoresAll.getInt("altura");
            String club = rs_JugadoresAll.getString("club");

            System.out.printf("|%20s|%20s|%20s|%20s|%20s|%n", codPais, nombreJugador, anyoNacimiento, altura, club);
        }
        System.out.println();

        st.close();
    }
    public static void LeerDatosPaises() throws SQLException {

        Statement st = con.createStatement();

        String PaisesAll = "SELECT * FROM PAISES";

        ResultSet rs_PaisesAll = st.executeQuery(PaisesAll);


        System.out.println();
        System.out.printf("|%13s|%13s|%n", "[Código País]", "[Nombre País]");
        while (rs_PaisesAll.next()){
            int codPais = rs_PaisesAll.getInt("cod_pais");
            String nombrePais = rs_PaisesAll.getString("nombrePais");

            System.out.printf("|%13s|%13s|%n", codPais, nombrePais);
        }
        System.out.println();

        st.close();
    }
    public static void DarDeAltaJugador() {

        PreparedStatement st = null;

        System.out.print("Código de país: ");
        int input_cod_pais = inputValue.nextInt();
        System.out.print("Nombre: ");
        String input_nombre = inputValue.next();
        System.out.print("Año de nacimiento: ");
        int input_anyoNacimiento = inputValue.nextInt();
        System.out.print("Altura: ");
        int input_altura = inputValue.nextInt();
        System.out.print("Club: ");
        String input_club = inputValue.next();

        String sql = "INSERT INTO JUGADORES (cod_pais, nombre, anyoNacimiento, altura, club) VALUES (?,?,?,?,?);";
        try {
        st = con.prepareStatement(sql);
        st.setInt(1, input_cod_pais);
        st.setString(2, input_nombre);
        st.setInt(3, input_anyoNacimiento);
        st.setInt(4, input_altura);
        st.setString(5, input_club);

        st.executeUpdate();
            System.out.println("----------------------------------------\n" +
                               "|¡Se ha insertado el jugador con éxito!|\n" +
                               "----------------------------------------");
            System.out.println();


    } catch (SQLException e){
        System.out.println("Error: " + e.getMessage());
    } finally {
            try {
                if (st != null && !st.isClosed()){
                    st.close();
                }
            } catch (SQLException e) {
                System.out.println("No se ha podido cerrar el Statement por alguna razón");
            }
        }
    }
    private static void DarDeAltaPais() {
        PreparedStatement st = null;

        System.out.print("Código de país: ");
        int input_cod_pais = inputValue.nextInt();
        System.out.print("Nombre país: ");
        String input_nombre = inputValue.next();

        String sql = "INSERT INTO PAISES (cod_pais, nombrePais) VALUES (?,?);";
        try {
            st = con.prepareStatement(sql);
            st.setInt(1, input_cod_pais);
            st.setString(2, input_nombre);

            st.executeUpdate();
            System.out.println("-------------------------------------\n" +
                               "|¡Se ha insertado el país con éxito!|\n" +
                               "-------------------------------------");
            System.out.println("Se ha insertado el país con éxito");

        } catch (SQLException e){
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (st != null && !st.isClosed()){
                    st.close();
                }
            } catch (SQLException e) {
                System.out.println("No se ha podido cerrar el Statement por alguna razón");
            }
        }
    }







}
