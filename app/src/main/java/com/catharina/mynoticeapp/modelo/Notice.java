package com.catharina.mynoticeapp.modelo;

/**
 * Created by catharina on 17/09/16.
 */
public class Notice {

    private long id;
    private String titulo;
    private String corpo;
    private String date;

    public Notice(long id, String titulo, String corpo, String date) {
        this.id = id;
        this.titulo = titulo;
        this.corpo = corpo;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCorpo() {
        return corpo;
    }

    public void setCorpo(String corpo) {
        this.corpo = corpo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Notice notice = (Notice) o;

        if (id != notice.id) return false;
        if (!titulo.equals(notice.titulo)) return false;
        if (corpo != null ? !corpo.equals(notice.corpo) : notice.corpo != null) return false;
        return date != null ? date.equals(notice.date) : notice.date == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + titulo.hashCode();
        result = 31 * result + (corpo != null ? corpo.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
