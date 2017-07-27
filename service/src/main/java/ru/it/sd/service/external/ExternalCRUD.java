package ru.it.sd.service.external;

import ru.it.sd.model.HasId;

import java.util.Set;

/**
 * Created by user on 27.07.2017.
 */
public interface ExternalCRUD<T extends HasId,K> {
   K read(long id);

   long create(T entity);

   long update(T entity);

   //long patch(T entity, Set<String> fields);

   void delete(T entity);
}
