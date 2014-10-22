package com.hotelbooking.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;


public class BaseDAO {

	/** 统计指定类的所有持久化对象 */
	public int countAll(Class clazz){
		// TODO Auto-generated method stub
		String hql = "select count(*) from "+clazz.getName()+ " as a";
		Long count=new Long("-1");
		Session session=HibernateSessionFactory.getSession();
		Transaction ts=null;
		try{
			Query query=session.createQuery(hql);
			ts=session.beginTransaction();
			query.setMaxResults(1);
			count=(Long)query.uniqueResult();
			ts.commit();
		}catch(Exception e){
			if(ts!=null)ts.rollback();
			System.out.println("【系统错误】在统计指定类的所有持久化对象时出错，原因：");
			e.printStackTrace();
		}finally{
			HibernateSessionFactory.closeSession();
		}
		return count.intValue();
	}

	/** 统计指定规则的查询结果 */
	public int countQuery(String hql, Object ...args) {
		// TODO Auto-generated method stub
		String counthql = hql;
		Long count=-new Long("-1");
		Session session=HibernateSessionFactory.getSession();
		Transaction ts=null;
		try{
			Query query=session.createQuery(hql);
			ts=session.beginTransaction();
			query.setMaxResults(1);
			for (int i = 0; i < args.length; i++)
				query.setParameter(i, args[i]);
			count=(Long) (query.uniqueResult());
			ts.commit();
		}catch(Exception e){
			if(ts!=null)ts.rollback();
			System.out.println("【系统错误】在统计指定规则的查询结果时出错，原因：");
			e.printStackTrace();
		}finally{
			HibernateSessionFactory.closeSession();
		}
		return count.intValue();
	}
	
	public void delele(Object obj)
	{
		Session session=HibernateSessionFactory.getSession();
		Transaction ts=null;
		try{
			ts=session.beginTransaction();
			session.delete(obj);
			ts.commit();
		}catch(Exception e){
			if(ts!=null)ts.rollback();
			System.out.println("【系统错误】在加载满足条件的持久化对象时出错，原因：");
			e.printStackTrace();
		}finally{
			HibernateSessionFactory.closeSession();
		}
	}

	/** 删除指定ID的持久化对象 */
	public void delById(Class clazz, Serializable id) {
		// TODO Auto-generated method stub
		Session session=HibernateSessionFactory.getSession();
		Transaction ts=null;
		try{
			ts=session.beginTransaction();
			session.delete(session.load(clazz, id));
			ts.commit();
		}catch(Exception e){
			if(ts!=null)ts.rollback();
			System.out.println("【系统错误】在加载满足条件的持久化对象时出错，原因：");
			e.printStackTrace();
		}finally{
			HibernateSessionFactory.closeSession();
		}

	}

	public Connection getConnection() {
		// TODO Auto-generated method stub
		return null;
	}

	/** 装载指定类的所有持久化对象 */
	public List listAll(Class clazz) {
		// TODO Auto-generated method stub
		List list=new ArrayList();
		String hql="from "+ clazz.getName();
		Session session=HibernateSessionFactory.getSession();
		Transaction ts=null;
		try{
			Query query=session.createQuery(hql);
			ts=session.beginTransaction();
			list=query.list();
			ts.commit();
			if(!Hibernate.isInitialized(list)){Hibernate.initialize(list);}
		}catch(Exception e){
			if(ts!=null)ts.rollback();
			System.out.println("【系统错误】在加载满足条件的持久化对象时出错，原因：");
			e.printStackTrace();
		}finally{
			HibernateSessionFactory.closeSession();
		}
		return list;
	}

	public List listAll(Class clazz, int pageNo, int pageSize) {
		List list=new ArrayList();
		String hql="from " + clazz.getName();
		Session session=HibernateSessionFactory.getSession();
		Transaction ts=null;
		try{
			Query query=session.createQuery(hql);
			query.setFirstResult(pageNo * pageSize);
			query.setMaxResults(pageSize);
			ts=session.beginTransaction();
			list=query.list();
			ts.commit();
			if(!Hibernate.isInitialized(list)){Hibernate.initialize(list);}
		}catch(Exception e){
			if(ts!=null)ts.rollback();
			System.out.println("【系统错误】在加载满足条件的持久化对象时出错，原因：");
			e.printStackTrace();
		}finally{
			HibernateSessionFactory.closeSession();
		}
		return list;
	}

