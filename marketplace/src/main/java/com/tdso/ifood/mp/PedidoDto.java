package com.tdso.ifood.mp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PedidoDto {

    public PedidoDto() {
    }

    private Long id;
    private String texto;
    private Restaurante restaurante;
    private List<Prato> pratos = new ArrayList<>();
    private BigDecimal total;

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

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public List<Prato> getPratos() {
        return pratos;
    }

    public void setPratos(List<Prato> pratos) {
        this.pratos = pratos;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public static PedidoDto from(PratoDto pratoDto) {
        PedidoDto pedidoDto = new PedidoDto();
        pedidoDto.setId(pratoDto.id);
        // pedidoDto.setRestaurante(pratoDto.);
        // pedidoDto.setPratos(pratos);

        return pedidoDto;

    }

}
