package com.training.main;

import java.util.List;
import java.util.Random;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.training.model.Student;
import com.training.util.HibernateUtil;

public class HqlMain {

	public static void main(String[] args) {
		Session session=HibernateUtil.buildSessionFactory().getCurrentSession();
		session.beginTransaction();
		
        //1)insert the bulk of records(hbm2ddl->create)
		Random r=new Random(); 
		for(int i=1;i<=10;i++) 
		{
			Student student=new Student();
			//student.setRollno(i);
			student.setName("Name"+i);
			student.setMarks(r.nextInt(100));
			session.persist(student);
		}
		
		/*//2)fetch the particular record from table(hbm2ddl->update)
		 
		@SuppressWarnings("deprecation")
		Query q=session.createQuery("from Student where rollno=5");
		Student student=(Student)q.uniqueResult();
		System.out.println("rollno: "+student.getRollno()+" name: "+student.getName()+" marks: "+student.getMarks());
		
		//3)fetch multiple datas from table
		Query q=session.createQuery("select rollno,name,marks from Student s where s.marks>50");
		List<Student[]> stu=(List<Student[]>)q.list();
		for(Object[] o:stu)
		{
			System.out.println(o[0]+" "+o[1]+" "+o[2]);	
		}	*/	
		/*//4)aggregate function
		Query q=session.createQuery("select sum(marks) from Student s where s.marks>50");
		Object marks =(Object)q.uniqueResult();
			System.out.println(marks);*/
		/*//5)preinit value and preparedStatement to the query
		int b=50;
		Query q=session.createQuery("select sum(marks) from Student s where s.marks>"+b);*/
		
		//prepared statement
		  int b=50;
		Query q=session.createQuery("select sum(marks) from Student s where s.marks>:a");
		q.setParameter("a",b);
		Object marks =(Object)q.uniqueResult();
		System.out.println(marks);
		
		session.getTransaction().commit();
		System.out.println("Hibernate Query Language");
	}
	
	

}
