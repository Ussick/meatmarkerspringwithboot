package ua.itea.daoImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.itea.dao.UserDao;
import ua.itea.dto.UserDto;
import ua.itea.model.User;
import ua.itea.utils.DbConnector;
import ua.itea.utils.UserConverter;

import java.io.*;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;


@Service
public class UserDbSevice implements UserDao {
    public static final Logger LOG = Logger.getLogger(UserDbSevice.class.getName());
    public static final String INSERT_USER =
            "INSERT INTO users (login, name, password, gender, region, comment) VALUES(?,?,?,?,?,?)";
    private DbConnector db;

    private ObjectMapper objectMapper;
    private UserConverter converter;

    private static final String url = "http://localhost:8081/";

    public UserDbSevice() {
        db = DbConnector.getInstance();
        converter=new UserConverter();
    }


    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String checkLogin(String login, String password) {
        String name = null;
        String request = "login=" + login + "&password=" + getSaltedHashedPassword(password);

        LOG.info("Starting query");
        try {
            URL urlObj = new URL(url + "login/");
            HttpURLConnection urlConnection = (HttpURLConnection) urlObj.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
//            urlConnection.setUseCaches(false);
//            urlConnection.setRequestProperty( "Content-Type", "application/json" );
//            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestMethod("POST");
            OutputStream os = urlConnection.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
            osw.write(request);
            osw.flush();
            osw.close();

            InputStream inputStream = urlConnection.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            name = objectMapper.reader().readValue(bufferedInputStream, User.class).getName();
            LOG.info("Query success");
            urlConnection.disconnect();
        } catch (IOException exception) {
            LOG.log(Level.SEVERE, "DB error " + exception.getMessage(), exception);
            exception.printStackTrace();
        }

        return name;
    }

    public void addUser(User user) {
        LOG.info("Starting query");
        JSONObject msg=new JSONObject(new Gson().toJson(converter.convertUser(user)));
        try {
            URL urlObj = new URL(url + "registration");
            HttpURLConnection urlConnection = (HttpURLConnection) urlObj.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setUseCaches(false);
            urlConnection.setRequestProperty("Content-Type", "application/json");
//            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestMethod("POST");
            OutputStream os = urlConnection.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
            osw.write(msg.toString());
            osw.flush();
            osw.close();

            InputStream inputStream = urlConnection.getInputStream();
//            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
//            objectMapper.reader().readValue(bufferedInputStream);
            LOG.info("Query success");
            urlConnection.disconnect();
        } catch (IOException exception) {
            LOG.log(Level.SEVERE, "DB error " + exception.getMessage(), exception);
            exception.printStackTrace();
        }
    }

    private String getSaltedHashedPassword(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String passphrase = "myspecialspice" + password;
        md.update(passphrase.getBytes(StandardCharsets.UTF_8));
        return String.format("%064x", new BigInteger(1, md.digest()));
    }
}
