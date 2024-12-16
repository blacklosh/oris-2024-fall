package ru.itis.config;

import com.mongodb.MongoClient;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.itis.mapper.ChatMapper;
import ru.itis.mapper.MessageMapper;
import ru.itis.mapper.UserMapper;
import ru.itis.mapper.impl.ChatMapperImpl;
import ru.itis.mapper.impl.MessageMapperImpl;
import ru.itis.mapper.impl.UserMapperImpl;
import ru.itis.repository.ChatRepository;
import ru.itis.repository.MongoFileRepository;
import ru.itis.repository.UserRepository;
import ru.itis.repository.impl.ChatRepositoryImpl;
import ru.itis.repository.impl.MongoFileRepositoryImpl;
import ru.itis.repository.impl.UserRepositoryImpl;
import ru.itis.service.ChatsService;
import ru.itis.service.FileService;
import ru.itis.service.UserService;
import ru.itis.service.impl.ChatsServiceImpl;
import ru.itis.service.impl.FileMongoServiceImpl;
import ru.itis.service.impl.FileServiceImpl;
import ru.itis.service.impl.UserServiceImpl;
import ru.itis.servlet.ChatWebSocketServlet;
import ru.itis.util.PropertyReader;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

@Slf4j
@WebListener
public class MainServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        DataSource dataSource = dataSource();
        context.setAttribute("dataSource", dataSource);

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        context.setAttribute("jdbcTemplate", jdbcTemplate);

        UserMapper userMapper = new UserMapperImpl();
        context.setAttribute("userMapper", userMapper);

        UserRepository userRepository = new UserRepositoryImpl(jdbcTemplate);
        context.setAttribute("userRepository", userRepository);

        UserService userService = new UserServiceImpl(userRepository, userMapper);
        context.setAttribute("userService", userService);

        List<String> PROTECTED_URIS = List.of(PropertyReader.getProperty("PROTECTED_URIS").split(","));
        context.setAttribute("PROTECTED_URIS", PROTECTED_URIS);
        List<String> NOTAUTH_URIS = List.of(PropertyReader.getProperty("NOTAUTH_URIS").split(","));
        context.setAttribute("NOTAUTH_URIS", NOTAUTH_URIS);

        String PROTECTED_REDIRECT = PropertyReader.getProperty("PROTECTED_REDIRECT");
        context.setAttribute("PROTECTED_REDIRECT", PROTECTED_REDIRECT);
        String NOTAUTH_REDIRECT = PropertyReader.getProperty("NOTAUTH_REDIRECT");
        context.setAttribute("NOTAUTH_REDIRECT", NOTAUTH_REDIRECT);

        String AUTHORIZATION = PropertyReader.getProperty("AUTHORIZATION");
        context.setAttribute("AUTHORIZATION", AUTHORIZATION);

        ChatRepository chatRepository = new ChatRepositoryImpl(jdbcTemplate);
        ChatWebSocketServlet.setChatRepository(chatRepository);
        ChatMapper chatMapper = new ChatMapperImpl();
        MessageMapper messageMapper = new MessageMapperImpl(userRepository);

        ChatsService chatsService = new ChatsServiceImpl(chatRepository,
                chatMapper,
                userMapper,
                messageMapper);
        context.setAttribute("chatsService", chatsService);

        String filePath = PropertyReader.getProperty("FILE_PATH");
        String defaultImageName = PropertyReader.getProperty("DEFAULT_AVATAR_NAME");

        MongoClient mongoClient = mongoClient();
        context.setAttribute("mongoClient", mongoClient);

        MongoFileRepository mongoFileRepository = new MongoFileRepositoryImpl(mongoClient);

        //FileService fileService = new FileServiceImpl(filePath, defaultImageName);
        FileService fileService = new FileMongoServiceImpl(mongoFileRepository, UUID.fromString(defaultImageName));
        context.setAttribute("fileService", fileService);


    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("-=-=-=-=-=-=-=-=- CONTEXT DESTROYED -==-=-=-=-=-=-=-=-=");
    }

    private DataSource dataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(PropertyReader.getProperty("DB_URL"));
        dataSource.setUser(PropertyReader.getProperty("DB_USER"));
        dataSource.setPassword(PropertyReader.getProperty("DB_PASSWORD"));
        return dataSource;
    }

    private MongoClient mongoClient() {
        String host = PropertyReader.getProperty("MONGO_HOST");
        int port = Integer.parseInt(PropertyReader.getProperty("MONGO_PORT"));
        return new MongoClient(host, port);
    }
}
