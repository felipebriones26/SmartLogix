package cl.smartlogix.ms_usuarios.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("OPERADOR_PYME")
public class OperadorPyme extends Usuario {
    private String rutPyme; 
}