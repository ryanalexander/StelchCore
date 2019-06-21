package com.stelch.games2.core.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class SQL
{
    private Connection connection;

    public SQL(String host, int port, String username, String password, String database)
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + (port > 0 ? port : 3306) + "/" + database+"?autoReconnect=true&useSSL=false", username, password);
        }
        catch (Exception e)
        {
            System.out.println("Failed to connect to SQL Server [IP: " + host + ":" + port + ";Database:" + database + "]");
        }
    }

    public void query (String query, boolean t){
        if(!t){return;}
        if (this.connection == null) { return; }
        try
        {
            this.connection.createStatement().execute(query);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Failed to execute a SQL Query, assumed server is offline.");
        }
    }

    public ResultSet query(String query)
    {
        if (this.connection == null) {
            return null;
        }
        try
        {
            return this.connection.createStatement().executeQuery(query);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Failed to execute a SQL Query, assumed server is offline.");
        }
        return null;
    }

    public void close()
    {
        try
        {
            this.connection.close();
        }
        catch (Exception localException) {}
    }
}