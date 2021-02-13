package ru.progwards.tasktracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author Aleksandr Sidelnikov
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(of = {"id"})
@Table(name = "users")
public class User {

    @Id
    @SequenceGenerator(name = "users_seq", sequenceName = "users_seq", allocationSize = 1)
    @GeneratedValue(generator = "users_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    @Column(unique = true)
    private String email;

    @NotBlank
    private String password;

    @ManyToMany(mappedBy = "users")
    private List<UserRole> roles;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<Project> projectsOwner;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Task> tasksAuthor;

    @OneToMany(mappedBy = "executor", fetch = FetchType.LAZY)
    private List<Task> tasksExecutor;

    @OneToMany(mappedBy = "worker", fetch = FetchType.LAZY)
    private List<WorkLog> workLogs;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<TaskNote> taskNotesAuthor;

    @OneToMany(mappedBy = "updater", fetch = FetchType.LAZY)
    private List<TaskNote> taskNotesUpdater;


}