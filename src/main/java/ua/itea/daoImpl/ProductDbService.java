package ua.itea.daoImpl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.itea.dao.ProductDao;
import ua.itea.model.Product;
import ua.itea.utils.DbConnector;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ProductDbService implements ProductDao {
    public static final Logger LOG = Logger.getLogger(ProductDbService.class.getName());
    private ObjectMapper objectMapper;

    private static final String url = "http://localhost:8081/products/";


    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Product> getProducts() {
        List<Product> result = new ArrayList();
        LOG.info("Starting query");
        try {
            URL urlObj = new URL(url );
            HttpURLConnection urlConnection = (HttpURLConnection) urlObj.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            result = objectMapper.readValue(bufferedInputStream, new TypeReference<List<Product>>() {});
            LOG.info("Query success");
            urlConnection.disconnect();
        } catch (IOException exception) {
            LOG.log(Level.SEVERE,"DB error "+exception.getMessage(),exception);
            exception.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Product> getProductsByCategoryId(String categoryId) {
        String filePath = "?category=";
        List<Product> result = new ArrayList();
        LOG.info("Starting query");
        try {
            URL urlObj = new URL(url + filePath + categoryId);
            HttpURLConnection urlConnection = (HttpURLConnection) urlObj.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            result = objectMapper.readValue(bufferedInputStream, new TypeReference<List<Product>>() {});
//            TypeReference<Map<Product,Integer>> tr=new TypeReference<Map<Product, Integer>>() {};
//            objectMapper.readValue(bufferedInputStream, tr);
            LOG.info("Query success");
            urlConnection.disconnect();
        } catch (IOException exception) {
            LOG.log(Level.SEVERE,"DB error "+exception.getMessage(),exception);
            exception.printStackTrace();
        }
        return result;
    }

    @Override
    public Product getProductById(String id) {
        String filePath = "?id=";
        Product result = null;
        LOG.info("Starting query");
        try {
            URL urlObj = new URL(url + filePath + id);
            HttpURLConnection urlConnection = (HttpURLConnection) urlObj.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            result = objectMapper.reader().readValue(bufferedInputStream, Product.class);
            LOG.info("Query success");
            urlConnection.disconnect();
        } catch (IOException exception) {
            LOG.log(Level.SEVERE,"DB error "+exception.getMessage(),exception);
            exception.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(new ProductDbService().getProductById("1"));

    }
}
