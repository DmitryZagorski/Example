package com;

import com.client.Client;

import java.sql.Connection;
import java.util.List;

public interface InterfaceSQL {
    Connection getConnection();

    List<Client> getListOfClientsFromSQL();

}