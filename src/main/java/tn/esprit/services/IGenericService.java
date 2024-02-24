package tn.esprit.services;

import javafx.collections.ObservableList;

public interface IGenericService<E> {
    public E save(E entity);

    public void update(E entity);

    public void deleteById(int id);

    public ObservableList<E> getAll();

    public E getById(int id);

    public boolean find(E e);

    public E findOne(E entity);
}
