package ru.progwards.tasktracker.service.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.util.List;

/**
 * @author Aleksandr Sidelnikov
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @SequenceGenerator(name = "users_seq", sequenceName = "users_seq", allocationSize = 1)
    @GeneratedValue(generator = "users_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

//    private List<UserRole> roles;

    @Lazy
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    List<Project> projectsOwner;

    @Lazy
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    List<Task> tasksAuthor;

    @Lazy
    @OneToMany(mappedBy = "executor", fetch = FetchType.LAZY)
    List<Task> tasksExecutor;

    @Lazy
    @OneToMany(mappedBy = "worker", fetch = FetchType.LAZY)
    List<WorkLog> workLogs;

    @Lazy
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    List<TaskNote> taskNotesAuthor;

    @Lazy
    @OneToMany(mappedBy = "updater", fetch = FetchType.LAZY)
    List<TaskNote> taskNotesUpdater;


}