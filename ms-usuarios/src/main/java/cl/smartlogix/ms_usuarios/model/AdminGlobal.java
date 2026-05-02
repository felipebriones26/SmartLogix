package cl.smartlogix.ms_usuarios.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("ADMIN_GLOBAL")
public class AdminGlobal extends Usuario {
    private String nivelAcceso = "TOTAL";
}
