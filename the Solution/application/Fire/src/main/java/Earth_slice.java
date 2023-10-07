
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author mohammedhamdy32
 */
public class Earth_slice extends javax.swing.JFrame {

    /**
     * Creates new form Earth_slice
     */
    
    JButton []nodes_slices_buttons;
    int nodes_index[];
    int counter; //Number of nodes in this slice
    
    int fire_counter;
    int fire_index[];
    JButton []fire_slice_buttons;
    Fire_main fire_main;
            
    public Earth_slice( ImageIcon slice_image , Fire_main main_frame ,int slice_number_x , int slice_number_y ) {
        initComponents();
        fire_main = main_frame;
        this.setLocation(140 , 0 );
        Earth_slice_image.setIcon( slice_image );
        int size_of_x_slice_according_to_main_earth_frame = main_frame.earth_slices_x_size / main_frame.number_of_x_slice; //55
        int size_of_y_slice_according_to_main_earth_frame = main_frame.earth_slices_y_size / main_frame.number_of_y_slice; //70
        
        /******  Handel Nodes ******/
        /* Loop to know the number of nodes in this slice */
        counter=0;
        for( int i=0;i<main_frame.number_of_nodes;i++ )
        {
            if( ( main_frame.nodes_coordinates[i][0] >= slice_number_x*55)  && (main_frame.nodes_coordinates[i][0] <= slice_number_x*55+55) && (main_frame.nodes_coordinates[i][1] >= slice_number_y*70 ) && (main_frame.nodes_coordinates[i][1] <= slice_number_y*70+70 )  )
            {  /* So the node is in this slice */
               counter++;
            }
        }
        
        /* Loop to put all nodes index in the nodes_number array */
        nodes_index = new int[counter];
        int k=0;
        for( int i=0 ; i<main_frame.number_of_nodes ; i++ )
        {
            if( ( main_frame.nodes_coordinates[i][0] >= slice_number_x*55)  && (main_frame.nodes_coordinates[i][0] <= slice_number_x*55+55) && (main_frame.nodes_coordinates[i][1] >= slice_number_y*70 ) && (main_frame.nodes_coordinates[i][1] <= slice_number_y*70+70 )  )
            {  /* So the node is in this slice */
               nodes_index[k] = i;
               k++;
            }
        }
        
        /* Put the buttons in the lable */
        nodes_slices_buttons = new JButton[counter];
        int j=0;
        for( int i=0 ; i<counter ; i++ )
        {
               int index = nodes_index[i];
               nodes_slices_buttons[j] = new JButton();
               nodes_slices_buttons[j].setBounds( (int)( (main_frame.nodes_coordinates[index][0]-55*slice_number_x)*12.7 - main_frame.size_of_square_button/2 ) , (int)( (main_frame.nodes_coordinates[index][1]-70*slice_number_y)*10 - main_frame.size_of_square_button/2 ) , main_frame.size_of_square_button , main_frame.size_of_square_button );
               nodes_slices_buttons[j].setBackground(new Color( main_frame.node_value[index] , 0, 255 - main_frame.node_value[index] ) );
               nodes_slices_buttons[j].setText("");
               Earth_slice_image.add(nodes_slices_buttons[j]); /*Add the button in the label*/
               j++;
        }
        
        
        /***** Handel Fire  ******/
        /* Loop to know the number of fires in this slice */
        fire_counter=0;
        for( int i=0;i<main_frame.fire_number ;i++ )
        {
            if( ( main_frame.fire_coordinates[i][0] >= slice_number_x*55)  && (main_frame.fire_coordinates[i][0] <= slice_number_x*55+55) && (main_frame.fire_coordinates[i][1] >= slice_number_y*70 ) && (main_frame.fire_coordinates[i][1] <= slice_number_y*70+70 )  )
            {  /* So the node is in this slice */
               fire_counter++;
            }
        }
        
         /* Loop to put all nodes index in the nodes_number array */
        fire_index = new int[fire_counter];
        int l=0;
        for( int i=0 ; i<main_frame.fire_number ; i++ )
        {
            if( ( main_frame.fire_coordinates[i][0] >= slice_number_x*55)  && (main_frame.fire_coordinates[i][0] <= slice_number_x*55+55) && (main_frame.fire_coordinates[i][1] >= slice_number_y*70 ) && (main_frame.fire_coordinates[i][1] <= slice_number_y*70+70 )  )
            {  /* So the node is in this slice */
               fire_index[l] = i;
               l++;
            }
        }
        
        /* Put the buttons in the lable */
        fire_slice_buttons = new JButton[fire_counter];
        int h=0;
        ImageIcon image;

        for( int i=0 ; i<fire_counter ; i++ )
        {
               image = new ImageIcon("C:\\Users\\mohammedhamdy32\\Documents\\NetBeansProjects\\Fire\\src\\main\\java\\img\\fire-removebg-preview.png");
               int index = fire_index[i];
               fire_slice_buttons[h] = new JButton(image);
               fire_slice_buttons[h].setBounds( (int)( (main_frame.fire_coordinates[index][0]-55*slice_number_x)*12.7 - main_frame.fire_image_size/2 ) , (int)( (main_frame.fire_coordinates[index][1]-70*slice_number_y)*10 - main_frame.fire_image_size/2 ) , main_frame.fire_image_size , main_frame.fire_image_size );
               fire_slice_buttons[h].setText("");
               Earth_slice_image.add(fire_slice_buttons[h]); /*Add the button in the label*/
               h++;
        }
        
        
        /********************************************************************************************************
         *                                    Display Nodes Information                                         *
         ********************************************************************************************************/
        for( int i=0 ; i < counter ; i++ )
        {
            int index = nodes_index[i];
            double Latitude = main_frame.Map_GetLatitude( main_frame.nodes_coordinates[index][0] );
            double Longitude = main_frame.Map_GetLongitude( main_frame.nodes_coordinates[index][1] );
            Nodes_info_text_area.append("Node " + (int)(i+1) +" : " +Latitude + "," + Longitude + "\n");
            Nodes_info_text_area.append( "Value : " + main_frame.node_value[index] );
        }
        
        
        /***********************************************************************************************************
         *                                    Display Fire Information                                             *
         ***********************************************************************************************************/
        for( int i=0 ; i<fire_counter ; i++ )
        {
            int index = fire_index[i];
            double Latitude = main_frame.Map_GetLatitude( main_frame.fire_coordinates[index][0] );
            double Longitude = main_frame.Map_GetLongitude( main_frame.fire_coordinates[index][1] );
            Fire_info_text_area.append("Fire " + (int)(i+1) +" : " +Latitude + "," + Longitude + "\n");
            Fire_info_text_area.append( "Value : " + main_frame.node_value[index] );
        }
        
        
    }
    
    
    public void Display_nodes_info()
    {
  
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Earth_slice_panel = new javax.swing.JPanel();
        Earth_slice_image = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Nodes_info_text_area = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        Fire_info_text_area = new javax.swing.JTextArea();
        jTextFieldLatitude = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jTextFieldLongitude = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabelDisplay = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        Earth_slice_panel.setBackground(new java.awt.Color(255, 255, 255));

        Earth_slice_image.setIcon(new javax.swing.ImageIcon("C:\\Users\\mohammedhamdy32\\Documents\\NetBeansProjects\\Fire\\src\\main\\java\\img\\earth_slices\\slice_3_11.jpg")); // NOI18N

        Nodes_info_text_area.setColumns(20);
        Nodes_info_text_area.setRows(5);
        Nodes_info_text_area.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        Nodes_info_text_area.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        Nodes_info_text_area.setEnabled(false);
        jScrollPane1.setViewportView(Nodes_info_text_area);

        Fire_info_text_area.setColumns(20);
        Fire_info_text_area.setRows(5);
        Fire_info_text_area.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        Fire_info_text_area.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        Fire_info_text_area.setEnabled(false);
        jScrollPane2.setViewportView(Fire_info_text_area);

        jTextFieldLatitude.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldLatitudeActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton1.setText("check location");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextFieldLongitude.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldLongitudeActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Latitude");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Longitude");

