package tn.esprit.services;

import javafx.collections.ObservableList;
import tn.esprit.entities.User;

public interface IUserservice{

    User findByUsername(String username);
    public ObservableList<User> getAll();
}
