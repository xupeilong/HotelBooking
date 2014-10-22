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

	/** ͳ��ָ��������г־û����� */
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
			System.out.println("��ϵͳ������ͳ��ָ��������г־û�����ʱ����ԭ��");
			e.printStackTrace();
		}finally{
			HibernateSessionFactory.closeSession();
		}
		return count.intValue();
	}

	/** ͳ��ָ������Ĳ�ѯ��� */
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
			System.out.println("��ϵͳ������ͳ��ָ������Ĳ�ѯ���ʱ����ԭ��");
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
			System.out.println("��ϵͳ�����ڼ������������ĳ־û�����ʱ����ԭ��");
			e.printStackTrace();
		}finally{
			HibernateSessionFactory.closeSession();
		}
	}

	/** ɾ��ָ��ID�ĳ־û����� */
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
			System.out.println("��ϵͳ�����ڼ������������ĳ־û�����ʱ����ԭ��");
			e.printStackTrace();
		}finally{
			HibernateSessionFactory.closeSession();
		}

	}

	public Connection getConnection() {
		// TODO Auto-generated method stub
		return null;
	}

	/** װ��ָ��������г־û����� */
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
			System.out.println("��ϵͳ�����ڼ������������ĳ־û�����ʱ����ԭ��");
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
			System.out.println("��ϵͳ�����ڼ������������ĳ־û�����ʱ����ԭ��");
			e.printStackTrace();
		}finally{
			HibernateSessionFactory.closeSession();
		}
		return list;
	}

	/** ����ָ��ID�ĳ־û����� */
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
			System.out.println("��ϵͳ�����ڼ���ָ��ID�ĳ־û�����ʱ����ԭ��");
			e.printStackTrace();
		}finally{
			HibernateSessionFactory.closeSession();
		}
		return obj;
	}

	/**�������������ĳ־û�����*/
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
			System.out.println("��ϵͳ�����ڼ������������ĳ־û�����ʱ����ԭ��");
			e.printStackTrace();
		}finally{
			HibernateSessionFactory.closeSession();
		}
		return obj;
	}

	/** ��ѯ���������ĳ־û����� */
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
			System.out.println("��ϵͳ�����ڲ�ѯ���������ĳ־û�����ʱ����ԭ��");
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
			System.out.println("��ϵͳ�����ڲ�ѯ���������ĳ־û�����ʱ����ԭ��");
			e.printStackTrace();
		}finally{
			HibernateSessionFactory.closeSession();
		}
		return list;
	}

	/** ��������ָ���ĳ־û����� */
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
			System.out.println("��ϵͳ�����ڱ���ָ���ĳ־û�����ʱ����ԭ��");
			e.printStackTrace();
			return false;
		}finally{
			HibernateSessionFactory.closeSession();
		}

	}
	
	/** ����ָ���ĳ־û����� */
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
			System.out.println("��ϵͳ�����ڸ���ָ���ĳ־û�����ʱ����ԭ��");
			e.printStackTrace();
		}finally{
			HibernateSessionFactory.closeSession();
		}

	}
	/** ������������ */
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
			System.out.println("��ϵͳ�����ڼ������������ĳ־û�����ʱ����ԭ��");
			e.printStackTrace();
		}finally{
			HibernateSessionFactory.closeSession();
		}
		return count;
	}
	
}