        jLabelDisplay.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        javax.swing.GroupLayout Earth_slice_panelLayout = new javax.swing.GroupLayout(Earth_slice_panel);
        Earth_slice_panel.setLayout(Earth_slice_panelLayout);
        Earth_slice_panelLayout.setHorizontalGroup(
            Earth_slice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Earth_slice_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Earth_slice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(Earth_slice_panelLayout.createSequentialGroup()
                        .addComponent(Earth_slice_image)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                        .addGroup(Earth_slice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
                            .addComponent(jScrollPane2))
                        .addGap(63, 63, 63))
                    .addGroup(Earth_slice_panelLayout.createSequentialGroup()
                        .addGroup(Earth_slice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(Earth_slice_panelLayout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldLatitude, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(Earth_slice_panelLayout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextFieldLongitude, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(32, 32, 32)
                        .addComponent(jButton1)
                        .addGap(49, 49, 49)
                        .addComponent(jLabelDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(210, 210, 210))))
        );
        Earth_slice_panelLayout.setVerticalGroup(
            Earth_slice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Earth_slice_panelLayout.createSequentialGroup()
                .addGroup(Earth_slice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Earth_slice_panelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Earth_slice_image))
                    .addGroup(Earth_slice_panelLayout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(Earth_slice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Earth_slice_panelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(Earth_slice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldLatitude, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(Earth_slice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldLongitude, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(Earth_slice_panelLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(Earth_slice_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelDisplay, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Earth_slice_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Earth_slice_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldLatitudeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldLatitudeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldLatitudeActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       
        double lat=0,lon=0;
        try{
          lat = Double.parseDouble(jTextFieldLatitude.getText()  );
          lon = Double.parseDouble( jTextFieldLongitude.getText() );
        }catch(Exception e)
        {
            JOptionPane.showMessageDialog( this , "Invaled parameter");
        }
        
        jLabelDisplay.setText("No near Fire");
        /* Check if there is a fire near this point */
        for( int i=0 ; i < fire_main.fire_number ; i++ )
        {
            System.out.println(( fire_main.Map_GetLatitude(fire_main.fire_coordinates[i][0]+5)  ) );
            if( lat <= ( fire_main.Map_GetLatitude(fire_main.fire_coordinates[i][0]+5)  )  && lat >= ( fire_main.Map_GetLongitude(fire_main.fire_coordinates[i][0]-5) )  )
              if( lon >= ( fire_main.Map_GetLongitude(fire_main.fire_coordinates[i][1]+5)  )  && lon <= ( fire_main.Map_GetLongitude(fire_main.fire_coordinates[i][1]-5) ) )
              {
                jLabelDisplay.setText("Take care there is a fire near you!! at point lat:"+lat+" lon:"+lon);
                JOptionPane.showMessageDialog(this, "Take care there is a fire near you!! at point lat:"+lat+" lon:"+lon);
              }
        }
        
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextFieldLongitudeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldLongitudeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldLongitudeActionPerformed

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
            java.util.logging.Logger.getLogger(Earth_slice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Earth_slice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Earth_slice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Earth_slice.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ImageIcon image ;
                 image = new ImageIcon("C:\\Users\\mohammedhamdy32\\Documents\\NetBeansProjects\\Fire\\src\\main\\java\\img\\earth_slices\\slice_3_11.jpg");
                 Fire_main main_frame = new Fire_main();
                new Earth_slice(image,main_frame,3,11).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Earth_slice_image;
    private javax.swing.JPanel Earth_slice_panel;
    private javax.swing.JTextArea Fire_info_text_area;
    private javax.swing.JTextArea Nodes_info_text_area;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelDisplay;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextFieldLatitude;
    private javax.swing.JTextField jTextFieldLongitude;
    // End of variables declaration//GEN-END:variables
}