	/** 加载指定ID的持久化对象 */
	public Object getById(Class clazz, Serializable id) {
		// TODO Auto-generated method stub 
		Object obj=null;
		Session session=HibernateSessionFactory.getSession();
		Transaction ts=null;
		try{
			ts=session.beginTransaction();
			obj=session.get(clazz, id);
			ts.commit();
		}catch(Exception e){
			if(ts!=null)ts.rollback();
			System.out.println("【系统错误】在加载指定ID的持久化对象时出错，原因：");
			e.printStackTrace();
		}finally{
			HibernateSessionFactory.closeSession();
		}
		return obj;
	}

	/**加载满足条件的持久化对象*/
	public Object loadObject(String hql, Object ...args) {
		// TODO Auto-generated method stub
		Object obj=null;
		Session session=HibernateSessionFactory.getSession();
		Transaction ts=null;
		try{
			Query query=session.createQuery(hql);
			for (int i = 0; i < args.length; i++)
				query.setParameter(i, args[i]);
			ts=session.beginTransaction();
			List list=query.list();
			ts.commit();
			if(!Hibernate.isInitialized(list)){Hibernate.initialize(list);}
			if(list.size()>0) obj=list.get(0);
		}catch(Exception e){
			if(ts!=null)ts.rollback();
			System.out.println("【系统错误】在加载满足条件的持久化对象时出错，原因：");
			e.printStackTrace();
		}finally{
			HibernateSessionFactory.closeSession();
		}
		return obj;
	}

	/** 查询满足条件的持久化对象 */
	public List query(String hql, Object ...args) {
		// TODO Auto-generated method stub
		List list=new ArrayList();
		Session session=HibernateSessionFactory.getSession();
		Transaction ts=null;
		try{
			Query query=session.createQuery(hql);
			for (int i = 0; i < args.length; i++)
				query.setParameter(i, args[i]);
			ts=session.beginTransaction();
			list=query.list();
			ts.commit();
			if(!Hibernate.isInitialized(list)){Hibernate.initialize(list);}
		}catch(Exception e){
			if(ts!=null)ts.rollback();
			System.out.println("【系统错误】在查询满足条件的持久化对象时出错，原因：");
			e.printStackTrace();
		}finally{
			HibernateSessionFactory.closeSession();
		}
		return list;
	}
	
	public List query(int pageNum, int pageSize, String hql, Object ...args) {
		// TODO Auto-generated method stub
		List list=new ArrayList();
		Session session=HibernateSessionFactory.getSession();
		Transaction ts=null;
		try{
			Query query=session.createQuery(hql);
			for (int i = 0; i < args.length; i++)
				query.setParameter(i, args[i]);
			query.setFirstResult(pageNum * pageSize);
			query.setMaxResults(pageSize);
			ts=session.beginTransaction();
			list=query.list();
			ts.commit();
			if(!Hibernate.isInitialized(list)){Hibernate.initialize(list);}
		}catch(Exception e){
			if(ts!=null)ts.rollback();
			System.out.println("【系统错误】在查询满足条件的持久化对象时出错，原因：");
			e.printStackTrace();
		}finally{
			HibernateSessionFactory.closeSession();
		}
		return list;
	}

	/** 保存或更新指定的持久化对象 */
	public boolean save(Object obj) {
		// TODO Auto-generated method stub
		Session session=HibernateSessionFactory.getSession();
		Transaction ts=null;
		try{
			ts=session.beginTransaction();
			session.save(obj);
			ts.commit();
			return true;
		}catch(Exception e){
			if(ts!=null)ts.rollback();
			System.out.println("【系统错误】在保存指定的持久化对象时出错，原因：");
			e.printStackTrace();
			return false;
		}finally{
			HibernateSessionFactory.closeSession();
		}

	}
	
	/** 更新指定的持久化对象 */
	public void update(Object obj) {
		// TODO Auto-generated method stub
		Session session=HibernateSessionFactory.getSession();
		Transaction ts=null;
		try{
			ts=session.beginTransaction();
			session.update(obj);
			ts.commit();
		}catch(Exception e){
			if(ts!=null)ts.rollback();
			System.out.println("【系统错误】在更新指定的持久化对象时出错，原因：");
			e.printStackTrace();
		}finally{
			HibernateSessionFactory.closeSession();
		}

	}
	/** 条件更新数据 */
	public int update(String hql) {
		// TODO Auto-generated method stub
		int count=-1;
		Session session=HibernateSessionFactory.getSession();
		Transaction ts=null;
		try{
			Query query=session.createQuery(hql);
			ts=session.beginTransaction();
			count=query.executeUpdate();
			ts.commit();
		}catch(Exception e){
			if(ts!=null)ts.rollback();
			System.out.println("【系统错误】在加载满足条件的持久化对象时出错，原因：");
			e.printStackTrace();
		}finally{
			HibernateSessionFactory.closeSession();
		}
		return count;
	}
	
}
