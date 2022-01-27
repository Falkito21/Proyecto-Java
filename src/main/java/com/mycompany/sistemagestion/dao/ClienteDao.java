/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemagestion.dao;

import com.mycompany.sistemagestion.models.Cliente;
import com.mysql.jdbc.StringUtils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tito
 */
public class ClienteDao {
    public Connection conectar(){
        //CREAMOS LAS BARIABLES PARA CONECTARSE A LA BASE DE DATOS DE MYSQL 
        String baseDatos = "java";
        String usuario = "root";
        String contraseña = "";
        String host = "Localhost";
        String puerto = "3306";
        String driver = "com.mysql.jdbc.Driver";
        String conexionUrl = "jdbc:mysql://" 
                + host + ":" 
                + puerto + "/" 
                + baseDatos + "?useSSL=false";
        
        Connection conexion = null;
        
        try {
            Class.forName(driver);
            //NOS CONECTAMOS A LA BASE DE DATOS 
            conexion =DriverManager.getConnection(conexionUrl, usuario, contraseña);
        
        } catch (Exception ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conexion;
    }
    
    
    //CREAMOS LA FUNCION AGREGAR CLIENTES **************************************
    //LA FUNCION AGREGAR RESIVE LOS VALORES DE CLIENTE
    public void agregar(Cliente cliente){
        //CREAMOS LAS BARIABLES PARA CONECTARSE A LA BASE DE DATOS DE MYSQL 
        
        try {
            Connection conexion = conectar();
            //CONSULTA A LA BASE DE DATOS
            String sql = "INSERT INTO `clientes` (`id`, `nombre`, `apellido`, `email`, `telefono`) VALUES (NULL, '" 
                    + cliente.getNombre() +"', '  " 
                    + cliente.getApellido() + "', '" 
                    + cliente.getEmail() +"', '" 
                    + cliente.getTelefono() + "');";
            Statement statement = conexion.createStatement();
            statement.execute(sql);
        
        } catch (Exception ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //CREAMOS LA FUNCION LISTAR CLIENTES ***************************************
    public List<Cliente> listar(){
        
        List<Cliente> listado = new ArrayList<>();
        
        try {
            Connection conexion = conectar();
            //CONSULTA A LA BASE DE DATOS
            String sql = "SELECT * FROM `clientes`";
                    
            Statement statement = conexion.createStatement();
            ResultSet resultado = statement.executeQuery(sql);
        
            //RECORREMOS LO QUE HAY EN resultado Y LO COMBERTIMOS A UNA CLASE CLIENTE
            while(resultado.next()){
                Cliente cliente = new Cliente();
                cliente.setId(resultado.getString("id"));
                cliente.setNombre(resultado.getString("nombre"));
                cliente.setApellido(resultado.getString("apellido"));
                cliente.setEmail(resultado.getString("email"));
                cliente.setTelefono(resultado.getString("telefono"));
                listado.add(cliente);
            }
            
        } catch (Exception ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listado;
    }
    
    //CREAMOS LA FUNCION ELIMINAR CLIENTE **************************************
    public void eliminar(String id){
        
        try {
            Connection conexion = conectar();
            //CONSULTA A LA BASE DE DATOS
            String sql = "DELETE FROM `clientes` WHERE `clientes`.`id` = " + id;
            Statement statement = conexion.createStatement();
            statement.execute(sql);
        
        } catch (Exception ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    
    public void actualizar(Cliente cliente){
        
        try {
            Connection conexion = conectar();
            //CONSULTA A LA BASE DE DATOS
            String sql = "UPDATE `clientes` SET `nombre` = '" + cliente.getNombre() +
                    "', `apellido` = '" + cliente.getApellido() +
                    "', `email` = '" + cliente.getEmail() +
                    "', `telefono` = '" + cliente.getTelefono() +
                    "' WHERE `clientes`.`id` = " + cliente.getId() + ";";
            Statement statement = conexion.createStatement();
            statement.execute(sql);
        
        } catch (Exception ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    //CREO FUNCION QUE AGREGA O ACTUALIZA UN CLIENTE
    public void guardar(Cliente cliente) {
        
        //SI EL CLIENTE NO TIENE UN ID -
        if (StringUtils.isEmptyOrWhitespaceOnly(cliente.getId())){
            // VA A AGREGAR UN CLIENTE
            agregar(cliente);
        } else {
            // SINO VA ACUTIALIZAR EL CLIENTE
            actualizar(cliente);
        }
    }
    
    
}
