public class Jugador {
    private int codJugador;
    private int codPais;
    private String nomJugador;
    private int fechaNacimiento;
    private float alturaJugador;
    private String clubJugador;

    public String getNacionalidad() {
        return nacionalidad;
    }

    private String nacionalidad;



    public Jugador(int codPais, String nacionalidad, String nomJugador, int fechaNacimiento, float alturaJugador, String clubJugador){
        this.codPais = codPais;
        this.nacionalidad = nacionalidad;
        this.nomJugador = nomJugador;
        this.fechaNacimiento = fechaNacimiento;
        this.alturaJugador = alturaJugador;
        this.clubJugador = clubJugador;
    }

    public int getCodPais() {
        return codPais;
    }

    public void setCodPais(int codPais) {
        this.codPais = codPais;
    }

    public String getNomJugador() {
        return nomJugador;
    }

    public void setNomJugador(String nomJugador) {
        this.nomJugador = nomJugador;
    }

    public int getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(int fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public float getAlturaJugador() {
        return alturaJugador;
    }

    public void setAlturaJugador(float alturaJugador) {
        this.alturaJugador = alturaJugador;
    }

    public String getClubJugador() {
        return clubJugador;
    }

    public void setClubJugador(String clubJugador) {
        this.clubJugador = clubJugador;
    }
}
