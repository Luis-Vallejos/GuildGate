package com.guildgate.web.Service.generic;

/**
 *
 * @author Juan - Luis
 * @param <T>
 * @param <ID>
 */
public interface IWriteService<T, ID> {

    boolean create(T entity);

    boolean edit(T entity);

    boolean delete(ID id);
}
