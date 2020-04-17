package com.example.testParseMVCListViewSubActivity.Model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Ingrediente")
public class Ingrediente extends ParseObject {

    public Ingrediente() {
    }

    public String getNombre() {return getString("nombre");
    }

    public void setNombre(String nombre) {
        put("nombre",nombre);
    }

    @Override
    public String toString() {
        return this.getNombre()+" ";
    }
}