/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelDao;

import ModelBeans.BeansCliente;
import ModelConexao.ConexaoBD;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Aurelio Macie Junior
 */
public class DaoCliente {

    ConexaoBD conex = new ConexaoBD();
    BeansCliente cliente = new BeansCliente();

    public void salvar(BeansCliente cliente) {
        conex.conexao();
        try {
            PreparedStatement pst = conex.con.prepareStatement("insert into clientes(nome, contacto, idade, sexo, morada) values(?,?,?,?,?)");
            pst.setString(1, cliente.getNome());
            pst.setString(2, cliente.getContacto());
            pst.setInt(3, cliente.getIdade());
            pst.setString(4, String.valueOf(cliente.getSexo()));
            pst.setString(5, cliente.getMorada());
            pst.execute();
            JOptionPane.showMessageDialog(null, "DADOS INSERIDOS COM SUCESSO!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "NAO FOI POSSIVEL INSERIR DADOS!\n Error:" + ex);
        }
        conex.desconexao();
    }

    public void editar(BeansCliente cliente) {
        conex.conexao();
        try {
            PreparedStatement pst = conex.con.prepareStatement("update clientes set nome=?, contacto=?, idade=?, sexo=?, morada=? where id=?");
            pst.setString(1, cliente.getNome());
            pst.setString(2, cliente.getContacto());
            pst.setInt(3, cliente.getIdade());
            pst.setString(4, cliente.getSexo());
            pst.setString(5, cliente.getMorada());
            pst.setInt(6, cliente.getCodigo());
            pst.execute();
            JOptionPane.showMessageDialog(null, "DADOS ALTERADOS COM SUCESSO!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "NAO FOI POSSIVEL ALTERAR DADOS!\n Error:" + ex);
        }
        conex.desconexao();
    }

    
    public void excluir(BeansCliente cliente){
        conex.conexao();
        try {
            PreparedStatement pst = conex.con.prepareStatement("delete from clientes where id=?");
            pst.setInt(1, cliente.getCodigo());
            pst.execute();
            JOptionPane.showMessageDialog(null, "DADOS EXCLUIDOS COM SUCESSO!");
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "ERRO NA EXCLUSAO DE DADOS!\n Error:" + ex);
        }
        
        conex.desconexao();
    }
    
    public BeansCliente buscaCliente(BeansCliente cliente) {
        conex.conexao();
        conex.executaSql("select * from clientes where nome like '%" + cliente.getPesquisa() + "%'");

           try { 
            conex.rs.first();
            cliente.setCodigo(conex.rs.getInt("id"));
            cliente.setNome(conex.rs.getString("nome"));
            cliente.setContacto(conex.rs.getString("contacto"));
            cliente.setIdade(conex.rs.getInt("idade"));
            cliente.setSexo(conex.rs.getString("sexo"));
            cliente.setMorada(conex.rs.getString("morada"));

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "CLIENTE SEM REGISTRO");
        }

        conex.desconexao();
        return cliente;
    }
}