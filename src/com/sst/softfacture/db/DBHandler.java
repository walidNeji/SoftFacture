/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sst.softfacture.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Neji Med Walid << medwalid.neji@bitaka.com.tn >>
 */
public class DBHandler {

    private static DBHandler databaseHandler;
    private final EntityManagerFactory managerFactory;

    private DBHandler() {
        this.managerFactory = Persistence.createEntityManagerFactory("SoftFacturePU");
    }

    public static EntityManagerFactory instance() {
        if (databaseHandler == null) {
            databaseHandler = new DBHandler();
        }
        return databaseHandler.managerFactory;

    }
}
