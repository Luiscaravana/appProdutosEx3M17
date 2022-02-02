package Ex3M17;

import Exerc7.FLogin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class FProdutos {


    private JButton buttonSearch;
    private JTextField textFieldPName;
    private JTextField textFieldPrice;
    private JTextField textFieldQty;
    private JButton saveButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JPanel panelPrincipal;
    private JTextField textFieldPid;


    private PreparedStatement pst;
    private Connection con;


    public static void main(String[] args) {
        JFrame frame = new JFrame("Gestão de Produtos");
        frame.setContentPane(new FProdutos().panelPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550,400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void conexao() {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost/bdProdutos", "root", "1234");
                System.out.println("Success");
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
    }

    public FProdutos() {

        buttonSearch.addActionListener(new ActionListener() {


            @Override
            public void actionPerformed(ActionEvent e) {
                conexao();
                try {
                    String pid = textFieldPid.getText();

                    pst = con.prepareStatement("select NOME,PRECO,QUANTIDADE from produtos where ID = ?");

                    pst.setString(1,pid);

                    ResultSet rs = pst.executeQuery();

                    if(rs.next()==true)
                    {

                        String name = rs.getString(1);

                        String price = rs.getString(2);

                        String qty = rs.getString(3);

                        textFieldPName.setText(name);
                        textFieldPrice.setText(price);
                        textFieldQty.setText(qty);
                    }
                    else
                    {
                        textFieldPName.setText("");
                        textFieldPrice.setText("");
                        textFieldQty.setText("");
                        JOptionPane.showMessageDialog(null,"Invalid Product ID");
                    }
                }

                catch (SQLException ex)  {
                    ex.printStackTrace();  }

            }
        });


        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conexao();
                String name,price,qty;

                name = textFieldPName.getText();

                price = textFieldPrice.getText();

                qty = textFieldQty.getText();


                try {

                    pst = con.prepareStatement("insert into produtos(NOME,PRECO,QUANTIDADE)values(?,?,?)");
                    pst.setString(1, name);

                    pst.setString(2, price);

                    pst.setString(3, qty);

                    pst.executeUpdate();

                    JOptionPane.showMessageDialog(null,"Record Added!!!!");

                    textFieldPName.setText("");

                    textFieldPrice.setText("");

                    textFieldQty.setText("");

                    textFieldPName.requestFocus();

                }

                catch (SQLException e1)

                {

                    e1.printStackTrace();

                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conexao();
                String pid,name,price,qty;
                name = textFieldPName.getText();
                price = textFieldPrice.getText();
                qty = textFieldQty.getText();
                pid = textFieldPid.getText();
                try {
                    pst = con.prepareStatement("update produtos set NOME = ?,PRECO = ?,QUANTIDADE = ? where ID = ?");
                    pst.setString(1, name);
                    pst.setString(2, price);
                    pst.setString(3, qty);
                    pst.setString(4, pid);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Update!!");
                    textFieldPName.setText("");
                    textFieldPrice.setText("");
                    textFieldQty.setText("");
                    textFieldPName.requestFocus();
                    textFieldPid.setText("");
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });


        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                conexao();
                String bid;

                bid = textFieldPid.getText();
                try {
                    pst = con.prepareStatement("delete from produtos where PRECO = ?");
                    pst.setString(1, bid);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Delete!!");
                    textFieldPName.setText("");
                    textFieldPrice.setText("");
                    textFieldQty.setText("");
                    textFieldPName.requestFocus();
                    textFieldPid.setText("");
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });

    }
}
