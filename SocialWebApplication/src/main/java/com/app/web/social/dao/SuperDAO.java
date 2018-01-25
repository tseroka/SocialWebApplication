package com.app.web.social.dao;

import java.io.Serializable;

import java.lang.reflect.ParameterizedType;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
 
public abstract class SuperDAO<PrimaryKey extends Serializable, T> {
     
    private Class<T> entity;
     
    @SuppressWarnings("unchecked")
	public SuperDAO()
    {
        this.entity =(Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }
     
    @Autowired
    private SessionFactory sessionFactory;
 
    protected Session getSession()
    {
        return sessionFactory.getCurrentSession();
    }
 
    protected Session openSession()
    {
        return sessionFactory.openSession();
    }
    
    public T getEntityByPrimaryKey(PrimaryKey key) 
    {
        return (T) getSession().get(entity, key);
    }
 
    public T loadEntityByPrimaryKey(PrimaryKey key) 
    {
        return (T) getSession().load(entity, key);
    }
    
    public void persist(T entity) 
    {
        getSession().persist(entity);
    }
 
    public void update(T entity) 
    {
        getSession().update(entity);
    }
    
    public void remove(T entity) 
    {
        getSession().remove(entity);
    }
  
}