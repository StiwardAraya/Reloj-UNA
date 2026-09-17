package cr.ac.una.relojunaws.model;

import cr.ac.una.relojunaws.model.dto.PlanillaDTO;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "PLANILLA", schema = "relojUNA")
@NamedQueries({
    @NamedQuery(name = "Planilla.findAll", query = "SELECT p FROM Planilla p"),
    @NamedQuery(name = "Planilla.findById", query = "SELECT p FROM Planilla p WHERE p.id = :id")
})
public class Planilla {

    @Id
    @SequenceGenerator(name = "PLN_ID_GENERATOR", sequenceName = "relojUNA.PLN_SEQ_01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PLN_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Basic(optional = false)
    @Column(name = "mes")
    private Integer mes;

    @Basic(optional = false)
    @Column(name = "anio")
    private Integer anio;

    @Basic(optional = false)
    @Column(name = "fecha_generacion")
    private LocalDate fechaGeneracion;

    @Basic(optional = false)
    @Column(name = "total_pagado")
    private BigDecimal totalPagado;

    @Version
    @Basic(optional = false)
    @Column(name = "version_planilla")
    private Long version;

    @OneToMany(mappedBy = "planilla", fetch = FetchType.LAZY)
    private List<DetallePlanilla> detallesPlanilla;

    public Planilla() {
    }

    public Planilla(Long id) {
        this.id = id;
    }

    public Planilla(PlanillaDTO dto) {
        this.id = dto.getId();
    }

    public final void actualizar(PlanillaDTO dto) {
        this.mes = dto.getMes();
        this.anio = dto.getAnio();
        this.fechaGeneracion = dto.getFechaGeneracion();
        this.totalPagado = dto.getTotalPagado();
        this.version = dto.getVersion();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public LocalDate getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(LocalDate fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public BigDecimal getTotalPagado() {
        return totalPagado;
    }

    public void setTotalPagado(BigDecimal totalPagado) {
        this.totalPagado = totalPagado;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public List<DetallePlanilla> getDetallesPlanilla() {
        return detallesPlanilla;
    }

    public void setDetallesPlanilla(List<DetallePlanilla> detallesPlanilla) {
        this.detallesPlanilla = detallesPlanilla;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Planilla other = (Planilla) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Planilla{" + "id=" + id + '}';
    }

}
