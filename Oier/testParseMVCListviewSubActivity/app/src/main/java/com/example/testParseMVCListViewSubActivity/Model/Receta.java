package com.example.testParseMVCListViewSubActivity.Model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Receta")
public class Receta extends ParseObject {

    public Receta() {
    }

    public String getNombre() {return getString("nombre");
    }

    public void setNombre(String nombre) {
        put("nombre",nombre);
    }

    public String getDescripcion() {return getString("descripcion");
    }

    public void setDescripcion(String desc){ put("descripcion",desc);
    }

    @Override
    public String toString() {
        return this.getNombre()+" ";
    }
}
