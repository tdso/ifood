package com.tdso.ifood.mp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PedidoDto {

    public PedidoDto() {
    }

    private Long id;
    private String texto;

    // public Restaurante restaurante;
    // public List<Prato> pratos = new ArrayList<>();
    // public BigDecimal total;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

}
