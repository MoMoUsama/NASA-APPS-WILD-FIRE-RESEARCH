/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author mohammedhamdy32
 */
import java.awt.Color;
import java.awt.Graphics;
import java.sql.*;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Fire_main extends javax.swing.JFrame {

    Connection conn;
    /*Connection variable*/

    int x_map_size = 1100;
    /* The X-axis size of our earth map */
    int y_map_size = 701;
    /* The Y-axis size of our earth map */
    int x_map_center = x_map_size / 2;
    /* خط الاستواء */
    int y_map_center = y_map_size / 2;

    /* For image slices */
    int earth_slices_x_size = 700;
    int earth_slices_y_size = 700;
    int number_of_x_slice = 20;
    int number_of_y_slice = 10;   // The number of images is number_of_x_slice * number_of_y_slice

    int x_map_scale_factor = 3;
    int y_map_scale_factor = 4;

    /* Any points at the map */
    int x_node = x_map_size / 2;
    /* used to calculate the latitude and Longitude */
    int y_node = y_map_size / 2;
    int longitude_node = 0;
    /* The Longitude when x=550 */
    int latitude_node = 0;
    /* The latitude  when y=350 */


 /* Node buttons */
    public int number_of_nodes;
    /* The number of node button array*/
    int size_of_square_button = 5;
    JButton[] nodes_lables;
    int[][] nodes_coordinates;
    /*This matrex will contain the coordinates of each node */
    int[] node_value;
    /* Contains the value of each node */


    // Import ImageIcon     
    ImageIcon earth_slices = new ImageIcon();
    /* My earth image icon slices */
    JLabel earth_slice_lable = new JLabel();

    // Fires
    int fire_number;
    JButton[] fire_buttons;
    int[][] fire_coordinates;
    /*This matrex will contain the coordinates of each fire */
    int fire_value[][];
    int fire_image_size = 20;
    /* It conatains the values of fire like CO, CO2, etc... */

 /* Constaractor */
    public Fire_main() {
        initComponents();
        
        /* Connecting to the server */
        try {                                      // mysql:host=$host;dbname=$db;
            conn = DriverManager.getConnection("jdbc:mysql://sql10.freesqldatabase.com/sql10650569", "sql10650569", "JQDVEuL5BQ");
            System.out.println("connected");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

        
        Statement statement;
        try {
            statement = conn.createStatement();
            
            ResultSet resultSet = statement.executeQuery("select count(*)  from device;");
            if(resultSet.next())
              number_of_nodes = resultSet.getInt(1);
            
            number_of_nodes = number_of_nodes + 5;
            System.out.println("nodes number " + number_of_nodes );
              
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        /* Assing number of nodes variable */
        nodes_lables = new JButton[number_of_nodes];
        /*Make the array*/
        nodes_coordinates = new int[number_of_nodes][2];
        node_value = new int[number_of_nodes];

        /* Get the cordinates and values of each node */
        /*use Map_x_axis_from_Latitude and Map_x_axis_from_Longitude functions to change the GPS coordinates to our image corredinates */
       
        
        try {
          statement = conn.createStatement();
          
          ResultSet resultSet = statement.executeQuery("SELECT longitude, latitude FROM device;");
          
          double lat , lon ;
          int counter =0;
          while(resultSet.next())
          {
              lat = resultSet.getDouble(1);
              lon = resultSet.getDouble(2);
              nodes_coordinates[counter][0] = Map_x_axis_from_Latitude(lat) ;
              nodes_coordinates[counter][1] = Map_y_axis_from_Longitude(lon);
              node_value[counter] = 255;             
              System.out.println( Map_x_axis_from_Latitude(lat) + " " + Map_y_axis_from_Longitude(lon) + "\n" );
              counter++;
              
          }
          
     
         
        }catch (SQLException ex) {
            System.out.println(ex);
        }
        
//        node_value[0] = 100;
//        node_value[1] = 132;
//        node_value[2] = 809;
//        node_value[3] = 200;
//        node_value[4] = 240;
//        node_value[5] = 656;

        /* Fire icons */
        fire_number = 1;
        fire_buttons = new JButton[fire_number];
        /*Make the array*/
        fire_coordinates = new int[fire_number][2];
//       fire_value = new int[fire_number][3];
        fire_coordinates[0][0] = Map_x_axis_from_Latitude(-95.75);
        fire_coordinates[0][1] = Map_y_axis_from_Longitude(37.2);

        /* Fill the buttons */
        /* Make the nodes */
        Make_nodes();
        /* Make fire */
        Make_fire();
        


    }/*End of constractor*/

 /* This function take the x_axis point to our map image and returns the Latitude at this point */
    public double Map_GetLatitude(double x) {
        double latitude = ((x - x_node) / x_map_scale_factor) + latitude_node;
        return latitude;
    }

    /* This function take the x_axis point to our map image and returns the Longitude at this point */
    public double Map_GetLongitude(double y) {

        double Longitude = longitude_node - ((y - y_node) / y_map_scale_factor);
        return Longitude;
    }

    /* This function take the Latitude returns the x-axis at our map image */
    private int Map_x_axis_from_Latitude(double Latitude) {

        int x = (int) (x_map_scale_factor * (Latitude - latitude_node)) + x_node;
        return x;
    }

    /* This function take the Longitude returns the y-axis at our map image */
    private int Map_y_axis_from_Longitude(double Longitude) {

        int y = (int) ((y_map_scale_factor * (longitude_node - Longitude)) + y_node);
        return y;
    }

    /* This function takes the x_axis and y-axis points to our map image and returns the Latitude and Longitude at this point */
    private double[] Map_GetLatitudeLongitude(double x, double y) {
        double temp[] = new double[2];

        temp[0] = Map_GetLatitude(x);
        temp[1] = Map_GetLongitude(y);

        return temp;
    }

    private void Make_nodes() {

        for (int i = 0 ; i < number_of_nodes ; i++) {
            nodes_lables[i] = new JButton();
            nodes_lables[i].setBounds(nodes_coordinates[i][0] - size_of_square_button / 2, nodes_coordinates[i][1] - size_of_square_button / 2, size_of_square_button, size_of_square_button);
            nodes_lables[i].setBackground(new Color(node_value[i], 0, 255 - node_value[i]));
            nodes_lables[i].setText("");
            EarthLable.add(nodes_lables[i]);
            /*Add the button in the label*/
        }

    }

    private void Make_fire() {
        ImageIcon image;

        for (int i = 0 ; i < fire_number ; i++) {

            image = new ImageIcon("C:\\Users\\mohammedhamdy32\\Documents\\NetBeansProjects\\Fire\\src\\main\\java\\img\\fire-removebg-preview.png");
            fire_buttons[i] = new JButton(image);
            fire_buttons[i].setBounds(fire_coordinates[i][0] - fire_image_size / 2, fire_coordinates[i][1] - fire_image_size / 2, fire_image_size, fire_image_size);
            fire_buttons[i].setText("");
            fire_buttons[i].setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
            EarthLable.add(fire_buttons[i]);
        }
    }
    
    
    /* Visable and Disvisible Node */
    private void DisVisibleNodes()
    {
        for (int i = 0; i < number_of_nodes; i++)
        {
           nodes_lables[i].setVisible(false);
        }
    }
    
    private void VisibleNodes()
    {
        for (int i = 0; i < number_of_nodes; i++)
        {
           nodes_lables[i].setVisible(true);
        }
    }
    
    /* Visable and Disvisible Fire */
    private void DisVisibleFire()
    {
        for (int i = 0; i < fire_number; i++)
        {
           fire_buttons[i].setVisible(false);
        }
    }
    
    private void VisibleFire()
    {
        for (int i = 0; i < fire_number; i++)
        {
           fire_buttons[i].setVisible(true);
        }
    }


    private void MatrexButtonsSetValues() {

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        EarthPanel = new javax.swing.JPanel();
        EarthLable = new javax.swing.JLabel();
        nodeToggleButton = new javax.swing.JToggleButton();
        FireToggleButton = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 102));

        mainPanel.setBackground(new java.awt.Color(0, 0, 51));

        EarthPanel.setBackground(new java.awt.Color(0, 0, 51));

        EarthLable.setIcon(new javax.swing.ImageIcon("C:\\Users\\mohammedhamdy32\\Documents\\NetBeansProjects\\Fire\\src\\main\\java\\img\\earth_image3.jpg")); // NOI18N
        EarthLable.setToolTipText("");
        EarthLable.setOpaque(true);
        EarthLable.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                EarthLableMouseMoved(evt);
            }
        });
        EarthLable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                EarthLableMousePressed(evt);
            }
        });

        nodeToggleButton.setBackground(new java.awt.Color(0, 102, 0));
        nodeToggleButton.setText("Nodes");
        nodeToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nodeToggleButtonActionPerformed(evt);
            }
        });

        FireToggleButton.setBackground(new java.awt.Color(0, 102, 51));
        FireToggleButton.setText("Fire");
        FireToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FireToggleButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout EarthPanelLayout = new javax.swing.GroupLayout(EarthPanel);
        EarthPanel.setLayout(EarthPanelLayout);
        EarthPanelLayout.setHorizontalGroup(
            EarthPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, EarthPanelLayout.createSequentialGroup()
                .addGap(122, 122, 122)
                .addGroup(EarthPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(FireToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nodeToggleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 135, Short.MAX_VALUE)
                .addComponent(EarthLable)
                .addContainerGap())
        );
        EarthPanelLayout.setVerticalGroup(
            EarthPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EarthPanelLayout.createSequentialGroup()
                .addContainerGap(42, Short.MAX_VALUE)
                .addComponent(EarthLable)
                .addGap(17, 17, 17))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, EarthPanelLayout.createSequentialGroup()
                .addGap(189, 189, 189)
                .addComponent(FireToggleButton)
                .addGap(88, 88, 88)
                .addComponent(nodeToggleButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        EarthLable.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(EarthPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(226, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(EarthPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(120, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void EarthLableMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EarthLableMouseMoved
        // TODO add your handling code here:
        int x, y;
        x = evt.getX();
        y = evt.getY();
        double Latitude_and_Longitude[] = new double[2];
        Latitude_and_Longitude = Map_GetLatitudeLongitude(x, y);

        EarthLable.setToolTipText(Latitude_and_Longitude[0] + "," + Latitude_and_Longitude[1]);

    }//GEN-LAST:event_EarthLableMouseMoved

    private void EarthLableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EarthLableMousePressed
        int x = evt.getX();
        /* Get the pressent posstion */
        int y = evt.getY();

        int x_image_slice_number = x / (x_map_size / number_of_x_slice);
        int y_image_slice_number = y / (y_map_size / number_of_y_slice);

        earth_slices = new ImageIcon("C:\\Users\\mohammedhamdy32\\Documents\\NetBeansProjects\\Fire\\src\\main\\java\\img\\earth_slices\\slice_" + y_image_slice_number + "_" + x_image_slice_number + ".jpg");
        new Earth_slice(earth_slices, this, x_image_slice_number, y_image_slice_number).setVisible(true);


    }//GEN-LAST:event_EarthLableMousePressed

    private void FireToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FireToggleButtonActionPerformed

        if( FireToggleButton.isSelected() )
        {
            DisVisibleFire();
        }else
        {
            VisibleFire();
        }
        
    }//GEN-LAST:event_FireToggleButtonActionPerformed

    private void nodeToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nodeToggleButtonActionPerformed

        if( nodeToggleButton.isSelected() )
        {
            DisVisibleNodes();
        }else
        {
            VisibleNodes();
        }

    }//GEN-LAST:event_nodeToggleButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Fire_main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Fire_main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Fire_main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Fire_main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Fire_main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel EarthLable;
    private javax.swing.JPanel EarthPanel;
    private javax.swing.JToggleButton FireToggleButton;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JToggleButton nodeToggleButton;
    // End of variables declaration//GEN-END:variables
}
