package ru.afrolovskiy.accountService;

import org.hibernate.Session;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

import ru.afrolovskiy.base.User;
import ru.afrolovskiy.base.Users;

@SuppressWarnings("deprecation")
public class DBUsers implements Users {
	Configuration configuration = null;
	
	public DBUsers() {
		AnnotationConfiguration annotationConfiguration = 
				new AnnotationConfiguration().addAnnotatedClass(User.class);
		configuration = annotationConfiguration.configure();		
	}
	
	public Integer getId(String name) {
		Session session = configuration.buildSessionFactory().openSession();
		User user = (User) session.createQuery("from User where name = '" + name + "'").uniqueResult();
	    session.close();
	    if (user != null) {
	    	System.out.println("name:" + user.getName() + " id:" + user.getId());
	    	return user.getId();
	    }
	    return null;	    
	}

}
