package ru.progwards.tasktracker.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Service;
import ru.progwards.tasktracker.model.TaskAttachmentContent;

//@Service
public class HibernateUtil {

    private static final SessionFactory factory;

    static {
        try {
            Configuration configuration = new Configuration()
//                    .configure("hibernate-h2.cfg.xml") // собственная файловая БД, H2
                    //.configure("hibernate-mariadb.cfg.xml") // общая БД, MariaDB
                    .configure("hibernate-postgres.cfg.xml") // собственная файловая БД
                    .addAnnotatedClass(ru.progwards.tasktracker.model.AccessRule.class)
                    .addAnnotatedClass(TaskAttachmentContent.class)
                    .addAnnotatedClass(ru.progwards.tasktracker.model.Project.class)
                    .addAnnotatedClass(ru.progwards.tasktracker.model.RelatedTask.class)
                    .addAnnotatedClass(ru.progwards.tasktracker.model.RelationType.class)
                    .addAnnotatedClass(ru.progwards.tasktracker.model.Task.class)
                    .addAnnotatedClass(ru.progwards.tasktracker.model.TaskAttachment.class)
                    .addAnnotatedClass(ru.progwards.tasktracker.model.TaskNote.class)
                    .addAnnotatedClass(ru.progwards.tasktracker.model.TaskPriority.class)
                    .addAnnotatedClass(ru.progwards.tasktracker.model.TaskType.class)
                    .addAnnotatedClass(ru.progwards.tasktracker.model.User.class)
                    .addAnnotatedClass(ru.progwards.tasktracker.model.UserRole.class)
                    .addAnnotatedClass(ru.progwards.tasktracker.model.WorkFlow.class)
                    .addAnnotatedClass(ru.progwards.tasktracker.model.WorkFlowAction.class)
                    .addAnnotatedClass(ru.progwards.tasktracker.model.WorkFlowStatus.class)
                    .addAnnotatedClass(ru.progwards.tasktracker.model.WorkLog.class);
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