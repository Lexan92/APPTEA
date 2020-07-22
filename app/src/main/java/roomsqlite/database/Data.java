package roomsqlite.database;

import roomsqlite.entidades.CategoriaHabCotidiana;
import roomsqlite.entidades.CategoriaJuego;
import roomsqlite.entidades.CategoriaPictograma;
import roomsqlite.entidades.Juego;
import roomsqlite.entidades.Pais;

public class Data {

    public static CategoriaHabCotidiana[] catHabCotidianasData(){
        return new CategoriaHabCotidiana[]{
                new CategoriaHabCotidiana(1, "Aseo personal",true),
                new CategoriaHabCotidiana(2, "El baño",true),
                new CategoriaHabCotidiana(3, "Agenda diaria",true),
                new CategoriaHabCotidiana(4, "Salud",true),
                new CategoriaHabCotidiana(5, "Comer",true),
                new CategoriaHabCotidiana(6, "Fiestas",true),
                new CategoriaHabCotidiana(7, "Vacaciones",true),
                new CategoriaHabCotidiana(8, "Cuidado Personal",true),
                new CategoriaHabCotidiana(9, "Paseos",true),
                new CategoriaHabCotidiana(10, "Amigos",true),
                new CategoriaHabCotidiana(11, "La cuidad",true),
                new CategoriaHabCotidiana(12, "La escuela",true)
        };
    }

    public static Pais[] paises(){
        return new Pais[]{
                new Pais(1, "El Salvador"),
                new Pais(2, "Guatemala"),
                new Pais(3, "Honduras")
        };
    }

    public static CategoriaJuego[] categoriaJuegos(){
        return new CategoriaJuego[]{
                new CategoriaJuego(1, "Juegos de Selección", true)
        };
    }

    public static Juego[] juegos(){
        return new Juego[]{
                new Juego(1,1,"Juego Vocales",true),
                new Juego(2,1,"Juego Numeros",true),
                new Juego(3,1,"Juego de la Frutas", true),
                new Juego(4,1,"Juego de las Verduras", false)

        };
    }

    public static CategoriaPictograma[] categoriaPictogramas(){
        return new CategoriaPictograma[]{
                new CategoriaPictograma(1,"Colores", true),
                new CategoriaPictograma(2,"Frutas", true),
                new CategoriaPictograma(3,"Animales",true),
                new CategoriaPictograma(4,"Verduras",true),
                new CategoriaPictograma(5,"Números",true),
                new CategoriaPictograma(6,"Emociones",true),
                new CategoriaPictograma(7,"Generales", false),
                new CategoriaPictograma(8,"Comida", false),
                new CategoriaPictograma(9,"Direcciones",false),
                new CategoriaPictograma(10,"Acciones",true),
                new CategoriaPictograma(11,"Enfermedades",true),
                new CategoriaPictograma(12,"Sentimientos",true),
                new CategoriaPictograma(13,"Bebidas", true),
                new CategoriaPictograma(14,"Aseo Personal", true),
                new CategoriaPictograma(15,"Accesorios",true),
                new CategoriaPictograma(16,"Ropa",true),
                new CategoriaPictograma(17,"Lugares",true),
                new CategoriaPictograma(18,"Juguetes",true),
                new CategoriaPictograma(19,"Abecedario", true),
                new CategoriaPictograma(20,"Instrumentos Musicales", true),
                new CategoriaPictograma(21,"Casa",true),
                new CategoriaPictograma(22,"Colegio",true),
                new CategoriaPictograma(23,"Cuerpo",true),
                new CategoriaPictograma(24,"Normas de Cortesía",true),
                new CategoriaPictograma(25,"Sensaciones", true),
                new CategoriaPictograma(26,"Medios de Transporte", true),
                new CategoriaPictograma(27,"Medios de Comunicación",true),
                new CategoriaPictograma(28,"Deporte",true),
                new CategoriaPictograma(29,"Pronombres",true),
                new CategoriaPictograma(30,"Preposiciones",true),
                new CategoriaPictograma(31,"Adjetivos", true),
                new CategoriaPictograma(32,"Adverbios", true),
                new CategoriaPictograma(33,"Verbos",true)
        };
    }

}
