package ru.progwards.tasktracker.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CmdExec {
    public void exec1() {
        try {
            String url = "jdbc:postgresql://localhost:5432/tt";
            String username = "postgres";
            String password = "m8v-nfZ-cDE-ZNs";
            Class.forName("org.postgresql.Driver").getDeclaredConstructor().newInstance();
            String sqlCommand = "\n" + "ALTER TABLE workflow DROP CONSTRAINT FK4us01hafv638tk2ltggua4nl9;" +
                    "INSERT INTO task_attachment_content (id, data) select 1000000, 'BLOB CONTENT'  where not exists (select id from task_attachment_content where id = 1000000);\n" +
                    "INSERT INTO task_attachment_content (id, data) select 1000001, 'BLOB CONTENT'  where not exists (select id from task_attachment_content where id = 1000001);\n" +
                    "INSERT INTO task_attachment_content (id, data) select 1000002, 'BLOB CONTENT'  where not exists (select id from task_attachment_content where id = 1000002);\n" +
                    "\n" +
                    "INSERT INTO users (id, email, name, password) select 1000000, 'user1@mail.com', 'username1', 'password1'  where not exists (select id from users where id = 1000000);\n" +
                    "INSERT INTO users (id, email, name, password) select 1000001, 'user2@mail.com', 'username2', 'password2'  where not exists (select id from users where id = 1000001);\n" +
                    "INSERT INTO users (id, email, name, password) select 1000002, 'user3@mail.com', 'username3', 'password3'  where not exists (select id from users where id = 1000002);\n" +
                    "\n" +
                    "INSERT INTO task_priority (id, name, value) select 1000000, 'name1', 2  where not exists (select id from task_priority where id = 1000000);\n" +
                    "INSERT INTO task_priority (id, name, value) select 1000001, 'name2', 2  where not exists (select id from task_priority where id = 1000001);\n" +
                    "INSERT INTO task_priority (id, name, value) select 1000002, 'name1', 2  where not exists (select id from task_priority where id = 1000002);\n" +
                    "\n" +
                    "INSERT INTO project (id, created, description, last_task_code, name, prefix, owner_id) select 1000000, CURRENT_TIMESTAMP, 'description1', 1, 'name', 'prefix', 1000000  where not exists (select id from project where id = 1000000);\n" +
                    "INSERT INTO project (id, created, description, last_task_code, name, prefix, owner_id) select 1000001, CURRENT_TIMESTAMP, 'description2', 1, 'name2', 'prefix', 1000001  where not exists (select id from project where id = 1000001);\n" +
                    "INSERT INTO project (id, created, description, last_task_code, name, prefix, owner_id) select 1000002, CURRENT_TIMESTAMP, 'description3', 1, 'name3', 'prefix', 1000002  where not exists (select id from project where id = 1000002);\n" +
                    "\n" +
                    "INSERT INTO user_role (id, name, system_role) select 1000000, 'name1', 1000000  where not exists (select id from user_role where id = 1000000);\n" +
                    "INSERT INTO user_role (id, name, system_role) select 1000001, 'name2', 1000001  where not exists (select id from user_role where id = 1000001);\n" +
                    "INSERT INTO user_role (id, name, system_role) select 1000002, 'name3', 1000002  where not exists (select id from user_role where id = 1000002);\n" +
                    "\n" +
                    "INSERT INTO user_role_user (user_role_id, user_id) select 1000000, 1000000  where not exists (select user_role_id from user_role_user where user_role_id = 1000000);\n" +
                    "INSERT INTO user_role_user (user_role_id, user_id) select 1000001, 1000001  where not exists (select user_role_id from user_role_user where user_role_id = 1000001);\n" +
                    "INSERT INTO user_role_user (user_role_id, user_id) select 1000002, 1000002  where not exists (select user_role_id from user_role_user where user_role_id = 1000002);\n" +
                    "\n" +
                    "INSERT INTO access_rule (id, access_type, object_id, object_name, property_name, user_role) select 1000000, 'MODIFY', 1000000, 'name1', 'text', 1000000  where not exists (select id from access_rule where id = 1000000);\n" +
                    "INSERT INTO access_rule (id, access_type, object_id, object_name, property_name, user_role) select 1000001, 'MODIFY', 1000001, 'name2', 'text2', 1000001  where not exists (select id from access_rule where id = 1000001);\n" +
                    "INSERT INTO access_rule (id, access_type, object_id, object_name, property_name, user_role) select 1000002, 'MODIFY', 1000002, 'name3', 'text3', 1000002  where not exists (select id from access_rule where id = 1000002);\n" +
                    "\n" +
                    "INSERT INTO relation_type (id, name, counter_id) select 1000000, 'name1', 1000000  where not exists (select id from relation_type where id = 1000000);\n" +
                    "INSERT INTO relation_type (id, name, counter_id) select 1000001, 'name2', 1000001  where not exists (select id from relation_type where id = 1000001);\n" +
                    "INSERT INTO relation_type (id, name, counter_id) select 1000002, 'name2', 1000002  where not exists (select id from relation_type where id = 1000002);\n" +
                    "\n" +
                    "INSERT INTO workflow (id, name, pattern, start_status_id) select 1000000, 'nameWorkFlow1', true, 1000000  where not exists (select id from workflow where id = 1000000);\n" +
                    "INSERT INTO workflow (id, name, pattern, start_status_id) select 1000001, 'nameWorkFlow2', true, 1000001  where not exists (select id from workflow where id = 1000001);\n" +
                    "INSERT INTO workflow (id, name, pattern, start_status_id) select 1000002, 'nameWorkFlow3', false, 1000002  where not exists (select id from workflow where id = 1000002);\n" +
                    "\n" +
                    "INSERT INTO workflow_status (id, always_allow, name, state, workflow_id) select 1000000, true, 'nameWFStatus1', 1, 1000000  where not exists (select id from workflow_status where id = 1000000);\n" +
                    "INSERT INTO workflow_status (id, always_allow, name, state, workflow_id) select 1000001, true, 'nameWFStatus2', 1, 1000001  where not exists (select id from workflow_status where id = 1000001);\n" +
                    "INSERT INTO workflow_status (id, always_allow, name, state, workflow_id) select 1000002, true, 'nameWFStatus3', 1, 1000002  where not exists (select id from workflow_status where id = 1000002);\n" +
                    "\n" +
                    "INSERT INTO workflow_action (id, name, next_status_id, parent_status_id) select 1000000, 'nameWFAction1', 1000000, 1000000  where not exists (select id from workflow_action where id = 1000000);\n" +
                    "INSERT INTO workflow_action (id, name, next_status_id, parent_status_id) select 1000001, 'nameWFAction2', 1000001, 1000001  where not exists (select id from workflow_action where id = 1000001);\n" +
                    "INSERT INTO workflow_action (id, name, next_status_id, parent_status_id) select 1000002, 'nameWFAction3', 1000002, 1000002  where not exists (select id from workflow_action where id = 1000002);\n" +
                    "\n" +
                    "INSERT INTO task_type (id, name, project_id, work_flow_id) select 1000000, 'name1', 1000000, 1000000  where not exists (select id from task_type where id = 1000000);\n" +
                    "INSERT INTO task_type (id, name, project_id, work_flow_id) select 1000001, 'name2', 1000001, 1000001  where not exists (select id from task_type where id = 1000001);\n" +
                    "INSERT INTO task_type (id, name, project_id, work_flow_id) select 1000002, 'name3', 1000002, 1000002  where not exists (select id from task_type where id = 1000002);\n" +
                    "\n" +
                    "INSERT INTO task (id, code, created, description, estimation, name, timeLeft, timeSpent, updated, author_id, executor_id, priority_id, project_id, status_id, type_id) select 1000000, 'code1', CURRENT_TIMESTAMP, 'description1', 1, 'name1', 1, 1, CURRENT_TIMESTAMP, 1000000, 1000000, 1000000, 1000000, 1000000, 1000000  where not exists (select id from task where id = 1000000);\n" +
                    "INSERT INTO task (id, code, created, description, estimation, name, timeLeft, timeSpent, updated, author_id, executor_id, priority_id, project_id, status_id, type_id) select 1000001, 'code2', CURRENT_TIMESTAMP, 'description2', 1, 'name2', 1, 1, CURRENT_TIMESTAMP, 1000001, 1000001, 1000001, 1000001, 1000001, 1000001  where not exists (select id from task where id = 1000001);\n" +
                    "INSERT INTO task (id, code, created, description, estimation, name, timeLeft, timeSpent, updated, author_id, executor_id, priority_id, project_id, status_id, type_id) select 1000002, 'code3', CURRENT_TIMESTAMP, 'description3', 1, 'name3', 1, 1, CURRENT_TIMESTAMP, 1000002, 1000002, 1000002, 1000002, 1000002, 1000002  where not exists (select id from task where id = 1000002);\n" +
                    "\n" +
                    "INSERT INTO task_attachment (id, created, extension, name, size, content_id, task_id) select 1000000, CURRENT_TIMESTAMP, 'extension1', 'name1', 2048, 1000000, 1000000  where not exists (select id from task_attachment where id = 1000000);\n" +
                    "INSERT INTO task_attachment (id, created, extension, name, size, content_id, task_id) select 1000001, CURRENT_TIMESTAMP, 'extension2', 'name2', 2048, 1000001, 1000001  where not exists (select id from task_attachment where id = 1000001);\n" +
                    "INSERT INTO task_attachment (id, created, extension, name, size, content_id, task_id) select 1000002, CURRENT_TIMESTAMP, 'extension3', 'name3', 2048, 1000002, 1000002  where not exists (select id from task_attachment where id = 1000002);\n" +
                    "\n" +
                    "INSERT INTO related_task (id, attached_task_id, current_task_id, counter_type_id) select 1000000, 1000000, 1000000, 1000000  where not exists (select id from related_task where id = 1000000);\n" +
                    "INSERT INTO related_task (id, attached_task_id, current_task_id, counter_type_id) select 1000001, 1000001, 1000001, 1000001  where not exists (select id from related_task where id = 1000001);\n" +
                    "INSERT INTO related_task (id, attached_task_id, current_task_id, counter_type_id) select 1000002, 1000002, 1000002, 1000002  where not exists (select id from related_task where id = 1000002);\n" +
                    "\n" +
                    "INSERT INTO task_note (id, comment, created, updated, author_id, task_id, updater_id) select 1000000, 'comment1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1000000, 1000000, 1000000  where not exists (select id from task_note where id = 1000000);\n" +
                    "INSERT INTO task_note (id, comment, created, updated, author_id, task_id, updater_id) select 1000001, 'comment2', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1000001, 1000001, 1000001  where not exists (select id from task_note where id = 1000001);\n" +
                    "INSERT INTO task_note (id, comment, created, updated, author_id, task_id, updater_id) select 1000002, 'comment3', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1000002, 1000002, 1000002  where not exists (select id from task_note where id = 1000002);\n" +
                    "\n" +
                    "INSERT INTO work_log (id, description, estimateChange, estimateValue, spent, start, task_id, user_id) select 1000000, 'description', 3, 2048, 2048, CURRENT_TIMESTAMP, 1000000, 1000000  where not exists (select id from work_log where id = 1000000);\n" +
                    "INSERT INTO work_log (id, description, estimateChange, estimateValue, spent, start, task_id, user_id) select 1000001, 'description2', 2, 2048, 2048, CURRENT_TIMESTAMP, 1000001, 1000001  where not exists (select id from work_log where id = 1000001);\n" +
                    "INSERT INTO work_log (id, description, estimateChange, estimateValue, spent, start, task_id, user_id) select 1000002, 'description3', 4, 2048, 2048, CURRENT_TIMESTAMP, 1000002, 1000002  where not exists (select id from work_log where id = 1000002);\n" +
                    "\n" +
                    "alter table workflow \n" +
                    "       add constraint FK4us01hafv638tk2ltggua4nl9 \n" +
                    "       foreign key (start_status_id) \n" +
                    "       references workflow_status;" +
                    "\n" +
                    "\n" +
                    "\n";
            try (Connection conn = DriverManager.getConnection(url, username, password)){

                Statement statement = conn.createStatement();
                // вставка данных
                statement.executeUpdate(sqlCommand);

                System.out.println("Data has been insert!");
            }
        }
        catch (Exception err) {
            err.printStackTrace();
        }
    }
}
