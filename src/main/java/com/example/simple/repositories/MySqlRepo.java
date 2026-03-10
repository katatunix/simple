package com.example.simple.repositories;

import com.example.simple.services.Repo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Repository
public class MySqlRepo implements Repo {
    private final String host;
    private final int port;
    private final String username;
    private final String password;
    private final String database;

    public MySqlRepo(
            @Value("${mysql.host}") String host,
            @Value("${mysql.port}")int port,
            @Value("${mysql.username}") String username,
            @Value("${mysql.password}") String password,
            @Value("${mysql.database}") String database) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.database = database;
    }

    private Connection getConnection() {
        var url = String.format("jdbc:mysql://%s:%d/%s", host, port, database);
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean checkLogin(String username, String password) {
        var sql = "SELECT username FROM users WHERE username = ? AND password = ?";
        try (
            var conn = getConnection();
            var ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (var rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
