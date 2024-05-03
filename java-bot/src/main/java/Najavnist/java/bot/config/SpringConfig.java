//package Najavnist.java.bot.config;
//
//import Najavnist.java.bot.service.JavaBot;
//import org.springframework.context.annotation.Bean;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import org.springframework.orm.hibernate5.HibernateTransactionManager;
//import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//import java.util.Properties;
//
//public class SpringConfig {
//    @Bean
//    public DataSource dataSource(JavaBot javaBot) {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//
//        dataSource.setDriverClassName(javaBot.getEnv().getRequiredProperty("hibernate.driver_class"));
//        dataSource.setUrl(javaBot.getEnv().getRequiredProperty("hibernate.connection.url"));
//        dataSource.setUsername(javaBot.getEnv().getRequiredProperty("hibernate. connection. username") );
//        dataSource.setPassword(javaBot.getEnv().getRequiredProperty("hibernate.connection.password") );
//
//        return dataSource;
//    }
//        private Properties hibernateProperties(JavaBot javaBot){
//        Properties properties=new Properties();
//        properties.put("hibernate.dialekt", javaBot.getEnv().getRequiredProperty("hibernate.dialekt"));
//        properties.put("hibernate.show_sql",javaBot.getEnv().getRequiredProperty("hibernate.show_sql"));
//        return properties;
//    }
//    @Bean
//    public LocalSessionFactoryBean sessionFactory(JavaBot javaBot){
//        LocalSessionFactoryBean sessionFactory=new LocalSessionFactoryBean();
//        sessionFactory.setDataSource(dataSource(javaBot));
//        sessionFactory.setPackagesToScan("Najavnist.DataBase");//сканує папку на наявність Entyty
//        sessionFactory.setHibernateProperties(hibernateProperties(javaBot));
//        return sessionFactory;
//    }
//    @Bean
//    public PlatformTransactionManager hibernateTransactionManager(JavaBot javaBot) {
//        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
//        transactionManager. setSessionFactory(sessionFactory(javaBot).getObject());
//
//        return transactionManager;
//
//}
//}
