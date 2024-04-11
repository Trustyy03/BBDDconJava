import java.io.*;
import java.nio.Buffer;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import static java.io.DataInputStream.readUTF;

/*0. salir
* 1. cargar datos iniciales
* 2. mostrar datos
* 3. guardar datos.
* 4. borrar datos.
* 5. Incorporar datos de fichero.
* 6. Ordenar datos.
*/
public class ListaJugadores {

    static java.sql.Connection con = DBConnection.getConnection("jdbc:mysql://192.168.43.156:33060/demo", "chris", "secret");
//    static java.sql.Connection con = DBConnection.getConnection("jdbc:mysql://192.168.43.156:33060/demo", "chris", "secret"); //PARA ADRIÁN
    static Scanner inputValue = new Scanner(System.in);

    public static void main(String[] args) throws SQLException, IOException {


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
        System.out.println("7. Modificar jugador");
        System.out.println("8. Cargar datos de jugadores desde archivo");
    }

    public static int obtenerOpcion(){
        Scanner inputValue = new Scanner(System.in);
        System.out.println("Dame una opción: ");
        return inputValue.nextInt();
    }

    public static void ejecutarOpcion(int opcion) throws SQLException, IOException {
        switch(opcion){
            case 1:
                leerDatosJugadores();
                break;
            case 2:
                leerDatosPaises();
                break;
            case 3:
                darDeAltaJugador();
                break;
            case 4:
                darDeAltaPais();
                break;
            case 5:
                darDeBajaJugador();
                break;
            case 6:
                darDeBajaPais();
                break;
            case 7:
                modificaJugador();
                break;
            case 8:
                cargaJugadoresDesdeArchivo();
                break;
        }
    }

    private static void darDeBajaPais() {
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

    private static void darDeBajaJugador() {
        PreparedStatement st = null;

        System.out.print("Introduce el código de jugador que quieres dar de baja: ");
        String inputCodJugador = inputValue.next();

        String sql = "DELETE FROM JUGADORES WHERE cod_jugador = ?;";
        try {
            st = con.prepareStatement(sql);
            st.setString(1, inputCodJugador);

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


    public static void leerDatosJugadores() throws SQLException {

        Statement st = con.createStatement();

        String JugadoresAll = "SELECT * FROM JUGADORES";

        ResultSet rs_JugadoresAll = st.executeQuery(JugadoresAll);

        System.out.println();
        System.out.printf("|%20s|%20s|%20s|%20s|%20s|%20s|%n", "[Código Jugador]","[Código País]", "[Nombre Jugador]", "[Año Nacimiento]", "[Altura]", "[Club]");
        while (rs_JugadoresAll.next()){
            int codJugador = rs_JugadoresAll.getInt("cod_jugador");
            int codPais = rs_JugadoresAll.getInt("cod_pais");
            String nombreJugador = rs_JugadoresAll.getString("nombre");
            String anyoNacimiento = rs_JugadoresAll.getString("anyoNacimiento");
            int altura = rs_JugadoresAll.getInt("altura");
            String club = rs_JugadoresAll.getString("club");

            System.out.printf("|%20s|%20s|%20s|%20s|%20s|%20s|%n", codJugador,codPais, nombreJugador, anyoNacimiento, altura, club);
        }
        System.out.println();

        st.close();
    }
    public static void leerDatosPaises() throws SQLException {

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
    public static void darDeAltaJugador() {

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
    private static void darDeAltaPais() {
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
    public static void modificaJugador(){
        PreparedStatement pst = null;
        Statement st = null;

        System.out.print("Introduce el código de jugador que quieres modificar: ");
        int inputCodJugador = inputValue.nextInt();

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


        String sql = "UPDATE JUGADORES SET cod_pais = ?, nombre = ?, anyoNacimiento = ?, altura = ?, club = ? where cod_jugador = ?;";

        try {

            pst = con.prepareStatement(sql);

            pst.setInt(6, inputCodJugador);
            pst.setInt(1, input_cod_pais);
            pst.setString(2, input_nombre);
            pst.setInt(3,input_anyoNacimiento);
            pst.setInt(4,input_altura);
            pst.setString(5,input_club);

            pst.executeUpdate();


            System.out.println("----------------------------------------\n" +
                    "|¡Se ha modificado el jugador con éxito!|\n" +
                    "----------------------------------------");
            System.out.println();

        } catch (SQLException e){
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (pst != null && !pst.isClosed()){
                    pst.close();
                }
            } catch (SQLException e) {
                System.out.println("No se ha podido cerrar el Statement por alguna razón");
            }
        }
    }
    public static void cargaJugadoresDesdeArchivo() throws IOException {

        //String cadena;
        DataInputStream f = new DataInputStream(new FileInputStream("./src/jugadoresAdri.dat"));


        while (f.available()>0){
            System.out.println("Código País: " + f.readInt());
            System.out.println("Nacionalidad: " + f.readUTF());
            System.out.println("Nombre: " + f.readUTF());
            System.out.println("Año nacimiento: " + f.readInt());
            System.out.println("Altura: " + f.readFloat());
            System.out.println("Equipo: " + f.readUTF());
            System.out.println();

        }
        f.close();

        /*
        FileReader equipo = new FileReader("./src/equipo.dat");
        BufferedReader prueba = new BufferedReader(equipo);
        while((cadena = prueba.readLine())!=null){
            System.out.println(cadena);
        }
        prueba.close();*/



    }







}
