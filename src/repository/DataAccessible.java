package repository;

import java.util.ArrayList;

public interface DataAccessible<T,K > {
     boolean save(T type) ;
     boolean deleteAll() ;
     ArrayList<T> findAll();
     T findById(K id);
}
