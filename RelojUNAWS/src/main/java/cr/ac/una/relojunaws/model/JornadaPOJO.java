package cr.ac.una.relojunaws.model;

import java.time.Duration;
import java.time.LocalDate;

public class JornadaPOJO {

    private Empleado empleado;
    private LocalDate fecha;
    private Marca marcaEntrada;
    private Marca marcaSalida;
    private Duration horasTrabajadas;
    private boolean esDiaLibre;

    public JornadaPOJO() {
    }

    public JornadaPOJO(Empleado empleado, LocalDate fecha, Marca marcaEntrada, Marca marcaSalida, Duration horasTrabajadas, boolean esDiaLibre) {
        this.empleado = empleado;
        this.fecha = fecha;
        this.marcaEntrada = marcaEntrada;
        this.marcaSalida = marcaSalida;
        this.horasTrabajadas = horasTrabajadas;
        this.esDiaLibre = esDiaLibre;
    }

    public boolean isCompleta() {
        return marcaEntrada != null && marcaSalida != null;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Marca getEntrada() {
        return marcaEntrada;
    }

    public void setEntrada(Marca marcaEntrada) {
        this.marcaEntrada = marcaEntrada;
    }

    public Marca getSalida() {
        return marcaSalida;
    }

    public void setSalida(Marca marcaSalida) {
        this.marcaSalida = marcaSalida;
    }

    public Duration getHorasTrabajadas() {
        return horasTrabajadas;
    }

    public void setHorasTrabajadas(Duration horasTrabajadas) {
        this.horasTrabajadas = horasTrabajadas;
    }

    public boolean isEsDiaLibre() {
        return esDiaLibre;
    }

    public void setEsDiaLibre(boolean esDiaLibre) {
        this.esDiaLibre = esDiaLibre;
    }

    @Override
    public String toString() {
        return "JornadaPOJO{" + "empleado=" + empleado.getId() + ", fecha=" + fecha + ", marcaEntrada=" + marcaEntrada.getId() + ", marcaSalida=" + marcaSalida.getId() + ", horasTrabajadas=" + horasTrabajadas + ", esDiaLibre=" + esDiaLibre + '}';
    }

}
