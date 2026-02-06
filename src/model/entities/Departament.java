package model.entities;

import java.io.Serializable;
import java.util.Objects;

public class Departament implements Serializable {
    private int id;
    private String nome;

    public Departament() {
    }

    public Departament(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Departament that = (Departament) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Departament{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
