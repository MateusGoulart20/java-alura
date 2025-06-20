package br.com.alurases.salurase.model;

public enum Categoria {
    ACAO("Action","Ação"),
    ROMANCE("Romance","Romance"),
    COMEDIA("Comedy","Comédia"),
    DRAMA("Drama","Drama"),
    CRIME("Crime","Crime");

    private String categoriaOmdb;
    private String categoriaPortugues;

    Categoria(String categoriaOmdb, String categoriaPortugues){
        this.categoriaOmdb = categoriaOmdb;
        this.categoriaPortugues = categoriaPortugues;
    }

    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        return fromPortugues(text);
        //throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }
    public static Categoria fromPortugues(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaPortugues.toUpperCase().contains(text.toUpperCase())
                || categoria.categoriaPortugues.equalsIgnoreCase(text)
                || categoria.categoriaOmdb.toUpperCase().contains(text.toUpperCase())
                || categoria.categoriaOmdb.equalsIgnoreCase(text)
            ) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }
}
