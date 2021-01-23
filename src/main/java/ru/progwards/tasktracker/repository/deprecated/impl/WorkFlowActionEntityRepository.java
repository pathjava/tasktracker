//package ru.progwards.tasktracker.repository.deprecated.impl;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import ru.progwards.tasktracker.repository.deprecated.Repository;
//import ru.progwards.tasktracker.repository.deprecated.RepositoryByParentId;
//import ru.progwards.tasktracker.repository.deprecated.entity.WorkFlowActionEntity;
//
//import java.io.*;
//import java.util.*;
//import java.util.concurrent.locks.Lock;
//import java.util.concurrent.locks.ReadWriteLock;
//import java.util.concurrent.locks.ReentrantLock;
//import java.util.concurrent.locks.ReentrantReadWriteLock;
//
///**
// * Хранение объектов в формате JSON в файле построчно
// *
// * @author Gregory Lobkov
// */
//@org.springframework.stereotype.Repository
//public class WorkFlowActionEntityRepository implements Repository<Long, WorkFlowActionEntity>, RepositoryByParentId<Long, WorkFlowActionEntity> {
//
//    /**
//     * Имя файла-хранилища данных
//     */
//    private String fileName = "src/main/resources/data/WorkFlowAction.json";
//
//    public WorkFlowActionEntityRepository() throws IOException {
//        file = new File(fileName);
//        if (!file.exists())
//            file.createNewFile();
//    }
//
//    /**
//     * Указатель на актуальный файл
//     */
//    private File file;
//
//    /**
//     * Блокировщик всех записей в файл
//     */
//    private Lock lockWrite = new ReentrantLock();
//
//    /**
//     * Блокировщик чтения, используется на последнем этапе записи в файл
//     */
//    private ReadWriteLock lockRead = new ReentrantReadWriteLock();
//
//    /**
//     * JSON сериализатор - многопоточный ли?
//     */
//    private Gson json = new GsonBuilder().create();
//
//
//    /**
//     * Сохранение нового объекта
//     * без проверки на существование идентификатора - быстрое добавление в конец хранилища
//     *
//     * @param entity добавляемый объект
//     */
//    @Override
//    public void create(WorkFlowActionEntity entity) {
//        if (entity.getId() == null)
//            entity.setId(new Random().nextLong());
//        String newLine = json.toJson(entity);
//
//        lockWrite.lock();
//        lockRead.writeLock().lock();
//        // лочим чтение и добавляем новую строчку в конец файла
//        try (FileWriter fileWriter = new FileWriter(file, true);
//             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
//             PrintWriter printWriter = new PrintWriter(bufferedWriter)) {
//
//            printWriter.println(newLine);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            lockRead.writeLock().unlock();
//            lockWrite.unlock();
//        }
//    }
//
//    /**
//     * Обновление объекта в хранилище
//     *
//     * Идентификатор объекта уже должен быть в хранилище
//     *
//     * @param entity обновленный объект
//     * @throws NoSuchElementException если не смог найти объект в хранилище
//     */
//    @Override
//    public void update(WorkFlowActionEntity entity) {
//        update(entity, false, true);
//    }
//
//    /**
//     * Обновление объекта в хранилище (для внутреннего использования)
//     * Параметрами можно определить различное поведение
//     *
//     * @param entity объект для обновления
//     * @param addIfNotFound {@code true} добавляем в случае отсутствия идентификатора,
//     *                      {@code false} оставляем хранилище без изменений, если не нашли объект изменения в хранилище
//     * @param throwIfNotFound {@code true} если нужно сгенерировать ошибку при осутствии объекта в храниилище
//     *                                    - метод не добавит объект даже если {@code addIfNotFound = true}
//     * @throws NoSuchElementException если не смог найти объект в хранилище (когда {@code throwIfNotFound = true})
//     */
//    private void update(WorkFlowActionEntity entity, boolean addIfNotFound, boolean throwIfNotFound) {
//        lockWrite.lock();
//        try {
//            WorkFlowActionEntity instance = new WorkFlowActionEntity();
//            Long id = entity.getId();
//            String newLine = json.toJson(entity);
//            boolean found = false;
//            // предпологается создать клон основного файла, а только потом лочить чтение, чтобы переименовать файлы
//            File file2 = new File(fileName + "_tmp");
//            try (FileWriter fileWriter = new FileWriter(file2);
//                 BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
//                 PrintWriter printWriter = new PrintWriter(bufferedWriter);
//                 FileReader fileReader = new FileReader(file);
//                 BufferedReader bufferedReader = new BufferedReader(fileReader)) {
//                //читаем основной файл и тут же заполняем временный
//                String line = bufferedReader.readLine();
//                while (line != null) {
//                    if (!found) {
//                        instance = json.fromJson(line, WorkFlowActionEntity.class);
//                        if (id.compareTo(instance.getId())==0) {
//                            line = newLine;
//                            found = true;
//                        }
//                    }
//                    printWriter.println(line);
//                    line = bufferedReader.readLine();
//                }
//                //если такой строчки не было, добавляем в конец
//                if (!found && addIfNotFound) {
//                    printWriter.println(newLine);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            if (!found) {
//                if (throwIfNotFound) {
//                    file2.delete();
//                    throw new NoSuchElementException("Cannot find id='" + id + "'");
//                }
//                if (!addIfNotFound) {
//                    file2.delete();
//                    return;
//                }
//            }
//
//            // лочим чтение и подменяем на обновленный файл
//            replaceFile(file, file2);
//
//        } finally {
//            lockWrite.unlock();
//        }
//    }
//
//    /**
//     * Удаление объекта из хранилища
//     *
//     * @param id идентификатор удаляемого объекта
//     * @throws NoSuchElementException если не смог найти объект в хранилище
//     */
//    @Override
//    public void delete(Long id) {
//        delete(id, true);
//    }
//
//
//    /**
//     * Удаление объекта из хранилища (для внутреннего использования)
//     * Параметрами можно определить различное поведение
//     *
//     * @param id идентификатор удаляемого объекта
//     * @param throwIfNotFound {@code true} если нужно сгенерировать ошибку при осутствии объекта в храниилище
//     * @throws NoSuchElementException если не смог найти объект в хранилище (когда {@code throwIfNotFound = true})
//     */
//    public void delete(Long id, boolean throwIfNotFound) {
//        lockWrite.lock();
//        try {
//            WorkFlowActionEntity instance = new WorkFlowActionEntity();
//            boolean found = false;
//            // предпологается создать клон основного файла, а только потом лочить чтение, чтобы переименовать файлы
//            File file2 = new File(fileName + "_tmp");
//            try (FileWriter fileWriter = new FileWriter(file2);
//                 BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
//                 PrintWriter printWriter = new PrintWriter(bufferedWriter);
//                 FileReader fileReader = new FileReader(file);
//                 BufferedReader bufferedReader = new BufferedReader(fileReader)) {
//                String line = bufferedReader.readLine();
//                while (line != null) {
//                    if (found) {
//                        printWriter.println(line);
//                    } else {
//                        instance = json.fromJson(line, WorkFlowActionEntity.class);
//                        if (id.compareTo(instance.getId())==0) {
//                            found = true;
//                        } else {
//                            printWriter.println(line);
//                        }
//                    }
//                    line = bufferedReader.readLine();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            if (found) {
//                // лочим чтение и подменяем на обновленный файл
//                replaceFile(file, file2);
//            } else {
//                file2.delete();
//                if (throwIfNotFound)
//                    throw new NoSuchElementException("Cannot find id='" + id + "'");
//            }
//        } finally {
//            lockWrite.unlock();
//        }
//    }
//
//    /**
//     * Остановить чтение и подменить один файл другим
//     *
//     * @param original какой файл заменяем
//     * @param newOne новый файл хранилища
//     */
//    private void replaceFile(File original, File newOne) {
//        lockRead.writeLock().lock();
//        try {
//            // лочим чтение и подменяем на обновленный файл
//            if (original.delete()) {
//                if (!newOne.renameTo(original)) {
//                    newOne.delete();
//                    throw new RuntimeException("File is broken. Cannot rename temp file to original one.");
//                }
//            } else {
//                newOne.delete();
//                throw new RuntimeException("Cannot replace original file.");
//            }
//        } finally {
//            lockRead.writeLock().unlock();
//        }
//    }
//
//    /**
//     * Получить список всех записей из хранилища
//     *
//     * @return список всех POJO-объектов из хранилища
//     */
//    public Collection<WorkFlowActionEntity> get() {
//        List<WorkFlowActionEntity> result = new ArrayList<>();
//        lockRead.readLock().lock();
//        try {
//            try (FileReader fileReader = new FileReader(file);
//                 BufferedReader bufferedReader = new BufferedReader(fileReader)) {
//                String line = bufferedReader.readLine();
//                while (line != null) {
//                    WorkFlowActionEntity instance = new WorkFlowActionEntity();
//                    instance = json.fromJson(line, WorkFlowActionEntity.class);
//                    result.add(instance);
//                    line = bufferedReader.readLine();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } finally {
//            lockRead.readLock().unlock();
//        }
//        return result;
//    }
//
//    /**
//     * Получить объект из хранилища
//     *
//     * @param id идентификатор POJO-объекта
//     * @return POJO-объект
//     */
//    @Override
//    public WorkFlowActionEntity get(Long id) {
//        lockRead.readLock().lock();
//        try {
//            WorkFlowActionEntity instance = new WorkFlowActionEntity();
//            try (FileReader fileReader = new FileReader(file);
//                 BufferedReader bufferedReader = new BufferedReader(fileReader)) {
//                String line = bufferedReader.readLine();
//                while (line != null) {
//                    instance = json.fromJson(line, WorkFlowActionEntity.class);
//                    if (id.compareTo(instance.getId())==0) return instance;
//                    line = bufferedReader.readLine();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } finally {
//            lockRead.readLock().unlock();
//        }
//        throw new NoSuchElementException("Cannot find id='" + id + "'");
//    }
//
//    /**
//     * Получить список сущностей по идентификатору родителя
//     *
//     * @param parentId идентификатор родителя
//     * @return спиок сущностей
//     */
//    @Override
//    public Collection<WorkFlowActionEntity> getByParentId(Long parentId) {
//        List<WorkFlowActionEntity> result = new ArrayList<>();
//        lockRead.readLock().lock();
//        try {
//            try (FileReader fileReader = new FileReader(file);
//                 BufferedReader bufferedReader = new BufferedReader(fileReader)) {
//                String line = bufferedReader.readLine();
//                while (line != null) {
//                    WorkFlowActionEntity instance = new WorkFlowActionEntity();
//                    instance = json.fromJson(line, WorkFlowActionEntity.class);
//                    if(instance.getParentStatus_id().equals(parentId)) {
//                        result.add(instance);
//                    }
//                    line = bufferedReader.readLine();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } finally {
//            lockRead.readLock().unlock();
//        }
//        return result;
//    }
//
//}