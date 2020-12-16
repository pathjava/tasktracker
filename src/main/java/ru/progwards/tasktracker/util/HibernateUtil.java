package ru.progwards.tasktracker.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;

@Service
public class HibernateUtil {

    private static final SessionFactory factory;

    static {
        try {
            Configuration configuration = new Configuration()
                    .configure("hibernate-h2.cfg.xml") // собственная файловая БД, H2
                    //.configure("hibernate-mariadb.cfg.xml") // общая БД, MariaDB
                    //.configure("hibernate-postgres.cfg.xml") // собственная файловая БД
                    .addAnnotatedClass(ru.progwards.tasktracker.service.vo.AccessRule.class)
                    .addAnnotatedClass(ru.progwards.tasktracker.service.vo.AttachmentContent.class)
                    .addAnnotatedClass(ru.progwards.tasktracker.service.vo.Project.class)
                    .addAnnotatedClass(ru.progwards.tasktracker.service.vo.RelatedTask.class)
                    .addAnnotatedClass(ru.progwards.tasktracker.service.vo.RelationType.class)
                    .addAnnotatedClass(ru.progwards.tasktracker.service.vo.Task.class)
                    .addAnnotatedClass(ru.progwards.tasktracker.service.vo.TaskAttachment.class)
                    .addAnnotatedClass(ru.progwards.tasktracker.service.vo.TaskNote.class)
                    .addAnnotatedClass(ru.progwards.tasktracker.service.vo.TaskPriority.class)
                    .addAnnotatedClass(ru.progwards.tasktracker.service.vo.TaskType.class)
                    .addAnnotatedClass(ru.progwards.tasktracker.service.vo.User.class)
                    .addAnnotatedClass(ru.progwards.tasktracker.service.vo.UserRole.class)
                    .addAnnotatedClass(ru.progwards.tasktracker.service.vo.WorkFlow.class)
                    .addAnnotatedClass(ru.progwards.tasktracker.service.vo.WorkFlowAction.class)
                    .addAnnotatedClass(ru.progwards.tasktracker.service.vo.WorkFlowStatus.class)
                    .addAnnotatedClass(ru.progwards.tasktracker.service.vo.WorkLog.class);
            factory = configuration.buildSessionFactory();
        }
        catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }

    }

    public static SessionFactory getSessionFactory() {
        return factory;
    }

    /**
     * Clear cache and close connection
     */
    public static void shutdown() {
        if (!factory.isClosed())
            factory.close();
    }

}