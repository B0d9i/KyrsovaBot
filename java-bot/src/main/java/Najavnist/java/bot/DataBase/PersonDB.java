package Najavnist.java.bot.DataBase;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.transaction.Transactional;
import java.util.List;

public class PersonDB {

    public List<Person> index(){
        return null;
    }
    public Person show(int id){
        return null;
    }
    public void save(Person person){

    }
    public void update(int id,Person updatePerson){

    }
    public void delete(int id){

    }
    public static void saveCheckSurname() {
        Configuration configuration = new Configuration().addAnnotatedClass(Person.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            // Виправити запит на оновлення
            session.createQuery("delete from  Person WHERE checkKeyboard>0")
                    .executeUpdate();
            // Вибірка всіх записів
            List<Person> people = session.createQuery("from Person").list();
            // Виведення записів
            for (Person person : people) {
                System.out.println(person);
            }
            session.getTransaction().commit();
        }finally{
            sessionFactory.close();
        }

    }



//    public boolean checkSurname(){
//        Configuration configuration = new Configuration().addAnnotatedClass(Person.class);
//
//        SessionFactory sessionFactory = configuration.buildSessionFactory();
//        Session session = sessionFactory.getCurrentSession();
//
//        try {
//        session.beginTransaction();
//
//        @Query("SELECT u from Person u where u.checkSurname=true")
//        public List<Person> findUsersWithCheckUserTrue(){
//            return en
//            }
//
//        }finally{
//        sessionFactory.close();
//        }
//
//        return ;
//    }

}
