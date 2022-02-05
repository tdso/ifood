package com.tdso.ifood.cadastro.model.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import com.tdso.ifood.cadastro.model.dto.groups.ValidationGroups;
import com.tdso.ifood.cadastro.model.dto.groups.ValidationGroups.Post;
import com.tdso.ifood.cadastro.model.dto.groups.ValidationGroups.Put;
import com.tdso.ifood.cadastro.model.validator.PaginaValidator;

@PaginaValidator
public class LivroDto {

    @Null(groups = ValidationGroups.Post.class)
    @NotNull(groups = ValidationGroups.Put.class, message = "O id da obra deve ser informado")
    public Long id;

    @NotBlank(message = "O nome do autor deve ser informado")
    public String autor;

    @NotEmpty(message = "O nome da obra deve ser informado")
    public String obra;

    // @Min(value = 3, message = "A obra deve possuir pelo menos 3 páginas")
    public Integer paginas;

    public LivroDto() {
    }

    public LivroDto(@Null(groups = Post.class) @NotNull(groups = Put.class) Long id,
            @NotBlank(message = "O nome do autor deve ser informado") String autor,
            @NotEmpty(message = "O nome da obra deve ser informado") String obra,
            @Min(value = 3, message = "A obra deve possuir pelo menos 3 páginas") Integer paginas) {
        this.id = id;
        this.autor = autor;
        this.obra = obra;
        this.paginas = paginas;
    }

    public LivroDto(@NotBlank(message = "O nome do autor deve ser informado") String autor,
            @NotEmpty(message = "O nome da obra deve ser informado") String obra,
            @Min(value = 3, message = "A obra deve possuir pelo menos 3 páginas") Integer paginas) {
        this.autor = autor;
        this.obra = obra;
        this.paginas = paginas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getObra() {
        return obra;
    }

    public void setObra(String obra) {
        this.obra = obra;
    }

    public Integer getPaginas() {
        return paginas;
    }

    public void setPaginas(Integer paginas) {
        this.paginas = paginas;
    }

}